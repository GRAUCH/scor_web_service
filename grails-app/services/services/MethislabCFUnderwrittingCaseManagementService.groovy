package services

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import com.scortelemed.schemas.methislabCF.*
import com.scortelemed.Conf
import com.ws.servicios.*
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.MethislabCFService
import com.ws.servicios.ServiceZohoService
import com.zoho.services.Frontal;
import com.zoho.services.RespuestaCRMInforme
import com.zoho.services.Usuario
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty

import org.springframework.web.context.request.RequestContextHolder
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/methislabCF")
@SchemaValidation
@GrailsCxfEndpoint(address='/methislabCF/MethislabCFUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class MethislabCFUnderwrittingCaseManagementService {

	def expedienteService
	def methislabCFService
	def estadisticasService
	def requestService
	def logginService
	def serviceZohoService

	@WebResult(name = "caseManagementResponse")
	MethislabCFUnderwrittingCaseManagementResponse MethislabCFUnderwrittingCaseManagementResponse(@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			MethislabCFUnderwrittingCaseManagementRequest MethislabCFUnderwrittingCaseManagementRequest) {

		MethislabCFUnderwrittingCaseManagementResponse resultado = new MethislabCFUnderwrittingCaseManagementResponse()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		def opername="MethislabCFUnderwrittingCaseManagementRequest"
		def requestXML = ""
		Request requestBBDD
		List<WsError> wsErrors = new ArrayList<WsError>()
		String message = null
		StatusType status = null
		String code = 0

		def company = Company.findByNombre(TipoCompany.CF_LIFE.getNombre())

        logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (company.generationAutomatic) {

					requestXML=methislabCFService.marshall(MethislabCFUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)

					wsErrors = methislabCFService.validarDatosObligatorios(requestBBDD)

					if (wsErrors != null && wsErrors.size() == 0) {

						logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

						expedienteService.crearExpediente(requestBBDD, TipoCompany.CF_LIFE, MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

						requestService.insertarRecibido(company, MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)


						message = "Il caso e stato elaborato correttamente"
						status = StatusType.OK
						code = 0
						def dni = MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.fiscalIdentificationNumber
						String dniPart = dni.length() > 13 ? dni.substring(0, 13) : dni.padRight(13, '0')
						def numSolicitud = MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber+dniPart

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *
						 */
						logginService.putInfoMessage("Buscando en CRM solicitud de " + company.nombre + " con numero de solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
						expedienteService.busquedaCrm(requestBBDD, company, numSolicitud, MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.certificateNumber, null)

					} else {
						String error =  util.detalleError(wsErrors)
						message = "Errore di convalida: " + error
						status = StatusType.ERROR
						code = 8
						requestService.insertarError(company, MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)
						logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)
					}
				}
				
			} else {
				message = "L'operazione viene disattivata temporaneamente"
				status = StatusType.OK
				code = 1
				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		} catch (Exception e){

			message = "Error: " + e.printStackTrace()
			status = StatusType.ERROR
			code = 2
			requestService.insertarError(company, MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + MethislabCFUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,e.getMessage())
			
		}finally{
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(message)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)

		return resultado
	}

	@WebResult(name = "CaseManagementResultsResponse")
	MethislabCFUnderwrittingCasesResultsResponse methislabCFUnderwrittingCasesResultsResponse(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
			MethislabCFUnderwrittingCasesResultsRequest methislabCFUnderwrittingCasesResults) {

		def opername="MethislabCFUnderwrittingCaseManagementResponse"
		def requestXML = ""
		Request requestBBDD
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		String messages = null
		StatusType status = null
		int code = 0

		MethislabCFUnderwrittingCasesResultsResponse resultado =new MethislabCFUnderwrittingCasesResultsResponse()

		Company company = Company.findByNombre(TipoCompany.CF_LIFE.getNombre())

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString())

			if(operacion && operacion.activo ) {

				if (methislabCFUnderwrittingCasesResults && methislabCFUnderwrittingCasesResults.dateStart && methislabCFUnderwrittingCasesResults.dateEnd){

					requestXML=methislabCFService.marshall(methislabCFUnderwrittingCasesResults)
					requestBBDD=requestService.crear(opername,requestXML)

					Date date = methislabCFUnderwrittingCasesResults.getDateStart().toGregorianCalendar().getTime();
					Date dateEnd = methislabCFUnderwrittingCasesResults.getDateEnd().toGregorianCalendar().getTime();

					// Creamos el formato base sin zona
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

					// Calculamos el timezone en horas
					TimeZone tz = TimeZone.getDefault();
					int offsetHours = tz.getRawOffset() / (1000 * 60 * 60); // convierte milisegundos a horas

					// Añadimos el offset manualmente
					String fechaIni = sdf.format(date) + String.format(":%02d", offsetHours);
					String fechaFin = sdf.format(dateEnd) + String.format(":%02d", offsetHours);

					Frontal frontal =  serviceZohoService.instanciarFrontalZoho(Conf.findByName("frontalZoho.wsdl")?.value)
					Usuario usuario = serviceZohoService.obtenerUsuarioFrontal(company.ou);

					logginService.putInfoMessage("Consultando zoho con fecha " + fechaIni + "-" + fechaFin)
					RespuestaCRMInforme respuestaCRMInforme_one = frontal.informeExpedientes(usuario, company.codigoSt, null, 1, fechaIni, fechaFin);
					logginService.putInfoMessage("Zoho ha devuelto "+ respuestaCRMInforme_one.listaExpedientesInforme.size() + " expedientes para el estado 1")
					RespuestaCRMInforme respuestaCRMInforme_two = frontal.informeExpedientes(usuario, company.codigoSt, null, 2, fechaIni, fechaFin);
					logginService.putInfoMessage("Zoho ha devuelto "+ respuestaCRMInforme_two.listaExpedientesInforme.size() + " expedientes para el estado 2")
					logginService.putInfoMessage("Consultando zoho termiando")

					if (respuestaCRMInforme_one?.listaExpedientesInforme) {
						expedientes.addAll(respuestaCRMInforme_one.listaExpedientesInforme)
					}

					if (respuestaCRMInforme_two?.listaExpedientesInforme) {
						expedientes.addAll(respuestaCRMInforme_two.listaExpedientesInforme)
					}
					String idIdentificador = new Date().format( 'dd-mm-yyyy HH:mm:ss' )
					if(expedientes){
						logginService.putInfoMessage("Se van a procesar " + expedientes.size() + " expedientes para " + company.nombre)
						requestService.insertarEnvio(company, "SOLICITUD: " + idIdentificador , requestXML.toString())
						expedientes.each { expedientePoliza ->
							requestService.insertarEnvio(company, "EXPEDIENTE: " + idIdentificador, "ST:" + expedientePoliza.getCodigoST() + "#CIA:" + expedientePoliza.getNumSolicitud())
							resultado.getExpediente().add(methislabCFService.rellenaDatosSalidaConsulta(expedientePoliza, methislabCFUnderwrittingCasesResults.dateStart))
						}

						messages = "Risultati restituiti"
						status = StatusType.OK
						code = 3

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion realizada correctamente para " + company.nombre + " con fecha: " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString())
					}else{

						messages = "Nessun risultato per le date indicate"
						status = StatusType.OK
						code = 6
						requestService.insertarEnvio(company, "SOLICITUD: " + idIdentificador , "No hay resultados para " + company.nombre)
						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No hay resultados para " + company.nombre)
					}
				} else {

					messages = "Nessuna data  stata inserita per la query"
					status = StatusType.ERROR
					code = 7
					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No se han introducido fechas para la consulta " + company.nombre)
				}
			} else {

				messages = "L'operazione viene disabilitata temporaneamente"
				status = StatusType.OK
				code = 1
				logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ResultadoReconocimientoMedico","Peticion de " + company.nombre + " con fecha: " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString() + " Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			messages = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2
			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString(), e)
			requestService.insertarError(company, methislabCFUnderwrittingCasesResults.dateStart.toString().substring(0,10) + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString().substring(0,10), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + methislabCFUnderwrittingCasesResults.dateStart.toString() + "-" + methislabCFUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
			
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessages(messages)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)

		return resultado
	}
}
package services

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.methislab.*
import com.ws.servicios.*
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.MethislabService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import servicios.RespuestaCRMInforme

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/methislab")
@SchemaValidation
@GrailsCxfEndpoint(address='/methislab/MethislabUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class MethislabUnderwrittingCaseManagementService {

	def expedienteService
	@Autowired
	private MethislabService methislabService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "caseManagementResponse")
	MethislabUnderwrittingCaseManagementResponse methislabUnderwrittingCaseManagementResponse(@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			MethislabUnderwrittingCaseManagementRequest methislabUnderwrittingCaseManagementRequest) {

		MethislabUnderwrittingCaseManagementResponse resultado = new MethislabUnderwrittingCaseManagementResponse()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		def opername="MethislabUnderwrittingCaseManagementRequest"
		def requestXML = ""
		def requestBBDD
		def tarificadorService
		List<WsError> wsErrors = new ArrayList<WsError>()
		String message = null
		StatusType status = null
		String code = 0

		def company = Company.findByNombre('methislab')

        logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (Company.findByNombre("methislab").generationAutomatic) {

					requestXML=methislabService.marshall("http://www.scortelemed.com/schemas/methislab",methislabUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					requestBBDD.fecha_procesado = new Date()
					requestBBDD.save(flush:true)

					wsErrors = methislabService.validarDatosObligatorios(requestBBDD)

					if (wsErrors != null && wsErrors.size() == 0) {

						message = "Il caso e stato elaborato correttamente";
						status = StatusType.OK
						code = 0

						logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

						expedienteService.crearExpediente(requestBBDD, TipoCompany.METHIS_LAB)

						methislabService.insertarRecibido(company, methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA")

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *
						 */
						logginService.putInfoMessage("Buscando en CRM solicitud de " + company.nombre + " con numero de solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
						methislabService.busquedaCrm(methislabUnderwrittingCaseManagementRequest.candidateInformation.policyNumber, company.ou, methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, opername, company.codigoSt, company.id, requestBBDD, methislabUnderwrittingCaseManagementRequest.candidateInformation.certificateNumber, company.nombre)

					} else {

						String error =  util.detalleError(wsErrors)

						message = "Errore di convalida: " + error
						status = StatusType.ERROR
						code = 8

						methislabService.insertarError(company, methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)
						logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)

					}
				}
				
			} else {

				message = "L'operazione viene disattivata temporaneamente";
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		} catch (Exception e){

			message = "Error: " + e.printStackTrace();
			status = StatusType.ERROR
			code = 2

			methislabService.insertarError(company, methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + methislabUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,e.getMessage())
			
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
	MethislabUnderwrittingCasesResultsResponse methislabUnderwrittingCasesResultsResponse(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
			MethislabUnderwrittingCasesResultsRequest methislabUnderwrittingCasesResults) {

		def opername="MethislabUnderwrittingCaseManagementResponse"
		def requestXML = ""
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>();
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		String messages = null
		StatusType status = null
		int code = 0

		MethislabUnderwrittingCasesResultsResponse resultado =new MethislabUnderwrittingCasesResultsResponse()
		Company company = Company.findByNombre("methislab")

		def timedelay = System.currentTimeMillis()
		logginService.putInfoEndpoint("Endpoint-" + opername + "Tiempo inicial: ", timedelay)
		try{
			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)
			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString())
			if(operacion && operacion.activo ) {

				if (methislabUnderwrittingCasesResults && methislabUnderwrittingCasesResults.dateStart && methislabUnderwrittingCasesResults.dateEnd){

					requestXML=methislabService.marshall("http://www.scortelemed.com/schemas/methislab",methislabUnderwrittingCasesResults)

					requestService.crear(opername,requestXML)

					Date date = methislabUnderwrittingCasesResults.dateStart.toGregorianCalendar().getTime()
					SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					String fechaIni = sdfr.format(date);
					date = methislabUnderwrittingCasesResults.dateEnd.toGregorianCalendar().getTime()
					String fechaFin = sdfr.format(date);


					for (int i = 1; i < 3; i++){
						expedientes.addAll(tarificadorService.obtenerInformeExpedientes(company.codigoSt,null,i,fechaIni,fechaFin,"IT"))
					}

					methislabService.insertarEnvio(company, methislabUnderwrittingCasesResults.dateStart.toString().substring(0,10) + "-" + methislabUnderwrittingCasesResults.dateEnd.toString().substring(0,10), requestXML.toString())

					if(expedientes){
						expedientes.each { expedientePoliza ->
							resultado.getExpediente().add(methislabService.rellenaDatosSalidaConsulta(expedientePoliza, methislabUnderwrittingCasesResults.dateStart, logginService))
						}
						messages = "Risultati restituiti"
						status = StatusType.OK
						code = 3
						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion realizada correctamente para " + company.nombre + " con fecha: " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString())
					}else{

						messages = "Nessun risultato per le date indicate"
						status = StatusType.OK
						code = 6

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No hay resultados para " + company.nombre)
					}
				} else {

					messages = "Nessuna data � stata inserita per la query"
					status = StatusType.ERROR
					code = 7

					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No se han introducido fechas para la consulta " + company.nombre)
				}
			} else {

				messages = "L'operazione viene disabilitata temporaneamente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ResultadoReconocimientoMedico","Peticion de " + company.nombre + " con fecha: " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString() + " Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){


			messages = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString(), e)

			methislabService.insertarError(company, methislabUnderwrittingCasesResults.dateStart.toString().substring(0,10) + "-" + methislabUnderwrittingCasesResults.dateEnd.toString().substring(0,10), requestXML.toString(), "CONSULTA", "Peticion no realizada para solicitud: " + methislabUnderwrittingCasesResults.dateStart.toString() + "-" + methislabUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
			
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessages(messages)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)
		logginService.putInfoEndpoint(opername,"Estoy devolviendo resultado ${resultado}")
		def timeFinal = System.currentTimeMillis() - timedelay
		logginService.putInfoEndpoint("Endpoint-"+opername +"Tiempo tiempo TOTAL: ", timeFinal)
		return resultado
	}
}
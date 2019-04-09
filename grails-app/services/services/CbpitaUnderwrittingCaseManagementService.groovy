package services

import com.scortelemed.Company

import com.ws.servicios.*
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
import com.scortelemed.Operacion
import java.text.SimpleDateFormat
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import grails.util.Environment
import com.scortelemed.schemas.netinsurance.StatusType
import com.scortelemed.schemas.netinsurance.StatusType
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsResponse



@WebService(targetNamespace = "http://www.scortelemed.com/schemas/cbpita")
@SchemaValidation
@GrailsCxfEndpoint(address='/cbpita/CbpitaUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class CbpitaUnderwrittingCaseManagementService	 {

	@Autowired
	private CbpitaService cbpitaService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService
	
	@WebResult(name = "caseManagementResponse")
	CbpitaUnderwrittingCaseManagementResponse cbpitaUnderwrittingCaseManagement (@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			CbpitaUnderwrittingCaseManagementRequest cbpitaUnderwrittingCaseManagementRequest) {

		CbpitaUnderwrittingCaseManagementResponse resultado = new CbpitaUnderwrittingCaseManagementResponse()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		List<WsError> wsErrors = new ArrayList<WsError>()
		def opername="CbpitaUnderwrittingCaseManagementRequest"
		def requestXML = ""
		def requestBBDD
		def tarificadorService
		Company company = null
		String message = null
		StatusType status = null
		String code = 0

		try{

			company = Company.findByNombre('cbp-italia')

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoEndpoint("Endpoint-"+opername," Peticion de " + company.nombre + " para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

			if (operacion && operacion.activo){

				if (company.generationAutomatic) {

					requestXML = cbpitaService.marshall("http://www.scortelemed.com/schemas/cbpita", cbpitaUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername, requestXML)
					requestBBDD.fecha_procesado = new Date()
					requestBBDD.save(flush: true)

					wsErrors = cbpitaService.validarDatosObligatorios(requestBBDD)

					if (wsErrors != null && wsErrors.size() == 0) {

						message = "Il caso e stato elaborato correttamente";
						status = StatusType.OK
						code = 0

						cbpitaService.crearExpediente(requestBBDD)

						logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
						cbpitaService.insertarRecibido(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA")

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *                    */

						logginService.putInfoMessage("Buscando en CRM solicitud de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
						cbpitaService.busquedaCrm(cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, company.ou, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.policyNumber, opername, company.codigoSt, company.id, requestBBDD, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.certificateNumber, company.nombre)

					} else {

						String error = util.detalleError(wsErrors)

						message = "Errore di convalida: " + error
						status = StatusType.ERROR
						code = 8

						cbpitaService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)
						logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)

					}
				}
				
			} else {

				message = "L'operazione viene disattivata temporaneamente";
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("ERROR en alta de HMI-CBP","Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		} catch (Exception e){

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			cbpitaService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ERROR en alta de HMI-CBP","Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, e)

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
	CbpitaUnderwrittingCasesResultsResponse cbpitaUnderwrittingCasesResultsResponse(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
					CbpitaUnderwrittingCasesResultsRequest cbpitaUnderwrittingCasesResultsRequest) {

		def opername="cbpitaUnderwrittingCaseManagementResponse"
		def requestXML = ""
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>();
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		Company company = null
		String message = null
		StatusType status = null
		int code = 0

		CbpitaUnderwrittingCasesResultsResponse resultado = new CbpitaUnderwrittingCasesResultsResponse()

		try{

			company = Company.findByNombre("cbp-italia")

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString())

			if(operacion && operacion.activo ) {

				if (Company.findByNombre("cbp-italia").generationAutomatic) {

					if (cbpitaUnderwrittingCasesResultsRequest && cbpitaUnderwrittingCasesResultsRequest.dateStart && cbpitaUnderwrittingCasesResultsRequest.dateEnd) {

						requestXML = cbpitaService.marshall("http://www.scortelemed.com/schemas/cbpita", cbpitaUnderwrittingCasesResultsRequest)

						requestService.crear(opername, requestXML)

						Date date = cbpitaUnderwrittingCasesResultsRequest.dateStart.toGregorianCalendar().getTime()
						SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
						String fechaIni = sdfr.format(date);
						date = cbpitaUnderwrittingCasesResultsRequest.dateEnd.toGregorianCalendar().getTime()
						String fechaFin = sdfr.format(date);


						for (int i = 1; i < 3; i++) {
							if (Environment.current.name.equals("production_wildfly")) {
								expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1071", null, i, fechaIni, fechaFin, "IT"))
							} else {
								expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1071", null, i, fechaIni, fechaFin, "IT"))
							}
						}

						cbpitaService.insertarEnvio(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), requestXML.toString())

						if (expedientes) {

							expedientes.each { expedientePoliza ->

								resultado.getExpediente().add(cbpitaService.rellenaDatosSalidaConsulta(expedientePoliza, cbpitaUnderwrittingCasesResultsRequest.dateStart))
							}

							message = "Risultati restituiti"
							status = StatusType.OK
							code = 0

							logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Peticion realizada correctamente para " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString())
						} else {

							message = "Nessun risultato per le date indicate"
							status = StatusType.OK
							code = 6

							logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No hay resultados para " + company.nombre)
						}
					} else {

						message = "Nessuna data � stata inserita per la query"
						status = StatusType.ERROR
						code = 7
						logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han introducido fechas para la consulta " + company.nombre)
					}
				}
				} else {

					message = "L'operazione viene disabilitata temporaneamente"
					status = StatusType.OK
					code = 1

					logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				}

		}catch (Exception e){


			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString() + ". Error: " + e.getMessage())

			cbpitaService.insertarError(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0,10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0,10), requestXML.toString(), "CONSULTA", "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString() + ". Error: " + e.getMessage())

		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(notes)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)

		return resultado
	}
}
package services


import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import com.scortelemed.Company
import com.scortelemed.Recibido
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.CbpitaService
import com.ws.servicios.RequestService
import com.ws.servicios.TarificadorService
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.cbpita.StatusType;

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

		String message = null
		StatusType status = null
		String code = 0

		def company = Company.findByNombre('cbp-italia')

		logginService.putInfoEndpoint("Endpoint-"+opername," Peticion de " + company.nombre + " para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

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
				correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber,"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		} catch (Exception e){

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			cbpitaService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, e)

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
}
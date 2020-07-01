package services

import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import hwsol.webservices.CorreoUtil

import java.text.SimpleDateFormat
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.web.context.request.RequestContextHolder
import servicios.Filtro

import com.scortelemed.Company
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementResponse
import com.ws.enumeration.StatusType

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/afiEsca")
@SchemaValidation
@GrailsCxfEndpoint(address='/afiesca/AfiEscaUnderwrittingCaseManagement',
excludes=['requestService', 'estadisticasService','RequestService','getRequestService','setRequestService'],
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class AfiEscaUnderwrittingCaseManagementService {

	def requestService
	def estadisticasService
	def expedienteService
	def logginService
	def francesasService
	def tarificadorService

	@WebResult(name = "AfiEscaUnderwrittingCaseManagementResponse")
	AfiEscaUnderwrittingCaseManagementResponse AfiEscaUnderwrittingCaseManagement(
			@WebParam(partName = "AfiEscaUnderwrittingCaseManagementRequest", name = "AfiEscaUnderwrittingCaseManagementRequest")
			AfiEscaUnderwrittingCaseManagementRequest afiEscaUnderwrittingCaseManagementRequest) {

		def opername = "AfiEscaUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def requestBBDD
		def company = Company.findByNombre(TipoCompany.AFI_ESCA.getNombre())
		def respuestaCrm

		Filtro filtro = new Filtro()
		AfiEscaUnderwrittingCaseManagementResponse resultado = new AfiEscaUnderwrittingCaseManagementResponse()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Afiesca para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo && afiEscaUnderwrittingCaseManagementRequest && company.generationAutomatic) {

				requestXML=requestService.marshall(afiEscaUnderwrittingCaseManagementRequest,AfiEscaUnderwrittingCaseManagementRequest.class)
				requestBBDD = requestService.crear(opername,requestXML)
				resultado.setStatusType(StatusType.ok)
				resultado.setComments("ok mensaje")


				logginService.putInfoMessage("Se procede el alta automatica de Afiesca con numero de solicitud " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
				expedienteService.crearExpediente(requestBBDD, TipoCompany.AFI_ESCA)
				requestService.insertarRecibido(company, afiEscaUnderwrittingCaseManagementRequest.policy.policy_number, requestBBDD.request, TipoOperacion.ALTA)

				/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
				 *
				 */
				correoUtil.envioEmail(opername, null, 1)
				tarificadorService.busquedaAfiescaCrm(afiEscaUnderwrittingCaseManagementRequest.policy.policy_number, company.ou, opername, company.codigoSt, company.id, requestBBDD)
								
			} else {

				resultado.setStatusType(StatusType.error)
				resultado.setComments("L'op�ration est temporairement d�sactiv�e")
				logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}
		} catch (Exception e) {

			logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number + ". - Error: "+e)
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number,e)
			resultado.setStatusType(StatusType.error)
			resultado.setComments(opername+": "+e)
			requestService.insertarError(company, afiEscaUnderwrittingCaseManagementRequest.policy.policy_number, requestBBDD.request, TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number + ". Error: " + e.getMessage())

		} finally {

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")

			resultado.setDate(afiEscaUnderwrittingCaseManagementRequest.request_Data.date)
		}

		return resultado
	}
}
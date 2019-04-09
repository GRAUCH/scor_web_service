package services

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

import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro

import com.scortelemed.Company
import com.scortelemed.Recibido
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
	def logginService
	def crearExpedienteService
	def tarificadorService

	@WebResult(name = "AfiEscaUnderwrittingCaseManagementResponse")
	AfiEscaUnderwrittingCaseManagementResponse AfiEscaUnderwrittingCaseManagement(
			@WebParam(partName = "AfiEscaUnderwrittingCaseManagementRequest", name = "AfiEscaUnderwrittingCaseManagementRequest")
			AfiEscaUnderwrittingCaseManagementRequest afiEscaUnderwrittingCaseManagementRequest) {

		def opername = "AfiEscaUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def requestBBDD
		def company = Company.findByNombre('afiesca')
		def respuestaCrm

		Filtro filtro = new Filtro()
		AfiEscaUnderwrittingCaseManagementResponse resultado = new AfiEscaUnderwrittingCaseManagementResponse()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Afiesca para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo && afiEscaUnderwrittingCaseManagementRequest && Company.findByNombre("afiesca").generationAutomatic) {

				requestXML=requestService.marshall(afiEscaUnderwrittingCaseManagementRequest,AfiEscaUnderwrittingCaseManagementRequest.class)
				requestBBDD = requestService.crear(opername,requestXML)
				requestBBDD.fecha_procesado = new Date()
				requestBBDD.save(flush:true)
				resultado.setStatusType(StatusType.ok)
				resultado.setComments("ok mensaje")


				logginService.putInfoMessage("Se procede el alta automatica de Afiesca con numero de solicitud " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
				crearExpedienteService.crearExpediente(requestBBDD)

				/**Metemos en recibidos
				 *
				 */
				Recibido recibido = new Recibido()
				recibido.setFecha(new Date())
				recibido.setCia(company.id.toString())
				recibido.setIdentificador(afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
				recibido.setInfo(requestBBDD.request)
				recibido.setOperacion("ALTA")
				recibido.save(flush:true)
				
				
				/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
				 *
				 */
				correoUtil.envioEmail(opername, null, 1)
				tarificadorService.busquedaAfiescaCrm(afiEscaUnderwrittingCaseManagementRequest.policy.policy_number, company.ou, opername, company.codigoSt, company.id, requestBBDD)
								
			} else {

				resultado.setStatusType(StatusType.error)
				resultado.setComments("L'op�ration est temporairement d�sactiv�e")
				logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
				correoUtil.envioEmailErrores("ERROR en alta de AfiEsca","Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}
		} catch (Exception e) {

			logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number + ". - Error: "+e)
			correoUtil.envioEmailErrores("ERROR en alta de AfiEsca","Peticion no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number,e)
			resultado.setStatusType(StatusType.error)
			resultado.setComments(opername+": "+e)

			/**Metemos en errores
			 *
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(afiEscaUnderwrittingCaseManagementRequest.policy.policy_number)
			error.setInfo(requestBBDD.request)
			error.setOperacion("ALTA")
			error.setError("Peticion no realizada para solicitud: " + afiEscaUnderwrittingCaseManagementRequest.policy.policy_number + ". Error: " + e.getMessage())
			error.save(flush:true)
		} finally {

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")

			resultado.setDate(afiEscaUnderwrittingCaseManagementRequest.request_Data.date)
		}

		return resultado
	}
}
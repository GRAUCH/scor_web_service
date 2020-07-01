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
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementResponse
import com.ws.enumeration.StatusType

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/alptis")
@SchemaValidation
@GrailsCxfEndpoint(address='/alptis/AlptisUnderwrittingCaseManagement',
excludes=['requestService', 'estadisticasService','RequestService','getRequestService','setRequestService'],
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class AlptisUnderwrittingCaseManagementService {

	def requestService
	def estadisticasService
	def expedienteService
	def logginService
	def tarificadorService

	@WebResult(name = "AlptisUnderwrittingCaseManagementResponse")
	AlptisUnderwrittingCaseManagementResponse AlptisUnderwrittingCaseManagement(
			@WebParam(partName = "AlptisUnderwrittingCaseManagementRequest",name = "AlptisUnderwrittingCaseManagementRequest")
			AlptisUnderwrittingCaseManagementRequest alptisUnderwrittingCaseManagementRequest) {

		def opername="AlptisUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def requestBBDD
		def company = Company.findByNombre('alptis')
		def respuestaCrm
		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		AlptisUnderwrittingCaseManagementResponse resultado = new AlptisUnderwrittingCaseManagementResponse()

		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Alptis para solicitud: " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)
			def attr = RequestContextHolder.getRequestAttributes()

			if (alptisUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){

				if (operacion && operacion.activo){

					requestXML=requestService.marshall(alptisUnderwrittingCaseManagementRequest,AlptisUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear(opername,requestXML)

					resultado.setStatusType(StatusType.ok)
					resultado.setComments("ok mensaje")

					if (Company.findByNombre("alptis").generationAutomatic) {

						logginService.putInfoMessage("Se procede el alta automatica de Alptis con numero de solicitud " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number)
						expedienteService.crearExpediente(requestBBDD, TipoCompany.ALPTIS)
						requestService.insertarRecibido(company, alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number, requestBBDD.request, TipoOperacion.ALTA)

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *
						 */
						correoUtil.envioEmail(opername, null, 1)
						tarificadorService.busquedaAlptisCrm(alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number, company.ou, opername, company.codigoSt, company.id, requestBBDD)
						
					}
				} else {

					resultado.setStatusType(StatusType.error)
					resultado.setComments("L'op�ration est temporairement d�sactiv�e.")
					logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number)
					correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				}
			} else {

				Calendar fecha = Calendar.getInstance()
				fecha.setTime(new Date())

				def fechaIni = fecha.getTime().format ('yyyyMMdd HH:mm')
				def msg = "Une annulation est requise pour le dossier " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number.toString() + " en date du : " + fechaIni + " par la soci�t� Alptis."

				correoUtil.envioEmailNoTratados("AlptisUnderwrittingCaseManagementRequest",msg)
			}
		}catch (Exception e){

			logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number.toString() + ". - Error: "+e)
			correoUtil.envioEmailErrores(opername," Peticion no realizada para solicitud "+ alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number.toString(),e)
			resultado.setStatusType(StatusType.error)
			resultado.setComments(opername+": "+e.getMessage())
			requestService.insertarError(company, alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number, requestBBDD.request, TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + alptisUnderwrittingCaseManagementRequest.policy.BasicPolicyGroup.policy_number + ". Error: "+e.getMessage())

		} finally {

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")

			resultado.setDate(alptisUnderwrittingCaseManagementRequest.request_Data.date)
		}

		return resultado
	}
}
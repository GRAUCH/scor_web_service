package services

import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import hwsol.webservices.CorreoUtil

import java.text.SimpleDateFormat

import javax.jws.WebMethod
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
import com.ws.enumeration.StatusType
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementResponse
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.RequestService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/lifesquare")
@SchemaValidation
@GrailsCxfEndpoint(address='/lifesquare/LifesquareUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class LifesquareUnderwrittingCaseManagementService {
	
	def estadisticasService
	def requestService
	def logginService
	def expedienteService

	@WebResult(name = "LifesquareUnderwrittingCaseManagementResponse")
	@WebMethod
	LifesquareUnderwrittingCaseManagementResponse LifesquareUnderwrittingCaseManagement(
			@WebParam(partName = "LifesquareUnderwrittingCaseManagementRequest",name = "LifesquareUnderwrittingCaseManagementRequest")
			LifesquareUnderwrittingCaseManagementRequest lifesquareUnderwrittingCaseManagementRequest) {

		def opername = "LifesquareUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD
		def company = Company.findByNombre(TipoCompany.ZEN_UP.getNombre())

		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		LifesquareUnderwrittingCaseManagementResponse resultado = new LifesquareUnderwrittingCaseManagementResponse()

		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Lifesqueare para solicitud: " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (lifesquareUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){

				if (operacion && operacion.activo){

					requestXML = requestService.marshall(lifesquareUnderwrittingCaseManagementRequest,LifesquareUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear(opername,requestXML)

					resultado.setStatusType(StatusType.ok)
					resultado.setComments("ok mensaje")

					if (company.generationAutomatic) {

						logginService.putInfoMessage("Se procede el alta automatica de Lifesquare con numero de solicitud " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number)
						expedienteService.crearExpediente(requestBBDD, TipoCompany.ZEN_UP)
						requestService.insertarRecibido(company,lifesquareUnderwrittingCaseManagementRequest.policy.policy_number,requestBBDD.request, TipoOperacion.ALTA)

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *
						 */
						correoUtil.envioEmail(opername, null, 1)
						expedienteService.busquedaCrm(requestBBDD, company, null, null, lifesquareUnderwrittingCaseManagementRequest.policy.policy_number)
						
					}
					
				} else {
					resultado.setStatusType(StatusType.error)
					resultado.setComments("La operacion esta desactivada temporalmente.")
					logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number)
					correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				}
			} else {

				def msg = "Une annulation est requise pour le dossier " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number.toString()
				correoUtil.envioEmailNoTratados("LifesquareUnderwrittingCaseManagementRequest",msg)
			}
			
		}catch (Exception e){
			
			logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number.toString() + ". - Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para solicitud: " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number.toString(),e)
			resultado.setStatusType(StatusType.error)
			resultado.setComments("Error en "+opername+ ": "+e.getMessage())
			requestService.insertarError(company, lifesquareUnderwrittingCaseManagementRequest.policy.policy_number.toString(), requestBBDD.request, TipoOperacion.ALTA,
										  "Peticion no realizada para solicitud: " + lifesquareUnderwrittingCaseManagementRequest.policy.policy_number.toString() + "- Error: "+e.getMessage())

		} finally {

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")

			resultado.setDate(lifesquareUnderwrittingCaseManagementRequest.request_Data.date)
		}

		return resultado
	}

}
package services

import com.scortelemed.Request
import com.scortelemed.TipoCompany
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty

import grails.util.Environment

import com.ws.lifesquare.beans.LifesquareUnderwrittingCasesResultsRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCasesResultsResponse
import com.ws.lifesquare.beans.TuwCase
import com.scortelemed.Company

import hwsol.webservices.CorreoUtil

import java.text.SimpleDateFormat

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

import org.apache.cxf.annotations.SchemaValidation
import org.springframework.web.context.request.RequestContextHolder

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/lifesquare")
@SchemaValidation
@GrailsCxfEndpoint(address='/lifesquare/LifesquareUnderwrittingCasesResults',
	excludes=['requestService', 'estadisticasService','RequestService','getRequestService','setRequestService'], 
	expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class LifesquareUnderwrittingCasesResultsService {
	
	def requestService
	def expedienteService
	def estadisticasService
	def tarificadorService
	def logginService
	
	@WebResult(name = "LifesquareUnderwrittingCasesResultsResponse")
	@WebMethod
	LifesquareUnderwrittingCasesResultsResponse LifesquareUnderwrittingCasesResults(
		@WebParam(partName = "LifesquareUnderwrittingCasesResultsRequest", name = "LifesquareUnderwrittingCasesResultsRequest") 
		LifesquareUnderwrittingCasesResultsRequest LifesquareUnderwrittingCasesResultsRequest) {
		
		def opername = "LifesquareUnderwrittingCasesResultsRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD
		def expedientes
		def company = Company.findByNombre(TipoCompany.ZEN_UP.getNombre())
		
		LifesquareUnderwrittingCasesResultsResponse result=new LifesquareUnderwrittingCasesResultsResponse()
		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion para fecha: " + LifesquareUnderwrittingCasesResultsRequest.date)
		
		try{
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)
			
			if(operacion && operacion.activo && LifesquareUnderwrittingCasesResultsRequest && LifesquareUnderwrittingCasesResultsRequest.date){
				
				requestXML=requestService.marshall(LifesquareUnderwrittingCasesResultsRequest,LifesquareUnderwrittingCasesResultsRequest.class)
				requestBBDD = requestService.crear(opername,requestXML)
				
				//PARSEAMOS LA FECHA
				def fechaHora = new SimpleDateFormat("yyyyMMdd HH:mm")
				String convertido = fechaHora.format(LifesquareUnderwrittingCasesResultsRequest.date)
				def fechaFin = convertido
				fechaFin= fechaFin.toString()+":00"
				Calendar fecha = Calendar.getInstance()
				fecha.setTime(LifesquareUnderwrittingCasesResultsRequest.date)
				fecha.add(Calendar.MINUTE , -120)
				def fechaIni = fecha.getTime().format ('yyyyMMdd HH:mm')
				fechaIni= fechaIni.toString()+":00"

				expedientes=expedienteService.obtenerInformeExpedientes(company.codigoSt,null,1,fechaIni,fechaFin,company.ou)
				
				StringBuilder sbInfo = new StringBuilder ("Realizando proceso envio de informacion para " + company.nombre + " con fecha ")
				sbInfo.append(fechaIni).append("-").append(fechaIni)
				logginService.putInfoMessage(sbInfo.toString())
				
				//SI EXISTEN EXPEDIENTES EMPEZAMOS A RELLANAR TUWCASE
				if(expedientes){
					def listTuwCases=[]
					expedientes.each { item ->
						def tuwCases=new TuwCase() 
						tuwCases.policy_number=item.numSolicitud
						tuwCases.reference_number=item.numPoliza
						tuwCases.zip=tarificadorService.obtenerZip(item.nodoAlfresco)
						listTuwCases.add(tuwCases)
					}
				
					result.setTuwCase(listTuwCases)
					result.setComments("ok")
					
					
					listTuwCases.each { caso ->
						String identificador = caso.policy_number!=null?caso.policy_number:caso.reference_number
						requestService.insertarEnvio(company, identificador, requestXML.toString())
						logginService.putInfoMessage("Informacion expediente " + identificador + " enviado a " + company.nombre + " correctamente")
						
					}
					
					
				}else{				
					result.setComments("There are no closed dossiers for date indicated.")
					logginService.putInfoMessage("No hay resultados para " + company.nombre)
				}
				 				 
				logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente para fecha: " + LifesquareUnderwrittingCasesResultsRequest.date)
				
				//ENVIAMOS UN CORREO PARA INFORMAR
				correoUtil.envioEmail(opername, null, 1)
			} else {
				result.setComments("La operacion esta desactivada temporalmente.")
				logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion no realizada para fecha: " + LifesquareUnderwrittingCasesResultsRequest.date)
			}		
		}catch (Exception e){
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para fecha: " + LifesquareUnderwrittingCasesResultsRequest.date + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para fecha: " + LifesquareUnderwrittingCasesResultsRequest.date,e)
			result.setComments("Error en LifesquareUnderwrittingCasesResultsRequest: "+e.getMessage())
				
		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		
			result.setDateRequested(LifesquareUnderwrittingCasesResultsRequest.date)
		}
		
		return result
	}
}
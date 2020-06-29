package services

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import grails.util.Environment

import com.scortelemed.Company
import com.scortelemed.Envio
import com.ws.afiesca.beans.AfiEscaUnderwrittingCasesResultsRequest
import com.ws.afiesca.beans.AfiEscaUnderwrittingCasesResultsResponse
import com.ws.afiesca.beans.TuwCase
import com.ws.enumeration.StatusType;

import hwsol.webservices.CorreoUtil
import java.text.SimpleDateFormat
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

import org.apache.cxf.annotations.SchemaValidation
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import servicios.Expediente

import javax.xml.bind.DatatypeConverter

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/afiEsca")
@SchemaValidation
@GrailsCxfEndpoint(address='/afiesca/AfiEscaUnderwrittingCasesResults',
	excludes=['requestService', 'estadisticasService','RequestService','getRequestService','setRequestService'], 
	expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class AfiEscaUnderwrittingCasesResultsService {
	
	def requestService
	def estadisticasService
	def tarificadorService
	def logginService
	
	@WebResult(name = "AfiEscaUnderwrittingCasesResultsResponse")
	AfiEscaUnderwrittingCasesResultsResponse AfiEscaUnderwrittingCasesResults(
		@WebParam(partName = "AfiEscaUnderwrittingCasesResultsRequest", name = "AfiEscaUnderwrittingCasesResultsRequest") 
		AfiEscaUnderwrittingCasesResultsRequest afiEscaUnderwrittingCasesResultsRequest) {
		
		def opername="AfiEscaUnderwrittingCasesResultsRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def expedientes
		def requestBBDD
		def company = Company.findByNombre('afiesca')
		
		AfiEscaUnderwrittingCasesResultsResponse result=new AfiEscaUnderwrittingCasesResultsResponse()
	
		try{
			
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)
			
			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + afiEscaUnderwrittingCasesResultsRequest.date.toString())
			
			if(operacion && operacion.activo && afiEscaUnderwrittingCasesResultsRequest && afiEscaUnderwrittingCasesResultsRequest.date){
				requestXML=requestService.marshall(afiEscaUnderwrittingCasesResultsRequest,AfiEscaUnderwrittingCasesResultsRequest.class)
				requestBBDD = requestService.crear(opername,requestXML)
				
				//PARSEAMOS LA FECHA
				def fechaHora = new SimpleDateFormat("yyyyMMdd HH:mm");
				String convertido = fechaHora.format(afiEscaUnderwrittingCasesResultsRequest.date);
				def fechaFin = convertido
				fechaFin= fechaFin.toString()+":00"
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(afiEscaUnderwrittingCasesResultsRequest.date)
				fecha.add(Calendar.MINUTE , -120)
				def fechaIni = fecha.getTime().format ('yyyyMMdd HH:mm')
				fechaIni= fechaIni.toString()+":00"
					
				if (Environment.current.name.equals("production_wildfly")) {
					expedientes=tarificadorService.obtenerInformeExpedientes("1035",null,1,fechaIni,fechaFin,"FR")
				} else {
					expedientes=tarificadorService.obtenerInformeExpedientes("1048",null,1,fechaIni,fechaFin,"FR") 
				}
				
				/**Metemos en enviados
				 *
				 */
				Envio envio = new Envio()
				envio.setFecha(new Date())
				envio.setCia(company.id.toString())
				envio.setIdentificador(afiEscaUnderwrittingCasesResultsRequest.date.toString())
				envio.setInfo(requestXML.toString())
				envio.save(flush:true)
				
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
						envio = new Envio()
						envio.setFecha(new Date())
						envio.setCia(company.id.toString())
						envio.setIdentificador(caso.policy_number!=null?caso.policy_number:caso.reference_number)
						envio.setInfo("")
						envio.save(flush:true)
						
						logginService.putInfoMessage("Informacion expediente " + envio.getIdentificador() + " enviado a " + company.nombre + " correctamente")
						
					}
					
					
				}else{
				
					result.setComments("There are no closed dossiers for date indicated.")
					logginService.putInfoMessage("No hay resultados para " + company.nombre)
				}
				 				 
				logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente para fecha: " + afiEscaUnderwrittingCasesResultsRequest.date)
				correoUtil.envioEmail(opername, null, 1)
				
			} else {
			
				result.setComments("La operacion esta desactivada temporalmente.")
				logginService.putInfoEndpoint("La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}		
		}catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para fecha: " + afiEscaUnderwrittingCasesResultsRequest.date + ". Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para fecha: " + afiEscaUnderwrittingCasesResultsRequest.date,e)
			result.setComments("Error en AfiEscaUnderwrittingCasesResultsRequest: "+e.getMessage())
			
			
			/**Metemos en errores
			 *
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(afiEscaUnderwrittingCasesResultsRequest.date.toString())
			error.setInfo(requestXML.toString())
			error.setOperacion("CONSULTA")
			error.setError(e.getMessage())
			error.save(flush:true)
				
		}finally{
			
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		
			result.setDateRequested(afiEscaUnderwrittingCasesResultsRequest.date)
		}
		
		return result
	}
}
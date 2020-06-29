package services

import com.scortelemed.TipoCompany
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil

import java.text.SimpleDateFormat

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
import servicios.Filtro

import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Operacion
import com.scortelemed.Recibido
import com.scortelemed.schemas.netinsurance.StatusType
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCasesResultsResponse
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.SimplefrService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/simplefr")
@SchemaValidation
@GrailsCxfEndpoint(address='/simplefr/SimplefrUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class SimplefrUnderwrittingCaseManagementService	 {

	def expedienteService
	@Autowired
	private SimplefrService simplefrService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "CaseManagementResponse")
	SimplefrUnderwrittingCaseManagementResponse simpleUnderwrittingCaseManagement(
			@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			SimplefrUnderwrittingCaseManagementRequest simplefrUnderwrittingCaseManagement) {

		def opername="SimpleUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD
		def respuestaCrm
		
		Company company = Company.findByNombre("simplefr")
		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		SimplefrUnderwrittingCaseManagementResponse resultado =new SimplefrUnderwrittingCaseManagementResponse()
		
		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Simplefr para solicitud: " + simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber)

		try{
			
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){
				requestXML=simplefrService.marshall(simplefrUnderwrittingCaseManagement)
				requestBBDD = requestService.crear(opername,requestXML)

				expedienteService.crearExpediente(requestBBDD, TipoCompany.MALAKOFF_MEDERIC)
				resultado.setMessage("The case has been successfully processed")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.OK)
				
				/**Metemos en recibidos
				 *
				 */
				Recibido recibido = new Recibido()
				recibido.setFecha(new Date())
				recibido.setCia(company.id.toString())
				recibido.setIdentificador(simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber)
				recibido.setInfo(requestBBDD.request)
				recibido.setOperacion("ALTA")
				recibido.save(flush:true)
				
				/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
				 *
				 */
				simplefrService.busquedaCrm(simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber, company.ou, opername, company.codigoSt, company.id, requestBBDD)
				
			} else {	
				
				resultado.setStatus(StatusType.ERROR)
				resultado.setMessage("The operation is temporarily disabled")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber)
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				
			}
		} catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para solicitud: " + simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para solicitud: " + simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber,e)
			resultado.setMessage("Error: " + e.printStackTrace())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			
			/**Metemos en errores
			 *
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber)
			error.setInfo(requestBBDD.request)
			error.setOperacion("ALTA")
			error.setError("Peticion no realizada para solicitud: " + simplefrUnderwrittingCaseManagement.candidateInformation.requestNumber + ". Error: "+e.getMessage())
			error.save(flush:true)
			
		}finally{
			
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}

	@WebResult(name = "CaseManagementResultsResponse")
	SimplefrUnderwrittingCasesResultsResponse simplefrUnderwrittingCasesResults(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
			SimplefrUnderwrittingCasesResultsRequest simpleUnderwrittingCasesResults) {

		def opername="SimplefrUnderwrittingCaseManagementResponse"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def expedientes
		def company = Company.findByNombre('simplefr')
		def estadisticasService = new EstadisticasService()
		def requestService = new RequestService()
		def tarificadorService = new TarificadorService()
		def logginService = new LogginService()
		def requestBBDD
		
		TransformacionUtil util = new TransformacionUtil()
		
		SimplefrUnderwrittingCasesResultsResponse resultado =new SimplefrUnderwrittingCasesResultsResponse()

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString())
			
			if(operacion && operacion.activo && simplefrUnderwrittingCasesResults && simpleUnderwrittingCasesResults.dateStart && simpleUnderwrittingCasesResults.dateEnd){

				requestXML=simplefrService.marshall(simplefrUnderwrittingCasesResults)
				requestBBDD=requestService.crear(opername,requestXML)

				Date date = simpleUnderwrittingCasesResults.dateStart.toGregorianCalendar().getTime()
				SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				String fechaIni = sdfr.format(date);
				date = simpleUnderwrittingCasesResults.dateEnd.toGregorianCalendar().getTime()
				String fechaFin = sdfr.format(date);
				
				/**Metemos en enviados
				 *
				 */
				Envio envio = new Envio()
				envio.setFecha(new Date())
				envio.setCia(company.id.toString())
				envio.setIdentificador(simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString())
				envio.setInfo(requestXML.toString())
				envio.save(flush:true)

				for (int i = 1; i < 3; i++){
					if (Environment.current.name.equals("production_wildfly")) {
						expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1060",null,i,fechaIni,fechaFin,"IT"))
					} else {
						expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1060",null,i,fechaIni,fechaFin,"IT"))
					}
				}

				if(expedientes){

					expedientes.each { expedientePoliza ->

						resultado.getResults().add(simplefrService.rellenaDatosSalida(expedientePoliza, simpleUnderwrittingCasesResults.dateStart, logginService))
					}

					resultado.setNotes("Risultati restituiti")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.OK)
				}else{
					resultado.setNotes("Nessun risultato per le date indicate")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.OK)
					logginService.putInfoMessage("No hay resultados para " + company.nombre)
				}

				logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente para Net Insurance con fecha: " + simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString())
				
			} else {
			
				resultado.setNotes("L'operazione "+ opername +" viene disabilitata temporaneamente")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				logginService.putInfoEndpoint("La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				
			}
			
		}catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion realizada para Simplefr con fecha: " + simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString() + "- Error: " + e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion realizada para caser con fecha: " + simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString(),e)
			resultado.setNotes("Errore nel " + opername + ": " + e.getMessage())
			resultado.setDate(simpleUnderwrittingCasesResults.dateStart)
			resultado.setStatus(StatusType.ERROR)
			
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(simpleUnderwrittingCasesResults.dateStart.toString() + "-" + simpleUnderwrittingCasesResults.dateEnd.toString())
			error.setInfo(requestXML.toString())
			error.setOperacion("CONSULTA")
			error.setError(e.getMessage())
			error.save(flush:true)
			
		}finally{
			
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}
}
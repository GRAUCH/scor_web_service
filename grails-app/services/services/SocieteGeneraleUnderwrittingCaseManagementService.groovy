package services

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
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCasesResultsResponse
import com.scortelemed.schemas.societegenerale.StatusType
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.RequestService
import com.ws.servicios.SocieteGeneraleService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/societeGenerale")
@SchemaValidation
@GrailsCxfEndpoint(address='/societeGenerale/SocieteGeneraleUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class SocieteGeneraleUnderwrittingCaseManagementService	 {
	
	@Autowired
	private SocieteGeneraleService societeGeneraleService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "CaseManagementResponse")
	SocieteGeneraleUnderwrittingCaseManagementResponse societeGeneraleUnderwrittingCaseManagement(
			@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			SocieteGeneraleUnderwrittingCaseManagementRequest societeGeneraleUnderwrittingCaseManagement) {

		def opername="SocieteGeneraleUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD
		def respuestaCrm
		
		Company company = Company.findByNombre("societeGenerale")
		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		SocieteGeneraleUnderwrittingCaseManagementResponse resultado =new SocieteGeneraleUnderwrittingCaseManagementResponse()
		
		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Simplefr para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber)

		try{
			
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){
				requestXML=societeGeneraleService.marshall("http://www.scortelemed.com/schemas/netinsurance",societeGeneraleUnderwrittingCaseManagement)
				requestBBDD = requestService.crear(opername,requestXML)
				requestBBDD.fecha_procesado = new Date()
				requestBBDD.save(flush:true)
				societeGeneraleService.crearExpediente(requestBBDD)
				resultado.setMessage("The case has been successfully processed")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.OK)
				
				/**Metemos en recibidos
				 *
				 */
				Recibido recibido = new Recibido()
				recibido.setFecha(new Date())
				recibido.setCia(company.id.toString())
				recibido.setIdentificador(societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber)
				recibido.setInfo(requestBBDD.request)
				recibido.setOperacion("ALTA")
				recibido.save(flush:true)
				
				/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
				 *
				 */
				societeGeneraleService.busquedaCrm(societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber, company.ou, opername, company.codigoSt, company.id, requestBBDD)
				
			} else {	
				
				resultado.setStatus(StatusType.ERROR)
				resultado.setMessage("The operation is temporarily disabled")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber)
				correoUtil.envioEmailErrores("ERROR en alta de SocieteGenerale","Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				
			}
		} catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores("ERROR en alta de SocieteGenerale","Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber,e)
			resultado.setMessage("Error: " + e.printStackTrace())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			
			/**Metemos en errores
			 *
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber)
			error.setInfo(requestBBDD.request)
			error.setOperacion("ALTA")
			error.setError("Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber + ". Error: "+e.getMessage())
			error.save(flush:true)
			
		}finally{
			
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}

	@WebResult(name = "CaseManagementResultsResponse")
	SocieteGeneraleUnderwrittingCasesResultsResponse societeGeneraleUnderwrittingCasesResults(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
			SocieteGeneraleUnderwrittingCasesResultsRequest societeGeneraleUnderwrittingCasesResults) {

		def opername="SocieteGeneraleUnderwrittingCaseManagementResponse"
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
		
		SocieteGeneraleUnderwrittingCasesResultsResponse resultado =new SocieteGeneraleUnderwrittingCasesResultsResponse()

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString())
			
			if(operacion && operacion.activo && societeGeneraleUnderwrittingCasesResults && societeGeneraleUnderwrittingCasesResults.dateStart && societeGeneraleUnderwrittingCasesResults.dateEnd){

				requestXML=societeGeneraleService.marshall("http://www.scortelemed.com/schemas/societeGenerale",societeGeneraleUnderwrittingCasesResults)

				requestService.crear(opername,requestXML)

				Date date = societeGeneraleUnderwrittingCasesResults.dateStart.toGregorianCalendar().getTime()
				SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
				String fechaIni = sdfr.format(date);
				date = societeGeneraleUnderwrittingCasesResults.dateEnd.toGregorianCalendar().getTime()
				String fechaFin = sdfr.format(date);
				
				/**Metemos en enviados
				 *
				 */
				Envio envio = new Envio()
				envio.setFecha(new Date())
				envio.setCia(company.id.toString())
				envio.setIdentificador(societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString())
				envio.setInfo(requestXML.toString())
				envio.save(flush:true)

				for (int i = 1; i < 3; i++){
					if (Environment.current.name.equals("production_wildfly")) {
						expedientes.addAll(tarificadorService.obtenerInformeExpedientes("xxxx",null,i,fechaIni,fechaFin,"FR"))
					} else {
						expedientes.addAll(tarificadorService.obtenerInformeExpedientes("xxxx",null,i,fechaIni,fechaFin,"FR"))
					}
				}

				if(expedientes){

					expedientes.each { expedientePoliza ->

						resultado.getResults().add(societeGeneraleService.rellenaDatosSalida(expedientePoliza, societeGeneraleUnderwrittingCasesResults.dateStart, logginService))
					}

					resultado.setNotes("Results returned")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.OK)
				}else{
					resultado.setNotes("No results for the indicated dates")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.OK)
					logginService.putInfoMessage("No hay resultados para " + company.nombre)
				}

				logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente para Net Insurance con fecha: " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString())
				
			} else {
			
				resultado.setNotes("The "+ opername +" operation is temporarily disabled")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				logginService.putInfoEndpoint("La operacion " + opername + " esta desactivada temporalmente")
				
			}
			
		}catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion realizada para Simplefr con fecha: " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString() + "- Error: " + e.getMessage())
			resultado.setNotes("Errore nel " + opername + ": " + e.getMessage())
			resultado.setDate(societeGeneraleUnderwrittingCasesResults.dateStart)
			resultado.setStatus(StatusType.ERROR)
			
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString())
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
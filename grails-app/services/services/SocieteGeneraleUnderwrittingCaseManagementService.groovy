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
import com.scortelemed.Operacion
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCasesResultsResponse
import com.scortelemed.schemas.societegenerale.StatusType
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.SocieteGeneraleService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/societeGenerale")
@SchemaValidation
@GrailsCxfEndpoint(address='/societeGenerale/SocieteGeneraleUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class SocieteGeneraleUnderwrittingCaseManagementService	 {

	def requestService
	def expedienteService
	def societeGeneraleService
	def estadisticasService
	def logginService
	def tarificadorService

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
				requestXML=societeGeneraleService.marshall(societeGeneraleUnderwrittingCaseManagement)
				requestBBDD = requestService.crear(opername,requestXML)

				expedienteService.crearExpediente(requestBBDD, TipoCompany.SOCIETE_GENERALE)
				resultado.setMessage("The case has been successfully processed")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.OK)
				requestService.insertarRecibido(company, societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber, requestBBDD.request, "ALTA")

				/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
				 *
				 */
				societeGeneraleService.busquedaCrm(societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber, company.ou, opername, company.codigoSt, company.id, requestBBDD)
				
			} else {	
				
				resultado.setStatus(StatusType.ERROR)
				resultado.setMessage("The operation is temporarily disabled")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				logginService.putInfoMessage("Peticion " + opername + " no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber)
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				
			}
		} catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber,e)
			resultado.setMessage("Error: " + e.printStackTrace())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			requestService.insertarError(company, societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber, requestBBDD.request, "ALTA", "Peticion no realizada para solicitud: " + societeGeneraleUnderwrittingCaseManagement.candidateInformation.requestNumber + ". Error: "+e.getMessage())

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
		def tarificadorService = new TarificadorService()
		def logginService = new LogginService()
		def requestBBDD
		
		TransformacionUtil util = new TransformacionUtil()
		
		SocieteGeneraleUnderwrittingCasesResultsResponse resultado =new SocieteGeneraleUnderwrittingCasesResultsResponse()

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString())
			
			if(operacion && operacion.activo && societeGeneraleUnderwrittingCasesResults && societeGeneraleUnderwrittingCasesResults.dateStart && societeGeneraleUnderwrittingCasesResults.dateEnd){

				requestXML=societeGeneraleService.marshall(societeGeneraleUnderwrittingCasesResults)
				requestBBDD=requestService.crear(opername,requestXML)

				Date date = societeGeneraleUnderwrittingCasesResults.dateStart.toGregorianCalendar().getTime()
				SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
				String fechaIni = sdfr.format(date)
				date = societeGeneraleUnderwrittingCasesResults.dateEnd.toGregorianCalendar().getTime()
				String fechaFin = sdfr.format(date)
				requestService.insertarEnvio(company, societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString(), requestXML.toString())

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
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
				
			}
			
		}catch (Exception e){
		
			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion realizada para Simplefr con fecha: " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString() + "- Error: " + e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion realizada para caser con fecha: " + societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString(),e)
			resultado.setNotes("Errore nel " + opername + ": " + e.getMessage())
			resultado.setDate(societeGeneraleUnderwrittingCasesResults.dateStart)
			resultado.setStatus(StatusType.ERROR)
			requestService.insertarError(company, societeGeneraleUnderwrittingCasesResults.dateStart.toString() + "-" + societeGeneraleUnderwrittingCasesResults.dateEnd.toString(), requestXML.toString(), "CONSULTA", e.getMessage())

		}finally{
			
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}
}
package services

import com.scortelemed.TipoCompany
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil

import java.text.SimpleDateFormat

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder

import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM

import com.scortelemed.Company
import com.scortelemed.Recibido
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementResponse
import com.ws.cajamar.beans.ConsolidacionPolizaRequest
import com.ws.cajamar.beans.ConsolidacionPolizaResponse
import com.ws.cajamar.beans.StatusType
import com.ws.servicios.CajamarService
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/cajamar")
//@SchemaValidation
@org.apache.cxf.annotations.SchemaValidation(enabled = true)
@GrailsCxfEndpoint(address='/cajamar/CajamarUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,
properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"),
	@GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false"),
	@GrailsCxfEndpointProperty(name = "set-jaxb-validation-event-handler", value = "false")
])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class CajamarUnderwrittingCaseManagementService {

	def expedienteService

	@Autowired
	private CajamarService cajamarService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "CajamarUnderwrittingCaseManagementResponse")
	@WebMethod
	CajamarUnderwrittingCaseManagementResponse CajamarUnderwrittingCaseManagement(
			@WebParam(partName = "CajamarUnderwrittingCaseManagementRequest",name = "CajamarUnderwrittingCaseManagementRequest")
			CajamarUnderwrittingCaseManagementRequest cajamarUnderwrittingCaseManagementRequest) {


		CajamarUnderwrittingCaseManagementResponse resultado = new CajamarUnderwrittingCaseManagementResponse()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		Filtro filtro = new Filtro()

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")

		def opername = "CajamarUnderwrittingCaseManagementRequest"
		def requestXML = ""
		def requestBBDD
		def tarificadorService
		def respuestaCrm

		def company = Company.findByNombre('cajamar')


		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Cajamar para solicitud: " + cajamarUnderwrittingCaseManagementRequest.regScor.numref)

		try {

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (Company.findByNombre("cajamar").generationAutomatic && cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("1")) {

					requestXML=cajamarService.marshall("http://www.scortelemed.com/schemas/cajamar",cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					requestBBDD.fecha_procesado = new Date()
					requestBBDD.save(flush:true)
					expedienteService.crearExpediente(requestBBDD, TipoCompany.CAJAMAR)
					resultado.setComments("El caso se ha procesado correctamente")
					resultado.setStatus(StatusType.OK)
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))

					logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + cajamarUnderwrittingCaseManagementRequest.regScor.numref)


					/**Metemos en recibidos
					 *
					 */
					Recibido recibido = new Recibido()
					recibido.setFecha(new Date())
					recibido.setCia(company.id.toString())
					recibido.setIdentificador(cajamarUnderwrittingCaseManagementRequest.regScor.numref)
					recibido.setInfo(requestBBDD.request)
					recibido.setOperacion("ALTA")
					recibido.save(flush:true)

					/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
					 * 
					 */
					cajamarService.busquedaCrm(cajamarUnderwrittingCaseManagementRequest.regScor.numref, company.ou, opername, company.codigoSt, company.id, requestBBDD)

				}

				/**ANULACION
				 *
				 */
				if (cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("3")){

					def msg = "Ha llegado una anulaci�n de " + company.nombre + " con n�mero de referencia: " + cajamarUnderwrittingCaseManagementRequest.getRegScor().getNumref()

					requestXML=cajamarService.marshall("http://www.scortelemed.com/schemas/cajamar",cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)

					Recibido recibido = new Recibido()
					recibido.setFecha(new Date())
					recibido.setCia(company.id.toString())
					recibido.setIdentificador(cajamarUnderwrittingCaseManagementRequest.regScor.numref)
					recibido.setInfo(requestBBDD.request)
					recibido.setOperacion("ANULACION")
					recibido.save(flush:true)
					
					resultado.setComments("El caso se ha procesado correctamente")
					resultado.setStatus(StatusType.OK)
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))


					logginService.putInfoMessage("Ha llegado una anulaci�n de " + company.nombre + " con n�mero de referencia: " + cajamarUnderwrittingCaseManagementRequest.getRegScor().getNumref())

					correoUtil.envioEmailNoTratados("CajamarUnderwrittingCaseManagementRequest",msg)

				}

				/**MODIFICACION
				 *
				 */
				if (cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("2")){

					def msg = "Ha llegado una modificaci�n de " + company.nombre + " con n�mero de referencia: " + cajamarUnderwrittingCaseManagementRequest.getRegScor().getNumref()

					requestXML=cajamarService.marshall("http://www.scortelemed.com/schemas/cajamar",cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)

					Recibido recibido = new Recibido()
					recibido.setFecha(new Date())
					recibido.setCia(company.id.toString())
					recibido.setIdentificador(cajamarUnderwrittingCaseManagementRequest.regScor.numref)
					recibido.setInfo(requestBBDD.request)
					recibido.setOperacion("MODIFICACION")
					recibido.save(flush:true)
					
					resultado.setComments("El caso se ha procesado correctamente")
					resultado.setStatus(StatusType.OK)
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))


					logginService.putInfoMessage("Ha llegado una modificaci�n " + company.nombre + " con n�mero de referencia: " + cajamarUnderwrittingCaseManagementRequest.getRegScor().getNumref())

					correoUtil.envioEmailNoTratados("CajamarUnderwrittingCaseManagementRequest",msg)
				}


			} else {

				resultado.setComments("La operacion esta desactivada temporalmente")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				logginService.putInfoEndpoint("La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)

			}

		} catch (Exception e){

			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para numero de solicitud: " +cajamarUnderwrittingCaseManagementRequest.regScor.numref + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion no realizada para numero de solicitud: " +cajamarUnderwrittingCaseManagementRequest.regScor.numref,e)
			resultado.setComments("Error: " + e.printStackTrace())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)

			/**Metemos en errores
			 * 
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(cajamarUnderwrittingCaseManagementRequest.regScor.numref)
			error.setInfo(requestBBDD.request)
			error.setOperacion("ALTA")
			error.setError("Peticion no realizada para solicitud: " + cajamarUnderwrittingCaseManagementRequest.regScor.numref + "- Error: "+e.getMessage())
			error.save(flush:true)

		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")

		}

		return resultado
	}

	@WebResult(name = "ConsolidacionPolizaResponse")
	ConsolidacionPolizaResponse consolidacionPoliza(
			@WebParam(partName = "ConsolidacionPolizaRequest", name = "ConsolidacionPolizaRequest")
			ConsolidacionPolizaRequest consolidacionPoliza) {

		def opername="CajamarConsolidacionPolizaResponse"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD

		Company company = Company.findByNombre("cajamar")
		RespuestaCRM expediente = new RespuestaCRM();
		TransformacionUtil util = new TransformacionUtil()
		ConsolidacionPolizaResponse resultado=new ConsolidacionPolizaResponse()

		try{
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)


			logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de " + company.nombre + " para solicitud: " + consolidacionPoliza.requestNumber)

			if (operacion && operacion.activo){

				requestXML=cajamarService.marshall("http://www.scortelemed.com/schemas/cajamar",consolidacionPoliza)
				requestBBDD = requestService.crear(opername,requestXML)
				requestBBDD.fecha_procesado = new Date()
				requestBBDD.save(flush:true)

				/**Metemos en recibidos
				 *
				 */
				Recibido recibido = new Recibido()
				recibido.setFecha(new Date())
				recibido.setCia(consolidacionPoliza.ciaCode)
				recibido.setIdentificador(consolidacionPoliza.requestNumber)
				recibido.setInfo(requestXML.toString())
				recibido.setOperacion("CONSOLIDACION")
				recibido.save(flush:true)


				if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() != null && consolidacionPoliza.ciaCode !=null && !consolidacionPoliza.ciaCode.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()){

					expediente = expedienteService.consultaExpedienteNumSolicitud(consolidacionPoliza.requestNumber,"ES", consolidacionPoliza.ciaCode)

					if (expediente != null && expediente.getErrorCRM() == null && expediente.getListaExpedientes() != null && expediente.getListaExpedientes().size() > 0){

						Expediente eModificado = expediente.getListaExpedientes().get(0)
						eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

						RespuestaCRM respuestaCrmExpediente = tarificadorService.modificaExpediente("ES",eModificado,null,null)

						if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()){

							resultado.setMessage("Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle())
							resultado.setDate(util.fromDateToXmlCalendar(new Date()))
							resultado.setStatus(StatusType.ERROR)
							resultado.setCodigo(0)

							logginService.putInfoEndpoint("Endpoint-"+opername,"Error en la modificacion para NUM_SOLICITUD: " + consolidacionPoliza.requestNumber + ", ciaCode: " + consolidacionPoliza.ciaCode +". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())


							/**Metemos en errores
							 *
							 */
							com.scortelemed.Error error = new com.scortelemed.Error()
							error.setFecha(new Date())
							error.setCia(company.id.toString())
							error.setIdentificador(consolidacionPoliza.requestNumber)
							error.setInfo(requestXML.toString())
							error.setOperacion(opername)
							error.setError(respuestaCrmExpediente.getErrorCRM().getDetalle())
							error.save(flush:true)
						} else {

							resultado.setMessage("El caso se ha procesado correctamente")
							resultado.setDate(util.fromDateToXmlCalendar(new Date()))
							resultado.setStatus(StatusType.OK)
							
						}
					} else {

						resultado.setMessage("Poliza no encontrada")
						resultado.setDate(util.fromDateToXmlCalendar(new Date()))
						resultado.setStatus(StatusType.ERROR)
						resultado.setCodigo(1)
						logginService.putInfoEndpoint("Endpoint-"+opername,"No hay datos para la consulta de " + company.nombre + " requestNumber: " + consolidacionPoliza.requestNumber + ", ciaCode: " + consolidacionPoliza.ciaCode)
					}
				} else {

					resultado.setMessage("Datos obligatorios incompletos (ciaCode, requestNumber, policyNumber)")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.ERROR)
					resultado.setCodigo(2)
					logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion no realizada por falta de datos: ")
				}
			} else {

				resultado.setMessage("La operacion esta desactivada temporalmente")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				resultado.setCodigo(3)
				logginService.putInfoEndpoint("Endpoint-"+opername,"La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}
		} catch (Exception e){

			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion no realizada para NUM_SOLICITUD: " + consolidacionPoliza.requestNumber + "- Error: "+e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion no realizada para NUM_SOLICITUD: " + consolidacionPoliza.requestNumber,e)
			resultado.setMessage("Error: " + e.printStackTrace())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			resultado.setCodigo(4)
			/**Metemos en errores
			 *
			 */
			com.scortelemed.Error error = new com.scortelemed.Error()
			error.setFecha(new Date())
			error.setCia(company.id.toString())
			error.setIdentificador(consolidacionPoliza.requestNumber)
			error.setInfo(requestXML.toString())
			error.setOperacion(opername)
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
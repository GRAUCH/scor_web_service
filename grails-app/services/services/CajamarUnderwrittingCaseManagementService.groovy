package services

import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
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

import org.springframework.web.context.request.RequestContextHolder

import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM

import com.scortelemed.Company
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementResponse
import com.ws.cajamar.beans.ConsolidacionPolizaRequest
import com.ws.cajamar.beans.ConsolidacionPolizaResponse
import com.ws.cajamar.beans.StatusType
import com.ws.servicios.impl.companies.CajamarService
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
	def cajamarService
	def estadisticasService
	def requestService
	def logginService
	def tarificadorService

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
		Request requestBBDD

		def company = Company.findByNombre(TipoCompany.CAJAMAR.getNombre())


		logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de Cajamar para solicitud: " + cajamarUnderwrittingCaseManagementRequest.regScor.numref)

		try {

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (company.generationAutomatic && cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("1")) {

					requestXML=cajamarService.marshall(cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)

					expedienteService.crearExpediente(requestBBDD, TipoCompany.CAJAMAR)
					resultado.setComments("El caso se ha procesado correctamente")
					resultado.setStatus(StatusType.OK)
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))

					logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + cajamarUnderwrittingCaseManagementRequest.regScor.numref)
					requestService.insertarRecibido(company, cajamarUnderwrittingCaseManagementRequest.regScor.numref, requestBBDD.request, TipoOperacion.ALTA)

					/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
					expedienteService.busquedaCrm(requestBBDD, company, cajamarUnderwrittingCaseManagementRequest.regScor.numref, null, null)

				}

				/**ANULACION
				 *
				 */
				if (cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("3")){

					def msg = "Ha llegado una anulaci�n de " + company.nombre + " con n�mero de referencia: " + cajamarUnderwrittingCaseManagementRequest.getRegScor().getNumref()

					requestXML=cajamarService.marshall(cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					requestService.insertarRecibido(company, cajamarUnderwrittingCaseManagementRequest.regScor.numref, requestBBDD.request, TipoOperacion.ANULACION)
					
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

					requestXML=cajamarService.marshall(cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					requestService.insertarRecibido(company, cajamarUnderwrittingCaseManagementRequest.regScor.numref, requestBBDD.request, TipoOperacion.MODIFICACION)
					
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
			requestService.insertarError(company, cajamarUnderwrittingCaseManagementRequest.regScor.numref, requestBBDD.request, TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + cajamarUnderwrittingCaseManagementRequest.regScor.numref + "- Error: "+e.getMessage())

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
		Request requestBBDD

		Company company = Company.findByNombre(TipoCompany.CAJAMAR.getNombre())
		RespuestaCRM respuestaCRM = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		ConsolidacionPolizaResponse resultado=new ConsolidacionPolizaResponse()

		try{
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)


			logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion de " + company.nombre + " para solicitud: " + consolidacionPoliza.requestNumber)

			if (operacion && operacion.activo){

				requestXML=cajamarService.marshall(consolidacionPoliza)
				requestBBDD = requestService.crear(opername,requestXML)
				requestService.insertarRecibido(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION)

				if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() != null && consolidacionPoliza.ciaCode !=null && !consolidacionPoliza.ciaCode.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()){

					respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consolidacionPoliza.requestNumber,company.ou, consolidacionPoliza.ciaCode)

					if (respuestaCRM != null && respuestaCRM.getErrorCRM() == null && respuestaCRM.getListaExpedientes() != null && respuestaCRM.getListaExpedientes().size() > 0){

						Expediente eModificado = respuestaCRM.getListaExpedientes().get(0)
						eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

						RespuestaCRM respuestaCrmExpediente = expedienteService.modificaExpediente(company.ou,eModificado,null,null)

						if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()){

							resultado.setMessage("Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle())
							resultado.setDate(util.fromDateToXmlCalendar(new Date()))
							resultado.setStatus(StatusType.ERROR)
							resultado.setCodigo(0)

							logginService.putInfoEndpoint("Endpoint-"+opername,"Error en la modificacion para NUM_SOLICITUD: " + consolidacionPoliza.requestNumber + ", ciaCode: " + consolidacionPoliza.ciaCode +". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())
							requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION, respuestaCrmExpediente.getErrorCRM().getDetalle())

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
			requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION, e.getMessage())

		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}
}
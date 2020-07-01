package services

import com.scortelemed.Company
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.enginyers.*
import com.ws.servicios.impl.ExpedienteService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.web.context.request.RequestContextHolder

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/enginyers")
@SchemaValidation
@GrailsCxfEndpoint(address='/enginyers/EnginyersUnderwrittingCaseManagement', inInterceptors = ['interceptorEventos'],
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)

class EnginyersUnderwrittingCaseManagementService {

	def expedienteService = new ExpedienteService()
	def enginyersService
	def estadisticasService
	def requestService
	def logginService
	def tarificadorService

	@WebResult(name = "addExpResponse")
	@WebMethod(action = "http://www.scortelemed.com/schemas/enginyers/addExp")
	AddExpResponse addExp(@WebParam(partName = "addExp",name = "addExp")AddExp addExp) {

			def opername="EnginyersResultadoReconocimientoMedicoRequest"
			def correoUtil = new CorreoUtil()
			List<WsError> wsErrors = new ArrayList<WsError>()
			def requestXML
			def requestBBDD

			Company company = null
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
			TransformacionUtil util = new TransformacionUtil()
			AddExpResponse resultado=new AddExpResponse()

			ArrayOfFaultElement array = new ArrayOfFaultElement()
			FaultElement fault = new FaultElement()
			Expedient expedient = new Expedient()
			expedient.setIdExpedient(Integer.valueOf(addExp.getD().getPolicyNumber()))
			List<FaultElement> faultList = new ArrayList<FaultElement>()
			ErrorElement errorElement = new ErrorElement()

			try{

				company = Company.findByNombre('enginyers')

				def operacion = estadisticasService.obtenerObjetoOperacion(opername)

				logginService.putInfoEndpoint("Endpoint-"+opername," Peticion de " + company.nombre + " para solicitud: " + addExp.d.getPolicyNumber())

					if (operacion && operacion.activo) {

						if (company.generationAutomatic) {
							
							logginService.putInfoMessage("Realizando peticion AddExp para " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber())

							requestXML = enginyersService.marshall(addExp)
							requestBBDD = requestService.crear(opername, requestXML)
		
							wsErrors = enginyersService.validarDatosObligatorios(requestBBDD)
		
							if (wsErrors != null && wsErrors.size() == 0) {

								expedienteService.crearExpediente(requestBBDD, TipoCompany.ENGINYERS)

								expedient.setAddExpResultCode(0)
								fault.setFaultCode("0")
								fault.setFaultString("")

								requestService.insertarRecibido(company, addExp.d.getPolicyNumber(), requestXML.toString(), "ALTA")

								/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
								 *                                */
								enginyersService.busquedaCrm(addExp.d.getPolicyNumber(), company.ou, opername, company.codigoSt, company.id, requestBBDD, company.nombre)
							
							} else {

								String error = util.detalleError(wsErrors)

								expedient.setAddExpResultCode(-1)
								fault.setFaultCode("8")
								fault.setFaultString("Error de validacion: " + error)

								requestService.insertarError(company, addExp.d.getPolicyNumber(), requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + addExp.d.getPolicyNumber() + ". Error de validacion: " + error)
								logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber() + ". Error de validacion: " + error)

							}
						}
					} else {

						expedient.setAddExpResultCode(-1)
						fault.setFaultCode("1")
						fault.setFaultString("La operacion " + opername + " esta desactivada temporalmente")

						logginService.putInfoEndpoint("GestionReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
						correoUtil.envioEmail("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber() + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)

					}
			} catch (Exception e){

				requestService.insertarError(company,addExp.d.getPolicyNumber(), requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + addExp.d.getPolicyNumber() + ". Error: " + e.getMessage())

				logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber() + ". Error: " + e.getMessage())
				correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber(), e)

				expedient.setAddExpResultCode(-1)
				fault.setFaultCode("2")
				fault.setFaultString("Error: " + e.getMessage())

			}finally {

				def sesion = RequestContextHolder.currentRequestAttributes().getSession()
				sesion.removeAttribute("userEndPoint")
				sesion.removeAttribute("companyST")

			}

		logginService.putInfoMessage("Fin peticion de informacion de servicio GestionReconocimientoMedico para " + company.nombre)

		errorElement.setErrorMessage("")
		errorElement.setErrorNumber(0)
		errorElement.setErrorSource("")
		fault.setDetail(errorElement)
		fault.setFaultActor("")
		faultList.add(fault)
		array.getFaultElement().addAll(faultList)
		expedient.setFaultArray(array)
		resultado.setAddExpResult(expedient)

		return resultado
	}
				
}

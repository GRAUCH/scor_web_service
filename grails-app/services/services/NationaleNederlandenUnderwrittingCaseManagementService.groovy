package services

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.ws.servicios.*
import com.scortelemed.schemas.nn.*
import com.ws.servicios.impl.RequestService
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import servicios.*

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/nn")
@SchemaValidation
@GrailsCxfEndpoint(address='/nn/NnUnderwrittingCaseManagement',
		expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class NationaleNederlandenUnderwrittingCaseManagementService {

	@Autowired
	private NnService nnService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "GestionReconocimientoMedicoResponse")
	GestionReconocimientoMedicoResponse gestionReconocimientoMedico(
			@WebParam(partName = "GestionReconocimientoMedicoRequest",name = "GestionReconocimientoMedicoRequest")
					GestionReconocimientoMedicoRequest gestionReconocimientoMedico) {

		def opername="NnResultadoReconocimientoMedicoRequest"
		def correoUtil = new CorreoUtil()
		List<WsError> wsErrors = new ArrayList<WsError>()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD
		def respuestaCrm

		String message = null
		StatusType status = null
		int code = 0

		Company company = Company.findByNombre("Nn")
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		GestionReconocimientoMedicoResponse resultado=new GestionReconocimientoMedicoResponse()

		logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (Company.findByNombre("Nn").generationAutomatic) {

					logginService.putErrorEndpoint("GestionReconocimientoMedico","Realizando peticion " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber)

					requestXML=nnService.marshall("http://www.scortelemed.com/schemas/nn",gestionReconocimientoMedico)
					requestBBDD = requestService.crear(opername,requestXML)
					requestBBDD.fecha_procesado = new Date()
					requestBBDD.save(flush:true)

					wsErrors = nnService.validarDatosObligatorios(requestBBDD)

					if (wsErrors != null && wsErrors.size() == 0) {

						nnService.crearExpediente(requestBBDD)

						message = "El caso se ha procesado correctamente"
						status = StatusType.OK
						code = 0

						nnService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), "ALTA")

						/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
						 *
						 */
						nnService.busquedaCrm(gestionReconocimientoMedico.candidateInformation.requestNumber, company.ou, opername, company.codigoSt, company.id, requestBBDD, gestionReconocimientoMedico.candidateInformation.certificateNumber, company.nombre)

					} else {


						String error =  util.detalleError(wsErrors)

						message = "Error de validacion: " + error
						status = StatusType.ERROR
						code = 8

						nnService.insertarError(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error de validacion: " + error)
						logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error de validacion: " + error)

					}
				}
			} else {

				message = "La operacion " + opername + " esta desactivada temporalmente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente",0)
			}
		} catch (Exception e){

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			nnService.insertarError(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber, e)

		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(message)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)

		return resultado
	}

	@WebResult(name = "ResultadoReconocimientoMedicoResponse")
	ResultadoReconocimientoMedicoResponse resultadoReconocimientoMedico(
			@WebParam(partName = "ResultadoReconocimientoMedicoRequest", name = "ResultadoReconocimientoMedicoRequest")
					ResultadoReconocimientoMedicoRequest resultadoReconocimientoMedico) {

		ResultadoReconocimientoMedicoResponse resultado = new ResultadoReconocimientoMedicoResponse()

		def opername="NnResultadoReconocimientoMedicoResponse"
		def requestXML = ""
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		RespuestaCRM respuestaCRM = new RespuestaCRM()

		String message = null
		StatusType status = null
		int code = 0

		Company company = Company.findByNombre("nn")

		logginService.putInfoMessage("Realizando peticion de informacion de servicio ResultadoReconocimientoMedico para la cia " + company.nombre)

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo){

				if (resultadoReconocimientoMedico && resultadoReconocimientoMedico.dateStart && resultadoReconocimientoMedico.dateEnd) {

					requestXML=nnService.marshall("http://www.scortelemed.com/schemas/caser",resultadoReconocimientoMedico)

					requestService.crear(opername,requestXML)

					Date date = resultadoReconocimientoMedico.dateStart.toGregorianCalendar().getTime()
					SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					String fechaIni = sdfr.format(date);
					date = resultadoReconocimientoMedico.dateEnd.toGregorianCalendar().getTime()
					String fechaFin = sdfr.format(date);

					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Realizando peticion para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString())

					nnService.insertarEnvio (company, resultadoReconocimientoMedico.dateStart.toString().substring(0,10) + "-" + resultadoReconocimientoMedico.dateEnd.toString().substring(0,10), requestXML.toString())

					for (int i = 1; i < 3; i++){

						if (Environment.current.name.equals("production_wildfly")) {
							expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1061",null,i,fechaIni,fechaFin,"ES"))
						} else {
							expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1062",null,i,fechaIni,fechaFin,"ES"))
						}
					}

					if(expedientes){

						expedientes.each { expedientePoliza ->

							resultado.getExpediente().add(caserService.rellenaDatosSalida(expedientePoliza, resultadoReconocimientoMedico.dateStart, logginService))
						}

						message = "Resultados devueltos"
						status = StatusType.OK
						code = 3

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion realizada correctamente para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString())

					}else{

						message = "No hay resultados para el rango de fechas indicado"
						status = StatusType.OK
						code = 9

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No hay resultados para el rango de fechas indicado para " + company.nombre)
					}
				} else {

					message = "Datos obligatorios incompletos. Es necesario un rango de fechas"
					status = StatusType.ERROR
					code = 7
					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Datos obligatorios incompletos. Es necesario un rango de fechas")
				}
			} else {

				message = "La operacion " + opername + " esta desactivada temporalmente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ResultadoReconocimientoMedico","Peticion de " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString() + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString() + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString(), e)

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			nnService.insertarError(company,resultadoReconocimientoMedico.fechaIni.toString(), requestXML.toString(), "CONSULTA", "Peticion no realizada para solicitud: " + resultadoReconocimientoMedico.fechaIni.toString() + ". Error: " + e.getMessage())
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(message)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCode(code)

		return resultado
	}
}
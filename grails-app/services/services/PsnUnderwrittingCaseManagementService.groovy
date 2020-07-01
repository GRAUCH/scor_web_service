package services

import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError

import java.text.SimpleDateFormat
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty

import org.springframework.web.context.request.RequestContextHolder

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.schemas.psn.ConsolidacionPolizaRequest
import com.scortelemed.schemas.psn.ConsolidacionPolizaResponse
import com.scortelemed.schemas.psn.ConsultaDocumentoRequest
import com.scortelemed.schemas.psn.ConsultaDocumentoResponse
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest
import com.scortelemed.schemas.psn.ConsultaExpedienteResponse
import com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.psn.GestionReconocimientoMedicoResponse
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoResponse
import com.scortelemed.schemas.psn.StatusType
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.companies.PsnService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.TarificadorService

import servicios.Expediente
import servicios.Filtro
import servicios.ClaveFiltro
import servicios.RespuestaCRM
import servicios.RespuestaCRMInforme

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/psn")
@SchemaValidation
@GrailsCxfEndpoint(address='/psn/PsnUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class PsnUnderwrittingCaseManagementService	 {

	def expedienteService
	def psnService
	def estadisticasService
	def requestService
	def logginService
	def tarificadorService

	@WebResult(name = "GestionReconocimientoMedicoResponse")
	GestionReconocimientoMedicoResponse gestionReconocimientoMedico(
			@WebParam(partName = "GestionReconocimientoMedicoRequest",name = "GestionReconocimientoMedicoRequest")
			GestionReconocimientoMedicoRequest gestionReconocimientoMedico) {

		def opername="PsnResultadoReconocimientoMedicoRequest"
		def correoUtil = new CorreoUtil()
		List<WsError> wsErrors = new ArrayList<WsError>()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD
		def respuestaCrm

		String message = null
		StatusType status = null
		int code = 0

		Company company = Company.findByNombre(TipoCompany.PSN.getNombre())
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		GestionReconocimientoMedicoResponse resultado=new GestionReconocimientoMedicoResponse()

		logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo) {

				if (company.generationAutomatic) {

					logginService.putErrorEndpoint("GestionReconocimientoMedico", "Realizando peticion " + gestionReconocimientoMedico.getCandidateInformation().getOperation().toString() + " para " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber)

					requestXML = psnService.marshall(gestionReconocimientoMedico)
					requestBBDD = requestService.crear(opername, requestXML)

					wsErrors = psnService.validarDatosObligatorios(requestBBDD)

					if (wsErrors != null && wsErrors.size() == 0) {

						if (gestionReconocimientoMedico.getCandidateInformation().getOperation().toString().equals("A")) {


							expedienteService.crearExpediente(requestBBDD, TipoCompany.PSN)

							message = "El caso se ha procesado correctamente"
							status = StatusType.OK
							code = 0

							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)

							/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
							 *                                */
							psnService.busquedaCrm(gestionReconocimientoMedico.candidateInformation.requestNumber, company.ou, opername, company.codigoSt, company.id, requestBBDD, gestionReconocimientoMedico.candidateInformation.certificateNumber, company.nombre)


						}

						if (gestionReconocimientoMedico.getCandidateInformation().getOperation().toString().equals("B")) {

							requestXML = psnService.marshall(gestionReconocimientoMedico)
							requestBBDD = requestService.crear(opername, requestXML)
							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestBBDD.request, TipoOperacion.BAJA)

							message = "La baja se ha procesado correctamente"
							status = StatusType.OK
							code = 0

							logginService.putInfoMessage("Ha llegado una baja de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)

							correoUtil.envioEmailNoTratados("PsnResultadoReconocimientoMedicoRequest", "Ha llegado una baja de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)
						}

						if (gestionReconocimientoMedico.getCandidateInformation().getOperation().toString().equals("M")) {

							requestXML = psnService.marshall(gestionReconocimientoMedico)
							requestBBDD = requestService.crear(opername, requestXML)
							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestBBDD.request, TipoOperacion.MODIFICACION)

							message = "La modificacion se ha procesado correctamente"
							status = StatusType.OK
							code = 0

							logginService.putInfoMessage("Ha llegado una modificacion de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)

							correoUtil.envioEmailNoTratados("PsnResultadoReconocimientoMedicoRequest", "Ha llegado una modificacion de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)
						}
					} else {


						String error = util.detalleError(wsErrors)

						message = "Error de validacion: " + error
						status = StatusType.ERROR
						code = 8

						requestService.insertarError(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error de validacion: " + error)
						logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error de validacion: " + error)

					}
				}
			} else {

				message = "La operacion " + opername + " esta desactivada temporalmente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("GestionReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		} catch (Exception e){

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			requestService.insertarError(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error: " + e.getMessage())

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

		def opername="PsnResultadoReconocimientoMedicoResponse"
		def requestXML = ""
		def requestBBDD
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		RespuestaCRM respuestaCRM = new RespuestaCRM()

		String message = null
		StatusType status = null
		int code = 0

		Company company = Company.findByNombre(TipoCompany.PSN.getNombre())

		logginService.putInfoMessage("Realizando peticion de informacion de servicio ResultadoReconocimientoMedico para la cia " + company.nombre)

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo && resultadoReconocimientoMedico){

				if (resultadoReconocimientoMedico.numSolicitud != null && !resultadoReconocimientoMedico.numSolicitud.isEmpty()){

					requestXML=psnService.marshall(resultadoReconocimientoMedico)
					requestBBDD=requestService.crear(opername,requestXML)

					requestService.insertarEnvio (company, resultadoReconocimientoMedico.numSolicitud.toString(), requestXML.toString())

					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Realizando peticion para " + company.nombre + " con numero de solicitud: " + resultadoReconocimientoMedico.numSolicitud.toString())

					/** numSolicitud
					 *
					 */
					Filtro filtro = new Filtro()

					filtro = new Filtro()
					filtro.setClave(ClaveFiltro.CLIENTE)
					filtro.setValor(company.codigoSt)
					Filtro filtroRelacionado = new Filtro()
					filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
					filtroRelacionado.setValor(resultadoReconocimientoMedico.numSolicitud)
					filtro.setFiltroRelacionado(filtroRelacionado)

					respuestaCRM = psnService.informeExpedientePorFiltro(filtro,"ES")

					if(respuestaCRM != null && respuestaCRM.getListaExpedientesInforme() != null && respuestaCRM.getListaExpedientesInforme().size() > 0){

						if(respuestaCRM.getListaExpedientesInforme().size() == 1){

							for (int i = 0; i < respuestaCRM.getListaExpedientesInforme().size(); i++){

								Expediente expediente = respuestaCRM.getListaExpedientesInforme().get(i)

								/**PARA EVITAR CONSULTAR DATOS DE OTRAS COMPA�IAS
								 *
								 */

								if (expediente.getCandidato().getCompanya().getCodigoST().equals(company.getCodigoSt())) {

									if (expediente.getCodigoEstado().toString().equals("CERRADO") || expediente.getCodigoEstado().toString().equals("ANULADO") || expediente.getCodigoEstado().toString().equals("CANCELADO") ) {

										/**Solo se pueden devolver expedientes en estado CERRADO, ANULADO o CANCELLED
										 * 
										 */

										resultado.getExpediente().add(psnService.rellenaDatosSalidaConsulta(expediente, resultadoReconocimientoMedico.numSolicitud.toString()))

										message = "Resultados devueltos"
										status = StatusType.OK
										code = 3

										logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion realizada correctamente para " + company.nombre + " con numero de solicitud: " + resultadoReconocimientoMedico.numSolicitud.toString())
									} else {

										message = "El expediente no esta terminado"
										status = StatusType.OK
										code = 4

										logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion no realizada para " + company.nombre + " con numero de solicitud: " + resultadoReconocimientoMedico.numSolicitud.toString() + " porque el expediente no esta terminado")
									}
								}
							}
						} else {

							message = "Se ha encontrado mas de un expediente"
							status = StatusType.OK
							code = 5

							logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion no realizada para " + company.nombre + " con numero de solicitud: " + resultadoReconocimientoMedico.numSolicitud.toString() + " porque porque hay mas de un resultado")
						}
					} else {


						message = "No hay resultados para el numero de solitud indicado"
						status = StatusType.OK
						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No hay resultados para " + company.nombre)
						code = 6
					}
				} else {

					message = "Datos obligatorios incompletos. Es necesario un numero de solicitud"
					status = StatusType.ERROR
					code = 7
					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Datos obligatorios incompletos. Es necesario un numero de solicitud")
				}
			} else {

				message = "La operacion " + opername + " esta desactivada temporalmente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ResultadoReconocimientoMedico","Peticion de " + company.nombre + " con numero de expiente: " + resultadoReconocimientoMedico.numSolicitud.toString() + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con con numero de expiente: " + resultadoReconocimientoMedico.numSolicitud.toString() + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con numero de expiente: " + resultadoReconocimientoMedico.numSolicitud.toString(), e)

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			requestService.insertarError(company,resultadoReconocimientoMedico.numSolicitud.toString(), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + resultadoReconocimientoMedico.numSolicitud.toString() + ". Error: " + e.getMessage())
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

	@WebResult(name = "ConsolidacionPolizaResponse")
	ConsolidacionPolizaResponse consolidacionPoliza(
			@WebParam(partName = "ConsolidacionPolizaRequest", name = "ConsolidacionPolizaRequest")
			ConsolidacionPolizaRequest consolidacionPoliza) {

		def opername="PsnConsolidacionPolizaResponse"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		def crearExpedienteService
		def requestBBDD

		String notes = null
		StatusType status = null

		Company company = Company.findByNombre(TipoCompany.PSN.getNombre())
		RespuestaCRM expediente = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		ConsolidacionPolizaResponse resultado=new ConsolidacionPolizaResponse()
		String identificador = ""

		logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsolidacionPoliza para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				requestXML=psnService.marshall(consolidacionPoliza)
				requestBBDD = requestService.crear(opername,requestXML)

				if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()){

					Filtro filtro = new Filtro()
					filtro.setClave(ClaveFiltro.CLIENTE)
					filtro.setValor(company.getCodigoSt())
					Filtro filtroRelacionado = new Filtro()
					filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
					filtroRelacionado.setValor(consolidacionPoliza.requestNumber)

					identificador = "num_sol:"+consolidacionPoliza.requestNumber

					if (consolidacionPoliza.certificateNumber != null && !consolidacionPoliza.certificateNumber.isEmpty()) {

						Filtro filtroRelacionado2 = new Filtro()
						filtroRelacionado2.setClave(ClaveFiltro.NUM_SUPLEMENTO)
						filtroRelacionado2.setValor(consolidacionPoliza.certificateNumber)
						filtroRelacionado.setFiltroRelacionado(filtroRelacionado2)

						identificador = identificador + "-num_cer:" + consolidacionPoliza.certificateNumber
					}

					filtro.setFiltroRelacionado(filtroRelacionado)

					requestService.insertarRecibido(company, identificador, requestXML.toString(), TipoOperacion.CONSOLIDACION)

					expediente = psnService.informeExpedientePorFiltro(filtro,"ES")

					if (expediente != null && expediente.getErrorCRM() == null && expediente.getListaExpedientesInforme() != null && expediente.getListaExpedientesInforme().size() > 0) {

						if (expediente.getListaExpedientesInforme().size() == 1) {

							Expediente eModificado = expediente.getListaExpedientesInforme().get(0)
							eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

							logginService.putInfoEndpoint("ConsolidacionPoliza","Se procede a la modificacion de " + company.nombre + " con " + identificador)

							RespuestaCRM respuestaCrmExpediente = expedienteService.modificaExpediente("ES",eModificado,null,null)

							if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()){

								notes = "Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle()
								status = StatusType.ERROR

								correoUtil.envioEmail("ConsolidacionPoliza","Error en la modificacion de " + company.nombre + " con " + identificador + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle(), null)
								logginService.putInfoEndpoint("ConsolidacionPoliza","Error en la modificacion de " + company.nombre + " con " + identificador + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())

								requestService.insertarError(company, identificador, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + identificador + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())
							} else {

								notes = "El caso se ha procesado correctamente"
								status = StatusType.OK

								logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion realizada correctamente para " + company.nombre + " con " + identificador)
							}
						} else {

							notes = "Se ha encontrado mas de un expediente"
							status = StatusType.OK

							logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion no realizada por " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + " porque hay mas de un resultado")
						}
					} else {

						notes = "Poliza no encontrada"
						status = StatusType.OK

						logginService.putInfoEndpoint("ConsolidacionPoliza","No hay resultados para " + company.nombre)
					}
				} else {

					notes = "Datos obligatorios incompletos (ciaCode, requestNumber, policyNumber)"
					status = StatusType.ERROR

					logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion no realizada para " + company.nombre + " por falta de datos de entrada")
				}
			} else {

				notes = "Esta operacion esta desactivada temporalmente"
				status = StatusType.OK

				logginService.putInfoEndpoint("ConsolidacionPoliza","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ConsolidacionPoliza","Peticion de " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		} catch (Exception e){

			notes = "Error: " + e.getMessage()()
			status = StatusType.ERROR

			logginService.putErrorEndpoint("ConsolidacionPoliza","Peticion realizada para " + company.nombre + " con con numero de expiente: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ConsolidacionPoliza","Peticion realizada para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber, e)

			requestService.insertarError(company, identificador, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(notes)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)

		return resultado
	}

	@WebResult(name = "ConsultaExpediente")
	ConsultaExpedienteResponse consultaExpediente(
			@WebParam(partName = "ConsultaExpedienteRequest", name = "ConsultaExpedienteRequest")
			ConsultaExpedienteRequest consultaExpediente) {

		ConsultaExpedienteResponse resultado = new ConsultaExpedienteResponse()

		def opername="PsnConsultaExpediente"
		def requestXML = ""
		def requestBBDD
		RespuestaCRM respuestaCRM = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		String identificador = ""

		String message = null
		StatusType status = null
		int code = 0

		Company company = Company.findByNombre(TipoCompany.PSN.getNombre())

		logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsultaExpediente para la cia " + company.nombre)

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo && consultaExpediente) {


				if ((consultaExpediente.numSolicitud != null && !consultaExpediente.numSolicitud.isEmpty()) || (consultaExpediente.numExpediente != null  && !consultaExpediente.numExpediente.isEmpty()) || (consultaExpediente.numSumplemento != null && !consultaExpediente.numSumplemento.isEmpty())){

					requestXML=psnService.marshall(consultaExpediente)
					requestBBDD=requestService.crear(opername,requestXML)

					Filtro filtro = new Filtro()

					/**Si numExpediente esta relleno buscamos por codigost que es unico y el resto da igual
					 *
					 */
					if (consultaExpediente.numExpediente != null && !consultaExpediente.numExpediente.isEmpty()) {

						filtro = new Filtro()
						filtro.setClave(ClaveFiltro.EXPEDIENTE)
						filtro.setValor(consultaExpediente.numExpediente)

						identificador = "numExp: " +consultaExpediente.numExpediente
					} else if ((consultaExpediente.numSolicitud != null && !consultaExpediente.numSolicitud.isEmpty()) && (consultaExpediente.numSumplemento == null || consultaExpediente.numSumplemento.isEmpty())){

						/** numSolicitud
						 * 
						 */
						filtro = new Filtro()
						filtro.setClave(ClaveFiltro.CLIENTE)
						filtro.setValor(company.codigoSt)
						Filtro filtroRelacionado = new Filtro()
						filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
						filtroRelacionado.setValor(consultaExpediente.numSolicitud)
						filtro.setFiltroRelacionado(filtroRelacionado)

						identificador = "num_sol: " + consultaExpediente.numSolicitud
					} else if ((consultaExpediente.numSolicitud != null && !consultaExpediente.numSolicitud.isEmpty()) && (consultaExpediente.numSumplemento != null || !consultaExpediente.numSumplemento.isEmpty())){

						/** numSolicitud + numSuplemento
						 *
						 */

						filtro = new Filtro()
						filtro.setClave(ClaveFiltro.CLIENTE)
						filtro.setValor(company.codigoSt)

						Filtro filtroRelacionado = new Filtro()
						filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
						filtroRelacionado.setValor(consultaExpediente.numSolicitud)

						Filtro filtroRelacionado2 = new Filtro()
						filtroRelacionado2.setClave(ClaveFiltro.NUM_SUPLEMENTO)
						filtroRelacionado2.setValor(consultaExpediente.numSumplemento)
						filtroRelacionado.setFiltroRelacionado(filtroRelacionado2)

						filtro.setFiltroRelacionado(filtroRelacionado)

						identificador = "num_sol: " + consultaExpediente.numSolicitud + "-numSup: " + consultaExpediente.numSumplemento
					}

					requestService.insertarEnvio (company, identificador, requestXML.toString())

					logginService.putInfoEndpoint("ConsultaExpediente","Realizando peticion para " + company.nombre + " con " + identificador)

					respuestaCRM = psnService.informeExpedientePorFiltro(filtro,"ES")

					if(respuestaCRM != null && respuestaCRM.getListaExpedientesInforme() != null && respuestaCRM.getListaExpedientesInforme().size() > 0){

						for (int i = 0; i < respuestaCRM.getListaExpedientesInforme().size(); i++){

							Expediente expediente = respuestaCRM.getListaExpedientesInforme().get(i)

							/**PARA EVITAR CONSULTAR DATOS DE OTRAS COMPA�IAS
							 *
							 */

							if (expediente.getCandidato().getCompanya().getCodigoST().equals(company.getCodigoSt())) {

								resultado.getExpediente().add(psnService.rellenaDatosSalidaConsultaExpediente(expediente, new SimpleDateFormat("yyyyMMdd").format(new Date()), respuestaCRM))
							}

							if (resultado.getExpediente() != null && resultado.getExpediente().size() > 0) {

								message = "Resultados devueltos"
								status = StatusType.OK
								code = 3

								logginService.putInfoEndpoint("ConsultaExpediente","Peticion realizada correctamente para " + company.nombre + " con " + identificador)
							}
						}
					} else {

						resultado.expediente = null
						message = "No hay resultados para el numero de solitud indicado"
						status = StatusType.OK
						code = 6

						logginService.putInfoEndpoint("ConsultaExpediente","No hay resultados para " + company.nombre)
					}
				} else {

					message = "Datos obligatorios incompletos. Es necesario algun dato de busqueda (numSolicitud, numExpediente, numSumplemento) para la consulta de " + company.nombre
					status = StatusType.ERROR
					code = 7

					logginService.putInfoEndpoint("ConsultaExpediente","Datos obligatorios incompletos. Es necesario algun dato de busqueda (numSolicitud, numExpediente, numSumplemento) para la consulta de " + company.nombre)
				}
			} else {

				message = "La operacion " + opername + " esta desactivada temporalmente"
				status = StatusType.OK
				code = 1

				logginService.putInfoEndpoint("ConsultaExpediente","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ConsultaExpediente","Peticion de " + company.nombre + " con " + identificador + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ConsultaExpediente","Peticion realizada para " + company.nombre + " con " + identificador + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ConsultaExpediente","Peticion realizada para " + company.nombre + " con " + identificador, e)

			message = "Error: " + e.getMessage()
			status = StatusType.ERROR
			code = 2

			requestService.insertarError(company, identificador, requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para " + identificador + ". Error: " + e.getMessage())
		}finally{
			//BORRAMOS VARIABLES DE SESION
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

	@WebResult(name = "ConsultaDocumento")
	ConsultaDocumentoResponse consultaDocumento(
			@WebParam(partName = "ConsultaDocumentoRequest", name = "ConsultaDocumentoRequest")
			ConsultaDocumentoRequest consultaDocumento) {

		ConsultaDocumentoResponse resultado = new ConsultaDocumentoResponse()

		def opername="PsnConsultaDocumento"
		def requestXML = ""
		def requestBBDD
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		Filtro filtro = new Filtro()
		Company company = Company.findByNombre(TipoCompany.PSN.getNombre())
		String indentificador = null
		RespuestaCRM respuestaCRM = new RespuestaCRM()
		String identificador = ""
		String messages = null
		StatusType status = null

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsultaDocumento para la cia " + company.nombre)

			if(operacion && operacion.activo) {

				if (consultaDocumento && consultaDocumento.codigoSt != null && !consultaDocumento.codigoSt.isEmpty()){

					filtro = new Filtro()
					filtro.setClave(ClaveFiltro.EXPEDIENTE)
					filtro.setValor(consultaDocumento.codigoSt)

					respuestaCRM = psnService.informeExpedientePorFiltro(filtro,"ES")

					if (consultaDocumento.nodoAlfresco != null && !consultaDocumento.nodoAlfresco.isEmpty()) {

						if (psnService.existeDocumentoNodo(respuestaCRM,consultaDocumento.nodoAlfresco)) {

							identificador = "nodo: " + consultaDocumento.nodoAlfresco.substring(consultaDocumento.nodoAlfresco.lastIndexOf("/")+1,consultaDocumento.nodoAlfresco.length())

							requestXML=psnService.marshall(consultaDocumento)
							requestBBDD=requestService.crear(opername,requestXML)

							requestService.insertarEnvio(company, identificador, requestXML.toString())

							logginService.putInfoEndpoint("ConsultaDocumento","Realizando peticion para " + company.nombre + " con numero de expiente: " + consultaDocumento.codigoSt + " y " + identificador)

							resultado.setDocumento(psnService.rellenaDatosSalidaDocumentoNodo(consultaDocumento.nodoAlfresco, util.fromDateToXmlCalendar(new Date()), logginService, consultaDocumento.codigoSt))

							if (resultado.getDocumento() != null) {

								messages = "Resultados devueltos"
								status = StatusType.OK

								logginService.putInfoEndpoint("ConsultaDocumento","Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consultaDocumento.codigoSt + " y " + identificador)
							} else {

								resultado.documento = null
								messages = "No hay resultados para el nodo indicado"
								status = StatusType.OK

								logginService.putInfoEndpoint("ConsultaDocumento","No hay resultados para " + company.nombre)
							}
						} else {

							resultado.documento = null
							messages = "Este nodo no pertenece a este expediente"
							status = StatusType.OK

							logginService.putInfoEndpoint("ConsultaDocumento","El " + identificador + " no pertenece a este expediente:" + consultaDocumento.codigoSt)
						}
					} else if (consultaDocumento.documentacionId != null && !consultaDocumento.documentacionId.isEmpty()) {

						if (psnService.existeDocumentoId(respuestaCRM,consultaDocumento.documentacionId)) {

							identificador = "documentacionId: " + consultaDocumento.documentacionId

							requestXML=psnService.marshall(consultaDocumento)
							requestBBDD=requestService.crear(opername,requestXML)

							requestService.insertarEnvio(company, identificador, requestXML.toString())

							logginService.putInfoEndpoint("ConsultaDocumento","Realizando peticion para " + company.nombre + " con numero de expiente: " + consultaDocumento.codigoSt + " y " + identificador)

							resultado.setDocumento(psnService.rellenaDatosSalidaDocumentoNodo(consultaDocumento.documentacionId, util.fromDateToXmlCalendar(new Date()), logginService, consultaDocumento.codigoSt))

							if (resultado.getDocumento() != null) {

								messages = "Resultados devueltos"
								status = StatusType.OK

								logginService.putInfoEndpoint("ConsultaDocumento","Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consultaDocumento.codigoSt + " y " + identificador)
							} else {

								messages = "No hay resultados para el documentoId indicado"
								status = StatusType.OK

								logginService.putInfoEndpoint("ConsultaDocumento","No hay resultados para " + company.nombre)
							}
						} else {

							resultado.documento = null
							messages = "Este documentoId no pertenece a este expediente"
							status = StatusType.OK

							logginService.putInfoEndpoint("ConsultaDocumento","El documentoId " + consultaDocumento.documentacionId + " no pertenece a este expediente:" + consultaDocumento.codigoSt)
						}
					} else {

						messages = "Datos obligatorios incompletos nodoAlfresco o documentacionId del expediente"
						status = StatusType.OK
						logginService.putInfoEndpoint("ConsultaDocumento","Datos obligatorios incompletos nodoAlfresco o documentacionId del expediente")
					}
				} else {

					messages = "Datos obligatorios incompletos codigo de expediente"
					status = StatusType.OK
					logginService.putInfoEndpoint("ConsultaDocumento","Datos obligatorios incompletos codigo de expediente")
				}
			} else {

				messages = "La operacion ConsultaDocumento esta desactivada temporalmente"
				status = StatusType.OK

				logginService.putInfoEndpoint("ConsultaExpediente","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("ConsultaExpediente","Peticion de " + company.nombre + " con identificador " + identificador + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ConsultaExpediente","Peticion realizada para " + company.nombre + " con " + identificador + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ConsultaExpediente","Peticion realizada para " + company.nombre + " con " + identificador, e )

			messages = "Error en ConsultaExpediente: " + e.getMessage()
			status = StatusType.ERROR

			requestService.insertarError(company, identificador, requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + identificador + ". Error: " + e.getMessage())
			
		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(messages)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)

		return resultado
	}
}
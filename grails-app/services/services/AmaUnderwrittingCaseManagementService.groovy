package services

import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import servicios.ExpedienteInforme

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

import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM
import servicios.RespuestaCRMInforme

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.schemas.ama.ConsolidacionPolizaRequest
import com.scortelemed.schemas.ama.ConsolidacionPolizaResponse
import com.scortelemed.schemas.ama.ConsultaDocumentoRequest
import com.scortelemed.schemas.ama.ConsultaDocumentoResponse
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteResponse
import com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.ama.GestionReconocimientoMedicoResponse
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ResultadoSiniestroResponse
import com.scortelemed.schemas.ama.StatusType
import com.ws.servicios.impl.companies.AmaService
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/ama")
@SchemaValidation
@GrailsCxfEndpoint(address='/ama/AmaUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class AmaUnderwrittingCaseManagementService	 {

	def expedienteService
	def amaService
	def estadisticasService
	def requestService
	def logginService
	def tarificadorService

	@WebResult(name = "GestionReconocimientoMedicoResponse")
	GestionReconocimientoMedicoResponse gestionReconocimientoMedico(
			@WebParam(partName = "GestionReconocimientoMedicoRequest",name = "GestionReconocimientoMedicoRequest")
			GestionReconocimientoMedicoRequest gestionReconocimientoMedico) {

		def opername="AmaResultadoReconocimientoMedicoRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD

		String notes = null
		StatusType status = null

		Company company = Company.findByNombre(TipoCompany.AMA.getNombre())
		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		GestionReconocimientoMedicoResponse resultado=new GestionReconocimientoMedicoResponse()

		logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{
			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (company.generationAutomatic) {

					requestXML=amaService.marshall(gestionReconocimientoMedico)
					requestBBDD = requestService.crear(opername,requestXML)

					if (gestionReconocimientoMedico && gestionReconocimientoMedico.candidateInformation.operacion) {


						if (gestionReconocimientoMedico.candidateInformation.operacion.toString().toUpperCase().equals("A")){

							logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + gestionReconocimientoMedico.candidateInformation.requestNumber)

							expedienteService.crearExpediente(requestBBDD, TipoCompany.AMA)

							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)

							notes = "El caso se ha procesado correctamente"
							status = StatusType.OK

							/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
							expedienteService.busquedaCrm(requestBBDD, company, gestionReconocimientoMedico.candidateInformation.requestNumber, null, null)
							
						} else if (gestionReconocimientoMedico.candidateInformation.operacion.toString().toUpperCase().equals("M")){


							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION)

							notes = "El caso se ha procesado correctamente"
							status = StatusType.OK

							logginService.putInfoEndpoint("GestionReconocimientoMedico","Ha llegado una modificacion " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)
							correoUtil.envioEmailNoTratados("AmaResultadoReconocimientoMedicoRequest","Ha llegado una modificacion " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)
						} else if (gestionReconocimientoMedico.candidateInformation.operacion.toString().toUpperCase().equals("B")){


							requestService.insertarRecibido(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.BAJA)

							notes = "El caso se ha procesado correctamente"
							status = StatusType.OK

							logginService.putInfoEndpoint("GestionReconocimientoMedico","Ha llegado una baja de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)

							correoUtil.envioEmailNoTratados("GestionReconocimientoMedico","Ha llegado una baja de " + company.nombre + " con numero de referencia: " + gestionReconocimientoMedico.candidateInformation.requestNumber)
						} else {

							notes = "El campo operacion tiene que contener uno de estos valores ('A','M','B')"
							status = StatusType.OK

							logginService.putInfoEndpoint("GestionReconocimientoMedico","No se han introducido el tipo de operacion para " + company.nombre)
						}
					}
				}
			} else {

				notes = "Esta operacion esta desactivada temporalmente"
				status = StatusType.OK

				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		} catch (Exception e){

			notes = "Error: " + e.getMessage()
			status = StatusType.ERROR

			requestService.insertarError(company, gestionReconocimientoMedico.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.candidateInformation.requestNumber,e)
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

	@WebResult(name = "ResultadoSiniestroResponse")
	ResultadoSiniestroResponse resultadoSiniestro(
			@WebParam(partName = "ResultadoSiniestroRequest", name = "ResultadoSiniestroRequest")
			ResultadoSiniestroRequest resultadoSiniestro) {

		String opername="AmaResultadoSiniestroRequest"

		CorreoUtil correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD
		List<ExpedienteInforme> expedientes = new ArrayList<ExpedienteInforme>()

		Company company = Company.findByNombre(TipoCompany.AMA.getNombre())
		TransformacionUtil util = new TransformacionUtil()

		String notes = null
		StatusType status = null

		ResultadoSiniestroResponse resultado = new ResultadoSiniestroResponse()

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando peticion de informacion de servicio resultadoSiniestro para la cia " + company.nombre)

			if(operacion && operacion.activo){

				if (resultadoSiniestro && resultadoSiniestro.dateStart && resultadoSiniestro.dateEnd) {

					requestXML=amaService.marshall(resultadoSiniestro)
					requestBBDD = requestService.crear(opername,requestXML)

					Date date = resultadoSiniestro.dateStart.toGregorianCalendar().getTime()
					SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
					String fechaIni = sdfr.format(date)
					date = resultadoSiniestro.dateEnd.toGregorianCalendar().getTime()
					String fechaFin = sdfr.format(date)

					if (Environment.current.name.equals("production_wildfly")) {
						expedientes=expedienteService.obtenerInformeExpedientesSiniestros("1060",null,null,fechaIni,fechaFin,company.ou)
					} else {
						expedientes=expedienteService.obtenerInformeExpedientesSiniestros("1061",null,null,fechaIni,fechaFin,company.ou)
					}

					logginService.putInfoEndpoint("ResultadoSiniestro","Realizando peticion para " + company.nombre + " con fecha " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10))

					requestService.insertarEnvio (company, resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10), requestXML.toString())


					if(expedientes){

						expedientes.each { expedientePoliza ->

							/**
							 * VALIDACION PARA VER QUE TODOS LOS DATOS CENESARIOS PARA AMA ESTAN RELLENOS 
							 * 
							 */

							if (amaService.expedienteGestionado(expedientePoliza, correoUtil, opername) && amaService.cumpleRequisitosVisualizacion(expedientePoliza, correoUtil, opername)){

								resultado.getExpediente().add(amaService.rellenaDatosSalidaSiniestro(expedientePoliza))
							}
						}

						if (resultado.getExpediente() != null && resultado.getExpediente().size() > 0){

							notes = "Resultados devueltos"
							status = StatusType.OK

							logginService.putInfoEndpoint("ResultadoSiniestro","Peticion realizada correctamente para " + company.nombre + " con fecha: " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10))
						} else

							notes = "No hay resultados para las fechas indicadas"
							status = StatusType.OK

							logginService.putInfoEndpoint("ResultadoSiniestro","No hay resultados para " + company.nombre)
					} else {

						notes = "No hay resultados para las fechas indicadas"
						status = StatusType.OK

						logginService.putInfoEndpoint("ResultadoSiniestro","No hay resultados para " + company.nombre)
					}
				} else {

					notes = "No se han introducido fechas para la consulta"
					status = StatusType.ERROR

					logginService.putInfoEndpoint("ResultadoSiniestro","No se han introducido fechas para la consulta " + company.nombre)
				}
			} else {

				notes = "Esta operacion esta desactivada temporalmente"
				status = StatusType.OK

				logginService.putInfoEndpoint("ResultadoSiniestro","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ResultadoSiniestro","Peticion de " + company.nombre + " con fecha: " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10) + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente",0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ResultadoSiniestro","Peticion realizada para " + company.nombre + " con fecha: " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10) + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoSiniestro","Peticion realizada para " + company.nombre + " con fecha: " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10), e)

			notes = "Error: " + e.getMessage()
			status = StatusType.ERROR

			requestService.insertarError(company, resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + resultadoSiniestro.dateStart.toString().substring(0,10) +"-"+resultadoSiniestro.dateEnd.toString().substring(0,10) + ". Error: " + e.getMessage())
		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setNotas(notes)
		resultado.setEstado(status)
		resultado.setFecha(util.fromDateToXmlCalendar(new Date()))

		return resultado
	}

	@WebResult(name = "ConsolidacionPolizaResponse")
	ConsolidacionPolizaResponse consolidacionPoliza(
			@WebParam(partName = "ConsolidacionPolizaRequest", name = "ConsolidacionPolizaRequest")
			ConsolidacionPolizaRequest consolidacionPoliza) {

		def opername="AmaConsolidacionPolizaResponse"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD
		def codigoSt

		String notes = null
		StatusType status = null
		int codigo = 0

		Company company = Company.findByNombre(TipoCompany.AMA.getNombre())
		RespuestaCRM respuestaCRM = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		ConsolidacionPolizaResponse resultado=new ConsolidacionPolizaResponse()

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsolidacionPoliza para la cia " + company.nombre)

			if (operacion && operacion.activo){

				requestXML=amaService.marshall(consolidacionPoliza)
				requestBBDD = requestService.crear(opername,requestXML)

				if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() != null && consolidacionPoliza.ciaCode !=null && !consolidacionPoliza.ciaCode.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()){

					if (consolidacionPoliza.ciaCode.equals("C0803")){
						codigoSt = "1064"
					}
					if (consolidacionPoliza.ciaCode.equals("M0328")){
						codigoSt = "1059"
					}

					requestService.insertarRecibido(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION)

					respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consolidacionPoliza.requestNumber,company.ou,codigoSt )

					if (respuestaCRM != null && respuestaCRM.getErrorCRM() == null && respuestaCRM.getListaExpedientes() != null && respuestaCRM.getListaExpedientes().size() > 0){

						if (respuestaCRM.getListaExpedientes().size() == 1) {

							logginService.putInfoEndpoint("ConsolidacionPoliza","Se procede a la modificacion de " + company.nombre + " con numero de solicitud " + consolidacionPoliza.requestNumber)

							Expediente eModificado = respuestaCRM.getListaExpedientes().get(0)
							eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

							RespuestaCRM respuestaCrmExpediente = expedienteService.modificaExpediente(company.ou,eModificado,null,null)

							if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()){


								notes = "Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle()
								status = StatusType.ERROR
								codigo = 1

								correoUtil.envioEmail("ConsolidacionPoliza","Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle(), null)
								logginService.putInfoEndpoint("ConsolidacionPoliza","Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())

								requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())
							} else {

								notes = "El caso se ha procesado correctamente"
								status = StatusType.OK
								codigo = 0

								logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber)
							}
						} else {

							notes = "Se ha encontrado mas de un expediente"
							status = StatusType.OK
							codigo = 2

							logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion no realizada para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + " porque hay mas de un resultado")
						}
					} else {

						notes = "Poliza no encontrada"
						status = StatusType.OK
						codigo = 3

						logginService.putInfoEndpoint("ConsolidacionPoliza","No hay resultados para " + company.nombre)
					}
				} else {

					notes = "Datos obligatorios incompletos (ciaCode, requestNumber, policyNumber)"
					status = StatusType.ERROR
					codigo = 4

					logginService.putInfoEndpoint("ConsolidacionPoliza","Peticion no realizada para " + company.nombre + " por falta de datos de entrada")
				}
			} else {

				notes = "Esta operacion esta desactivada temporalmente"
				status = StatusType.OK
				codigo = 5

				logginService.putInfoEndpoint("ConsolidacionPoliza","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ConsolidacionPoliza","Peticion de " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + " .Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		} catch (Exception e){

			notes = "Error: " + e.getMessage()()
			status = StatusType.ERROR
			codigo = 6

			logginService.putErrorEndpoint("ConsolidacionPoliza","Peticion realizada para " + company.nombre + " con con numero de expiente: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ConsolidacionPoliza","Peticion realizada para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber,e)

			requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(notes)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)
		resultado.setCodigo(codigo)

		return resultado
	}

	@WebResult(name = "ConsultaExpediente")
	ConsultaExpedienteResponse consultaExpediente(
			@WebParam(partName = "ConsultaExpedienteRequest", name = "ConsultaExpedienteRequest")
			ConsultaExpedienteRequest consultaExpediente) {

		ConsultaExpedienteResponse resultado = new ConsultaExpedienteResponse()

		def opername="AmaConsultaExpediente"
		def requestXML = ""
		Request requestBBDD
		RespuestaCRM respuestaCRM = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		String identificador = ""

		Company company = Company.findByNombre(TipoCompany.AMA.getNombre())

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " para solicitud: " + consultaExpediente.numSolicitud)

			if(operacion && operacion.activo && consultaExpediente) {


				if ((consultaExpediente.numSolicitud != null && !consultaExpediente.numSolicitud.isEmpty()) || (consultaExpediente.numExpediente != null  && !consultaExpediente.numExpediente.isEmpty()) || (consultaExpediente.numSumplemento != null && !consultaExpediente.numSumplemento.isEmpty())){

					requestXML=amaService.marshall(consultaExpediente)
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
					} else {

						resultado.expediente = null
						resultado.setNotes("Datos obligatorios incompletos. Es necesario indicar numExpediente o numSolicitud o numSolicitud+numSuplemento")
						resultado.setDate(util.fromDateToXmlCalendar(new Date()))
						resultado.setStatus(StatusType.OK)
						logginService.putInfoMessage("Datos obligatorios incompletos. Es necesario indicar numExpediente o numSolicitud o numSolicitud+numSuplemento." + company.nombre)
						return resultado
					}
					requestService.insertarEnvio(company, identificador, requestXML.toString())

					respuestaCRM = expedienteService.informeExpedientePorFiltro(filtro,company.ou)

					if(respuestaCRM != null && respuestaCRM.getListaExpedientesInforme() != null && respuestaCRM.getListaExpedientesInforme().size() > 0){

						for (int i = 0; i < respuestaCRM.getListaExpedientesInforme().size(); i++){

							Expediente expediente = respuestaCRM.getListaExpedientesInforme().get(i)

							/**PARA EVITAR CONSULTAR DATOS DE OTRAS COMPANIAS
							 *
							 */

							if (expediente.getCandidato().getCompanya().getCodigoST().equals(company.getCodigoSt())) {

								resultado.getExpediente().add(amaService.rellenaDatosSalidaConsultaExpediente(expediente, new SimpleDateFormat("yyyyMMdd").format(new Date()), respuestaCRM))
							}
							if (resultado.getExpediente() != null) {


								resultado.setNotes("Resultados devueltos")
								resultado.setDate(util.fromDateToXmlCalendar(new Date()))
								resultado.setStatus(StatusType.OK)
							}
						}
					} else {
						resultado.expediente = null
						resultado.setNotes("No hay resultados para el expediente indicado")
						resultado.setDate(util.fromDateToXmlCalendar(new Date()))
						resultado.setStatus(StatusType.OK)
						logginService.putInfoMessage("No hay resultados para " + company.nombre)
					}

					logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente de " + company.nombre + " para la solicitud: " + identificador)
				} else {

					resultado.setNotes("Datos obligatorios incompletos. Es necesario algun dato de busqueda (numSolicitud, numExpediente, numSumplemento)")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.ERROR)
					logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion no realizada por falta de datos")
				}
			} else {
				resultado.setNotes("La operacion " + opername + " esta desactivada temporalmente")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				logginService.putInfoEndpoint("Endpoint-"+opername,"La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion realizada de " + company.nombre + " para la solicitud: " + identificador + "- Error: " + e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion realizada de " + company.nombre + " para la solicitud: " + identificador,e)
			resultado.setNotes("Error en consultaExpediente: " + e.getMessage())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			requestService.insertarError(company, identificador, requestXML.toString(), TipoOperacion.CONSULTA, e.getMessage())

		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}
	@WebResult(name = "ConsultaDocumento")
	ConsultaDocumentoResponse consultaDocumento(
			@WebParam(partName = "ConsultaDocumentoRequest", name = "ConsultaDocumentoRequest")
			ConsultaDocumentoRequest consultaDocumento) {

		ConsultaDocumentoResponse resultado = new ConsultaDocumentoResponse()

		def opername="AmaConsultaDocumento"
		def requestXML = ""
		Request requestBBDD
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()
		Filtro filtro = new Filtro()
		Company company = Company.findByNombre(TipoCompany.AMA.getNombre())
		String indentificador = null
		RespuestaCRM respuestaCRM = new RespuestaCRM()

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " para solicitud: " + consultaDocumento.nodoAlfresco)

			if(operacion && operacion.activo) {

				if (consultaDocumento && consultaDocumento.codigoSt != null && !consultaDocumento.codigoSt.isEmpty()){

					filtro = new Filtro()
					filtro.setClave(ClaveFiltro.EXPEDIENTE)
					filtro.setValor(consultaDocumento.codigoSt)

					respuestaCRM = expedienteService.informeExpedientePorFiltro(filtro,company.ou)

					if (consultaDocumento.nodoAlfresco != null && !consultaDocumento.nodoAlfresco.isEmpty()) {

						if (amaService.existeDocumentoNodo(respuestaCRM,consultaDocumento.nodoAlfresco)) {

							requestXML=amaService.marshall(consultaDocumento)
							requestBBDD=requestService.crear(opername,requestXML)
							requestService.insertarEnvio(company, consultaDocumento.nodoAlfresco.substring(consultaDocumento.nodoAlfresco.lastIndexOf("/")+1,consultaDocumento.nodoAlfresco.length()), requestXML.toString())

							resultado.setDocumento(amaService.rellenaDatosSalidaDocumentoNodo(consultaDocumento.nodoAlfresco, util.fromDateToXmlCalendar(new Date()), logginService, consultaDocumento.codigoSt))



							if (resultado.getDocumento() != null) {

								resultado.setMessage("Resultados devueltos")
								resultado.setDate(util.fromDateToXmlCalendar(new Date()))
								resultado.setStatus(StatusType.OK)
							} else {

								resultado.documento = null
								resultado.setMessage("No hay resultados para el nodo indicado")
								resultado.setDate(util.fromDateToXmlCalendar(new Date()))
								resultado.setStatus(StatusType.OK)
								logginService.putInfoMessage("No hay resultados para " + company.nombre)
							}

							logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente de " + company.nombre + " para la solicitud: " + consultaDocumento.nodoAlfresco)
						} else {

							resultado.documento = null
							resultado.setMessage("Este nodo no pertenece a este expediente")
							resultado.setDate(util.fromDateToXmlCalendar(new Date()))
							resultado.setStatus(StatusType.OK)
							logginService.putInfoMessage("Este nodo no pertenece a este expediente." + company.nombre)
						}
					} else if (consultaDocumento.documentacionId != null && !consultaDocumento.documentacionId.isEmpty()) {

						if (amaService.existeDocumentoId(respuestaCRM,consultaDocumento.documentacionId)) {

							requestXML=amaService.marshall(consultaDocumento)
							requestBBDD=requestService.crear(opername,requestXML)
							requestService.insertarEnvio(company, consultaDocumento.documentacionId, requestXML.toString())

							resultado.setDocumento(amaService.rellenaDatosSalidaDocumentoId(consultaDocumento.documentacionId, util.fromDateToXmlCalendar(new Date()), logginService, consultaDocumento.codigoSt))

							if (resultado.getDocumento() != null) {

								resultado.setMessage("Resultados devueltos")
								resultado.setDate(util.fromDateToXmlCalendar(new Date()))
								resultado.setStatus(StatusType.OK)
							} else {

								resultado.documento = null
								resultado.setMessage("No hay resultados para el documentoId indicado")
								resultado.setDate(util.fromDateToXmlCalendar(new Date()))
								resultado.setStatus(StatusType.OK)
								logginService.putInfoMessage("No hay resultados para " + company.nombre)
							}

							logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion realizada correctamente de " + company.nombre + " para la solicitud: " + consultaDocumento.nodoAlfresco)
						} else {

							resultado.documento = null
							resultado.setMessage("Este documentoId no pertenece a este expediente")
							resultado.setDate(util.fromDateToXmlCalendar(new Date()))
							resultado.setStatus(StatusType.OK)
							logginService.putInfoMessage("Este documentoId no pertenece a este expediente." + company.nombre)
						}
					} else {

						resultado.setMessage("Datos obligatorios incompletos nodoAlfresco o documentacionId del expediente")
						resultado.setDate(util.fromDateToXmlCalendar(new Date()))
						resultado.setStatus(StatusType.OK)
						logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion no realizada por falta de datos")
					}
				} else {

					resultado.setMessage("Datos obligatorios incompletos codigoSt del expediente")
					resultado.setDate(util.fromDateToXmlCalendar(new Date()))
					resultado.setStatus(StatusType.OK)
					logginService.putInfoEndpoint("Endpoint-"+opername,"Peticion no realizada por falta de datos")
				}
			} else {

				resultado.setMessage("La operacion " + opername + " esta desactivada temporalmente")
				resultado.setDate(util.fromDateToXmlCalendar(new Date()))
				resultado.setStatus(StatusType.ERROR)
				logginService.putInfoEndpoint("Endpoint-"+opername, "La operacion " + opername + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores(opername,"Endpoint-"+ opername + ". La operacion esta desactivada temporalmente",null)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("Endpoint-"+opername,"Peticion realizada de " + company.nombre + " para la solicitud: " + consultaDocumento.nodoAlfresco + "- Error: " + e.getMessage())
			correoUtil.envioEmailErrores(opername,"Peticion realizada de " + company.nombre + " para la solicitud: " + consultaDocumento.nodoAlfresco,e)
			resultado.setMessage("Error en consultaExpediente: " + e.getMessage())
			resultado.setDate(util.fromDateToXmlCalendar(new Date()))
			resultado.setStatus(StatusType.ERROR)
			requestService.insertarError(company, consultaDocumento.nodoAlfresco.substring(consultaDocumento.nodoAlfresco.lastIndexOf("/")+1,consultaDocumento.nodoAlfresco.length()), requestXML.toString(), TipoOperacion.DOCUMENTACION, e.getMessage())

		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		return resultado
	}
}
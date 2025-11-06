package services

import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
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

import org.springframework.web.context.request.RequestContextHolder
import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM
import servicios.RespuestaCRMInforme

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierResponse
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementResponse
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCasesResultsResponse
import com.scortelemed.schemas.netinsurance.StatusType
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService
import com.ws.servicios.impl.companies.NetinsuranceService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.TarificadorService

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/netinsurance")
@SchemaValidation
@GrailsCxfEndpoint(address='/netinsurance/NetinsuranceUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class NetinsuranceUnderwrittingCaseManagementService	 {

	def expedienteService
	def netinsuranceService
	def estadisticasService
	def requestService
	def logginService
	def tarificadorService

	@WebResult(name = "CaseManagementResponse")
	NetinsuranteUnderwrittingCaseManagementResponse netInsuranteUnderwrittingCaseManagement(
			@WebParam(partName = "CaseManagementRequest",name = "CaseManagementRequest")
			NetinsuranteUnderwrittingCaseManagementRequest netInsuranteUnderwrittingCaseManagement) {

		def opername="NetinsuranceUnderwrittingCaseManagementRequest"
		def correoUtil = new CorreoUtil()
		def requestXML = ""
		Request requestBBDD

		Company company = Company.findByNombre(TipoCompany.NET_INSURANCE.getNombre())
		Filtro filtro = new Filtro()
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
		TransformacionUtil util = new TransformacionUtil()
		NetinsuranteUnderwrittingCaseManagementResponse resultado =new NetinsuranteUnderwrittingCaseManagementResponse()

		String message = null
		StatusType status = null

		logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

		try{

			def operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if (operacion && operacion.activo){

				if (company.generationAutomatic) {

					requestXML=netinsuranceService.marshall(netInsuranteUnderwrittingCaseManagement)
					requestBBDD = requestService.crear(opername,requestXML)

					expedienteService.crearExpediente(requestBBDD, TipoCompany.NET_INSURANCE)

					message = "Il caso � stato elaborato correttamente"
					status = StatusType.OK

					requestService.insertarRecibido(company, netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)

					/**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
					expedienteService.busquedaCrm(requestBBDD, company, netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber, null, null)
				}
			} else {

				message = "L'operazione viene disattivata temporaneamente"
				status = StatusType.OK

				logginService.putInfoEndpoint("GestionReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber,"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		} catch (Exception e){

			message = "Error: " + e.printStackTrace()
			status = StatusType.ERROR

			requestService.insertarError(company, netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber + ". Error: " + e.getMessage())

			logginService.putErrorEndpoint("GestionReconocimientoMedico","Peticion no realizada de " + company.nombre + " con numero de solicitud: " + netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("GestionReconocimientoMedico","Peticion de " + company.nombre + " con numero de solicitud: " + netInsuranteUnderwrittingCaseManagement.candidateInformation.requestNumber,e.getMessage())
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setMessage(message)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)

		return resultado
	}

	@WebResult(name = "CaseManagementResultsResponse")
	NetinsuranteUnderwrittingCasesResultsResponse netInsuranteUnderwrittingCasesResults(
			@WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
			NetinsuranteUnderwrittingCasesResultsRequest netInsuranteUnderwrittingCasesResults) {

		def opername="NetinsuranceUnderwrittingCaseManagementResponse"
		def requestXML = ""
		Request requestBBDD
		List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		String notes = null
		StatusType status = null

		NetinsuranteUnderwrittingCasesResultsResponse resultado =new NetinsuranteUnderwrittingCasesResultsResponse()

		Company company = Company.findByNombre(TipoCompany.NET_INSURANCE.getNombre())

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString())

			if(operacion && operacion.activo ) {

				if (netInsuranteUnderwrittingCasesResults && netInsuranteUnderwrittingCasesResults.dateStart && netInsuranteUnderwrittingCasesResults.dateEnd){

					requestXML=netinsuranceService.marshall(netInsuranteUnderwrittingCasesResults)
					requestBBDD=requestService.crear(opername,requestXML)

					Date date = netInsuranteUnderwrittingCasesResults.dateStart.toGregorianCalendar().getTime()
					SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
					String fechaIni = sdfr.format(date)
					date = netInsuranteUnderwrittingCasesResults.dateEnd.toGregorianCalendar().getTime()
					String fechaFin = sdfr.format(date)

					expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt,null,1,fechaIni,fechaFin,company.ou))
					expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt,null,2,fechaIni,fechaFin,company.ou))

					requestService.insertarEnvio(company, netInsuranteUnderwrittingCasesResults.dateStart.toString().substring(0,10) + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString().substring(0,10), requestXML.toString())

					if(expedientes){

						expedientes.each { expedientePoliza ->

							resultado.getExpediente().add(netinsuranceService.rellenaDatosSalidaConsulta(expedientePoliza, netInsuranteUnderwrittingCasesResults.dateStart, logginService))
						}

						notes = "Risultati restituiti"
						status = StatusType.OK

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Peticion realizada correctamente para " + company.nombre + " con fecha: " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString())
					}else{

						notes = "Nessun risultato per le date indicate"
						status = StatusType.OK

						logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No hay resultados para " + company.nombre)
					}
				} else {

					notes = "Nessuna data � stata inserita per la query"
					status = StatusType.ERROR

					logginService.putInfoEndpoint("ResultadoReconocimientoMedico","No se han introducido fechas para la consulta " + company.nombre)
				}
			} else {

				notes = "L'operazione viene disabilitata temporaneamente"
				status = StatusType.OK

				logginService.putInfoEndpoint("ResultadoReconocimientoMedico","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion de " + company.nombre + " con fecha: " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString(),"Esta operacion para " + company.nombre + " esta desactivada temporalmente")
			}
		}catch (Exception e){


			notes = "Error: " + e.getMessage()
			status = StatusType.ERROR

			logginService.putErrorEndpoint("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ResultadoReconocimientoMedico","Peticion realizada para " + company.nombre + " con fecha: " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())

			requestService.insertarError(company, netInsuranteUnderwrittingCasesResults.dateStart.toString().substring(0,10) + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString().substring(0,10), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + netInsuranteUnderwrittingCasesResults.dateStart.toString() + "-" + netInsuranteUnderwrittingCasesResults.dateEnd.toString() + ". Error: " + e.getMessage())
		}finally{

			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setNotes(notes)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)

		return resultado
	}

	@WebResult(name = "NetinsuranteGetDossierResponse")
	NetinsuranteGetDossierResponse netInsuranteGetDossier(
			@WebParam(partName = "NetinsuranteGetDossierRequest", name = "NetinsuranteGetDossierRequest")
			NetinsuranteGetDossierRequest consultaExpediente) {

		NetinsuranteGetDossierResponse resultado = new NetinsuranteGetDossierResponse()

		def opername="NetinsuranteGetDossier"
		def requestXML = ""
		Request requestBBDD
		String notes = null
		StatusType status = null

		RespuestaCRM respuestaCRM = new RespuestaCRM()
		TransformacionUtil util = new TransformacionUtil()
		CorreoUtil correoUtil = new CorreoUtil()

		Company company = Company.findByNombre(TipoCompany.NET_INSURANCE.getNombre())

		logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsultaExpediente para la cia " + company.nombre)

		try{

			Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

			if(operacion && operacion.activo) {

				if (consultaExpediente && consultaExpediente.requestNumber) {

					requestXML=netinsuranceService.marshall(consultaExpediente)
					requestBBDD=requestService.crear(opername,requestXML)

					logginService.putInfoEndpoint("ConsultaExpediente","Realizando peticion para " + company.nombre + " con numero de expiente: " + consultaExpediente.requestNumber)
					respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consultaExpediente.requestNumber, company.ou, company.codigoSt)
					requestService.insertarEnvio (company, consultaExpediente.requestNumber, requestXML.toString())
					if(respuestaCRM != null && respuestaCRM.getListaExpedientes() != null){

						for (int i = 0; i < respuestaCRM.getListaExpedientes().size(); i++){

							Expediente expediente = respuestaCRM.getListaExpedientes().get(i)

							/**PARA EVITAR CONSULTAR DATOS DE OTRAS COMPA�IAS
							 *
							 */

							if (expediente.getCandidato().getCompanya().getCodigoST().equals(company.getCodigoSt())) {

								resultado.getExpedienteConsulta().add(netinsuranceService.rellenaDatosSalidaExpediente(expediente, util.fromDateToXmlCalendar(new Date()), logginService, company.nombre, company))
							}
						}
					}
					if (resultado.getExpedienteConsulta() != null && resultado.getExpedienteConsulta().size() > 0) {
						notes = "Risultati restituiti"
						status = StatusType.OK
						logginService.putInfoEndpoint("ConsultaExpediente","Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consultaExpediente.requestNumber)
					} else {
						resultado.expedienteConsulta = null
						notes = "Nessun risultato per le dossier indicate"
						status = StatusType.OK
						logginService.putInfoEndpoint("ConsultaExpediente","No hay resultados para " + company.nombre)
					}
				} else {

					notes = "Nessun file � stato introdotto"
					status = StatusType.ERROR

					logginService.putInfoEndpoint("ConsultaExpediente","No se han introducido expediente para la consulta de " + company.nombre)
				}
			} else {

				notes = "Questa operazione � temporaneamente disabilitata"
				status = StatusType.OK

				logginService.putInfoEndpoint("ConsultaExpediente","Esta operacion para " + company.nombre + " esta desactivada temporalmente")
				correoUtil.envioEmail("ConsultaExpediente","Peticion de " + company.nombre + " con numero de expiente: " + consultaExpediente.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
			}
		}catch (Exception e){

			logginService.putErrorEndpoint("ConsultaExpediente","Peticion realizada para " + company.nombre + " con con numero de expiente: " + consultaExpediente.requestNumber + ". Error: " + e.getMessage())
			correoUtil.envioEmailErrores("ConsultaExpediente","Peticion realizada para " + company.nombre + " con numero de expiente: " + consultaExpediente.requestNumber, e)

			notes = "Errore in ConsultaExpediente: " + e.getMessage()
			status = StatusType.ERROR

			requestService.insertarError(company, consultaExpediente.requestNumber, requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + consultaExpediente.requestNumber + ". Error: " + e.getMessage())
		}finally{
			//BORRAMOS VARIABLES DE SESION
			def sesion=RequestContextHolder.currentRequestAttributes().getSession()
			sesion.removeAttribute("userEndPoint")
			sesion.removeAttribute("companyST")
		}

		resultado.setNotes(notes)
		resultado.setDate(util.fromDateToXmlCalendar(new Date()))
		resultado.setStatus(status)

		return resultado
	}
}
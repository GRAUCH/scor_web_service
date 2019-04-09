package com.scortelemed

import com.scortelemed.schemas.enginyers.AddExp
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.QName
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource

import org.springframework.beans.factory.annotation.Autowired

import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest;
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest
import com.ws.servicios.AmaService
import com.ws.servicios.CajamarService
import com.ws.servicios.CaserService
import com.ws.servicios.LagunaroService
import com.ws.servicios.LogginService
import com.ws.servicios.NetinsuranceService;
import com.ws.servicios.EnginyersService

@Secured(['ROLE_ADMIN'])
class RequestController {

	def estadisticasService
	def validacionXmlService
	def logginService = new LogginService()
	def crearExpedienteService
	def requestService
	@Autowired
	private CaserService caserService
	@Autowired
	private AmaService amaService
	@Autowired
	private LagunaroService lagunaroService
	@Autowired
	private CajamarService cajamarService
	@Autowired
	private NetinsuranceService netinsuranceService
	@Autowired
	private EnginyersService enginyersService


	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = { redirect action:'listbuscador' }

	def create = {
		def requestInstance = new Request()
		requestInstance.properties = params
		return [requestInstance: requestInstance]
	}

	def save = {
		def requestInstance = new Request(params)
		if (requestInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
			redirect(action: "show", id: requestInstance.id)
		}
		else {
			render(view: "create", model: [requestInstance: requestInstance])
		}
	}

	def show = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"

		def requestInstance = Request.get(params.id)

		if (!requestInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
			redirect(action: "list")
		}
		else {
			def estadisticasInstanceList = Estadistica.findAllByRequest(requestInstance, params)
			def total = Estadistica.findAllByRequest(requestInstance).size()

			[requestInstance: requestInstance, estadisticasInstanceList: estadisticasInstanceList, total: total]
		}
	}

	def estadisticas = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"

		def requestInstance = Request.get(params.id)

		def estadisticasInstanceList = Estadistica.findAllByRequest(requestInstance, params)
		def total = Estadistica.findAllByRequest(requestInstance).size()

		[requestInstance: requestInstance, estadisticasInstanceList: estadisticasInstanceList, total: total]
	}

	def edit = {
		def requestInstance = Request.get(params.id)
		if (!requestInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [requestInstance: requestInstance]
		}
	}

	def update = {
		def requestInstance = Request.get(params.id)
		if (requestInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (requestInstance.version > version) {
					requestInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'request.label', default: 'Request')] as Object[], "Another user has updated this Request while you were editing")
					render(view: "edit", model: [requestInstance: requestInstance])
					return
				}
			}
			requestInstance.properties = params
			if (!requestInstance.hasErrors() && requestInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				redirect(action: "show", id: requestInstance.id)
			}
			else {
				render(view: "edit", model: [requestInstance: requestInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def requestInstance = Request.get(params.id)
		if (requestInstance) {
			try {
				requestInstance.delete(flush: true)
				def listaEstadisticas = Estadistica.findAllByRequest(requestInstance)
				listaEstadisticas.each { it.delete() }
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'request.label', default: 'Request'), params.id])}"
			redirect(action: "list")
		}
	}

	def updateRelacion = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"

		def requestInstance = Request.get(params.id)

		def estadisticasInstanceList = Estadistica.findAllByRequest(requestInstance, params)
		def total = Estadistica.findAllByRequest(requestInstance).size()

		render(view: "estadisticas", model: [requestInstance: requestInstance, estadisticasInstanceList: estadisticasInstanceList, total: total])
	}


	def listbuscador = {

		if(!params.pag && (params.webservice || params.operacion || params.claveProceso || params.request || params.descartado || params.fecha_procesado || params.errores || params.condiciones || params.company)) {
			session['operacion'] = params.operacion
			session['claveProceso'] = params.claveProceso
			session['request'] = params.request
			session['descartado'] = params.descartado
			session['fecha_procesado'] = params.fecha_procesado
			session['fecha_procesadoFin'] = params.fecha_procesadoFin
			session['errores'] = params.errores
			session['condiciones'] = params.condiciones
			session['company'] = params.company
			session['webservice'] = params.webservice
			session['fecha_creacion'] = params.fecha_creacion
			session['fecha_creacionFin'] = params.fecha_creacionFin
			session['bus'] = params.input
		}

		if(params.pag){
			session['sort'] = params.sort
			session['order'] = params.order
			session['max'] = params.max
			session['offset'] = params.offset
		}

		params.operacion = session['operacion']
		params.claveProceso = session['claveProceso']
		params.request = session['request']
		params.descartado = session['descartado']
		params.fecha_procesado = session['fecha_procesado']
		params.fecha_procesadoFin = session['fecha_procesadoFin']
		params.errores = session['errores']
		params.condiciones = session['condiciones']
		params.company = session['company']
		params.webservice = session['webservice']
		params.fecha_creacion = session['fecha_creacion']
		params.fecha_creacionFin = session['fecha_creacionFin']
		params.input = session['bus']

		if(session['max']) params.max = session['max']
		else params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)

		if(session['offset']) params.offset = session['offset']
		else params.offset = params?.offset?.toInteger() ?: 0

		if(session['sort']) params.sort = session['sort']
		else params.sort = params?.sort ?: "fecha_creacion"

		if(session['order']) params.order = session['order']
		else params.order = params?.order ?: "desc"

		if(params.input == 'Buscar' || params.pag) {
			def opera
			if(params.operacion){
				opera = Operacion.findAllByClaveLike("%" + params.operacion.replaceAll(" ", "%") + "%")
			}
			def comp
			if(params.company){
				comp = Company.findAllByNombreLike("%" + params.company.replaceAll(" ", "%") + "%")
			}

			def descartado
			if(params.descartado == 'on') descartado = true

			def webs
			if(params.webservice) {
				webs = Webservice.findAllByClaveLike(params.webservice.replaceAll(" ", "%"))
			}

			def cTotal = Request.createCriteria()
			def total = cTotal {
				if(params.condiciones == 'on') {
					and {
						if(params.operacion && params.operacion != 'null') 'in'("operacion", opera)
						if(params.request) ilike("request", "%" + params.request.replaceAll(" ", "%") + "%")
						if(params.claveProceso) ilike("claveProceso", "%" + params.claveProceso + "%")
						if(params.fecha_procesado) between("fecha_procesado",params.fecha_procesado, params.fecha_procesadoFin + 1)
						if(params.descartado ==  'on') eq("descartado", descartado)
						if(params.company && params.company != 'null') 'in'("company", comp)
						if(params.fecha_creacion) between("fecha_creacion",params.fecha_creacion, params.fecha_creacionFin + 1)
						if(params.errores ==  'on') ne("errores", "")
						if(params.webservice && params.webservice != 'null' && webs?.operaciones[0]) 'in'("operacion", webs?.operaciones[0])
					}
				} else {
					or {
						if(params.operacion && params.operacion != 'null') 'in'("operacion", opera)
						if(params.request) ilike("request", "%" + params.request.replaceAll(" ", "%") + "%")
						if(params.claveProceso) ilike("claveProceso", "%" + params.claveProceso + "%")
						if(params.fecha_procesado) between("fecha_procesado",params.fecha_procesado, params.fecha_procesadoFin + 1)
						if(params.descartado ==  'on') eq("descartado", descartado)
						if(params.company && params.company != 'null') 'in'("company", comp)
						if(params.fecha_creacion) between("fecha_creacion",params.fecha_creacion, params.fecha_creacionFin + 1)
						if(params.errores ==  'on') ne("errores", "")
						if(params.webservice && params.webservice != 'null' && webs?.operaciones[0]) 'in'("operacion", webs?.operaciones[0])
					}
				}
			}

			def c = Request.createCriteria()
			def results = c {
				if(params.condiciones == 'on') {
					and {
						if(params.operacion && params.operacion != 'null') 'in'("operacion", opera)
						if(params.request) ilike("request", "%" + params.request.replaceAll(" ", "%") + "%")
						if(params.claveProceso) ilike("claveProceso", "%" + params.claveProceso + "%")
						if(params.fecha_procesado) between("fecha_procesado",params.fecha_procesado, params.fecha_procesadoFin + 1)
						if(params.descartado ==  'on') eq("descartado", descartado)
						if(params.company && params.company != 'null') 'in'("company", comp)
						if(params.fecha_creacion) between("fecha_creacion",params.fecha_creacion, params.fecha_creacionFin + 1)
						if(params.errores ==  'on') ne("errores", "")
						if(params.webservice && params.webservice != 'null' && webs?.operaciones[0]) 'in'("operacion", webs?.operaciones[0])
					}
				} else {
					or {
						if(params.operacion && params.operacion != 'null') 'in'("operacion", opera)
						if(params.request) ilike("request", "%" + params.request.replaceAll(" ", "%") + "%")
						if(params.claveProceso) ilike("claveProceso", "%" + params.claveProceso + "%")
						if(params.fecha_procesado) between("fecha_procesado",params.fecha_procesado, params.fecha_procesadoFin + 1)
						if(params.descartado ==  'on') eq("descartado", descartado)
						if(params.company && params.company != 'null') 'in'("company", comp)
						if(params.fecha_creacion) between("fecha_creacion",params.fecha_creacion, params.fecha_creacionFin + 1)
						if(params.errores ==  'on') ne("errores", "")
						if(params.webservice && params.webservice != 'null' && webs?.operaciones[0]) 'in'("operacion", webs?.operaciones[0])
					}
				}
				order(params.sort, params.order)
				if(params.max) {
					maxResults(params.max?.toInteger())
					firstResult(params.offset?.toInteger())
				} else {
					maxResults(session.max?.toInteger())
					firstResult(session.offset?.toInteger())
					params.max = session.max
					params.offset = session.offset
				}
			}

			[requestInstanceList: results, params: params, requestInstanceTotal: total.size()]
		} else {
			if(params.input == "Ver Todo") {

				session['operacion'] = ''
				session['claveProceso'] = ''
				session['request'] = ''
				session['descartado'] = ''
				session['fecha_procesado'] = ''
				session['fecha_procesadoFin'] = ''
				session['errores'] = ''
				session['condiciones'] = ''
				session['company'] = ''
				session['webservice'] = ''
				session['fecha_creacion'] = ''
				session['fecha_creacionFin'] = ''
				session['bus'] = ''

				session['sort'] = ''
				session['order'] = ''
				session['max'] = ''
				session['offset'] = ''

				redirect(action:"listbuscador")
			} else {

				[requestInstanceList: Request.list(params), requestInstanceTotal: Request.count(), params: params]
			}
		}
	}

	def salvarDescarte = {
		def requestInstance = Request.get(params.id)
		if(params.descartado=="on") {
			requestInstance.descartado = true
		}
		else {
			requestInstance.descartado = false
		}
		requestInstance.save()

		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
		redirect(action: show, id: params.id)
	}

	def guardarRequest = {
		def requestInstance = Request.get(params.id)

		def paso = true//validacionXmlService.validar(params.request, nombreXsd)

		if(paso) {
			requestInstance.request = params.request
			requestInstance.errores = null
			requestInstance.save()

			estadisticasService.borrar(params.id)
			estadisticasService.crear(requestInstance,requestInstance.operacion,params.request)
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
		}

		redirect(action: show, id: params.id)
	}


	def procesarRequest = {


		def requestXML
		def requestBBDD

		def requestInstance = Request.get(params.id)

		def cia = requestInstance.company

		switch (cia) {

			case "lagunaro":

				JAXBContext jaxbContext = JAXBContext.newInstance(com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
				com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

				def opername="GestionReconocimientoMedicoRequest"

				if (Company.findByNombre(requestInstance.company).generationAutomatic) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=requestService.marshall(gestionReconocimientoMedicoRequest,com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class)
					requestBBDD = requestService.crear(opername,requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					lagunaroService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				}

				break
			case "ama":

				JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
				com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

				def opername="AmaResultadoReconocimientoMedicoRequest"

				if (Company.findByNombre(requestInstance.company).generationAutomatic) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=amaService.marshall("http://www.scortelemed.com/schemas/ama",gestionReconocimientoMedicoRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					amaService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				}

				break
			case "caser":

				JAXBContext jaxbContext = JAXBContext.newInstance(GestionReconocimientoMedicoRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), GestionReconocimientoMedicoRequest.class);
				GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

				def opername="CaserResultadoReconocimientoMedicoRequest"

				if (Company.findByNombre(requestInstance.company).generationAutomatic) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=caserService.marshall("http://www.scortelemed.com/schemas/caser",gestionReconocimientoMedicoRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					caserService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				}

				break
			case "cajamar":

				JAXBContext jaxbContext = JAXBContext.newInstance(CajamarUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<CajamarUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), CajamarUnderwrittingCaseManagementRequest.class);
				CajamarUnderwrittingCaseManagementRequest cajamarUnderwrittingCaseManagementRequest = root.getValue()

				def opername="CajamarUnderwrittingCaseManagementRequest"

				if (Company.findByNombre(requestInstance.company).generationAutomatic && cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("1")) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=cajamarService.marshall("http://www.scortelemed.com/schemas/cajamar",cajamarUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear(opername,requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					crearExpedienteService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				} else if (!cajamarUnderwrittingCaseManagementRequest.getRegScor().getYtipo().toString().equals("1")){
					flash.error = "${message(code: 'default.invalid.type.operation.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}

				break
			case "afiesca":

				JAXBContext jaxbContext = JAXBContext.newInstance(AfiEscaUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());
				AfiEscaUnderwrittingCaseManagementRequest afiEscaUnderwrittingCaseManagementRequest = (AfiEscaUnderwrittingCaseManagementRequest) jaxbUnmarshaller.unmarshal(reader);

				if (Company.findByNombre(requestInstance.company).generationAutomatic && afiEscaUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML = requestService.marshall(afiEscaUnderwrittingCaseManagementRequest,AfiEscaUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear("AfiEscaUnderwrittingCaseManagementRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					crearExpedienteService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				} else if (!afiEscaUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){
					flash.error = "${message(code: 'default.invalid.type.operation.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}

				break
			case "alptis":

				JAXBContext jaxbContext = JAXBContext.newInstance(AlptisUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());
				AlptisUnderwrittingCaseManagementRequest alptisUnderwrittingCaseManagementRequest = (AlptisUnderwrittingCaseManagementRequest) jaxbUnmarshaller.unmarshal(reader);

				if (Company.findByNombre(requestInstance.company).generationAutomatic && alptisUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML = requestService.marshall(alptisUnderwrittingCaseManagementRequest,AlptisUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear("AlptisUnderwrittingCaseManagementRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					crearExpedienteService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				} else if (!alptisUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){
					flash.error = "${message(code: 'default.invalid.type.operation.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}

				break
			case "lifesquare":

				JAXBContext jaxbContext = JAXBContext.newInstance(LifesquareUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());
				LifesquareUnderwrittingCaseManagementRequest lifesquareUnderwrittingCaseManagementRequest = (LifesquareUnderwrittingCaseManagementRequest) jaxbUnmarshaller.unmarshal(reader);

				if (Company.findByNombre(requestInstance.company).generationAutomatic && lifesquareUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML = requestService.marshall(lifesquareUnderwrittingCaseManagementRequest,LifesquareUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear("LifesquareUnderwrittingCaseManagementRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					crearExpedienteService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				} else if (!lifesquareUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){
					flash.error = "${message(code: 'default.invalid.type.operation.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}

				break
			case "netinsurance":

				JAXBContext jaxbContext = JAXBContext.newInstance(NetinsuranteUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<NetinsuranteUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), NetinsuranteUnderwrittingCaseManagementRequest.class);
				NetinsuranteUnderwrittingCaseManagementRequest netinsuranteUnderwrittingCaseManagementRequest = root.getValue();

				if (Company.findByNombre(requestInstance.company).generationAutomatic) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=netinsuranceService.marshall("http://www.scortelemed.com/schemas/netinsurance",netinsuranteUnderwrittingCaseManagementRequest)
					requestBBDD = requestService.crear("NetinsuranceUnderwrittingCaseManagementRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					netinsuranceService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}
				break
			case "lifesquare":

				JAXBContext jaxbContext = JAXBContext.newInstance(LifesquareUnderwrittingCaseManagementRequest.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());
				LifesquareUnderwrittingCaseManagementRequest lifesquareUnderwrittingCaseManagementRequest = (LifesquareUnderwrittingCaseManagementRequest) jaxbUnmarshaller.unmarshal(reader);

				if (Company.findByNombre(requestInstance.company).generationAutomatic && lifesquareUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML = requestService.marshall(lifesquareUnderwrittingCaseManagementRequest,LifesquareUnderwrittingCaseManagementRequest.class)
					requestBBDD = requestService.crear("LifesquareUnderwrittingCaseManagementRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					crearExpedienteService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"

				} else if (!lifesquareUnderwrittingCaseManagementRequest.getRequest_Data().getRecord().getNombre().equals("A")){
					flash.error = "${message(code: 'default.invalid.type.operation.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}

				break
			case "enginyers":

				JAXBContext jaxbContext = JAXBContext.newInstance(AddExp.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(requestInstance.getRequest().trim());

				JAXBElement<AddExp> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), AddExp.class);
				AddExp addExp = root.getValue();

				if (Company.findByNombre(requestInstance.company).generationAutomatic) {
					def compania=Company.findByNombre(requestInstance.company)
					session.companyST=compania.codigoSt
					requestXML=enginyersService.marshall("http://www.scortelemed.com/schemas/enginyers",addExp)
					requestBBDD = requestService.crear("EnginyersResultadoReconocimientoMedicoRequest",requestXML)
					logginService.putInfoMessage("Se ha procesado una request namnualmente para: " + requestInstance.company)
					netinsuranceService.crearExpediente(requestBBDD)
					flash.message = "${message(code: 'default.processed.message', args: [message(code: 'request.label', default: 'Request'), requestInstance.id])}"
				}
				break
			default:
				break

		}

		redirect(action: show, id: params.id)
	}


	def webselect = {

		if(params.id) {

			session['company'] = params.id

			def companyElegida = Company.findByNombreLike(params.id);

			if(companyElegida) {
				def webserviceListado = companyElegida.usuarios.webservices.flatten().unique()

				['webserviceListado': webserviceListado]
			}
		}
	}

	def operationselect = {

		if(params.id) {

			session['operation'] = params.id

			def webserviceElegido = Webservice.findByClaveLike(params.id);
			if(webserviceElegido) {
				def operacionListado = webserviceElegido.operaciones.flatten().unique()

				['operacionListado': operacionListado]
			}
		}
	}


}

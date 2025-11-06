package com.scortelemed

import com.ws.servicios.IComprimidoService
import com.ws.servicios.ServiceFactory
import grails.util.Holders

/**
 * Authority Controller.
 */
class TestController {

	// the delete, save and update actions only accept POST requests
	static Map allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

	def authenticateService
	def tarificadorService
	
	def index = {
		redirect action: list, params: params
	}

	/**
	 * Display the list authority page.
	 */
	def list = {
		if (!params.max) {
			params.max = Holders.config.pag.maximo
		}
		[authorityList: Authority.list(params)]
	}

	/**
	 * Display the show authority page.
	 */
	def show = {
		def authority = Authority.get(params.id)
		if (!authority) {
			flash.message = "Rol no encontrado con id $params.id"
			redirect action: list
			return
		}

		[authority: authority]
	}

	/**
	 * Delete an authority.
	 */
	def delete = {
		def authority = Authority.get(params.id)
		if (!authority) {
			flash.message = "Rol no encontrado con id $params.id"
			redirect action: list
			return
		}

		authenticateService.deleteRole(authority)

		flash.message = "Rol $params.id borrar."
		redirect action: list
	}

	/**
	 * Display the edit authority page.
	 */
	def edit = {
		def authority = Authority.get(params.id)
		if (!authority) {
			flash.message = "Rol no encontrado con id $params.id"
			redirect action: list
			return
		}

		[authority: authority]
	}

	/**
	 * Authority update action.
	 */
	def update = {

		def authority = Authority.get(params.id)
		if (!authority) {
			flash.message = "Rol no encontrado con id $params.id"
			redirect action: edit, id: params.id
			return
		}

		long version = params.version.toLong()
		if (authority.version > version) {
			authority.errors.rejectValue 'version', 'authority.optimistic.locking.failure',
				'Otro usuario a actualizado este rol mientras estaba editando.'
			render view: 'edit', model: [authority: authority]
			return
		}

		if (authenticateService.updateRole(authority, params)) {
			//authenticateService.clearCachedRequestmaps()
			redirect action: show, id: authority.id
		}
		else {
			render view: 'edit', model: [authority: authority]
		}
	}

	/**
	 * Display the create new authority page.
	 */
	def create = {
		[authority: new Authority()]
	}

	/**
	 * Save a new authority.
	 */
	def save = {

		def authority = new Authority()
		authority.properties = params
		if (authority.save()) {
			redirect action: show, id: authority.id
		}
		else {
			render view: 'create', model: [authority: authority]
		}
	}
	
	def ws3() {
		def result = tarificadorService.obtenerNodoConsultaExpediente("00042255")
		
		render result.listaExpedientes[0].nodoAlfresco.toString()
	}
	
	def ws4() {
		IComprimidoService zipService = ServiceFactory.getComprimidoImpl(TipoCompany.SCOR)
		def result = zipService.obtenerZip("workspace://SpacesStore/c05f472f-45c6-4ca4-b521-79eb52ba7caf")
		String str = new String(result.datosRespuesta.content)
		
		render str
	}
}

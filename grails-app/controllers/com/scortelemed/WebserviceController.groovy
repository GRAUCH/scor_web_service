package com.scortelemed
import grails.util.Holders 

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class WebserviceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [webserviceInstanceList: Webservice.list(params), webserviceInstanceTotal: Webservice.count()]
    }

    def create = {
        def webserviceInstance = new Webservice()
        webserviceInstance.properties = params
        return [webserviceInstance: webserviceInstance]
    }

    def save = {
        def webserviceInstance = new Webservice(params)
        if (webserviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'webservice.label', default: 'Webservice'), webserviceInstance.id])}"
            redirect(action: "show", id: webserviceInstance.id)
        }
        else {
            render(view: "create", model: [webserviceInstance: webserviceInstance])
        }
    }

    def show = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
		
        def webserviceInstance = Webservice.get(params.id)
        if (!webserviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
            redirect(action: "list")
        }
        else {
			def operacionInstanceList = Operacion.findAllByWebservice(webserviceInstance, params)
			def total = Operacion.findAllByWebservice(webserviceInstance).size()
			
            [webserviceInstance: webserviceInstance, operacionInstanceList: operacionInstanceList, total: total]
        }
    }
	
	def operacion = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
			
		def webserviceInstance = Webservice.get(params.id)

		def operacionInstanceList = Operacion.findAllByWebservice(webserviceInstance, params)
		def total = Operacion.findAllByWebservice(webserviceInstance).size()
			
        [webserviceInstance: webserviceInstance, operacionInstanceList: operacionInstanceList, total: total]
	}

    def edit = {	
        def webserviceInstance = Webservice.get(params.id)
		
		def listaUsers = webserviceInstance.usuarios.id.toArray()
		def listaCompleta = Person.list()
		
		def listaFinal = [:]
		listaCompleta.each {
			if(it.id in listaUsers) listaFinal.put(it, true)
			else listaFinal.put(it, false)
		}
        if (!webserviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [webserviceInstance: webserviceInstance, listaFinal: listaFinal]
        }
    }

    def update = {
		def listadoUsuarios = []
		def tam
		params.each {
			if(it.key.contains("US_") && it.value == "on") {
				tam = it.key.size() - 1 
				listadoUsuarios.add(it.key[3..tam])
			}
		}		
		def listadoObjUsuarios
		if(listadoUsuarios) listadoObjUsuarios = Person.getAll(listadoUsuarios)
		
        def webserviceInstance = Webservice.get(params.id)
        if (webserviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (webserviceInstance.version > version) {
                    
                    webserviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'webservice.label', default: 'Webservice')] as Object[], "Another user has updated this Webservice while you were editing")
                    render(view: "edit", model: [webserviceInstance: webserviceInstance])
                    return
                }
            }
            webserviceInstance.properties = params
			webserviceInstance.usuarios = listadoObjUsuarios
            if (!webserviceInstance.hasErrors() && webserviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'webservice.label', default: 'Webservice'), webserviceInstance.id])}"
                redirect(action: "show", id: webserviceInstance.id)
            }
            else {
                render(view: "edit", model: [webserviceInstance: webserviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def webserviceInstance = Webservice.get(params.id)
        if (webserviceInstance) {
            try {
                webserviceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webservice.label', default: 'Webservice'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def updateRelacion = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
		
		def webserviceInstance = Webservice.get(params.id)

		def operacionInstanceList = Operacion.findAllByWebservice(webserviceInstance, params)
		def total = Operacion.findAllByWebservice(webserviceInstance).size()

		render(view: "operacion", model: [webserviceInstance: webserviceInstance, operacionInstanceList: operacionInstanceList, total: total])
	}
}

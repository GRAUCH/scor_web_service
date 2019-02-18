package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class OperacionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [operacionInstanceList: Operacion.list(params), operacionInstanceTotal: Operacion.count()]
    }

    def create = {
        def operacionInstance = new Operacion()
        operacionInstance.properties = params
        return [operacionInstance: operacionInstance]
    }

    def save = {
        def operacionInstance = new Operacion(params)
        if (operacionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'operacion.label', default: 'Operacion'), operacionInstance.id])}"
            redirect(action: "show", id: operacionInstance.id)
        }
        else {
            render(view: "create", model: [operacionInstance: operacionInstance])
        }
    }

    def show = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
		
        def operacionInstance = Operacion.get(params.id)
		
        if (!operacionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
            redirect(action: "list")
        }
        else {
			def datowebserviceInstanceList = Datowebservice.findAllByOperacion(operacionInstance, params)
			def total = Datowebservice.findAllByOperacion(operacionInstance).size()
			
			[operacionInstance: operacionInstance, datowebserviceInstanceList: datowebserviceInstanceList, total: total]
        }
    }
	
	def datowebservice = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
			
		def operacionInstance = Operacion.get(params.id)

		def datowebserviceInstanceList = Datowebservice.findAllByOperacion(operacionInstance, params)
		def total = Datowebservice.findAllByOperacion(operacionInstance).size()
			
		[operacionInstance: operacionInstance, datowebserviceInstanceList: datowebserviceInstanceList, total: total]

	}

    def edit = {
        def operacionInstance = Operacion.get(params.id)
        if (!operacionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [operacionInstance: operacionInstance]
        }
    }

    def update = {
        def operacionInstance = Operacion.get(params.id)
        if (operacionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (operacionInstance.version > version) {
                    
                    operacionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'operacion.label', default: 'Operacion')] as Object[], "Another user has updated this Operacion while you were editing")
                    render(view: "edit", model: [operacionInstance: operacionInstance])
                    return
                }
            }
            operacionInstance.properties = params
            if (!operacionInstance.hasErrors() && operacionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'operacion.label', default: 'Operacion'), operacionInstance.id])}"
                redirect(action: "show", id: operacionInstance.id)
            }
            else {
                render(view: "edit", model: [operacionInstance: operacionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def operacionInstance = Operacion.get(params.id)
        if (operacionInstance) {
            try {
                operacionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'operacion.label', default: 'Operacion'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def updateRelacion = {
		params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "id"
		params.order = params?.order ?: "asc"
		
		def operacionInstance = Operacion.get(params.id)
		
		def datowebserviceInstanceList = Datowebservice.findAllByOperacion(operacionInstance, params)
		def total = Datowebservice.findAllByOperacion(operacionInstance).size()

		render(view: "datowebservice", model: [operacionInstance: operacionInstance, datowebserviceInstanceList: datowebserviceInstanceList, total: total])
	}
}

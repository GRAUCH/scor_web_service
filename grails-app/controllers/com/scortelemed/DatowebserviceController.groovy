package com.scortelemed
import grails.util.Holders 
import org.hibernate.FetchMode as FM
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class DatowebserviceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
	
		
		def listDatos = Datowebservice.findAll(
				"FROM Datowebservice d, " +
				"Operacion o INNER JOIN d.operacion ope, " +
				"Webservice w INNER JOIN o.webservice we"
				)
		
        [datowebserviceInstanceList: Datowebservice.list(params), datowebserviceInstanceTotal: Datowebservice.count(), listDatos: listDatos]
    }

    def create = {
        def datowebserviceInstance = new Datowebservice()
        datowebserviceInstance.properties = params
        return [datowebserviceInstance: datowebserviceInstance]
    }

    def save = {
        def datowebserviceInstance = new Datowebservice(params)
        if (datowebserviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), datowebserviceInstance.id])}"
            redirect(action: "show", id: datowebserviceInstance.id)
        }
        else {
            render(view: "create", model: [datowebserviceInstance: datowebserviceInstance])
        }
    }

    def show = {
        def datowebserviceInstance = Datowebservice.get(params.id)

        if (!datowebserviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
            redirect(action: "list")
        }
        else {
            [datowebserviceInstance: datowebserviceInstance]
        }
    }

    def edit = {
        def datowebserviceInstance = Datowebservice.get(params.id)
        if (!datowebserviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [datowebserviceInstance: datowebserviceInstance]
        }
    }

    def update = {
        def datowebserviceInstance = Datowebservice.get(params.id)
        if (datowebserviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (datowebserviceInstance.version > version) {
                    
                    datowebserviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'datowebservice.label', default: 'Datowebservice')] as Object[], "Another user has updated this Datowebservice while you were editing")
                    render(view: "edit", model: [datowebserviceInstance: datowebserviceInstance])
                    return
                }
            }
            datowebserviceInstance.properties = params
            if (!datowebserviceInstance.hasErrors() && datowebserviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), datowebserviceInstance.id])}"
                redirect(action: "show", id: datowebserviceInstance.id)
            }
            else {
                render(view: "edit", model: [datowebserviceInstance: datowebserviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def datowebserviceInstance = Datowebservice.get(params.id)
        if (datowebserviceInstance) {
            try {
                datowebserviceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'datowebservice.label', default: 'Datowebservice'), params.id])}"
            redirect(action: "list")
        }
    }
}

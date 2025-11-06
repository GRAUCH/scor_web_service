package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class DestinatarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [destinatarioInstanceList: Destinatario.list(params), destinatarioInstanceTotal: Destinatario.count()]
    }

    def create = {
        def destinatarioInstance = new Destinatario()
        destinatarioInstance.properties = params
        return [destinatarioInstance: destinatarioInstance]
    }

    def save = {
        def destinatarioInstance = new Destinatario(params)
        if (destinatarioInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), destinatarioInstance.id])}"
            redirect(action: "show", id: destinatarioInstance.id)
        }
        else {
            render(view: "create", model: [destinatarioInstance: destinatarioInstance])
        }
    }

    def show = {
        def destinatarioInstance = Destinatario.get(params.id)
        if (!destinatarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
            redirect(action: "list")
        }
        else {
            [destinatarioInstance: destinatarioInstance]
        }
    }

    def edit = {
        def destinatarioInstance = Destinatario.get(params.id)
        if (!destinatarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [destinatarioInstance: destinatarioInstance]
        }
    }

    def update = {
        def destinatarioInstance = Destinatario.get(params.id)
        if (destinatarioInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (destinatarioInstance.version > version) {
                    
                    destinatarioInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'destinatario.label', default: 'Destinatario')] as Object[], "Another user has updated this Destinatario while you were editing")
                    render(view: "edit", model: [destinatarioInstance: destinatarioInstance])
                    return
                }
            }
            destinatarioInstance.properties = params
            if (!destinatarioInstance.hasErrors() && destinatarioInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), destinatarioInstance.id])}"
                redirect(action: "show", id: destinatarioInstance.id)
            }
            else {
                render(view: "edit", model: [destinatarioInstance: destinatarioInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {		
		def destinatarioInstance = Destinatario.get(params.id)
		
		// hack para borrar relaciones
		if(destinatarioInstance.avisos) {
			def tmp=[]
			destinatarioInstance.avisos.each { tmp << it }
			tmp.each { destinatarioInstance.removeFromAvisos(it) }
		}

        if (destinatarioInstance) {
            try {
                destinatarioInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'destinatario.label', default: 'Destinatario'), params.id])}"
            redirect(action: "list")
        }
    }
}

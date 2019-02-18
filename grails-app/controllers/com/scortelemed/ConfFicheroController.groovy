package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ConfFicheroController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [confFicheroInstanceList: ConfFichero.list(params), confFicheroInstanceTotal: ConfFichero.count()]
    }

    def create = {
        def confFicheroInstance = new ConfFichero()
        confFicheroInstance.properties = params
        return [confFicheroInstance: confFicheroInstance]
    }

    def save = {
        def confFicheroInstance = new ConfFichero(params)
        if (confFicheroInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), confFicheroInstance.id])}"
            redirect(action: "show", id: confFicheroInstance.id)
        }
        else {
            render(view: "create", model: [confFicheroInstance: confFicheroInstance])
        }
    }

    def show = {
        def confFicheroInstance = ConfFichero.get(params.id)
        if (!confFicheroInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
            redirect(action: "list")
        }
        else {
            [confFicheroInstance: confFicheroInstance]
        }
    }

    def edit = {
        def confFicheroInstance = ConfFichero.get(params.id)
        if (!confFicheroInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [confFicheroInstance: confFicheroInstance]
        }
    }

    def update = {
        def confFicheroInstance = ConfFichero.get(params.id)
        if (confFicheroInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (confFicheroInstance.version > version) {
                    
                    confFicheroInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'confFichero.label', default: 'ConfFichero')] as Object[], "Another user has updated this ConfFichero while you were editing")
                    render(view: "edit", model: [confFicheroInstance: confFicheroInstance])
                    return
                }
            }
            confFicheroInstance.properties = params
            if (!confFicheroInstance.hasErrors() && confFicheroInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), confFicheroInstance.id])}"
                redirect(action: "show", id: confFicheroInstance.id)
            }
            else {
                render(view: "edit", model: [confFicheroInstance: confFicheroInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def confFicheroInstance = ConfFichero.get(params.id)
        if (confFicheroInstance) {
            try {
                confFicheroInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'confFichero.label', default: 'ConfFichero'), params.id])}"
            redirect(action: "list")
        }
    }
}

package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class IpcontrolController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [ipcontrolInstanceList: Ipcontrol.list(params), ipcontrolInstanceTotal: Ipcontrol.count()]
    }

    def create = {
        def ipcontrolInstance = new Ipcontrol()
        ipcontrolInstance.properties = params
        return [ipcontrolInstance: ipcontrolInstance]
    }

    def save = {
        def ipcontrolInstance = new Ipcontrol(params)
        if (ipcontrolInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), ipcontrolInstance.id])}"
            redirect(action: "show", id: ipcontrolInstance.id)
        }
        else {
            render(view: "create", model: [ipcontrolInstance: ipcontrolInstance])
        }
    }

    def show = {
        def ipcontrolInstance = Ipcontrol.get(params.id)
        if (!ipcontrolInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
            redirect(action: "list")
        }
        else {
            [ipcontrolInstance: ipcontrolInstance]
        }
    }

    def edit = {
        def ipcontrolInstance = Ipcontrol.get(params.id)
        if (!ipcontrolInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [ipcontrolInstance: ipcontrolInstance]
        }
    }

    def update = {
        def ipcontrolInstance = Ipcontrol.get(params.id)
        if (ipcontrolInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (ipcontrolInstance.version > version) {
                    
                    ipcontrolInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'ipcontrol.label', default: 'Ipcontrol')] as Object[], "Another user has updated this Ipcontrol while you were editing")
                    render(view: "edit", model: [ipcontrolInstance: ipcontrolInstance])
                    return
                }
            }
            ipcontrolInstance.properties = params
            if (!ipcontrolInstance.hasErrors() && ipcontrolInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), ipcontrolInstance.id])}"
                redirect(action: "show", id: ipcontrolInstance.id)
            }
            else {
                render(view: "edit", model: [ipcontrolInstance: ipcontrolInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ipcontrolInstance = Ipcontrol.get(params.id)
        if (ipcontrolInstance) {
            try {
                ipcontrolInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'ipcontrol.label', default: 'Ipcontrol'), params.id])}"
            redirect(action: "list")
        }
    }
}

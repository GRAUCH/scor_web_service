package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class PlantillaController {
	
	def avisosService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [plantillaInstanceList: Plantilla.list(params), plantillaInstanceTotal: Plantilla.count()]
    }

    def create = {
        def plantillaInstance = new Plantilla()
        plantillaInstance.properties = params
        return [plantillaInstance: plantillaInstance]
    }

    def save = {
        def plantillaInstance = new Plantilla(params)
        if (plantillaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), plantillaInstance.id])}"
            redirect(action: "show", id: plantillaInstance.id)
        }
        else {
            render(view: "create", model: [plantillaInstance: plantillaInstance])
        }
    }

    def show = {
        def plantillaInstance = Plantilla.get(params.id)
        if (!plantillaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
            redirect(action: "list")
        }
        else {
            [plantillaInstance: plantillaInstance]
        }
    }

    def edit = {
        def plantillaInstance = Plantilla.get(params.id)
        if (!plantillaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [plantillaInstance: plantillaInstance]
        }
    }

    def update = {
        def plantillaInstance = Plantilla.get(params.id)
        if (plantillaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (plantillaInstance.version > version) {
                    
                    plantillaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'plantilla.label', default: 'Plantilla')] as Object[], "Another user has updated this Plantilla while you were editing")
                    render(view: "edit", model: [plantillaInstance: plantillaInstance])
                    return
                }
            }
            plantillaInstance.properties = params
            if (!plantillaInstance.hasErrors() && plantillaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), plantillaInstance.id])}"
                redirect(action: "show", id: plantillaInstance.id)
            }
            else {
                render(view: "edit", model: [plantillaInstance: plantillaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def plantillaInstance = Plantilla.get(params.id)
        if (plantillaInstance) {
            try {
                plantillaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'plantilla.label', default: 'Plantilla'), params.id])}"
            redirect(action: "list")
        }
    }
	
	
	def ayudante = {
		if(params.plan){
			def webs = params.plan
			def variables = avisosService.generarAyuda(webs[0])
			[variables: variables]
		}
	}
	
	def ayuda = {
		[ayuda: params.ayuda]
	}
}

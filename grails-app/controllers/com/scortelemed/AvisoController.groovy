package com.scortelemed
import grails.util.Holders 
import org.apache.axis2.transport.http.HttpTransportProperties;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class AvisoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : Holders.config.pag.maximo, Holders.config.pag.maximo)
        [avisoInstanceList: Aviso.list(params), avisoInstanceTotal: Aviso.count()]
		
    }

    def create = {
        def avisoInstance = new Aviso()
        avisoInstance.properties = params
        return [avisoInstance: avisoInstance]
    }

    def save = {
        def avisoInstance = new Aviso(params)
        if (avisoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'aviso.label', default: 'Aviso'), avisoInstance.id])}"
            redirect(action: "show", id: avisoInstance.id)
        }
        else {
            render(view: "create", model: [avisoInstance: avisoInstance])
        }
    }

    def show = {
        def avisoInstance = Aviso.get(params.id)
        if (!avisoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
            redirect(action: "list")
        }
        else {
            [avisoInstance: avisoInstance]
        }
    }

    def edit = {
        def avisoInstance = Aviso.get(params.id)
		
		
		def listaDestinatario = avisoInstance.destinatarios.id.toArray()
		def listaCompleta = Destinatario.list()
		
		def listaFinal = [:]
		listaCompleta.each {
			if(it.id in listaDestinatario) listaFinal.put(it, true)
			else listaFinal.put(it, false)
		}

        if (!avisoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [avisoInstance: avisoInstance, listaFinal: listaFinal]
        }
    }

    def update = {
		def listadoDestinos = []
		def tam
		params.each {
			if(it.key.contains("DE_") && it.value == "on") {
				tam = it.key.size() - 1
				listadoDestinos.add(it.key[3..tam])
			}
		}
		def listadoObjDes
		if(listadoDestinos) listadoObjDes= Person.getAll(listadoDestinos)
		
        def avisoInstance = Aviso.get(params.id)
        if (avisoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (avisoInstance.version > version) {
                    
                    avisoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'aviso.label', default: 'Aviso')] as Object[], "Another user has updated this Aviso while you were editing")
                    render(view: "edit", model: [avisoInstance: avisoInstance])
                    return
                }
            }
            avisoInstance.properties = params
			avisoInstance.destinatarios = listadoObjDes
            if (!avisoInstance.hasErrors() && avisoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'aviso.label', default: 'Aviso'), avisoInstance.id])}"
				redirect(action: "show", id: avisoInstance.id)
            }
            else {
                render(view: "edit", model: [avisoInstance: avisoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def avisoInstance = Aviso.get(params.id)
        if (avisoInstance) {
            try {
                avisoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'aviso.label', default: 'Aviso'), params.id])}"
            redirect(action: "list")
        }
    }
}

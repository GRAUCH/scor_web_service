package com.scortelemed

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class AgenteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond Agente.list(params), model:[agenteInstanceCount: Agente.count()]
    }

    def show(Agente agenteInstance) {
        respond agenteInstance
    }

    def create() {
        respond new Agente(params)
    }

    @Transactional
    def save(Agente agenteInstance) {
        if (agenteInstance == null) {
            notFound()
            return
        }

        if (agenteInstance.hasErrors()) {
            respond agenteInstance.errors, view:'create'
            return
        }

        agenteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'agente.label', default: 'Agente'), agenteInstance.id])
                redirect agenteInstance
            }
            '*' { respond agenteInstance, [status: CREATED] }
        }
    }

    def edit(Agente agenteInstance) {
        respond agenteInstance
    }

    @Transactional
    def update(Agente agenteInstance) {
        if (agenteInstance == null) {
            notFound()
            return
        }

        if (agenteInstance.hasErrors()) {
            respond agenteInstance.errors, view:'edit'
            return
        }

        agenteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Agente.label', default: 'Agente'), agenteInstance.id])
                redirect agenteInstance
            }
            '*'{ respond agenteInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Agente agenteInstance) {

        if (agenteInstance == null) {
            notFound()
            return
        }

        agenteInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Agente.label', default: 'Agente'), agenteInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'agente.label', default: 'Agente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

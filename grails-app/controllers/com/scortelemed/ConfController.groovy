package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ConfController {

    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : Holders.config.pag.maximo,  Holders.config.pag.maximo)
        [ confInstanceList: Conf.list( params ), confInstanceTotal: Conf.count() ]
    }

    def show = {
        def confInstance = Conf.get( params.id )

        if(!confInstance) {
        	flash.message = g.message(code:'conf.notfound', args: [params.id])
            redirect(action:list)
        }
        else { return [ confInstance : confInstance ] }
    }

    def delete = {
        def confInstance = Conf.get( params.id )
        if(confInstance) {
            try {
                confInstance.delete(flush:true)
                flash.message = g.message(code:'conf.deleted', args: [params.id])
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'conf.label', default: 'Conf'), params.id])}"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
            	flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'conf.label', default: 'Conf'), params.id])}"
                redirect(action:show,id:params.id)
            }
        }
        else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'conf.label', default: 'Conf'), params.id])}"
            redirect(action:list)
        }
    }

    def edit = {
        def confInstance = Conf.get( params.id )

        if(!confInstance) {
        	flash.message = g.message(code:'conf.notfound', args: [params.id])
            redirect(action:list)
        }
        else {
            return [ confInstance : confInstance ]
        }
    }

    def update = {
        def confInstance = Conf.get( params.id )
        if(confInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(confInstance.version > version) {
                    
                    confInstance.errors.rejectValue("version", "conf.optimistic.locking.failure", "g.message(code:'conf.notupdated')")
                    render(view:'edit',model:[confInstance:confInstance])
                    return
                }
            }
			if(params.value) {
	            confInstance.properties = params
	            if(!confInstance.hasErrors() && confInstance.save()) {
	                flash.message = g.message(code:'conf.update', args: [params.id])
	                redirect(action:show,id:confInstance.id)
	            }
	            else {
	                render(view:'edit',model:[confInstance:confInstance])
	            }
			}
			else {
				flash.message = g.message(code:'conf.notblank')
                render(view:'edit',model:[confInstance:confInstance])
                return
			}
			
        }
        else {
        	flash.message = g.message(code:'conf.notfound', args: [params.id])
            redirect(action:list)
        }
    }

    def create = {
        def confInstance = new Conf()
        confInstance.properties = params
        return ['confInstance':confInstance]
    }

    def save = {
        def confInstance = new Conf(params)
        if(!confInstance.hasErrors() && confInstance.save()) {
        	flash.message = g.message(code:'conf.created', args: [confInstance.id])
            redirect(action:show,id:confInstance.id)
        }
        else {
            render(view:'create',model:[confInstance:confInstance])
        }
    }

}

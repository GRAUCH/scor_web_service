package com.scortelemed
import grails.util.Holders 
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class EstadisticaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		if (!params.max) {
			params.max = Holders.config.pag.maximo
			session.max = Holders.config.pag.maximo
			session.offset = 0
		}
        [estadisticaInstanceList: Estadistica.list(params), estadisticaInstanceTotal: Estadistica.count()]
    }

    def create = {
        def estadisticaInstance = new Estadistica()
        estadisticaInstance.properties = params
        return [estadisticaInstance: estadisticaInstance]
    }

    def save = {
        def estadisticaInstance = new Estadistica(params)
        if (estadisticaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), estadisticaInstance.id])}"
            redirect(action: "show", id: estadisticaInstance.id)
        }
        else {
            render(view: "create", model: [estadisticaInstance: estadisticaInstance])
        }
    }

    def show = {
        def estadisticaInstance = Estadistica.get(params.id)
        if (!estadisticaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
            redirect(action: "list")
        }
        else {
            [estadisticaInstance: estadisticaInstance]
        }
    }

    def edit = {
        def estadisticaInstance = Estadistica.get(params.id)
        if (!estadisticaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [estadisticaInstance: estadisticaInstance]
        }
    }

    def update = {
        def estadisticaInstance = Estadistica.get(params.id)
        if (estadisticaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (estadisticaInstance.version > version) {
                    
                    estadisticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'estadistica.label', default: 'Estadistica')] as Object[], "Another user has updated this Estadistica while you were editing")
                    render(view: "edit", model: [estadisticaInstance: estadisticaInstance])
                    return
                }
            }
            estadisticaInstance.properties = params
            if (!estadisticaInstance.hasErrors() && estadisticaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), estadisticaInstance.id])}"
                redirect(action: "show", id: estadisticaInstance.id)
            }
            else {
                render(view: "edit", model: [estadisticaInstance: estadisticaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def estadisticaInstance = Estadistica.get(params.id)
        if (estadisticaInstance) {
            try {
                estadisticaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'estadistica.label', default: 'Estadistica'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def listbuscador = {
		if(params.input == 'Buscar' || params.max) {
			def opera
			def req
			if(params.operacion){
				opera = Operacion.findAllByClaveLike("%" + params.operacion.replaceAll(" ", "%") + "%")
			}
			if(params.claveProcesoRequest){
				req = Request.findAllByClaveProcesoLike("%" + params.claveProcesoRequest.replaceAll(" ", "%") + "%")
			}
			
			def cTotal = Estadistica.createCriteria()
			def total = cTotal {
					if(params.condiciones == 'on') {
						and {
							if(params.claveProcesoRequest) 'in'("request", req)
							if(params.clave) like("clave", "%" + params.clave.replaceAll(" ", "%") + "%")
							if(params.valor) like("value", "%" + params.valor.replaceAll(" ", "%") + "%")
							if(params.operacion) 'in'("operacion", opera)
							if(params.fecha_procesado) between("fecha_alta",params.fecha_procesado, params.fecha_procesadoFin + 1)
						}
					} else {
						or {
							if(params.claveProcesoRequest) 'in'("request", req)
							if(params.clave) like("clave", "%" + params.clave.replaceAll(" ", "%") + "%")
							if(params.valor) like("value", "%" + params.valor.replaceAll(" ", "%") + "%")
							if(params.operacion) 'in'("operacion", opera)
							if(params.fecha_procesado) between("fecha_alta",params.fecha_procesado, params.fecha_procesadoFin + 1)
						}
					}
			}
					
			def c = Estadistica.createCriteria()
			def results = c {
					if(params.condiciones == 'on') {
						and {
							if(params.claveProcesoRequest) 'in'("request", req)
							if(params.clave) like("clave", "%" + params.clave.replaceAll(" ", "%") + "%")
							if(params.valor) like("value", "%" + params.valor.replaceAll(" ", "%") + "%")
							if(params.operacion) 'in'("operacion", opera)
							if(params.fecha_procesado) between("fecha_alta",params.fecha_procesado, params.fecha_procesadoFin + 1)
						}
					} else {
						or {
							if(params.claveProcesoRequest) 'in'("request", req)
							if(params.clave) like("clave", "%" + params.clave.replaceAll(" ", "%") + "%")
							if(params.valor) like("value", "%" + params.valor.replaceAll(" ", "%") + "%")
							if(params.operacion) 'in'("operacion", opera)
							if(params.fecha_procesado) between("fecha_alta",params.fecha_procesado, params.fecha_procesadoFin + 1)
						}
					}
				order("id", "asc")
				if(params.max) {
					maxResults(params.max?.toInteger())
					firstResult(params.offset?.toInteger())
				} else {
					maxResults(session.max?.toInteger())
					firstResult(session.offset?.toInteger())
					params.max = session.max
					params.offset = session.offset
				}
			}
			[estadisticaInstanceList: results, params: params, estadisticaInstanceTotal: total.size()]
		} else {
			redirect action: list
		}
		
}
}

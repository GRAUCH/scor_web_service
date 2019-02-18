package com.scortelemed
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders
import hwsol.webservices.CorreoUtil

@Secured(['ROLE_ADMIN'])
class GeneracionFicheroController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def recepcionFicheroLagunaroService
	def ficheroCajamarService
	def ficheroService
	def logginService
    
    def index = {
    	redirect(action: "list", params: params)
    }

    def list = {
    	params.max = Math.min(params?.max?.toInteger() ?: Holders.config.pag.maximo, Holders.config.pag.maximo)
        params.offset = params?.offset?.toInteger() ?: 0
        params.sort = params?.sort ?: "id"
        params.order = params?.order ?: "asc"

		// sacas webservices -> sacas sus operaciones -> REQUEST de esas operaciones
		def geneRequest, geneRequestTotal
		
		if (params.company && params.company!="null"){
			def compa = Company.get(params.company)
			
			/*
			 * FIXME: Ahora esta HARDCORE esta se soluciona en la siguiente revision!!!
			 */
			def claveOperacion
			
			if(compa){
				claveOperacion=obternerClaveOPeracion(compa.nombre)
				
				def operacion = Operacion.findByClave(claveOperacion)

				if(operacion){
					geneRequest = Request.createCriteria().list {
						eq("company", compa)
						isNull("fecha_procesado")
						eq("descartado", false)
						order("id", "asc")
						eq("operacion", operacion)
						maxResults(params.max?.toInteger())
						firstResult(params.offset?.toInteger())
					}
	
					geneRequestTotal = Request.findAll("from Request where fecha_procesado is null and descartado=? and company =? and operacion=?", [false, compa, operacion]).size()
				}
			}
		}

		[geneRequest: geneRequest, geneRequestTotal: geneRequestTotal, company: params.company]
    }
    
    def generar () {
		try{
			if(params.company) {			
				def result = generarPorCompania(params.company.toInteger(), true)
				if(result!="null"){
					return result
				}else{
					throw new Exception("No existe la compañia");
				}
			} else {
				redirect(action: "list", params: params)
			}
		}catch (Exception e){
			redirect(action: "list", params: params)
		}
    }
	
	@Secured('permitAll')
	def createFiles () {
		try{
			CorreoUtil correoUtil = new CorreoUtil()
			
			def company = Company.findById(params.id.toInteger())
			
			if (company) {
				def result = generarPorCompania(company.id.toInteger(), false)
				session.companyST = company.codigoSt

				if (result.equals("OK")) {
					correoUtil.envioEmail(company.nombre, "Se han generado los ficheros correctamente par la compañia " + company.nombre, 0)
				} else if (!result.equals("null") && result) {
					correoUtil.envioEmail(company.nombre, "Se han generado los ficheros correctamente par la compañia " + company.nombre, result)
				}
			} else {
				render "No existe la compañia con id : " + params.id
			}
		}catch (Exception e){
			logginService.putErrorMessage ("No se ha podido generar el fichero: " + e)
			redirect(action: "list", params: params)
		}
	}
	
	//http://localhost:8080/webservicessoap/generacionFichero/generarPorCompania/6
	@Secured('permitAll')
	def generarPorCompania (Integer idCompania, boolean isWeb) {		
		try{
			def result = "null"
			def compa
			def ahora = new Date().time	
			if(params.id){
				compa = Company.get(params.id.toInteger())
			}else{
				compa = Company.get(idCompania)
			}
	
			/*
			 * FIXME: Ahora esta HARDCORE esta se soluciona en la siguiente revision!!!
			 */
			if(compa){
				def claveOperacion=obternerClaveOPeracion(compa.nombre)
				def operacion = Operacion.findByClave(claveOperacion)
				
				def c = Request.createCriteria()
				def geneRequest = c {
					eq("company", compa)
					isNull("fecha_procesado")
					eq("descartado", false)
					eq("operacion", operacion)
				}
				
				if(geneRequest) {
					def datos = [:]
					def tiempo = new Date().time - ahora
					def datosGuardar
					
					if(claveOperacion.equals("GestionReconocimientoMedicoRequest")){
						geneRequest.each { datos.put(it.claveProceso, recepcionFicheroLagunaroService.preparar(it.claveProceso)) }
						
						// Aqui se guarda !!!
						datosGuardar = recepcionFicheroLagunaroService.guardarDatos(datos)
						
						// Aqui se procesan los que no tienen fallo !!!
						if(datosGuardar[2]) {
							datos.each { if(!it.value[2]) { recepcionFicheroLagunaroService.procesarPeticion(it.key) } }
						}
					} else if (claveOperacion.equals("CajamarUnderwrittingCaseManagementRequest")) {
						geneRequest.each { datos.put(it.claveProceso, ficheroCajamarService.preparar(it.claveProceso)) }
						
						datosGuardar = ficheroService.guardarDatos(datos, compa.nombre.substring(0, 4).toUpperCase())
					} else {
						geneRequest.each { datos.put(it.claveProceso, ficheroService.preparar(it.claveProceso)) }
						// Aqui se guarda el fichero en la máquina
						datosGuardar = ficheroService.guardarDatos(datos, compa.nombre.substring(0, 4).toUpperCase())
					}
					
					if (!params.id && isWeb) {
						return [datos: datosGuardar[0], tiempo: tiempo, errores:datosGuardar[1], conseguido: datosGuardar[2]]
					} else if (!isWeb && datosGuardar[1]) {
						return datosGuardar[1].toString()
					} else {
						return "OK"
					}
				}
			} 
			
		}catch (Exception e){
			return "Se ha producido el siguiente error: "+e
		}
	}
	
	def obternerClaveOPeracion = { nombre ->
		def result=""
		
		switch(nombre.toUpperCase()) {
			case "AFIESCA":
				result="AfiEscaUnderwrittingCaseManagementRequest"
				break;
			case "ALPTIS":
				result="AlptisUnderwrittingCaseManagementRequest"
				break;
			case "LAGUNARO":
				result="GestionReconocimientoMedicoRequest"
				break;		
			case "LIFESQUARE":
				result="LifesquareUnderwrittingCaseManagementRequest"
				break;
			case "CAJAMAR":
				result="CajamarUnderwrittingCaseManagementRequest"
				break;
		}
		
		return result
	}
}
package com.ws.servicios

import com.scortelemed.Estadistica
import com.scortelemed.Operacion
import com.scortelemed.Request
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest

class EstadisticasService {

	def logginService = new LogginService()
	/*
	 * Crea una estadistica de la request que se le pase como parametro:
	 * request puede ser un objeto tipo Request o un mapa con los siguiente atributos: 
	 * operacion, request y claveProceso.
	 * Devuelve un mapa con claveProceso y operacion.clave
	 */
	
	def crear(request,operacion,requestXML){
		def salida = [:]
		
		if (operacion){
			def datos = obtenerDatos(operacion.clave.toString(),requestXML.toString())
			def estadistica
			
			datos.each { key, value ->
				estadistica = new Estadistica()
				estadistica.operacion = operacion
				estadistica.request = request
				estadistica.clave = key
				estadistica.value = value
				estadistica.save()
			}

			salida.put('claveProceso', request.claveProceso)
			salida.put('operacion', operacion)
		}
				
		return salida
	}
		
	/*
	 * Borra las estadisticas de una Requests pasandole el ID
	 */
	def borrar(idReq){
		def req = Request.get(idReq)
		def estadisticas = Estadistica.findAllByRequest(req)
		estadisticas.each {
			it.delete()
		}
	}

	/*
	 * Obtiene las claves definidas en la tabla Datowebservice
	 */
	def obtenerClaves(opera){
		def operacion = Operacion.findByClave(opera)
		def claves = Datowebservice.findAllByOperacion(operacion)
		
	}
	
	/*
	 * Obtiene las datos directamente de un mensaje dato
	 */
	def obtenerDatos(String operacion,Object requestXML) {
		def mapaEsta = [:]	
		def rootNode = new XmlSlurper().parseText(requestXML)
		
		operacion=operacion.split("#")[0]
		if (operacion.equals("AfiEscaUnderwrittingCaseManagementRequest") || operacion.equals("LifesquareUnderwrittingCaseManagementRequest")){
			reAfiescaImpl(rootNode, mapaEsta)
		} else if(operacion.equals("AlptisUnderwrittingCaseManagementRequest")){
			reAlptisImpl(rootNode, mapaEsta)
		} else if(operacion.equals("GestionReconocimientoMedicoRequest")){
			reLagunaroImpl(rootNode, mapaEsta)
		} else if (operacion.equals("CajamarUnderwrittingCaseManagementRequest")){
			reCajamarImpl(rootNode, mapaEsta)
		} else if (operacion.equals("AmaResultadoReconocimientoMedicoRequest")){
			reAmaImpl(rootNode, mapaEsta)
		} else if (operacion.equals("CaserResultadoReconocimientoMedicoRequest")){
			reCaserImpl(rootNode, mapaEsta)
		} else if (operacion.equals("PsnResultadoReconocimientoMedicoRequest")){
			rePsnImpl(rootNode, mapaEsta)
		}
				
		return mapaEsta
	}
	
	/*
	 * Crea un mapa con los datos
	 */
	
	def tieneHijosAux=false
	def obtenMapa(nodo, mapaEsta, nombre) {	
		def valorAux=""
		nodo.childNodes().each {
			if(it.childNodes().hasNext() != false) {
				obtenMapa(it, mapaEsta, it.name())
				tieneHijosAux=true
			} else {
				valorAux=it.name()+"=="+it.text()
				if(mapaEsta.containsKey(nombre)){
					if (tieneHijosAux) {
						valorAux=mapaEsta.get(nombre).concat("%%").concat(valorAux)
						tieneHijosAux=false
					}else{
						valorAux=mapaEsta.get(nombre).concat("##").concat(valorAux)
					}	
				}
				
				mapaEsta.put(nombre,valorAux)
			}
		}
		
		tieneHijosAux=false
	}
	
	def tieneHijosAlptisAux=false
	def obtenMapaAlptis(nodo, mapaEsta, nombre) {	
		def valorAux=""
		
		nodo.childNodes().each {
			if(nombre.equals("policy")){
				tieneHijosAlptisAux=false
			}
			
			if(it.childNodes().hasNext() != false) {
				obtenMapaAlptis(it, mapaEsta, it.name())
				tieneHijosAlptisAux=true
			} else {
				valorAux=it.name()+"=="+it.text()
				if(mapaEsta.containsKey(nombre) && !nombre.equals("BasicPolicyGroup")){
					if(tieneHijosAlptisAux){
						valorAux=mapaEsta.get(nombre).concat("%%").concat(valorAux)
						tieneHijosAlptisAux=false
					}else{
						valorAux=mapaEsta.get(nombre).concat("##").concat(valorAux)
					}	
					
					mapaEsta.put(nombre,valorAux)
				}else if(nombre.equals("BasicPolicyGroup")){
					valorAux=mapaEsta.get("policy").concat("##").concat(valorAux)
					
					mapaEsta.put("policy",valorAux)
				}else{
					mapaEsta.put(nombre,valorAux)
				}
				
			}
		}
		
		tieneHijosAlptisAux=false
	}
	
	def obtenMapaLagunaro(nodo, mapaEsta, nombre) {
		def mapaAuxList=""
		try{
			nodo.childNodes().each {
				if(it.childNodes().hasNext() != false) {
					def mapaAuxMap=[:]
					def cont=0
					def sizeChildNodes=it.childNodes().size()
					it.childNodes().each {				
						mapaAuxList=mapaAuxList+it.name()+"="+it.text()
						cont++
						if(cont<sizeChildNodes){
							mapaAuxList=mapaAuxList+"##"
						}
					}
	
					mapaAuxList=mapaAuxList+"%%"
				} else {
					mapaEsta.put(nombre + "_" + it.name(), it.text())
				}
			}
			
			if (mapaAuxList!=""){
				mapaEsta.put(nombre,mapaAuxList)
			}
		}catch (Exception e){
			logginService.putError("obtenMapaLagunaro","Ha ocurrido el siguiente error: "+e)	
		}
	}	
	
	def reLagunaroImpl(nodo, mapaEsta){
		obtenMapaLagunaro(nodo.datos_envio, mapaEsta, nodo.datos_envio.name())
		obtenMapaLagunaro(nodo.poliza, mapaEsta, nodo.poliza.name())
		obtenMapaLagunaro(nodo.datos_capital, mapaEsta, nodo.datos_capital.name())
		obtenMapaLagunaro(nodo.asegurado, mapaEsta, nodo.asegurado.name())
		obtenMapaLagunaro(nodo.agente, mapaEsta, nodo.agente.name())
		obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}	
	
	def reAfiescaImpl(nodo, mapaEsta){
		obtenMapa(nodo.request_Data, mapaEsta, nodo.request_Data.name())
		obtenMapa(nodo.policy, mapaEsta, nodo.policy.name())
		obtenMapa(nodo.candidate, mapaEsta, nodo.candidate.name())
		obtenMapa(nodo.agent, mapaEsta, nodo.agent.name())
		//obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}
	
	def reAlptisImpl(nodo, mapaEsta){
		obtenMapaAlptis(nodo.request_Data, mapaEsta, nodo.request_Data.name())
		obtenMapaAlptis(nodo.policy, mapaEsta, nodo.policy.name())
		obtenMapaAlptis(nodo.candidate, mapaEsta, nodo.candidate.name())
		obtenMapaAlptis(nodo.agent, mapaEsta, nodo.agent.name())
		obtenMapaAlptis(nodo.questions, mapaEsta, nodo.questions.name())
		//obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}
	
	def reCajamarImpl(nodo, mapaEsta){
		obtenMapa(nodo.RegScor, mapaEsta, nodo.RegScor.name())
		//obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}
	
	def reAmaImpl(nodo, mapaEsta){
		obtenMapa(nodo.RegScor, mapaEsta, nodo.RegScor.name())
		//obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}
	
	def reCaserImpl(nodo, mapaEsta){
		obtenMapa(nodo.CandidateInformation, mapaEsta, nodo.CandidateInformation.name())
		obtenMapa(nodo.PolicyHolderInformation, mapaEsta, nodo.PolicyHolderInformation.name())
		obtenMapa(nodo.ServiceInformation, mapaEsta, nodo.ServiceInformation.name())
	}
	
	def rePsnImpl(nodo, mapaEsta){
		obtenMapa(nodo.RegScor, mapaEsta, nodo.RegScor.name())
		//obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
	}
	
/*	OJO COMENTADO TRAS UNIÓN LAGUNARO
	def obtenServicios(nodo, mapaServicio, nombre, i) {
		def att
		nodo.childNodes().each {
			i++
			if(it.childNodes().hasNext() != false) {
				if(it.name() == "prueba_medica") {
					att = it.attributes()
					mapaServicio.put(nombre + "_" + it.name() + "#" + i, att.codigo_prueba_medica)
				}
				obtenServicios(it, mapaServicio, nombre, i - 1)
			} else {
				mapaServicio.put(nombre + "_" + it.name() + "#" + i, it.text())
			}
		}
	}
*/

	def obtenServicios(nodo, mapaServicio, nombre, i) {
		def att
		
		nodo.childNodes().each {		
			if(it.childNodes().hasNext() != false) {				
				if(it.name() == "prueba_medica") {
					i++
					att = it.attributes()
					mapaServicio.put(nombre + "_" + it.name() + "#" + i, att.codigo_prueba_medica)
				}
				obtenServicios(it, mapaServicio, nombre, i - 1)
			} else {
				mapaServicio.put(nombre + "_" + it.name() + "#" + i, it.text())
			}
		}
	}	
	
	
/* OJO metodo de lagunaro sustituido por el siguiente obtenerOperacion
	def obtenerOperacion(mensaje) {
		def rootNode = new XmlSlurper().parseText(mensaje)
		def chi = rootNode.Body.childNodes()
		def opera = chi[0].name()
		
		return opera
	}
*/	
	
	def obtenerOperacion(mensaje) {
		def result
		def rootNode = new XmlSlurper().parseText(mensaje)
		//HACEMOS ESTA COMPROBACIÓN POR REQUEST CUYA CABEZERA SEA SOAP
		if(rootNode.Body.childNodes()){
			result=rootNode.Body.childNodes()[0].name()
		}else{
			result=rootNode[0].name()
		}
		
		return result
	}
	
	def obtenerObjetoOperacion(mensaje) {
		return Operacion.findByClave(mensaje)
	}
	
}
package com.ws.servicios

import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.context.request.RequestContextHolder

class LogginService {
	def authenticateService
	
	def putInfoEndpoint = { String operacion, String mensaje->
		def datosConexion=dameDatosSession()
			
		log.info ("["+operacion+"] ("+datosConexion.user+"-"+datosConexion.ip+"): "+mensaje)
	}
	
	def putInfo= { operacion, mensaje->
		def datosConexion=dameDatosSession()
		log.info ("["+operacion+"] ("+datosConexion.user+"-"+datosConexion.ip+"): "+mensaje)
	}
	
	def putInfoMessage= { String mensaje->
		log.info (mensaje)
	}
	
	def putErrorEndpoint = { String operacion, String mensaje->
		def datosConexion=dameDatosSession()
			
		log.error ("["+operacion+"] ("+datosConexion.user+"-"+datosConexion.ip+"): "+mensaje)
	}
	
	
	def putError = { String operacion, String mensaje->
		def datosConexion=dameDatosSession()
			
		log.error ("["+operacion+"] : "+mensaje)
	}
	
	def putErrorMessage= { mensaje->
		log.error (mensaje)
	}

	def putDebugMessage= { mensaje->
		log.debug(mensaje)
	}

	def dameDatosSession (){
		
		def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
		def ip = request.getRemoteAddr();
		def uri = request.getRequestURI()
		def sesion = RequestContextHolder.currentRequestAttributes().getSession()
		def user=sesion.getAttribute("userEndPoint")

		def datos = [ip: ip, uri: uri, user: user]

		return datos
		
	}

}
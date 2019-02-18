package com.ws.servicios
import org.apache.commons.logging.*
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils

class LogginService {
	def authenticateService
	
	def putInfoEndpoint = { operacion, mensaje->
		def datosConexion=dameDatosSession()
			
		log.info ("["+operacion+"] ("+datosConexion.user+"-"+datosConexion.ip+"): "+mensaje)
	}
	
	def putInfo= { operacion, mensaje->
		def datosConexion=dameDatosSession()
			
		log.info ("["+operacion+"]: "+mensaje)
	}
	
	def putInfoMessage= { mensaje->	
		log.info (mensaje)
	}
	
	def putErrorEndpoint = { operacion, mensaje->
		def datosConexion=dameDatosSession()
			
		log.error ("["+operacion+"] ("+datosConexion.user+"-"+datosConexion.ip+"): "+mensaje)
	}
	
	
	def putError = { operacion, mensaje->
		def datosConexion=dameDatosSession()
			
		log.error ("["+operacion+"] : "+mensaje)
	}
	
	def putErrorMessage= { mensaje->
		log.error (mensaje)
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
package com.ws.servicios

import com.scortelemed.Person
import org.springframework.beans.factory.InitializingBean
import org.springframework.web.context.request.RequestContextHolder

import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.UnsupportedCallbackException

class ServerPasswordCallbackHandlerService implements CallbackHandler,InitializingBean{

	def logginService
	

    void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException{
		def person
		
		try{
	        for (pc in callbacks){
				person=Person.findByUsername(pc.identifier)					
									
				if(person) {	
					pc.password = person.password
						
					def sesion = RequestContextHolder.currentRequestAttributes().getSession()
					sesion.setAttribute("userEndPoint",person?.username)	
					sesion.setAttribute("companyST",person?.company?.codigoSt)
				}
	        }
		}catch (Exception e){
			logginService.putError("ServerPasswordCallbackHandlerService","El usuario " + person?.username + " ha intentado hacer login pero ha dado el error: "+e)
		}
    }


    void afterPropertiesSet() {
    }
	
	boolean comprobarPermiso () {
		boolean result=false
		
		return result
	}
}
package com.ws.servicios
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback
import org.springframework.beans.factory.InitializingBean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.apache.commons.codec.digest.DigestUtils as DU
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder

import com.scortelemed.Person
import com.scortelemed.Ipcontrol

class ServerPasswordCallbackHandlerService implements CallbackHandler,InitializingBean{

	def daoAuthenticationProvider
	def springSecurityService
	def userService
	String companyStWs
	def logginService
	
    @Override
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

    @Override
    void afterPropertiesSet() {
    }
	
	boolean comprobarPermiso () {
		boolean result=false
		
		return result
	}
}
package com.scortelemed

import grails.util.Holders

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.Authentication

import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityUtils

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder;

@Secured(['ROLE_ADMIN','ROLE_USER'])
class InicioController {

	def mailService
	def logginService
	def springSecurityService
	def myUser

	def index = {

		boolean hasAdmin = SpringSecurityUtils.ifAllGranted("ROLE_ADMIN");
		boolean hasUser = SpringSecurityUtils.ifAllGranted("ROLE_USER");

		def auth = SecurityContextHolder.getContext().getAuthentication()

		if (hasUser){
			session.setAttribute("ou", obtenerOu(auth))
			redirect(controller: "dashboard", action: "index")
		} else if (hasAdmin) {
			session.setAttribute("ou", "AD")
		}
	}

	/*
	 * FIXME: ESTO ES TEMPORAL!!!!!!!!!!
	 */
	def ultimasRequest = {
		params.max = Math.min(params?.max?.toInteger() ?: 25, 25)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "fecha_creacion"
		params.order = params?.order ?: "desc"

		def	listado = Request.list(params)
		def	listadoTotal = Request.count()

		[ultimasRequest: listado, ultimasRequestTotal: listadoTotal]
	}
	def ultimasRequestTramita = {

		params.max = Math.min(params?.max?.toInteger() ?: 15, 15)
		params.offset = params?.offset?.toInteger() ?: 0
		params.sort = params?.sort ?: "fecha_procesado"
		params.order = params?.order ?: "asc"

		def operacion = Operacion.findByClave("TramitacionReconocimientoMedicoRequest")
		def listado, listadoTotal
		if(operacion) {
			listado = Request.findAllByOperacion(operacion, params)
			listadoTotal = Request.countByOperacion(operacion)
		} else listadoTotal = 0

		[ultimasRequest: listado, ultimasRequestTotal: listadoTotal]
	}

	def obtenerOu(auth){

		int count = 0
		String ou = null

		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)auth.getAuthorities()

		Iterator iterator = authorities.iterator()

		while(iterator.hasNext()){

			String element = (String) iterator.next();
			count = StringUtils.countMatches(element, "_")

			if (count > 1){

				ou = element.substring(element.lastIndexOf("_")+1, element.lastIndexOf("_")+3)
			}
		}

		return ou
	}
}

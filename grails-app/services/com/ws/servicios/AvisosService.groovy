package com.ws.servicios

import com.scortelemed.*

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class AvisosService {
	
	def groovyPagesTemplateEngine
	def logginService
	
	def enviarAvisos(opera, proceso){
		def avi = Aviso.findAllByOperacion(opera)

		avi.each {
			if(avi.activo)
				this.aviso(it, proceso)
		}
		return null
	}

	def aviso(aviso, proceso){
		def mapa = creaMapa(aviso, proceso)
		def mensaje = parsearPlantilla(aviso.plantilla.body, mapa, true)
		def destino = aviso.destinatarios
		def subject = parsearPlantilla(aviso.plantilla.subject, mapa)
		
		def destinoFinal = []
		destino.each {
			if(it.activo)
				destinoFinal += it.email
		
		}
		//Por ahora se va a hacer desde el propio endpoint
		//this.enviarEmail(destinoFinal, subject, mensaje)
		//def correoUtil = new CorreoUtil()
		//correoUtil.envioEmail(subject,0)
		
		return null
	}
	
	def creaMapa(aviso, proceso){
		def request = Request.findByClaveProceso(proceso)
		def mapa = Estadistica.findAllByRequest(request)
		
		def claves = mapa.clave
		def valores = mapa.value
		def pares = [:]
		pares = [claves, valores].transpose()
		
		def salida = [:]
		pares.each{ salida << (it as MapEntry) }
		
		//TODO: Definir que mas datos va a necesitar
		
		
		// Datos universales para todos los mapas...
			salida.put('proceso', proceso)	//el nombre del proceso
			
			def fecha = new Date()
			salida.put('fecha', fecha)	//la fecha actual
			
			salida.put('clave_operacion', aviso.operacion.clave)	//la clave de la operacion webservice
			salida.put('operacion', aviso.operacion.descripcion)	//la descripcion(nombre) de la operacion webservice
			
			salida.put('destinatarios', aviso.destinatarios)	//lista de destinatarios (lista de objetos)
		// Datos universales para todos los mapas...

		return salida
	}
	
	def parsearPlantilla(contenido, mapa, useTemplate=false) {
		try{
			def salida = new StringWriter()
			groovyPagesTemplateEngine.createTemplate(contenido, 'sample').make(mapa).writeTo(salida)

			if(useTemplate) {
				def body = [:]
				body.put('body', salida.toString())
				
				def template = Conf.findByName("template_email")

				if(template){
					def salida2 = new StringWriter()
					groovyPagesTemplateEngine.createTemplate(template.value, 'sample2').make(body).writeTo(salida2)
				
					return salida2.toString()
				} else {
					throw new org.codehaus.groovy.grails.exceptions.GrailsRuntimeException("No existe ningun template para decorar la plantilla.")
				}
			}
			else {				
				return salida.toString()
			}
		}catch (Exception e){
			logginService.putError("parsearPlantilla","Ha ocurrido el siguiente error: "+e)
		}
	}
	
/*	def enviarEmail(destino, tema, cuerpo) {
		def defecto = Conf.findByName("admin_email")
		
		if(defecto){
			mailService.sendMail {
				to "${defecto.value}"
				bcc destino.toArray()
				subject "${tema}"
				html "${cuerpo}"
			}
		} else {
			throw new org.codehaus.groovy.grails.exceptions.GrailsRuntimeException("No existe ningun admin_email para enviar avisos.")
		}
		return null
	}
*/	

	def enviarEmail(destino, tema, cuerpo) {
	     //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider())
	  
	     String subject = "Titulo/Subject del mail"
	     String message = "Mensaje/Body del mail"
	        
		  boolean debug = false
		
		  Properties props = new Properties()
		  props.put("mail.smtp.host", "mail.scortelemed.com")
		  props.put("mail.smtp.auth", "true")
		  props.put("mail.debug", "false")
		  props.put("mail.smtp.port", "25")
		  props.put("mail.smtp.socketFactory.port", "25")
		 // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
		  props.put("mail.smtp.socketFactory.fallback", "false")
	
	      Session session = Session.getDefaultInstance(props)
		
	
		  session.setDebug(debug)
		  Message msg = new MimeMessage(session)
		  InternetAddress addressFrom = new InternetAddress("usuarioExtranet@scortelemed.com")
		  msg.setFrom(addressFrom)
		  String[] addressTo = new InternetAddress[1]
		  addressTo[0] = "d@hwsol.com"
		  msg.setRecipients(Message.RecipientType.TO, "d@hwsol.com")
		  msg.setSubject(subject)
		  msg.setContent(message, "text/plain")
		  Transport t = session.getTransport("smtp")
		
		  // Aqui usuario y password de gmail
		  //t.connect("pruebashwsol@gmail.com","5helloworld5")
		  t.connect("usuarioExtranet","T1pm050c")
		  t.sendMessage(msg,msg.getAllRecipients())
		  t.close()
		  
	      return null
	}
	
	def generarAyuda(operacion) {
		def web
		def variables

		if(operacion.getClass().getName() == "java.lang.String") {
			web = Operacion.get(operacion)
			variables = Datowebservice.findAllByOperacion(web)
		}
		else {
			variables = Datowebservice.findAllByOperacion(operacion)
		}
		
		def ayuda = "<tr>"
		def link = ""
		def tres = 0
		
		variables.each{
			tres++
			if(tres > 3) {
				ayuda = ayuda + "</tr><tr>"
				tres = 0
			}
			link = '<a class="ayudanteLink" onClick="inse(\'&#36;{' + it.nombre + '}\');">'
			ayuda = ayuda + '<td class="ayudanteCaja" >' + link + it.nombre + " (" + it.tipo +")</a> "
			ayuda = ayuda + '<a onclick="Modalbox.show(this.href, {title: this.title, width: 500}); return false;" title="Ayuda para tipo: Cadena" href="/webservices/plantilla/ayuda?ayuda=cadena">'
			ayuda = ayuda + '<span class="ayudanteAyuda">ayuda</span></a></td>'
			
			}
		ayuda = ayuda + "</tr>"
		
		return ayuda
	}

}
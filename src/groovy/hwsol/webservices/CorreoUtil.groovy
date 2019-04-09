package hwsol.webservices

import java.security.Security;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.web.context.request.RequestContextHolder as RCH;
import com.scortelemed.Conf
import com.scortelemed.Destinatario
import com.scortelemed.Person
import com.scortelemed.Company
import com.ws.servicios.LogginService
import grails.util.Environment

class CorreoUtil {

	LogginService logginService=new LogginService()

	def remitente="usuarioExtranet@scortelemed.com"
	def usuarioConexion="usuarioExtranet"
	def passConexion="T1pm050c"
	def host="mail.scortelemed.com"
	def port="25"

	//	def remitente="hwsoltest@gmail.com"
	//	def usuarioConexion="hwsoltest@gmail.com"
	//	def passConexion="hwsol1234"
	//	def host="smtp.gmail.com"
	//	def port="587"
	
	private String envioEmailNoTratados(String operacion, String mensaje){
		
		if (Environment.current != Environment.DEVELOPMENT) {
	
			//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	
			def destinatarios = dameDestinatariosCompania()
			String subject = "Webservices: " + operacion;
	
			boolean debug = false;
			Session sessionMail = Session.getDefaultInstance(propertiesMail());
	
			try{
				sessionMail.setDebug(debug);
				Message msg = new MimeMessage(sessionMail);
				InternetAddress addressFrom = new InternetAddress(remitente);
				msg.setFrom(addressFrom);
	
				InternetAddress[] addressTo = null;
	
				if (!Environment.current.name.equals("production_wildfly")) {
					subject = "Webservices Test PREPRO: " + operacion;
					//				addressTo = new InternetAddress[1];
					//				addressTo[0] = new InternetAddress("javier.rodriguez@scortelemed.com");
				}
	
				if (destinatarios){
					addressTo = new InternetAddress[destinatarios.size()];
					def cont=0
					destinatarios.each{
						addressTo[cont] = new InternetAddress(it.trim());
						cont++
					}
	
					msg.setRecipients(Message.RecipientType.TO, addressTo);
					msg.setSubject(subject);
					msg.setContent(mensaje, "text/plain");
					Transport t = sessionMail.getTransport("smtp");
					t.connect(usuarioConexion,passConexion);
					t.sendMessage(msg,msg.getAllRecipients());
					t.close();
	
					logginService.putInfo("CorreoUtil", "EL correo se ha enviado correctamente a : "+destinatarios)
	
					return null
				}
	
			} catch (Exception e) {
				logginService.putErrorEndpoint("Method envioEmail", "Ha ocurrido un error en envio de email: "+e)
			}
		}
	}

	private String envioEmail(String operacion, String mensaje, int numRegistros){

		if (Environment.current != Environment.DEVELOPMENT) {

			//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			String message

			def destinatarios = dameDestinatariosCompania()
			String subject = "Webservices: " + operacion;

			if (!mensaje) {
				message = "Número de Registros: " + numRegistros;
			} else {
				message = mensaje
			}

			boolean debug = false;
			Session sessionMail = Session.getDefaultInstance(propertiesMail());

			try{
				sessionMail.setDebug(debug);
				Message msg = new MimeMessage(sessionMail);
				InternetAddress addressFrom = new InternetAddress(remitente);
				msg.setFrom(addressFrom);

				InternetAddress[] addressTo = null;

				if (!Environment.current.name.equals("production_wildfly")) {
					subject = "Webservices Test PREPRO: " + operacion;
					//				addressTo = new InternetAddress[1];
					//				addressTo[0] = new InternetAddress("javier.rodriguez@scortelemed.com");
				}

				if (destinatarios){
					addressTo = new InternetAddress[destinatarios.size()];
					def cont=0
					destinatarios.each{
						addressTo[cont] = new InternetAddress(it.trim());
						cont++
					}

					msg.setRecipients(Message.RecipientType.TO, addressTo);
					msg.setSubject(subject);
					msg.setContent(message, "text/plain");
					Transport t = sessionMail.getTransport("smtp");
					t.connect(usuarioConexion,passConexion);
					t.sendMessage(msg,msg.getAllRecipients());
					t.close();

					logginService.putInfo("CorreoUtil", "EL correo se ha enviado correctamente a : "+destinatarios)

					return null
				}

			} catch (Exception e) {
				logginService.putErrorEndpoint("Method envioEmail", "Ha ocurrido un error en envio de email: "+e)
			}
		}
	}

	private String envioEmailErrores(String operacion,String request,Exception error){

		if (Environment.current != Environment.DEVELOPMENT) {

			//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

			String subject = "Webservices: " + operacion

			if (!Environment.current.name.equals("production_wildfly")) {
				subject = "Webservices Test PREPRO: " + operacion;
			}

			String message = null;

			boolean debug = false;

			Session sessionMail = Session.getDefaultInstance(propertiesMail());

			try{
				message=request + "\n" + "Exception: " + error + "\n" + new Date();

				sessionMail.setDebug(debug);
				Message msg = new MimeMessage(sessionMail);
				InternetAddress addressFrom = new InternetAddress(remitente);
				msg.setFrom(addressFrom);
				InternetAddress[] addressTo = new InternetAddress[1];

				addressTo[0] = new InternetAddress(dameEmailErrores());

				msg.setRecipients(Message.RecipientType.TO, addressTo);
				msg.setSubject(subject);
				msg.setContent(message, "text/plain");
				// Prioridad al envío
				// 1 La más alta - 3 Normal - 5 la más baja
				msg.addHeader("X-Priority", "1");
				Transport t = sessionMail.getTransport("smtp");
				t.connect(usuarioConexion,passConexion);
				t.sendMessage(msg,msg.getAllRecipients());
				t.close();
				return null
			} catch (Exception e) {
				logginService.putErrorEndpoint("Method envioEmailErrores", "Ha ocurrido un error en envio de email: "+e)
			}
		}
	}

	private Properties propertiesMail(){
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.fallback", "false");

		//ACTIVAR SI HACEMOS PRUEBAS EN LOCAL
		//props.put("mail.smtp.starttls.enable", "true");

		return props;
	}

	def dameDestinatariosCompania = {
		->
		def company
		def email

		def sesion = RCH.currentRequestAttributes().getSession()
		if (sesion.userEndPoint) {
			company=Person.findByUsername(sesion.userEndPoint)?.company
			email=Destinatario.findAllByCompany(company).email
		} else if ( sesion.companyST ) {
			company=Company.findByCodigoSt(sesion.companyST)
			email=Destinatario.findAllByCompany(company).email
			sesion.companyST=null
		} else {
			email=dameEmailErrores()
		}

		return email
	}

	def dameEmailErrores = {
		->
		return Conf.findByName('admin_email').value.toString()
	}
}

import com.scortelemed.Conf
import hwsol.webservices.MyUserDetailsContextMapper
import hwsol.webservices.EventIntrceptorListener


beans = {
	
	ldapUserDetailsMapper(MyUserDetailsContextMapper){
	
	}

	interceptorEventos(EventIntrceptorListener) {}
	
	autorizacionPRO(wslite.http.auth.HTTPBasicAuthorization) {
		username = "alptis"
		password = "29rHyk28Km62kZP4VoUJ"
	}

	soapAlptisRecetteWSPRO(wslite.soap.SOAPClient) {
		serviceURL = "https://webservices.alptis.org/scortelemed/ws.php"

		//httpClient = ref('httpClient')
		authorization = ref('autorizacionPRO')
	}
	
	
	autorizacion(wslite.http.auth.HTTPBasicAuthorization) {
		username = "alptis"
		password = "mp4!wsscoralp"
	}

	soapAlptisRecetteWS(wslite.soap.SOAPClient) {
		serviceURL = "http://recette-webservices.alptis.org/webservices/scortelemed/ws.php"

		authorization = ref('autorizacion')
	}
	
	autorizacionCMWS(wslite.http.auth.HTTPBasicAuthorization) {
		username = "vevisct"
		password = "21yb51gs"
	}
	
	soapCajamarRecetteWS(wslite.soap.SOAPClient) {
		serviceURL = "https://www.user.generali.es/evi_vidaEmissioServWeb/services/TeleSeleccionHandlerService/WEB-INF/wsdl/TeleSeleccionHandlerService.wsdl"

		authorization = ref('autorizacionCMWS')
	}
	
//	customAuthorizationPolicy(AuthorizationPolicy) {
//		println "entro para login"
//		username = "vevisct"
//		password = "21yb51gs"
////		authorizationType = 'Basic'
//	}
	

}
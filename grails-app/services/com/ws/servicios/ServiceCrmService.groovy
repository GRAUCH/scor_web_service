package com.ws.servicios
import grails.util.Holders as CH

import org.apache.axis2.client.Options
import org.apache.axis2.transport.http.HTTPConstants
import org.apache.axis2.transport.http.HttpTransportProperties
import org.apache.xmlbeans.*
import org.springframework.web.context.request.RequestContextHolder

import com.hwsol.crm.dynamics4.webservice.CrmServiceStub
import com.microsoft.schemas.crm._2006.query.*
import com.microsoft.schemas.crm._2007.coretypes.CrmAuthenticationToken
import com.microsoft.schemas.crm._2007.webservices.*
import com.scortelemed.Company
import com.scortelemed.Conf

class ServiceCrmService {
	def logginService
	
	private static String endpointURL = null
	private static String userName = null
	private static String password = null 
	private static String userNameLag = null
	private static String passwordLag = null
	private static String host = null
	private static String domain = null 
	private static String orgName = null
	
	private static String crmPhrase = CH.config.webservices.crm.crmPhrase
    boolean transactional = true

    def conexion(String xmlPeticion) {
		setConectionCRM()
		com.microsoft.schemas.crm._2007.webservices.FetchDocument.Fetch elfetch = com.microsoft.schemas.crm._2007.webservices.FetchDocument.Fetch.Factory.newInstance();
		elfetch.setFetchXml(xmlPeticion.toString());
		CrmServiceStub stub;  
		try {
			stub = new CrmServiceStub(endpointURL);
			setOptions(stub._getServiceClient().getOptions());
			//Now this is required. Without it all i got was 401s errors  
			CrmAuthenticationTokenDocument catd = CrmAuthenticationTokenDocument.Factory.newInstance();
			CrmAuthenticationToken token = CrmAuthenticationToken.Factory.newInstance();
			token.setAuthenticationType(0);
			token.setOrganizationName(orgName);
			catd.setCrmAuthenticationToken(token);
			boolean fetchNext = true;  
			FetchDocument fd = FetchDocument.Factory.newInstance();
			fd.setFetch(elfetch);
			
			FetchResponseDocument frd = stub.fetch(fd,  catd, null, null);
			def xmlResultado = frd.getFetchResponse().getFetchResult()	

			return xmlResultado
	
		} catch (Exception e) {  
			logginService.putError("conexion","No se ha podido conectar al crm: "+e)
		}
    }

    
	private void setConectionCRM(){
		endpointURL = Conf.findByName("crm.wsdl")?.value
		userName = Conf.findByName("crm.usuario")?.value
		password = Conf.findByName("crm.clave")?.value
		userNameLag = Conf.findByName("crm.usuario.lagunaro")?.value
		passwordLag = Conf.findByName("crm.clave.lagunaro")?.value
		host = Conf.findByName("crm.host")?.value
		domain = Conf.findByName("crm.domain")?.value
		orgName = Conf.findByName("crm.orgName")?.value
	}
	
	
	private void setOptions(Options options){  	
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		def compania
		
		if(session.compa!=null && session.compa!=""){
			compania=Company.findByNombre(session.compa)
		}
		
		if (compania){
			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			  
			ArrayList authSchemes = new ArrayList();
			authSchemes.add(HttpTransportProperties.Authenticator.NTLM);
			auth.setAuthSchemes(authSchemes);
			
			
			if (session.compa == "lagunaro"){				
				auth.setUsername(userNameLag); 
				auth.setPassword(passwordLag); 
			}else{
				auth.setUsername(userName);
				auth.setPassword(password);
			}
			
			auth.setHost(host);
			//auth.setPort(port);
			auth.setDomain(domain);
			auth.setPreemptiveAuthentication(true); //it doesnt matter...
			options.setProperty(HTTPConstants.AUTHENTICATE, auth);
			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, "true");
			
			orgName=orgName
		}
	}    
  
}

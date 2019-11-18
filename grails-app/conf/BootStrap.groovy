import org.apache.cxf.frontend.ServerFactoryBean
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.ws.security.WSConstants
import org.apache.ws.security.handler.WSHandlerConstants
import org.grails.cxf.utils.GrailsCxfUtils
import grails.converters.JSON
import grails.converters.XML
import org.codehaus.groovy.grails.web.converters.marshaller.xml.InstanceMethodBasedMarshaller
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName
import org.apache.ws.security.WSPasswordCallback
import org.springframework.beans.factory.InitializingBean
import org.apache.ws.security.validate.Validator
import org.apache.ws.security.WSSecurityException
import org.apache.ws.security.validate.UsernameTokenValidator
import org.apache.ws.security.WSSecurityEngine
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor

class BootStrap {

    ServerFactoryBean afiEscaUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean alptisUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean gestionReconocimientosMedicosServiceFactory
    ServerFactoryBean afiEscaUnderwrittingCasesResultsServiceFactory
    ServerFactoryBean lifesquareUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean lifesquareUnderwrittingCasesResultsServiceFactory
    ServerFactoryBean cajamarUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean amaUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean psnUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean caserUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean netinsuranceUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean methislabUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean methislabCFUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean cbpitaUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean simplefrUnderwrittingCaseManagementServiceFactory
    ServerFactoryBean societeGeneraleUnderwrittingCaseManagementServiceFactory
    //ServerFactoryBean enginyersUnderwrittingCaseManagementServiceFactory

    def serverPasswordCallbackHandlerService
//
    def init = { servletContext ->
        Map<String, Object> inPropsDigest = [:]
        inPropsDigest.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        inPropsDigest.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_DIGEST)
        inPropsDigest.put(WSHandlerConstants.PW_CALLBACK_REF, serverPasswordCallbackHandlerService)

        Map<String, Object> inPropsText = [:]
        inPropsText.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        inPropsText.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_TEXT)
        inPropsText.put(WSHandlerConstants.PW_CALLBACK_REF, serverPasswordCallbackHandlerService)

        afiEscaUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        alptisUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        gestionReconocimientosMedicosServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        afiEscaUnderwrittingCasesResultsServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        lifesquareUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        lifesquareUnderwrittingCasesResultsServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        cajamarUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        amaUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        psnUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        caserUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        netinsuranceUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        simplefrUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        societeGeneraleUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        cbpitaUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        methislabUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        methislabCFUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
        //enginyersUnderwrittingCaseManagementServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inPropsDigest))
    }

    def destroy = {
    }
}

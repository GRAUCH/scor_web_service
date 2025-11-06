package hwsol.webservices

import com.scor.global.Uri
import com.scortelemed.Company
import org.apache.cxf.common.injection.NoJSR250Annotations
import org.apache.cxf.common.logging.LogUtils
import org.apache.cxf.interceptor.AbstractLoggingInterceptor
import org.apache.cxf.interceptor.Fault
import org.apache.cxf.message.Message
import org.apache.cxf.phase.Phase
import org.codehaus.groovy.grails.web.util.WebUtils
import org.opensaml.ws.message.MessageException
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpServletRequest
import javax.xml.ws.WebServiceException
import java.util.logging.Logger
import grails.util.Holders
import org.apache.cxf.helpers.CastUtils;

class EventIntrceptorListener extends AbstractLoggingInterceptor  {


    private static final Logger LOG = LogUtils.getLogger(EventIntrceptorListener)
    def name
    def grailsApplication = Holders.grailsApplication

    EventIntrceptorListener() {
        super(Phase.RECEIVE)
        log LOG, 'Creando un interceptor personalizado'
    }

    void handleMessage(Message message) throws Fault {

        String requestUri = (String)message.getExchange().getInMessage().get(Message.REQUEST_URI)
        def ctx = grailsApplication.mainContext
        def service = ctx.getBean("interceptorEventosService")
        Uri uri = service.getComapnyFromRequest(requestUri)
        Company company = Company.findByNombre(uri.getCompany())
        def sesion = RequestContextHolder.currentRequestAttributes().getSession()
        sesion.setAttribute("companyST",company?.codigoSt)
        def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        def ip = request.getRemoteAddr()
        def host = request.getRemoteHost()
        log LOG, "$name :: Calling from " + company.getNombre() + " con ip " + ip
        log LOG, "$name :: Calling from " + company.getNombre() + " con host " + host
        if (!service.validIp(company.getNombre(), ip)) {
            log LOG, "$name :: Forbidden: IP address rejected"
            throw new WebServiceException("Forbidden: IP address rejected")
        }
        log LOG, "$name :: IP permitida para operacion compañia " + uri.getCompany()
    }

    @Override
    protected Logger getLogger() {
        LOG
    }
}
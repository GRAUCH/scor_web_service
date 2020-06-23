package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Request
import com.ws.servicios.IExpedienteService
import com.ws.servicios.LogginService
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class ExpedienteService implements IExpedienteService {

    def logginService = new LogginService()
    def grailsApplication

    def crearExpediente(Request req) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientCrearOrabpel")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("orabpelCreacion.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientCrearOrabpel.initiate(crearExpedienteBPM(req))
            return "OK"
        } catch (Exception e) {
            throw new WSException(this.getClass(), "crearExpediente", ExceptionUtils.composeMessage(null, e));
        }
    }

    private def crearExpedienteBPM(Request req) {
        def listadoFinal = []
        RootElement payload = new RootElement()

        listadoFinal.add(buildCabecera(req,null))
        listadoFinal.add(buildDatos(req, req.company))
        listadoFinal.add(buildPie(null))

        payload.cabeceraOrDATOSOrPIE = listadoFinal

        return payload
    }

    private def buildDatos(Request req, Company company) {
        try {
            DATOS dato = new DATOS()
            //TODO IMPLEMENTAR SWITCH
            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
    }

    def buildCabecera(Request req, String codigoSt) {
        def formato = new SimpleDateFormat("yyyyMMdd");
        RootElement.CABECERA cabecera = new RootElement.CABECERA()
        if(codigoSt) {
            cabecera.setCodigoCia(codigoSt)
        } else {
            cabecera.setCodigoCia(req.company.codigoSt)
        }
        cabecera.setContadorSecuencial("1")
        cabecera.setFechaGeneracion(formato.format(new Date()))
        cabecera.setFiller("")
        cabecera.setTipoFichero("1")
        return cabecera
    }

    def buildPie(Request req) {
        RootElement.PIE pie = new RootElement.PIE()
        pie.setFiller("")
        pie.setNumFilasFichero(100)
        pie.setNumRegistros(1)
        return pie
    }
}

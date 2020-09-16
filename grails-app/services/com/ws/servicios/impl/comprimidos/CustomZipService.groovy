package com.ws.servicios.impl.comprimidos

import com.scor.comprimirdocumentos.ParametrosEntrada
import com.scortelemed.Conf
import com.ws.servicios.IComprimidoService
import grails.transaction.Transactional
import hwsol.webservices.CorreoUtil
import servicios.ExpedienteInforme

@Transactional
class CommonZipService implements IComprimidoService{

    def grailsApplication
    def logginService
    def correoUtil = new CorreoUtil()

    @Override
    def obtenerZip(String nodo) {
        def response = new byte[0]
        try {
            def parametrosEntrada = new ParametrosEntrada()
            parametrosEntrada.usuario = Conf.findByName("orabpel.usuario")?.value
            parametrosEntrada.clave = Conf.findByName("orabpel.clave")?.value
            parametrosEntrada.refNodo = nodo
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientComprimidoAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("orabpel.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientComprimidoAlptis.process(parametrosEntrada)
            return salida.datosRespuesta.content
        } catch (Exception e) {
            logginService.putErrorMessage("No se ha podido obtener el zip del nodo : " + nodo + ". Error msg: "  + e.getMessage())
            logginService.putErrorMessage("Causa : " + e.getCause())
            correoUtil.envioEmailErrores("No se ha podido obtener el zip del nodo", "No se ha podido obtener el ZIP", e)
        }
        return response
    }

    def obtenerZip(ExpedienteInforme expediente) {
        return obtenerZip(expediente.getNodoAlfresco())
    }

}

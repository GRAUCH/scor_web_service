package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Conf
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.ws.servicios.CompanyFactory
import com.ws.servicios.ICompanyService
import com.ws.servicios.IExpedienteService
import grails.transaction.Transactional
import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro

import java.text.SimpleDateFormat

@Transactional
class ExpedienteService implements IExpedienteService {

    def logginService
    def correoUtil
    def tarificadorService
    def grailsApplication
    ICompanyService companyService


    def consultaExpediente(String unidadOrganizativa, Filtro filtro) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.consultaExpediente(tarificadorService.obtenerUsuarioFrontal(unidadOrganizativa),filtro)
            return salida
        } catch (Exception e) {
            logginService.putError("consultaExpediente","No se ha podido consultar el expediente " + e)
            correoUtil.envioEmailErrores("consultaExpediente", "No se ha podido consultar el expediente ", e)
            return null
        }
    }

    def consultaExpedienteCodigoST(String codigoST, String unidadOrganizativa) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(unidadOrganizativa, filtro)
    }

    def consultaExpedienteNumSolicitud(String requestNumber, String unidadOrganizativa, String codigoST) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.CLIENTE)
        filtro.setValor(codigoST)
        Filtro filtroRelacionado = new Filtro()
        filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
        filtroRelacionado.setValor(requestNumber)
        filtro.setFiltroRelacionado(filtroRelacionado)
        return consultaExpediente(unidadOrganizativa, filtro)
    }

    def obtenerInformeExpedientes(String companya, String servicioScor, int estado, String fechaIni, String fechaFin, String pais) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientes(tarificadorService.obtenerUsuarioFrontal(pais), companya, servicioScor, estado, fechaIni, fechaFin)
            return salida.listaExpedientesInforme
        } catch (Throwable e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente", e)
            return null
        }
    }

    def obtenerInformeExpedientesSiniestros(String companya, String producto, int estado, String fechaIni, String fechaFin, String pais) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientesSiniestros(tarificadorService.obtenerUsuarioFrontal(pais), companya, producto, estado, fechaIni, fechaFin)
            return salida.listaExpedientes
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return null
        }
    }


    def modificaExpediente(String pais, Expediente expediente, def servicioScorList, def paqueteScorList) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.modificaExpediente(tarificadorService.obtenerUsuarioFrontal(pais), expediente, servicioScorList, paqueteScorList)
            return salida
        } catch (Exception e) {
            logginService.putError("modificaExpediente", "No se ha podido ejecutar la operacion de modificacion : " + e)
            correoUtil.envioEmailErrores("modificaExpediente", "No se ha podido ejecutar la operacion de modificacion ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return null
        }
    }

    /**
     * MÉTODO PARA LA CREACIÓN DE EXPEDIENTES
     *
     * @param Request req
     * @param TipoCompany comp
     * @return
     */
    def crearExpediente(Request req, TipoCompany comp) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientCrearOrabpel")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("orabpelCreacion.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientCrearOrabpel.initiate(crearExpedienteBPM(req, comp))
            return "OK"
        } catch (Exception e) {
            throw new WSException(this.getClass(), "crearExpediente", ExceptionUtils.composeMessage(null, e));
        }
    }

    private def crearExpedienteBPM(Request req, TipoCompany comp) {
        companyService = CompanyFactory.getCompanyImpl(comp)
        def listadoFinal = []
        RootElement payload = new RootElement()
        try {
            String codigoSt = companyService.getCodigoStManual(req)
            listadoFinal.add(buildCabecera(req, codigoSt))
            listadoFinal.add(companyService.buildDatos(req, codigoSt))
            listadoFinal.add(buildPie(null))
            payload.cabeceraOrDATOSOrPIE = listadoFinal
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
        return payload
    }

    private def buildCabecera(Request req, String codigoSt) {
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

    private def buildPie(Request req) {
        RootElement.PIE pie = new RootElement.PIE()
        pie.setFiller("")
        pie.setNumFilasFichero(100)
        pie.setNumRegistros(1)
        return pie
    }
}

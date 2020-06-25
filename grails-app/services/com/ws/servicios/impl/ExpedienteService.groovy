package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Conf
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.ws.servicios.IExpedienteService
import com.ws.servicios.LogginService
import grails.transaction.Transactional
import servicios.ClaveFiltro
import servicios.Filtro

import java.text.SimpleDateFormat

@Transactional
class ExpedienteService implements IExpedienteService {

    def logginService = new LogginService()
    def tarificadorService
    def grailsApplication
    //Servicios Compañías
    def amaService
    def cajamarService
    def caserService
    def cbpitaService
    def enginyersService
    def francesasService
    def lagunaroService
    def methislabCFService
    def methislabService
    def netinsuranceService
    def nnService
    def psnService
    def simplefrService
    def societeGeneraleService

    @Override
    def consultaExpediente(String unidadOrganizativa, Filtro filtro) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.consultaExpediente(tarificadorService.obtenerUsuarioFrontal(unidadOrganizativa),filtro)
            return salida
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes","No se ha podido obtener el informe de expediente : " + e)
            return null
        }
    }

    @Override
    def consultaExpedienteCodigoST(String codigoST, String unidadOrganizativa) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(unidadOrganizativa, filtro)
    }

    @Override
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

    @Override
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
        def listadoFinal = []
        RootElement payload = new RootElement()
        String codigoSt = getCodigoStManual(req, comp)
        listadoFinal.add(buildCabecera(req, codigoSt))
        listadoFinal.add(buildDatos(req, codigoSt, comp))
        listadoFinal.add(buildPie(null))

        payload.cabeceraOrDATOSOrPIE = listadoFinal

        return payload
    }

    private def getCodigoStManual(Request req, TipoCompany comp) {
        String codigoSt = null
        switch(comp) {
            case TipoCompany.AMA:
                codigoSt = amaService.getCodigoStManual(req)
                break
            case TipoCompany.ENGINYERS:
                codigoSt = enginyersService.getCodigoStManual(req)
                break
            case TipoCompany.PSN:
                codigoSt = psnService.getCodigoStManual(req)
                break
            default:
                break
        }
        return codigoSt
    }

    private def buildDatos(Request req, String codigoSt, TipoCompany tipo) {
        try {
            DATOS dato = new DATOS()
            switch(tipo) {
                case TipoCompany.AMA:
                    dato = amaService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.CAJAMAR:
                    dato = cajamarService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.CASER:
                    dato = caserService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.CBP_ITALIA:
                    dato = cbpitaService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.ENGINYERS:
                    dato = enginyersService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.AFI_ESCA:
                case TipoCompany.ALPTIS:
                case TipoCompany.ZEN_UP:
                    dato = francesasService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.LAGUN_ARO:
                    dato = lagunaroService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.CF_LIFE:
                    dato = methislabCFService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.METHIS_LAB:
                    dato = methislabService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.NET_INSURANCE:
                    dato = netinsuranceService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.NATIONALE_NETHERLANDEN:
                    dato = nnService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.PSN:
                    dato = psnService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.MALAKOFF_MEDERIC:
                    dato = simplefrService.buildDatos(req, codigoSt)
                    break
                case TipoCompany.SOCIETE_GENERALE:
                    dato = societeGeneraleService.buildDatos(req, codigoSt)
                    break
            }
            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
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

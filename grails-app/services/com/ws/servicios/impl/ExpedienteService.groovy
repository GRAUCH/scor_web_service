package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import com.ws.enumeration.UnidadOrganizativa
import com.ws.servicios.ServiceFactory
import com.ws.servicios.ICompanyService
import com.ws.servicios.IExpedienteService
import grails.transaction.Transactional
import grails.util.Environment
import grails.util.Holders
import hwsol.webservices.CorreoUtil
import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM
import servicios.Usuario

import java.text.SimpleDateFormat

import static grails.async.Promises.task

@Transactional
class ExpedienteService implements IExpedienteService {

    def logginService = Holders.grailsApplication.mainContext.getBean("logginService")
    def requestService = Holders.grailsApplication.mainContext.getBean("requestService")

    def grailsApplication = Holders.getGrailsApplication()
    CorreoUtil correoUtil = new CorreoUtil()
    ICompanyService companyService


    def consultaExpediente(UnidadOrganizativa pais, Filtro filtro) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.consultaExpediente(obtenerUsuarioFrontal(pais),filtro)
            return salida
        } catch (Exception e) {
            logginService.putError("consultaExpediente","No se ha podido consultar el expediente " + e)
            correoUtil.envioEmailErrores("consultaExpediente", "No se ha podido consultar el expediente ", e)
            return null
        }
    }

    def consultaExpedienteCodigoST(String codigoST, UnidadOrganizativa pais) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(pais, filtro)
    }

    def consultaExpedienteNumSolicitud(String requestNumber, UnidadOrganizativa pais, String codigoST) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.CLIENTE)
        filtro.setValor(codigoST)
        Filtro filtroRelacionado = new Filtro()
        filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
        filtroRelacionado.setValor(requestNumber)
        filtro.setFiltroRelacionado(filtroRelacionado)
        return consultaExpediente(pais, filtro)
    }

    /**
     * Obtener Expedientes en formato Informe (Más reducido)
     *
     * @param companya (Código ST de la compañía)
     * @param servicioScor (Código ST de servicio SCOR)
     * @param estado (0 abiertos, 1 Cerrados, 2 Anulados, 3 Cerrados y Anulados)
     * @param fechaIni
     * @param fechaFin
     * @param pais
     * @return
     */
    def obtenerInformeExpedientes(String companya, String servicioScor, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientes(obtenerUsuarioFrontal(pais), companya, servicioScor, estado, fechaIni, fechaFin)
            return salida.listaExpedientesInforme
        } catch (Throwable e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente", e)
            return null
        }
    }

    /**
     * Obtener Expedientes de Siniestro en formato Informe (Más reducido)
     *
     * @param companya (Código ST de la compañía)
     * @param producto (Código ST del producto)
     * @param estado (0 abiertos, 1 Cerrados, 2 Anulados, 3 Cerrados y Anulados)
     * @param fechaIni
     * @param fechaFin
     * @param pais
     * @return
     */
    def obtenerInformeExpedientesSiniestros(String companya, String producto, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientesSiniestros(obtenerUsuarioFrontal(pais), companya, producto, estado, fechaIni, fechaFin)
            return salida.listaExpedientes
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return null
        }
    }

    def informeExpedientePorFiltro(Filtro filtro, UnidadOrganizativa pais) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.informeExpedientesPorFiltro(obtenerUsuarioFrontal(pais),filtro)
            return salida.listaExpedientesInforme
        } catch (Exception e) {
            logginService.putError("informeExpedientePorFiltro de ama","No se ha podido obtener el informe de expediente : " + e)
            return null
        }
    }

    List<Expediente> informeExpedienteCodigoST(String codigoST, UnidadOrganizativa pais) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(pais, filtro)?.getListaExpedientes()
    }

    def modificaExpediente(UnidadOrganizativa pais, Expediente expediente, def servicioScorList, def paqueteScorList) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.modificaExpediente(obtenerUsuarioFrontal(pais), expediente, servicioScorList, paqueteScorList)
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
    boolean crearExpediente(Request req, TipoCompany comp) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientCrearOrabpel")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("orabpelCreacion.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientCrearOrabpel.initiate(crearExpedienteBPM(req, comp))
            return true
        } catch (Exception e) {
            throw new WSException(this.getClass(), "crearExpediente", ExceptionUtils.composeMessage(null, e))
        }
    }

    private def crearExpedienteBPM(Request req, TipoCompany comp) {
        companyService = ServiceFactory.getCompanyImpl(comp)
        def listadoFinal = []
        RootElement payload = new RootElement()
        try {
            String codigoSt = companyService.getCodigoStManual(req)
            listadoFinal.add(buildCabecera(req, codigoSt))
            listadoFinal.add(companyService.buildDatos(req, codigoSt))
            listadoFinal.add(buildPie(null))
            payload.cabeceraOrDATOSOrPIE = listadoFinal
        } catch (Exception e) {
           logginService.putError("crearExpedienteBPM","Error en el metodo crearExpedienteBPM: " + e)
        }
        return payload
    }

    private def buildCabecera(Request req, String codigoSt) {
        def formato = new SimpleDateFormat("yyyyMMdd")
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

    UnidadOrganizativa obtenerUnidadOrganizativa(TipoCompany tipo) {
        Company company = Company.findByNombre(tipo.nombre)
        return company?.ou
    }

    Usuario obtenerUsuarioFrontal(UnidadOrganizativa unidadOrganizativa) {
        def usuario = new Usuario()

        switch(unidadOrganizativa) {
            case UnidadOrganizativa.ES:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "7Q%NN!v5"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "ES"
                    usuario.usuario = "usuarioBPM"
                } else {
                    usuario.clave = "Wcbhjfod!"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "ES"
                    usuario.usuario = "gcaballero-es"
                }
                break
            case UnidadOrganizativa.IT:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "sc5t4!QAZ123"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "IT"
                    usuario.usuario = "adminitalia"
                } else {
                    usuario.clave = "P@ssword"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "IT"
                    usuario.usuario = "admin-ITA"
                }
                break
            case UnidadOrganizativa.FR:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "5#6GAkXP456"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "FR"
                    usuario.usuario = "webclientesFR"
                } else {
                    usuario.clave = "5#6GAkXP456"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "FR"
                    usuario.usuario = "webclientesFR"
                }
                break
            default:
                break
        }
        return usuario
    }

    def busquedaCrm(Request requestBBDD, Company company, String requestNumber, String certificateNumber, String policyNumber) {
        task {

            String opername = "ExpedienteService BusquedaCrm"
            String logExpediente = getLogExpediente(policyNumber, requestNumber, certificateNumber, company.codigoSt)
            logginService.putInfoMessage(opername+" - Buscando en CRM solicitud de "+logExpediente)
            RespuestaCRM respuestaCrm
            int limite = 1
            boolean encontrado = false
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            Filtro filtro = getFiltradoCRM(policyNumber, requestNumber, certificateNumber, company.codigoSt)
            Thread.currentThread().sleep(25000)

            try {
                while( !encontrado && limite < 20) {
                    Thread.currentThread().sleep(5000)
                    respuestaCrm = consultaExpediente(company.ou, filtro)
                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {
                        for (Expediente exp: respuestaCrm.getListaExpedientes()) {
                            logginService.putInfoMessage(opername+" - Expediente encontrado: " + exp.getCodigoST() + " para " + company.nombre)

                            String fechaCreacion = format.format(new Date())
                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(company.codigoSt) &&
                                    fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())){
                                /**Alta procesada correctamente*/
                                encontrado = true
                                logginService.putInfoMessage(opername+" - Nueva alta automatica de "+logExpediente+" procesada correctamente. Verificado tras "+limite+" intentos")
                            }
                        }
                    }
                    limite++
                }

                /**Alta procesada pero no se ha encontrado en CRM.*/
                if (limite == 10) {
                    logginService.putInfoMessage(opername+" - Nueva alta de "+logExpediente+" se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores(opername,"Nueva alta de "+logExpediente+" se ha procesado pero no se ha dado de alta en CRM",null)
                    requestService.insertarError(company.id, requestNumber, requestBBDD.request, TipoOperacion.ALTA, "Peticion procesada para solicitud: "+logExpediente+". Error: No encontrada en CRM")
                }
            } catch (Exception e) {
                logginService.putInfoMessage(opername+" - Nueva alta de "+logExpediente+". Error: " + e.getMessage())
                correoUtil.envioEmailErrores(opername,"Nueva alta de "+logExpediente,e)
            }
        }
    }

    private String getLogExpediente(String numPoliza, String numSolicitud, String numCertificado, String codigoStCompany) {
        String logExpediente = codigoStCompany
        if(numPoliza) {
            logExpediente = logExpediente.concat(" con numPoliza: " + numPoliza)
        }
        if(numSolicitud) {
            logExpediente = logExpediente.concat(" con numSolicitud: " + numSolicitud)
        }
        if(numCertificado) {
            logExpediente = logExpediente.concat(" con numCertificado: " + numCertificado)
        }
        return logExpediente
    }

    private Filtro getFiltradoCRM(String numPoliza, String numSolicitud, String numCertificado, String codigoStCompany) {
        Filtro filtro = new Filtro()
        if(numPoliza) {
            filtro.setClave(ClaveFiltro.NUM_POLIZA)
            filtro.setValor(numPoliza)
        } else if(codigoStCompany && numSolicitud) {
            filtro.setClave(ClaveFiltro.CLIENTE)
            filtro.setValor(codigoStCompany)
            Filtro filtroRelacionado = new Filtro()
            filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
            filtroRelacionado.setValor(numSolicitud)
            if(numCertificado) {
                Filtro filtroRelacionado2 = new Filtro()
                filtroRelacionado2.setClave(ClaveFiltro.NUM_CERTIFICADO)
                filtroRelacionado2.setValor(numCertificado)
                filtroRelacionado.setFiltroRelacionado(filtroRelacionado2)
            }
            filtro.setFiltroRelacionado(filtroRelacionado)
        }
        return filtro
    }
}

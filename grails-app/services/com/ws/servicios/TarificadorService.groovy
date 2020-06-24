package com.ws.servicios

import com.scor.comprimirdocumentos.ParametrosEntrada
import com.scortelemed.Conf
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.FetchUtilLagunaro
import hwsol.webservices.ScorExpedienteTarificado
import org.apache.commons.codec.binary.Base64
import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro
import servicios.Filtro as FiltroGorm
import servicios.Usuario as UsuarioGorm

import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

import static grails.async.Promises.task

class TarificadorService {

    /*
     Se conecta al CRM y devuelve los expedientes tarificados para una fecha
     */
    def grailsApplication
    def expedienteService
    def hwSoapClientService
    def fetchUtil = new FetchUtilLagunaro()
    def soapAlptisRecetteWSPRO
    def soapAlptisRecetteWS
    def logginService = new LogginService()
    CorreoUtil correoUtil = new CorreoUtil()
    def tarificador = { fecha ->
        def listaExpedientes = []
        try {
            def expediente = new ScorExpedienteTarificado()
            def xmlResultado = ""
            def tarificador = "  Tarificad o "

            tarificador = tarificador.trim().toLowerCase()

            /////////////////////////////////////////////////////////////////////////
            def sqlXml = fetchUtil.dameExpedientesTarificados(fecha, "1004")
            def crmService = new ServiceCrmService()
            xmlResultado = crmService.conexion(sqlXml)
            listaExpedientes = fetchUtil.rellenaExpedientesTarificados(xmlResultado)

            //Buscamos si tienen algun paquete o servicios asociados
            for (int i = 0; i < listaExpedientes.size(); i++) {
                expediente = listaExpedientes[i]
                sqlXml = fetchUtil.dameCoberturasExpediente((String) expediente.id)
                xmlResultado = crmService.conexion(sqlXml)
                expediente = fetchUtil.rellenaCoberturasExpediente(xmlResultado, expediente)
                sqlXml = fetchUtil.dameExpedientePaquete((String) expediente.id)

                xmlResultado = crmService.conexion(sqlXml)
                expediente = fetchUtil.rellenaExpedientePaquete(xmlResultado, expediente)


                if (expediente.codTipoRMedico) {
                    sqlXml = fetchUtil.dameServiciosPaquete((String) expediente.codTipoRMedico, "1004")
                    xmlResultado = crmService.conexion(sqlXml)
                    expediente = fetchUtil.rellenaServiciosPaquete(xmlResultado, expediente)
                }

                sqlXml = fetchUtil.dameExpedienteServicio((String) expediente.id)
                xmlResultado = crmService.conexion(sqlXml)
                listaExpedientes[i] = fetchUtil.rellenaExpedienteServicio(xmlResultado, expediente)

                def nodo = obtenerNodoConsultaExpediente(expediente.scorName.toString())
                if (nodo) {
                    expediente = asignarZipExpediente(expediente, nodo)
                }
            }

        } catch (Exception e) {
            logginService.putError("tarificador", "No se ha podido obtener la lista de expedientes: " + e)
            e.printStackTrace()
        }

        return listaExpedientes

    }

    /*
     * Borra las estadisticas de una Requests pasandole el ID
     */

    def borrar(idReq) {
        def req = Request.get(idReq)
        def estadisticas = Estadistica.findAllByRequest(req)
        estadisticas.each { it.delete() }
    }

    /*
     * Obtiene las claves definidas en la tabla Datowebservice
     */

    def obtenerClaves(opera) {
        def operacion = Operacion.findByClave(opera)
        def claves = Datowebservice.findAllByOperacion(operacion)

    }

    /*
     * Obtiene las datos directamente de un mensaje dato
     */

    def obtenerDatos(String mensaje) {

        def mapaEsta = [:]
        def rootNode = new XmlSlurper().parseText(mensaje)
        def body = rootNode.Body
        re(body.GestionReconocimientoMedicoRequest, mapaEsta)

        return mapaEsta
    }

    /*
     * Crea un mapa con los datos
     */

    def obtenMapa(nodo, mapaEsta, nombre) {
        nodo.childNodes().each {
            if (it.childNodes().hasNext() != false) {
                re(it, mapaEsta, nombre)
            } else {
                mapaEsta.put(nombre + "_" + it.name(), it.text())
            }

        }
    }

    def re(nodo, mapaEsta) {
        obtenMapa(nodo.datos_envio, mapaEsta, nodo.datos_envio.name())
        obtenMapa(nodo.poliza, mapaEsta, nodo.poliza.name())
        obtenMapa(nodo.asegurado, mapaEsta, nodo.asegurado.name())
        obtenMapa(nodo.agente, mapaEsta, nodo.agente.name())
        obtenServicios(nodo.reconocimiento_medico, mapaEsta, nodo.reconocimiento_medico.name(), 0)
    }

    def obtenServicios(nodo, mapaServicio, nombre, i) {
        def att
        nodo.childNodes().each {
            i++
            if (it.childNodes().hasNext() != false) {
                if (it.name() == "prueba_medica") {
                    att = it.attributes()
                    mapaServicio.put(nombre + "_" + it.name() + "#" + i, att.codigo_prueba_medica)
                }
                obtenServicios(it, mapaServicio, nombre, i - 1)
            } else {
                mapaServicio.put(nombre + "_" + it.name() + "#" + i, it.text())
            }
        }
    }

    def obtenerTipoReconocomiento = { expediente ->
        def result = ""

        if (expediente.tipoRMedico) {
            if (expediente.tipoRMedico == "19a" || expediente.tipoRMedico == "19b")
                result = "19"
            else if (expediente.tipoRMedico == "16a" || expediente.tipoRMedico == "16b")
                result = "16"
            else if (expediente.tipoRMedico == "06a" || expediente.tipoRMedico == "06b")
                result = "06"
            else
                result = expediente.tipoRMedico
        } else {
            if (expediente.pruebasMedicas) {
                if (expediente.pruebasMedicas.size() <= 2) {
                    result = expediente.pruebasMedicas[0].codigo
                    expediente.pruebasMedicas.each {
                        if (it.codigo == "15") {
                            result = "12"
                        }
                    }
                }
            }
        }

        return result
    }

    def obtenerNodoConsultaExpediente(String idExpediente) {
        try {
            def usuario = new UsuarioGorm()
            usuario.clave = Conf.findByName("frontal.clave")?.value
            usuario.dominio = Conf.findByName("frontal.dominio")?.value
            usuario.usuario = Conf.findByName("frontal.usuario")?.value

            def filtro = new FiltroGorm()
            filtro.clave = "EXPEDIENTE"
            filtro.valor = idExpediente

            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)

            def salida = grailsApplication.mainContext.soapClientAlptis.consultaExpediente(usuario, filtro)
//(countryName:"Spain")

            if (salida.listaExpedientes) {
                return salida.listaExpedientes[0].nodoAlfresco
            } else {
                return false
            }
        } catch (Exception e) {
            logginService.putError("obtenerNodoConsultaExpediente", "No se ha podido obtener el nodo de expedientes " + idExpediente + ": " + e)
        }
    }

    def asignarZipExpediente(ScorExpedienteTarificado expediente, String nodo) {
        def response
        try {
            expediente.zip = obtenerZip(nodo).toString()

            return expediente
        } catch (Exception e) {
            logginService.putError("asignarZipExpediente", "No se ha podido asignar el zip del expediente : " + e)
            response = false
        }
    }

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

    def obtenerInformeExpedientes(String codigost, String arg2, int arg3, String fechaIni, String fechaFin, String pais) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientes(obtenerUsuarioFrontal(pais), codigost, arg2, arg3, fechaIni, fechaFin)
            return salida.listaExpedientesInforme
        } catch (Throwable e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente : " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente", e)
            return false
        }
    }

    def obtenerInformeExpedientesSiniestros(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {

        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientesSiniestros(obtenerUsuarioFrontal(arg6), arg1, arg2, arg3, arg4, arg5)
            return salida.listaExpedientes
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente : " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return false
        }
    }

    def obtenerInformeCitas(String arg1, String arg2, String arg3, String arg4, String arg5) {

        def response

        try {

            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)

            def salida = grailsApplication.mainContext.soapClientAlptis.informeCitasPorCia(obtenerUsuarioFrontal(arg5), arg1, arg2, arg3, arg4)

            return salida.listaCitas
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente : " + e)
            response = false
        }
    }

    def obtenerInformeActividades(String arg1, String arg2, String arg3, String arg4, String arg5) {

        def response

        try {

            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)

            def salida = grailsApplication.mainContext.soapClientAlptis.informeActividadesPorCia(obtenerUsuarioFrontal(arg5), arg1, arg2, arg3, arg4)

            return salida.listaActividades
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente : " + e)
            response = false
        }
    }

    def modificaExpediente(String arg1, Expediente arg2, String arg3, String arg4) {

        def response

        try {

            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)

            def salida = grailsApplication.mainContext.soapClientAlptis.modificaExpediente(obtenerUsuarioFrontal(arg1), arg2, arg3, arg4)

            return salida
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido ejecutar la operacion de modificacion : " + e)
            response = false
        }
    }

    def obtenerXMLExpedientePorZip(resultComprimidos) {
        StringBuilder sb = new StringBuilder()
        byte[] decodedBytes = Base64.decodeBase64(resultComprimidos.getBytes("UTF-8"))
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(decodedBytes))
        ZipEntry entrada

        while ((entrada = zis.getNextEntry()) != null) {
            String filename = entrada.getName()

            logginService.putInfoMessage("El nombre del fichero es: " + filename)

            if (filename.contains("xml")) {
                Reader r = new InputStreamReader(zis, "UTF-8");  //or whatever encoding
                char[] buf = new char[1024]
                int amt = r.read(buf)
                while (amt > 0) {
                    sb.append(buf, 0, amt)
                    amt = r.read(buf)
                }
            }
        }

        zis.close()

        return sb.toString().replace('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>', "")
    }

    def notificarCaseResultsAlptis() {
        def contadorResultados = 0
        def i = 0
        def soap = soapAlptisRecetteWS

        if (Environment.current.name.equals("production_wildfly")) {
            soap = soapAlptisRecetteWSPRO
        }

        def responseRecette = soap.send(connectTimeout: 600000, readTimeout: 600000) {
            body {
                AlptisUnderwrittingCaseResultsRequest(xmlns: "http://www.scortelemed.com/schemas/alptis") {
                    resulExpedienteSoap.each { item ->
                        tuw_cases() {
                            if (item[0][0].toString().contains('tuw')) {
                                mkp.yieldUnescaped(item[0][0].toString())
                                zip(resulComprimidos[i])
                                contadorResultados++
                            }
                            i++
                        }
                    }
                    dateRequested(params.date.toString())
                    comments("ok")
                }
            }
        }
    }

    def obtenerUsuarioFrontal(String unidadOrganizativa) {
        def usuario = new UsuarioGorm()

        if (unidadOrganizativa.equals("FR")) {
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
        }

        if (unidadOrganizativa.equals("ES")) {
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
        }

        if (unidadOrganizativa.equals("IT")) {
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
        }

        return usuario
    }

    def busquedaAfiescaCrm(policyNumber, ou, opername, companyCodigoSt, companyId, requestBBDD) {

        task {

            logginService.putInfoMessage("Buscando en CRM solicitud de AFiesca con policyNumber: " + policyNumber.toString())

            def respuestaCrm
            int limite = 0;
            boolean encontrado = false;

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            def correoUtil = new CorreoUtil()

            Thread.sleep(90000);

            try {
                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.CLIENTE);
                    filtro.setValor(companyCodigoSt.toString());
                    servicios.Filtro filtroRelacionado = new servicios.Filtro()
                    filtroRelacionado.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
                    filtroRelacionado.setValor(policyNumber.toString())
                    filtro.setFiltroRelacionado(filtroRelacionado)

                    respuestaCrm = expedienteService.consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true

                            }
                        }
                    }

                    if (encontrado) {

                        logginService.putInfoMessage("Nueva alta automatica de Afiesca con numero de solicitud " + policyNumber.toString() + " procesada correctamente")

                    }

                    limite++
                    Thread.sleep(10000)
                }

                /**Alta procesada pero no se ha encontrado en CRM.
                 *
                 */
                if (limite == 10) {

                    logginService.putInfoMessage("Nueva alta de Afiesca con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores(opername, "Nueva alta de Afiesca con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(companyCodigoSt.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para solitud: " + companyCodigoSt.toString() + ". Error: No encontrada en CRM")
                    error.save(flush: true)
                }

            } catch (Exception e) {

                logginService.putErrorMessage("Nueva alta de Afiesca con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage())
                correoUtil.envioEmailErrores(opername, "Nueva alta de Afiesca con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage(), null)

            }

        }
    }

    def busquedaAlptisCrm(policyNumber, ou, opername, companyCodigoSt, companyId, requestBBDD) {

        task {

            logginService.putInfoMessage("Buscando en CRM solicitud de Alptis con policyNumber: " + policyNumber.toString())

            def respuestaCrm
            int limite = 0;
            boolean encontrado = false;

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            CorreoUtil correoUtil = new CorreoUtil()

            Thread.sleep(90000);

            try {

                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.NUM_POLIZA);
                    filtro.setValor(policyNumber.toString());

                    respuestaCrm = expedienteService.consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumPoliza() != null && exp.getNumPoliza().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true

                            }
                        }
                    }

                    if (encontrado) {

                        logginService.putInfoMessage("Nueva alta automatica de Alptis con numero de solicitud: " + policyNumber.toString() + " procesada correctamente")

                    }

                    limite++
                    Thread.sleep(10000)

                }

                /**Alta procesada pero no se ha encontrado en CRM.
                 *
                 */
                if (limite == 10) {

                    logginService.putInfoMessage("Nueva alta de Alptis con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores(opername, " Nueva alta de Alptis con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(policyNumber.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para solitud: " + policyNumber.toString() + ". Error: No encontrada en CRM")
                    error.save(flush: true)

                }


            } catch (Exception e) {

                logginService.putErrorMessage("Nueva alta de Alptis con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage())
                correoUtil.envioEmailErrores(opername, "Nueva alta de Alptis con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage(), null)

            }

        }

    }

    def busquedaLifesquareCrm(policyNumber, ou, opername, companyCodigoSt, companyId, requestBBDD) {

        task {

            logginService.putInfoMessage("Buscando en CRM solicitud de Lifesaure con policyNumber: " + policyNumber.toString())

            def respuestaCrm
            int limite = 0;
            boolean encontrado = false;

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            CorreoUtil correoUtil = new CorreoUtil()

            Thread.sleep(90000);

            try {

                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.NUM_POLIZA);
                    filtro.setValor(policyNumber.toString());

                    respuestaCrm = expedienteService.consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumPoliza() != null && exp.getNumPoliza().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true

                            }
                        }
                    }

                    if (encontrado) {
                        logginService.putInfoMessage("Nueva alta automatica de Lifesquare con numero de solicitud: " + policyNumber.toString() + " procesada correctamente")
                    }

                    limite++
                    Thread.sleep(10000)

                }

                /**Alta procesada pero no se ha encontrado en CRM.
                 *
                 */
                if (limite == 10) {

                    logginService.putInfoMessage("Nueva alta automatica de Lifesquare con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores(opername, "Nueva alta automatica de Lifesquare con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(policyNumber.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para solitud: " + policyNumber.toString() + ". No encontrada en CRM")
                    error.save(flush: true)

                }

            } catch (Exception e) {

                logginService.putErrorMessage("Nueva alta automatica de Lifesquare con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage())
                correoUtil.envioEmailErrores(opername, "Nueva alta automatica de Lifesquare con numero de solicitud: " + policyNumber.toString() + " no se ha procesado: Motivo: " + e.getMessage(), null)
            }

        }
    }

}
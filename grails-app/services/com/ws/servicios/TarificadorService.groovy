package com.ws.servicios

import com.scortelemed.Company
import com.scortelemed.TipoCompany
import com.ws.enumeration.UnidadOrganizativa
import com.ws.servicios.impl.comprimidos.CommonZipService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.FetchUtilLagunaro
import hwsol.webservices.ScorExpedienteTarificado
import org.apache.commons.codec.binary.Base64
import servicios.Expediente

import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class TarificadorService {

    /*
     Se conecta al CRM y devuelve los expedientes tarificados para una fecha
     */
    CommonZipService commonZipService
    def grailsApplication
    def expedienteService
    def requestService
    def fetchUtil = new FetchUtilLagunaro()
    def logginService = new LogginService()
    CorreoUtil correoUtil = new CorreoUtil()

    def tarificador(Date fecha) {
        Company company = Company.findByNombre(TipoCompany.LAGUN_ARO.nombre) //Lagunaro
        def listaExpedientes = []
        try {
            def sqlXml = fetchUtil.dameExpedientesTarificados(fecha, company.codigoSt)
            def crmService = new ServiceCrmService()
            def xmlResultado = crmService.conexion(sqlXml)
            listaExpedientes = fetchUtil.rellenaExpedientesTarificados(xmlResultado)

            //Buscamos si tienen algun paquete o servicios asociados
            for (ScorExpedienteTarificado expediente:listaExpedientes) {
                sqlXml = fetchUtil.dameCoberturasExpediente((String) expediente.id)
                xmlResultado = crmService.conexion(sqlXml)
                expediente = fetchUtil.rellenaCoberturasExpediente(xmlResultado, expediente)
                sqlXml = fetchUtil.dameExpedientePaquete((String) expediente.id)
                xmlResultado = crmService.conexion(sqlXml)
                expediente = fetchUtil.rellenaExpedientePaquete(xmlResultado, expediente)


                if (expediente.codTipoRMedico) {
                    sqlXml = fetchUtil.dameServiciosPaquete((String) expediente.codTipoRMedico, company.codigoSt)
                    xmlResultado = crmService.conexion(sqlXml)
                    expediente = fetchUtil.rellenaServiciosPaquete(xmlResultado, expediente)
                }

                sqlXml = fetchUtil.dameExpedienteServicio((String) expediente.id)
                xmlResultado = crmService.conexion(sqlXml)
                expediente = fetchUtil.rellenaExpedienteServicio(xmlResultado, expediente)

                def nodo = obtenerNodoConsultaExpediente(expediente.scorName.toString(), UnidadOrganizativa.ES)
                if (nodo) {
                    logginService.putInfo("tarificador", "Generando ZIP")
                    expediente.zip = commonZipService.obtenerZip(nodo)
                    logginService.putInfo("tarificador", "Zip generado correctamente")
                }
            }

        } catch (Exception e) {
            logginService.putError("tarificador", "No se ha podido obtener la lista de expedientes: " + e)
            e.printStackTrace()
        }

        return listaExpedientes

    }

    private def obtenerNodoConsultaExpediente(String idExpediente, UnidadOrganizativa pais) {
        try {
            List<Expediente> salida = new ArrayList<>()
            salida.addAll(expedienteService.informeExpedienteCodigoST(idExpediente, pais))
            if (salida?.size() > 0) {
                return salida?.get(0).nodoAlfresco
            } else {
                return null
            }
        } catch (Exception e) {
            logginService.putError("obtenerNodoConsultaExpediente", "No se ha podido obtener el nodo de expedientes " + idExpediente + ": " + e)
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

    /**
    // Crea un mapa con los datos

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

    */

    /**
     * NO UTILIZADO
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

    def notificarCaseResultsAlptis() {
        def contadorResultados = 0
        def i = 0
        def soap = soapAlptisRecetteWS

        if (Environment.current.name.equals("production")) {
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
            if (Environment.current.name.equals("production")) {
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
            if (Environment.current.name.equals("production")) {
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
            if (Environment.current.name.equals("production")) {
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
    } */
}
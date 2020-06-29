package com.ws.servicios.impl.companies

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.Request
import com.scortelemed.schemas.methislab.*
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCasesResultsResponse.Expediente
import com.ws.servicios.ICompanyService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class MethislabService implements ICompanyService{

    TransformacionUtil util = new TransformacionUtil()
    def grailsApplication
    def requestService
    def expedienteService
    def logginService
    def tarificadorService

    @Override
    String marshall(def objeto) {
        String nameSpace = "http://www.scortelemed.com/schemas/methislab"
        String result
        try {
            if (objeto instanceof MethislabUnderwrittingCaseManagementRequest) {
                result = requestService.marshall(nameSpace, objeto, MethislabUnderwrittingCaseManagementRequest.class)
            } else if (objeto instanceof MethislabUnderwrittingCasesResultsRequest) {
                result = requestService.marshall(nameSpace, objeto, MethislabUnderwrittingCasesResultsRequest.class)
            }
        } finally {
            return result
        }
    }

    @Override
    def buildDatos(Request req, String codigoSt) {
        try {
            DATOS dato = new DATOS()
            Company company = req.company
            dato.registro = rellenaDatos(req, company)
            //dato.pregunta = rellenaPreguntas(req, company.nombre)
            dato.servicio = rellenaServicios(req, company.nombre)
            dato.coberturas = rellenaCoberturas(req)
            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
    }

    @Override
    def getCodigoStManual(Request req) {
        return null
    }

    def rellenaDatosSalidaConsulta(expedientePoliza, requestDate, logginService) {

        Expediente expediente = new Expediente()

        expediente.setRequestDate(requestDate)
        expediente.setRequestNumber(util.devolverDatos(expedientePoliza.getNumSolicitud()))
        expediente.setRequestState(devolverStateType(expedientePoliza.getCodigoEstado()))
        expediente.setProductCode(util.devolverDatos(expedientePoliza.getProducto().getCodigoProductoCompanya()))
        expediente.setPolicyNumber(util.devolverDatos(expedientePoliza.getNumPoliza()))
        expediente.setCertificateNumber(util.devolverDatos(expedientePoliza.getNumCertificado()))


        if (expedientePoliza.getCandidato() != null) {
            expediente.setFiscalIdentificationNumber(expedientePoliza.getCandidato().getNumeroDocumento())
            expediente.setMobilePhone(util.devolverTelefonoMovil(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber1(util.devolverTelefono1(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber2(util.devolverTelefono2(expedientePoliza.getCandidato()))
        } else {
            expediente.setFiscalIdentificationNumber("")
            expediente.setMobilePhone("")
            expediente.setPhoneNumber1("")
            expediente.setPhoneNumber2("")
        }

        byte[] compressedData = tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

        expediente.setZip(compressedData)

        expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

        if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {

            expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

                BenefitsType benefitsType = new BenefitsType()

                benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
                benefitsType.setBenefictCode(util.devolverDatos(coberturasPoliza.getCodigoCobertura()))
                benefitsType.setBenefictCapital(util.devolverDatos(coberturasPoliza.getCapitalCobertura()))

                BenefictResultType benefictResultType = new BenefictResultType()

                benefictResultType.setDescResult(util.devolverDatos(coberturasPoliza.getResultadoCobertura()))
                benefictResultType.setResultCode(util.devolverDatos(coberturasPoliza.getCodResultadoCobertura()))

                benefictResultType.setPremiumLoading(util.devolverDatos(coberturasPoliza.getValoracionPrima()))
                benefictResultType.setCapitalLoading(util.devolverDatos(coberturasPoliza.getValoracionCapital()))
                benefictResultType.setDescPremiumLoading("")
                benefictResultType.setDescCapitalLoading("")

                benefictResultType.exclusions = util.fromStringLoList(coberturasPoliza.getExclusiones())
                benefictResultType.temporalLoading = util.fromStringLoList(coberturasPoliza.getValoracionTemporal())
                benefictResultType.medicalReports = util.fromStringLoList(coberturasPoliza.getInformesMedicos())
                //benefictResultType.medicalTest = util.fromStringLoList(coberturasPoliza.getInformes)
                benefictResultType.notes = util.fromStringLoList(coberturasPoliza.getNotas())

                benefitsType.setBenefictResult(benefictResultType)

                expediente.getBenefitsList().add(benefitsType)
            }
        }

        return expediente
    }

    def rellenaDatos(req, company) {

        def mapDatos = [:]
        def listadoPreguntas = []
        def formato = new SimpleDateFormat("yyyyMMdd")
        def apellido
        def telefono1
        def telefono2
        def telefonoMovil
        def productCia
        def nombreAgente

        REGISTRODATOS datosRegistro = new REGISTRODATOS()

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("CandidateInformation")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode

                    /**NUMERO DE PRODUCTO
                     *
                     */



                    datosRegistro.codigoProducto = "EUROVITAPEN"

                    if (eElement.getElementsByTagName("productCode").item(0) != null) {
                        datosRegistro.campo8 = eElement.getElementsByTagName("productCode").item(0).getTextContent()
                    }

                    /**NOMBRE DE CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("name").item(0) != null) {
                        datosRegistro.nombreCliente = eElement.getElementsByTagName("name").item(0).getTextContent()
                    }

                    /**APELLIDO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("surname").item(0) != null) {
                        apellido = eElement.getElementsByTagName("surname").item(0).getTextContent()
                    }

                    datosRegistro.apellidosCliente = apellido

                    /**DNI CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) != null) {
                        datosRegistro.dni = eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent()
                    }

                    /**SEXO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("gender").item(0) != null) {
                        datosRegistro.sexo = eElement.getElementsByTagName("gender").item(0).getTextContent() == "M" ? "M" : "V"
                    } else {
                        datosRegistro.sexo = "M"
                    }

                    /**DIRECCION CLIENTE**/

                    if (eElement.getElementsByTagName("address").item(0) != null) {
                        datosRegistro.direccionCliente = eElement.getElementsByTagName("address").item(0).getTextContent()
                    } else {
                        datosRegistro.direccionCliente = "."
                    }

                    /**CODIGO POSTAL CLIENTE
                     *
                     */

                    if (eElement.getElementsByTagName("postalCode").item(0) != null) {
                        datosRegistro.codigoPostal = eElement.getElementsByTagName("postalCode").item(0).getTextContent()
                    } else {
                        datosRegistro.codigoPostal = "."
                    }

                    /**POBLACION
                     *
                     */

                    if (eElement.getElementsByTagName("city").item(0) != null) {
                        datosRegistro.poblacion = eElement.getElementsByTagName("city").item(0).getTextContent()
                    } else {
                        datosRegistro.poblacion = "."
                    }
                    /**PROVINCIA
                     *
                     */

                    if (eElement.getElementsByTagName("province").item(0) != null) {
                        datosRegistro.provincia = eElement.getElementsByTagName("province").item(0).getTextContent()
                    } else {
                        datosRegistro.provincia = "."
                    }

                    /**TELEFONOS
                     */

                    if (eElement.getElementsByTagName("phoneNumber1").item(0) != null && eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent().isEmpty()) {

                        telefono1 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()

                        if (telefono1 != null && !telefono1.isEmpty() && (telefono1.startsWith("0039") || telefono1.startsWith("+39"))) {
                            datosRegistro.telefono1 = telefono1
                        } else if (telefono1 != null && !telefono1.isEmpty()) {
                            datosRegistro.telefono1 = "0039" + telefono1
                        } else {
                            datosRegistro.telefono1 = null
                        }
                    }

                    if (eElement.getElementsByTagName("phoneNumber2").item(0) != null && eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().isEmpty()) {

                        telefono2 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()

                        if (telefono2 != null && !telefono2.isEmpty() && (telefono2.startsWith("0039") || telefono2.startsWith("+39"))) {
                            datosRegistro.telefono2 = telefono2
                        } else if (telefono2 != null && !telefono2.isEmpty()) {
                            datosRegistro.telefono2 = "0039" + telefono2
                        } else {
                            datosRegistro.telefono2 = null
                        }
                    }

                    if (eElement.getElementsByTagName("mobileNumber").item(0) != null) {

                        telefonoMovil = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()

                        if (telefonoMovil != null && !telefonoMovil.isEmpty() && (telefonoMovil.startsWith("0039") || telefonoMovil.startsWith("+39"))) {
                            telefonoMovil = telefonoMovil
                        } else if (telefonoMovil != null && !telefonoMovil.isEmpty()) {
                            telefonoMovil = "0039" + telefonoMovil
                        } else {
                            telefonoMovil = null
                        }
                    }

                    if (telefonoMovil != null && !telefonoMovil.isEmpty()) {
                        datosRegistro.telefono1 = telefonoMovil
                    }

                    if (datosRegistro.telefono1 == null || datosRegistro.telefono1.isEmpty()) {
                        if (datosRegistro.telefono3 != null && !datosRegistro.telefono3.isEmpty()) {
                            datosRegistro.telefono1 = datosRegistro.telefono3
                        } else if (datosRegistro.telefono2 != null && !datosRegistro.telefono2.isEmpty()) {
                            datosRegistro.telefono1 = datosRegistro.telefono2
                        } else {
                            datosRegistro.telefono1 = "999999999"
                        }
                    }

                    /**CODIGO CIA
                     *
                     */

                    datosRegistro.codigoCia = company.codigoSt

                    /**FECHA DE NACIMIENTO
                     *
                     */

                    if (eElement.getElementsByTagName("birthDate").item(0) != null && !eElement.getElementsByTagName("birthDate").item(0).getTextContent().isEmpty()) {
                        datosRegistro.fechaNacimiento = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar().getTime())
                    } else {
                        datosRegistro.fechaNacimiento = formato.format(util.fromStringToXmlCalendar("2017-01-01T00:00:00").toGregorianCalendar().getTime())
                    }

                    /**ESTADO CIVIL
                     *
                     */

                    if (eElement.getElementsByTagName("civilState").item(0) != null) {
                        datosRegistro.estadoCivil = eElement.getElementsByTagName("civilState").item(0).getTextContent()
                    }

                    /**EMAIL
                     *
                     */

                    if (eElement.getElementsByTagName("email").item(0) != null) {
                        datosRegistro.email = eElement.getElementsByTagName("email").item(0).getTextContent()
                    }

                    /**CLAVE DE VALIDACION
                     *
                     */
                    if (eElement.getElementsByTagName("password").item(0) != null) {
                        datosRegistro.claveValidacionCliente = eElement.getElementsByTagName("password").item(0).getTextContent()
                    }

                    /**POLIZA
                     *
                     */

                    if (eElement.getElementsByTagName("policyNumber").item(0) != null) {
                        datosRegistro.numPoliza = eElement.getElementsByTagName("policyNumber").item(0).getTextContent()
                    }

                    /**CERTIFICADO
                     *
                     */

                    if (eElement.getElementsByTagName("certificateNumber").item(0) != null) {
                        datosRegistro.numCertificado = eElement.getElementsByTagName("certificateNumber").item(0).getTextContent()
                    }

                    /**CERTIFICADO
                     *
                     */

                    if (eElement.getElementsByTagName("comments").item(0) != null) {
                        datosRegistro.observaciones = eElement.getElementsByTagName("comments").item(0).getTextContent()
                    }

                    /**FECHA DE SOLICITUD
                     *
                     */

                    if (eElement.getElementsByTagName("requestDate").item(0) != null && !eElement.getElementsByTagName("requestDate").item(0).getTextContent().isEmpty()) {
                        datosRegistro.fechaEnvio = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("requestDate").item(0).getTextContent()).toGregorianCalendar().getTime())
                    } else {
                        datosRegistro.fechaEnvio = util.fromDateToString(new Date(), "yyyyMMdd")
                    }
                    /**NUMERO DE REFERENCIA
                     *
                     */

                    if (eElement.getElementsByTagName("requestNumber").item(0) != null) {
                        datosRegistro.numSolicitud = eElement.getElementsByTagName("requestNumber").item(0).getTextContent()
                    }

                    /**CODIGO DE AGENTE
                     *
                     */

                    if (eElement.getElementsByTagName("agent").item(0) != null) {

                        if (eElement.getElementsByTagName("agent").item(0).getTextContent().toString().length() > 20) {
                            datosRegistro.codigoAgencia = eElement.getElementsByTagName("agent").item(0).getTextContent().substring(0, 19)
                        } else {
                            datosRegistro.codigoAgencia = eElement.getElementsByTagName("agent").item(0).getTextContent()
                        }

                        datosRegistro.nomApellAgente = eElement.getElementsByTagName("agent").item(0).getTextContent()
                    } else {

                        datosRegistro.codigoAgencia = "."
                        datosRegistro.nomApellAgente = "."
                    }

                    datosRegistro.nomApellAgente = nombreAgente
                }
            }

            setCamposGenericos(datosRegistro)

            return datosRegistro
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }


    private void setCamposGenericos(REGISTRODATOS datos) {

        datos.lugarNacimiento = ""
        datos.pais = "IT"
        datos.emailAgente = ""
        datos.tipoCliente = "N"
        datos.franjaHoraria = ""
        datos.codigoCuestionario = ""
        datos.campo1 = "it"
        datos.campo2 = ""
        datos.campo3 = "IT"
        datos.campo4 = ""
        datos.campo5 = ""
        datos.campo6 = ""
        datos.campo7 = ""
        datos.campo8 = ""
        datos.campo9 = ""
        datos.campo10 = ""
        datos.campo11 = ""
        datos.campo12 = ""
        datos.campo13 = ""
        datos.campo14 = ""
        datos.campo15 = ""
        datos.campo16 = ""
        datos.campo17 = ""
        datos.campo18 = ""
        datos.campo19 = ""
        datos.campo20 = ""
    }

    private def rellenaServicios(req, nameCompany) {

        def listadoServicios = []

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("ServiceInformation")

            if (nList != null && nList.length > 0) {

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp)

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode

                        if (eElement.getElementsByTagName("serviceCode").item(0) != null) {

                            DATOS.Servicio servicio = new DATOS.Servicio()

                            servicio.codigoServicio = eElement.getElementsByTagName("serviceCode").item(0).getTextContent()
                            servicio.tipoServicios = "S"
                            if (eElement.getElementsByTagName("serviceDescription").item(0) != null) {
                                servicio.descripcionServicio = eElement.getElementsByTagName("serviceDescription").item(0).getTextContent()
                            }

                            servicio.filler = ""
                            listadoServicios.add(servicio)
                        }
                    }
                }
            } else {

                DATOS.Servicio servicio = new DATOS.Servicio()

                servicio.codigoServicio = "TS"
                servicio.tipoServicios = "S"
                servicio.descripcionServicio = "Teleselezione"
                servicio.filler = ""

                listadoServicios.add(servicio)
            }

            return listadoServicios
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }

    private def rellenaPreguntas(req, nameCompany) {

        def listadoPreguntas = []

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("CandidateInformation")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode

                    /**PREGUNTAS PREVIAS
                     *
                     */

                    if (eElement.getElementsByTagName("igp").item(0) != null && !eElement.getElementsByTagName("igp").item(0).getTextContent().isEmpty()) {
                        DATOS.Pregunta pregunta = new DATOS.Pregunta()
                        pregunta.codigoPregunta = "igp"
                        pregunta.tipoDatos = "STRING"
                        pregunta.respuesta = eElement.getElementsByTagName("igp").item(0).getTextContent().equals("S") ? "SI" : "NO"
                        listadoPreguntas.add(pregunta)
                    }
                    if (eElement.getElementsByTagName("agente").item(0) != null && !eElement.getElementsByTagName("agente").item(0).getTextContent().isEmpty()) {
                        DATOS.Pregunta pregunta = new DATOS.Pregunta()
                        pregunta.codigoPregunta = "agente"
                        pregunta.tipoDatos = "STRING"
                        pregunta.respuesta = eElement.getElementsByTagName("agente").item(0).getTextContent()
                        listadoPreguntas.add(pregunta)
                    }
                }
            }

            return listadoPreguntas
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e))
        }
    }

    private def rellenaCoberturas(req) {

        def listadoCoberturas = []
        def capital

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("BenefitsType")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode

                    DATOS.Coberturas cobertura = new DATOS.Coberturas()

                    /**COBERTURAS QUE NOS LLEGA SIEMPRE ES COB5
                     *
                     */
                    cobertura.filler = ""
                    cobertura.codigoCobertura = eElement.getElementsByTagName("benefictCode").item(0).getTextContent().toUpperCase()
                    cobertura.nombreCobertura = eElement.getElementsByTagName("benefictName").item(0).getTextContent()
                    cobertura.capital = Float.parseFloat(eElement.getElementsByTagName("benefictCapital").item(0).getTextContent())

                    listadoCoberturas.add(cobertura)

                    capital = Float.parseFloat(eElement.getElementsByTagName("benefictCapital").item(0).getTextContent())
                }
            }

            return listadoCoberturas
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }

    def busquedaCrm(policyNumber, ou, requestNumber, opername, companyCodigoSt, companyId, requestBBDD, certificateNumber, String companyName) {

        task {

            logginService.putInfoMessage("BusquedaExpedienteCrm - Buscando en CRM solicitud de " + companyName + " con numero de solicitud: " + requestNumber + " y num. certificado: " + certificateNumber.toString())

            def respuestaCrm
            int limite = 0
            boolean encontrado = false

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            CorreoUtil correoUtil = new CorreoUtil()

            Thread.sleep(90000)


            try {


                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.CLIENTE)
                    filtro.setValor(companyCodigoSt.toString())

                    servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
                    filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
                    filtroRelacionado1.setValor(requestNumber.toString())

                    servicios.Filtro filtroRelacionado2 = new servicios.Filtro()
                    filtroRelacionado2.setClave(servicios.ClaveFiltro.NUM_CERTIFICADO)
                    filtroRelacionado2.setValor(certificateNumber.toString())
                    filtroRelacionado1.setFiltroRelacionado(filtroRelacionado2)

                    filtro.setFiltroRelacionado(filtroRelacionado1)

                    respuestaCrm = expedienteService.consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            logginService.putInfoMessage("BusquedaExpedienteCrm - Expediente encontrado: " + exp.getCodigoST() + " para " + companyName)

                            String fechaCreacion = format.format(new Date())

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(requestNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura()) && exp.getNumCertificado() != null && exp.getNumCertificado().equals(certificateNumber)) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true
                                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta automatica de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString() + " procesada correctamente")

                            }
                        }
                    }


                    limite++
                    Thread.sleep(10000)
                }

                /**Alta procesada pero no se ha encontrado en CRM.
                 *
                 */
                if (limite == 10) {

                    logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(requestNumber.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString() + ". No encontrada en CRM")
                    error.save(flush: true)
                }
            } catch (Exception e) {

                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString() + ". Error: " + e.getMessage())
                correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " y num. certificado: " + certificateNumber.toString(), e)
            }
        }
    }

    def devolverStateType(estado) {

        switch (estado) {
            case "CERRADO": return RequestStateType.CLOSED
            case "ANULADO": return RequestStateType.CANCELLED
            case "RECHAZADO": return RequestStateType.REJECTED
            default: return null
        }

    }

    def devolverNombreCobertura(codigo) {

        if (codigo.equals("COB5")) {
            return BenefictNameType.DEAD
        } else if (codigo.equals("COB4")) {
            return BenefictNameType.DISABILITY_30
        } else if (codigo.equals("COB2")) {
            return BenefictNameType.ACCIDENTAL_DEAD
        } else {
            return null
        }
    }

    /**Devuelve lista con los errores de validacion
     *
     * @param requestBBDD
     * @return
     */
    List<WsError> validarDatosObligatorios(requestBBDD) {

        List<WsError> wsErrors = new ArrayList<WsError>()
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd")
        String telefono1 = null
        String telefono2 = null
        String telefonoMovil = null
        String fechaNacimiento = null

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(requestBBDD.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("CandidateInformation")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode

                    /**CODIGO DE PRODUCTO
                     *
                     */

                    if (eElement.getElementsByTagName("productCode").item(0) == null || eElement.getElementsByTagName("productCode").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("productCode", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**NOMBRE DE CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("name").item(0) == null || eElement.getElementsByTagName("name").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("name", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**APELLIDO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("surname").item(0) == null || eElement.getElementsByTagName("surname").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("surname", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**DNI CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) == null || eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("fiscalIdentificationNumber", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**SEXO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("gender").item(0) == null || eElement.getElementsByTagName("gender").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("gender", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**PAIS
                     *
                     */

                    if (eElement.getElementsByTagName("country").item(0) != null && !eElement.getElementsByTagName("country").item(0).getTextContent().isEmpty()) {

                        if (!eElement.getElementsByTagName("country").item(0).getTextContent().equals("IT")) {

                            wsErrors.add(new WsError("country", eElement.getElementsByTagName("country").item(0).getTextContent(), "Valori possibili (IT)"))
                            break
                        }

                    } else {
                        wsErrors.add(new WsError("country", null, "L'elemento non può essere nullo"))
                        break
                    }

                    /**TELEFONOS
                     *
                     */

                    if (eElement.getElementsByTagName("phoneNumber1").item(0) != null && eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent().isEmpty()) {
                        telefono1 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("phoneNumber2").item(0) != null && eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().isEmpty()) {
                        telefono2 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("mobileNumber").item(0) != null && eElement.getElementsByTagName("mobileNumber").item(0).getTextContent() != null && !eElement.getElementsByTagName("mobileNumber").item(0).getTextContent().isEmpty()) {
                        telefonoMovil = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()
                    }

                    if (telefono1 == null && telefono2 == null && telefonoMovil == null) {
                        wsErrors.add(new WsError("telefono", null, "Ci deve essere un telefono di contatto"))
                        break
                    }

                    /**FECHA DE NACIMIENTO
                     *
                     */

                    if (eElement.getElementsByTagName("birthDate").item(0) == null || eElement.getElementsByTagName("birthDate").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("birthDate", null, "L'elemento non può essere nullo"))
                        break
                    } else {
                        try {
                            formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar().getTime())
                        } catch (Exception e) {
                            wsErrors.add(new WsError("birthDate", eElement.getElementsByTagName("birthDate").item(0).getTextContent(), "Formato della data yyyy-MM-dd"))
                        }


                    }

                }
            }

            return wsErrors

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }
}
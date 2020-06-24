package com.ws.servicios

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.Request
import com.scortelemed.schemas.caser.*
import com.scortelemed.schemas.caser.ConsultaExpedienteResponse.ExpedienteConsulta
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoResponse.Expediente
import hwsol.webservices.CorreoUtil
import hwsol.webservices.GenerarZip
import hwsol.webservices.TransformacionUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import servicios.RespuestaCRM

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.namespace.QName
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class CaserService implements ICompanyService{

    TransformacionUtil util = new TransformacionUtil()
    def grailsApplication
    def logginService
    def requestService
    def expedienteService
    GenerarZip generarZip = new GenerarZip()
    def tarificadorService
    TransformacionUtil transformacionUtil = new TransformacionUtil()
    CorreoUtil correoUtil = new CorreoUtil()

    @Override
    String marshall(String nameSpace, def objeto) {
        String result
        try {
            if (objeto instanceof ResultadoReconocimientoMedicoRequest) {
                result = requestService.marshall(nameSpace, objeto, ResultadoReconocimientoMedicoRequest.class)
            } else if (objeto instanceof GestionReconocimientoMedicoRequest) {
                result = requestService.marshall(nameSpace, objeto, GestionReconocimientoMedicoRequest.class)
            } else if (objeto instanceof ConsultaExpedienteRequest) {
                result = requestService.marshall(nameSpace, objeto, ConsultaExpedienteRequest.class)
            } else if (objeto instanceof ConsolidacionPolizaRequest) {
                result = requestService.marshall(nameSpace, objeto, ConsolidacionPolizaRequest.class)
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
            dato.coberturas = rellenaCoberturas(req)
            dato.registro = rellenaDatos(req, company, dato.coberturas)
            dato.pregunta = rellenaPreguntas(req, company.nombre)
            dato.servicio = rellenaServicios(req)
            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
    }

    @Override
    def getCodigoStManual(Request req) {
        return null
    }

    def rellenaDatosSalida(expedientePoliza, requestDate, logginService) {

        Expediente expediente = new Expediente()

        expediente.setRequestDate(requestDate)
        expediente.setRequestNumber(util.devolverDatos(expedientePoliza.getNumSolicitud()))
        expediente.setRequestState(util.devolverStateType(expedientePoliza.getCodigoEstado()))
        expediente.setProductCode(util.devolverDatos(expedientePoliza.getProducto().getCodigoProductoCompanya()))
        expediente.setPolicyNumber(util.devolverDatos(expedientePoliza.getNumPoliza()))
        expediente.setCertificateNumber(util.devolverDatos(expedientePoliza.getNumCertificado()))


        if (expedientePoliza.getCandidato() != null) {
            expediente.setFiscalIdentificationNumber(expedientePoliza.getCandidato().getNumeroDocumento())
            expediente.setMobilePhone(util.devolverTelefonoMovil(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber1(util.devolverTelefono1(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber2(util.devolverTelefono2(expedientePoliza.getCandidato()))
            expediente.setDomicilio(expedientePoliza?.getCandidato()?.getDireccion())
            expediente.setCodPostal(expedientePoliza?.getCandidato()?.getCodigoPostal())
            expediente.setLocalidad(expedientePoliza?.getCandidato()?.getLocalidad())
            expediente.setProvincia(expedientePoliza?.getCandidato()?.getProvincia())
        } else {
            expediente.setFiscalIdentificationNumber("")
            expediente.setMobilePhone("")
            expediente.setPhoneNumber1("")
            expediente.setPhoneNumber2("")
            expediente.setDomicilio("")
            expediente.setCodPostal("")
            expediente.setLocalidad("")
            expediente.setProvincia("")
        }

        byte[] compressedData = tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

        expediente.setZip(compressedData)

        expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

        if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {

            expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

                BenefitsType benefitsType = new BenefitsType()

                benefitsType.setBenefictName(util.devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
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

    def rellenaDatosSalidaConsulta(expedientePoliza, requestDate, logginService) {

        ExpedienteConsulta expediente = new ExpedienteConsulta()

        expediente.setRequestDate(requestDate)
        expediente.setRequestNumber(util.devolverDatos(expedientePoliza.getNumSolicitud()))
        expediente.setRequestState(expedientePoliza.getCodigoEstado().toString())
        expediente.setProductCode(util.devolverDatos(expedientePoliza.getProducto().getCodigoProductoCompanya()))
        expediente.setPolicyNumber(util.devolverDatos(expedientePoliza.getNumPoliza()))
        expediente.setCertificateNumber(util.devolverDatos(expedientePoliza.getNumCertificado()))

        if (expedientePoliza.getCandidato() != null) {
            expediente.setFiscalIdentificationNumber(expedientePoliza.getCandidato().getNumeroDocumento())
            expediente.setMobilePhone(util.devolverTelefonoMovil(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber1(util.devolverTelefono1(expedientePoliza.getCandidato()))
            expediente.setPhoneNumber2(util.devolverTelefono2(expedientePoliza.getCandidato()))
            expediente.setDomicilio(expedientePoliza?.getCandidato()?.getDireccion())
            expediente.setCodPostal(expedientePoliza?.getCandidato()?.getCodigoPostal())
            expediente.setLocalidad(expedientePoliza?.getCandidato()?.getLocalidad())
            expediente.setProvincia(expedientePoliza?.getCandidato()?.getProvincia())
        } else {
            expediente.setFiscalIdentificationNumber("")
            expediente.setMobilePhone("")
            expediente.setPhoneNumber1("")
            expediente.setPhoneNumber2("")
            expediente.setDomicilio("")
            expediente.setCodPostal("")
            expediente.setLocalidad("")
            expediente.setProvincia("")
        }

        byte[] compressedData = tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

        expediente.setZip(compressedData)

        expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

        if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {


            expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

                BenefitsType benefitsType = new BenefitsType()

                benefitsType.setBenefictName(util.devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
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
                //				benefictResultType.medicalTest = util.fromStringLoList(coberturasPoliza.getInformes())
                benefictResultType.notes = util.fromStringLoList(coberturasPoliza.getNotas())

                benefitsType.setBenefictResult(benefictResultType)

                expediente.getBenefitsList().add(benefitsType)
            }
        }

        return expediente
    }

    def envioEmail(req) {

        def datosEmail = rellenoDatosEmail(req)
        def textEmail = compongoText(datosEmail)

        logginService.putInfo('Envio email modificacion Caser', textEmail)
        correoUtil.envioEmail('Modificacion caso', textEmail, 0)
    }

    String compongoText(datosEmail){
        StringBuilder sb = new StringBuilder()
        sb.append("El Dossier del cliente ${datosEmail.get('nombre')}, ${datosEmail.get('apellido')} con DNI  ${datosEmail.get('dni')}")
        sb.append("\n")
        sb.append("Request Number es : ${datosEmail.get('requestNumber')}")
        sb.append("\n")
        sb.append("Policy Number number es : ${datosEmail.get('policyNumber')}")
        sb.append("\n")
        sb.append("Certificate Number es : ${datosEmail.get('certificateNumber')}")
        sb.append("\n")
        sb.append("La modificacion es la siguiente :  ${datosEmail.get('comments')}")
        return sb.toString()
    }
    def rellenoDatosEmail(req) {

        def datosEmail = [:]
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        DocumentBuilder builder = factory.newDocumentBuilder()

        InputSource is = new InputSource(new StringReader(req.request))
        is.setEncoding("UTF-8")
        Document doc = builder.parse(is)

        doc.getDocumentElement().normalize()

        NodeList nList = doc.getElementsByTagName("CandidateInformation")
        def nombre, apellido, dni, requestNumber, policyNumber, certificateNumber, comments
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp)
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode
                nombre = eElement.getElementsByTagName("name").item(0).getTextContent()
                apellido = eElement.getElementsByTagName("surname").item(0).getTextContent()
                dni = eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent()
            }
        }
        nList = doc.getElementsByTagName("PolicyHolderInformation")

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp)
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode
                requestNumber = eElement.getElementsByTagName("requestNumber").item(0).getTextContent()
                policyNumber = eElement.getElementsByTagName("policyNumber").item(0).getTextContent()
                certificateNumber = eElement.getElementsByTagName("certificateNumber").item(0).getTextContent()
                comments = eElement.getElementsByTagName("comments").item(0).getTextContent()
            }
        }

        datosEmail.put('nombre', nombre)
        datosEmail.put('apellido', apellido)
        datosEmail.put('dni', dni)
        datosEmail.put('requestNumber', requestNumber)
        datosEmail.put('policyNumber', policyNumber)
        datosEmail.put('certificateNumber', certificateNumber)
        datosEmail.put('comments', comments)

        return datosEmail

    }

    def rellenaDatos(req, company, coberturas) {

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



                    List<DATOS.Coberturas> coberturasList = coberturas
                    for (int i = 0; i < coberturasList.size(); i++) {

                        if (coberturasList.get(i).getCodigoCobertura().equalsIgnoreCase('Cob5') || coberturasList.get(i).getCodigoCobertura().equalsIgnoreCase('Cob05')) {
                            logginService.putInfo("rellenaDatos", "Vino ${coberturasList.get(i).getCodigoCobertura()} ** pongo producto SRP")
                            datosRegistro.codigoProducto = "SRP"
                            break
                        } else if (coberturasList.get(i).getCodigoCobertura().equalsIgnoreCase('Cob1') || coberturasList.get(i).getCodigoCobertura().equalsIgnoreCase('Cob01')) {
                            if (grails.util.Environment.current.name == "production_wildfly") {
                                logginService.putInfo("rellenaDatos", "Vino  ${coberturasList.get(i).getCodigoCobertura()} ** pongo producto 5441")
                                datosRegistro.codigoProducto = "5441" //REAL
                            } else {
                                logginService.putInfo("rellenaDatos", "Vino  ${coberturasList.get(i).getCodigoCobertura()} ** pongo producto 3635 para prepro")
                                datosRegistro.codigoProducto = "3635" //PREPRO
                            }
                        } else {
                            logginService.putError("rellenaDatos", "    *****    ")
                            logginService.putError("rellenaDatos", "El expediente trajo la cobertura : ${coberturasList.get(i).getCodigoCobertura()}, pero no se usa para seleccionar el producto.")
                            logginService.putError("rellenaDatos", "    *****    ")
                        }
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
                     *
                     */

                    if (eElement.getElementsByTagName("phoneNumber1").item(0) != null && eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent().isEmpty()) {
                        telefono1 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()
                        datosRegistro.telefono1 = telefono1
                    }

                    if (eElement.getElementsByTagName("phoneNumber2").item(0) != null && eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().isEmpty()) {
                        telefono2 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()
                        datosRegistro.telefono2 = telefono2
                    }

                    if (eElement.getElementsByTagName("mobileNumber").item(0) != null && eElement.getElementsByTagName("mobileNumber").item(0).getTextContent() != null && !eElement.getElementsByTagName("mobileNumber").item(0).getTextContent().isEmpty()) {
                        telefonoMovil = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()
                        datosRegistro.telefono3 = telefonoMovil
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

                    /**EDAD ACTUARIAL
                     *
                     */
                    if (eElement.getElementsByTagName("actuarialAge").item(0) != null && !eElement.getElementsByTagName("actuarialAge").item(0).getTextContent().isEmpty()) {

                        datosRegistro.edadActuarial = Integer.parseInt(eElement.getElementsByTagName("actuarialAge").item(0).getTextContent())

                    } else {

                        datosRegistro.edadActuarial = util.calcularEdadActuarial(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar())

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
                }
            }

            nList = doc.getElementsByTagName("PolicyHolderInformation")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

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

                    if (eElement.getElementsByTagName("requestDate").item(0) != null) {
                        datosRegistro.fechaEnvio = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("requestDate").item(0).getTextContent()).toGregorianCalendar().getTime())
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

                    if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) != null) {
                        datosRegistro.codigoAgencia = eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent()
                    }

                    /**NOMBRE DE AGENTE
                     *
                     */
                    if (eElement.getElementsByTagName("name").item(0) != null) {
                        nombreAgente = eElement.getElementsByTagName("name").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("surname1").item(0) != null) {
                        nombreAgente = nombreAgente + " " + eElement.getElementsByTagName("surname1").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("surname2").item(0) != null) {
                        nombreAgente = nombreAgente + " " + eElement.getElementsByTagName("surname2").item(0).getTextContent()
                    }

                    datosRegistro.nomApellAgente = nombreAgente
                }
            }

            setCamposGenericos(datosRegistro, productCia)

            return datosRegistro
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }


    private void setCamposGenericos(REGISTRODATOS datos, String productCia) {

        datos.lugarNacimiento = ""
        datos.pais = "ES"
        datos.emailAgente = ""
        datos.tipoCliente = "N"
        datos.franjaHoraria = ""
        datos.codigoCuestionario = ""
        datos.campo1 = "es"
        datos.campo2 = ""
        datos.campo3 = "ES"
        datos.campo4 = ""
        datos.campo5 = ""
        datos.campo6 = productCia
        datos.campo7 = ""
        datos.campo8 = productCia
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

    private def rellenaServicios(req) {

        def listadoServicios = []

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("ServiceInformation")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode

                    if (eElement.getElementsByTagName("serviceCode").item(0) != null) {

                        DATOS.Servicio servicio = new DATOS.Servicio()

                        servicio.codigoServicio = eElement.getElementsByTagName("serviceCode").item(0).getTextContent()
                        servicio.tipoServicios = eElement.getElementsByTagName("serviceType").item(0).getTextContent()
                        if (eElement.getElementsByTagName("serviceDescription").item(0) != null) {
                            servicio.descripcionServicio = eElement.getElementsByTagName("serviceDescription").item(0).getTextContent()
                        }

                        servicio.filler = ""
                        listadoServicios.add(servicio)
                    }
                }
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

                    Element eElement = (Element) nNode;

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
            throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e));
        }
    }

    private def rellenaCoberturas(req) {

        def listadoCoberturas = []
        def capital
        String codigoProducto

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            /**Primero tenemos que saber que producto nos llega para actuar en consecuencia
             *
             */

            NodeList nListP = doc.getElementsByTagName("CandidateInformation")

            for (int tempP = 0; tempP < nListP.getLength(); tempP++) {

                Node nNode = nListP.item(tempP)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    /**NUMERO DE PRODUCTO
                     *                    */

                    if (eElement.getElementsByTagName("productCode").item(0) != null) {
                        if (eElement.getElementsByTagName("productCode").item(0).getTextContent().toString().equals("1190")) {
                            codigoProducto = "SRP"
                        } else {
                            codigoProducto = "5441" //REAL
                            //codigoProducto = "3635" //PREPRO
                        }
                    }
                }
            }

            /**Calculamos las coberturaqs en base al producto
             *
             */

            NodeList nList = doc.getElementsByTagName("BenefitsType")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

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

            if (codigoProducto.equals("SRP")) {

                DATOS.Coberturas cobertura_1 = new DATOS.Coberturas()

                cobertura_1.filler = ""
                cobertura_1.codigoCobertura = "COB2"
                cobertura_1.nombreCobertura = "ACCIDENTE"
                cobertura_1.capital = capital

                listadoCoberturas.add(cobertura_1)

                DATOS.Coberturas cobertura_2 = new DATOS.Coberturas()

                cobertura_2.filler = ""
                cobertura_2.codigoCobertura = "COB4"
                cobertura_2.nombreCobertura = "INVALIDEZ"
                cobertura_2.capital = capital

                listadoCoberturas.add(cobertura_2)
            }


            return listadoCoberturas

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    List<servicios.Expediente> existeExpediente(String numeroSolicitud, String nombreCia, String companyCodigoSt, String unidadOrganizativa) {

        logginService.putInfoMessage("Buscando si existe expediente con numero de solicitud " + numeroSolicitud + " para " + nombreCia)

        servicios.Filtro filtro = new servicios.Filtro()
        List<servicios.Expediente> expedientes = new ArrayList<servicios.Expediente>()
        RespuestaCRM respuestaCrm

        try {

            filtro.setClave(servicios.ClaveFiltro.CLIENTE)
            filtro.setValor(companyCodigoSt)

            servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
            filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
            filtroRelacionado1.setValor(numeroSolicitud)
            filtro.setFiltroRelacionado(filtroRelacionado1)

            respuestaCrm = expedienteService.consultaExpediente(unidadOrganizativa.toString(), filtro)

            if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                    servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                    if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt) && exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(numeroSolicitud)) {

                        logginService.putInfoMessage("Expediente con número de poliza " + numeroSolicitud + " y expediente " + exp.getCodigoST() + " para " + nombreCia + " ya existe en el sistema")
                        expedientes.add(respuestaCrm.getListaExpedientes().get(i))
                    }
                }
            } else {
                logginService.putInfoMessage("Expediente con número de poliza " + numeroSolicitud + " para " + nombreCia + " no se existe en el sistema")
            }

        } catch (Exception e) {
            logginService.putInfoMessage("Buscando si existe expediente con numero de poliza " + numeroSolicitud + " para " + nombreCia + " . Error: " + +e.getMessage())
            correoUtil.envioEmailErrores("ERROR en búsqueda de duplicados para " + nombreCia, "Buscando si existe expediente con numero de poliza " + numeroSolicitud + " para " + nombreCia, e)
        }

        return expedientes
    }

    def busquedaCrm(policyNumber, ou, requestNumber, opername, companyCodigoSt, companyId, requestBBDD, certificateNumber, companyName) {
        task {
            logginService.putInfoMessage("BusquedaExpedienteCrm - Buscando en CRM solicitud de " + companyName + " con numSolicitud: " + policyNumber.toString() + ", referencia: " + certificateNumber)
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

                    respuestaCrm = consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            logginService.putInfoMessage("BusquedaExpedienteCrm - Expediente encontrado: " + exp.getCodigoST() + " para " + companyName)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(requestNumber.toString()) && exp.getNumPoliza() != null &&
                                    exp.getNumPoliza().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura()) && exp.getNumCertificado() != null && exp.getNumCertificado().equals(certificateNumber)) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true
                                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta automatica de " + companyName + " con numero de solicitud: " + requestNumber.toString() + " procesada correctamente")
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

                    logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(requestNumber.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para numero de solicitud: " + policyNumber.toString() + ". No encontrada en CRM")
                    error.save(flush: true)
                }
            } catch (Exception e) {

                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString() + ". Error: " + e.getMessage())
                correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString(), e)

            }
        }
    }


    private def obtenerProductos(req, nameCompany) {
        return null
    }

    void insertarRecibido(Company company, String identificador, String info, String operacion) {

        Recibido recibido = new Recibido()
        recibido.setFecha(new Date())
        recibido.setCia(company.id.toString())
        recibido.setIdentificador(identificador)
        recibido.setInfo(info)
        recibido.setOperacion(operacion)
        recibido.save(flush: true)
    }

    void insertarError(Company company, String identificador, String info, String operacion, String detalleError) {

        com.scortelemed.Error error = new com.scortelemed.Error()
        error.setFecha(new Date())
        error.setCia(company.id.toString())
        error.setIdentificador(identificador)
        error.setInfo(info)
        error.setOperacion(operacion)
        error.setError(detalleError)
        error.save(flush: true)

    }

    void insertarEnvio(Company company, String identificador, String info) {

        Envio envio = new Envio()
        envio.setFecha(new Date())
        envio.setCia(company.id.toString())
        envio.setIdentificador(identificador)
        envio.setInfo(info)
        envio.save(flush: true)
    }

}
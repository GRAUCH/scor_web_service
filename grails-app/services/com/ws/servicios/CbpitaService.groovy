package com.ws.servicios

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.global.ZipUtils
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.schemas.cbpita.*
import com.scortelemed.servicios.Candidato
import com.scortelemed.servicios.Frontal
import com.scortelemed.servicios.FrontalServiceLocator
import hwsol.webservices.CorreoUtil
import hwsol.webservices.GenerarZip
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import servicios.RespuestaCRM
import servicios.TipoEstadoExpediente
import servicios.TipoMotivoAnulacion

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.namespace.QName
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class CbpitaService {

    TransformacionUtil util = new TransformacionUtil()
    def grailsApplication
    def logginService = new LogginService()
    GenerarZip generarZip = new GenerarZip()
    def tarificadorService
    TransformacionUtil transformacionUtil = new TransformacionUtil()
    ZipUtils zipUtils = new ZipUtils()
    CorreoUtil correoUtil = new CorreoUtil()

    def marshall(nameSpace, clase) {

        StringWriter writer = new StringWriter();

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(clase.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            def root = null
            QName qName = null

            if (clase instanceof com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest) {
                qName = new QName(nameSpace, "CbpitaUnderwrittingCasesResultsRequest");
                root = new JAXBElement<CbpitaUnderwrittingCasesResultsRequest>(qName, CbpitaUnderwrittingCasesResultsRequest.class, clase);
            }

            if (clase instanceof com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest) {
                qName = new QName(nameSpace, "CbpitaUnderwrittingCaseManagementRequest");
                root = new JAXBElement<CbpitaUnderwrittingCaseManagementRequest>(qName, CbpitaUnderwrittingCaseManagementRequest.class, clase);
            }

            jaxbMarshaller.marshal(root, writer);
            String result = writer.toString();
        } finally {
            writer.close();
        }

        return writer
    }

    com.scortelemed.servicios.RespuestaCRM modificarExpediente(com.scortelemed.servicios.Expediente expediente, String ou) {

        Frontal frontal = instanciarFrontal(Conf.findByName("frontal.wsdl")?.value)

        com.scortelemed.servicios.Usuario usuario = new com.scortelemed.servicios.Usuario()

        usuario.clave = "P@ssword"
        usuario.dominio = "scor.local"
        usuario.unidadOrganizativa = "IT"
        usuario.usuario = "admin-ITA"

        return frontal.modificaExpediente(usuario, expediente, null, null)
    }

    def instanciarFrontal(String frontalPortAddress) {

        FrontalServiceLocator fs = new FrontalServiceLocator();
        fs.setFrontalPortEndpointAddress(frontalPortAddress);
        Frontal frontal = fs.getFrontalPort();

        return frontal;
    }

    List<servicios.Expediente> existeExpediente(String numPoliza, String nombreCia, String companyCodigoSt, String ou) {

        logginService.putInfoMessage("Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia)

        servicios.Filtro filtro = new servicios.Filtro()
        List<servicios.Expediente> expedientes = new ArrayList<servicios.Expediente>()
        RespuestaCRM respuestaCrm

        try {

            filtro.setClave(servicios.ClaveFiltro.CLIENTE);
            filtro.setValor(companyCodigoSt.toString());

            servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
            filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
            filtroRelacionado1.setValor(numPoliza.toString())

            filtro.setFiltroRelacionado(filtroRelacionado1)

            respuestaCrm = consultaExpediente(ou.toString(), filtro)

            if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                    servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                    if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) && exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(numPoliza.toString())) {

                        logginService.putInfoMessage("Expediente con número de poliza " + numPoliza + " y expediente " + exp.getCodigoST() + " para " + nombreCia + " ya existe en el sistema")
                        expedientes.add(respuestaCrm.getListaExpedientes().get(i))
                    }
                }
            } else {

                logginService.putInfoMessage("Expediente con número de poliza " + numPoliza + " para " + nombreCia + " no se existe en el sistema")
            }

        } catch (Exception e) {

            logginService.putInfoMessage("Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia + " . Error: " + +e.getMessage())
            correoUtil.envioEmailErrores("ERROR en búsqueda de duplicados para " + nombreCia, "Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia, e)
        }

        return expedientes
    }

    def crearExpediente = { req ->
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

    def consultaExpediente = { ou, filtro ->

        try {

            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)

            def salida = grailsApplication.mainContext.soapClientAlptis.consultaExpediente(tarificadorService.obtenerUsuarioFrontal(ou), filtro)

            return salida
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientes de Methislab", "No se ha podido obtener el informe de expediente : " + e)
            return null
        }
    }

    private def crearExpedienteBPM = { req ->
        def listadoFinal = []
        RootElement payload = new RootElement()

        listadoFinal.add(buildCabecera(req))
        listadoFinal.add(buildDatos(req, req.company))
        listadoFinal.add(buildPie())

        payload.cabeceraOrDATOSOrPIE = listadoFinal

        return payload
    }

    private def buildCabecera = { req ->
        def formato = new SimpleDateFormat("yyyyMMdd");
        RootElement.CABECERA cabecera = new RootElement.CABECERA()
        cabecera.setCodigoCia(req.company.codigoSt)
        cabecera.setContadorSecuencial("1")
        cabecera.setFechaGeneracion(formato.format(new Date()))
        cabecera.setFiller("")
        cabecera.setTipoFichero("1")

        return cabecera
    }

    private def buildPie = {

        RootElement.PIE pie = new RootElement.PIE()
        pie.setFiller("")
        pie.setNumFilasFichero(100)

        pie.setNumRegistros(1)

        return pie
    }

    private def buildDatos = { req, company ->

        try {

            DATOS dato = new DATOS()

            dato.registro = rellenaDatos(req, company)
            dato.pregunta = rellenaPreguntas(req, company.nombre)
            dato.servicio = rellenaServicios(req, company.nombre)
            dato.coberturas = rellenaCoberturas(req)

            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
        }
    }

    public def rellenaDatos(req, company) {

        def mapDatos = [:]
        def listadoPreguntas = []
        def formato = new SimpleDateFormat("yyyyMMdd");
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

                    Element eElement = (Element) nNode;

                    /**NUMERO DE PRODUCTO
                     *
                     */

                    datosRegistro.codigoProducto = "SRP"


                    if (eElement.getElementsByTagName("productCode").item(0) != null) {
                        datosRegistro.codigoProducto = eElement.getElementsByTagName("productCode").item(0).getTextContent()
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

                        if (telefono1 != null && !telefono1.isEmpty() && (telefono1.startsWith("0039") || telefono1.startsWith("+39"))) {
                            datosRegistro.telefono1 = telefono1
                        } else if (telefono1 != null && !telefono1.isEmpty()) {
                            datosRegistro.telefono1 = "0039" + telefono1
                        } else {
                            datosRegistro.telefono1 = null
                        }
                    }

                    if (eElement.getElementsByTagName("phoneNumber2").item(0) != null
                            && eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null
                            && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().isEmpty()) {

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
                        datosRegistro.codigoAgencia = codificarAgente(eElement.getElementsByTagName("agent").item(0).getTextContent())
                    }

                    /**NOMBRE DE AGENTE

                     *                    */
                    if (eElement.getElementsByTagName("agent").item(0) != null) {
                        nombreAgente = codificarAgente(eElement.getElementsByTagName("agent").item(0).getTextContent())
                    }

                    if (eElement.getElementsByTagName("surname1").item(0) != null) {
                        nombreAgente = nombreAgente + " " + eElement.getElementsByTagName("surname1").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("surname2").item(0) != null) {
                        nombreAgente = nombreAgente + " " + eElement.getElementsByTagName("surname2").item(0).getTextContent()
                    }

                    datosRegistro.nomApellAgente = nombreAgente

                    if (eElement.getElementsByTagName("contactTime").item(0) != null) {
                        if (eElement.getElementsByTagName("contactTime").item(0).getTextContent() == "M") {
                            datosRegistro.observaciones = "Mattina (08:00 a 14:00) C (12:00 a 16:00)"
                        } else if (eElement.getElementsByTagName("contactTime").item(0).getTextContent() == "P") {
                            datosRegistro.observaciones = "Pomeriggio (14:00 a 20:00)"
                        } else if (eElement.getElementsByTagName("contactTime").item(0).getTextContent() == "I") {
                            datosRegistro.observaciones = "Indifferente"
                        } else datosRegistro.observaciones = ""
                    }

                }
            }

            setCamposGenericos(datosRegistro)

            return datosRegistro
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
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

                        Element eElement = (Element) nNode;

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

                servicio.codigoServicio = "TM"
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

            return listadoCoberturas
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    def busquedaCrm(solicitud, ou, companyCodigoSt, companyId, requestBBDD, String nombrecia) {

        task {

            logginService.putInfoMessage("Buscando en CRM solicitud de " + nombrecia + " con requestNumber: " + solicitud.toString())

            def respuestaCrm
            int limite = 0;
            boolean encontrado = false;

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            Thread.sleep(90000);


            try {


                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.CLIENTE);
                    filtro.setValor(companyCodigoSt.toString());

                    servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
                    filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
                    filtroRelacionado1.setValor(solicitud.toString())

                    filtro.setFiltroRelacionado(filtroRelacionado1)

                    respuestaCrm = consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(solicitud.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true
                            }
                        }
                    }

                    if (encontrado) {

                        logginService.putInfoMessage("Nueva alta automatica de " + nombrecia + " con numero de solicitud: " + solicitud.toString() + " procesada correctamente")
                    }

                    limite++
                    Thread.sleep(10000)
                }

                /**Alta procesada pero no se ha encontrado en CRM.
                 *
                 */
                if (limite == 10) {


                    logginService.putInfoMessage("Nueva alta de " + nombrecia + " con numero de solicitud: " + solicitud.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores("ERROR en alta de HMI-CBP", "Nueva alta de " + nombrecia + " con numero de solicitud: " + solicitud.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(solicitud.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para numero de solicitud: " + solicitud.toString() + ". No encontrada en CRM")
                    error.save(flush: true)
                }
            } catch (Exception e) {


                logginService.putErrorMessage("Nueva alta de " + nombrecia + " con numero de solicitud: " + solicitud.toString() + " no se ha procesado: Motivo: " + e.getMessage())
                correoUtil.envioEmailErrores("ERROR en alta de HMI-CBP", "Nueva alta de " + nombrecia + " con numero de solicitud: " + solicitud.toString() + " no se ha procesado: Motivo: " + e.getMessage(), null)

            }
        }
    }

    public void insertarRecibido(Company company, String identificador, String info, String operacion) {

        Recibido recibido = new Recibido()
        recibido.setFecha(new Date())
        recibido.setCia(company.id.toString())
        recibido.setIdentificador(identificador)
        recibido.setInfo(info)
        recibido.setOperacion(operacion)
        recibido.save(flush: true)
    }

    public void insertarError(Company company, String identificador, String info, String operacion, String detalleError) {

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
        envio.hasErrors()
    }

    /**Devuelve lista con los errores de validacion
     *
     * @param requestBBDD
     * @return
     */
    public List<WsError> validarDatosObligatorios(requestBBDD) {

        List<WsError> wsErrors = new ArrayList<WsError>()
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
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

                    Element eElement = (Element) nNode;

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
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    def rellenaDatosSalidaConsulta(servicios.ExpedienteInforme expedientePoliza, codigoSt, requestDate, String zipPath, String user, String password) {

        CbpitaUnderwrittingCasesResultsResponse.Expediente expediente = new CbpitaUnderwrittingCasesResultsResponse.Expediente()

        expediente.setRequestDate(requestDate)
        expediente.setRequestNumber(util.devolverDatos(expedientePoliza.getNumSolicitud()))
        expediente.setRequestState(devolverStateType(expedientePoliza.getCodigoEstado()))

        if (expedientePoliza.getCodigoEstado() == TipoEstadoExpediente.ANULADO && expedientePoliza.getMotivoAnulacion() != TipoMotivoAnulacion.ABIERTO_POR_ERROR) {
            expediente.setCancellationReason(traducirMotivo(expedientePoliza.getMotivoAnulacion().toString()))
        } else {
            expediente.setCancellationReason("")
        }

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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(requestDate.toGregorianCalendar().getTime());

        logginService.putInfoMessage("Iniciado generacion de zip para expediente " + codigoSt)

        if (expedientePoliza.getCodigoEstado() != TipoEstadoExpediente.ANULADO) {
            try {
                byte[] compressedData = zipUtils.generarZips(expedientePoliza, codigoSt, dateString, zipPath, user, password)
                expediente.setZip(compressedData)
            } catch (Exception e) {
                logginService.putError("rellenaDatosSalidaConsulta", "Error:  " + e.getMessage())
                correoUtil.envioEmailErrores("rellenaDatosSalidaConsulta", "COMPRIMIR FICHEROS", e)
                expediente.setZip(new byte[0])
            }
            //zipUtils.eraseFiles(expedientePoliza, zipPath)
        } else {
            expediente.setZip(new byte[0])
        }

        logginService.putInfoMessage("Fin generacion de zip para expediente " + codigoSt)

        expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

        if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {


            expedientePoliza.getCoberturasExpediente().each {
                coberturasPoliza ->

                    BenefitsType benefitsType = new BenefitsType()

                    benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
                    benefitsType.setBenefictCode(util.devolverDatos(coberturasPoliza.getCodigoCobertura()))
                    benefitsType.setBenefictCapital(util.devolverDatos(coberturasPoliza.getCapitalCobertura()))

                    BenefictResultType benefictResultType = new BenefictResultType()

                    benefictResultType.setDescResult(util.devolverDatos(coberturasPoliza.getResultadoCobertura()))
                    benefictResultType.setResultCode(util.codificarResultado(coberturasPoliza.getCodResultadoCobertura()))

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

    public String traducirMotivo(String motivo) {

        String motivoTraducido = null

        switch (motivo) {
            case "DUPLICIDAD":
                motivoTraducido = "Doppione (stesso numero richiesta)"
                break
            case "RECHAZO_DEL_CANDIDATO":
                motivoTraducido = "Assicurto rifiuta la chiamata"
                break
            case "ILOCALIZABLE":
                motivoTraducido = "Assicurato non risponde"
                break
            case "FALTA_DE_DATOS":
                motivoTraducido = "Mancano dati necessari al processo di TUW"
                break
            case "CANDIDATO_NO_ACUDE":
                motivoTraducido = "Assicurato non si presenta"
                break
                break
            case "RECHAZA_PRUEBAS":
                motivoTraducido = "Rifiuta ulteriori esami"
                break
                break
            case "NO_AUTORIZA_LOPD":
                motivoTraducido = "LOPD non autorizzato"
                break
                break
            case "SOLICITUD_DUPLICADA":
                motivoTraducido = "Doppione (stesso numero richiesta)"
                break
                break
            case "ANULADO_POR_LA_COMPANYA":
                motivoTraducido = "Annullata da CBP"
                break
            case "NO_RECIBIDA_DOCUMENTACION_PRELIMINAR":
                motivoTraducido = "Documentazione mancante"
                break
            default:
                break
        }


        return motivoTraducido
    }

    private def obtenerProductos(req, nameCompany) {
        return null
    }

    def devolverStateType(estado) {

        switch (estado) {
            case "CERRADO": return RequestStateType.CLOSED;
            case "ANULADO": return RequestStateType.CANCELLED;
            case "RECHAZADO": return RequestStateType.REJECTED;
            default: return null;
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

    com.scortelemed.servicios.Expediente componerExpedienteModificado(servicios.Expediente expediente, CbpitaUnderwrittingCaseManagementRequest.CandidateInformation infoCandidato) {

        com.scortelemed.servicios.Expediente expedienteModificado = new com.scortelemed.servicios.Expediente()
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String observaciones = ""
        String modificaciones = ""

        expedienteModificado.setCodigoST(expediente.getCodigoST())
        expedienteModificado.setCandidato(new Candidato())
        expedienteModificado.getCandidato().setDireccion(infoCandidato.getAddress())
        expedienteModificado.getCandidato().setCodigoPostal(infoCandidato.getPostalCode())
        expedienteModificado.getCandidato().setProvincia(infoCandidato.getProvince())
        expedienteModificado.getCandidato().setLocalidad(infoCandidato.getCity())

        if (infoCandidato.getPhoneNumber1() != null && !infoCandidato.getPhoneNumber1().toString().isEmpty()) {
            expedienteModificado.getCandidato().setTelefono1("0039" + infoCandidato.getPhoneNumber1().toString())
            modificaciones += "Telefono1: " + "0039" + infoCandidato.getPhoneNumber1().toString() + " "
        }
        if (infoCandidato.getPhoneNumber2() != null && !infoCandidato.getPhoneNumber2().toString().isEmpty()) {
            expedienteModificado.getCandidato().setTelefono2("0039" + infoCandidato.getPhoneNumber2().toString())
            modificaciones += "Telefono2: " + "0039" + infoCandidato.getPhoneNumber1().toString() + " "
        }
        if (infoCandidato.getMobileNumber() != null && !infoCandidato.getMobileNumber().toString().isEmpty()) {
            expedienteModificado.getCandidato().setTelefono3("0039" + infoCandidato.getMobileNumber())
            modificaciones += "Mobile: " + "0039" + infoCandidato.getPhoneNumber1().toString() + " "
        }

        if (expediente.getObservaciones() != null) {
            observaciones = expediente.getObservaciones() + "\n"
            expedienteModificado.setObservaciones(observaciones + formato.format(new Date()) + ": " + modificaciones)
        } else {
            expedienteModificado.setObservaciones(formato.format(new Date()) + ": " + modificaciones)
        }

        return expedienteModificado
    }


    String codificarAgente(String agente) {

        switch (agente) {

            case "300.CBPPIT": return "PITAGORA"
            case "300.CBPSPE": return "SPEFIN"
            case "300.CBPPRO": return "BANCA PROGETTO"
            case "300.CBPWEF": return "WE FINANCE"
            case "300.CBPRAC": return "RACES"
            case "300.CBPSIR": return "SIRIOFIN"
            case "300.CBPDYN": return "DYNAMICA RETAIL"
            case "300.CBPCOF": return "COFIDIS"
            case "300.CBPBPF": return "BANCA POPOLARE DEL FRUSINATE"

            default:
                return agente
        }
    }

}
package com.ws.servicios

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Recibido
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.namespace.QName
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class EnginyersService {

    def grailsApplication
    def logginService = new LogginService()
    def tarificadorService = new TarificadorService()
    TransformacionUtil util = new TransformacionUtil()

    /**
     *
     * @param clase
     * @return
     */
    def marshall(nameSpace, clase) {

        StringWriter writer = new StringWriter();

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(clase.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            def root = null
            QName qName = null

            if (clase instanceof com.scortelemed.schemas.enginyers.AddExp) {
                qName = new QName(nameSpace, "addExp");
                root = new JAXBElement<com.scortelemed.schemas.enginyers.AddExp>(qName, com.scortelemed.schemas.enginyers.AddExp.class, clase);
            }


            jaxbMarshaller.marshal(root, writer);
            String result = writer.toString();

        } finally {
            writer.close();
        }

        return writer
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

        if (Environment.current.name.equals("production_wildfly")) {
            cabecera.setCodigoCia("1071")
        } else {
            cabecera.setCodigoCia("1072")
        }

        cabecera.setContadorSecuencial("1")
        cabecera.setFechaGeneracion(formato.format(new Date()))
        cabecera.setFiller("")
        cabecera.setTipoFichero("1")

        return cabecera

    }

    private def buildDatos = { req, company ->

        try {

            DATOS dato = new DATOS()
            dato.registro = rellenaDatos(req, company)
            //dato.servicio = rellenaServicios(req, company.nombre)
            dato.coberturas = rellenaCoberturas(req)

            return dato
        } catch (Exception e) {
            logginService.putError(e.toString())
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

            NodeList nList = doc.getElementsByTagName("riskTypeElement")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    DATOS.Coberturas cobertura = new DATOS.Coberturas()

                    cobertura.filler = ""
                    cobertura.codigoCobertura = "COB" + eElement.getElementsByTagName("idRisk").item(0).getTextContent()
                    cobertura.nombreCobertura = obtenerNombreCobertura(cobertura.codigoCobertura)
                    cobertura.capital = Float.parseFloat(eElement.getElementsByTagName("riskValue").item(0).getTextContent())

                    listadoCoberturas.add(cobertura)

                }
            }

            return listadoCoberturas

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    private def rellenaServicios(req, nameCompany) {

        def listadoServicios = []

        try {

            DATOS.Servicio servicio = new DATOS.Servicio()
            servicio.tipoServicios = "S"
            servicio.descripcionServicio = "Teleseleccion Mediana"
            servicio.filler = ""
            servicio.codigoServicio = "TM"

            listadoServicios.add(servicio)

            return listadoServicios
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
        }
    }

    String obtenerObservaciones(req) {

        String observaciones

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("additionalField")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

//TODO: en la proxima implementacion sacar el paquete y asegurase que entre el campo observaciones en el sitio correcto.

                    if (eElement.getElementsByTagName("value").item(0) != null && !eElement.getElementsByTagName("value").item(0).getTextContent().isEmpty()) {
                        observaciones = eElement.getElementsByTagName("value").item(0).getTextContent()
                    }

                }
            }

            return observaciones

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    String obtenerNombreCobertura(String codigo) {

        String nombre

        switch (codigo) {
            case "COB1":
                nombre = "Dependencia grave"
                break
            case "COB3":
                nombre = "Enfermedades"
                break
            case "COB430":
                nombre = "Baja Laboral <90 días"
                break
            case "COB431":
                nombre = "Baja Laboral >90 días"
                break
            case "COB432":
                nombre = "Vida"
                break
            case "COB433":
                nombre = "Hospitalización"
                break
            case "COB434":
                nombre = "Renta de Invalidez IPT"
                break
            case "COB435":
                nombre = "Renta de Invalidez IPA"
                break
            case "COB436":
                nombre = "IPT"
                break
            case "COB437":
                nombre = "IPA(Invalidez Absoluta y Permanente)"
                break
            case "COB438":
                nombre = "Gastos Quirúrgicos"
                break
            case "COB439":
                nombre = "Accidentes Vida ASC"
                break
            case "COB440":
                nombre = "Gastos Médicos"
                break
            case "COB441":
                nombre = "Accidentes Invalidez ASC"
                break
            case "COB442":
                nombre = "Circulación Vida"
                break
            case "COB443":
                nombre = "Accidentes Muerte Q<30"
                break
            case "COB444":
                nombre = "Circulación Invalidez"
                break
            case "COB445":
                nombre = "Accidentes Invalidez Q<30"
                break
            case "COB446":
                nombre = "Accidentes Vida ASC Circulación"
                break
            case "COB447":
                nombre = "Accidentes Invalidez ASC Circulación"
                break
            case "COB448":
                nombre = "IPA( Invalidez Absoluta por Accidente)"
                break
            case "COB449":
                nombre = "Vida Accidental"
                break
            case "COB450":
                nombre = "Protesis"
                break
            case "COB451":
                nombre = "Renta Estudios Vida"
                break
            case "COB452":
                nombre = "Renta Estudios IPA"
                break
            case "COB453":
                nombre = "Dependencia servera"
                break
            case "COB454":
                nombre = "Cancer de mama"
                break
        }

    }

    def rellenaDatos(req, company) {

        def mapDatos = [:]
        def listadoPreguntas = []
        def formato = new SimpleDateFormat("yyyyMMdd");
        def apellido
        def telefono1
        def telefono2
        def telefonoMovil
        def productCia
        def nombreAgente
        def eUWReferenceCode
        def codigooficinanuevo

        REGISTRODATOS datosRegistro = new REGISTRODATOS()

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("d")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    /**NUMERO DE PRODUCTO
                     *
                     */
// comento el codigo: porque estan llegando coberturas que no estan contepladas y esta fallado la carga.
//                    if (existeDependencia(req)) {
//                        datosRegistro.codigoProducto = "DEPENDENCIA"
//                    }

//                    if (existeVida(req)) {
                    datosRegistro.codigoProducto = "PENGINYERS"
//                    }

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
                        datosRegistro.apellidosCliente = eElement.getElementsByTagName("surname").item(0).getTextContent()
                    }

                    /**DNI CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("nif").item(0) != null) {
                        datosRegistro.dni = eElement.getElementsByTagName("nif").item(0).getTextContent()
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

                    /**PAIS
                     *
                     */

                    datosRegistro.pais = "ES"

                    /**CIA
                     *
                     */
                    datosRegistro.codigoCia = company.codigoSt

                    /**TELEFONOS
                     *
                     */

                    if (eElement.getElementsByTagName("phone").item(0) != null && eElement.getElementsByTagName("phone").item(0).getTextContent() != null && !eElement.getElementsByTagName("phone").item(0).getTextContent().isEmpty()) {
                        telefono1 = eElement.getElementsByTagName("phone").item(0).getTextContent()
                        datosRegistro.telefono1 = telefono1
                    }

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

                    /**POLIZA
                     *
                     */

                    if (eElement.getElementsByTagName("policyNumber").item(0) != null) {
                        datosRegistro.numPoliza = eElement.getElementsByTagName("policyNumber").item(0).getTextContent()
                    }

                    /**COMENTS
                     *
                     */

                    if (obtenerObservaciones(req) != null) {
                        datosRegistro.observaciones = obtenerObservaciones(req)
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

                    if (eElement.getElementsByTagName("policyNumber").item(0) != null) {
                        datosRegistro.numSolicitud = eElement.getElementsByTagName("policyNumber").item(0).getTextContent()
                    }

                    /**MOVIMIETO
                     *
                     */

                    if (eElement.getElementsByTagName("certificateNumber").item(0) != null) {
                        datosRegistro.numMovimiento = eElement.getElementsByTagName("certificateNumber").item(0).getTextContent()
                    }

                    /**CODIGO DE AGENTE
                     *
                     */

                    if (eElement.getElementsByTagName("agency").item(0) != null) {
                        datosRegistro.codigoAgencia = eElement.getElementsByTagName("agency").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("agent").item(0) != null) {
                        codigooficinanuevo = eElement.getElementsByTagName("agent").item(0).getTextContent()
                    }

                    if (eElement.getElementsByTagName("eUWReferenceCode").item(0) != null) {
                        eUWReferenceCode = eElement.getElementsByTagName("eUWReferenceCode").item(0).getTextContent()
                    }
                }
            }

            camposGenericos(datosRegistro, eUWReferenceCode, codigooficinanuevo)


            return datosRegistro
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }

    }

    private void camposGenericos(REGISTRODATOS datos, eUWReferenceCode, codigoOficina) {

        datos.lugarNacimiento = ""
        datos.emailAgente = ""
        datos.tipoCliente = "N"
        datos.franjaHoraria = ""
        datos.codigoCuestionario = ""
        datos.campo1 = "es"
        datos.campo2 = ""
        datos.campo3 = "ES"
        datos.campo4 = eUWReferenceCode
        datos.campo5 = codigoOficina
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

    private def buildPie = {

        RootElement.PIE pie = new RootElement.PIE()
        pie.setFiller("")
        pie.setNumFilasFichero(100)

        pie.setNumRegistros(1)

        return pie
    }

    def busquedaCrm(policyNumber, ou, opername, companyCodigoSt, companyId, requestBBDD, companyName) {

        task {

            logginService.putInfoMessage("BusquedaExpedienteCrm - Buscando en CRM solicitud de " + companyName + " con numSolicitud: " + policyNumber.toString())

            def respuestaCrm
            int limite = 0;
            boolean encontrado = false;

            servicios.Filtro filtro = new servicios.Filtro()
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            CorreoUtil correoUtil = new CorreoUtil()

            Thread.sleep(90000);

            try {

                while (!encontrado && limite < 10) {

                    filtro.setClave(servicios.ClaveFiltro.CLIENTE);
                    filtro.setValor(companyCodigoSt.toString());

                    servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
                    filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
                    filtroRelacionado1.setValor(policyNumber.toString())

                    filtro.setFiltroRelacionado(filtroRelacionado1)

                    respuestaCrm = consultaExpediente(ou.toString(), filtro)

                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

                        for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

                            servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

                            logginService.putInfoMessage("BusquedaExpedienteCrm - Expediente encontrado: " + exp.getCodigoST() + " para " + companyName)

                            String fechaCreacion = format.format(new Date());

                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
                                    exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())) {

                                /**Alta procesada correctamente
                                 *
                                 */

                                encontrado = true
                                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta automatica de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " procesada correctamente")
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

                    logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM", null)

                    /**Metemos en errores
                     *
                     */
                    com.scortelemed.Error error = new com.scortelemed.Error()
                    error.setFecha(new Date())
                    error.setCia(companyId.toString())
                    error.setIdentificador(policyNumber.toString())
                    error.setInfo(requestBBDD.request)
                    error.setOperacion("ALTA")
                    error.setError("Peticion procesada para numero de solicitud: " + policyNumber.toString() + ". No encontrada en CRM")
                    error.save(flush: true)
                }
            } catch (Exception e) {

                logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " . Error: " + e.getMessage())
                correoUtil.envioEmailErrores("BusquedaExpedienteCrm", "Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString(), e)
            }
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
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente : " + e)
            return null
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

            NodeList nList = doc.getElementsByTagName("d")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    /**NOMBRE DE CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("name").item(0) == null || eElement.getElementsByTagName("name").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("name", null, "Elemento no puede ser nulo"))
                        break
                    }

                    /**APELLIDO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("surname").item(0) == null || eElement.getElementsByTagName("surname").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("surname", null, "Elemento no puede ser nulo"))
                        break
                    }

                    /**DNI CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("nif").item(0) == null || eElement.getElementsByTagName("nif").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("nif", null, "Elemento no puede ser nulo"))
                        break
                    }

                    /**SEXO CANDIDATO
                     *
                     */

                    if (eElement.getElementsByTagName("gender").item(0) == null || eElement.getElementsByTagName("gender").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("gender", null, "Elemento no puede ser nulo"))
                        break
                    }

                    /**CITY
                     *
                     */
                    if (eElement.getElementsByTagName("city").item(0) == null || eElement.getElementsByTagName("city").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("city", null, "Elemento no puede ser nulo"))
                        break
                    }

                    /**TELEFONOS
                     *
                     */

                    if (eElement.getElementsByTagName("phone").item(0) == null || eElement.getElementsByTagName("phone").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("phone", null, "Debe existir un telefono de contacto"))
                        break
                    }

                    /**FECHA DE NACIMIENTO
                     *
                     */

                    if (eElement.getElementsByTagName("birthDate").item(0) == null || eElement.getElementsByTagName("birthDate").item(0).getTextContent().isEmpty()) {
                        wsErrors.add(new WsError("birthDate", null, "Elemento no puede ser nulo"))
                        break
                    } else {
                        try {
                            formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar().getTime())
                        } catch (Exception e) {
                            wsErrors.add(new WsError("birthDate", eElement.getElementsByTagName("birthDate").item(0).getTextContent(), "Formato fecha yyyy-MM-dd"))
                        }
                    }
                }
            }

            return wsErrors
        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }
    }

    private boolean existeDependencia(req) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("riskTypeElement")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    if (eElement.getElementsByTagName("idRisk").item(0).getTextContent().toString().equals("1") || eElement.getElementsByTagName("idRisk").item(0).getTextContent().toString().equals("453")) {
                        return true
                    }
                }
            }

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }

        return false

    }

    private boolean existeVida(req) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
            DocumentBuilder builder = factory.newDocumentBuilder()

            InputSource is = new InputSource(new StringReader(req.request))
            is.setEncoding("UTF-8")
            Document doc = builder.parse(is)

            doc.getDocumentElement().normalize()

            NodeList nList = doc.getElementsByTagName("riskTypeElement")

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp)

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    if (eElement.getElementsByTagName("idRisk").item(0).getTextContent().toString().equals("432") || eElement.getElementsByTagName("idRisk").item(0).getTextContent().toString().equals("449")) {
                        return true
                    }
                }
            }

        } catch (Exception e) {
            throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
        }

        return false

    }
}

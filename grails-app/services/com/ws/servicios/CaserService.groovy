package com.ws.servicios

import com.scor.global.WSException
import com.scor.global.ExceptionUtils
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scor.srpfileinbound.RootElement
import com.scortelemed.schemas.caser.BenefictNameType
import com.scortelemed.schemas.caser.BenefictResultType
import com.scortelemed.schemas.caser.BenefitsType
import com.scortelemed.schemas.caser.ConsolidacionPolizaRequest
import com.scortelemed.schemas.caser.ConsultaExpedienteRequest
import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest
import com.scortelemed.schemas.caser.ConsultaExpedienteResponse.ExpedienteConsulta
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoResponse.Expediente
import com.scortelemed.servicios.Filtro
import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Estadistica
import com.scortelemed.Conf
import com.scortelemed.Recibido

import hwsol.webservices.CorreoUtil
import hwsol.webservices.GenerarZip
import hwsol.webservices.TransformacionUtil
import servicios.Candidato
import servicios.DocumentacionExpedienteInforme
import servicios.RespuestaCRM
import servicios.RespuestaCRMInforme
import servicios.TipoDocumentacion;
import servicios.TipoTelefono;

import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

import javax.servlet.http.HttpSession
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.axis.types.Token
import org.xml.sax.InputSource
import static grails.async.Promises.*
import org.apache.commons.codec.binary.Base64;

class CaserService {

	TransformacionUtil util = new TransformacionUtil()
	def grailsApplication
	def logginService = new LogginService()
	GenerarZip generarZip = new GenerarZip()
	def tarificadorService
	TransformacionUtil transformacionUtil = new TransformacionUtil()

	public def rellenaDatosSalida(expedientePoliza, requestDate, logginService) {

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
		} else {
			expediente.setFiscalIdentificationNumber("")
			expediente.setMobilePhone("")
			expediente.setPhoneNumber1("")
			expediente.setPhoneNumber2("")
		}

		byte[] compressedData=tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

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

	public def rellenaDatosSalidaConsulta(expedientePoliza, requestDate, logginService) {

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
		} else {
			expediente.setFiscalIdentificationNumber("")
			expediente.setMobilePhone("")
			expediente.setPhoneNumber1("")
			expediente.setPhoneNumber2("")
		}

		byte[] compressedData=tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

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

	def marshall (nameSpace, clase){

		StringWriter writer = new StringWriter();

		try{

			JAXBContext jaxbContext = JAXBContext.newInstance(clase.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			def root = null
			QName qName = null

			if (clase instanceof com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest){
				qName = new QName(nameSpace, "ResultadoReconocimientoMedicoRequest");
				root = new JAXBElement<ResultadoReconocimientoMedicoRequest>(qName, ResultadoReconocimientoMedicoRequest.class, clase);
			}

			if (clase instanceof com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest){
				qName = new QName(nameSpace, "GestionReconocimientoMedicoRequest");
				root = new JAXBElement<GestionReconocimientoMedicoRequest>(qName, GestionReconocimientoMedicoRequest.class, clase);
			}

			if (clase instanceof com.scortelemed.schemas.caser.ConsultaExpedienteRequest){
				qName = new QName(nameSpace, "ConsultaExpedienteRequest");
				root = new JAXBElement<ConsultaExpedienteRequest>(qName, ConsultaExpedienteRequest.class, clase);
			}
			
			if (clase instanceof ConsolidacionPolizaRequest){
				qName = new QName(nameSpace, "ConsolidacionPolizaRequest");
				root = new JAXBElement<ConsolidacionPolizaRequest>(qName, ConsolidacionPolizaRequest.class, clase);
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

	def consultaExpediente = { ou, filtro ->

		try {

			def ctx = grailsApplication.mainContext
			def bean = ctx.getBean("soapClientAlptis")
			bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)

			def salida=grailsApplication.mainContext.soapClientAlptis.consultaExpediente(tarificadorService.obtenerUsuarioFrontal(ou),filtro)

			return salida
		} catch (Exception e) {
			logginService.putError("obtenerInformeExpedientes de Caser","No se ha podido obtener el informe de expediente : " + e)
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

	public def rellenaDatos (req, company) {

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
						productCia = datosRegistro.nombreCliente = eElement.getElementsByTagName("productCode").item(0).getTextContent()
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
						datosRegistro.sexo = eElement.getElementsByTagName("gender").item(0).getTextContent()=="M"?"M":"V"
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
					
					if (datosRegistro.telefono1 == null || datosRegistro.telefono1.isEmpty()){
						if (datosRegistro.telefono3 != null && !datosRegistro.telefono3.isEmpty()){
							datosRegistro.telefono1 = datosRegistro.telefono3
						} else if (datosRegistro.telefono2 != null && !datosRegistro.telefono2.isEmpty()){
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

			setCamposGenericos (datosRegistro, productCia)

			return datosRegistro
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
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

	private def rellenaServicios (req, nameCompany) {

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

			return listadoServicios
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}

	private def rellenaPreguntas (req, nameCompany) {

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
						pregunta.respuesta = eElement.getElementsByTagName("igp").item(0).getTextContent().equals("S")?"SI":"NO"
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

	private def rellenaCoberturas (req) {

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


			return listadoCoberturas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
		}
	}

	def busquedaCrm (policyNumber, ou, requestNumber, opername, companyCodigoSt, companyId, requestBBDD, certificateNumber, companyName) {

		task {

			logginService.putInfoMessage("BusquedaExpedienteCrm - Buscando en CRM solicitud de " + companyName + " con numSolicitud: " + policyNumber.toString() + ", referencia: " + certificateNumber)
			
			
			def respuestaCrm
			int limite = 0;
			boolean encontrado = false;

			servicios.Filtro filtro = new servicios.Filtro()
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
			CorreoUtil correoUtil = new CorreoUtil()

			Thread.sleep(30000);


			try {


				while( !encontrado && limite < 10) {

					filtro.setClave(servicios.ClaveFiltro.CLIENTE);
					filtro.setValor(companyCodigoSt.toString());

					servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
					filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
					filtroRelacionado1.setValor(requestNumber.toString())

					servicios.Filtro filtroRelacionado2 = new servicios.Filtro()
					filtroRelacionado2.setClave(servicios.ClaveFiltro.NUM_CERTIFICADO);
					filtroRelacionado2.setValor(certificateNumber.toString())
					filtroRelacionado1.setFiltroRelacionado(filtroRelacionado2)

					filtro.setFiltroRelacionado(filtroRelacionado1)

					respuestaCrm = consultaExpediente(ou.toString(),filtro)

					if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

						for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

							servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

							logginService.putInfoMessage("BusquedaExpedienteCrm - Expediente encontrado: " + exp.getCodigoST() + " para " + companyName)
							
							String fechaCreacion = format.format(new Date());

							if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) &&
							exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(requestNumber.toString()) && exp.getNumPoliza() != null &&
							exp.getNumPoliza().equals(policyNumber.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura()) && exp.getNumCertificado() != null && exp.getNumCertificado().equals(certificateNumber)){

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
					correoUtil.envioEmailErrores("BusquedaExpedienteCrm","Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString() + " se ha procesado pero no se ha dado de alta en CRM",null)


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
					error.save(flush:true)
				}
			} catch (Exception e) {

				logginService.putInfoMessage("BusquedaExpedienteCrm - Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString() + ". Error: " + e.getMessage())
				correoUtil.envioEmailErrores("BusquedaExpedienteCrm","Nueva alta de " + companyName + " con numero de solicitud: " + policyNumber.toString() + " y referencia: " + certificateNumber.toString(),e)
			
			}
		}
	}


	private def obtenerProductos (req, nameCompany) {
		return null
	}
	
	public void insertarRecibido(Company company, String identificador, String info, String operacion) {
		
		Recibido recibido = new Recibido()
		recibido.setFecha(new Date())
		recibido.setCia(company.id.toString())
		recibido.setIdentificador(identificador)
		recibido.setInfo(info)
		recibido.setOperacion(operacion)
		recibido.save(flush:true)
	}
	
	public void insertarError(Company company, String identificador, String info, String operacion, String detalleError) {
		
		com.scortelemed.Error error = new com.scortelemed.Error()
		error.setFecha(new Date())
		error.setCia(company.id.toString())
		error.setIdentificador(identificador)
		error.setInfo(info)
		error.setOperacion(operacion)
		error.setError(detalleError)
		error.save(flush:true)
		
	}

	public void insertarEnvio (Company company, String identificador, String info) {

		Envio envio = new Envio()
		envio.setFecha(new Date())
		envio.setCia(company.id.toString())
		envio.setIdentificador(identificador)
		envio.setInfo(info)
		envio.save(flush:true)
	}
	
}
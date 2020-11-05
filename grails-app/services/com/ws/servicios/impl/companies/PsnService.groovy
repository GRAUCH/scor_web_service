package com.ws.servicios.impl.companies

import com.scor.global.ContentResult
import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.Company
import com.scortelemed.Request
import com.scortelemed.schemas.psn.*
import com.scortelemed.schemas.psn.ConsultaDocumentoResponse.Documento
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoResponse.Expediente
import com.scortelemed.servicios.TipoSexo
import com.ws.servicios.ICompanyService
import grails.util.Holders
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import servicios.*

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

class PsnService implements ICompanyService{

	TransformacionUtil util = new TransformacionUtil()
	def logginService = Holders.grailsApplication.mainContext.getBean("logginService")
	def requestService = Holders.grailsApplication.mainContext.getBean("requestService")
	def tarificadorService = Holders.grailsApplication.mainContext.getBean("tarificadorService")
	ContentResult contentResult = new ContentResult()


	String marshall(def objeto) {
		String nameSpace = "http://www.scortelemed.com/schemas/psn"
		String result
		try{
			if (objeto instanceof GestionReconocimientoMedicoRequest){
				result = requestService.marshall(nameSpace, objeto, GestionReconocimientoMedicoRequest.class)
			} else if (objeto instanceof ResultadoReconocimientoMedicoRequest){
				result = requestService.marshall(nameSpace, objeto, ResultadoReconocimientoMedicoRequest.class)
			} else if (objeto instanceof ConsolidacionPolizaRequest){
				result = requestService.marshall(nameSpace, objeto, ConsolidacionPolizaRequest.class)
			} else if (objeto instanceof ConsultaExpedienteRequest){
				result = requestService.marshall(nameSpace, objeto, ConsultaExpedienteRequest.class)
			} else if (objeto instanceof ConsultaDocumentoRequest){
				result = requestService.marshall(nameSpace, objeto, ConsultaDocumentoRequest.class)
			}
		} finally {
			return result
		}
	}


	def buildDatos(Request req, String codigoSt) {
		try {
			DATOS dato = new DATOS()
			Company company = req.company
			dato.registro = rellenaDatos(req, company)
			//dato.pregunta = rellenaPreguntas(req)
			//dato.servicio = rellenaServicios(req, company.nombre)
			dato.coberturas = rellenaCoberturas(req)
			return dato
		} catch (Exception e) {
			logginService.putError("buildDatos",e.toString())
		}
	}


	String getCodigoStManual(Request req) {
		def pais
		def codigoCia

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

				/**PAIS
				 *
				 */

				if (eElement.getElementsByTagName("country").item(0) != null && !eElement.getElementsByTagName("country").item(0).getTextContent().isEmpty()) {
					pais = eElement.getElementsByTagName("country").item(0).getTextContent().toString()
				} else {
					pais = "ES"
				}
			}
		}

		if (pais.toString().equals("ES")){

			codigoCia = "1030"
		}

		if (pais.toString().equals("PT")){

			codigoCia = "1058"
		}
		return codigoCia
	}

	def rellenaDatosSalidaDocumentoNodo(nodoAlfresco, requestDate, logginService, String codigoSt) {

		byte[] compressedData=contentResult.obtenerFichero(nodoAlfresco)

		Documento documento = new Documento();
		documento.setCodigoSt(codigoSt)
		documento.setDocumentacionId(null);
		documento.setNodoAlfresco(nodoAlfresco)
		documento.setContenido(compressedData)


		return documento
	}

	def rellenaDatosSalidaConsultaExpediente(servicios.Expediente expedientePoliza, String requestDate, RespuestaCRM respuestaCRM) {

		ConsultaExpedienteResponse.Expediente expediente = new ConsultaExpedienteResponse.Expediente()

		CandidateInformationType candidateInformation = new CandidateInformationType()
		PolicyHolderInformationType policyHolderInformationType = new PolicyHolderInformationType()

		policyHolderInformationType.setDossierNumber(expedientePoliza.getCodigoST())
		policyHolderInformationType.setPolicyNumber(expedientePoliza.getNumPoliza())
		policyHolderInformationType.setCertificateNumber(expedientePoliza.getNumCertificado())
		policyHolderInformationType.setSuplementNumber(expedientePoliza.getNumSuplemento())
		policyHolderInformationType.setRequestDate(requestDate)
		policyHolderInformationType.setRequestState(expedientePoliza.getCodigoEstado().toString())
		policyHolderInformationType.setRequestNumber(expedientePoliza.getNumSubPoliza())

		expediente.setPolicyHolderInformation(policyHolderInformationType);

		if (expedientePoliza.getCandidato() != null) {

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd")
			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");

			candidateInformation.setCodigoSt(expedientePoliza.getCandidato().getCodigoST())
			candidateInformation.setFiscalIdentificationNumber(expedientePoliza.getCandidato().getNumeroDocumento())
			candidateInformation.setName(expedientePoliza.getCandidato().getNombre())
			candidateInformation.setSurname(expedientePoliza.getCandidato().getApellidos())
			candidateInformation.setAddress(expedientePoliza.getCandidato().getDireccion())
			candidateInformation.setCity(expedientePoliza.getCandidato().getLocalidad())
			candidateInformation.setProvince(expedientePoliza.getCandidato().getProvincia())
			candidateInformation.setCountry(expedientePoliza.getCandidato().getPais())
			candidateInformation.setPostalCode(expedientePoliza.getCandidato().getCodigoPostal())
			candidateInformation.setEmail(expedientePoliza.getCandidato().getCorreo1())
			candidateInformation.setGender(expedientePoliza.getCandidato().getSexo() == TipoSexo.HOMBRE?GenderType.H:GenderType.M)
			candidateInformation.setCivilState(devolverEstadoCivil(expedientePoliza.getCandidato().getEstadoCivil()))
			candidateInformation.setMobileNumber(util.devolverTelefonoMovil(expedientePoliza.getCandidato()))
			candidateInformation.setPhoneNumber1(util.devolverTelefono1(expedientePoliza.getCandidato()))
			candidateInformation.setPhoneNumber2(util.devolverTelefono2(expedientePoliza.getCandidato()))
			candidateInformation.setPhoneNumber2(util.devolverTelefono2(expedientePoliza.getCandidato()))
			candidateInformation.setProductCode(expedientePoliza.getProducto().getCodigoProductoCompanya())
			candidateInformation.setBirthDate(myFormat.format(fromUser.parse(expedientePoliza.getCandidato().getFechaNacimiento())))

			expediente.setCandidateInformation(candidateInformation);
		}

		if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {

			expedientePoliza.getCoberturasExpediente().each { 
				coberturasPoliza ->

				BenefitsType benefitsType = new BenefitsType()
				benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
				benefitsType.setBenefictCode(devolverCodigo(coberturasPoliza.getCodigoCobertura()))
				benefitsType.setBenefictCapital(util.devolverDatos(coberturasPoliza.getCapitalCobertura()))

				BenefictResultType benefictResultType = new BenefictResultType()

				benefictResultType.setPremiumLoading(util.devolverDatos(coberturasPoliza.getValoracionPrima()))
				benefictResultType.setCapitalLoading(util.devolverDatos(coberturasPoliza.getValoracionCapital()))

				benefictResultType.exclusions = util.fromStringLoList(coberturasPoliza.getExclusiones())
				benefictResultType.temporalLoading = util.fromStringLoList(coberturasPoliza.getValoracionTemporal())
				benefictResultType.medicalReports = util.fromStringLoList(coberturasPoliza.getInformesMedicos())
				benefictResultType.notes = util.fromStringLoList(coberturasPoliza.getNotas())

				benefitsType.setBenefictResult(benefictResultType)

				expediente.getBenefits().add(benefitsType)
			}
		}

		if (expedientePoliza.getListaCitas() != null && expedientePoliza.getListaCitas().size() > 0) {

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd")
			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");


			expedientePoliza.getListaCitas().each { Cita cita ->

				AppointmentsType appointments = new AppointmentsType()

				appointments.setCitaId(cita.getCitaId())
				appointments.setDescripcion(cita.getDescripcion())
				appointments.setEstado(cita.getEstado())
				appointments.setFecha(myFormat.format(fromUser.parse(cita.getFecha())))
				appointments.setFechaCreacion(cita.getFechaCreacion())
				appointments.setFechaModificacion(cita.getFechaModificacion())
				appointments.setHora(cita.getHora())
				appointments.setHoraFin(cita.getHoraFin())
				appointments.setTema(cita.getTema())
				appointments.setTipoCita(devolverTipoCita(cita.getTipoCita()))

				expediente.getAppointments().add(appointments);
			}
		}

		if (expedientePoliza.getListaLlamadas() != null && expedientePoliza.getListaLlamadas().size() > 0) {

			expedientePoliza.getListaLlamadas().each { Llamada actividad ->


				SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd")

				ActivitiesType activities = new ActivitiesType()

				activities.setDescripcion(actividad.getDescripcionLlamada())
				activities.setDestinatarios(actividad.getTipoDestinatario().toString())
				activities.setFechaCreacion(myFormat.format(actividad.getFechaCreacionLlamada().toGregorianCalendar().getTime()))
				activities.setNumeroDestino(actividad.getNumeroDestino())
				activities.setStatusCode(actividad.getTipoLLamada().toString())
				activities.setSubject(actividad.getSubject())
				activities.setType("LLamada")

				expediente.getActivities().add(activities);
			}
		}

		if (expedientePoliza.getListaDocumentosInforme() != null && expedientePoliza.getListaDocumentosInforme().size() > 0) {

			expedientePoliza.getListaDocumentosInforme().each { DocumentacionExpedienteInforme documento ->

				DocumentType document = new DocumentType()

				document.setCodigoSt(documento.getCodigoST())
				document.setDocumentacionId(documento.getDocumentacionId())
				if (documento.getFechaHoraRecepcion() != null) {
					document.setFechaHoraRecepcion(documento.getFechaHoraRecepcion().toString())
				}
				document.setNodoAlfresco(documento.getNodoAlfresco())
				document.setNombre(documento.getNombre())

				expediente.getDocuments().add(document);
			}
		}

		return expediente
	}

	def rellenaDatosSalidaConsulta(servicios.Expediente expedientePoliza, String numSolicitud) {

		Expediente expediente = new Expediente()

		expediente.setRequestDate(util.fromDateToXmlCalendar(new Date()))
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

		byte[] compressedData=tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

		expediente.setZip(compressedData)

		expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

		if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {


			expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

				BenefitsType benefitsType = new BenefitsType()
				benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
				benefitsType.setBenefictCode(devolverCodigo(coberturasPoliza.getCodigoCobertura()))
				benefitsType.setBenefictCapital(util.devolverDatos(coberturasPoliza.getCapitalCobertura()))

				BenefictResultType benefictResultType = new BenefictResultType()

				benefictResultType.setDescResult(util.devolverDatos(coberturasPoliza.getResultadoCobertura()))
				benefictResultType.setResultCode(util.devolverDatos(coberturasPoliza.getCodResultadoCobertura()))
				benefictResultType.setPremiumLoading(util.devolverDatos(coberturasPoliza.getValoracionPrima()))
				benefictResultType.setCapitalLoading(util.devolverDatos(coberturasPoliza.getValoracionCapital()))

				benefictResultType.exclusions = util.fromStringLoList(coberturasPoliza.getExclusiones())
				benefictResultType.temporalLoading = util.fromStringLoList(coberturasPoliza.getValoracionTemporal())
				benefictResultType.medicalReports = util.fromStringLoList(coberturasPoliza.getInformesMedicos())
				benefictResultType.notes = util.fromStringLoList(coberturasPoliza.getNotas())

				benefitsType.setBenefictResult(benefictResultType)

				expediente.getBenefitsList().add(benefitsType)
			}
		}

		return expediente
	}

	def rellenaDatos (req, company) {

		def mapDatos = [:]
		def listadoPreguntas = []
		def formato = new SimpleDateFormat("yyyyMMdd")
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

			NodeList nList = doc.getElementsByTagName("CandidateInformation")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

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
						datosRegistro.apellidosCliente = eElement.getElementsByTagName("surname").item(0).getTextContent()
					}

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

					/**PAIS
					 *
					 */

					if (eElement.getElementsByTagName("country").item(0) != null && !eElement.getElementsByTagName("country").item(0).getTextContent().isEmpty()) {
						datosRegistro.pais = eElement.getElementsByTagName("country").item(0).getTextContent()
					} else {
						datosRegistro.pais = "ES"
					}

					if (datosRegistro.pais.toString().equals("ES")) {

						datosRegistro.codigoCia = "1030"
					}

					if (datosRegistro.pais.toString().equals("PT")) {

						datosRegistro.codigoCia = "1058"
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

			camposGenericos (datosRegistro, eUWReferenceCode, codigooficinanuevo, datosRegistro.pais)


			return datosRegistro
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}


	private void camposGenericos(REGISTRODATOS datos, eUWReferenceCode, codigoOficina, pais) {

		datos.lugarNacimiento = ""
		datos.emailAgente = ""
		datos.tipoCliente = "N"
		datos.franjaHoraria = ""
		datos.codigoCuestionario = ""

		if (pais.equals("ES")){
			datos.campo1 = "es"
			datos.campo2 = ""
			datos.campo3 = "ES"
		}
		if (pais.equals("PT")){
			datos.campo1 = "pt"
			datos.campo2 = ""
			datos.campo3 = "PT"
		}
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

	private def rellenaCoberturas (req) {

		def listadoCoberturas = []
		def capital
		def codigoCoberturaPsn

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()

			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			doc.getDocumentElement().normalize()

			NodeList nList = doc.getElementsByTagName("benefits")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					DATOS.Coberturas cobertura = new DATOS.Coberturas()

					/**COBERTURAS QUE NOS LLEGA SIEMPRE ES COB5
					 *
					 */

					codigoCoberturaPsn = eElement.getElementsByTagName("benefictCode").item(0).getTextContent().toUpperCase()

					switch (codigoCoberturaPsn) {
						case "01":
							cobertura.codigoCobertura = "COB5"
							break
						case "02":
							cobertura.codigoCobertura = "COB2"
							break
						case "03":
							cobertura.codigoCobertura = "COB8"
							break
						case "04":
							cobertura.codigoCobertura = "COB9"
							break
						case "06":
							cobertura.codigoCobertura = "COB4"
							break
						case "07":
							cobertura.codigoCobertura = "COB3"
							break
						case "08":
							cobertura.codigoCobertura = "COB1"
							break
					}

					cobertura.filler = ""
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

	private def rellenaPreguntas (req) {

		def listadoPreguntas = []

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()

			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			doc.getDocumentElement().normalize()

			NodeList nList = doc.getElementsByTagName("previousQuestions")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode


					/**PREGUNTAS PREVIAS
					 *
					 */

					DATOS.Pregunta pregunta = new DATOS.Pregunta()

					pregunta.codigoPregunta = eElement.getElementsByTagName("question").item(0).getTextContent()
					pregunta.tipoDatos = eElement.getElementsByTagName("dataType").item(0).getTextContent()
					pregunta.respuesta = eElement.getElementsByTagName("answer").item(0).getTextContent()=="S"?"SI":"NO"
					listadoPreguntas.add(pregunta)
				}
			}

			return listadoPreguntas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e))
		}
	}

	def devolverCodigo(codigo){

		switch (codigo) {
			case "COB5":
				return  "01"
				break
			case "COB2":
				return "02"
				break
			case "COB8":
				return "03"
				break
			case "COB9":
				return "04"
				break
			case "COB4":
				return "06"
				break
			case "COB3":
				return "07"
				break
			case "COB1":
				return "08"
				break
		}
	}

	def devolverNombreCobertura(codigo){

		if (codigo.equals("COB1")) {
			return "Dependency"
		} else if (codigo.equals("COB2")) {
			return "Accidental Dead"
		} else if (codigo.equals("COB3")) {
			return "Critical Illness"
		} else if (codigo.equals("COB4")) {
			return "Permanent Disability"
		} else if (codigo.equals("COB5")) {
			return "Dead"
		} else if (codigo.equals("COB6")) {
			return ""
		} else if (codigo.equals("COB7")) {
			return ""
		} else if (codigo.equals("COB8")) {
			return "Disability 30"
		} else if (codigo.equals("COB9")) {
			return "Disability 30-90"
		} else {
			return null
		}
	}

	def devolverStateType(estado){

		switch(estado){
			case "CERRADO": return RequestStateType.CLOSED;
			case "ANULADO": return RequestStateType.CANCELLED;
			case "RECHAZADO": return RequestStateType.REJECTED;
			default: return null;
		}
	}

	def devolverEstadoCivil(estado){

		switch(estado){
			case "SOLTERO" : return CivilStateType.S;
			case "CASADO" : return CivilStateType.C;
			case "DIVORCIADO" : return CivilStateType.D;
			case "SEPARADO" : return CivilStateType.S;
			case "PAREJA_DE_HECHO" : return CivilStateType.P;
			case "VIUDO" : return CivilStateType.V;
			default: return null;
		}
	}

	def devolverTipoCita(TipoCita cita){

		switch(cita){
			case TipoCita.TELESELECCION : return TipoCitaType.TELESELECCION
			case TipoCita.SERVICIO_CITA : return TipoCitaType.SERVICIO_CITA
			case TipoCita.CENTRO_MEDICO : return TipoCitaType.CENTRO_MEDICO
			default: return null;
		}
	}

	Boolean existeDocumentoNodo (RespuestaCRM respuestaCRM, String nodo ) {

		boolean existeDocumento = false;

		if(respuestaCRM != null && respuestaCRM.getListaExpedientesInforme() != null && respuestaCRM.getListaExpedientesInforme().size() > 0){

			if (respuestaCRM.getListaExpedientesInforme().get(0) != null && respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme() != null && respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme().size() > 0) {

				respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme().each { DocumentacionExpedienteInforme documento ->

					if (documento.getNodoAlfresco().equals(nodo)) {
						existeDocumento = true
					}
				}
			}
		}

		return existeDocumento
	}

	Boolean existeDocumentoId (RespuestaCRM respuestaCRM, String id ) {

		boolean existeDocumento = false;

		if(respuestaCRM != null && respuestaCRM.getListaExpedientesInforme() != null && respuestaCRM.getListaExpedientesInforme().size() > 0){

			if (respuestaCRM.getListaExpedientesInforme().get(0) != null && respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme() != null && respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme().size() > 0) {

				respuestaCRM.getListaExpedientesInforme().get(0).getListaDocumentosInforme().each { DocumentacionExpedienteInforme documento ->

					if (documento.getDocumentacionId().equals(id)) {
						existeDocumento = true
					}
				}
			}
		}

		return existeDocumento
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


					/**CODIGO DE OPERACION
					 *
					 */

					if (eElement.getElementsByTagName("operation").item(0) != null && !eElement.getElementsByTagName("operation").item(0).getTextContent().isEmpty()) {

						if (!eElement.getElementsByTagName("operation").item(0).getTextContent().equals("A") && !eElement.getElementsByTagName("operation").item(0).getTextContent().equals("B") && !eElement.getElementsByTagName("operation").item(0).getTextContent().equals("M")) {
							wsErrors.add(new WsError("operation", eElement.getElementsByTagName("operation").item(0).getTextContent().toString(), "Posibles valores (A, B, M)"))
							break
						}
					} else {
						wsErrors.add(new WsError("operation", null, "Elemento no puede ser nulo. Posibles valores (A, B, M)"))
						break
					}

					/**CODIGO DE PRODUCTO
					 *
					 */

					if (eElement.getElementsByTagName("productCode").item(0) == null || eElement.getElementsByTagName("productCode").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("productCode",null,"Elemento no puede ser nulo"))
						break
					}

					/**NOMBRE DE CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("name").item(0) == null || eElement.getElementsByTagName("name").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("name",null,"Elemento no puede ser nulo"))
						break
					}

					/**APELLIDO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("surname").item(0) == null || eElement.getElementsByTagName("surname").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("surname",null,"Elemento no puede ser nulo"))
						break
					}

					/**DNI CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) == null || eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("fiscalIdentificationNumber",null,"Elemento no puede ser nulo"))
						break
					}

					/**SEXO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("gender").item(0) == null || eElement.getElementsByTagName("gender").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("gender",null,"Elemento no puede ser nulo"))
						break
					}

					/**CITY
					 *
					 */
					if (eElement.getElementsByTagName("city").item(0) == null || eElement.getElementsByTagName("city").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("city",null,"Elemento no puede ser nulo"))
						break
					}

					/**PAIS
					 *
					 */

					if (eElement.getElementsByTagName("country").item(0) != null && !eElement.getElementsByTagName("country").item(0).getTextContent().isEmpty()) {

						if (!eElement.getElementsByTagName("country").item(0).getTextContent().equals("ES") && !eElement.getElementsByTagName("country").item(0).getTextContent().equals("PT")) {

							wsErrors.add(new WsError("country",eElement.getElementsByTagName("country").item(0).getTextContent(),"Posibles valores (ES, PT)"))
							break
						}

					} else {
						wsErrors.add(new WsError("country",null,"Elemento no puede ser nulo"))
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
						wsErrors.add(new WsError("telefono",null,"Debe existir un telefono de contacto"))
						break
					}

					/**FECHA DE NACIMIENTO
					 *
					 */

					if (eElement.getElementsByTagName("birthDate").item(0) == null || eElement.getElementsByTagName("birthDate").item(0).getTextContent().isEmpty()) {
						wsErrors.add(new WsError("birthDate",null,"Elemento no puede ser nulo"))
						break
					} else {
						try {
							formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar().getTime())
						} catch(Exception e){
							wsErrors.add(new WsError("birthDate",eElement.getElementsByTagName("birthDate").item(0).getTextContent(),"Formato fecha yyyy-MM-dd"))
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

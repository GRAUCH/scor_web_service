package com.ws.servicios.impl.companies

import com.scor.comprimirdocumentos.ParametrosEntrada
import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.*
import com.scortelemed.schemas.netinsurance.*
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierResponse.ExpedienteConsulta
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCasesResultsResponse.Expediente
import com.scortelemed.servicios.Candidato
import com.scortelemed.servicios.Frontal
import com.scortelemed.servicios.FrontalServiceLocator
import com.ws.servicios.ICompanyService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.ZipResponse
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import servicios.RespuestaCRM

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class NetinsuranceService implements ICompanyService{

	TransformacionUtil util = new TransformacionUtil()
	def logginService
	def requestService
	def expedienteService
	def tarificadorService
	def grailsApplication


	String marshall(def objeto) {
		String nameSpace = "http://www.scortelemed.com/schemas/netinsurance"
		String result
		try{
			if (objeto instanceof NetinsuranteUnderwrittingCaseManagementRequest){
				result = requestService.marshall(nameSpace, objeto, NetinsuranteUnderwrittingCaseManagementRequest.class)
			} else if (objeto instanceof NetinsuranteUnderwrittingCasesResultsRequest){
				result = requestService.marshall(nameSpace, objeto, NetinsuranteUnderwrittingCasesResultsRequest.class)
			} else if (objeto instanceof NetinsuranteGetDossierRequest){
				result = requestService.marshall(nameSpace, objeto, NetinsuranteGetDossierRequest.class)
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
			//dato.pregunta = rellenaPreguntas(req, company.nombre)
			dato.servicio = rellenaServicios(req, company.nombre)
			dato.coberturas = rellenaCoberturas(req)

			return dato
		} catch (Exception e) {
			logginService.putError(e.toString())
		}
	}


	def getCodigoStManual(Request req) {
		return null
	}

	 def rellenaDatosSalidaConsulta(servicios.Expediente expedientePoliza, requestDate, logginService) {


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

		byte[] compressedData=tarificadorService.obtenerZip(expedientePoliza.getNodoAlfresco())

		expediente.setZip(compressedData)

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

	 def rellenaDatosSalidaExpediente(servicios.Expediente expedientePoliza, requestDate, logginService, String cia, Company company) {

		ExpedienteConsulta expediente = new ExpedienteConsulta()
		byte[] compressedData = null

		expediente.setRequestDate(requestDate)
		expediente.setRequestNumber(util.devolverDatos(expedientePoliza.getNumSolicitud()))
		expediente.setRequestState(traducirEstado(expedientePoliza.getCodigoEstado()))
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

		expediente.setZip(new byte[0])
		expediente.setNotes(util.devolverDatos(expedientePoliza.getTarificacion().getObservaciones()))

		if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {


			expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

				BenefitsType benefitsType = new BenefitsType()

				benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getNombreCobertura()))
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

	def rellenaDatos (req, company) {

		def mapDatos = [:]
		def listadoPreguntas = []
		def formato = new SimpleDateFormat("yyyyMMdd")
		def apellido
		def telefono1
		def telefono2
		def telefono3
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

						telefono2 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()

						if (telefono2 != null && !telefono2.isEmpty() && (telefono2.startsWith("0039") || telefono2.startsWith("+39"))) {
							datosRegistro.telefono2 = telefono2
						} else if (telefono2 != null && !telefono2.isEmpty()){
							datosRegistro.telefono2 = "0039" + telefono2
						} else {
							datosRegistro.telefono2 = null
						}
					}

					if (eElement.getElementsByTagName("phoneNumber2").item(0) != null && eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().isEmpty()) {

						telefono3 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()

						if (telefono3 != null && !telefono3.isEmpty() && (telefono3.startsWith("0039") || telefono3.startsWith("+39"))) {
							datosRegistro.telefono3 = telefono3
						} else if (telefono2 != null && !telefono2.isEmpty()){
							datosRegistro.telefono3 = "0039" + telefono3
						} else {
							datosRegistro.telefono3 = null
						}
					}

					if (eElement.getElementsByTagName("mobileNumber").item(0) != null && eElement.getElementsByTagName("mobileNumber").item(0).getTextContent() != null && !eElement.getElementsByTagName("mobileNumber").item(0).getTextContent().isEmpty()) {

						telefonoMovil = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()

                        if (telefonoMovil != null && !telefonoMovil.isEmpty() && (telefonoMovil.startsWith("0039") || telefonoMovil.startsWith("+39"))) {
                            telefonoMovil = telefonoMovil
                        } else if (telefonoMovil != null && !telefonoMovil.isEmpty()){
                            telefonoMovil = "0039" + telefonoMovil
                        } else {
                            telefonoMovil = null
                        }

					}

					if (telefonoMovil != null && !telefonoMovil.isEmpty()) {
						datosRegistro.telefono1 = telefonoMovil
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
                    log.info("***         Netinsurance           ***")
					log.info("** Los telefonos que quedaron fueron: ")
					log.info("Telefono1 :  ${datosRegistro.telefono1}")
					log.info("Telefono2 :  ${datosRegistro.telefono2}")
					log.info("Telefono3 :  ${datosRegistro.telefono3}")
					log.info("***      Fin de los telefonos       ***")
                    log.info("***         Netinsurance           ***")
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

					if (eElement.getElementsByTagName("agency").item(0) != null && !eElement.getElementsByTagName("agency").item(0).getTextContent().isEmpty()) {

						Agente instituto = Agente.findByValor(eElement.getElementsByTagName("agency").item(0).getTextContent().toString())

						if (instituto != null && instituto.getAgente() != null && !instituto.getAgente().isEmpty()) {

							if (instituto.getAgente().length() > 20) {
								datosRegistro.codigoAgencia = instituto.getAgente().substring(0, 19)
							} else {
								datosRegistro.codigoAgencia = instituto.getAgente()
							}

							datosRegistro.nomApellAgente = instituto.getAgente()

						} else {

							datosRegistro.codigoAgencia = eElement.getElementsByTagName("agency").item(0).getTextContent().toString()
							datosRegistro.nomApellAgente = eElement.getElementsByTagName("agency").item(0).getTextContent().toString()
						}

					} else {

						datosRegistro.codigoAgencia = "."
						datosRegistro.nomApellAgente = "."
					}
				}
			}

			setCamposGenericos (datosRegistro)

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

			if (nList != null && nList.length > 0){

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

	def devolverNombreCobertura(codigo){

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

	def devolverStateType(estado){

		switch(estado){
			case "CERRADO": return RequestStateType.CLOSED;
			case "ANULADO": return RequestStateType.CANCELLED;
			case "RECHAZADO": return RequestStateType.REJECTED;
			default: return null;
		}
	}

	String traducirEstado(estado) {

		switch(estado){
			case "PENDIENTE_CONTACTO": return "ATTENDE CONTATTO";
			case "PEDIENTE_VALIDAR_DATOS": return "IN ATTESA DI CONVALIDARE I DATI";
			case "CITA_CONCERTADA": return "APPUNTAMENTO ACCORDATO";
			case "NO_ACUDE_CITA": return "NON SI PRESENTA ALL'APPUNTAMENTO";
			case "RECHAZA_PRUEBAS": return "RIFIUTA PROVE";
			case "NO_LOCALIZADO": return "IRREPERIBILE ";
			case "CARTA_ILOCALIZABLE_ENVIADA": return "LETTERA IRREPERIBILE INVIATA";
			case "SOLICITADO_MENSAJERO": return "RICHIESTO CORRIERE";
			case "PENDIENTE_LABORATORIO": return "IN ATTESA DI LABORTAORIO";
			case "CERRADO": return "CHIUSO";
			case "ANULADO": return "ANNULLATO";
			case "EXPEDIENTE_TARIFICADO": return "DOSSIER TARIFFATO";
			case "RESULTADOS_RECIBIDOS": return "RISULTATI RICEVUTI";
			case "PENDIENTE_TARIFICAR": return "IN ATTESA DI TARIFFAZIONE";
			case "PENDIENTE_INFORME_CANDIDATO": return "IN ATTESA REFERTO_CANDIDATO";
			case "DATOS_ERRONEOS": return "DATI INVALIDI";
			case "CONTACTO_DIFERIDO": return "CONTATTO POSPOSTO";
			case "RETRASO_CONTACTO": return "RITARDO CONTATTO";
			case "PENDIENTE_CITA": return "IN ATTESA DI APPUNTAMENTO";
			case "PENDIENTE_MENSAJERO": return "IN ATTESA CORRIERE";
			case "RECEPCION_DOCUMENTACION_RESULTADOS": return "RICEZIONE DOCUMENTAZIONE RISULTATI";
			case "PENDIENTE_CLASIFICAR": return "IN ATTESA DI CLASSIFICARE";
			case "DOCUMENTACION_PARCIAL": return "DOCUMENTAZIONE PARZIALE";
			case "DOCUMENTACION_ERRONEA": return "DOCUMENTAZIONE ERRONEA";
			case "PRUEBAS_ADICIONALES": return "PROVE ADDIZIONALI";
			case "INFORMES_COMPLEMENTARIOS": return "REFERTI COMPLEMENTARI";
			case "INFORMACION_ADICIONAL": return "INFORMAZIONI ADDIZIONALI";
			case "ENVIO_RESULTADOS_CANDIDATO": return "INVIO RISULTATI CANDIDATO";
			case "CANDIDATO_RECHAZA_PRUEBAS": return "CANDIDATO RIFIUTA PROVE";
			case "ACEPTADO": return "ACCETTATO";
			case "REAPERTURADO": return "RIAPERTO";
			case "RECHAZADO": return "RIFIUTATO ";
			case "ABIERTO": return "APERTO";
			case "PREAPERTURADO": return "PREAPERTURA";
			case "ABIERTO_POR_REVISION_PAGO_RENTAS": return "APERTO PER REVSIONE SALDO RENDITA";
			case "PENDIENTE_RED": return "ATTENDE RETE";
			case "NO_LOCALIZADO_CITA": return "IRREPERIBILE APPUNTAMENTO";
			case "COMUNICAR_CITA_CANDIDATO": return "COMUNICARE APPUNTAMENTO CANDIDATO";
			case "PENDIENTE_CM_GESTION_CITA": return "ATTENDE CM GESTIONE APPUNTAMENTO";
			case "PENDIENTE_LLAMADA_CANDIDATO": return "IN ATTESA CHIAMATA CANDIDATO";
			case "PENDIENTE_LLAMADA_OFICINA": return "IN ATTESA CHIAMATA SUCCURSALE";
			case "REALIZA_TUW_NO_PPMM": return "REALIZZA TUW NON PROVE MEDICHE";
			case "PENDIENTE_CIA": return "ATTENDE CO";
			default: return null;
		}
	}

	servicios.Expediente existeExpediente(String numPoliza, String nombreCia, String companyCodigoSt, String ou) {

		logginService.putInfoMessage("Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia)

		CorreoUtil correoUtil = new CorreoUtil()
		servicios.Filtro filtro = new servicios.Filtro()
        servicios.Expediente expediente = null;
		RespuestaCRM respuestaCrm

		try {

			filtro.setClave(servicios.ClaveFiltro.CLIENTE);
			filtro.setValor(companyCodigoSt.toString());

			servicios.Filtro filtroRelacionado1 = new servicios.Filtro()
			filtroRelacionado1.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
			filtroRelacionado1.setValor(numPoliza.toString())

			filtro.setFiltroRelacionado(filtroRelacionado1)

			respuestaCrm = expedienteService.consultaExpediente(ou, filtro)

			if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

				for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

					servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

					if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(companyCodigoSt.toString()) && exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(numPoliza.toString())) {

						logginService.putInfoMessage("Expediente con número de poliza " + numPoliza + " y expediente " + exp.getCodigoST() + " para " + nombreCia + " ya existe en el sistema")
						expediente = respuestaCrm.getListaExpedientes().get(i)
					}
				}
			} else {

                logginService.putInfoMessage("Expediente con número de poliza " + numPoliza + " para " + nombreCia + " no se existe en el sistema")
                expediente = null
            }

		} catch (Exception e) {

			logginService.putInfoMessage("Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia + " . Error: " + + e.getMessage())
			correoUtil.envioEmailErrores("ERROR en búsqueda de duplicados para " + nombreCia,"Buscando si existe expediente con numero de poliza " + numPoliza + " para " + nombreCia, e)
		}

		return expediente
	}
}

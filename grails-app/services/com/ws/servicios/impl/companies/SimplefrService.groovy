package com.ws.servicios.impl.companies

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.Company
import com.scortelemed.Request
import com.scortelemed.TipoOperacion
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCasesResultsRequest
import com.ws.servicios.ICompanyService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class SimplefrService implements ICompanyService{

	TransformacionUtil util = new TransformacionUtil()
	def logginService
	def requestService
	def expedienteService
	def tarificadorService
	def grailsApplication


	String marshall(def objeto) {
		String nameSpace = "http://www.scortelemed.com/schemas/simplefr"
		String result
		try{
			if (objeto instanceof SimplefrUnderwrittingCaseManagementRequest){
				result = requestService.marshall(nameSpace, objeto, SimplefrUnderwrittingCaseManagementRequest.class)
			} else if (objeto instanceof SimplefrUnderwrittingCasesResultsRequest){
				result = requestService.marshall(nameSpace, objeto, SimplefrUnderwrittingCasesResultsRequest.class)
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

					if (eElement.getElementsByTagName("phoneNumber1").item(0) != null) {
						telefono1 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("phoneNumber2").item(0) != null) {
						telefono2 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("mobileNumber").item(0) != null) {
						telefonoMovil = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()
					}

					if (telefonoMovil != null && !telefonoMovil.isEmpty()){
						datosRegistro.telefono1 = telefonoMovil
					}

					if (telefono1 != null || telefono2 != null) {

						if (telefonoMovil == null || telefonoMovil.isEmpty()){
							datosRegistro.telefono1 = new String("999999999")
							datosRegistro.telefono2 = new String("")
							datosRegistro.telefono3 = new String("")
						} else {

							if (telefono1 != null && telefono2 == null) {
								datosRegistro.telefono2 = telefono1
								datosRegistro.telefono3 = new String("")
							}

							if (telefono1 != null && telefono2 != null)
								datosRegistro.telefono2 = telefono1
							datosRegistro.telefono3 = telefono2
						}

						if (telefono1 == null && telefono2 != null) {
							datosRegistro.telefono2 = telefono2
							datosRegistro.telefono3 = new String("")
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

			setCamposGenericos (datosRegistro)

			return datosRegistro
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}


	private void setCamposGenericos(REGISTRODATOS datos) {

		datos.lugarNacimiento = ""
		datos.pais = "FR"
		datos.emailAgente = ""
		datos.tipoCliente = "N"
		datos.franjaHoraria = ""
		datos.codigoCuestionario = ""
		datos.campo1 = "fr"
		datos.campo2 = ""
		datos.campo3 = "FR"
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

	def rellenaDatosSalida(expedientePoliza, requestDate, logginService) {

		return null
	}
}

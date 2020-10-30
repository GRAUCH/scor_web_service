package com.ws.servicios.impl.companies

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.Company
import com.scortelemed.Request
import com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest
import com.ws.lagunaro.beans.TramitacionReconocimientoMedicoRequest
import com.ws.servicios.ICompanyService
import grails.util.Holders
import hwsol.webservices.TransformacionUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

class LagunaroService implements ICompanyService{

	TransformacionUtil util = new TransformacionUtil()
	def requestService = Holders.grailsApplication.mainContext.getBean("requestService")
	def logginService = Holders.grailsApplication.mainContext.getBean("logginService")


	/**
	 * LAGUNARO  (Beans, sin namespace)
	 */

	String marshall(def objeto) {
		String result
		try{
			if (objeto instanceof GestionReconocimientoMedicoRequest){
				result = requestService.marshall(objeto, GestionReconocimientoMedicoRequest.class)
			} else if (objeto instanceof TramitacionReconocimientoMedicoRequest){
				result = requestService.marshall(objeto, TramitacionReconocimientoMedicoRequest.class)
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
			dato.servicio = rellenaServicios(req, company.nombre)
			dato.coberturas = rellenaCoberturas(req)
			return dato
		} catch (Exception e) {
			logginService.putError("buildDatos",e.toString())
		}
	}


	def getCodigoStManual(Request req) {
		return null
	}

	def rellenaDatos (req, company) {

		def mapDatos = [:]
		def listadoPreguntas = []
		def formato = new SimpleDateFormat("yyyyMMdd")
		def apellido1
		def apellido2
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

			NodeList nList = doc.getElementsByTagName("lag:poliza")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					/**El producto para lagunaro es fijo
					 * 
					 */

					datosRegistro.codigoProducto = "10100"

					if (eElement.getElementsByTagName("lag:cod_poliza").item(0) != null) {
						datosRegistro.numSolicitud = eElement.getElementsByTagName("lag:cod_poliza").item(0).getTextContent()
					}

					/**POLIZA
					 *
					 */

					if (eElement.getElementsByTagName("lag:tipo_documento").item(0) != null && eElement.getElementsByTagName("lag:tipo_documento").item(0).getTextContent().toString().equals("3")) {
						datosRegistro.numPoliza = eElement.getElementsByTagName("lag:cod_poliza").item(0).getTextContent()
					}

					/**CERTIFICADO
					 * 
					 */
					if (eElement.getElementsByTagName("lag:certificado").item(0) != null) {
						datosRegistro.numCertificado = eElement.getElementsByTagName("lag:certificado").item(0).getTextContent()
					}

					/**MOVIMIETO
					 * 
					 */

					if (eElement.getElementsByTagName("lag:movimiento").item(0) != null) {
						datosRegistro.numMovimiento = eElement.getElementsByTagName("lag:movimiento").item(0).getTextContent()
					}


					/**FECHA DE SOLICITUD
					 *
					 */

					if (eElement.getElementsByTagName("lag:fecha_efecto").item(0) != null) {
						datosRegistro.fechaEnvio = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("lag:fecha_efecto").item(0).getTextContent()).toGregorianCalendar().getTime())
					}

					/**OBSERVACIONES
					 * 
					 */
					if (eElement.getElementsByTagName("lag:texto_informativo").item(0) != null) {
						datosRegistro.observaciones = eElement.getElementsByTagName("lag:texto_informativo").item(0).getTextContent()
					}
				}
			}


			nList = doc.getElementsByTagName("lag:asegurado")


			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode



					/**NOMBRE DE CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("lag:nombre").item(0) != null) {
						datosRegistro.nombreCliente = eElement.getElementsByTagName("lag:nombre").item(0).getTextContent()
					}

					/**APELLIDO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("lag:apellido1").item(0) != null) {
						apellido1 = eElement.getElementsByTagName("lag:apellido1").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("lag:apellido2").item(0) != null) {
						apellido2 = eElement.getElementsByTagName("lag:apellido2").item(0).getTextContent()
					}

					datosRegistro.apellidosCliente = apellido1 + " " + apellido2

					/**DNI CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("lag:nif").item(0) != null) {
						datosRegistro.dni = eElement.getElementsByTagName("lag:nif").item(0).getTextContent()
					}

					/**SEXO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("lag:sexo").item(0) != null) {
						datosRegistro.sexo = eElement.getElementsByTagName("lag:sexo").item(0).getTextContent()=="H"?"V":"M"
					} else {
						datosRegistro.sexo = "M"
					}

					/**DIRECCION CLIENTE**/

					if (eElement.getElementsByTagName("lag:direccion").item(0) != null) {
						datosRegistro.direccionCliente = eElement.getElementsByTagName("lag:direccion").item(0).getTextContent()
					} else {
						datosRegistro.direccionCliente = "."
					}

					/**CODIGO POSTAL CLIENTE
					 *
					 */

					if (eElement.getElementsByTagName("lag:codigo_postal").item(0) != null) {
						datosRegistro.codigoPostal = eElement.getElementsByTagName("lag:codigo_postal").item(0).getTextContent()
					} else {
						datosRegistro.codigoPostal = "."
					}

					/**POBLACION
					 *
					 */

					if (eElement.getElementsByTagName("lag:localidad").item(0) != null) {
						datosRegistro.poblacion = eElement.getElementsByTagName("lag:localidad").item(0).getTextContent()
					} else {
						datosRegistro.poblacion = "."
					}
					/**PROVINCIA
					 *
					 */

					if (eElement.getElementsByTagName("lag:provincia").item(0) != null) {
						datosRegistro.provincia = eElement.getElementsByTagName("lag:provincia").item(0).getTextContent()
					} else {
						datosRegistro.provincia = "."
					}

					/**TELEFONOS
					 * 
					 */

					if (eElement.getElementsByTagName("lag:telefono1").item(0) != null) {
						datosRegistro.telefono1 = eElement.getElementsByTagName("lag:telefono1").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("lag:telefono2").item(0) != null) {
						datosRegistro.telefono2 = eElement.getElementsByTagName("lag:telefono2").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("lag:telefono3").item(0) != null) {
						datosRegistro.telefono3 = eElement.getElementsByTagName("lag:telefono3").item(0).getTextContent()
					}

					/**CODIGO CIA
					 *
					 */

					datosRegistro.codigoCia = company.codigoSt

					/**FECHA DE NACIMIENTO
					 *
					 */

					if (eElement.getElementsByTagName("lag:fecha_nacimiento").item(0) != null && !eElement.getElementsByTagName("lag:fecha_nacimiento").item(0).getTextContent().isEmpty()) {
						datosRegistro.fechaNacimiento = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("lag:fecha_nacimiento").item(0).getTextContent()).toGregorianCalendar().getTime())
					} else {
						datosRegistro.fechaNacimiento = formato.format(util.fromStringToXmlCalendar("2017-01-01T00:00:00").toGregorianCalendar().getTime())
					}

					/**ESTADO CIVIL
					 *
					 */

					if (eElement.getElementsByTagName("lag:estado_civil").item(0) != null) {
						datosRegistro.estadoCivil = eElement.getElementsByTagName("lag:estado_civil").item(0).getTextContent()
					}

					/**EMAIL
					 *
					 */

					if (eElement.getElementsByTagName("lag:email").item(0) != null) {
						datosRegistro.email = eElement.getElementsByTagName("lag:email").item(0).getTextContent()
					}

					/**HORA CONTACTO
					 *
					 */
					if (eElement.getElementsByTagName("lag:hora_contacto").item(0) != null) {

						if (eElement.getElementsByTagName("lag:hora_contacto").item(0).getTextContent() == "2") {
							datosRegistro.franjaHoraria ="M"
						} else if (eElement.getElementsByTagName("lag:hora_contacto").item(0).getTextContent() == "3") {
							datosRegistro.franjaHoraria ="T"
						} else if (eElement.getElementsByTagName("lag:hora_contacto").item(0).getTextContent() == "4") {
							datosRegistro.franjaHoraria ="C"
						} else
							datosRegistro.franjaHoraria =""
					}
				}
			}

			nList = doc.getElementsByTagName("lag:agente")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					/**CODIGO AGENCIA
					 *
					 */
					if (eElement.getElementsByTagName("lag:agente").item(0) != null) {
						datosRegistro.codigoAgencia = eElement.getElementsByTagName("lag:agente").item(0).getTextContent()
					}

					/**NOMBRE AGENTE
					 * 
					 */
					if (eElement.getElementsByTagName("lag:descripcion").item(0) != null) {
						datosRegistro.nomApellAgente = eElement.getElementsByTagName("lag:descripcion").item(0).getTextContent()
					}

					/**TELEFONO AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("lag:telefono").item(0) != null) {
						datosRegistro.telefonoAgente = eElement.getElementsByTagName("lag:telefono").item(0).getTextContent()
					}
				}
			}

			setCamposGenericos (datosRegistro, productCia)

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
		//datos.franjaHoraria = ""
		datos.codigoCuestionario = ""
		datos.campo1 = "es"
		datos.campo2 = ""
		datos.campo3 = "ES"
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

			NodeList nList = doc.getElementsByTagName("lag:prueba_medica")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					DATOS.Servicio servicio = new DATOS.Servicio()

					servicio.codigoServicio = eElement.getElementsByTagName("lag:codigo_prueba_medica").item(0).getTextContent()
					servicio.tipoServicios = "S"
					if (eElement.getElementsByTagName("lag:descripcion").item(0) != null) {
						servicio.descripcionServicio = eElement.getElementsByTagName("lag:descripcion").item(0).getTextContent()
					}

					servicio.filler = ""
					listadoServicios.add(servicio)
				}
			}

			nList = doc.getElementsByTagName("lag:tipo_reconocimiento_medico")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode != null && nNode.getFirstChild() != null && nNode.getFirstChild().getTextContent()){

					def valor = nNode.getFirstChild().getTextContent()

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode

						if (eElement.getFirstChild() != null) {

							DATOS.Servicio servicio = new DATOS.Servicio()

							servicio.codigoServicio = eElement.getFirstChild().getTextContent()
							servicio.tipoServicios = "P"
							servicio.descripcionServicio = ""

							servicio.filler = ""
							listadoServicios.add(servicio)
						}
					}
				}
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

			NodeList nList = doc.getElementsByTagName("lag:garantia")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					DATOS.Coberturas cobertura = new DATOS.Coberturas()

					/**COBERTURAS QUE NOS LLEGA SIEMPRE ES COB5
					 *
					 */
					cobertura.filler = ""
					cobertura.codigoCobertura = eElement.getElementsByTagName("lag:codigo").item(0).getTextContent().toUpperCase()
					cobertura.nombreCobertura = eElement.getElementsByTagName("lag:descripcion").item(0).getTextContent()
					cobertura.capital = Float.parseFloat(eElement.getElementsByTagName("lag:capital").item(0).getTextContent())

					listadoCoberturas.add(cobertura)
				}
			}

			return listadoCoberturas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}
}
package com.ws.servicios.impl.companies

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.Company
import com.scortelemed.Request
import com.scortelemed.TipoOperacion
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.ConsolidacionPolizaRequest
import com.ws.servicios.ICompanyService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat

import static grails.async.Promises.task

class CajamarService implements ICompanyService{

	TransformacionUtil transformacion = new TransformacionUtil()
	def requestService
	def expedienteService
	def tarificadorService
	def grailsApplication
	def logginService

	/**
	 * CAJAMAR  (Beans, sin namespace)
	 */

	String marshall(def objeto) {
		String nameSpace = "http://www.scortelemed.com/schemas/cajamar"
		String result
		try{
			if (objeto instanceof CajamarUnderwrittingCaseManagementRequest){
				result = requestService.marshall(nameSpace, objeto, CajamarUnderwrittingCaseManagementRequest.class)
			} else if (objeto instanceof ConsolidacionPolizaRequest){
				result = requestService.marshall(nameSpace, objeto, ConsolidacionPolizaRequest.class)
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
			dato.pregunta = rellenaPreguntas(req, company.nombre)
			dato.servicio = rellenaServicios(req, company.nombre)
			dato.coberturas = rellenaCoberturas(req)
			return dato
		} catch (Exception e) {
			logginService.putError(e.toString())
		}
	}


	def getCodigoStManual(Request req) {
		return null //No tiene particularidades
	}

	def rellenaDatos (req, company) {

		def mapDatos = [:]
		def listadoPreguntas = []
		def formato = new SimpleDateFormat("yyyyMMdd")
		def apellido1
		def apellido2
		def codigoCliente
		def telefono = null
		def telefonoMovil = null
		def dias
		def obs
		def franjaInicio
		def franjaFin
		def anio
		def mes
		def dia

		REGISTRODATOS datos = new REGISTRODATOS()

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()

			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			doc.getDocumentElement().normalize()

			NodeList nList = doc.getElementsByTagName("RegScor")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					/**NUMERO DE REFERENCIA
					 *
					 */
					if (eElement.getElementsByTagName("yprodu").item(0) != null) {
						datos.codigoProducto = eElement.getElementsByTagName("yprodu").item(0).getTextContent()
					}
					/**NUMERO DE REFERENCIA
					 *
					 */
					if (eElement.getElementsByTagName("numref").item(0) != null) {
						datos.numSolicitud = eElement.getElementsByTagName("numref").item(0).getTextContent()
					}
					/**FECHA DE SOLICITUD
					 *
					 */
					if (eElement.getElementsByTagName("fechso").item(0) != null) {
						datos.fechaEnvio = eElement.getElementsByTagName("fechso").item(0).getTextContent()
					}
					/**CODIGO DE AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("ofsucu").item(0) != null) {
						datos.codigoAgencia = eElement.getElementsByTagName("ofsucu").item(0).getTextContent()
					}
					/**TELEFONO DE AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("telccc").item(0) != null) {
						datos.telefonoAgente = eElement.getElementsByTagName("telccc").item(0).getTextContent()
					}
					/**NOMBRE DE AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("sucnom").item(0) != null) {
						datos.nomApellAgente = eElement.getElementsByTagName("sucnom").item(0).getTextContent()
					}
					/**CODIGO DE CLIENTE CM
					 *
					 */
					if (eElement.getElementsByTagName("wpers").item(0) != null) {
						codigoCliente = eElement.getElementsByTagName("wpers").item(0).getTextContent()
					}
					/**NOMBRE DE CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("nmbase").item(0) != null) {
						datos.nombreCliente = eElement.getElementsByTagName("nmbase").item(0).getTextContent()
					}
					/**APELLIDO 1 CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("ap1ase").item(0) != null) {
						apellido1 = eElement.getElementsByTagName("ap1ase").item(0).getTextContent()
					}
					/**APELLIDO 2 CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("ap2ase").item(0) != null) {
						apellido2 = eElement.getElementsByTagName("ap2ase").item(0).getTextContent()
					}

					datos.apellidosCliente = apellido1 + " " + apellido2

					/**DNI CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("dniase").item(0) != null) {
						datos.dni = eElement.getElementsByTagName("dniase").item(0).getTextContent()
					}
					/**SEXO CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("ysexcl").item(0) != null) {
						datos.sexo = eElement.getElementsByTagName("ysexcl").item(0).getTextContent()=="M"?"M":"V"
					}
					/**DIRECCION CLIENTE**/
					if (eElement.getElementsByTagName("wdomcc").item(0) != null) {
						datos.direccionCliente = eElement.getElementsByTagName("wdomcc").item(0).getTextContent()
					}
					/**CODIGO POSTAL CLIENTE
					 *
					 */
					if (eElement.getElementsByTagName("ydpost").item(0) != null) {
						datos.codigoPostal = eElement.getElementsByTagName("ydpost").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("wpobcl").item(0) != null) {
						datos.poblacion = eElement.getElementsByTagName("wpobcl").item(0).getTextContent()
					}
					if (eElement.getElementsByTagName("wprocl").item(0) != null) {
						datos.provincia = eElement.getElementsByTagName("wprocl").item(0).getTextContent()
					}
					if (eElement.getElementsByTagName("tefcli").item(0) != null && !eElement.getElementsByTagName("tefcli").item(0).getTextContent().toString().equals("999999999") && !eElement.getElementsByTagName("tefcli").item(0).getTextContent().toString().equals("000000000")) {
						telefono = eElement.getElementsByTagName("tefcli").item(0).getTextContent()
					}
					if (eElement.getElementsByTagName("movcli").item(0) != null && !eElement.getElementsByTagName("movcli").item(0).getTextContent().toString().equals("999999999") && !eElement.getElementsByTagName("movcli").item(0).getTextContent().toString().equals("000000000")) {
						telefonoMovil = eElement.getElementsByTagName("movcli").item(0).getTextContent()
					}

					if (telefono == null && telefonoMovil == null){
						datos.telefono1 = new String("999999999")
					}

					if (telefono != null && telefonoMovil != null){
						datos.telefono1 = telefono
						datos.telefono2 = telefonoMovil
					}

					if (telefono != null && telefonoMovil == null){
						datos.telefono1 = telefono
					}

					if (telefono == null && telefonoMovil != null){
						datos.telefono1 = telefonoMovil
					}

					/**HORA INICIO DE CONTACTO CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("franhi").item(0) != null) {
						franjaInicio = eElement.getElementsByTagName("franhi").item(0).getTextContent()
					}
					/**HORA FIN DE CONTACTO CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("franhf").item(0) != null) {
						franjaFin = eElement.getElementsByTagName("franhf").item(0).getTextContent()
					}
					/**DIAS DE CONTACTO CANDIDATO
					 *
					 */
					if (eElement.getElementsByTagName("diastl").item(0) != null) {
						dias = transformacion.obtenerDiasContacto(eElement.getElementsByTagName("diastl").item(0).getTextContent())
					}

					if (codigoCliente != null && !codigoCliente.isEmpty()){
						obs = "Id_Cliente: " + codigoCliente +";\n"
					}
					if (franjaInicio != null && !franjaInicio.isEmpty() && franjaFin != null && !franjaFin.isEmpty()){
						obs += franjaInicio + "-" + franjaFin + "--Dias: " + dias
					}
					/**OBSERVACIONES EXPEDIENTE
					 *
					 */
					datos.observaciones = obs
					datos.codigoCia = company.codigoSt

					/**FECHA DE NACIMIENTO
					 *
					 */
					if (eElement.getElementsByTagName("yprodu").item(0) != null) {
						anio = eElement.getElementsByTagName("aanccl").item(0).getTextContent()
						mes = eElement.getElementsByTagName("mmnccl").item(0).getTextContent()
						dia = eElement.getElementsByTagName("ddnccl").item(0).getTextContent()
					}

					datos.fechaNacimiento = anio+mes+dia
				}



				/**CAMPOS GENERICOS NECESARIOS PARA LA CARGA BPEL
				 *
				 */
				setCamposGenericos (datos)

				return datos
			}
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}


	private void setCamposGenericos(REGISTRODATOS datos) {

		datos.telefono3 = ""
		datos.email = ""
		datos.lugarNacimiento = ""
		datos.pais = "ES"
		datos.claveValidacionCliente = ""
		datos.emailAgente = ""
		datos.estadoCivil = ""
		datos.tipoCliente = "N"
		datos.franjaHoraria = ""
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




	private def rellenaPreguntas (req, nameCompany) {

		def listadoPreguntas = []

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()

			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			doc.getDocumentElement().normalize()

			NodeList nList = doc.getElementsByTagName("RegScor")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode


					/**PREGUNTAS PREVIAS
					 *
					 */

					if (eElement.getElementsByTagName("xsegvi").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "1"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xsegvi").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xbajam").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "2"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xbajam").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xfisic").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "4"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xfisic").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xhospi").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "5"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xhospi").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xdrosn").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "6"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xdrosn").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xdepre").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "7"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xdepre").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xfumsn").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "9"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xfumsn").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xpsida").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "10"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xpsida").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("xesvid").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "11"
						pregunta.tipoDatos = "BOLEAN"
						pregunta.respuesta = eElement.getElementsByTagName("xesvid").item(0).getTextContent()=="S"?"SI":"NO"
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("pesokg").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "peso"
						pregunta.tipoDatos = "STRING"
						pregunta.respuesta = eElement.getElementsByTagName("pesokg").item(0).getTextContent()
						listadoPreguntas.add(pregunta)
					}
					if (eElement.getElementsByTagName("estatu").item(0) != null) {
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "altu"
						pregunta.tipoDatos = "STRING"
						pregunta.respuesta = eElement.getElementsByTagName("estatu").item(0).getTextContent()
						listadoPreguntas.add(pregunta)
					}
				}
			}

			return listadoPreguntas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e))
		}
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

			NodeList nList = doc.getElementsByTagName("RegScor")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					DATOS.Coberturas cobertura = new DATOS.Coberturas()

					/**COBERTURAS
					 *
					 */
					if (eElement.getElementsByTagName("tlcom").item(0) != null && eElement.getElementsByTagName("tlcom").item(0).getTextContent() == "1") {
						DATOS.Servicio servicio = new DATOS.Servicio()
						servicio.tipoServicios = "S"
						servicio.descripcionServicio = "Teleseleccion Mediana"
						servicio.filler = ""
						servicio.codigoServicio = "TM"
						listadoServicios.add(servicio)
					}

					if (eElement.getElementsByTagName("tlabr").item(0) != null && eElement.getElementsByTagName("tlabr").item(0).getTextContent() == "1") {
						DATOS.Servicio servicio = new DATOS.Servicio()
						servicio.tipoServicios = "S"
						servicio.descripcionServicio = "Teleseleccion Corta"
						servicio.filler = ""
						servicio.codigoServicio = "TC"
						listadoServicios.add(servicio)
					}

					if (eElement.getElementsByTagName("zprumd").item(0) != null) {

						def pruebaMedica = eElement.getElementsByTagName("zprumd").item(0).getTextContent()
						def codigoP

						switch (pruebaMedica) {
							case "B":
								codigoP = "637"
								break
							case "C":
								codigoP = "638"
								break
							case "D":
								codigoP = "639"
								break
							case "E":
								codigoP = "640"
								break
							case "F":
								codigoP = "641"
								break
							default:
								break
						}


						DATOS.Servicio servicio = new DATOS.Servicio()
						servicio.tipoServicios = "P"
						servicio.descripcionServicio = "Paquete " + codigoP
						servicio.filler = ""
						servicio.codigoServicio = codigoP
						listadoServicios.add(servicio)
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


		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()
			def codigoProducto
			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			NodeList nList = doc.getElementsByTagName("RegScor")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					/**NUMERO DE REFERENCIA
					 *
					 */
					if (eElement.getElementsByTagName("yprodu").item(0) != null) {
						codigoProducto = eElement.getElementsByTagName("yprodu").item(0).getTextContent()
					}
				}
			}

			doc.getDocumentElement().normalize()

			nList = doc.getElementsByTagName("cobertType")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					DATOS.Coberturas cobertura = new DATOS.Coberturas()

					/**COBERTURAS
					 *
					 */
					if (eElement.getElementsByTagName("cobern").item(0) != null) {
						cobertura.filler = ""
						def codigoCobertura = eElement.getElementsByTagName("cobern").item(0).getTextContent()

						def codigoC = obtenerCobertura(codigoProducto, codigoCobertura);

						cobertura.codigoCobertura = codigoC
						cobertura.nombreCobertura = codigoC
						cobertura.capital = 0
						listadoCoberturas.add(cobertura)
					}
				}
			}

			return listadoCoberturas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}

	def busquedaCrm (numref, ou, opername, comapanyCodigoSt, companyId, requestBBDD) {

		task {

			logginService.putInfoMessage("Buscando en CRM solicitud de Cajamar con requestNumber: " + numref.toString())

			def respuestaCrm
			int limite = 0;
			boolean encontrado = false;


			servicios.Filtro filtro = new servicios.Filtro()
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
			CorreoUtil correoUtil = new CorreoUtil()

			Thread.sleep(90000);

			try {

				while( !encontrado && limite < 10) {

					filtro.setClave(servicios.ClaveFiltro.CLIENTE);
					filtro.setValor(comapanyCodigoSt.toString());
					servicios.Filtro filtroRelacionado = new servicios.Filtro()
					filtroRelacionado.setClave(servicios.ClaveFiltro.NUM_SOLICITUD)
					filtroRelacionado.setValor(numref.toString())
					filtro.setFiltroRelacionado(filtroRelacionado)

					respuestaCrm = expedienteService.consultaExpediente(ou.toString(),filtro)

					if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {

						for (int i = 0; i < respuestaCrm.getListaExpedientes().size(); i++) {

							servicios.Expediente exp = respuestaCrm.getListaExpedientes().get(i)

							String fechaCreacion = format.format(new Date());

							if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(comapanyCodigoSt.toString()) &&
							exp.getNumSolicitud() != null && exp.getNumSolicitud().equals(numref.toString()) && fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())){

								/**Alta procesada correctamente
								 *
								 */

								encontrado = true
							}
						}
					}

					if (encontrado) {

						logginService.putInfoMessage("Nueva alta automatica de Cajamar con numero de solicitud: " + numref.toString() + " procesada correctamente")
					}

					limite++
					Thread.sleep(10000)
				}

				/**Alta procesada pero no se ha encontrado en CRM.
				 *
				 */
				if (limite == 10) {

					logginService.putInfoMessage("Nueva alta de Cajamar con numero de solicitud: " + numref.toString() + " se ha procesado pero no se ha dado de alta en CRM")
					correoUtil.envioEmailErrores("BusquedaExpedienteCrm","Nueva alta de Cajamar con numero de solicitud: " + numref.toString() + " se ha procesado pero no se ha dado de alta en CRM",null)
					requestService.insertarError(companyId.toString(), numref.toString(), (String)requestBBDD.request, TipoOperacion.ALTA, "Peticion procesada para numero de solictud: " + numref.toString() + ". No encontrada en CRM")

				}
			} catch (Exception e) {

				logginService.putErrorMessage("Nueva alta de Cajamar con numero de solicitud: " + numref.toString() + " no se ha procesado: Motivo: " + e.getMessage())
				correoUtil.envioEmailErrores(opername,"Nueva alta de Cajamar con numero de solicitud: " + numref.toString() + " no se ha procesado: Motivo: " + e.getMessage(),null)
			}
		}
	}


	String obtenerCobertura(String producto, String codigoCajamar){

		def codigoCRM

		switch (producto) {
			case "TPC02":
				if (codigoCajamar.equals("21001")){
					codigoCRM = "COB215"
				}
				if (codigoCajamar.equals("21003")){
					codigoCRM = "COB216"
				}
				if (codigoCajamar.equals("21007")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("21009")){
					codigoCRM = "COB223"
				}
				break
			case "TPC05":
				if (codigoCajamar.equals("21001")){
					codigoCRM = "COB215"
				}
				if (codigoCajamar.equals("21003")){
					codigoCRM = "COB216"
				}
				if (codigoCajamar.equals("21007")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("21009")){
					codigoCRM = "COB223"
				}
				break
			case "TPU01":
				if (codigoCajamar.equals("21001")){
					codigoCRM = "COB215"
				}
				if (codigoCajamar.equals("21003")){
					codigoCRM = "COB216"
				}
				break
			case "TPU03":
				if (codigoCajamar.equals("21001")){
					codigoCRM = "COB215"
				}
				if (codigoCajamar.equals("21003")){
					codigoCRM = "COB216"
				}
				break
			case "TPU11":
				if (codigoCajamar.equals("21001")){
					codigoCRM = "COB215"
				}
				if (codigoCajamar.equals("21003")){
					codigoCRM = "COB216"
				}
				break
			case "TRA01":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("401")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				if (codigoCajamar.equals("703")){
					codigoCRM = "COB223"
				}
				break
			case "TRA02":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA03":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA04":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA05":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA06":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				break
			case "TRA08":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("10001")){
					codigoCRM = "COB213"
				}
				if (codigoCajamar.equals("10002")){
					codigoCRM = "COB214"
				}
				if (codigoCajamar.equals("301")){
					codigoCRM = "COB218"
				}
				if (codigoCajamar.equals("302")){
					codigoCRM = "COB219"
				}
				if (codigoCajamar.equals("401")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				if (codigoCajamar.equals("703")){
					codigoCRM = "COB223"
				}
				break
			case "TRA11":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("401")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				if (codigoCajamar.equals("703")){
					codigoCRM = "COB223"
				}
				break
			case "TRA12":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA13":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA14":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA15":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				break
			case "TRA16":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				break
			case "TRA32":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("401")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				if (codigoCajamar.equals("703")){
					codigoCRM = "COB223"
				}
				break
			case "TRA35":
				if (codigoCajamar.equals("201")){
					codigoCRM = "COB217"
				}
				if (codigoCajamar.equals("401")){
					codigoCRM = "COB220"
				}
				if (codigoCajamar.equals("702")){
					codigoCRM = "COB221"
				}
				if (codigoCajamar.equals("703")){
					codigoCRM = "COB223"
				}
			default:
				break
		}

		return codigoCRM
	}

}

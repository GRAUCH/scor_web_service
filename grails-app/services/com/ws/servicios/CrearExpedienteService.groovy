package com.ws.servicios
import grails.util.Environment
import hwsol.webservices.FetchUtilLagunaro

import java.text.SimpleDateFormat

import org.apache.axis.types.Token

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.*
import com.scortelemed.Conf
import com.scortelemed.Estadistica

class CrearExpedienteService {

	/*
	 Se conecta al CRM y devuelve los expedientes tarificados para una fecha
	 */
	def grailsApplication
	def fetchUtil = new FetchUtilLagunaro()
	def expedienteService
	def requestService
	def logginService = new LogginService()

	/**
	 * AFI_ESCA, ALPTIS, ZEN_UP(Lifesquare)
	 */
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

		listadoFinal.add(expedienteService.buildCabecera(req, null))
		listadoFinal.add(buildDatos(req, req.company))
		listadoFinal.add(expedienteService.buildPie(null))

		payload.cabeceraOrDATOSOrPIE = listadoFinal

		return payload
	}

	private def buildDatos = { req, company ->
		try {
			DATOS dato = new DATOS()
			dato.registro = rellenaDatos(req, company)
			dato.pregunta = rellenaPreguntas(req, company.nombre)
			dato.servicio = rellenaServicio(req, company.nombre)
			dato.coberturas = rellenaCoberturas(req)
			return dato
		} catch (Exception e) {
			logginService.putError(e.toString())
		}
	}

	def rellenaDatos = { req, company ->
		def mapDatos = [:]
		def formato = new SimpleDateFormat("yyyyMMdd");

		try {
			def estadisticas = Estadistica.findAllByRequestAndClaveInList(req, ["policy", "candidate", "agent", "request_Data"])
			estadisticas.each {
				it.value.split("##").each { valor ->
					def valores = valor.split("==")
					if (valores.size() == 2) {
						mapDatos.put(valores[0], valores[1])
					}
				}
			}

			REGISTRODATOS datos = new REGISTRODATOS()
			datos.nombreCliente = mapDatos.get("name")
			datos.apellidosCliente = mapDatos.get("surname")


			if (mapDatos.get("phone1") == null && mapDatos.get("phone2") == null ){
				datos.telefono1 = new String("999999999")
				datos.telefono2 = new String("")
			} else {
				if (mapDatos.get("phone1") != null) {
					if (!mapDatos.get("phone1").toString().startsWith("0033")){
						datos.telefono1 = "0033"+mapDatos.get("phone1")
					} else {
						datos.telefono1 = mapDatos.get("phone1")
					}
				} else {
					if (!mapDatos.get("phone2").toString().startsWith("0033")){
						datos.telefono1 = "0033"+mapDatos.get("phone2")
						datos.telefono2 = "0033"+mapDatos.get("phone2")
					} else {
					    datos.telefono1 = mapDatos.get("phone2")
						datos.telefono2 = mapDatos.get("phone2")
					}
				}
				if (mapDatos.get("phone2") != null) {
					if (!mapDatos.get("phone2").toString().startsWith("0033")){
						datos.telefono1 = "0033"+ mapDatos.get("phone2")
						datos.telefono2 = "0033"+ mapDatos.get("phone2")
					} else {
					    datos.telefono1 = mapDatos.get("phone2")
						datos.telefono2 = mapDatos.get("phone2")
					}
				} else{
					datos.telefono2 = new String("")
				}
			}
			if (mapDatos.get("phone3") != null) {
				if (!mapDatos.get("phone3").toString().startsWith("0033")){
					datos.telefono3 = "0033"+mapDatos.get("phone3")
				} else {
					datos.telefono3 = mapDatos.get("phone3")
				}
			}

			datos.provincia = mapDatos.get("province")
			datos.poblacion = mapDatos.get("town")
			datos.direccionCliente = mapDatos.get("address")
			datos.codigoPostal = mapDatos.get("postal_code")
			datos.dni = mapDatos.get("fiscal_identification_number")
			datos.fechaNacimiento = mapDatos.get("birth_date").split("\\+")[0].replace("-", "")
			datos.sexo = mapDatos.get("gender")=="M"?"M":"V"
			datos.modificarSolicitudesDuplicadas = "N"
			datos.email = mapDatos.get("email")

			datos.estadoExpediente = "En attente contact"
			datos.codigoCia = company.codigoSt
			datos.codigoAgencia = company.codigoSt
			datos.tipoServicio = 0
			datos.codigoProducto = obtenerCodigoProductoPorNombreCompania(company.nombre, mapDatos.get("product"))
			datos.fechaGeneracion = new Token()
			datos.fechaEnvio = formato.format(new Date())
			
			/**PARA EL CASO DE AFIESCA SE INTECAMBIAN ESTOS DATOS
			 * 
			 */
			if (company.nombre.equals("afiesca")){
				datos.numPoliza = mapDatos.get("reference_number")?mapDatos.get("reference_number"):mapDatos.get("policy_number")
				datos.numSolicitud = mapDatos.get("policy_number")
			} else {
				datos.numPoliza = mapDatos.get("policy_number")
				datos.numSolicitud = mapDatos.get("reference_number")?mapDatos.get("reference_number"):mapDatos.get("policy_number")
			}
			datos.numSecuencia = " "
			datos.numMovimiento =" "
			datos.numCertificado =" "
			datos.modificarSolicitudesDuplicadas = "N"
			datos.capitalFallecimiento = 0
			datos.tipoCliente = mapDatos.get("client_type")
			datos.lugarNacimiento = ""
			datos.claveValidacionCliente = ""
			datos.nomApellAgente = mapDatos.get("description")
			datos.telefonoAgente = mapDatos.get("phone")
			datos.emailAgente = ""
			datos.estadoCivil = mapDatos.get("marital_status")
			datos.franjaHoraria= mapDatos.get("contact_time")

			datos.codigoCuestionario = ""
			def duration = mapDatos.get("duration")?"Durée de la Polize(mois) : " + mapDatos.get("duration"):""
			def obs = mapDatos.get("comments")?mapDatos.get("comments"):""
			datos.observaciones = obs.toString() + "" + duration.toString()
			//		datos.coberturaFallecimiento = "COB5"
			datos.capitalInvalidez = 0
			//		datos.coberturaInvalidez = "COB4"

			datos.pais= "FR"
			datos.campo1 = "fr"
			datos.campo2 = ""
			datos.campo3 = "FR"
			datos.campo4 = mapDatos.get("euw_code")?mapDatos.get("euw_code"):""
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

			return datos
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
		}
	}

	def rellenaPreguntas = { req, nameCompany ->
		def listadoPreguntas = []
		def mapDatos = [:]

		try {
			if (nameCompany.equals("lifesquare")) {
				def result = obtainMapData(req,"policy")
				def listQuestions = ["activate_profession##OCUPATION", "activate_sport##sport", "activate_oblivion##DR"]

				listQuestions.each {
					def question = it.split("##")
					DATOS.Pregunta que = new DATOS.Pregunta()
					que.codigoPregunta = question[1]
					que.tipoDatos = "BOLEAN"

					if (result.get(question[0]) && result.get(question[0]).toLowerCase().equals("oui")) {
						que.respuesta = "SI"
					} else {
						que.respuesta = "NO"
					}

					listadoPreguntas.add(que)
				}
			} else {
				def estadisticas = Estadistica.findAllByRequestAndClave(req, "question")

				estadisticas.value.each {
					def question = it.split("%%")

					question.each { q ->
						def questionFinal = q.split("##")
						DATOS.Pregunta que = new DATOS.Pregunta()

						questionFinal.each { qf ->
							def result = qf.split("==")

							if (result && result.size()==2) {
								if (result[0].equals("question_code")) {
									que.codigoPregunta = result[1]
								} else if (result[0].equals("answer")) {
									que.respuesta = obtainAnswer(result[1])
								} else if (result[0].equals("data_type")) {
									que.tipoDatos = obtainTypeData(result[1])
								}
							}
						}
						listadoPreguntas.add(que)
					}
				}
			}

			return listadoPreguntas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e));
		}
	}

	def rellenaServicio = { req, nombre ->
		def listadoServicios = []
		listadoServicios.add(obtenerServicioPorNombreCompania(req, nombre))

		return listadoServicios
	}

	def rellenaCoberturas = { req ->
		
		def listadoCoberturas = []
		def capital = 0

		try {
			
			def estadisticas = Estadistica.findAllByRequestAndClave(req, "benefit")
		
				estadisticas.each {
				it.value.split("%%").each { benefit ->
					def benefits = benefit.split("##")
					def mapCoberturas = [:]
					DATOS.Coberturas cobertura = new DATOS.Coberturas()
					cobertura.filler = ""

					benefits.each { ben ->
						def coberAux = ben.split("==")
						if (coberAux[0].equals("benefit_name")) {
							cobertura.codigoCobertura = obtainCodeCover(coberAux[1])
							cobertura.nombreCobertura = coberAux[1]
						} else if (coberAux[0].equals("benefit_capital")) {
							
							cobertura.capital = Float.parseFloat(coberAux[1])
							
							if (cobertura.codigoCobertura.equals("COB5")){
								capital = cobertura.capital
							}
						}
					}
					
					listadoCoberturas.add(cobertura)
				}
			}
				
			def mapDatos = [:]
			
			estadisticas = Estadistica.findAllByRequestAndClave(req, "policy")
			estadisticas.each {
				it.value.split("##").each { valor ->
					def valores = valor.split("==")
					if (valores.size() == 2) {
						mapDatos.put(valores[0], valores[1])
					}
				}
			}
			
			if (req.company.nombre.equals("alptis") && mapDatos.get("product").equals("Pareo-V6")){
				
				DATOS.Coberturas cobertura = new DATOS.Coberturas()
				cobertura.filler = ""
				
				cobertura.codigoCobertura = "COB300"
				cobertura.nombreCobertura = "GIS"
				cobertura.capital = capital
				
				listadoCoberturas.add(cobertura)
			}

			return listadoCoberturas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e));
		}
	}

	private def obtenerCodigoProductoPorNombreCompania = { nombre, codeProduct ->
		def result

		if (nombre.equals("alptis")) {
			result = codeProduct
		} else if (nombre.equals("lifesquare")){
			result = "Emprunteur"
		} else if (nombre.equals("afiesca")){
			result = "PERENIM"
		}

		return result
	}

	private def obtenerServicioPorNombreCompania = { req, nombre ->
		def mapDatos = [:]

		DATOS.Servicio servicio = new DATOS.Servicio()
		servicio.tipoServicios = "S"
		servicio.descripcionServicio = "Teleseleccion Francia"
		servicio.filler = ""

		def estadisticas = Estadistica.findAllByRequestAndClave(req, "policy")
		estadisticas.each {
			it.value.split("##").each { valor ->
				def valores = valor.split("==")
				if (valores.size() == 2) {
					mapDatos.put(valores[0], valores[1])
				}
			}
		}

		if (Environment.current.name.equals("production_wildfly")) {
			if (nombre.equals("alptis")) {
				servicio.codigoServicio = "000314"
			} else if (nombre.equals("afiesca")){
				servicio.codigoServicio = "001773"
			} else if (nombre.equals("lifesquare")){
				if (mapDatos.get("euw_code") && mapDatos.get("euw_code")!="") {
					servicio.codigoServicio = "TC"
				} else {
					servicio.codigoServicio = "TF"
				}
			}
		} else {
			if (nombre.equals("alptis")) {
				servicio.codigoServicio = "000314"
			} else if (nombre.equals("afiesca")){
				servicio.codigoServicio = "001730"
			} else if (nombre.equals("lifesquare")){
				if (mapDatos.get("euw_code") && mapDatos.get("euw_code")!="") {
					servicio.codigoServicio = "TC"
				} else {
					servicio.codigoServicio = "TF"
				}
			}
		}

		return servicio
	}

	private def mapCoberturas = { ->
		def mapCobers = [:]
		mapCobers.put("DECES", "COB5")
		mapCobers.put("PTIA", "COB20")
		mapCobers.put("IPP", "COB10")
		mapCobers.put("Incapacité (<30 jours)", "COB8")
		mapCobers.put("Invalidité ? 66%", "COB4")
		mapCobers.put("Incapacité (>90 jours)", "COB6")
		mapCobers.put("Incapacité (30-90 jours)", "COB7")

		return mapCobers
	}

	private def obtainCodeCover = { name ->
		def result
		name = requestService.convertirAscii(name)


		if (name.equals("DECES")) {
			result = "COB5"
		} else if (name.equals("PTIA"))  {
			result = "COB20"
		} else if (name.equals("IPP"))  {
			result = "COB10"
		} else if (name.indexOf("30-90") != -1)  {
			result = "COB7"
		} else if (name.indexOf("66") != -1)  {
			result = "COB4"
		} else if (name.indexOf("TOTALE") != -1)  {
			result = "COB200"
		} else if (name.indexOf("TEMPORAIRE") != -1 && name.indexOf("30") != -1)  {
			result = "COB201"
		} else if (name.indexOf("TEMPORAIRE") != -1 && name.indexOf("90") != -1)  {
			result = "COB202"
		} else if (name.indexOf("TEMPORAIRE") != -1 && name.indexOf("60") != -1)  {
			result = "COB203"
		} else if (name.indexOf("TEMPORAIRE") != -1 && name.indexOf("120") != -1)  {
			result = "COB204"
		} else if (name.indexOf("TEMPORAIRE") != -1 && name.indexOf("180") != -1)  {
			result = "COB205"
		} else if (name.indexOf("90") != -1 && name.indexOf("INCAPACITE") != -1)  {
			result = "COB6"
		} else if (name.indexOf("30") != -1 && name.indexOf("INCAPACITE") != -1)  {
			result = "COB8"
		} else if (name.indexOf("PARTIELLE") != -1)  {
			result = "COB206"
		} else if (name.indexOf("DORSALES") != -1)  {
			result = "COB207"
		} else if (name.indexOf("PSYCHIATRIQUES") != -1)  {
			result = "COB208"
		}

		return result
	}

	private def obtainMapData = { req, clave ->
		def mapDatos = [:]
		def estadisticas = Estadistica.findAllByRequestAndClave(req, clave)

		estadisticas.each {
			it.value.split("##").each { valor ->
				def valores = valor.split("==")
				if (valores.size() == 2) {
					mapDatos.put(valores[0], valores[1])
				}
			}
		}

		return mapDatos
	}

	private def obtainTypeData = { type ->
		def result

		if (type.equals("0") || type.equals("5")) {
			result = "BOLEAN"
		} else if (type.equals("1") || type.equals("3") || type.equals("4")) {
			result = "INTERGER"
		} else if (type.equals("2") || type.equals("6")) {
			result = "STRING"
		}

		return result
	}

	private def obtainAnswer = { answer ->
		def result

		if (answer.equals("0")) {
			result = "NO"
		} else if (answer.equals("1")) {
			result = "SI"
		} else { //if (result.equals("2") || result.equals("6")) {
			result = answer
		}

		return result
	}
}
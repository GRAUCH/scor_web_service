package com.ws.servicios.impl.companies

import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.BenefitInformation
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE
import com.scor.global.ContentResult
import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.DATOS
import com.scor.srpfileinbound.REGISTRODATOS
import com.scortelemed.*
import com.scortelemed.schemas.ama.*
import com.scortelemed.schemas.ama.ConsultaDocumentoResponse.Documento
import com.scortelemed.schemas.ama.ResultadoSiniestroResponse.Expediente
import com.scortelemed.schemas.ama.ResultadoSiniestroResponse.Expediente.Siniestro
import com.scortelemed.schemas.ama.ResultadoSiniestroResponse.Expediente.Siniestro.Pago
import com.scortelemed.schemas.ama.ResultadoSiniestroResponse.Expediente.Siniestro.Provision
import com.scortelemed.servicios.TipoSexo
import com.ws.servicios.ICompanyService
import grails.util.Holders
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import servicios.*

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.text.SimpleDateFormat


class AmaService implements ICompanyService{

	TransformacionUtil util = new TransformacionUtil()
	def requestService = Holders.grailsApplication.mainContext.getBean("requestService")
	def logginService = Holders.grailsApplication.mainContext.getBean("logginService")
	ContentResult contentResult = new ContentResult()

	String marshall(def objeto) {
		String nameSpace = "http://www.scortelemed.com/schemas/ama"
		String result
		try{
			if (objeto instanceof ResultadoReconocimientoMedicoRequest){
				result = requestService.marshall(nameSpace, objeto, ResultadoReconocimientoMedicoRequest.class)
			} else if (objeto instanceof GestionReconocimientoMedicoRequest){
				result = requestService.marshall(nameSpace, objeto, GestionReconocimientoMedicoRequest.class)
			} else if (objeto instanceof ResultadoSiniestroRequest){
				result = requestService.marshall(nameSpace, objeto, ResultadoSiniestroRequest.class)
			} else if (objeto instanceof SaveDossierResultsE){
				result = requestService.marshall(nameSpace, objeto, SaveDossierResultsE.class)
			} else if (objeto instanceof ConsolidacionPolizaRequest){
				result = requestService.marshall(nameSpace, objeto, ConsolidacionPolizaRequest.class)
			} else if (objeto instanceof ConsultaExpedienteRequest){
				result = requestService.marshall(nameSpace, objeto, ConsultaExpedienteRequest.class)
			}
		} finally {
			return result
		}
	}

	def buildDatos(Request req, String codigoCia) {
		try {
			DATOS dato = new DATOS()
			dato.registro = rellenaDatos(req, codigoCia)
			dato.servicio = rellenaServicios(req)
			dato.coberturas = rellenaCoberturas(req)
			dato.pregunta = rellenaPreguntas(req)
			return dato
		} catch (Exception e) {
			logginService.putError("buildDatos",e.toString())
		}
	}

	/**
	 * Ama usa el mismo wb para dos cias distintas, asi que tenemos que sacar el id del campo ciaCode del xml
	 */

	def getCodigoStManual(Request req) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
		DocumentBuilder builder = factory.newDocumentBuilder()

		InputSource is = new InputSource(new StringReader(req.request))
		is.setEncoding("UTF-8")
		Document doc = builder.parse(is)
		String codigoCiaAma
		String codigoCia

		doc.getDocumentElement().normalize()

		NodeList nList = doc.getElementsByTagName("CandidateInformation")

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp)

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode

				/**NUMERO DE PRODUCTO
				 *
				 */
				if (eElement.getElementsByTagName("ciaCode").item(0) != null) {
					codigoCiaAma = eElement.getElementsByTagName("ciaCode").item(0).getTextContent()
					if (codigoCiaAma != null && !codigoCiaAma.isEmpty()){
						if (codigoCiaAma.equals("C0803")){
							codigoCia = Company.findByNombre(TipoCompany.AMA_VIDA.nombre).codigoSt
						}
						if (codigoCiaAma.equals("M0328")){
							codigoCia = Company.findByNombre(TipoCompany.AMA.nombre).codigoSt
						}
					}
				}
			}
		}
		return codigoCia
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

		expediente.setPolicyHolderInformation(policyHolderInformationType)

		if (expedientePoliza.getCandidato() != null) {

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd")
			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd")

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

			expediente.setCandidateInformation(candidateInformation)
		}

		if (expedientePoliza.getCoberturasExpediente() != null && expedientePoliza.getCoberturasExpediente().size() > 0) {

			expedientePoliza.getCoberturasExpediente().each { coberturasPoliza ->

				BenefitsType benefitsType = new BenefitsType()
				benefitsType.setBenefictName(devolverNombreCobertura(coberturasPoliza.getCodigoCobertura()))
				benefitsType.setBenefictCode(coberturasPoliza.getCodigoCobertura())
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
			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd")


			expedientePoliza.getListaCitas().each {Cita cita ->

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

				expediente.getAppointments().add(appointments)
			}
		}

		if (expedientePoliza.getListaLlamadas() != null && expedientePoliza.getListaLlamadas().size() > 0) {

			expedientePoliza.getListaLlamadas().each {Llamada actividad ->


				SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd")

				ActivitiesType activities = new ActivitiesType()

				activities.setDescripcion(actividad.getDescripcionLlamada())
				activities.setDestinatarios(actividad.getTipoDestinatario().toString())
				activities.setFechaCreacion(myFormat.format(actividad.getFechaCreacionLlamada().toGregorianCalendar().getTime()))
				activities.setNumeroDestino(actividad.getNumeroDestino())
				activities.setStatusCode(actividad.getTipoLLamada().toString())
				activities.setSubject(actividad.getSubject())
				activities.setType("LLamada")

				expediente.getActivities().add(activities)
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

				expediente.getDocuments().add(document)
			}
		}

		return expediente
	}

	def rellenaDatosSalidaSiniestro(expedientePoliza) {

		List<Interviniente> listaIntervinientes = new ArrayList<Interviniente>()
		List<Pago> listaPagos = new ArrayList<Pago>()
		List<Provision> listaProvisiones = new ArrayList<Provision>()

		/**CREAMOS CANDIDATO
		 *
		 */

		Interviniente candidato = new Interviniente()

		candidato.setCodigoST(util.devolverDatos(expedientePoliza.getCandidato().getCodigoST()))
		candidato.setTipoInterviniente(util.obtenerTipoInterviniente("ASEGURADO"))
		candidato.setGenero(util.obtenerValorSexo(expedientePoliza.getCandidato().getSexo()))
		candidato.setTipoDocumento(util.obtenerTipoDocumento(expedientePoliza.getCandidato().getTipoDocumento()))
		candidato.setNumeroDocumento(util.devolverDatos(expedientePoliza.getCandidato().getNumeroDocumento()))
		candidato.setNombre(util.devolverDatos(expedientePoliza.getCandidato().getNombre()))
		candidato.setApellidos(util.devolverDatos(expedientePoliza.getCandidato().getApellidos()))
		candidato.setNumeroMutualista(util.devolverDatos(expedientePoliza.getCandidato().getInterviniente()))
		candidato.setNumeroCuenta(util.devolverDatos(expedientePoliza.getCandidato().getNumCuenta()))
		candidato.setIban(util.devolverDatos(expedientePoliza.getCandidato().getIban()))
		candidato.setTipoTelefono1(util.obtenerValorTelefono(expedientePoliza.getCandidato().getTipoTelefono1()))
		candidato.setTelefono1(util.devolverDatos(expedientePoliza.getCandidato().getTelefono1()))
		candidato.setTipoTelefono2(util.obtenerValorTelefono(expedientePoliza.getCandidato().getTipoTelefono2()))
		candidato.setTelefono2(util.devolverDatos(expedientePoliza.getCandidato().getTelefono2()))
		candidato.setEmail(util.devolverDatos(expedientePoliza.getCandidato().getCorreo1()))
		candidato.setDireccion(util.devolverDatos(expedientePoliza.getCandidato().getDireccion()))
		candidato.setLocalidad(util.devolverDatos(expedientePoliza.getCandidato().getLocalidad()))
		candidato.setProvincia(util.devolverDatos(expedientePoliza.getCandidato().getProvincia()))
		candidato.setCodigoPostal(util.devolverDatos(expedientePoliza.getCandidato().getCodigoPostal()))
		candidato.setPais(util.devolverDatos(expedientePoliza.getCandidato().getPais()))
		candidato.setFechaNacimiento(util.fromStringToXmlCalendar(expedientePoliza.getCandidato().getFechaNacimiento()))

		listaIntervinientes.add(candidato)

		/**CREAMOS TOMADOR SI LO HAY
		 *
		 */

		if (expedientePoliza.getTomador() != null){

			Interviniente tomador = new Interviniente()

			tomador.setCodigoST(util.devolverDatos(expedientePoliza.getTomador().getCodigoST()))
			tomador.setTipoInterviniente(util.obtenerTipoInterviniente("TOMADOR"))
			tomador.setGenero(util.obtenerValorSexo(expedientePoliza.getTomador().getSexo()))
			tomador.setTipoDocumento(util.obtenerTipoDocumento(expedientePoliza.getTomador().getTipoDocumento()))
			tomador.setNumeroDocumento(util.devolverDatos(expedientePoliza.getTomador().getNumeroDocumento()))
			tomador.setNombre(util.devolverDatos(expedientePoliza.getTomador().getNombre()))
			tomador.setApellidos(util.devolverDatos(expedientePoliza.getTomador().getApellidos()))
			tomador.setNumeroMutualista(util.devolverDatos(expedientePoliza.getTomador().getInterviniente()))
			tomador.setNumeroCuenta(util.devolverDatos(expedientePoliza.getTomador().getNumCuenta()))
			tomador.setIban(util.devolverDatos(expedientePoliza.getTomador().getIban()))
			tomador.setTipoTelefono1(util.obtenerValorTelefono(expedientePoliza.getTomador().getTipoTelefono1()))
			tomador.setTelefono1(util.devolverDatos(expedientePoliza.getTomador().getTelefono1()))
			tomador.setTipoTelefono2(util.obtenerValorTelefono(expedientePoliza.getTomador().getTipoTelefono2()))
			tomador.setTelefono2(util.devolverDatos(expedientePoliza.getTomador().getTelefono2()))
			tomador.setEmail(util.devolverDatos(expedientePoliza.getTomador().getCorreo1()))
			tomador.setDireccion(util.devolverDatos(expedientePoliza.getTomador().getDireccion()))
			tomador.setLocalidad(util.devolverDatos(expedientePoliza.getTomador().getLocalidad()))
			tomador.setProvincia(util.devolverDatos(expedientePoliza.getTomador().getProvincia()))
			tomador.setCodigoPostal(util.devolverDatos(expedientePoliza.getTomador().getCodigoPostal()))
			tomador.setPais(util.devolverDatos(expedientePoliza.getTomador().getPais()))
			tomador.setFechaNacimiento(util.fromStringToXmlCalendar(expedientePoliza.getTomador().getFechaNacimiento()))

			listaIntervinientes.add(tomador)

			/**AUNQUE EL TOMADOR Y EL ASEGURADO SEAN EL MISMO HAY QUE MANDARLO
			 * 
			 */
		} else {

			Interviniente asegurado = new Interviniente()

			asegurado.setCodigoST(util.devolverDatos(expedientePoliza.getCandidato().getCodigoST()))
			asegurado.setTipoInterviniente(util.obtenerTipoInterviniente("TOMADOR"))
			asegurado.setGenero(util.obtenerValorSexo(expedientePoliza.getCandidato().getSexo()))
			asegurado.setTipoDocumento(util.obtenerTipoDocumento(expedientePoliza.getCandidato().getTipoDocumento()))
			asegurado.setNumeroDocumento(util.devolverDatos(expedientePoliza.getCandidato().getNumeroDocumento()))
			asegurado.setNombre(util.devolverDatos(expedientePoliza.getCandidato().getNombre()))
			asegurado.setApellidos(util.devolverDatos(expedientePoliza.getCandidato().getApellidos()))
			asegurado.setNumeroMutualista(util.devolverDatos(expedientePoliza.getCandidato().getInterviniente()))
			asegurado.setNumeroCuenta(util.devolverDatos(expedientePoliza.getCandidato().getNumCuenta()))
			asegurado.setIban(util.devolverDatos(expedientePoliza.getCandidato().getIban()))
			asegurado.setTipoTelefono1(util.obtenerValorTelefono(expedientePoliza.getCandidato().getTipoTelefono1()))
			asegurado.setTelefono1(util.devolverDatos(expedientePoliza.getCandidato().getTelefono1()))
			asegurado.setTipoTelefono2(util.obtenerValorTelefono(expedientePoliza.getCandidato().getTipoTelefono2()))
			asegurado.setTelefono2(util.devolverDatos(expedientePoliza.getCandidato().getTelefono2()))
			asegurado.setEmail(util.devolverDatos(expedientePoliza.getCandidato().getCorreo1()))
			asegurado.setDireccion(util.devolverDatos(expedientePoliza.getCandidato().getDireccion()))
			asegurado.setLocalidad(util.devolverDatos(expedientePoliza.getCandidato().getLocalidad()))
			asegurado.setProvincia(util.devolverDatos(expedientePoliza.getCandidato().getProvincia()))
			asegurado.setCodigoPostal(util.devolverDatos(expedientePoliza.getCandidato().getCodigoPostal()))
			asegurado.setPais(util.devolverDatos(expedientePoliza.getCandidato().getPais()))
			asegurado.setFechaNacimiento(util.fromStringToXmlCalendar(expedientePoliza.getCandidato().getFechaNacimiento()))

			listaIntervinientes.add(asegurado)
		}


		/**RECORREMOS EXPEDIENTE
		 *
		 */
		Expediente expediente = new Expediente()

		expediente.setCodigoST(util.devolverDatos(expedientePoliza.getCodigoST()))
		expediente.setEstado(util.obtenerValorEstadoExpediente(expedientePoliza.getCodigoEstado().toString()))
		expediente.setFechaDeEstado(util.fromStringToXmlCalendar(expedientePoliza.getFechaUltimoCambioEstado()))
		expediente.setMotivoEstado(util.obtenerValorTipoMotivoEstadoExpediente(expedientePoliza.getTipoMotivoEstadoExpediente().toString()))
		expediente.setSituacionPoliza(util.obtenerValorSituacionPoliza(expedientePoliza.getSituacionpoliza().toString()))
		expediente.setIdOperacionConsultaPoliza(util.devolverDatos(expedientePoliza.getNumSolicitud().toString()))
		expediente.setNumeroPoliza(util.devolverDatos(expedientePoliza.getNumPoliza()))
		expediente.setNumeroSuplemento(util.devolverDatos(expedientePoliza.getNumSuplemento().toString()))
		expediente.setNumeroCertificado(util.devolverDatos(expedientePoliza.getNumCertificado()))
		expediente.setFechaAltaPoliza(util.fromStringToXmlCalendar(expedientePoliza.getFechaApertura()))
		expediente.setFechaSolicitudPoliza(util.fromStringToXmlCalendar(expedientePoliza.getFechaSolicitud()))
		expediente.setProducto(util.devolverDatos(expedientePoliza.getProducto().getCodigoProductoCompanya()))

		/**ASOCIAMOS INTERVINIENTES EN EL EXPEDIENTE
		 *
		 */

		expediente.interviniente = listaIntervinientes

		/**RECORREMOS SINIESTRO
		 *
		 */
		Siniestro siniestro = new Siniestro()

		if (expedientePoliza.getListaSiniestros() != null && expedientePoliza.getListaSiniestros().size() > 0) {

			siniestro.setCodigoST(util.devolverDatos(expedientePoliza.getListaSiniestros().get(0).getCodigoSt()))
			siniestro.setNumeroSiniestro(util.devolverDatos(expedientePoliza.getListaSiniestros().get(0).getSiniestroNN()))
			siniestro.setCausa(util.obtenerValorCausaAccidente(expedientePoliza.getListaSiniestros().get(0).getTipoCausaAccidente().toString()))
			siniestro.setCoberturaSiniestrada(util.obtenerValorDano(expedientePoliza.getListaSiniestros().get(0).getTipoDano().toString()))
			siniestro.setLimitacionFuncional(expedientePoliza.getListaSiniestros().get(0).getCodigoCie() != null?util.devolverDatos(expedientePoliza.getListaSiniestros().get(0).getCodigoCie().getCodigoCie().toString()):util.devolverDatos(null))
			siniestro.setFechaOcurrencia(util.fromStringToXmlCalendar(expedientePoliza.getListaSiniestros().get(0).getFechaOcurrencia()))
			siniestro.setFechaDeclaracion(util.fromStringToXmlCalendar(expedientePoliza.getListaSiniestros().get(0).getFechaDeclaracion()))
			siniestro.setRentaCapital(util.devolverDatosDecimal(expedientePoliza.getListaSiniestros().get(0).getYsi()))
			siniestro.setDescripcionSiniestro(util.devolverDatos(expedientePoliza.getListaSiniestros().get(0).getDescripcion()))
			siniestro.setTextoBeneficiarios(util.devolverDatos(expedientePoliza.getListaSiniestros().get(0).getTextoBeneficiarios()))
			siniestro.setFechaBajaMedica(util.fromStringToXmlCalendar(expedientePoliza.getListaSiniestros().get(0).getFechaBaja()))
			siniestro.setFechaAltaMedica(util.fromStringToXmlCalendar(expedientePoliza.getListaSiniestros().get(0).getFechaAltaMedica()))
			siniestro.setFechaInicioDerecho(util.fromStringToXmlCalendar(expedientePoliza.getListaSiniestros().get(0).getFechaInicioDerechos()))
			siniestro.setDiasBajaEstimados(expedientePoliza.getListaSiniestros().get(0).getCodigoCie() != null?new BigDecimal(expedientePoliza.getListaSiniestros().get(0).getCodigoCie().getDiasBaja()):new BigDecimal(0))
			siniestro.setOficinaGestora(util.devolverDatos(expedientePoliza.getSucursal().getCodigoBancarioOficina()))

			/**RECORREMOS PAGOS
			 *
			 */
			expedientePoliza.getListaSiniestros().get(0).getPagos().each { pagoPoliza ->

				Pago pago = new Pago()

				pago.setCodigoST(util.devolverDatos(pagoPoliza.getCodigoST()))
				pago.setSubtipoPago(util.obtenerSubtipoPago(pagoPoliza.getTipoSubPago().toString()))
				pago.setMetodoPago(util.obtenerMetodoPago(pagoPoliza.getTipoMetodoPago().toString()))
				pago.setTipoPerceptor(util.obtenerTipoPerceptor(pagoPoliza.getTipoPerceptor().toString()))
				pago.setTipoImpuesto(util.obtenerTipoIva(pagoPoliza.getTipoIva()))

				if (pago.getTipoPerceptor() != null){

					if (pago.getTipoPerceptor() == util.obtenerTipoPerceptor(TipoPerceptor.ASEGURADO)){
						pago.setIntervinienteBeneficiario(candidato)
					}

					else if (pago.getTipoPerceptor() == util.obtenerTipoPerceptor(TipoPerceptor.BENEFICIARIO)){

						Interviniente beneficiario = new Interviniente()

						beneficiario.setCodigoST(util.devolverDatos(pagoPoliza.getBeneficiario().getCodigoST()))
						beneficiario.setTipoInterviniente(util.obtenerTipoInterviniente("BENEFICIARIO"))
						beneficiario.setGenero(util.obtenerValorSexo(pagoPoliza.getBeneficiario().getSexo()))
						beneficiario.setTipoDocumento(util.obtenerTipoDocumento(pagoPoliza.getBeneficiario().getTipoDocumento()))
						beneficiario.setNumeroDocumento(util.devolverDatos(pagoPoliza.getBeneficiario().getNumeroDocumento()))
						beneficiario.setNombre(util.devolverDatos(pagoPoliza.getBeneficiario().getNombre()))
						beneficiario.setApellidos(util.devolverDatos(pagoPoliza.getBeneficiario().getApellidos()))
						beneficiario.setNumeroMutualista(util.devolverDatos(expedientePoliza.getBeneficiario().getInterviniente()))
						beneficiario.setNumeroCuenta(util.devolverDatos(pagoPoliza.getBeneficiario().getNumCuenta()))
						beneficiario.setIban(util.devolverDatos(pagoPoliza.getBeneficiario().getIban()))
						beneficiario.setTipoTelefono1(util.obtenerValorTelefono(pagoPoliza.getBeneficiario().getTipoTelefono1()))
						beneficiario.setTelefono1(util.devolverDatos(pagoPoliza.getBeneficiario().getTelefono1()))
						beneficiario.setTipoTelefono2(util.obtenerValorTelefono(pagoPoliza.getBeneficiario().getTipoTelefono2()))
						beneficiario.setTelefono2(util.devolverDatos(pagoPoliza.getBeneficiario().getTelefono2()))
						beneficiario.setEmail(util.devolverDatos(pagoPoliza.getBeneficiario().getCorreo1()))
						beneficiario.setDireccion(util.devolverDatos(pagoPoliza.getBeneficiario().getDireccion()))
						beneficiario.setLocalidad(util.devolverDatos(pagoPoliza.getBeneficiario().getLocalidad()))
						beneficiario.setProvincia(util.devolverDatos(pagoPoliza.getBeneficiario().getProvincia()))
						beneficiario.setCodigoPostal(util.devolverDatos(pagoPoliza.getBeneficiario().getCodigoPostal()))
						beneficiario.setPais(util.devolverDatos(pagoPoliza.getBeneficiario().getPais()))
						beneficiario.setFechaNacimiento(util.fromStringToXmlCalendar(pagoPoliza.getBeneficiario().getFechaNacimiento()))

						pago.setIntervinienteBeneficiario(beneficiario)
					} else if (pago.getTipoPerceptor() == util.obtenerTipoPerceptor(TipoPerceptor.COLABORADOR)){

						Interviniente colaborador = new Interviniente()

						colaborador.setCodigoST("10033")
						colaborador.setTipoInterviniente(util.obtenerTipoInterviniente("COLABORADOR"))
						colaborador.setGenero(util.obtenerValorSexo("HOMBRE"))
						colaborador.setTipoDocumento(util.obtenerTipoDocumento("CIF"))
						colaborador.setNumeroDocumento("B85555696")
						colaborador.setNombre("Scor Telemed,  SLU")
						colaborador.setApellidos("")
						colaborador.setNumeroMutualista("0")
						colaborador.setNumeroCuenta("9300491804152710414077")
						colaborador.setIban("ES9300491804152710414077")
						colaborador.setTipoTelefono1(util.obtenerValorTelefono("FIJO"))
						colaborador.setTelefono1("911420700")
						colaborador.setTipoTelefono2("")
						colaborador.setTelefono2("")
						colaborador.setEmail("")
						colaborador.setDireccion("Pso. Castellana, 135 - 13\u00baA")
						colaborador.setLocalidad("Madrid")
						colaborador.setProvincia("Madrid")
						colaborador.setCodigoPostal("28046")
						colaborador.setPais("Espa\u00f1a")
						colaborador.setFechaNacimiento(null)

						pago.setIntervinienteBeneficiario(colaborador)
					}
				} else {

					pago.setIntervinienteBeneficiario(new Interviniente())
				}
				pago.setFechaEmision(util.fromStringToXmlCalendar(pagoPoliza.getFechaEmision()))
				pago.setFechaInicioPago(util.fromStringToXmlCalendar(pagoPoliza.getFechaInicio()))
				pago.setNumeroFactura(util.devolverDatos(pagoPoliza.getNumFactura()))
				pago.setFechaFactura(util.fromStringToXmlCalendar(pagoPoliza.getFechaFactura()))
				pago.setImporteBruto(util.devolverDatosDecimal(pagoPoliza.getImporteBruto()))
				pago.setImporteNeto(util.devolverDatosDecimal(pagoPoliza.getImporte()))
				pago.setTipoPago(util.obtenerTipoPago(pagoPoliza.getTipoPago().toString()))

				listaPagos.add(pago)
			}

			siniestro.pago = listaPagos

			/**RECORREMOS PROVISIONES
			 *
			 */
			expedientePoliza.getListaSiniestros().get(0).getProvisiones().each { provisionPoliza ->

				Provision provision = new Provision()

				provision.setIdProvision(util.devolverDatos(provisionPoliza.getIdProvision().replace("{", "").replace("}", "")))
				provision.setTipoProvision(util.obtenerValorTipoProvision(provisionPoliza.getTipoProvision()))
				provision.setSubcodigoReserva(util.obtenerValorTipoReserva(provisionPoliza.getTipoReserva().toString()))
				provision.setFechaProvision(util.fromStringToXmlCalendar(provisionPoliza.getFechaProvision()))
				provision.setImporte(util.devolverDatosDecimal(provisionPoliza.getImporte()))

				listaProvisiones.add(provision)
			}

			siniestro.provision = listaProvisiones


			/**ASOCIAMOS SINIESTRO AL EXPEDIENTE
			 *
			 */
			expediente.setSiniestro(siniestro)

			return expediente
		}
	}

	private def rellenaDatos (req, codigoCia) {

		def mapDatos = [:]
		def listadoPreguntas = []
		def formato = new SimpleDateFormat("yyyyMMdd")
		def apellido
		def telefono1
		def telefono2
		def telefonoMovil
		def eUWReferenceCode

		REGISTRODATOS datos = new REGISTRODATOS()

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
					if (eElement.getElementsByTagName("productCode").item(0) != null) {

						if (eElement.getElementsByTagName("productCode").item(0).getTextContent().toString().equals("0013")){

							datos.codigoProducto = "P49"
						} else {

							datos.codigoProducto = eElement.getElementsByTagName("productCode").item(0).getTextContent()
						}
					}
					/**CODIGO DE AGENTE
					 *
					 */

					if (eElement.getElementsByTagName("agency").item(0) != null) {
						datos.codigoAgencia = eElement.getElementsByTagName("agency").item(0).getTextContent()
					}

					/**TELEFONO DE AGENTE
					 *
					 */

					if (eElement.getElementsByTagName("agentPhone").item(0) != null) {
						datos.telefonoAgente = eElement.getElementsByTagName("agentPhone").item(0).getTextContent()
					}

					/**NOMBRE DE AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("agent").item(0) != null) {
						datos.nomApellAgente = eElement.getElementsByTagName("agent").item(0).getTextContent()
					}

					/**EMAIL AGENTE
					 *
					 */
					if (eElement.getElementsByTagName("agentEmail").item(0) != null) {
						datos.emailAgente = eElement.getElementsByTagName("agentEmail").item(0).getTextContent()
					}

					/**NOMBRE DE CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("name").item(0) != null) {
						datos.nombreCliente = eElement.getElementsByTagName("name").item(0).getTextContent()
					}

					/**APELLIDO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("surname").item(0) != null) {
						apellido = eElement.getElementsByTagName("surname").item(0).getTextContent()
					}

					datos.apellidosCliente = apellido

					/**DNI CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) != null) {
						datos.dni = eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent()
					}

					/**SEXO CANDIDATO
					 *
					 */

					if (eElement.getElementsByTagName("gender").item(0) != null) {
						datos.sexo = eElement.getElementsByTagName("gender").item(0).getTextContent()=="1"?"V":"M"
					}

					/**EDAD ACTUARIAL
					 *
					 */
					if (eElement.getElementsByTagName("actuarialAge").item(0) != null && !eElement.getElementsByTagName("actuarialAge").item(0).getTextContent().isEmpty()) {
						datos.edadActuarial = Integer.parseInt(eElement.getElementsByTagName("actuarialAge").item(0).getTextContent())
					}

					/**DIRECCION CLIENTE
					 *
					 */

					if (eElement.getElementsByTagName("address").item(0) != null) {
						datos.direccionCliente = eElement.getElementsByTagName("address").item(0).getTextContent()
					} else {
						datos.direccionCliente = "."
					}
					/**CODIGO POSTAL CLIENTE
					 *
					 */

					if (eElement.getElementsByTagName("postalCode").item(0) != null && !eElement.getElementsByTagName("postalCode").item(0).getTextContent().isEmpty()) {
						datos.codigoPostal = eElement.getElementsByTagName("postalCode").item(0).getTextContent()
					} else {
						datos.codigoPostal = "."
					}

					/**POBLACION
					 *
					 */

					if (eElement.getElementsByTagName("city").item(0) != null && !eElement.getElementsByTagName("city").item(0).getTextContent().isEmpty()) {
						datos.poblacion = eElement.getElementsByTagName("city").item(0).getTextContent()
					} else {
						datos.poblacion = "."
					}

					/**PROVINCIA
					 *
					 */

					if (eElement.getElementsByTagName("province").item(0) != null && !eElement.getElementsByTagName("province").item(0).getTextContent().isEmpty()) {
						datos.provincia = eElement.getElementsByTagName("province").item(0).getTextContent()
					} else {
						datos.provincia = "."
					}

					/**PAIS
					 *
					 */

					if (eElement.getElementsByTagName("country").item(0) != null && !eElement.getElementsByTagName("country").item(0).getTextContent().isEmpty()) {
						datos.pais = eElement.getElementsByTagName("country").item(0).getTextContent()
					} else {
						datos.pais = "ES"
					}


					/**TELEFONOS
					 *
					 */

					if (eElement.getElementsByTagName("phoneNumber1").item(0) != null && eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent().toString().equals("0")) {
						datos.telefono1 = eElement.getElementsByTagName("phoneNumber1").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("phoneNumber2").item(0) != null &&  eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent() != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().toString().equals("0")) {
						datos.telefono2 = eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent()
					}

					if (eElement.getElementsByTagName("mobileNumber").item(0) != null && eElement.getElementsByTagName("mobileNumber").item(0).getTextContent() != null && !eElement.getElementsByTagName("mobileNumber").item(0).getTextContent().toString().equals(0) && eElement.getElementsByTagName("phoneNumber2").item(0) != null && !eElement.getElementsByTagName("phoneNumber2").item(0).getTextContent().toString().equals("0")) {
						datos.telefono3 = eElement.getElementsByTagName("mobileNumber").item(0).getTextContent()
					}


					if (datos.telefono1 == null || datos.telefono1.isEmpty()){
						if (datos.telefono3 != null && !datos.telefono3.isEmpty()){
							datos.telefono1 = datos.telefono3
						} else if (datos.telefono2 != null && !datos.telefono2.isEmpty()){
							datos.telefono1 = datos.telefono2
						} else {
							datos.telefono1 = "999999999"
						}
					}

					/**CODIGO CIA
					 *
					 */

					datos.codigoCia = codigoCia

					/**FECHA DE NACIMIENTO
					 *
					 */

					if (eElement.getElementsByTagName("birthDate").item(0) != null && eElement.getElementsByTagName("birthDate").item(0).getTextContent() != null && !eElement.getElementsByTagName("birthDate").item(0).getTextContent().isEmpty()) {
						datos.fechaNacimiento = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("birthDate").item(0).getTextContent()).toGregorianCalendar().getTime())
					}

					/**ESTADO CIVIL
					 *
					 */

					if (eElement.getElementsByTagName("civilState").item(0) != null) {
						datos.estadoCivil = eElement.getElementsByTagName("civilState").item(0).getTextContent()
					}

					/**EMAIL
					 *
					 */

					if (eElement.getElementsByTagName("email").item(0) != null) {
						datos.email = eElement.getElementsByTagName("email").item(0).getTextContent()
					}

					/**CLAVE DE VALIDACION
					 *
					 */
					if (eElement.getElementsByTagName("password").item(0) != null) {
						datos.claveValidacionCliente = eElement.getElementsByTagName("password").item(0).getTextContent()
					}

					/**POLIZA
					 *
					 */

					if (eElement.getElementsByTagName("policyNumber").item(0) != null) {
						datos.numPoliza = eElement.getElementsByTagName("policyNumber").item(0).getTextContent()
					}

					/**CERTIFICADO
					 *
					 */

					if (eElement.getElementsByTagName("certificateNumber").item(0) != null) {
						datos.numCertificado = eElement.getElementsByTagName("certificateNumber").item(0).getTextContent()
					}

					/**COMENTS
					 *
					 */

					if (eElement.getElementsByTagName("comments").item(0) != null) {
						datos.observaciones = eElement.getElementsByTagName("comments").item(0).getTextContent()
					}

					/**FECHA DE SOLICITUD
					 *
					 */

					if (eElement.getElementsByTagName("requestDate").item(0) != null && eElement.getElementsByTagName("requestDate").item(0).getTextContent() != null && !eElement.getElementsByTagName("requestDate").item(0).getTextContent().isEmpty()) {
						datos.fechaEnvio = formato.format(util.fromStringToXmlCalendar(eElement.getElementsByTagName("requestDate").item(0).getTextContent()).toGregorianCalendar().getTime())
					}

					/**NUMERO DE REFERENCIA
					 *
					 */

					if (eElement.getElementsByTagName("requestNumber").item(0) != null) {
						datos.numSolicitud = eElement.getElementsByTagName("requestNumber").item(0).getTextContent()
					}

					/**FRANJA HORARIA
					 *
					 */
					if (eElement.getElementsByTagName("contactTime").item(0) != null) {
						datos.franjaHoraria =  eElement.getElementsByTagName("contactTime").item(0).getTextContent()
					} else {
						datos.franjaHoraria = ""
					}

					if (eElement.getElementsByTagName("eUWReferenceCode").item(0) != null) {
						eUWReferenceCode = eElement.getElementsByTagName("eUWReferenceCode").item(0).getTextContent()
					}
				}
			}

			setCamposGenericos (datos, eUWReferenceCode)

			return datos
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaDatos", ExceptionUtils.composeMessage(null, e))
		}
	}


	private void setCamposGenericos(REGISTRODATOS datos, eUWReferenceCode) {

		datos.lugarNacimiento = ""
		datos.emailAgente = ""
		datos.tipoCliente = "N"
		datos.codigoCuestionario = ""
		datos.campo1 = "es"
		datos.campo2 = ""
		datos.campo3 = "ES"
		datos.campo4 = eUWReferenceCode
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

	private def rellenaServicios (req) {

		def listadoServicios = []

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
			DocumentBuilder builder = factory.newDocumentBuilder()

			InputSource is = new InputSource(new StringReader(req.request))
			is.setEncoding("UTF-8")
			Document doc = builder.parse(is)

			doc.getDocumentElement().normalize()

			NodeList nList = doc.getElementsByTagName("services")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode

					if (eElement.getElementsByTagName("serviceType").item(0) != null) {

						DATOS.Servicio servicio = new DATOS.Servicio()
						servicio.tipoServicios = eElement.getElementsByTagName("serviceType").item(0)
						servicio.descripcionServicio = eElement.getElementsByTagName("serviceDescription").item(0)
						servicio.filler = ""
						servicio.codigoServicio = eElement.getElementsByTagName("serviceCode").item(0)

						listadoServicios.add(servicio)
					}
				}
			}

			return listadoServicios
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaServicios", ExceptionUtils.composeMessage(null, e))
		}
	}

	private def rellenaCoberturas (req) {

		def listadoCoberturas = []

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

					/**COBERTURAS
					 *
					 */
					cobertura.filler = ""

					cobertura.codigoCobertura = eElement.getElementsByTagName("benefictCode").item(0).getTextContent()

					if (eElement.getElementsByTagName("benefictName").item(0) != null) {
						cobertura.nombreCobertura = eElement.getElementsByTagName("benefictName").item(0).getTextContent()
					}
					cobertura.capital = Float.parseFloat(eElement.getElementsByTagName("benefictCapital").item(0).getTextContent())
					listadoCoberturas.add(cobertura)
				}
			}

			return listadoCoberturas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaCOberturas", ExceptionUtils.composeMessage(null, e))
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

			nList = doc.getElementsByTagName("CandidateInformation")

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp)

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode
					if (eElement.getElementsByTagName("rentaDiaria").item(0) != null) {
						/**NUMERO DE PRODUCTO
						 *
						 */
						DATOS.Pregunta pregunta = new DATOS.Pregunta()
						pregunta.codigoPregunta = "renta"
						pregunta.tipoDatos = "STRING"
						pregunta.respuesta = eElement.getElementsByTagName("rentaDiaria").item(0).getTextContent()
						listadoPreguntas.add(pregunta)
					}
				}
			}

			return listadoPreguntas
		} catch (Exception e) {
			throw new WSException(this.getClass(), "rellenaPreguntas", ExceptionUtils.composeMessage(null, e))
		}
	}

	/**METODO QUE VALIDA QUE LOS CAMPOS NECESARIOS PARA EL ENVIO A AMA ESTAN RELLENOS. CAMPOS QUE TIENEN QUE ESTAR RELLENOS
	 * 
	 * 
	 * FECHA_OCURRENCIA
	 * FEHCA_DECLARACION
	 * SITUACION_POILIZA
	 * NUMERO_CUENTA
	 * CAUSA
	 * NUMERO_SUPLEMENTO
	 * ID_OPERACION
	 * CAUSA
	 * 
	 * 
	 * 
	 * @param expediente
	 * @return
	 */
	boolean expedienteGestionado(servicios.Expediente expediente, CorreoUtil correoUtil, String opername){

		String codError = null

		//		return expediente != null && expediente.getSituacionPoliza() != null && expediente.getNumSuplemento() != null &&
		//				!expediente.getNumSuplemento().isEmpty() &&	expediente.getNumSolicitud() != null && !expediente.getNumSolicitud().isEmpty() && expediente.getSucursal() != null &&
		//				expediente.getSucursal().getCodigoBancarioOficina() != null && !expediente.getSucursal().getCodigoBancarioOficina().isEmpty() && expediente.getListaSiniestros() != null &&
		//				expediente.getListaSiniestros().get(0).getFechaOcurrencia() != null && !expediente.getListaSiniestros().get(0).getFechaOcurrencia().isEmpty() &&
		//				expediente.getListaSiniestros().get(0).getFechaDeclaracion() != null && !expediente.getListaSiniestros().get(0).getFechaDeclaracion().isEmpty() &&
		//				expediente.getListaSiniestros().get(0).getTipoCausaAccidente() != null


		if (expediente == null) {
			codError = "El expediente es nulo"
		}

		if (expediente.getSituacionPoliza() == null) {
			codError += ". El expediente no tiene la situaci�n de la p�liza"
		}

		if (expediente.getNumSuplemento() == null || expediente.getNumSuplemento().isEmpty()) {
			codError += ". El expediente no tiene umero de suplemento"
		}

		if (expediente.getNumSolicitud() == null || expediente.getNumSolicitud().isEmpty()) {
			codError += ". El expediente no tiene numero de solicitud"
		}

		if (expediente.getSucursal() == null) {
			codError += ". El expediente no tiene scucursal"
		}

		if (expediente.getSucursal().getCodigoBancarioOficina() == null || expediente.getSucursal().getCodigoBancarioOficina().isEmpty()) {
			codError += ". El expediente no tiene codigo de oficina"
		}

		if (expediente.getListaSiniestros() == null || expediente.getListaSiniestros().size() == 0){
			codError += ". El expediente no tiene siniestros"
		}

		if (expediente.getListaSiniestros().get(0).getFechaOcurrencia() == null || expediente.getListaSiniestros().get(0).getFechaOcurrencia().isEmpty()) {
			codError +=  ". El siniestro no tiene fecha de ocurrencia"
		}

		if (expediente.getListaSiniestros().get(0).getFechaDeclaracion() == null || expediente.getListaSiniestros().get(0).getFechaDeclaracion().isEmpty()){
			codError += ". El siniestro no tiene fecha de declaracion"
		}

		if (expediente.getListaSiniestros().get(0).getTipoCausaAccidente() == null) {
			codError += ". El siniestro no tiene tipo\\causa accidente"
		}

		if (codError == null) {

			return true

		} else {
			logginService.putInfoMessage("Expediente: " + expediente.getCodigoST() + " tiene los siguientes erres: " + codError + ". Esto esta impidiendo a AMA ver la gesti�n del siniestro.")
			correoUtil.envioEmail(opername, "Expediente: " + expediente.getCodigoST() + " tiene los siguientes erres: " + codError + ". Esto esta impidiendo a AMA ver la gesti�n del siniestro", 1)

			return false
		}

	}


	/**METODO QUE VALIDA CONDICIONES DE AMA PARA SU VISUALIZACI�N
	 * 
	 * El pago a Colaborador siempre deber� venir con tipo de impuesto.
	 * En caso de pago al asegurado siempre se deber� proporcionar el n�mero de cuenta/iban del mismo. 
	 *
	 * 
	 * @param expediente
	 * @return
	 */
	boolean cumpleRequisitosVisualizacion(servicios.Expediente expediente, CorreoUtil correoUtil, String opername){

		if (expediente != null && expediente.getListaSiniestros() != null && expediente.getListaSiniestros().size() > 0 && tienePago(expediente, correoUtil, opername)){
			return !existePagoColaboradorSinTipoImpuesto(expediente.getListaSiniestros().get(0).getPagos(), correoUtil, opername, expediente) && !existePagoAseguradoSinNumeroCuenta(expediente.getListaSiniestros().get(0).getPagos(), expediente, correoUtil, opername)
		}

		return true
	}

	boolean tienePago(servicios.Expediente expediente, CorreoUtil correoUtil, String opername){

		if (expediente.getListaSiniestros().get(0).getPagos() != null && expediente.getListaSiniestros().get(0).getPagos().size() > 0) {
			logginService.putInfoMessage("Expediente: " + expediente.getCodigoST() + " no tiene pago. Esto esta impidiendo a AMA ver la gesti�n del siniestro.")
			correoUtil.envioEmail(opername, "Expediente: " + expediente.getCodigoST() + " no tiene pago. Esto esta impidiendo a AMA ver la gesti�n del siniestro", 1)
			return true
		}

		return false

	}

	boolean existePagoColaboradorSinTipoImpuesto(List<servicios.Pago> pagos, CorreoUtil correoUtil, String opername, servicios.Expediente expediente){

		if (pagos != null && pagos.size() > 0){
			for (int i = 0; i < pagos.size(); i++){
				if (pagos.get(i).getTipoPerceptor() == TipoPerceptor.COLABORADOR && pagos.get(i).getTipoIva() == null){
					logginService.putInfoMessage("Expediente: " + expediente.getCodigoST() + " tiene pago a colaborador sin tipo de impuesto. Esto esta impidiendo a AMA ver la gesti�n del siniestro.")
					correoUtil.envioEmail(opername, "Expediente: " + expediente.getCodigoST() + " tiene pago a colaborador sin tipo de impuesto. Esto esta impidiendo a AMA ver la gesti�n del siniestro", 1)
					return true
				}
			}
		}

		return false

	}

	boolean existePagoAseguradoSinNumeroCuenta(List<servicios.Pago> pagos, servicios.Expediente expediente, CorreoUtil correoUtil, String opername){


		if (pagos != null && pagos.size() > 0){
			for (int i = 0; i < pagos.size(); i++){
				if (pagos.get(i).getTipoPerceptor() == TipoPerceptor.ASEGURADO && expediente.getCandidato().getNumCuenta() == null){
					logginService.putInfoMessage("Expediente: " + expediente.getCodigoST() + " tiene pago a asegurado sin n�mero de cuenta asociado. Esto esta impidiendo a AMA ver la gesti�n del siniestro.")
					correoUtil.envioEmail(opername, "Expediente: " + expediente.getCodigoST() + " tiene pago a asegurado sin n�mero de cuenta asociado. Esto esta impidiendo a AMA ver la gesti�n del siniestro", 1)
					return true
				}
			}
		}

		return false
	}

	def devolverEstadoCivil(estado){

		switch(estado){
			case "SOLTERO" : return CivilStateType.S
			case "CASADO" : return CivilStateType.C
			case "DIVORCIADO" : return CivilStateType.D
			case "SEPARADO" : return CivilStateType.S
			case "PAREJA_DE_HECHO" : return CivilStateType.P
			case "VIUDO" : return CivilStateType.V
			default: return null
		}
	}

	def devolverNombreCobertura(codigo){

		if (codigo.equals("COB5")) {
			return BenefictNameType.FALLECIMIENTO
		} else if (codigo.equals("COB262")) {
			return BenefictNameType.INCAPACIDAD_PERMANENTE_ABSOLUTA_IPA
		} else if (codigo.equals("COB254")) {
			return BenefictNameType.INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL
		} else if (codigo.equals("COB259")) {
			return BenefictNameType.GRAN_INVALIDEZ
		} else if (codigo.equals("COB265")) {
			return BenefictNameType.INCAPACIDAD_TEMPORAL_POR_ACCIDENTE
		} else if (codigo.equals("COB250")) {
			return BenefictNameType.INCAPACIDAD_TEMPORAL_POR_ENFERMEDAD
		} else if (codigo.equals("COB252")) {
			return BenefictNameType.FALLECIMIENTO_POR_ACCIDENTE
		} else if (codigo.equals("COB253")) {
			return BenefictNameType.FALLECIMIENTO_POR_ACCIDENTE_DE_CIRCULACION
		} else if (codigo.equals("COB263")) {
			return BenefictNameType.IPA_POR_ACCIDENTE
		} else if (codigo.equals("COB264")) {
			return BenefictNameType.IPA_POR_ACCIDENTE_DE_CIRCULACION
		} else if (codigo.equals("COB251")) {
			return BenefictNameType.INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL_POR_ACCIDENTE
		} else if (codigo.equals("COB255")) {
			return BenefictNameType.INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL_POR_ACCIDENTE_DE_CIRCULACION
		} else if (codigo.equals("COB260")) {
			return BenefictNameType.GRAN_INVALIDEZ_POR_ACCIDENTE
		} else if (codigo.equals("COB261")) {
			return BenefictNameType.GRAN_INVALIDEZ_POR_ACCIDENTE_DE_CIRCULACION
		} else {
			return null
		}
	}

	def devolverTipoCita(TipoCita cita){

		switch(cita){
			case TipoCita.TELESELECCION : return TipoCitaType.TELESELECCION
			case TipoCita.SERVICIO_CITA : return TipoCitaType.SERVICIO_CITA
			case TipoCita.CENTRO_MEDICO : return TipoCitaType.CENTRO_MEDICO
			default: return null
		}
	}
	Boolean existeDocumentoNodo (RespuestaCRM respuestaCRM, String nodo ) {

		boolean existeDocumento = false

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

		boolean existeDocumento = false

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

	def rellenaDatosSalidaDocumentoNodo(nodoAlfresco, requestDate, logginService, String codigoSt) {

		byte[] compressedData=contentResult.obtenerFichero(nodoAlfresco)

		Documento documento = new Documento()
		documento.setCodigoSt(codigoSt)
		documento.setDocumentacionId(null)
		documento.setNodoAlfresco(nodoAlfresco)
		documento.setContenido(compressedData)


		return documento
	}

	def rellenaDatosSalidaDocumentoId(documentoId, requestDate, logginService, String codigoSt) {

		byte[] compressedData=contentResult.obtenerFichero(documentoId)

		Documento documento = new Documento()
		documento.setCodigoSt(codigoSt)
		documento.setDocumentacionId(documentoId)
		documento.setNodoAlfresco(null)
		documento.setContenido(compressedData)


		return documento
	}

	boolean excluirDelEnvio(BenefitInformation[] benefitInformation){

		for (int i = 0; i < benefitInformation.length; i++) {

			if (benefitInformation[i].getBenefitResultCode().equals("9") || benefitInformation[i].getBenefitResultCode().equals("5")){
				return true
			}
		}

		return false

	}
	
}
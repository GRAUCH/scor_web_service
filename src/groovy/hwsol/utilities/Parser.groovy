package hwsol.utilities


import hwsol.entities.parser.RegistrarEventoSCOR
import hwsol.entities.parser.AlptisGeneralData
import hwsol.entities.parser.ValoracionTeleSeleccionResponse

import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource
import java.text.SimpleDateFormat
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

//AMA
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest

//CASER
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest

//PSN
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest as ConsultaExpedienteRequestPsn
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest as ResultadoReconocimientoMedicoRequestPsn

//Netinsurance
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierRequest

//Methislab
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCasesResultsRequest

//CBP Ita
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest

//MethislabCF
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCasesResultsRequest

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

class Parser {

	/**
	 * Métodos de parseo de AMA
	 */
	ConsultaExpedienteRequest jaxbParserExpedienteAma(String entrada) {
		ConsultaExpedienteRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(ConsultaExpedienteRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<ConsultaExpedienteRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), ConsultaExpedienteRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	ResultadoSiniestroRequest jaxbParserSiniestroAma(String entrada) {
		ResultadoSiniestroRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(ResultadoSiniestroRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<ResultadoSiniestroRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), ResultadoSiniestroRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	/**
	 * Métodos de parseo de CASER
	 */
	ResultadoReconocimientoMedicoRequest jaxbParserResultadoCaser(String entrada) {
		ResultadoReconocimientoMedicoRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(ResultadoReconocimientoMedicoRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<ResultadoReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), ResultadoReconocimientoMedicoRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	RegistrarEventoSCOR registrarEventoSCOR(String salida){

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
		DocumentBuilder builder = factory.newDocumentBuilder()

		InputSource is = new InputSource(new StringReader(salida))
		is.setEncoding("UTF-8")
		Document doc = builder.parse(is)

		doc.getDocumentElement().normalize()

		NodeList nList = doc.getElementsByTagName("service_RegistrarEventoSCOR")

		RegistrarEventoSCOR entradaDetalle = new RegistrarEventoSCOR()

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp)

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode

				if (eElement.getElementsByTagName("idExpediente").item(0) != null) {
					entradaDetalle.setIdExpediente(eElement.getElementsByTagName("idExpediente").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("codigoEvento").item(0) != null) {
					entradaDetalle.setCodigoEvento(eElement.getElementsByTagName("codigoEvento").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("detalle").item(0) != null) {
					entradaDetalle.setDetalle(eElement.getElementsByTagName("detalle").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("fecha").item(0) != null) {
					entradaDetalle.setFechaCierre(eElement.getElementsByTagName("fecha").item(0).getTextContent())
				}
			}
		}
		return entradaDetalle
	}

	/**
	 * Métodos de parseo de PSN
	 */
	ConsultaExpedienteRequestPsn jaxbParserExpedientePsn(String entrada) {
		ConsultaExpedienteRequestPsn salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(ConsultaExpedienteRequestPsn.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<ConsultaExpedienteRequestPsn> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), ConsultaExpedienteRequestPsn.class)
			salida = root.getValue()
		}
		return salida
	}

	ResultadoReconocimientoMedicoRequestPsn jaxbParserResultadoPsn(String entrada) {
		ResultadoReconocimientoMedicoRequestPsn salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(ResultadoReconocimientoMedicoRequestPsn.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<ResultadoReconocimientoMedicoRequestPsn> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), ResultadoReconocimientoMedicoRequestPsn.class)
			salida = root.getValue()
		}
		return salida
	}

	/**
	 * Métodos de parseo de NetInsurance
	 */
	NetinsuranteGetDossierRequest jaxbParserDossierNetInsurance(String entrada) {
		NetinsuranteGetDossierRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(NetinsuranteGetDossierRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<NetinsuranteGetDossierRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), NetinsuranteGetDossierRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	/**
	 * Métodos de parseo de Methislab
	 */
	MethislabUnderwrittingCasesResultsRequest jaxbParserMethislabUnderwritingCases(String entrada) {
		MethislabUnderwrittingCasesResultsRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(MethislabUnderwrittingCasesResultsRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<MethislabUnderwrittingCasesResultsRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), MethislabUnderwrittingCasesResultsRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	/**
	 * Métodos de parseo de Cbp Ita
	 */
	CbpitaUnderwrittingCasesResultsRequest jaxbParserCbpItaUnderwritingCases(String entrada) {
		CbpitaUnderwrittingCasesResultsRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(CbpitaUnderwrittingCasesResultsRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<CbpitaUnderwrittingCasesResultsRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), CbpitaUnderwrittingCasesResultsRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	/**
	 * Métodos de parseo de MethislabCF
	 */
	MethislabCFUnderwrittingCasesResultsRequest jaxbParserMethislabCFUnderwritingCases(String entrada) {
		MethislabCFUnderwrittingCasesResultsRequest salida = null
		if(entrada != null && entrada.length() > 0) {

			JAXBContext jaxbContext = JAXBContext.newInstance(MethislabCFUnderwrittingCasesResultsRequest.class)
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

			StringReader reader = new StringReader(entrada)

			JAXBElement<MethislabCFUnderwrittingCasesResultsRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), MethislabCFUnderwrittingCasesResultsRequest.class)
			salida = root.getValue()
		}
		return salida
	}

	ValoracionTeleSeleccionResponse valoracionTeleSeleccionResponse(String salida){

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
		DocumentBuilder builder = factory.newDocumentBuilder()

		InputSource is = new InputSource(new StringReader(salida))
		is.setEncoding("UTF-8")
		Document doc = builder.parse(is)

		doc.getDocumentElement().normalize()

		NodeList nList = doc.getElementsByTagName("ns2:valoracion")

		ValoracionTeleSeleccionResponse entradaDetalle = new ValoracionTeleSeleccionResponse()

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp)

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				if (eElement.getElementsByTagName("causte").item(0) != null) {
					entradaDetalle.setCausa(eElement.getElementsByTagName("causte").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("tipote").item(0) != null) {
					entradaDetalle.setMotivo(eElement.getElementsByTagName("tipote").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("tlabr").item(0) != null) {
					entradaDetalle.setCorta(eElement.getElementsByTagName("tlabr").item(0).getTextContent().equals("1")?true:false)
				}
				if (eElement.getElementsByTagName("tlcom").item(0) != null) {
					entradaDetalle.setMediana(eElement.getElementsByTagName("tlcom").item(0).getTextContent().equals("1")?true:false)
				}
				if (eElement.getElementsByTagName("wpers").item(0) != null) {
					entradaDetalle.setCodigoCliente(eElement.getElementsByTagName("wpers").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("coder8").item(0) != null) {
					entradaDetalle.setCodigoError(eElement.getElementsByTagName("coder8").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("werrts").item(0) != null) {
					entradaDetalle.setError(eElement.getElementsByTagName("werrts").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("cia").item(0) != null) {
					entradaDetalle.setCia(eElement.getElementsByTagName("cia").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("numref").item(0) != null) {
					entradaDetalle.setNumeroReferencia(eElement.getElementsByTagName("numref").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("yprodu").item(0) != null) {
					entradaDetalle.setProducto(eElement.getElementsByTagName("yprodu").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("yramex").item(0) != null) {
					entradaDetalle.setRamo(eElement.getElementsByTagName("yramex").item(0).getTextContent())
				}

				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

				String dia = null
				String mes = null
				String anio = null

				if (eElement.getElementsByTagName("ddests").item(0) != null) {
					dia = eElement.getElementsByTagName("ddests").item(0).getTextContent()
				}
				if (eElement.getElementsByTagName("mmests").item(0) != null) {
					mes = eElement.getElementsByTagName("mmests").item(0).getTextContent()
				}
				if (eElement.getElementsByTagName("aaests").item(0) != null) {
					anio = eElement.getElementsByTagName("aaests").item(0).getTextContent()
				}

				entradaDetalle.setFechaUltimoCambioEstado(formatter.parse(dia+mes+anio))

				if (eElement.getElementsByTagName("ddgets").item(0) != null) {
					dia = eElement.getElementsByTagName("ddgets").item(0).getTextContent()
				}
				if (eElement.getElementsByTagName("mmgets").item(0) != null) {
					mes = eElement.getElementsByTagName("mmgets").item(0).getTextContent()
				}
				if (eElement.getElementsByTagName("aagets").item(0) != null) {
					anio = eElement.getElementsByTagName("aagets").item(0).getTextContent()
				}

				entradaDetalle.setFechaSolicitud(formatter.parse(dia+mes+anio))
			}
		}

		return entradaDetalle
	}

	AlptisGeneralData alptisGeneralData(String salida){

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
		DocumentBuilder builder = factory.newDocumentBuilder()

		InputSource is = new InputSource(new StringReader(tratarXmlAlptis(salida)))
		is.setEncoding("UTF-8")
		Document doc = builder.parse(is)

		doc.getDocumentElement().normalize()

		NodeList nList = doc.getElementsByTagName("generalData")

		AlptisGeneralData entradaDetalle = new AlptisGeneralData()

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp)

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				if (eElement.getElementsByTagName("candidateSTCode").item(0) != null) {
					entradaDetalle.setCandidateSTCode(eElement.getElementsByTagName("candidateSTCode").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("name").item(0) != null) {
					entradaDetalle.setName(eElement.getElementsByTagName("name").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("surname").item(0) != null) {
					entradaDetalle.setSurname(eElement.getElementsByTagName("surname").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("fiscalIdentificationNumber").item(0) != null) {
					entradaDetalle.setFiscalIdentificationNumber(eElement.getElementsByTagName("fiscalIdentificationNumber").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("gender").item(0) != null) {
					entradaDetalle.setGender(eElement.getElementsByTagName("gender").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("dossierCode").item(0) != null) {
					entradaDetalle.setDossierCode(eElement.getElementsByTagName("dossierCode").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("productName").item(0) != null) {
					entradaDetalle.setProductName(eElement.getElementsByTagName("productName").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("type").item(0) != null) {
					entradaDetalle.setType(eElement.getElementsByTagName("type").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("sumAssured").item(0) != null) {
					entradaDetalle.setSumAssured(eElement.getElementsByTagName("sumAssured").item(0).getTextContent())
				}
				if (eElement.getElementsByTagName("requestNumber").item(0) != null) {
					entradaDetalle.setRequestNumber(eElement.getElementsByTagName("requestNumber").item(0).getTextContent())
				}

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				String requestDate = null
				String birthDate = null

				if (eElement.getElementsByTagName("birthDate").item(0) != null) {
					birthDate = eElement.getElementsByTagName("birthDate").item(0).getTextContent()
				}

				entradaDetalle.setBirthDate(formatter.parse(birthDate))

				if (eElement.getElementsByTagName("requestDate").item(0) != null) {
					requestDate = eElement.getElementsByTagName("requestDate").item(0).getTextContent()
				}

				entradaDetalle.setRequestDate(formatter.parse(requestDate))
			}
		}

		return entradaDetalle
	}
//
//	/*************************
//	 * MÉTODOS NO UTILIZADOS *
//	 *************************/
//
//	private groovy.util.NodeList getNodeListByTagName(String entrada) {
//		def list = new XmlParser().parseText(entrada)
//		assert list instanceof groovy.util.Node
//		return list
//	}
//
//	private EnvioAMA getEnvioAMAByNodeList(groovy.util.NodeList entrada) {
//		EnvioAMA salida = new EnvioAMA()
//		for (Node actual:list) {
//			if(actual.hasChildNodes()) {
//				salida.numExpediente = actual.getFirstChild().nodeValue
//			}
//		}
//	}
//
//	private String getElementByTagName(groovy.util.NodeList list, String tag)  {
//		for (Node actual:list) {
//			actual
//				if (eElement.getElementsByTagName(tag).item(0) != null) {
//					return eElement.getElementsByTagName(tag).item(0).getTextContent()
//					break
//				}
//
//
//		}
//	}
//
//	private <T> T jaxbParser(String entrada) {
//		T salida = null
//		if(entrada != null && entrada.length() > 0) {
//
//			JAXBContext jaxbContext = JAXBContext.newInstance(salida.getClass())
//			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()
//
//			StringReader reader = new StringReader(entrada)
//
//			JAXBElement<T> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), salida.getClass())
//			salida = root.getValue()
//		}
//		return salida;
//	}
//
//
//
//		private EnvioAMA getEnvioGroovy(String entrada) {
//		EnvioAMA salida = new EnvioAMA()
//		def list = new XmlParser().parseText(entrada)
//		def children = list.children()
//		List bucle = children.asList()
//		for (groovy.util.Node actual:bucle) {
//			if(actual.value() != null && !actual.value().toString().equalsIgnoreCase("[]")) {
//				if(actual.name().toString().contains("numExpediente")) {
//					salida.setExpediente(actual.value())
//				} else if(actual.name().toString().contains("numSolicitud")) {
//					salida.setSolicitud(actual.value())
//				} else if(actual.name().toString().contains("numSumplemento")) {
//					salida.setSumplemento(actual.value())
//				} else if(actual.name().toString().contains("DateStart")) {
//					salida.setFechaComienzo(actual.value())
//				} else if(actual.name().toString().contains("DateEnd")) {
//					salida.setFechaFin(actual.value())
//				}
//			}
//		}
//		return salida
//	}
//
//	private EnvioAMA getEnvioAMA(String entrada) {
//		EnvioAMA salida = new EnvioAMA()
//		if(entrada != null) {
//			String[] params = entrada.trim().replace("-",":").split(":")
//			if(params != null && params.length == 4) {
//				salida.setEstado(params[1].trim())
//				salida.setRespuesta(params[3].trim())
//			}
//		}
//		return salida
//	}
//
//












	private String tratarXmlAlptis(String entrada) {
		/**EL XML A TRATAR ESTA MAL FORMADO. TENEMOS QUE SELECCIONAR EL FRAGMENTO QUE QUEREMOS PARA SACAR LOS DATOS.
		 *
		 */

		int startIndex = 0
		int endIndex = 0
		def toBeReplaced = ""

		def xml = entrada.substring(entrada.indexOf("<generalData>"), entrada.indexOf("</generalData>")+"</generalData>".length())

		if (startIndex != -1){

			startIndex = xml.indexOf("<contractedBenefits>")
			endIndex = xml.indexOf("</contractedBenefits>")+"</contractedBenefits>".length()
			toBeReplaced = xml.substring(startIndex, endIndex)
		}


		def xml2 = xml.replace(toBeReplaced, "")

		startIndex = xml2.indexOf("<crmQuestions>")

		if (startIndex != -1){
			endIndex = xml2.indexOf("</crmQuestions>")+"</crmQuestions>".length()
			toBeReplaced = xml2.substring(startIndex, endIndex);
		}

		def xml3 = xml2.replace(toBeReplaced, "")

		return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>"+xml3
	}

}
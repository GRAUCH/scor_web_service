package hwsol.webservices

import com.scor.global.CompanyLog
import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Recibido

import hwsol.webservices.LogUtil
import hwsol.webservices.TransformacionUtil

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCasesResultsRequest

class LogUtil {

	def elementos

	public List<CompanyLog> obtenerCopaniasLog(String ou) {

		List<Company> cias = new ArrayList<Company>()
		List<CompanyLog> ciasLog = new ArrayList<CompanyLog>()

		if (ou != null && !ou.isEmpty() && !ou.equals("AD")) {
			cias = Company.findAllByOu(ou)
		} else {
			cias = Company.findAllByOuIsNotNull()
		}

		for (int i = 0; i < cias.size(); i++) {

			if (cias.get(i).generationAutomatic){

				CompanyLog ciaLog = new CompanyLog()

				ciaLog.setLogo(cias.get(i).nombre+".jpg")
				ciaLog.setRecibidos(Recibido.findAllByCia(cias.get(i).id.toString()))
				ciaLog.setEnviados(Envio.findAllByCia(cias.get(i).id.toString()))
				ciaLog.setName(cias.get(i).nombre)
				ciaLog.setId(cias.get(i).id.toString())
				ciaLog.setOu(cias.get(i).ou)

				ciasLog.add(ciaLog)
			}
		}

		return ciasLog
	}


	public obtenerRecibidos(nombre, idCia, desde, hasta) {

		List<Recibido> recibidos = new ArrayList<Recibido>()

		LogUtil logUtil = new LogUtil()

		if (idCia != null && !idCia.toString().isEmpty()){

			def criteria = Recibido.createCriteria()

			recibidos = criteria.list {
				eq("cia", idCia)
				and {
					between("fecha", desde, hasta)
				}
				order("fecha", "desc")
			}

			switch (nombre){

				case "caser":

					List<com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest> recibidosCaser = new ArrayList<com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest.class);
						com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

						recibidosCaser.add(gestionReconocimientoMedicoRequest)
					}

					elementos = recibidosCaser

					break


				/**RECIBIDOS AMA
				 *
				 */
				case "ama":

					List<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> recibidosAma = new ArrayList<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
						com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

						recibidosAma.add(recibidosAma)
					}

					elementos = recibidosAma

					break

				case "cajamar":

					List<CajamarUnderwrittingCaseManagementRequest> recibidosCajamar = new ArrayList<CajamarUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(CajamarUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<CajamarUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), CajamarUnderwrittingCaseManagementRequest.class);
						CajamarUnderwrittingCaseManagementRequest gestionReconocimientoMedicoRequest = root.getValue();

						recibidosCajamar.add(gestionReconocimientoMedicoRequest)
					}

					elementos = recibidosCajamar

					break

				case "lagunaro":

					List<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest> recibidosLagunaro = new ArrayList<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
						com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

						recibidosLagunaro.add(gestionReconocimientoMedicoRequest)
					}

					elementos = recibidosLagunaro

					break


				case "alptis":

					List<AlptisUnderwrittingCaseManagementRequest> recibidosAlptis = new ArrayList<AlptisUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(AlptisUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<AlptisUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), AlptisUnderwrittingCaseManagementRequest.class);
						AlptisUnderwrittingCaseManagementRequest alptisUnderwrittingCaseManagementRequest = root.getValue();

						recibidosAlptis.add(alptisUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosAlptis

					break

				case "afiesca":

					List<AfiEscaUnderwrittingCaseManagementRequest> recibidosAfiesca = new ArrayList<AfiEscaUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(AfiEscaUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<AfiEscaUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), AfiEscaUnderwrittingCaseManagementRequest.class);
						AfiEscaUnderwrittingCaseManagementRequest afiEscaUnderwrittingCaseManagementRequest = root.getValue();

						recibidosAfiesca.add(afiEscaUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosAfiesca

					break

				case "lifesquare":

					List<LifesquareUnderwrittingCaseManagementRequest> recibidosLifesquare = new ArrayList<LifesquareUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(LifesquareUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<LifesquareUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), LifesquareUnderwrittingCaseManagementRequest.class);
						LifesquareUnderwrittingCaseManagementRequest lifesquareUnderwrittingCaseManagementRequest = root.getValue();

						recibidosLifesquare.add(lifesquareUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosLifesquare

					break

				case "psn":

					List<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest> recibidosPsn = new ArrayList<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest.class);
						com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest psnUnderwrittingCaseManagementRequest = root.getValue();

						recibidosPsn.add(psnUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosPsn

					break

				case "netinsurance":

					List<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest> recibidosNetinsurance = new ArrayList<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest.class);
						com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest netInsuranceUnderwrittingCaseManagementRequest = root.getValue();

						recibidosNetinsurance.add(netInsuranceUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosNetinsurance

					break

				case "simplefr":

					List<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest> recibidosSimplefr = new ArrayList<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest.class);
						com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest simpleFrUnderwrittingCaseManagementRequest = root.getValue();

						recibidosSimplefr.add(simpleFrUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosSimplefr

					break

				case "societegenerale":

					List<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest> recibidosSocieteGenerale = new ArrayList<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest>()

					for (int i = 0; i < recibidos.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(recibidos.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest.class);
						com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest societeGeneraleUnderwrittingCaseManagementRequest = root.getValue();

						recibidosSocieteGenerale.add(societeGeneraleUnderwrittingCaseManagementRequest)
					}

					elementos = recibidosSocieteGenerale

					break
			}

			return elementos
		}
	}

	public obtenerEnviados(nombre, idCia, desde, hasta) {

		List<Envio> enviados = new ArrayList<Envio>()
		Parser parser = new Parser()
		LogUtil logUtil = new LogUtil()

		if (idCia != null && !idCia.toString().isEmpty()){

			def criteria = Envio.createCriteria()

			enviados = criteria.list {
				eq("cia", idCia)
				and {
					between("fecha", desde, hasta)
				}
				order("fecha", "desc")
			}


			switch (nombre){

				case "caser":

					List<EntradaDetalle> enviadosCaser = new ArrayList<EntradaDetalle>()

					for (int i = 0; i < enviados.size(); i++) {

						enviadosCaser.add(parser.leerEnvioCaser(enviados.get(i).info.trim()))
					}

					elementos = enviadosCaser

					break


				/**RECIBIDOS AMA
				 *
				 */
				case "ama":

					List<com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE> enviadosAma = new ArrayList<com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE>()

					for (int i = 0; i < enviados.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(enviados.get(i).info.trim());

						JAXBElement<com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE.class);
						com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsE saveDossierResultsE = root.getValue();

						enviadosAma.add(saveDossierResultsE)
					}

					elementos = enviadosAma

					break

				case "cajamar":

					List<EnvioCajamar> envioadosCajamar = new ArrayList<EnvioCajamar>()

					for (int i = 0; i < enviados.size(); i++) {

						envioadosCajamar.add(parser.leerEnvioCajamar(enviados.get(i).info.trim()))
					}

					elementos = envioadosCajamar

					break

				case "lagunaro":

					List<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> recibidosLagunaro = new ArrayList<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest>()

					for (int i = 0; i < enviados.size(); i++) {

						JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(enviados.get(i).info.trim());

						JAXBElement<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
						com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

						recibidosLagunaro.add(gestionReconocimientoMedicoRequest)
					}

					elementos = recibidosLagunaro

					break


				case "alptis":

					List<EnvioAlptis> envioadosAlptis = new ArrayList<EnvioAlptis>()

					for (int i = 0; i < enviados.size(); i++) {

						envioadosAlptis.add(parser.leerEnvioAlptis(enviados.get(i).info.trim()))
					}

					elementos = envioadosAlptis

					break

				case "afiesca":

				/**PARA AFIESCA NO HAY TRANSFORMACIÓN SE RECOGEN LOS DATOS DE LA TABLA ENVIO DIRECTAMENTE
				 * 
				 */
					elementos = enviados

					break

				case "lifesquare":

				/**PARA LIFESQUARE NO HAY TRANSFORMACIÓN SE RECOGEN LOS DATOS DE LA TABLA ENVIO DIRECTAMENTE
				 * 
				 */
					elementos = enviados

					break
			}

			return elementos
		}
	}
}

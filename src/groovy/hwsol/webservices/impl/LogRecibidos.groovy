package hwsol.webservices.impl

import com.scortelemed.Company
import com.scortelemed.Recibido
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest
import hwsol.utilities.Parser
import hwsol.webservices.LogService

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

class LogRecibidos implements LogService{

    LogRecibidos(){}

    List elementos
    Parser parser = new Parser()

    @Override
    List obtener(company, desde, hasta, max) {

        if (company != null && !company.toString().isEmpty()) {
            List<Recibido> recibidos = new ArrayList<Recibido>()
            recibidos = findRecibidos(desde, hasta, company, [max: max])

            TipoCompany filtro = TipoCompany.fromNombre(company.nombre)

            switch (filtro) {
                case TipoCompany.CASER:
                    List<com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest> recibidosCaser = new ArrayList<com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest.class);
                        com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

                        recibidosCaser.add(gestionReconocimientoMedicoRequest)
                    }
                    elementos = recibidosCaser
                    break

                case TipoCompany.AMA:
                    List<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> recibidosAma = new ArrayList<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());
                        JAXBElement<com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest.class);
                        com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue()

                        recibidosAma.add(gestionReconocimientoMedicoRequest)
                    }
                    elementos = recibidosAma
                    break

                case TipoCompany.CAJAMAR:
                    List<CajamarUnderwrittingCaseManagementRequest> recibidosCajamar = new ArrayList<CajamarUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(CajamarUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<CajamarUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), CajamarUnderwrittingCaseManagementRequest.class);
                        CajamarUnderwrittingCaseManagementRequest gestionReconocimientoMedicoRequest = root.getValue();

                        recibidosCajamar.add(gestionReconocimientoMedicoRequest)
                    }
                    elementos = recibidosCajamar
                    break

                case TipoCompany.LAGUN_ARO:
                    List<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest> recibidosLagunaro = new ArrayList<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest.class);
                        com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest = root.getValue();

                        recibidosLagunaro.add(gestionReconocimientoMedicoRequest)
                    }
                    elementos = recibidosLagunaro
                    break

                case TipoCompany.ALPTIS:
                    List<AlptisUnderwrittingCaseManagementRequest> recibidosAlptis = new ArrayList<AlptisUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(AlptisUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<AlptisUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), AlptisUnderwrittingCaseManagementRequest.class);
                        AlptisUnderwrittingCaseManagementRequest alptisUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosAlptis.add(alptisUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosAlptis
                    break

                case TipoCompany.AFI_ESCA:
                    List<AfiEscaUnderwrittingCaseManagementRequest> recibidosAfiesca = new ArrayList<AfiEscaUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(AfiEscaUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<AfiEscaUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), AfiEscaUnderwrittingCaseManagementRequest.class);
                        AfiEscaUnderwrittingCaseManagementRequest afiEscaUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosAfiesca.add(afiEscaUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosAfiesca
                    break

                case TipoCompany.ZEN_UP:
                    List<LifesquareUnderwrittingCaseManagementRequest> recibidosLifesquare = new ArrayList<LifesquareUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(LifesquareUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<LifesquareUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), LifesquareUnderwrittingCaseManagementRequest.class);
                        LifesquareUnderwrittingCaseManagementRequest lifesquareUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosLifesquare.add(lifesquareUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosLifesquare
                    break

                case TipoCompany.PSN:
                    List<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest> recibidosPsn = new ArrayList<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest.class);
                        com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest psnUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosPsn.add(psnUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosPsn
                    break

                case TipoCompany.NET_INSURANCE:
                    List<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest> recibidosNetinsurance = new ArrayList<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest.class);
                        com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest netInsuranceUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosNetinsurance.add(netInsuranceUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosNetinsurance
                    break

                case TipoCompany.MALAKOFF_MEDERIC:
                    List<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest> recibidosSimplefr = new ArrayList<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest>()

                    for (int i = 0; i < recibidos.size(); i++) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest.class);
                        com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest simpleFrUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosSimplefr.add(simpleFrUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosSimplefr
                    break

                case TipoCompany.SOCIETE_GENERALE:
                    List<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest> recibidosSocieteGenerale = new ArrayList<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        StringReader reader = new StringReader(actual.info.trim());

                        JAXBElement<com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest.class);
                        com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest societeGeneraleUnderwrittingCaseManagementRequest = root.getValue();

                        recibidosSocieteGenerale.add(societeGeneraleUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosSocieteGenerale
                    break

                case TipoCompany.ENGINYERS:
                    List<com.scortelemed.schemas.enginyers.AddExp> recibidosEnginyers = new ArrayList<com.scortelemed.schemas.enginyers.AddExp>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.enginyers.AddExp.class)
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

                        StringReader reader = new StringReader(actual.info.trim())

                        JAXBElement<com.scortelemed.schemas.enginyers.AddExp> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.enginyers.AddExp.class)
                        com.scortelemed.schemas.enginyers.AddExp enginyersUnderwrittingCaseManagementRequest = root.getValue()

                        recibidosEnginyers.add(enginyersUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosEnginyers
                    break

                case TipoCompany.METHIS_LAB:
                    List<com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest> recibidosMethislab = new ArrayList<com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest.class)
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

                        StringReader reader = new StringReader(actual.info.trim())

                        JAXBElement<com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest.class)
                        com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest methislabUnderwrittingCaseManagementRequest = root.getValue()

                        recibidosMethislab.add(methislabUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosMethislab
                    break

                case TipoCompany.CBP_ITALIA:
                    List<com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest> recibidosCbpItalia = new ArrayList<com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest.class)
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

                        StringReader reader = new StringReader(actual.info.trim())

                        JAXBElement<com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest.class)
                        com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest cbpItaliaUnderwrittingCaseManagementRequest = root.getValue()

                        recibidosCbpItalia.add(cbpItaliaUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosCbpItalia
                    break

                case TipoCompany.CF_LIFE:
                    List<com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest> recibidosMethislabCF = new ArrayList<com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest>()

                    for (Recibido actual : recibidos) {

                        JAXBContext jaxbContext = JAXBContext.newInstance(com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest.class)
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()

                        StringReader reader = new StringReader(actual.info.trim())

                        JAXBElement<com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest.class)
                        com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest methislabCFUnderwrittingCaseManagementRequest = root.getValue()

                        recibidosMethislabCF.add(methislabCFUnderwrittingCaseManagementRequest)
                    }
                    elementos = recibidosMethislabCF
                    break

                default:
                    elementos = new ArrayList<>() //Evitamos nullpointers
                    break
            }

            return elementos
        }
    }


    private List<Recibido> findRecibidos(desde, hasta, Company company, Map sortParams) {
        StringBuilder hqlQueryBuilder = new StringBuilder(' ')

        hqlQueryBuilder << 'FROM Recibido AS recibido  '
        Map namedParams = [idCia: company.id]
        hqlQueryBuilder << 'WHERE cia = :idCia '
        hqlQueryBuilder << 'AND '
        hqlQueryBuilder << "fecha BETWEEN  :iniDate "
        hqlQueryBuilder << 'AND '
        hqlQueryBuilder << " :endDate "
        namedParams["endDate"] = hasta
        namedParams["iniDate"] = desde


        hqlQueryBuilder << "ORDER BY fecha DESC"

        System.out.println("idCia ID  -->>" + company.id)


        Recibido.executeQuery(hqlQueryBuilder.toString(), namedParams, sortParams)
    }
}

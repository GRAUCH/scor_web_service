package hwsol.webservices.impl

import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.TipoCompany
//AMA
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
//Caser
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest
import hwsol.entities.parser.RegistrarEventoSCOR
//PSN
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest as ConsultaExpedienteRequestPsn
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest as ResultadoReconocimientoMedicoRequestPsn
//NetInsurance
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierRequest
//Methislab
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCasesResultsRequest
//CBP Ita
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest
//Methislab CF
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCasesResultsRequest
//Alptis
import hwsol.entities.parser.AlptisGeneralData
//Cajamar
import hwsol.entities.parser.ValoracionTeleSeleccionResponse

import hwsol.entities.Envio as NewEnvio
import hwsol.entities.EnvioAMA
import hwsol.entities.EnvioAlptis
import hwsol.entities.EnvioCaser
import hwsol.entities.EnvioCajamar
import hwsol.entities.EnvioNetInsurance
import hwsol.entities.EnvioMethislab
import hwsol.entities.EnvioMethislabCF
import hwsol.entities.EnvioCbpIta
import hwsol.entities.EnvioPSN
import hwsol.factory.SchemaEntities
import hwsol.utilities.Parser
import hwsol.webservices.LogService

class LogEnviados implements LogService{

    LogEnviados(){}

    List elementos
    Parser parser = new Parser()

    List obtener(company, desde, hasta, max) {

        if (company != null) {
            List<Envio> enviados = new ArrayList<>()
            enviados = findEnvios(desde, hasta, company, [max: max])

            TipoCompany filtro = TipoCompany.fromNombre(company.nombre)

            switch (filtro) { //TODO: Revisar la tabla de producción para conocer el comportamiento de los elementos enviados
                case TipoCompany.CASER:
                    elementos = leerEnviosCaser(enviados)
                    break
                case TipoCompany.AMA:
                    elementos = leerEnviosAMA(enviados)
                    break
                case TipoCompany.ALPTIS:
                    elementos = leerEnviosAlptis(enviados)
                    break
                case TipoCompany.CAJAMAR:
                    elementos = leerEnviosCajamar(enviados)
                    break
                case TipoCompany.PSN:
                    elementos = leerEnviosPSN(enviados)
                    break
                case TipoCompany.NET_INSURANCE:
                    elementos = leerEnviosNetInsurance(enviados)
                    break
                case TipoCompany.METHIS_LAB:
                    elementos = leerEnviosMethislab(enviados)
                    break
                case TipoCompany.CBP_ITALIA:
                    elementos = leerEnviosCbpIta(enviados)
                    break
                case TipoCompany.CF_LIFE:
                    elementos = leerEnvios(enviados, MethislabCFUnderwrittingCasesResultsRequest.class)
                    break
                case TipoCompany.LAGUN_ARO:
                case TipoCompany.AFI_ESCA:
                case TipoCompany.MALAKOFF_MEDERIC:
                case TipoCompany.SOCIETE_GENERALE:
                case TipoCompany.ZEN_UP:
                case TipoCompany.ENGINYERS:
                default: //AFIESCA, ZEN UP(LIFESQUARE), etc
                    elementos = enviados
                    break
            }

            return elementos
        }
    }

    List<NewEnvio> leerEnvios(List<Envio> entrada, Class<?> myObjectClass) {
        List<NewEnvio> enviados = new ArrayList<>()
        if (entrada != null && !entrada.isEmpty()) {
            for (Envio actual:entrada) {
                NewEnvio salida = new NewEnvio()
                salida.set(actual)
                String info = actual.info.trim()
                if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                    if (info.contains(myObjectClass.simpleName)) {
                        Object cases = parser.jaxbParser(info, myObjectClass)
                        salida.setInfo(SchemaEntities.toString(cases))
                    }
                }
                enviados.add(salida)
            }
        }
        return enviados
    }


    private def findEnvios(desde, hasta, Company company, Map sortParams) {
        StringBuilder hqlQueryBuilder = new StringBuilder(' ')

        hqlQueryBuilder << 'FROM Envio AS envio  '
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

    /**
     * Método principal para leer envíos de AMA
     * @param Envio entrada
     * @return EnvioAMA (Expediente, Siniestro u Otro)
     */
    List<EnvioAMA> leerEnviosAMA(List<Envio> entrada) {
        List<EnvioAMA> enviadosAma = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioAMA salida = new EnvioAMA()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("ConsultaExpedienteRequest")) {
                    ConsultaExpedienteRequest expediente = parser.jaxbParser(info, ConsultaExpedienteRequest.class)
                    salida.setInfo(SchemaEntities.toString(expediente))
                } else if (info.contains("ResultadoSiniestroRequest")) {
                    ResultadoSiniestroRequest siniestro = parser.jaxbParser(info, ResultadoSiniestroRequest.class)
                    salida.setInfo(SchemaEntities.toString(siniestro))
                }
            }
            enviadosAma.add(salida)
        }
        return enviadosAma
    }

    List<EnvioCaser> leerEnviosCaser(List<Envio> entrada) {
        List<EnvioCaser> enviadosCaser = new ArrayList<EnvioCaser>()
        for (Envio actual:entrada) {
            EnvioCaser salida = new EnvioCaser()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("ResultadoReconocimientoMedicoRequest")) {
                    ResultadoReconocimientoMedicoRequest resultado = parser.jaxbParser(info, ResultadoReconocimientoMedicoRequest.class)
                    salida.setResultado(resultado)
                    salida.setInfo(SchemaEntities.toString(resultado))
                } else if (info.contains("service_RegistrarEventoSCOR")) {
                    RegistrarEventoSCOR evento = parser.registrarEventoSCOR(info)
                    salida.setEventoSCOR(evento)
                    salida.setInfo(SchemaEntities.toString(evento))
                }
            }
            enviadosCaser.add(salida)
        }

        return enviadosCaser
    }

    List<EnvioPSN> leerEnviosPSN(List<Envio> entrada) {
        List<EnvioPSN> enviadosPSN = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioPSN salida = new EnvioPSN()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("ConsultaExpedienteRequest")) {
                    ConsultaExpedienteRequestPsn expediente = parser.jaxbParser(info, ConsultaExpedienteRequestPsn.class)
                    salida.setExpediente(expediente)
                    salida.setInfo(SchemaEntities.toString(expediente))
                } else if (info.contains("ResultadoReconocimientoMedicoRequest")) {
                    ResultadoReconocimientoMedicoRequestPsn resultado = parser.jaxbParser(info, ResultadoReconocimientoMedicoRequestPsn.class)
                    salida.setReconocimiento(resultado)
                    salida.setInfo(SchemaEntities.toString(resultado))
                }
            }
            enviadosPSN.add(salida)
        }
        return enviadosPSN
    }

    List<EnvioNetInsurance> leerEnviosNetInsurance(List<Envio> entrada) {
        List<EnvioNetInsurance> enviadosNet = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioNetInsurance salida = new EnvioNetInsurance()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("NetinsuranteGetDossierRequest")) {
                    NetinsuranteGetDossierRequest dossier = parser.jaxbParser(info, NetinsuranteGetDossierRequest.class)
                    salida.setDossier(dossier)
                    salida.setInfo(SchemaEntities.toString(dossier))
                }
            }
            enviadosNet.add(salida)
        }
        return enviadosNet
    }

    List<EnvioMethislab> leerEnviosMethislab(List<Envio> entrada) {
        List<EnvioMethislab> enviadosMethislab = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioMethislab salida = new EnvioMethislab()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("MethislabUnderwrittingCasesResultsRequest")) {
                    MethislabUnderwrittingCasesResultsRequest cases = parser.jaxbParser(info, MethislabUnderwrittingCasesResultsRequest.class)
                    salida.setResults(cases)
                    salida.setInfo(SchemaEntities.toString(cases))
                }
            }
            enviadosMethislab.add(salida)
        }
        return enviadosMethislab
    }

    List<EnvioCbpIta> leerEnviosCbpIta(List<Envio> entrada) {
        List<EnvioCbpIta> enviadosCbpIta = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioCbpIta salida = new EnvioCbpIta()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("CbpitaUnderwrittingCasesResultsRequest")) {
                    CbpitaUnderwrittingCasesResultsRequest cases = parser.jaxbParser(info, CbpitaUnderwrittingCasesResultsRequest.class)
                    salida.setResults(cases)
                    salida.setInfo(SchemaEntities.toString(cases))
                }
            }
            enviadosCbpIta.add(salida)
        }
        return enviadosCbpIta
    }

    List<EnvioMethislabCF> leerEnviosMethislabCF(List<Envio> entrada) {
        List<EnvioMethislabCF> enviadosMethislab = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioMethislabCF salida = new EnvioMethislabCF()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("MethislabCFUnderwrittingCasesResultsRequest")) {
                    MethislabCFUnderwrittingCasesResultsRequest cases = parser.jaxbParser(info, MethislabCFUnderwrittingCasesResultsRequest.class)
                    salida.setResults(cases)
                    salida.setInfo(SchemaEntities.toString(cases))
                }
            }
            enviadosMethislab.add(salida)
        }
        return enviadosMethislab
    }

    List<EnvioCajamar> leerEnviosCajamar(List<Envio> entrada) {
        List<EnvioCajamar> enviadosCajamar = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioCajamar salida = new EnvioCajamar()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("ValoracionTeleSeleccionResponse")) {
                    ValoracionTeleSeleccionResponse valoracion = parser.valoracionTeleSeleccionResponse(info)
                    salida.setValoracion(valoracion)
                    salida.setInfo(SchemaEntities.toString(valoracion))
                }
            }
            enviadosCajamar.add(salida)
        }
        return enviadosCajamar
    }

    List<EnvioAlptis> leerEnviosAlptis(List<Envio> entrada) {
        List<EnvioAlptis> enviadosAlptis = new ArrayList<>()
        for (Envio actual:entrada) {
            EnvioAlptis salida = new EnvioAlptis()
            salida.set(actual)
            String info = actual.info
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("generalData")) {
                    AlptisGeneralData valoracion = parser.alptisGeneralData(info)
                    salida.setValoracion(valoracion)
                    salida.setInfo(SchemaEntities.toString(valoracion))
                }
            }
            enviadosAlptis.add(salida)
        }
        return enviadosAlptis
    }



}

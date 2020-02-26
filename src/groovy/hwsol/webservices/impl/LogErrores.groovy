package hwsol.webservices.impl

import com.scortelemed.Company
import com.scortelemed.Error
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest
import com.scortelemed.schemas.enginyers.AddExp
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestLagunaro
import com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestPsn
import hwsol.factory.SchemaEntities
import hwsol.utilities.Parser
import hwsol.webservices.LogService

class LogErrores implements LogService{

    LogErrores(){}

    List elementos
    Parser parser = new Parser()

    List obtener(company, desde, hasta, max) {
        List<Error> errores = null
        if (company != null && !company.toString().isEmpty()) {
            errores = findErrores(company,desde, hasta, [max: max])

            TipoCompany filtro = TipoCompany.fromNombre(company.nombre)

            switch (filtro) { //TODO: Revisar la tabla de producción para conocer el comportamiento de los elementos enviados
                case TipoCompany.CASER:
                    elementos = leerErroresCaser(errores)
                    break
                case TipoCompany.CAJAMAR:
                    elementos = leerErrores(errores, CajamarUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.PSN:
                    elementos = leerErrores(errores, GestionReconocimientoMedicoRequestPsn.class)
                    break
                case TipoCompany.NET_INSURANCE:
                    elementos = leerErroresNetInsurance(errores)
                    break
                case TipoCompany.CBP_ITALIA:
                    elementos = leerErrores(errores, CbpitaUnderwrittingCasesResultsRequest.class)
                    break
                case TipoCompany.METHIS_LAB:
                    elementos = leerErrores(errores, MethislabUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.CF_LIFE:
                    elementos = leerErrores(errores, MethislabCFUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.ENGINYERS:
                    elementos = leerErrores(errores, AddExp.class)
                    break
                case TipoCompany.LAGUN_ARO:
                    elementos = leerErrores(errores, GestionReconocimientoMedicoRequestLagunaro.class)
                    break
                case TipoCompany.AMA:
                default: //AMA,
                    elementos = errores
                    break
                case TipoCompany.ALPTIS:
                    break
                case TipoCompany.AFI_ESCA:
                    break
                case TipoCompany.MALAKOFF_MEDERIC:
                    break
                case TipoCompany.SOCIETE_GENERALE:
                    break
                case TipoCompany.ZEN_UP:
                    break
                case TipoCompany.SCOR:
                    break
            }

            return elementos
        }
    }

    List<Error> leerErroresCaser(List<Error> entrada) {
        List<Error> errores = new ArrayList<>()
        for (Error actual:entrada) {
            String info = actual.info.trim()
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("GestionReconocimientoMedicoRequest")) {
                    actual.setInfo(SchemaEntities.toString(parser.jaxbParser(info, GestionReconocimientoMedicoRequest.class)))
                } else if (info.contains("service_RegistrarEventoSCOR")) {
                    actual.setInfo(SchemaEntities.toString(parser.registrarEventoSCOR(info)))
                }
            }
            errores.add(actual)
        }
        return errores
    }

    List<Error> leerErroresNetInsurance(List<Error> entrada) {
        List<Error> errores = new ArrayList<>()
        for (Error actual:entrada) {
            String info = actual.info.trim()
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info.contains("NetinsuranteGetDossierRequest")) {
                    actual.setInfo(SchemaEntities.toString(parser.jaxbParser(info, NetinsuranteGetDossierRequest.class)))
                } else if (info.contains("NetinsuranteUnderwrittingCaseManagementRequest")) {
                    actual.setInfo(SchemaEntities.toString(parser.jaxbParser(info, NetinsuranteUnderwrittingCaseManagementRequest.class)))
                }
            }
            errores.add(actual)
        }
        return errores
    }

    List<Error> leerErrores(List<Error> entrada, Class<?> myObjectClass) {
        List<Error> enviados = new ArrayList<>()
        for (Error actual:entrada) {
            String info = actual.info.trim()
            if (info != null && !info.isEmpty() && info.charAt(0) == '<') {
                if (info?.toUpperCase().contains(myObjectClass?.simpleName?.toUpperCase())) {
                    actual.setInfo(SchemaEntities.toString(parser.jaxbParser(info, myObjectClass)))
                }
            }
            enviados.add(actual)
        }
        return enviados
    }



    private List<Error> findErrores(Company company,desde, hasta, Map sortParams) {
        StringBuilder hqlQueryBuilder = new StringBuilder(' ')

        hqlQueryBuilder << 'FROM Error AS error  '
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


        Error.executeQuery(hqlQueryBuilder.toString(), namedParams, sortParams)
    }
}

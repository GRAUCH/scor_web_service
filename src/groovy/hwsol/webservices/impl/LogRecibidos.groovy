package hwsol.webservices.impl

import com.scortelemed.Company
import com.scortelemed.Recibido
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.ama.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestAma
import com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestPsn
import com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestLagunaro
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.simplefr.SimplefrUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.societegenerale.SocieteGeneraleUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.enginyers.AddExp
import hwsol.utilities.Parser
import hwsol.webservices.LogService

class LogRecibidos implements LogService{

    LogRecibidos(){}

    def logginService

    List elementos
    Parser parser = new Parser()

    @Override
    List obtener(company, desde, hasta, max) {

        if (company != null && !company.toString().isEmpty()) {
            List<Recibido> recibidos = findRecibidos(desde, hasta, company, [max: max])

            TipoCompany filtro = TipoCompany.fromNombre(company.nombre)

            switch (filtro) {
                case TipoCompany.CASER:
                    elementos = parser.jaxbListParser(recibidos, GestionReconocimientoMedicoRequest.class)
                    break
                case TipoCompany.AMA:
                    elementos = parser.jaxbListParser(recibidos, GestionReconocimientoMedicoRequestAma.class)
                    break
                case TipoCompany.CAJAMAR:
                    elementos = parser.jaxbListParser(recibidos, CajamarUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.LAGUN_ARO:
                    elementos = parser.jaxbListParser(recibidos, GestionReconocimientoMedicoRequestLagunaro.class)
                    break
                case TipoCompany.ALPTIS:
                    elementos = parser.jaxbListParser(recibidos, AlptisUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.AFI_ESCA:
                    elementos = parser.jaxbListParser(recibidos, AfiEscaUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.ZEN_UP:
                    elementos = parser.jaxbListParser(recibidos, LifesquareUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.PSN:
                    elementos = parser.jaxbListParser(recibidos, GestionReconocimientoMedicoRequestPsn.class)
                    break
                case TipoCompany.NET_INSURANCE:
                    elementos = parser.jaxbListParser(recibidos, NetinsuranteUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.MALAKOFF_MEDERIC:
                    elementos = parser.jaxbListParser(recibidos, SimplefrUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.SOCIETE_GENERALE:
                    elementos = parser.jaxbListParser(recibidos, SocieteGeneraleUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.ENGINYERS:
                    elementos = parser.jaxbListParser(recibidos, AddExp.class)
                    break
                case TipoCompany.METHIS_LAB:
                    elementos = parser.jaxbListParser(recibidos, MethislabUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.CBP_ITALIA:
                    elementos = parser.jaxbListParser(recibidos, CbpitaUnderwrittingCaseManagementRequest.class)
                    break
                case TipoCompany.CF_LIFE:
                    elementos = parser.jaxbListParser(recibidos, MethislabCFUnderwrittingCaseManagementRequest.class)
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
        Map namedParams = [idCia: company.id.toString()]
        hqlQueryBuilder << 'WHERE cia = :idCia '
        hqlQueryBuilder << 'AND '
        hqlQueryBuilder << "fecha BETWEEN  :iniDate "
        hqlQueryBuilder << 'AND '
        hqlQueryBuilder << " :endDate "
        namedParams["endDate"] = hasta
        namedParams["iniDate"] = desde
        hqlQueryBuilder << "ORDER BY fecha DESC"
        System.out.println("idCia ID  -->>" + company.id)

        try {
            Recibido.executeQuery(hqlQueryBuilder.toString(), namedParams, sortParams)
        } catch (Exception e) {
            logginService.putErrorMessage(this.class.getName() + + ".findRecibidos: Error:" + e.getCause().getMessage())
            throw new Exception(e.getMessage(), e)
        }
    }
}

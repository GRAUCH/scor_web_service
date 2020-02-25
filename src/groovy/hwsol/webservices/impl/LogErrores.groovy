package hwsol.webservices.impl

import com.scortelemed.Company
import com.scortelemed.Error
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

class LogErrores implements LogService{

    LogErrores(){}

    List elementos
    Parser parser = new Parser()

    @Override
    List obtener(company, desde, hasta, max) {
        List<Error> errores = null
        if (company != null && !company.toString().isEmpty()) {
            errores = findErrores(company,desde, hasta, [max: max])
        }
        elementos = errores
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

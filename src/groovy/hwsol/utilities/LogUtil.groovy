package hwsol.utilities

import com.scor.global.CompanyLog
import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Error
import com.scortelemed.Recibido
import com.scortelemed.TipoCompany
import com.ws.afiesca.beans.AfiEscaUnderwrittingCaseManagementRequest
import com.ws.alptis.beans.AlptisUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import com.ws.lifesquare.beans.LifesquareUnderwrittingCaseManagementRequest

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

class LogUtil {

    List<CompanyLog> obtenerCompanyLogs(String ou) {

        List<Company> cias = new ArrayList<Company>()
        List<CompanyLog> ciasLog = new ArrayList<CompanyLog>()

        if (ou != null && !ou.isEmpty() && !ou.equals("AD")) {
            cias = Company.findAllByOu(ou)
        } else {
            cias = Company.findAllByOuIsNotNull()
        }

        for (int i = 0; i < cias.size(); i++) {

            if (cias.get(i).generationAutomatic) {

                CompanyLog ciaLog = new CompanyLog()

                ciaLog.setLogo(cias.get(i)?.nombre + ".png")
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

}

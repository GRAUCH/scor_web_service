package hwsol.utilities

import com.scor.global.CompanyLog
import com.scortelemed.Company

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
                ciaLog.setName(cias.get(i).nombre)
                ciaLog.setId(cias.get(i).id.toString())
                ciaLog.setOu(cias.get(i).ou)
                ciasLog.add(ciaLog)
            }
        }
        return ciasLog
    }


    static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        return calendar
    }


    static def paramsToDateIni(params) {
        def fechaIni
        if (params.ini && params.fin) {
            try {
                if ((Date) params.fin && (Date) params.ini) {
                    fechaIni = LogUtil.dateToCalendar(params.ini)
                    fechaIni = fechaIni.getTime().format('yyyyMMdd HH:mm')
                }
            } catch (Exception e) {
                fechaIni = URLDecoder.decode(params.ini.trim(), "ISO-8859-1")

            }
        } else {
            Calendar fecha = Calendar.getInstance()
            fecha.add(Calendar.MINUTE, -30)
            fechaIni = fecha.getTime().format('yyyyMMdd HH:mm')
            fechaIni = fechaIni.toString() + ":00"
        }
        return fechaIni
    }


    static def paramsToDateFin(params) {
        def fechaFin
        if (params.ini && params.fin) {
            try {
                if ((Date) params.fin && (Date) params.ini) {
                    fechaFin = LogUtil.dateToCalendar(params.fin)
                    fechaFin = fechaFin.getTime().format('yyyyMMdd HH:mm')
                }
            } catch (Exception e) {
                fechaFin = URLDecoder.decode(params.fin.trim(), "ISO-8859-1")
            }
        } else {
            Calendar fecha = Calendar.getInstance()
            fecha.add(Calendar.MINUTE, -60)
            fechaFin = fecha.getTime().format('yyyyMMdd HH:mm')
            fechaFin = fechaFin.toString() + ":59"
        }
        return fechaFin
    }


}

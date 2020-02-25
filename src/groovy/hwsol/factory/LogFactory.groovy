package hwsol.factory

import com.scortelemed.Envio
import hwsol.webservices.LogService
import hwsol.webservices.impl.LogEnviados

class LogFactory {

    static LogService newLogService(Class <?> newClass) {
        LogService salida = null
        if(newClass != null) {
            if(newClass.equals(Envio.class)) {
                salida = new LogEnviados()
            }
        }
        return salida
    }
}

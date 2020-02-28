package hwsol.factory

import com.scortelemed.Envio
import com.scortelemed.Error
import com.scortelemed.Recibido
import hwsol.webservices.LogService
import hwsol.webservices.impl.LogEnviados
import hwsol.webservices.impl.LogErrores
import hwsol.webservices.impl.LogRecibidos

class LogFactory {

    static LogService newLogService(Class <?> newClass) {
        LogService salida = null
        if(newClass != null) {
            if(newClass.equals(Envio.class)) {
                salida = new LogEnviados()
            } else if(newClass.equals(Recibido.class)) {
                salida = new LogRecibidos()
            } else if(newClass.equals(Error.class)) {
                salida = new LogErrores()
            }
        }
        return salida
    }
}

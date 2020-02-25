package hwsol.factory

//AMA
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest
//Caser
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest
import hwsol.entities.RegistrarEventoSCOR
//PSN
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest as ConsultaExpedienteRequestPsn
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest as ResultadoReconocimientoMedicoRequestPsn

class SchemaEntities {

    static String toString(ConsultaExpedienteRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("numExpediente: ")
        builder.append(entrada.numExpediente)
        builder.append(", numSolicitud: ")
        builder.append(entrada.numSolicitud)
        builder.append(", numSumplemento: ")
        builder.append(entrada.numSumplemento)
        return builder.toString()
    }

    static String toString(ResultadoSiniestroRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("dateStart: ")
        builder.append(entrada.dateStart)
        builder.append(", dateEnd: ")
        builder.append(entrada.dateEnd)
        return builder.toString()
    }

    static String toString(ResultadoReconocimientoMedicoRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("dateStart: ")
        builder.append(entrada.dateStart)
        builder.append(", dateEnd: ")
        builder.append(entrada.dateEnd)
        return builder.toString()
    }
    static String toString(RegistrarEventoSCOR entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("fechaCierre: ")
        builder.append(entrada.fechaCierre)
        builder.append(", idExpediente: ")
        builder.append(entrada.idExpediente)
        builder.append(", codigoEvento: ")
        builder.append(entrada.codigoEvento)
        builder.append(", detalle: ")
        builder.append(entrada.detalle)
        return builder.toString()
    }

    static String toString(ConsultaExpedienteRequestPsn entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("numExpediente: ")
        builder.append(entrada.numExpediente)
        builder.append(", numSolicitud: ")
        builder.append(entrada.numSolicitud)
        builder.append(", numSumplemento: ")
        builder.append(entrada.numSumplemento)
        return builder.toString()
    }

    static String toString(ResultadoReconocimientoMedicoRequestPsn entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("numSolicitud: ")
        builder.append(entrada.numSolicitud)
        return builder.toString()
    }

}
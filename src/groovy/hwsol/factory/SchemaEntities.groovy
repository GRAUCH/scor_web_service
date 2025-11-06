package hwsol.factory

//AMA
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest
//Caser
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest
import com.scortelemed.schemas.caser.GestionReconocimientoMedicoRequest
import com.scortelemed.schemas.enginyers.AddExp
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCaseManagementRequest
import com.scortelemed.schemas.netinsurance.NetinsuranteUnderwrittingCaseManagementRequest
import com.ws.cajamar.beans.CajamarUnderwrittingCaseManagementRequest
import hwsol.entities.parser.AlptisGeneralData
import hwsol.entities.parser.RegistrarEventoSCOR
import com.ws.lagunaro.beans.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestLagunaro
//PSN
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest as ConsultaExpedienteRequestPsn
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest as ResultadoReconocimientoMedicoRequestPsn
import com.scortelemed.schemas.psn.GestionReconocimientoMedicoRequest as GestionReconocimientoMedicoRequestPsn

//NetInsurance
import com.scortelemed.schemas.netinsurance.NetinsuranteGetDossierRequest
//CBP Ita
import com.scortelemed.schemas.cbpita.CbpitaUnderwrittingCasesResultsRequest
//Methislab
import com.scortelemed.schemas.methislab.MethislabUnderwrittingCasesResultsRequest
//MethislabCF
import com.scortelemed.schemas.methislabCF.MethislabCFUnderwrittingCasesResultsRequest
import hwsol.entities.parser.ValoracionTeleSeleccionResponse


class SchemaEntities {

    static String toString(GestionReconocimientoMedicoRequestPsn entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("fiscalIdentificationNumber: ")
        builder.append(entrada.candidateInformation?.fiscalIdentificationNumber)
        builder.append(", birthDate: ")
        builder.append(entrada.candidateInformation?.birthDate)
        builder.append(", phoneNumber1: ")
        builder.append(entrada.candidateInformation?.phoneNumber1)
        return builder.toString()
    }

    static String toString(MethislabCFUnderwrittingCaseManagementRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("fiscalIdentificationNumber: ")
        builder.append(entrada.candidateInformation?.fiscalIdentificationNumber)
        builder.append(", birthDate: ")
        builder.append(entrada.candidateInformation?.birthDate)
        builder.append(", phoneNumber1: ")
        builder.append(entrada.candidateInformation?.phoneNumber1)
        return builder.toString()
    }

    static String toString(MethislabUnderwrittingCaseManagementRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("fiscalIdentificationNumber: ")
        builder.append(entrada.candidateInformation?.fiscalIdentificationNumber)
        builder.append(", birthDate: ")
        builder.append(entrada.candidateInformation?.birthDate)
        builder.append(", phoneNumber1: ")
        builder.append(entrada.candidateInformation?.phoneNumber1)
        return builder.toString()
    }

    static String toString(GestionReconocimientoMedicoRequestLagunaro entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("nombre: ")
        builder.append(entrada.asegurado?.nombre)
        builder.append(", apellido1: ")
        builder.append(entrada.asegurado?.apellido1)
        builder.append(", apellido2: ")
        builder.append(entrada.asegurado?.apellido2)
        return builder.toString()
    }

    static String toString(AddExp entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("nif: ")
        builder.append(entrada.d?.nif)
        builder.append(", policyNumber: ")
        builder.append(entrada.d?.policyNumber)
        builder.append(", companyCode: ")
        builder.append(entrada.gen?.companyCode)
        return builder.toString()
    }

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

    static String toString(GestionReconocimientoMedicoRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("productCode: ")
        builder.append(entrada.candidateInformation?.productCode)
        builder.append(", fiscalIdentificationNumber: ")
        builder.append(entrada.candidateInformation?.fiscalIdentificationNumber)
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

    static String toString(NetinsuranteGetDossierRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("requestNumber: ")
        builder.append(entrada.requestNumber)
        return builder.toString()
    }

    static String toString(NetinsuranteUnderwrittingCaseManagementRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("benefictCode: ")
        builder.append(entrada.benefits?.benefictCode)
        return builder.toString()
    }

    static String toString(MethislabUnderwrittingCasesResultsRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("dateStart: ")
        builder.append(entrada.dateStart)
        builder.append(", dateEnd: ")
        builder.append(entrada.dateEnd)
        return builder.toString()
    }

    static String toString(CbpitaUnderwrittingCasesResultsRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("dateStart: ")
        builder.append(entrada.dateStart)
        builder.append(", dateEnd: ")
        builder.append(entrada.dateEnd)
        return builder.toString()
    }

    static String toString(MethislabCFUnderwrittingCasesResultsRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("dateStart: ")
        builder.append(entrada.dateStart)
        builder.append(", dateEnd: ")
        builder.append(entrada.dateEnd)
        return builder.toString()
    }

    static String toString(ValoracionTeleSeleccionResponse entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("causa: ")
        builder.append(entrada.causa)
        builder.append(", motivo: ")
        builder.append(entrada.motivo)
        builder.append(", fechaUltimoCambioEstado: ")
        builder.append(entrada.fechaUltimoCambioEstado)
        builder.append(", fechaSolicitud: ")
        builder.append(entrada.fechaSolicitud)
        builder.append(", codigoCliente: ")
        builder.append(entrada.codigoCliente)
        builder.append(", codigoError: ")
        builder.append(entrada.codigoError)
        builder.append(", error: ")
        builder.append(entrada.error)
        builder.append(", cia: ")
        builder.append(entrada.cia)
        builder.append(", numeroReferencia: ")
        builder.append(entrada.numeroReferencia)
        builder.append(", producto: ")
        builder.append(entrada.producto)
        builder.append(", ramo: ")
        builder.append(entrada.ramo)
        builder.append(", corta: ")
        builder.append(entrada.corta)
        builder.append(", mediana: ")
        builder.append(entrada.mediana)
        return builder.toString()
    }

    static String toString(CajamarUnderwrittingCaseManagementRequest entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("numref: ")
        builder.append(entrada.regScor?.numref)
        return builder.toString()
    }

    static String toString(AlptisGeneralData entrada) {
        StringBuilder builder = new StringBuilder()
        builder.append("candidateSTCode: ")
        builder.append(entrada.candidateSTCode)
        builder.append(", name: ")
        builder.append(entrada.name)
        builder.append(", surname: ")
        builder.append(entrada.surname)
        builder.append(", birthDate: ")
        builder.append(entrada.birthDate)
        builder.append(", fiscalIdentificationNumber: ")
        builder.append(entrada.fiscalIdentificationNumber)
        builder.append(", gender: ")
        builder.append(entrada.gender)
        builder.append(", dossierCode: ")
        builder.append(entrada.dossierCode)
        builder.append(", productName: ")
        builder.append(entrada.productName)
        builder.append(", type: ")
        builder.append(entrada.type)
        builder.append(", sumAssured: ")
        builder.append(entrada.sumAssured)
        builder.append(", requestNumber: ")
        builder.append(entrada.requestNumber)
        builder.append(", requestDate: ")
        builder.append(entrada.requestDate)
        return builder.toString()
    }

}
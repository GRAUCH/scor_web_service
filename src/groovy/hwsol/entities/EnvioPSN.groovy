package hwsol.entities

import com.scortelemed.schemas.psn.ConsultaExpedienteRequest
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest


class EnvioPSN extends Envio{

	ConsultaExpedienteRequest expediente
	ResultadoReconocimientoMedicoRequest reconocimiento

    EnvioPSN() {
		super()
	}

}

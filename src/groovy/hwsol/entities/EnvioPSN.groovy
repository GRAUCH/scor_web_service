package hwsol.entities

import com.scortelemed.Envio
import com.scortelemed.schemas.psn.ConsultaExpedienteRequest
import com.scortelemed.schemas.psn.ResultadoReconocimientoMedicoRequest


class EnvioPSN extends Envio implements EnvioCompany{

	ConsultaExpedienteRequest expediente
	ResultadoReconocimientoMedicoRequest reconocimiento
	String estado
	String respuesta

    EnvioPSN() {
		super()
	}

	void set(Envio antiguo){
		if(antiguo != null) {
			this.fecha = antiguo.getFecha()
			this.cia = antiguo.getCia()
			this.identificador = antiguo.getIdentificador()
			this.info = antiguo.getInfo()
		}
	}

}

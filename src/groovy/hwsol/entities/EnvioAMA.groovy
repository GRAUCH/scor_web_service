package hwsol.entities

import com.scortelemed.Envio
import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest

class EnvioAMA extends Envio implements EnvioCompany{

	private ConsultaExpedienteRequest expediente
	private ResultadoSiniestroRequest siniestro
	private String estado
	private String respuesta

	EnvioAMA() {
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

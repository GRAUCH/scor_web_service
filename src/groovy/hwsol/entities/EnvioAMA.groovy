package hwsol.entities

import com.scortelemed.schemas.ama.ResultadoSiniestroRequest
import com.scortelemed.schemas.ama.ConsultaExpedienteRequest

class EnvioAMA extends Envio{

	ConsultaExpedienteRequest expediente
	ResultadoSiniestroRequest siniestro
	String estado
	String respuesta

	EnvioAMA() {
		super()
	}

}

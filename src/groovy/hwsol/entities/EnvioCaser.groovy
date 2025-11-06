package hwsol.entities

import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest
import hwsol.entities.parser.RegistrarEventoSCOR

class EnvioCaser extends Envio{

    RegistrarEventoSCOR eventoSCOR
    ResultadoReconocimientoMedicoRequest resultado
	
	EnvioCaser() {
		super()
	}
}

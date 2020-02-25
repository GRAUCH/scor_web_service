package hwsol.entities

import com.scortelemed.Envio
import com.scortelemed.schemas.caser.ResultadoReconocimientoMedicoRequest

class EnvioCaser extends Envio implements EnvioCompany{

    RegistrarEventoSCOR eventoSCOR
    ResultadoReconocimientoMedicoRequest resultado
	
	EnvioCaser() {
		super()
	}
    void set(Envio antiguo){
        if(antiguo != null) {
            this.fecha = antiguo.getFecha()
            this.cia = antiguo.getCia()
            this.identificador = antiguo.getIdentificador()
        }
    }
}

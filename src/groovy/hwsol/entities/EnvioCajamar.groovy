package hwsol.entities

import com.scortelemed.Envio

class EnvioCajamar extends Envio implements EnvioCompany{
	
    String causa
	String motivo
	Date fechaUltimoCambioEstado
	Date fechaSolicitud
	String codigoCliente
	String codigoError
	String error
	String cia
	String numeroReferencia
	String producto
	String ramo
	boolean corta
	boolean mediana

	EnvioCajamar() {
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

package hwsol.entities

import com.scortelemed.Envio

class EnvioAlptis extends Envio implements EnvioCompany{
	
	String candidateSTCode
	String name
	String surname
	Date birthDate
	String fiscalIdentificationNumber
	String gender
	String dossierCode
	String productName
	String type
	String sumAssured
	String requestNumber
	Date requestDate

	EnvioAlptis() {
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

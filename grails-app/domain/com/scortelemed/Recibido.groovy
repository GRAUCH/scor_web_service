package com.scortelemed

import java.util.Date;

class Recibido {

	Date fecha
	String cia
	String identificador
	String info
	String operacion

	static constraints = {
		fecha(nullable:false)
		cia(nullable:false)
		identificador(nullable:false)
		info(nullable:false)
		operacion(nullable:false)
	}
	
}

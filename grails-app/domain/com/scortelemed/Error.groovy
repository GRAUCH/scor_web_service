package com.scortelemed

import java.util.Date;

class Error {

	Date fecha
	String cia
	String identificador
	String info
	String operacion
	String error

	static constraints = {
		fecha(nullable:false)
		cia(nullable:false)
		identificador(nullable:false)
		info(nullable:false)
		error(nullable:false)
	}
	
}

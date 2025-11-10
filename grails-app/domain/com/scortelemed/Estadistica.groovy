package com.scortelemed
class Estadistica {
	String clave
	String value
	Date fecha_alta

	Operacion operacion
	
	Request request

	static mapping = {
		value type: 'text'
	}
	
	static constraints = {
		clave(blank:false)
		value(blank:false)
		fecha_alta(nullable:true)
	}
	
	def beforeInsert = {
		fecha_alta = new Date()
	}
	
	public String toString() {
		return clave
	}
	
	
}
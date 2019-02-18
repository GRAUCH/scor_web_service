package com.scortelemed
class Aviso {

	String nombre
	
	boolean activo
	Date fecha_alta
	Date fecha_modificacion
	
	Plantilla plantilla
	
	Operacion operacion
	
	static hasMany = [destinatarios:Destinatario]
	
	static constraints = {
		nombre(blank: false, unique: true)
		fecha_alta(nullable:true)
		fecha_modificacion(nullable:true)
	}
	
	def beforeInsert = {
		fecha_alta = new Date()
	}
	def beforeUpdate = {
		fecha_modificacion = new Date()
	}
	
	public String toString() {
		return nombre;
	}
}
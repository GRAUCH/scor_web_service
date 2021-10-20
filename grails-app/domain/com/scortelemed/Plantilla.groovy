package com.scortelemed
class Plantilla {
	
	String nombre
	String subject
	String body
	Date fecha_alta
	Date fecha_modificacion
	
	static hasMany = [avisos: Aviso]

	static mapping = {
		body sqlType: 'longText'
	}
		
    static constraints = {
    	nombre(blank: false, unique: true)
    	subject(blank:false)
		body(blank:false)
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

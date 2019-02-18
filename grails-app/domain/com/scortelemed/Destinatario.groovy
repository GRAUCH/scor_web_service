package com.scortelemed
class Destinatario {

	String email
	String nombre
	String apellidos
	boolean activo
	
	Date fecha_alta
	Date fecha_modificacion
	
	Company company
	
	static hasMany = [avisos:Aviso]
	              	
	static belongsTo = Aviso
		
    static constraints = {
		email(email:true)
    	nombre(nullable:true)
    	apellidos(nullable:true)
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
		if(nombre && apellidos) return nombre + " " + apellidos + " [" + email + "]"
		else return email
	}
}

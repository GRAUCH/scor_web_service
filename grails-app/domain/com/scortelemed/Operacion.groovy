package com.scortelemed
class Operacion {

	String clave
	boolean activo
	
	Date fecha_alta
	Date fecha_modificacion
	
	String descripcion
	
	String usuario_externo
	String pass_externo
	
	static hasMany = [avisos:Aviso, estadisticas:Estadistica, datowebservice:Datowebservice]
	
	Webservice webservice
	
	static constraints = {
		clave(nullable: false, blank:false)
		fecha_alta(nullable:true)
		fecha_modificacion(nullable:true)
	}
	
	def beforeInsert = {
		fecha_alta = new Date()
	}
	
	def beforeUpdate = {
		fecha_modificacion = new Date()
	}
	
	 String toString() {
		return clave
	}
}
package com.scortelemed
class Webservice {
	String clave
	String descripcion
	boolean activo
	
	String usuario_externo
	String pass_externo

	static hasMany = [usuarios: Person, operaciones: Operacion]
		
	public String toString() {
		return clave;
	}
	
	static constraints = {
        clave(nullable: false, blank:false)
        descripcion(blank: true)
	}
}
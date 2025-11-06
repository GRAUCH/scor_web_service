package com.scortelemed
class Request {
	Date fecha_procesado
	String request
	
	Operacion operacion
	
	String claveProceso
	
	boolean descartado=false
	
	String errores

	byte[] fichero
	
	Company company
	
	Date fecha_creacion=new Date()
	
	static hasMany = [estadisticas:Estadistica]

	static constraints = {
		fecha_procesado(nullable:true)
		claveProceso(nullable:true)
		fichero(nullable:true)
		errores(nullable:true)
	}
	
	static mapping = {
		request type:'text'
		fichero sqlType: "blob"
		errores type: "text"
	 }

	def beforeInsert = {
		def ran = new Random()
		def alfa = "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		def clave = operacion.clave + "#" + this.fecha_creacion.toString() + alfa[ran.nextInt(alfa.size())] + ran.nextInt(99999+1) + alfa[ran.nextInt(alfa.size())] + ran.nextInt(99999+1) + alfa[ran.nextInt(alfa.size())]

		this.claveProceso = clave.trim()		
	}
	
	public String toString() {
		return claveProceso
	}
	
}
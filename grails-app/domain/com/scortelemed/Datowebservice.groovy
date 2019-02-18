package com.scortelemed
class Datowebservice {

	String nombre
	String tipo

	Operacion operacion
	
    static constraints = {
    	nombre(blank:false, unique:true)
    }
	
	public String toString() {
		return nombre + " [ " + tipo + " ]";
	}	
}
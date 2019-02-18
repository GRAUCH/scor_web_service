package com.scortelemed

import com.ws.enumeration.UnidadOrganizativa;

class Company {
	
	String id
	String nombre
	String codigoSt
	String userCrm
	String passCrm
	String host
	String domain
	String orgName
	boolean generationAutomatic	
	boolean ipControl
	
	UnidadOrganizativa ou
	
	static hasMany = [destinatarios: Destinatario, usuarios: Person, ips: Ipcontrol, requests: Request]
	
    static constraints = {
    	nombre(blank:false, unique:true)
    	codigoSt(blank:false, unique:true)
		ou(blank: false, nullable:false)
    }
	
	public String toString() {
	    return nombre;
	}	
}
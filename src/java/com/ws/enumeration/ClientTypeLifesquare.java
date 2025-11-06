package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ClientType")
public enum ClientTypeLifesquare {
	N ("N"),
	V ("V");
	
	private final String nombre; 
	
	ClientTypeLifesquare(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

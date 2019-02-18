package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ClientType")
public enum ClientType {
	E ("E"),
	V ("V"),
	N ("N");
	
	private final String nombre; 
	
	ClientType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

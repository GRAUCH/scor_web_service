package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "StatusType")
public enum StatusType {
	ok("ok"),
	error("error");
	
	private final String nombre; 
	
	StatusType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

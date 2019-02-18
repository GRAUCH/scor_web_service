package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "StatusType")
public enum AlptisfStatusType {
	ok("ok"),
	error("error");
	
	private final String nombre; 
	
	AlptisfStatusType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

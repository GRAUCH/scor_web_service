package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SexType")
public enum SexType {
	H ("H"),
	M ("M");

	private final String nombre; 
	
	SexType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

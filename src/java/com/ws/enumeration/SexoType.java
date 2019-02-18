package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SexoType")
public enum SexoType {
	H ("H"),
	M ("M");

	private final String nombre; 
	
	SexoType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

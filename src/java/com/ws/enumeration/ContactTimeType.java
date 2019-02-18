package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ContactTimeType")
public enum ContactTimeType {
	M ("M"),
	C ("C"),
	T ("T"),
	N ("N");

	private final String nombre; 
	
	ContactTimeType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

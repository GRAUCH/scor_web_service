package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ContactTimeType")
public enum ContactTimeLifesquareType {
	M ("M"),
	L ("L"),
	A ("A"),
	N ("N");

	private final String nombre; 
	
	ContactTimeLifesquareType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

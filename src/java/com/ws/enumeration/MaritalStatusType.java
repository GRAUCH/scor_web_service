package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MaritalStatusType")
public enum MaritalStatusType {
	C ("C"),
	D ("D"),
	P ("P"),
	S ("S"),
	V ("V"),
	O ("O"),
	E ("E");

	private final String nombre; 
	
	MaritalStatusType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EstadoCivilType")
public enum EstadoCivilType {
	C ("C"),
	D ("D"),
	P ("P"),
	S ("S"),
	V ("V"),
	O ("O"),
	E ("E");

	private final String nombre; 
	
	EstadoCivilType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

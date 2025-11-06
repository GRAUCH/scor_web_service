package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MovimientoType")
public enum MovimientoType {
	A ("A"),
	B ("B");
	
	private final String nombre; 
	
	MovimientoType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

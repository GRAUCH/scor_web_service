package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HoraContactoType")
public enum HoraContactoType {
	M ("M"),
	C ("C"),
	T ("T"),
	N ("N");

	private final String nombre; 
	
	HoraContactoType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

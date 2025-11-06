package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "RecordType")
public enum RecordType {
	A ("A"),
	B ("B");
	
	private final String nombre; 
	
	RecordType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

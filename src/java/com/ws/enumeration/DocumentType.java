package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DocumentType")
@XmlEnum
public enum DocumentType {
    @XmlEnumValue("2")
    TWO(2),

    @XmlEnumValue("3")
    THREE(3);

    private final Integer nombre; 

    DocumentType(Integer nombre) { 
		this.nombre = nombre; 
	}
	
    public Integer getNombre() { 
    	return nombre;
    }
}

/**
 * TipoDocumentoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TipoReconocimientoMedicoType")
@XmlEnum
public enum TipoReconocimientoMedicoType {
    @XmlEnumValue("02")
    TWO("02"),

    @XmlEnumValue("05")
    FIVE("05"),
    
    @XmlEnumValue("06")
    SIX("06"),
    
    @XmlEnumValue("12")
    TWLEVE("12"),
    
    @XmlEnumValue("15")
    FIFTEEN("15"),
    
    @XmlEnumValue("16")
    SIXTEEN("16"),
    
    @XmlEnumValue("19")
    NINETEEN("19");
    
    private final String nombre; 

    TipoReconocimientoMedicoType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
}

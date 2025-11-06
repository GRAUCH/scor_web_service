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

@XmlType(name = "TipoDocumentoType")
@XmlEnum
public enum TipoDocumentoType {
    @XmlEnumValue("2")
    TWO(2),

    @XmlEnumValue("3")
    THREE(3);

    private final Integer nombre; 

    TipoDocumentoType(Integer nombre) { 
		this.nombre = nombre; 
	}
	
    public Integer getNombre() { 
    	return nombre;
    }
}

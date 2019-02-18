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

@XmlType(name = "TipoDictamenType")
@XmlEnum
public enum TipoDictamenType {
    @XmlEnumValue("1")
    ONE(1),

    @XmlEnumValue("2")
    TWO(2);

    private final Integer nombre; 

    TipoDictamenType(Integer nombre) { 
		this.nombre = nombre; 
	}
	
    public Integer getNombre() { 
    	return nombre;
    }
}

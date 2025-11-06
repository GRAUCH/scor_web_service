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

@XmlType(name = "TeleType")
@XmlEnum
public enum TeleType {
    @XmlEnumValue("1")
    ONE(1),

    @XmlEnumValue("0")
    ZERO(0);

    private final Integer type;

	TeleType(Integer type) { 
		this.type = type; 
	}
	
    public Integer getType() { 
    	return type;
    }
}

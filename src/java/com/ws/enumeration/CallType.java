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

@XmlType(name = "CallType")
@XmlEnum
public enum CallType {
    @XmlEnumValue("1")
    ONE(1),

    @XmlEnumValue("2")
    TWO(0),
    
    @XmlEnumValue("3")
    THREE(0);

    private final Integer call;

	CallType(Integer call) { 
		this.call = call; 
	}
	
    public Integer getCall() { 
    	return call;
    }
}

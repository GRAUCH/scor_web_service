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

@XmlType(name = "TipoGarantiaType")
@XmlEnum
public enum TipoGarantiaType {
    @XmlEnumValue("Fallecimiento")
    Fallecimiento("Fallecimiento"),

    @XmlEnumValue("Fallecimiento accidente")
    Fallecimiento_accidente("Fallecimiento accidente"),
    
    @XmlEnumValue("Enfermedades graves")
    Enfermedades_graves("Enfermedades graves"),
    
    @XmlEnumValue("Invalidez permanente absoluta")
    Invalidez_permanente_absoluta("Invalidez permanente absoluta"),
    
    @XmlEnumValue("Incapacidad 30")
    Incapacidad_30("Incapacidad 30"),
    
    @XmlEnumValue("Incapacidad 30-90")
    Incapacidad_30_90("Incapacidad 30-90"),
    
    @XmlEnumValue("Incapacidad 90")
    Incapacidad_90("Incapacidad 90"),
    
    @XmlEnumValue("Dependencia")
    Dependencia("Dependencia");

    private final String nombre; 

    TipoGarantiaType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
}

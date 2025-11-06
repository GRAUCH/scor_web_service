package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DataTypeType")
@XmlEnum
public enum DataTypeType {
    @XmlEnumValue("0")
    ZERO(0),
    
    @XmlEnumValue("1")
    ONE(1),
    
    @XmlEnumValue("2")
    TWO(2),
    
    @XmlEnumValue("3")
    THREE(3),
    
    @XmlEnumValue("4")
    FOUR(4),
    
    @XmlEnumValue("5")
    FIVE(5),

    @XmlEnumValue("6")
    SIX(6);

    private final Integer nombre; 

    DataTypeType(Integer nombre) { 
		this.nombre = nombre; 
	}
	
    public Integer getNombre() { 
    	return nombre;
    }
}

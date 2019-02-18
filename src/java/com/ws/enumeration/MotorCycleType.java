package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MotorCycleType")
@XmlEnum
public enum MotorCycleType {
    @XmlEnumValue("1")
    ONE("1"),

    @XmlEnumValue("0")
    ZERO("0");

    private final String moto;

	MotorCycleType(String moto) { 
		this.moto = moto; 
	}
	
    public String getMoto() { 
    	return moto;
    }
}

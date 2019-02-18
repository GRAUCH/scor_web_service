package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ContactTimeCajamarType")
@XmlEnum
public enum ContactTimeCajamarType {
    @XmlEnumValue("1")
    ONE("1"),

    @XmlEnumValue("2")
    TWO("2"),
    
    @XmlEnumValue("3")
    THREE("3"),
    
    @XmlEnumValue("4")
    FOUR("4");

    private final String hour;

	ContactTimeCajamarType(String hour) { 
		this.hour = hour; 
	}
	
    public String getHour() { 
    	return hour;
    }
}

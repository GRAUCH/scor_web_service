package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnumValue;

public enum CoberturasCajamarEnum {
    @XmlEnumValue("201")
    FALLECIMIENTO("201"),
      
    @XmlEnumValue("301")
    CANECER_MAMA("301"),
    
    @XmlEnumValue("302")
    INFARTO("302"),
    
    @XmlEnumValue("401")
    ACCIDENTE("401"),
    
    @XmlEnumValue("702")
    ABSOLUTA("702"),
    
    @XmlEnumValue("703")
    DOBLE_ABSOLUTA("703"),
    
    @XmlEnumValue("10001")
    MEDICA("10001"),
    
    @XmlEnumValue("10002")
    TESTAMENTO("10002"),
    
    @XmlEnumValue("21001")
    CAPITAL_FALLECIMIENTO("21001"),
    
    @XmlEnumValue("21003")
    CAPITAL_BSOLUTA("21003"),
    
    @XmlEnumValue("21007")
    FALLECIMIENTO_ACCIDENTE("21007"),
    
    @XmlEnumValue("21009")
    INVALIDEZ("21009");
	
    private final String valor; 

    CoberturasCajamarEnum(String valor) { 
		this.valor = valor; 
	}
	
    public String getValor() { 
    	return valor;
    }	

}

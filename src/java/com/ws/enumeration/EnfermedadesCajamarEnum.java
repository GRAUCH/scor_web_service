package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnumValue;

public enum EnfermedadesCajamarEnum {
    @XmlEnumValue("00001")
    CORAZON("00001"),
      
    @XmlEnumValue("00002")
    CEREBROVASCULARES("00002"),
    
    @XmlEnumValue("00003")
    HIPERTENSION("00003"),
    
    @XmlEnumValue("00004")
    DIABETES("00004"),
    
    @XmlEnumValue("00005")
    ENFERMEDADES_HIGADO("00005"),
    
    @XmlEnumValue("00006")
    ENFERMEDADES_TRANSMISION("00006"),
    
    @XmlEnumValue("00007")
    SIDA("00007"),
    
    @XmlEnumValue("00008")
    CANCER("00008"),
    
    @XmlEnumValue("00009")
    RETINOPATIA_MACULOPATIA("00009"),
    
    @XmlEnumValue("99999")
    NO_PADECE("99999");
	
    private final String valor; 

    EnfermedadesCajamarEnum(String valor) { 
		this.valor = valor; 
	}
	
    public String getValor() { 
    	return valor;
    }	

}

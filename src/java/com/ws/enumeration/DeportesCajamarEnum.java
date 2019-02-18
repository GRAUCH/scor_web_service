package com.ws.enumeration;

import javax.xml.bind.annotation.XmlEnumValue;

public enum DeportesCajamarEnum {
    @XmlEnumValue("00001")
    SUBACUATICAS("00001"),
      
    @XmlEnumValue("00002")
    ALA_DELTA("00002"),
    
    @XmlEnumValue("00003")
    ALPINISMO("00003"),
    
    @XmlEnumValue("00004")
    AVIONETAS("00004"),
    
    @XmlEnumValue("00005")
    BOXEO("00005"),
    
    @XmlEnumValue("00006")
    CAZA("00006"),
    
    @XmlEnumValue("00007")
    MOTOR("00007"),
    
    @XmlEnumValue("00008")
    AEREOS("00008"),
    
    @XmlEnumValue("00009")
    ESPELEOLOGIA("00009"),
    
    @XmlEnumValue("00010")
    GLOBO("00010"),
    
    @XmlEnumValue("00011")
    INMERSIONES("00011"),
    
    @XmlEnumValue("00012")
    INTERVENCION("00012"),
    
    @XmlEnumValue("00013")
    KARATE("00013"),
    
    @XmlEnumValue("00014")
    MOTOCICLISMO("00014"),
    
    @XmlEnumValue("00015")
    MOTONAUTICA("00015"),
    
    @XmlEnumValue("00016")
    NAVEGACION("00016"),
    
    @XmlEnumValue("00017")
    PARACAIDISMO("00017"),
    
    @XmlEnumValue("00018")
    PARAPENTE("00018"),
    
    @XmlEnumValue("00019")
    PUENTING("00019"),
    
    @XmlEnumValue("00020")
    RAFTING("00020"),
    
    @XmlEnumValue("00021")
    ULTRALIGEROS("00021");
    

    private final String valor; 

    DeportesCajamarEnum(String valor) { 
		this.valor = valor; 
	}
	
    public String getValor() { 
    	return valor;
    }	

}

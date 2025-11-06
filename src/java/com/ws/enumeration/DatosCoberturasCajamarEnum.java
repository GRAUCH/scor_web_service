package com.ws.enumeration;

public enum DatosCoberturasCajamarEnum {
	cobern("cobern",50),
	ccober("ccober",14),
	dcober("dcober",552);
	
	private final String valor;
	private final int tamanio;
	
	DatosCoberturasCajamarEnum(String value,int tamanio) { 
		this.valor = value; 
		this.tamanio = tamanio;
	}
    
    public String getValor() { 
    	return valor; 
    }

    public int getTamanio() { 
    	return tamanio;
    }
	

}

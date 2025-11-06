package com.ws.enumeration;

public enum CoberturasEnum {
	benefit_name("benefit_name",50),
	benefit_capital("benefit_capital",14),
	blancos("blancos",552);
	
	private final String valor;
	private final int tamanio;
	
	CoberturasEnum(String value,int tamanio) { 
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

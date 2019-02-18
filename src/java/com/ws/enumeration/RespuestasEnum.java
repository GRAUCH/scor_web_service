package com.ws.enumeration;

public enum RespuestasEnum {
	data_type("data_type",3),
	question_code("question_code",8),
	answer("answer",200),
	blancos("blancos",407);
	
	private final String valor;
	private final int tamanio;
	
	RespuestasEnum(String value,int tamanio) { 
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

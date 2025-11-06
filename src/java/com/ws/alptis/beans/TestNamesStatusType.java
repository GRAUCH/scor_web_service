package com.ws.alptis.beans;

public enum TestNamesStatusType {
	Finish ("Finish"),
	PendingFiles ("PendingFiles");
	
	private final String nombre; 
	
	TestNamesStatusType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

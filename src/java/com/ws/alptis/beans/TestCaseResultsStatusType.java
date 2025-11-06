package com.ws.alptis.beans;

public enum TestCaseResultsStatusType {
	M ("M"),
	C ("C"),
	T ("T"),
	N ("N");

	private final String nombre; 
	
	TestCaseResultsStatusType(String nombre) { 
		this.nombre = nombre; 
	}
	
    public String getNombre() { 
    	return nombre;
    }
	

}

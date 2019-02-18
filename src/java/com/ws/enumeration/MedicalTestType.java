package com.ws.enumeration;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MedicalTestType")
public enum MedicalTestType {
	B ("B"),
	C ("C"),
	D ("D"),
	E ("E"),
	F ("F");

	private final String medicalTest; 
	
	MedicalTestType(String medicalTest) { 
		this.medicalTest = medicalTest; 
	}
	
    public String getMedicalTest() { 
    	return medicalTest;
    }
	

}

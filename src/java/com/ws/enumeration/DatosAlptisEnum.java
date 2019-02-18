package com.ws.enumeration;

public enum DatosAlptisEnum {
	product("product",10),
	document_type("document_type",15),
	policy_number("policy_number",15),
	dateRequest("dateRequest",8),	
	passs_candidate("passs_candidate",8),
	state_request("state_request",2),
	name("name",25),
	surname("surname",40),
	address("address",150),
	postal_code("postal_code",5),
	town("town",40),
	province("province",25),
	country("country",2),
	phone1("phone1",30),
	phone2("phone2",30),
	phone3("phone3",30),
	email("email",50),
	birth_date("birth_date",8),
	age_present("age_present",3),
	birthplace("birthplace",40),
	fiscal_identification_number("fiscal_identification_number",16),
	agent("agent",8),
	name_surname_agent("name_surname_agent",70),
	phone("phone",10),
	email_agent("email_agent",50),
	gender("gender",1),
	marital_status("marital_status",1),
	client_type("client_type",1),
	contact_time("contact_time",1),
	code_form("code_form",3),
	reference_number("reference_number",4),
	duration("duration",3),
	comments("comments",100);
	
	private final String valor;
	private final int tamanio;
	
	DatosAlptisEnum(String value,int tamanio) { 
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

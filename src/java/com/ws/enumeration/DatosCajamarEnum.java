package com.ws.enumeration;

public enum DatosCajamarEnum {
	yprodu("yprodu",5),
	numref("numref",9),
	fechso("fechso",8),
	nmbase("nmbase",20),
	ap1ase("ap1ase",20),
	ap2ase("ap2ase",20),
	wdomcc("wdomcc",60),
	ydpost("ydpost",5),
	wpobcl("wpobcl",30),
	wprocl("wprocl",30),
	tefcli("tefcli",9),
	movcli("movcli",9),
	dniase("dniase",9),
	ysexcl("ysexcl",1),
	aanccl("aanccl",4),
	mmnccl("mmnccl",2),
	ddnccl("ddnccl",2),
	obs382("obs382",382),
	tlcom("tlcom",1),
	tlabr("tlabr",1),
	ytipo("ytipo",1),
	cia("cia",1),
	yramex("yramex",2),	
	ofsucu("ofsucu",4),
	telccc("telccc",9),
	sucnom("sucnom",30),
	wpers("wpers",10),	
	zprumd("zprumd",3),
	profri("profri",5),
	moto("moto",1),
	franho("franho",1),
	franhi("franhi",5),
	franhf("franhf",5),
	diastl("diastl",7),
	xsegvi("xsegvi",1),
	xbajam("xbajam",1),
	xfisic("xfisic",1),
	xhospi("xhospi",1),
	xdrosn("xdrosn",1),
	xdepre("xdepre",1),
	estatu("estatu",3),
	pesokg("pesokg",3),
	xfumsn("xfumsn",1),
	xpsida("xpsida",1),
	xesvid("xesvid",1);
	
	private final String valor;
	private final int tamanio;
	
	DatosCajamarEnum(String value,int tamanio) { 
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

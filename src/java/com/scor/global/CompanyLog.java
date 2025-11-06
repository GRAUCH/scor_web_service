package com.scor.global;

import java.util.List;

import com.scortelemed.Envio;
import com.scortelemed.Recibido;
import com.ws.enumeration.UnidadOrganizativa;

public class CompanyLog {
	
	String logo;
	List<Recibido> recibidos;
	List<Envio> enviados;
	String name;
	String id;
	UnidadOrganizativa ou; 
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}	
	public List<Recibido> getRecibidos() {
		return recibidos;
	}
	public void setRecibidos(List<Recibido> recibidos) {
		this.recibidos = recibidos;
	}
	public List<Envio> getEnviados() {
		return enviados;
	}
	public void setEnviados(List<Envio> enviados) {
		this.enviados = enviados;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UnidadOrganizativa getOu() {
		return ou;
	}
	public void setOu(UnidadOrganizativa ou) {
		this.ou = ou;
	}
	
}

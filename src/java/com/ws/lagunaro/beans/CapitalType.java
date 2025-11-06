package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapitalType" , propOrder = {
    "codigo",
    "descripcion",
    "capital"
})

public class CapitalType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private String capital;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
}

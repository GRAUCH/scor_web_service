package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgenteType", propOrder = {
	"agente",
    "descripcion",
    "telefono"
})
//@XmlRootElement(name = "agent",namespace="http://www.scortelemed.com/schemas/afiEsca")
public class AgenteType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Size(min = 2, max = 14)
	private String agente;
	private String descripcion;
	@XmlElement(nillable=true)
	private String telefono;
	
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

		
}

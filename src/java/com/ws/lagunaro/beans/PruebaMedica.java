package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pruebaMedica" , propOrder = {
    "descripcion",
    "codigo_prueba_medica"
})

public class PruebaMedica implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(nillable=true)
	private String descripcion;
	private String codigo_prueba_medica;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo_prueba_medica() {
		return codigo_prueba_medica;
	}
	public void setCodigo_prueba_medica(String codigo_prueba_medica) {
		this.codigo_prueba_medica = codigo_prueba_medica;
	}

}

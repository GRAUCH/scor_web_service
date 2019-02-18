package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "prueba_Medica", propOrder = { 
		"candidato_aporta_pruebas",
		"codigo_prueba_medica",
		"descripcion_prueba_medica",
		"fecha_realizacion" })

public class Prueba_Medica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="candidato_aporta_pruebas")
	private Boolean candidato_aporta_pruebas;
	@XmlAttribute(name="codigo_prueba_medica")
	private String codigo_prueba_medica;
	@XmlAttribute(name="descripcion_prueba_medica")
	private String descripcion_prueba_medica;
	@XmlAttribute(name="fecha_realizacion")
	private Date fecha_realizacion;
	
	public Boolean getCandidato_aporta_pruebas() {
		return candidato_aporta_pruebas;
	}
	public void setCandidato_aporta_pruebas(Boolean candidato_aporta_pruebas) {
		this.candidato_aporta_pruebas = candidato_aporta_pruebas;
	}
	public String getCodigo_prueba_medica() {
		return codigo_prueba_medica;
	}
	public void setCodigo_prueba_medica(String codigo_prueba_medica) {
		this.codigo_prueba_medica = codigo_prueba_medica;
	}
	public String getDescripcion_prueba_medica() {
		return descripcion_prueba_medica;
	}
	public void setDescripcion_prueba_medica(String descripcion_prueba_medica) {
		this.descripcion_prueba_medica = descripcion_prueba_medica;
	}
	public Date getFecha_realizacion() {
		return fecha_realizacion;
	}	
	public void setFecha_realizacion(Date fecha_realizacion) {
		this.fecha_realizacion = fecha_realizacion;
	}
	


}

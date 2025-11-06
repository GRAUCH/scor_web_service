package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.TipoDictamenType;
import com.ws.enumeration.TipoGarantiaType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "garantia", propOrder = {
    "codigo",
    "nombre_cobertura",
    "valoracion"
})
public class Garantia implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigo;
	private TipoGarantiaType nombre_cobertura;
	private TipoDictamenType valoracion;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public TipoGarantiaType getNombre_cobertura() {
		return nombre_cobertura;
	}
	public void setNombre_cobertura(TipoGarantiaType nombre_cobertura) {
		this.nombre_cobertura = nombre_cobertura;
	}
	public TipoDictamenType getValoracion() {
		return valoracion;
	}
	public void setValoracion(TipoDictamenType valoracion) {
		this.valoracion = valoracion;
	}
	
	
}

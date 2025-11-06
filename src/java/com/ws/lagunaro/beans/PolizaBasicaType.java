package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.TipoDocumentoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolizaBasicaGroup")
@XmlTransient
public class PolizaBasicaType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TipoDocumentoType tipo_documento;
	private String cod_poliza;
	private String certificado;
	private String movimiento;
	
	public TipoDocumentoType getTipo_documento() {
		return tipo_documento;
	}
	public void setTipo_documento(TipoDocumentoType tipo_documento) {
		this.tipo_documento = tipo_documento;
	}
	public String getCod_poliza() {
		return cod_poliza;
	}
	public void setCod_poliza(String cod_poliza) {
		this.cod_poliza = cod_poliza;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	

	
}


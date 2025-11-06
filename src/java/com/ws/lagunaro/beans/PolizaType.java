package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolizaType", propOrder = {
    "producto",
    "tipo_documento",
    "cod_poliza",
    "certificado",
    "movimiento",
    "fecha_efecto",
    "texto_informativo"
})
public class PolizaType extends PolizaBasicaType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String producto;
	private Date fecha_efecto;
	@XmlElement(nillable=true)
	private String texto_informativo;
	
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Date getFecha_efecto() {
		return fecha_efecto;
	}
	public void setFecha_efecto(Date fecha_efecto) {
		this.fecha_efecto = fecha_efecto;
	}
	public String getTexto_informativo() {
		return texto_informativo;
	}
	public void setTexto_informativo(String texto_informativo) {
		this.texto_informativo = texto_informativo;
	}
	
}

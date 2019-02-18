package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.MovimientoType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosEnvioType", propOrder = {
    "movimiento",
    "fecha_envio",
    "observaciones"
})
public class DatosEnvioType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MovimientoType movimiento;
	private Date fecha_envio;
	@XmlElement(nillable=true)
	private String observaciones;
	
	public MovimientoType getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(MovimientoType movimiento) {
		this.movimiento = movimiento;
	}
	public Date getFecha_envio() {
		return fecha_envio;
	}
	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}

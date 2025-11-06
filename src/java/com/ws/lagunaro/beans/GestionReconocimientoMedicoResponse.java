package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.StatusType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GestionReconocimientoMedicoResponse", propOrder = {
	"statusType",
    "fecha",
    "mensaje"
})
public class GestionReconocimientoMedicoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="status")
	private StatusType statusType;
	private Date fecha;
	@XmlElement(nillable=true)
	private String mensaje;
	
	public StatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}

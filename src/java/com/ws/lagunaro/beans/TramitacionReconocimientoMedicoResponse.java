package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.StatusType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramitacionReconocimientoMedicoResponse", propOrder = {
	"statusType",
    "fecha",
    "observaciones",
    "polizaBasicaGroup"
})
@XmlRootElement(name = "TramitacionReconocimientoMedicoResponse")
public class TramitacionReconocimientoMedicoResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="status")
	private StatusType statusType;
	private Date fecha;
	@XmlElement(nillable=true)
	private String observaciones;
	@XmlElement(name="poliza")
	private List<PolizaBasicaGroup> polizaBasicaGroup;
	
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public List<PolizaBasicaGroup> getPolizaBasicaGroup() {
		return polizaBasicaGroup;
	}
	public void setPolizaBasicaGroup(List<PolizaBasicaGroup> polizaBasicaGroup) {
		this.polizaBasicaGroup = polizaBasicaGroup;
	}
	

	
}

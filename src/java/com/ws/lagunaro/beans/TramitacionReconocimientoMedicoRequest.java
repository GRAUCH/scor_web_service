package com.ws.lagunaro.beans;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramitacionReconocimientoMedicoRequest", propOrder = {
    "fecha",
    "observaciones"
    
})
@XmlRootElement(name = "TramitacionReconocimientoMedicoRequest")
public class TramitacionReconocimientoMedicoRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fecha;
	@XmlElement(nillable=true)
	private String observaciones;
	
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
}

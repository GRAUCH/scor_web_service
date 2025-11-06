package com.ws.lagunaro.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
	
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolizaBasicaType", propOrder = {
    "tipo_documento",
    "cod_poliza",
    "certificado",
    "movimiento",
    "nif",
    "reconocimientoMedicoTramitacionType",
    "zip"
})
public class PolizaBasicaGroup extends PolizaBasicaType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nif;
	@XmlElement(nillable=true,name="reconocimiento_medico")
	private ReconocimientoMedicoTramitacionType reconocimientoMedicoTramitacionType;
	private String zip;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public ReconocimientoMedicoTramitacionType getReconocimientoMedicoTramitacionType() {
		return reconocimientoMedicoTramitacionType;
	}

	public void setReconocimientoMedicoTramitacionType(
			ReconocimientoMedicoTramitacionType reconocimientoMedicoTramitacionType) {
		this.reconocimientoMedicoTramitacionType = reconocimientoMedicoTramitacionType;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
	
}

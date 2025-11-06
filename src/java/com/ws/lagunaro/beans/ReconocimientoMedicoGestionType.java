package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.TipoReconocimientoMedicoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReconocimientoMedicoGestionType", propOrder = {
    "tipo_reconocimiento_medico",
    "prueba_medica"
})
public class ReconocimientoMedicoGestionType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(nillable=true)
	private TipoReconocimientoMedicoType tipo_reconocimiento_medico;
    protected List<PruebaMedica> prueba_medica;
	
	public TipoReconocimientoMedicoType getTipo_reconocimiento_medico() {
		return tipo_reconocimiento_medico;
	}
	public void setTipo_reconocimiento_medico(TipoReconocimientoMedicoType tipo_reconocimiento_medico) {
		this.tipo_reconocimiento_medico = tipo_reconocimiento_medico;
	}
	public List<PruebaMedica> getPrueba_medica() {
		return prueba_medica;
	}
	public void setPrueba_medica(List<PruebaMedica> prueba_medica) {
		this.prueba_medica = prueba_medica;
	}


	
}

package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReconocimientoMedicoTramitacionType", propOrder = {
	"tipo_reconocimiento_medico",
	"dictamenType",
    "prueba_medica"
})
public class ReconocimientoMedicoTramitacionType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(nillable=true,name="tipo_reconocimiento_medico")
	private String tipo_reconocimiento_medico;
	@XmlElement(name="prueba_medica")
	private List<Prueba_Medica> prueba_medica;
	@XmlElement(nillable=true,name="dictamen")
	private DictamenType dictamenType;

	public String getTipo_reconocimiento_medico() {
		return tipo_reconocimiento_medico;
	}

	public void setTipo_reconocimiento_medico(String tipo_reconocimiento_medico) {
		this.tipo_reconocimiento_medico = tipo_reconocimiento_medico;
	}

	public List<Prueba_Medica> getPrueba_medica() {
		return prueba_medica;
	}

	public void setPrueba_medica(List<Prueba_Medica> prueba_medica) {
		this.prueba_medica = prueba_medica;
	}

	public DictamenType getDictamenType() {
		return dictamenType;
	}

	public void setDictamenType(DictamenType dictamenType) {
		this.dictamenType = dictamenType;
	}
	
	
	
}

package com.ws.lagunaro.beans;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GestionReconocimientoMedicoRequest", propOrder = {
    "datos_envio",
    "poliza",
    "datos_capital",
    "asegurado",
    "agente",
    "reconocimiento_medico"
    
})
@XmlRootElement(name = "gestionReconocimientoMedicoRequest")
public class GestionReconocimientoMedicoRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DatosEnvioType datos_envio;
	private PolizaType poliza;
	@XmlElementWrapper
	@XmlElement(name="garantia")
    protected List<CapitalType> datos_capital;
	@XmlElement(nillable=true)
	private AseguradoType asegurado;
	@XmlElement(nillable=true)
	private AgenteType agente;
	@XmlElement(nillable=true)
	private ReconocimientoMedicoGestionType reconocimiento_medico;
	
	public DatosEnvioType getDatos_envio() {
		return datos_envio;
	}
	public void setDatos_envio(DatosEnvioType datos_envio) {
		this.datos_envio = datos_envio;
	}
	public PolizaType getPoliza() {
		return poliza;
	}
	public void setPoliza(PolizaType poliza) {
		this.poliza = poliza;
	}
	public List<CapitalType> getDatos_capital() {
		return datos_capital;
	}
	public void setDatos_capital(List<CapitalType> datos_capital) {
		this.datos_capital = datos_capital;
	}
	public AseguradoType getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(AseguradoType asegurado) {
		this.asegurado = asegurado;
	}
	public AgenteType getAgente() {
		return agente;
	}
	public void setAgente(AgenteType agente) {
		this.agente = agente;
	}
	public ReconocimientoMedicoGestionType getReconocimiento_medico() {
		return reconocimiento_medico;
	}
	public void setReconocimiento_medico(
			ReconocimientoMedicoGestionType reconocimiento_medico) {
		this.reconocimiento_medico = reconocimiento_medico;
	}


		
}

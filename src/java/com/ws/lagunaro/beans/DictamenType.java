package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DictamenType", propOrder = {
    "garantia"
})
public class DictamenType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Garantia> garantia;

	public List<Garantia> getGarantia() {
		return garantia;
	}

	public void setGarantia(List<Garantia> garantia) {
		this.garantia = garantia;
	}
	
	
}

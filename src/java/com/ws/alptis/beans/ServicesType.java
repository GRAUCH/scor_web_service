package com.ws.alptis.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicesType", propOrder = {
    "service_Pack",
    "serviceType"
})
public class ServicesType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(nillable=true)
	private String service_Pack;
	@XmlElement(nillable=true)
	private String serviceType;
	
	public String getService_Pack() {
		return service_Pack;
	}
	public void setService_Pack(String service_Pack) {
		this.service_Pack = service_Pack;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
}
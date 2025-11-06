package com.ws.lifesquare.beans;

import java.io.Serializable;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgentType", propOrder = {
	"agent",
    "description",
    "phone"
})
//@XmlRootElement(name = "agent",namespace="http://www.scortelemed.com/schemas/afiEsca")
public class AgentType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Size(min = 2, max = 14)
	private String agent;
	private String description;
	@XmlElement(nillable=true)
	private String phone;
	

	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

		
}

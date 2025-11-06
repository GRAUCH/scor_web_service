package com.ws.afiesca.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tuwCase", propOrder = {
	"policy_number",
    "reference_number",
    "zip"
})
public class TuwCase implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlElement(name="policy_number")
	private String policy_number;
	@XmlElement(name="reference_number")
	private String reference_number;
	@XmlElement(name="zip")
	private String zip;
	
	public String getPolicy_number() {
		return policy_number;
	}
	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}
	public String getReference_number() {
		return reference_number;
	}
	public void setReference_number(String reference_number) {
		this.reference_number = reference_number;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}

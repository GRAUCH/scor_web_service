package com.ws.alptis.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.DocumentType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasicPolicyGroup", propOrder = {
    "document_type",
    "policy_number",
    "sub_policy_number",
    "certificate",
    "suplement_number"
})
public class BasicPolicyGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DocumentType document_type;
	private String policy_number;
	private String sub_policy_number;
	private String certificate;
	private String suplement_number;
	
	public DocumentType getDocument_type() {
		return document_type;
	}
	public void setDocument_type(DocumentType document_type) {
		this.document_type = document_type;
	}
	public String getPolicy_number() {
		return policy_number;
	}
	public void setPolicy_number(String policy_number) {
		this.policy_number = policy_number;
	}
	public String getSub_policy_number() {
		return sub_policy_number;
	}
	public void setSub_policy_number(String sub_policy_number) {
		this.sub_policy_number = sub_policy_number;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getSuplement_number() {
		return suplement_number;
	}
	public void setSuplement_number(String suplement_number) {
		this.suplement_number = suplement_number;
	}
	
}


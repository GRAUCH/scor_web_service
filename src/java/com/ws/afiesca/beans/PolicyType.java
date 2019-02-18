package com.ws.afiesca.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.DocumentType;

	
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyType", propOrder = {
    "product",
    "document_type",
    "policy_number",
    "reference_number",
    "duration",
    "benefits"
})
public class PolicyType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String product;
	private DocumentType document_type;
	private String policy_number;
	private String reference_number;
	private Integer duration;
	@XmlElementWrapper
	@XmlElement(name="benefit")
    protected List<BenefitGroupType> benefits;
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
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
	public String getReference_number() {
		return reference_number;
	}
	public void setReference_number(String reference_number) {
		this.reference_number = reference_number;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public List<BenefitGroupType> getBenefits() {
		return benefits;
	}
	public void setBenefits(List<BenefitGroupType> benefits) {
		this.benefits = benefits;
	}
	
}

package com.ws.lifesquare.beans;

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
    "euw_code",
    "activate_profession",
    "activate_sport",
    "activate_oblivion",
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
	private String euw_code;
	private String activate_profession;
	private String activate_sport;
	private String activate_oblivion;
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
	public String getEuw_code() {
		return euw_code;
	}
	public void setEuw_code(String euw_code) {
		this.euw_code = euw_code;
	}
	public String getActivate_profession() {
		return activate_profession;
	}
	public void setActivate_profession(String activate_profession) {
		this.activate_profession = activate_profession;
	}
	public String getActivate_sport() {
		return activate_sport;
	}
	public void setActivate_sport(String activate_sport) {
		this.activate_sport = activate_sport;
	}
	public String getActivate_oblivion() {
		return activate_oblivion;
	}
	public void setActivate_oblivion(String activate_oblivion) {
		this.activate_oblivion = activate_oblivion;
	}
}

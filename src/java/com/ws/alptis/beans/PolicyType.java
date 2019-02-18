package com.ws.alptis.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyType", propOrder = {
    "product",
    "BasicPolicyGroup",
    "due_date",
    "benefits",
    "policyDuration"
})
public class PolicyType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String product;
	private BasicPolicyGroup BasicPolicyGroup;
	private Date due_date;
	@XmlElementWrapper
	@XmlElement(name="benefit")
    protected List<BenefitGroupType> benefits;
	private String policyDuration;
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public BasicPolicyGroup getBasicPolicyGroup() {
		return BasicPolicyGroup;
	}
	public void setBasicPolicyGroup(BasicPolicyGroup basicPolicyGroup) {
		BasicPolicyGroup = basicPolicyGroup;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public List<BenefitGroupType> getBenefits() {
		return benefits;
	}
	public void setBenefits(List<BenefitGroupType> benefits) {
		this.benefits = benefits;
	}
	public String getPolicyDuration() {
		return policyDuration;
	}
	public void setPolicyDuration(String policyDuration) {
		this.policyDuration = policyDuration;
	}

	
}

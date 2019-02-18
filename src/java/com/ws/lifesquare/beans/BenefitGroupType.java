package com.ws.lifesquare.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BenefitGroupType" , propOrder = {
    "benefit_name",
    "benefit_capital",
    "benefit_code"
})

public class BenefitGroupType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String benefit_name;
	private Float benefit_capital;
	private Float benefit_code;
	
	public String getBenefit_name() {
		return benefit_name;
	}
	public void setBenefit_name(String benefit_name) {
		this.benefit_name = benefit_name;
	}
	public Float getBenefit_capital() {
		return benefit_capital;
	}
	public void setBenefit_capital(Float benefit_capital) {
		this.benefit_capital = benefit_capital;
	}
	public Float getBenefit_code() {
		return benefit_code;
	}
	public void setBenefit_code(Float benefit_code) {
		this.benefit_code = benefit_code;
	}	
}

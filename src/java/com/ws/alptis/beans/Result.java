package com.ws.alptis.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "cod_st",
    "policy_number",
    "sub_policy_number",
    "certificate",
    "suplement_number",
    "test_results"
})
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cod_st;
	private String policy_number;
	private String sub_policy_number;
	private String certificate;
	private String suplement_number;
	private Tests test_results;
	
	public String getCod_st() {
		return cod_st;
	}
	public void setCod_st(String cod_st) {
		this.cod_st = cod_st;
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
	public Tests getTest_results() {
		return test_results;
	}
	public void setTest_results(Tests test_results) {
		this.test_results = test_results;
	}
	
	
	
}

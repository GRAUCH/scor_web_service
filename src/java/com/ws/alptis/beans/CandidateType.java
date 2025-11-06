package com.ws.alptis.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.ClientType;
import com.ws.enumeration.ContactTimeType;
import com.ws.enumeration.MaritalStatusType;
import com.ws.enumeration.SexType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateType", propOrder = {	
    "fiscal_identification_number",
    "name",
    "surname",
    "address",
    "town",
    "postal_code",
    "province",
    "country",
    "phone1",
    "phone2",
    "phone3",
    "email",
    "birth_date",
    "gender",
    "marital_status",
    "contact_time",
    "client_type"
})
public class CandidateType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String fiscal_identification_number;
	private String name;
	private String surname;
	private String address;
	private String town;
	private String postal_code;
	private String province;
	private String country;
	@XmlElement(nillable=true)
	private String phone1;
	@XmlElement(nillable=true)
	private String phone2;
	@XmlElement(nillable=true)
	private String phone3;
	@XmlElement(nillable=true)
	private String email;
	@XmlSchemaType(name = "date")
	private Date birth_date;
	private SexType gender;
	@XmlElement(nillable=true)
	private MaritalStatusType marital_status;
	@XmlElement(nillable=true)
	private ContactTimeType contact_time;
	@XmlElement(nillable=true)
	private ClientType client_type;
	
	public String getFiscal_identification_number() {
		return fiscal_identification_number;
	}
	public void setFiscal_identification_number(String fiscal_identification_number) {
		this.fiscal_identification_number = fiscal_identification_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public SexType getGender() {
		return gender;
	}
	public void setGender(SexType gender) {
		this.gender = gender;
	}
	public MaritalStatusType getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(MaritalStatusType marital_status) {
		this.marital_status = marital_status;
	}
	public ContactTimeType getContact_time() {
		return contact_time;
	}
	public void setContact_time(ContactTimeType contact_time) {
		this.contact_time = contact_time;
	}
	public ClientType getClient_type() {
		return client_type;
	}
	public void setClient_type(ClientType client_type) {
		this.client_type = client_type;
	}
		
}

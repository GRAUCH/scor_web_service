package com.ws.alptis.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testResultsType", propOrder = {
    "test_name",
    "description",
    "test_code",
    "zip_files",
    "test_status"
})
public class TestResultsType implements Serializable {

	private static final long serialVersionUID = 1L;
	private String test_name;
	private String description;
	private String test_code;
	private String zip_files;
	private TestNamesStatusType test_status;
	
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTest_code() {
		return test_code;
	}
	public void setTest_code(String test_code) {
		this.test_code = test_code;
	}
	public String getZip_files() {
		return zip_files;
	}
	public void setZip_files(String zip_files) {
		this.zip_files = zip_files;
	}
	public TestNamesStatusType getTest_status() {
		return test_status;
	}
	public void setTest_status(TestNamesStatusType test_status) {
		this.test_status = test_status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

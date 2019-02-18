package com.ws.alptis.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tests", propOrder = {
    "test",
    "test_results_status"
})
public class Tests implements Serializable {

	private static final long serialVersionUID = 1L;
	private TestResultsType test;
	private String test_results_status;
	
	public TestResultsType getTest() {
		return test;
	}
	public void setTest(TestResultsType test) {
		this.test = test;
	}
	public String getTest_results_status() {
		return test_results_status;
	}
	public void setTest_results_status(String test_results_status) {
		this.test_results_status = test_results_status;
	}
	
	
}

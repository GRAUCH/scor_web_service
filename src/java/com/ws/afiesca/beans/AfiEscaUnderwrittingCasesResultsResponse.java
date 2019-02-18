package com.ws.afiesca.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AfiEscaUnderwrittingCaseResultsResponse", propOrder = {
	"tuwCase",
    "dateRequested",
    "comments"
})
public class AfiEscaUnderwrittingCasesResultsResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="tuw_cases")
	private List<TuwCase> tuwCase;
	@XmlElement(name="dateRequested")
	private Date dateRequested;
	@XmlElement(name="comments")
	private String comments;
	
	public List<TuwCase> getTuwCase() {
		return tuwCase;
	}
	public void setTuwCase(List<TuwCase> tuwCase) {
		this.tuwCase = tuwCase;
	}
	public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

	
}

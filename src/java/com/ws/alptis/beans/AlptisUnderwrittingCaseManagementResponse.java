package com.ws.alptis.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.StatusType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlptisUnderwrittingCaseManagementResponse", propOrder = {
	"statusType",
    "date",
    "comments"
})
public class AlptisUnderwrittingCaseManagementResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="status")
	private StatusType statusType;
	private Date date;
	private String comments;
	
	public StatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

	
}

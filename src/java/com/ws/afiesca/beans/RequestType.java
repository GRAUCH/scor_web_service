package com.ws.afiesca.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.RecordType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "record",
    "date",
    "comments"
})
public class RequestType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RecordType record;
	private Date date;
	private String comments;
	

	public RecordType getRecord() {
		return record;
	}
	public void setRecord(RecordType record) {
		this.record = record;
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

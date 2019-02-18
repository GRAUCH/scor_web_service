package com.ws.afiesca.beans;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AfiEscaUnderwrittingCasesResultsRequest", propOrder = {
    "date"
    
})
@XmlRootElement(name = "AfiEscaUnderwrittingCasesResultsRequest")
public class AfiEscaUnderwrittingCasesResultsRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@XmlSchemaType(name = "date")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}

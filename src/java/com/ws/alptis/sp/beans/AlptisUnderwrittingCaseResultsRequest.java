package com.ws.alptis.sp.beans;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlptisUnderwrittingCaseResultsRequest", propOrder = {
    "tuw_cases",
    "dateRequested",
    "comments",
    "respuesta_remota"
})
@XmlRootElement(name = "AlptisUnderwrittingCaseResultsRequest")
public class AlptisUnderwrittingCaseResultsRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> tuw_cases;
	private String dateRequested;
	private String comments;
	private String respuesta_remota;
	
	public List<String> getTuw_cases() {
		return tuw_cases;
	}
	public void setTuw_cases(List<String> tuw_cases) {
		this.tuw_cases = tuw_cases;
	}
	public String getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(String dateRequested) {
		this.dateRequested = dateRequested;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRespuesta_remota() {
		return respuesta_remota;
	}
	public void setRespuesta_remota(String respuesta_remota) {
		this.respuesta_remota = respuesta_remota;
	}


	
}

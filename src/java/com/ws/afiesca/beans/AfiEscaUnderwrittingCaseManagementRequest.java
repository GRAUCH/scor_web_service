package com.ws.afiesca.beans;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AfiEscaUnderwrittingCaseManagementRequest", propOrder = {
    "request_Data",
    "policy",
    "candidate",
    "agent"
    
})
@XmlRootElement(name = "AfiEscaUnderwrittingCaseManagementRequest")
public class AfiEscaUnderwrittingCaseManagementRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="request_Data")
	private RequestType request_Data;
	@XmlElement(name="policy")
	private PolicyType policy;
	@XmlElement(name="candidate",nillable=true)
	private CandidateType candidate;
	@XmlElement(name="agent",nillable=true)
	private AgentType agent;
	
	public RequestType getRequest_Data() {
		return request_Data;
	}
	public void setRequest_Data(RequestType request_Data) {
		this.request_Data = request_Data;
	}
	public PolicyType getPolicy() {
		return policy;
	}
	public void setPolicy(PolicyType policy) {
		this.policy = policy;
	}
	public CandidateType getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidateType candidate) {
		this.candidate = candidate;
	}
	public AgentType getAgent() {
		return agent;
	}
	public void setAgent(AgentType agent) {
		this.agent = agent;
	}
	
	
}

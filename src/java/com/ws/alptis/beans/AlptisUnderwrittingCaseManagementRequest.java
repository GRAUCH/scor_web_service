package com.ws.alptis.beans;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alptisUnderwrittingCaseManagementRequest", propOrder = {
    "request_Data",
    "policy",
    "candidate",
    "agent",
    "services",
    "questions"
    
})
@XmlRootElement(name = "AlptisUnderwrittingCaseManagementRequest")
public class AlptisUnderwrittingCaseManagementRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RequestType request_Data;
	private PolicyType policy;
	@XmlElement(nillable=true)
	private CandidateType candidate;
	@XmlElement(nillable=true)
	private AgentType agent;
	@XmlElement(nillable=true)
	private ServicesType services;
	@XmlElementWrapper
	@XmlElement(name="question",nillable=true)
    protected List<QuestionType> questions;
	
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
	public ServicesType getServices() {
		return services;
	}
	public void setServices(ServicesType services) {
		this.services = services;
	}
	public List<QuestionType> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionType> questions) {
		this.questions = questions;
	}
		
}

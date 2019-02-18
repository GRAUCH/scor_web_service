package com.ws.alptis.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.ws.enumeration.DataTypeType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "questionType", propOrder = {
    "question_code",
    "data_type",
    "answer"
})
public class QuestionType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(nillable=true)
	private String question_code;
	@XmlElement(nillable=true)
	private DataTypeType data_type;
	@XmlElement(nillable=true)
	private String answer;
	
	public String getQuestion_code() {
		return question_code;
	}
	public void setQuestion_code(String question_code) {
		this.question_code = question_code;
	}
	public DataTypeType getData_type() {
		return data_type;
	}
	public void setData_type(DataTypeType data_type) {
		this.data_type = data_type;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}

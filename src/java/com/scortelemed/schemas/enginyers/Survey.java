
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Survey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Survey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="surveyNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="generalData" type="{http://www.scortelemed.com/schemas/enginyers}surveyGeneralProperties" minOccurs="0"/>
 *         &lt;element name="personData" type="{http://www.scortelemed.com/schemas/enginyers}surveyData" minOccurs="0"/>
 *         &lt;element name="riskTypeArray" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfRiskTypeElement" minOccurs="0"/>
 *         &lt;element name="questionsArray" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfGroups" minOccurs="0"/>
 *         &lt;element name="additional" type="{http://www.scortelemed.com/schemas/enginyers}surveyAdditionalFields" minOccurs="0"/>
 *         &lt;element name="faultArray" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfFaultElement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Survey", propOrder = {
    "surveyNumber",
    "generalData",
    "personData",
    "riskTypeArray",
    "questionsArray",
    "additional",
    "faultArray"
})
public class Survey {

    protected int surveyNumber;
    protected SurveyGeneralProperties generalData;
    protected SurveyData personData;
    protected ArrayOfRiskTypeElement riskTypeArray;
    protected ArrayOfGroups questionsArray;
    protected SurveyAdditionalFields additional;
    protected ArrayOfFaultElement faultArray;

    /**
     * Gets the value of the surveyNumber property.
     * 
     */
    public int getSurveyNumber() {
        return surveyNumber;
    }

    /**
     * Sets the value of the surveyNumber property.
     * 
     */
    public void setSurveyNumber(int value) {
        this.surveyNumber = value;
    }

    /**
     * Gets the value of the generalData property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyGeneralProperties }
     *     
     */
    public SurveyGeneralProperties getGeneralData() {
        return generalData;
    }

    /**
     * Sets the value of the generalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyGeneralProperties }
     *     
     */
    public void setGeneralData(SurveyGeneralProperties value) {
        this.generalData = value;
    }

    /**
     * Gets the value of the personData property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyData }
     *     
     */
    public SurveyData getPersonData() {
        return personData;
    }

    /**
     * Sets the value of the personData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyData }
     *     
     */
    public void setPersonData(SurveyData value) {
        this.personData = value;
    }

    /**
     * Gets the value of the riskTypeArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRiskTypeElement }
     *     
     */
    public ArrayOfRiskTypeElement getRiskTypeArray() {
        return riskTypeArray;
    }

    /**
     * Sets the value of the riskTypeArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRiskTypeElement }
     *     
     */
    public void setRiskTypeArray(ArrayOfRiskTypeElement value) {
        this.riskTypeArray = value;
    }

    /**
     * Gets the value of the questionsArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroups }
     *     
     */
    public ArrayOfGroups getQuestionsArray() {
        return questionsArray;
    }

    /**
     * Sets the value of the questionsArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroups }
     *     
     */
    public void setQuestionsArray(ArrayOfGroups value) {
        this.questionsArray = value;
    }

    /**
     * Gets the value of the additional property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyAdditionalFields }
     *     
     */
    public SurveyAdditionalFields getAdditional() {
        return additional;
    }

    /**
     * Sets the value of the additional property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyAdditionalFields }
     *     
     */
    public void setAdditional(SurveyAdditionalFields value) {
        this.additional = value;
    }

    /**
     * Gets the value of the faultArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFaultElement }
     *     
     */
    public ArrayOfFaultElement getFaultArray() {
        return faultArray;
    }

    /**
     * Sets the value of the faultArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFaultElement }
     *     
     */
    public void setFaultArray(ArrayOfFaultElement value) {
        this.faultArray = value;
    }

}

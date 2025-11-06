
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="d" type="{http://www.scortelemed.com/schemas/enginyers}surveyData" minOccurs="0"/>
 *         &lt;element name="risks" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfRiskTypeElement" minOccurs="0"/>
 *         &lt;element name="gen" type="{http://www.scortelemed.com/schemas/enginyers}surveyGeneralProperties" minOccurs="0"/>
 *         &lt;element name="additional" type="{http://www.scortelemed.com/schemas/enginyers}surveyAdditionalFields" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "d",
    "risks",
    "gen",
    "additional"
})
@XmlRootElement(name = "addExp")
public class AddExp {

    protected SurveyData d;
    protected ArrayOfRiskTypeElement risks;
    protected SurveyGeneralProperties gen;
    protected SurveyAdditionalFields additional;

    /**
     * Gets the value of the d property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyData }
     *     
     */
    public SurveyData getD() {
        return d;
    }

    /**
     * Sets the value of the d property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyData }
     *     
     */
    public void setD(SurveyData value) {
        this.d = value;
    }

    /**
     * Gets the value of the risks property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRiskTypeElement }
     *     
     */
    public ArrayOfRiskTypeElement getRisks() {
        return risks;
    }

    /**
     * Sets the value of the risks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRiskTypeElement }
     *     
     */
    public void setRisks(ArrayOfRiskTypeElement value) {
        this.risks = value;
    }

    /**
     * Gets the value of the gen property.
     * 
     * @return
     *     possible object is
     *     {@link SurveyGeneralProperties }
     *     
     */
    public SurveyGeneralProperties getGen() {
        return gen;
    }

    /**
     * Sets the value of the gen property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurveyGeneralProperties }
     *     
     */
    public void setGen(SurveyGeneralProperties value) {
        this.gen = value;
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

}

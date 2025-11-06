
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
 *         &lt;element name="addSurveyFromExpResult" type="{http://www.scortelemed.com/schemas/enginyers}Survey" minOccurs="0"/>
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
    "addSurveyFromExpResult"
})
@XmlRootElement(name = "addSurveyFromExpResponse")
public class AddSurveyFromExpResponse {

    protected Survey addSurveyFromExpResult;

    /**
     * Gets the value of the addSurveyFromExpResult property.
     * 
     * @return
     *     possible object is
     *     {@link Survey }
     *     
     */
    public Survey getAddSurveyFromExpResult() {
        return addSurveyFromExpResult;
    }

    /**
     * Sets the value of the addSurveyFromExpResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Survey }
     *     
     */
    public void setAddSurveyFromExpResult(Survey value) {
        this.addSurveyFromExpResult = value;
    }

}

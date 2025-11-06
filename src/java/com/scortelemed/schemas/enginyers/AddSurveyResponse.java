
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
 *         &lt;element name="addSurveyResult" type="{http://www.scortelemed.com/schemas/enginyers}Survey" minOccurs="0"/>
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
    "addSurveyResult"
})
@XmlRootElement(name = "addSurveyResponse")
public class AddSurveyResponse {

    protected Survey addSurveyResult;

    /**
     * Gets the value of the addSurveyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Survey }
     *     
     */
    public Survey getAddSurveyResult() {
        return addSurveyResult;
    }

    /**
     * Sets the value of the addSurveyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Survey }
     *     
     */
    public void setAddSurveyResult(Survey value) {
        this.addSurveyResult = value;
    }

}

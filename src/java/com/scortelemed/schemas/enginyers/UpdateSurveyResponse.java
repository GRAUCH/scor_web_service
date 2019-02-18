
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
 *         &lt;element name="updateSurveyResult" type="{http://www.scortelemed.com/schemas/enginyers}Survey" minOccurs="0"/>
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
    "updateSurveyResult"
})
@XmlRootElement(name = "updateSurveyResponse")
public class UpdateSurveyResponse {

    protected Survey updateSurveyResult;

    /**
     * Gets the value of the updateSurveyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Survey }
     *     
     */
    public Survey getUpdateSurveyResult() {
        return updateSurveyResult;
    }

    /**
     * Sets the value of the updateSurveyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Survey }
     *     
     */
    public void setUpdateSurveyResult(Survey value) {
        this.updateSurveyResult = value;
    }

}


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
 *         &lt;element name="addSurveyExternResult" type="{http://www.scortelemed.com/schemas/enginyers}Peticio" minOccurs="0"/>
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
    "addSurveyExternResult"
})
@XmlRootElement(name = "addSurveyExternResponse")
public class AddSurveyExternResponse {

    protected Peticio addSurveyExternResult;

    /**
     * Gets the value of the addSurveyExternResult property.
     * 
     * @return
     *     possible object is
     *     {@link Peticio }
     *     
     */
    public Peticio getAddSurveyExternResult() {
        return addSurveyExternResult;
    }

    /**
     * Sets the value of the addSurveyExternResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Peticio }
     *     
     */
    public void setAddSurveyExternResult(Peticio value) {
        this.addSurveyExternResult = value;
    }

}


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
 *         &lt;element name="addExpResult" type="{http://www.scortelemed.com/schemas/enginyers}Expedient" minOccurs="0"/>
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
    "addExpResult"
})
@XmlRootElement(name = "addExpResponse")
public class AddExpResponse {

    protected Expedient addExpResult;

    /**
     * Gets the value of the addExpResult property.
     * 
     * @return
     *     possible object is
     *     {@link Expedient }
     *     
     */
    public Expedient getAddExpResult() {
        return addExpResult;
    }

    /**
     * Sets the value of the addExpResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Expedient }
     *     
     */
    public void setAddExpResult(Expedient value) {
        this.addExpResult = value;
    }

}

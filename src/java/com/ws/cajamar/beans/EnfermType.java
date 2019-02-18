
package com.ws.cajamar.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnfermType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnfermType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enfer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnfermType", propOrder = {
    "enfer"
})
public class EnfermType {

    protected String enfer;

    /**
     * Gets the value of the enfer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnfer() {
        return enfer;
    }

    /**
     * Sets the value of the enfer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnfer(String value) {
        this.enfer = value;
    }

}

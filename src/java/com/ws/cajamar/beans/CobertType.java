
package com.ws.cajamar.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CobertType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CobertType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cobern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dcober" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CobertType", propOrder = {
    "cobern",
    "dcober"
})
public class CobertType {

    protected String cobern;
    protected String dcober;

    /**
     * Gets the value of the cobern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCobern() {
        return cobern;
    }

    /**
     * Sets the value of the cobern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCobern(String value) {
        this.cobern = value;
    }

    /**
     * Gets the value of the dcober property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDcober() {
        return dcober;
    }

    /**
     * Sets the value of the dcober property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDcober(String value) {
        this.dcober = value;
    }

}

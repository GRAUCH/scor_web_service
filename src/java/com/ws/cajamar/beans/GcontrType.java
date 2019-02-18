
package com.ws.cajamar.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GcontrType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GcontrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="coder8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="werrts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GcontrType", propOrder = {
    "coder8",
    "werrts"
})
public class GcontrType {

    protected String coder8;
    protected String werrts;

    /**
     * Gets the value of the coder8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoder8() {
        return coder8;
    }

    /**
     * Sets the value of the coder8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoder8(String value) {
        this.coder8 = value;
    }

    /**
     * Gets the value of the werrts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWerrts() {
        return werrts;
    }

    /**
     * Sets the value of the werrts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWerrts(String value) {
        this.werrts = value;
    }

}

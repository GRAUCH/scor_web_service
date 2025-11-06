
package com.ws.cajamar.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FfnaciType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FfnaciType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aanccl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mmnccl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ddnccl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FfnaciType", propOrder = {
    "aanccl",
    "mmnccl",
    "ddnccl"
})
public class FfnaciType {

    protected String aanccl;
    protected String mmnccl;
    protected String ddnccl;

    /**
     * Gets the value of the aanccl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAanccl() {
        return aanccl;
    }

    /**
     * Sets the value of the aanccl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAanccl(String value) {
        this.aanccl = value;
    }

    /**
     * Gets the value of the mmnccl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMmnccl() {
        return mmnccl;
    }

    /**
     * Sets the value of the mmnccl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMmnccl(String value) {
        this.mmnccl = value;
    }

    /**
     * Gets the value of the ddnccl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDdnccl() {
        return ddnccl;
    }

    /**
     * Sets the value of the ddnccl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDdnccl(String value) {
        this.ddnccl = value;
    }

}

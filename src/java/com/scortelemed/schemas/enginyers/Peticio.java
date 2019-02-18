
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Peticio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Peticio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="urlPeticio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="faultArray" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfFaultElement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Peticio", propOrder = {
    "urlPeticio",
    "faultArray"
})
public class Peticio {

    @XmlElement(required = true)
    protected String urlPeticio;
    protected ArrayOfFaultElement faultArray;

    /**
     * Gets the value of the urlPeticio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPeticio() {
        return urlPeticio;
    }

    /**
     * Sets the value of the urlPeticio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPeticio(String value) {
        this.urlPeticio = value;
    }

    /**
     * Gets the value of the faultArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFaultElement }
     *     
     */
    public ArrayOfFaultElement getFaultArray() {
        return faultArray;
    }

    /**
     * Sets the value of the faultArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFaultElement }
     *     
     */
    public void setFaultArray(ArrayOfFaultElement value) {
        this.faultArray = value;
    }

}

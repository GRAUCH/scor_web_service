
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for optionsListOfValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="optionsListOfValues">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="valueId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valueTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "optionsListOfValues", propOrder = {
    "valueId",
    "valueTitle"
})
public class OptionsListOfValues {

    protected int valueId;
    protected String valueTitle;

    /**
     * Gets the value of the valueId property.
     * 
     */
    public int getValueId() {
        return valueId;
    }

    /**
     * Sets the value of the valueId property.
     * 
     */
    public void setValueId(int value) {
        this.valueId = value;
    }

    /**
     * Gets the value of the valueTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueTitle() {
        return valueTitle;
    }

    /**
     * Sets the value of the valueTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueTitle(String value) {
        this.valueTitle = value;
    }

}

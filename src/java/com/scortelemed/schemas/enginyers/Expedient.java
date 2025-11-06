
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Expedient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Expedient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addExpResultCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idExpedient" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "Expedient", propOrder = {
    "addExpResultCode",
    "idExpedient",
    "faultArray"
})
public class Expedient {

    protected int addExpResultCode;
    protected Integer idExpedient;
    protected ArrayOfFaultElement faultArray;

    /**
     * Gets the value of the addExpResultCode property.
     * 
     */
    public int getAddExpResultCode() {
        return addExpResultCode;
    }

    /**
     * Sets the value of the addExpResultCode property.
     * 
     */
    public void setAddExpResultCode(int value) {
        this.addExpResultCode = value;
    }

    /**
     * Gets the value of the idExpedient property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdExpedient() {
        return idExpedient;
    }

    /**
     * Sets the value of the idExpedient property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdExpedient(Integer value) {
        this.idExpedient = value;
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

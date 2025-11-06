
package com.scortelemed.schemas.psn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaExpedienteRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaExpedienteRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numExpediente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numSumplemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaExpedienteRequest", propOrder = {
    "numSolicitud",
    "numExpediente",
    "numSumplemento"
})
public class ConsultaExpedienteRequest {

    protected String numSolicitud;
    protected String numExpediente;
    protected String numSumplemento;

    /**
     * Gets the value of the numSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSolicitud() {
        return numSolicitud;
    }

    /**
     * Sets the value of the numSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSolicitud(String value) {
        this.numSolicitud = value;
    }

    /**
     * Gets the value of the numExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumExpediente() {
        return numExpediente;
    }

    /**
     * Sets the value of the numExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumExpediente(String value) {
        this.numExpediente = value;
    }

    /**
     * Gets the value of the numSumplemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSumplemento() {
        return numSumplemento;
    }

    /**
     * Sets the value of the numSumplemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSumplemento(String value) {
        this.numSumplemento = value;
    }

}

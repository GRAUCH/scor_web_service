
package com.scortelemed.schemas.caser;

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
 *         &lt;element name="codExpediente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "codExpediente"
})
public class ConsultaExpedienteRequest {

    protected String codExpediente;

    /**
     * Gets the value of the codExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodExpediente() {
        return codExpediente;
    }

    /**
     * Sets the value of the codExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodExpediente(String value) {
        this.codExpediente = value;
    }

}

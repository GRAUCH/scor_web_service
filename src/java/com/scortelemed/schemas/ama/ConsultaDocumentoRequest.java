
package com.scortelemed.schemas.ama;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaDocumentoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaDocumentoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoSt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentacionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nodoAlfresco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaDocumentoRequest", propOrder = {
    "codigoSt",
    "documentacionId",
    "nodoAlfresco"
})
public class ConsultaDocumentoRequest {

    protected String codigoSt;
    protected String documentacionId;
    protected String nodoAlfresco;

    /**
     * Gets the value of the codigoSt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSt() {
        return codigoSt;
    }

    /**
     * Sets the value of the codigoSt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSt(String value) {
        this.codigoSt = value;
    }

    /**
     * Gets the value of the documentacionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentacionId() {
        return documentacionId;
    }

    /**
     * Sets the value of the documentacionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentacionId(String value) {
        this.documentacionId = value;
    }

    /**
     * Gets the value of the nodoAlfresco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodoAlfresco() {
        return nodoAlfresco;
    }

    /**
     * Sets the value of the nodoAlfresco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodoAlfresco(String value) {
        this.nodoAlfresco = value;
    }

}

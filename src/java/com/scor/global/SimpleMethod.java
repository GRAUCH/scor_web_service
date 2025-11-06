
package com.scor.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para simpleMethod complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="simpleMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="compania" type="{http://services/}compania" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleMethod", propOrder = {
    "compania"
})
public class SimpleMethod {

    protected Compania compania;

    /**
     * Obtiene el valor de la propiedad compania.
     * 
     * @return
     *     possible object is
     *     {@link Compania }
     *     
     */
    public Compania getCompania() {
        return compania;
    }

    /**
     * Define el valor de la propiedad compania.
     * 
     * @param value
     *     allowed object is
     *     {@link Compania }
     *     
     */
    public void setCompania(Compania value) {
        this.compania = value;
    }

}


package com.scor.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para simpleMethodResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="simpleMethodResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="simpleResult" type="{http://services/}compania" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleMethodResponse", propOrder = {
    "simpleResult"
})
public class SimpleMethodResponse {

    protected Compania simpleResult;

    /**
     * Obtiene el valor de la propiedad simpleResult.
     * 
     * @return
     *     possible object is
     *     {@link Compania }
     *     
     */
    public Compania getSimpleResult() {
        return simpleResult;
    }

    /**
     * Define el valor de la propiedad simpleResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Compania }
     *     
     */
    public void setSimpleResult(Compania value) {
        this.simpleResult = value;
    }

}


package com.scortelemed.schemas.methislabCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BenefitsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BenefitsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="benefictName" type="{http://www.scortelemed.com/schemas/methislab}BenefictNameType" minOccurs="0"/>
 *         &lt;element name="benefictCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="benefictCapital" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="benefictResult" type="{http://www.scortelemed.com/schemas/methislab}BenefictResultType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BenefitsType", propOrder = {
    "benefictName",
    "benefictCode",
    "benefictCapital",
    "benefictResult"
})
public class BenefitsType {

    protected BenefictNameType benefictName;
    @XmlElement(required = true)
    protected String benefictCode;
    @XmlElement(required = true)
    protected String benefictCapital;
    protected BenefictResultType benefictResult;

    /**
     * Gets the value of the benefictName property.
     *
     * @return
     *     possible object is
     *     {@link BenefictNameType }
     *
     */
    public BenefictNameType getBenefictName() {
        return benefictName;
    }

    /**
     * Sets the value of the benefictName property.
     *
     * @param value
     *     allowed object is
     *     {@link BenefictNameType }
     *
     */
    public void setBenefictName(BenefictNameType value) {
        this.benefictName = value;
    }

    /**
     * Gets the value of the benefictCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBenefictCode() {
        return benefictCode;
    }

    /**
     * Sets the value of the benefictCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBenefictCode(String value) {
        this.benefictCode = value;
    }

    /**
     * Gets the value of the benefictCapital property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBenefictCapital() {
        return benefictCapital;
    }

    /**
     * Sets the value of the benefictCapital property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBenefictCapital(String value) {
        this.benefictCapital = value;
    }

    /**
     * Gets the value of the benefictResult property.
     *
     * @return
     *     possible object is
     *     {@link BenefictResultType }
     *
     */
    public BenefictResultType getBenefictResult() {
        return benefictResult;
    }

    /**
     * Sets the value of the benefictResult property.
     *
     * @param value
     *     allowed object is
     *     {@link BenefictResultType }
     *     
     */
    public void setBenefictResult(BenefictResultType value) {
        this.benefictResult = value;
    }

}

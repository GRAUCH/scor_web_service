
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for questionOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="questionOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minRangeValidator" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxRangeValidator" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="labelTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="controlType" type="{http://www.scortelemed.com/schemas/enginyers}bonsaiControlType"/>
 *         &lt;element name="options" type="{http://www.scortelemed.com/schemas/enginyers}options" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "questionOptions", propOrder = {
    "minRangeValidator",
    "maxRangeValidator",
    "labelTitle",
    "inputLength",
    "controlType",
    "options"
})
public class QuestionOptions {

    protected int minRangeValidator;
    protected int maxRangeValidator;
    protected String labelTitle;
    protected Integer inputLength;
    @XmlElement(required = true)
    protected BonsaiControlType controlType;
    protected Options options;

    /**
     * Gets the value of the minRangeValidator property.
     * 
     */
    public int getMinRangeValidator() {
        return minRangeValidator;
    }

    /**
     * Sets the value of the minRangeValidator property.
     * 
     */
    public void setMinRangeValidator(int value) {
        this.minRangeValidator = value;
    }

    /**
     * Gets the value of the maxRangeValidator property.
     * 
     */
    public int getMaxRangeValidator() {
        return maxRangeValidator;
    }

    /**
     * Sets the value of the maxRangeValidator property.
     * 
     */
    public void setMaxRangeValidator(int value) {
        this.maxRangeValidator = value;
    }

    /**
     * Gets the value of the labelTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelTitle() {
        return labelTitle;
    }

    /**
     * Sets the value of the labelTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelTitle(String value) {
        this.labelTitle = value;
    }

    /**
     * Gets the value of the inputLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInputLength() {
        return inputLength;
    }

    /**
     * Sets the value of the inputLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInputLength(Integer value) {
        this.inputLength = value;
    }

    /**
     * Gets the value of the controlType property.
     * 
     * @return
     *     possible object is
     *     {@link BonsaiControlType }
     *     
     */
    public BonsaiControlType getControlType() {
        return controlType;
    }

    /**
     * Sets the value of the controlType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BonsaiControlType }
     *     
     */
    public void setControlType(BonsaiControlType value) {
        this.controlType = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link Options }
     *     
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link Options }
     *     
     */
    public void setOptions(Options value) {
        this.options = value;
    }

}

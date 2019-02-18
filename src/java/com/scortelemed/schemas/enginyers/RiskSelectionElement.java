
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for riskSelectionElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="riskSelectionElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retain" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="retainResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overchargeMotive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="premiumLoadings" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="extraPremiumLoadings" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="riskSelectionComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exclusions" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfExclusionsElement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "riskSelectionElement", propOrder = {
    "retain",
    "retainResponse",
    "overchargeMotive",
    "premiumLoadings",
    "extraPremiumLoadings",
    "riskSelectionComment",
    "exclusions"
})
public class RiskSelectionElement {

    protected boolean retain;
    protected String retainResponse;
    protected String overchargeMotive;
    protected double premiumLoadings;
    protected double extraPremiumLoadings;
    protected String riskSelectionComment;
    protected ArrayOfExclusionsElement exclusions;

    /**
     * Gets the value of the retain property.
     * 
     */
    public boolean isRetain() {
        return retain;
    }

    /**
     * Sets the value of the retain property.
     * 
     */
    public void setRetain(boolean value) {
        this.retain = value;
    }

    /**
     * Gets the value of the retainResponse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetainResponse() {
        return retainResponse;
    }

    /**
     * Sets the value of the retainResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetainResponse(String value) {
        this.retainResponse = value;
    }

    /**
     * Gets the value of the overchargeMotive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverchargeMotive() {
        return overchargeMotive;
    }

    /**
     * Sets the value of the overchargeMotive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverchargeMotive(String value) {
        this.overchargeMotive = value;
    }

    /**
     * Gets the value of the premiumLoadings property.
     * 
     */
    public double getPremiumLoadings() {
        return premiumLoadings;
    }

    /**
     * Sets the value of the premiumLoadings property.
     * 
     */
    public void setPremiumLoadings(double value) {
        this.premiumLoadings = value;
    }

    /**
     * Gets the value of the extraPremiumLoadings property.
     * 
     */
    public double getExtraPremiumLoadings() {
        return extraPremiumLoadings;
    }

    /**
     * Sets the value of the extraPremiumLoadings property.
     * 
     */
    public void setExtraPremiumLoadings(double value) {
        this.extraPremiumLoadings = value;
    }

    /**
     * Gets the value of the riskSelectionComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskSelectionComment() {
        return riskSelectionComment;
    }

    /**
     * Sets the value of the riskSelectionComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskSelectionComment(String value) {
        this.riskSelectionComment = value;
    }

    /**
     * Gets the value of the exclusions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfExclusionsElement }
     *     
     */
    public ArrayOfExclusionsElement getExclusions() {
        return exclusions;
    }

    /**
     * Sets the value of the exclusions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfExclusionsElement }
     *     
     */
    public void setExclusions(ArrayOfExclusionsElement value) {
        this.exclusions = value;
    }

}

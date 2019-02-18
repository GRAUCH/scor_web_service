
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for riskTypeElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="riskTypeElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idRisk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="riskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="riskSelection" type="{http://www.scortelemed.com/schemas/enginyers}riskSelectionElement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "riskTypeElement", propOrder = {
    "idRisk",
    "riskName",
    "riskValue",
    "riskSelection"
})
public class RiskTypeElement {

    protected int idRisk;
    protected String riskName;
    protected double riskValue;
    protected RiskSelectionElement riskSelection;

    /**
     * Gets the value of the idRisk property.
     * 
     */
    public int getIdRisk() {
        return idRisk;
    }

    /**
     * Sets the value of the idRisk property.
     * 
     */
    public void setIdRisk(int value) {
        this.idRisk = value;
    }

    /**
     * Gets the value of the riskName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskName() {
        return riskName;
    }

    /**
     * Sets the value of the riskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskName(String value) {
        this.riskName = value;
    }

    /**
     * Gets the value of the riskValue property.
     * 
     */
    public double getRiskValue() {
        return riskValue;
    }

    /**
     * Sets the value of the riskValue property.
     * 
     */
    public void setRiskValue(double value) {
        this.riskValue = value;
    }

    /**
     * Gets the value of the riskSelection property.
     * 
     * @return
     *     possible object is
     *     {@link RiskSelectionElement }
     *     
     */
    public RiskSelectionElement getRiskSelection() {
        return riskSelection;
    }

    /**
     * Sets the value of the riskSelection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiskSelectionElement }
     *     
     */
    public void setRiskSelection(RiskSelectionElement value) {
        this.riskSelection = value;
    }

}

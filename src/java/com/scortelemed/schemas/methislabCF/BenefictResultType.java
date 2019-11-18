
package com.scortelemed.schemas.methislabCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BenefictResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BenefictResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="premiumLoading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capitalLoading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descPremiumLoading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descCapitalLoading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="temporalLoading" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exclusions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="medicalReports" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="medicalTest" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BenefictResultType", propOrder = {
    "descResult",
    "resultCode",
    "premiumLoading",
    "capitalLoading",
    "descPremiumLoading",
    "descCapitalLoading",
    "temporalLoading",
    "exclusions",
    "medicalReports",
    "medicalTest",
    "notes"
})
public class BenefictResultType {

    protected String descResult;
    protected String resultCode;
    protected String premiumLoading;
    protected String capitalLoading;
    protected String descPremiumLoading;
    protected String descCapitalLoading;
    @XmlElement(nillable = true)
    protected List<String> temporalLoading;
    @XmlElement(nillable = true)
    protected List<String> exclusions;
    @XmlElement(nillable = true)
    protected List<String> medicalReports;
    @XmlElement(nillable = true)
    protected List<String> medicalTest;
    @XmlElement(nillable = true)
    protected List<String> notes;

    /**
     * Gets the value of the descResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescResult() {
        return descResult;
    }

    /**
     * Sets the value of the descResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescResult(String value) {
        this.descResult = value;
    }

    /**
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the premiumLoading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremiumLoading() {
        return premiumLoading;
    }

    /**
     * Sets the value of the premiumLoading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremiumLoading(String value) {
        this.premiumLoading = value;
    }

    /**
     * Gets the value of the capitalLoading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitalLoading() {
        return capitalLoading;
    }

    /**
     * Sets the value of the capitalLoading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitalLoading(String value) {
        this.capitalLoading = value;
    }

    /**
     * Gets the value of the descPremiumLoading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPremiumLoading() {
        return descPremiumLoading;
    }

    /**
     * Sets the value of the descPremiumLoading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPremiumLoading(String value) {
        this.descPremiumLoading = value;
    }

    /**
     * Gets the value of the descCapitalLoading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCapitalLoading() {
        return descCapitalLoading;
    }

    /**
     * Sets the value of the descCapitalLoading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCapitalLoading(String value) {
        this.descCapitalLoading = value;
    }

    /**
     * Gets the value of the temporalLoading property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the temporalLoading property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemporalLoading().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTemporalLoading() {
        if (temporalLoading == null) {
            temporalLoading = new ArrayList<String>();
        }
        return this.temporalLoading;
    }

    /**
     * Gets the value of the exclusions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exclusions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExclusions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExclusions() {
        if (exclusions == null) {
            exclusions = new ArrayList<String>();
        }
        return this.exclusions;
    }

    /**
     * Gets the value of the medicalReports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicalReports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedicalReports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMedicalReports() {
        if (medicalReports == null) {
            medicalReports = new ArrayList<String>();
        }
        return this.medicalReports;
    }

    /**
     * Gets the value of the medicalTest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicalTest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedicalTest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMedicalTest() {
        if (medicalTest == null) {
            medicalTest = new ArrayList<String>();
        }
        return this.medicalTest;
    }

    /**
     * Gets the value of the notes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotes() {
        if (notes == null) {
            notes = new ArrayList<String>();
        }
        return this.notes;
    }

}

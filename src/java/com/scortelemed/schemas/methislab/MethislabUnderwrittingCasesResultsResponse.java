
package com.scortelemed.schemas.methislab;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for methislabUnderwrittingCasesResultsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="methislabUnderwrittingCasesResultsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="status" type="{http://www.scortelemed.com/schemas/methislab}StatusType"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Expediente" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestState" type="{http://www.scortelemed.com/schemas/methislab}RequestStateType"/>
 *                   &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="benefitsList" type="{http://www.scortelemed.com/schemas/methislab}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="Zip" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *                   &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "methislabUnderwrittingCasesResultsResponse", propOrder = {
    "date",
    "status",
    "message",
    "code",
    "expediente"
})
public class MethislabUnderwrittingCasesResultsResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected StatusType status;
    @XmlElement(required = true)
    protected String message;
    protected int code;
    @XmlElement(name = "Expediente", nillable = true)
    protected List<MethislabUnderwrittingCasesResultsResponse.Expediente> expediente;

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessages() {
        return message;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessages(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the code property.
     *
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * Gets the value of the expediente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expediente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpediente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethislabUnderwrittingCasesResultsResponse.Expediente }
     * 
     * 
     */
    public List<MethislabUnderwrittingCasesResultsResponse.Expediente> getExpediente() {
        if (expediente == null) {
            expediente = new ArrayList<MethislabUnderwrittingCasesResultsResponse.Expediente>();
        }
        return this.expediente;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestState" type="{http://www.scortelemed.com/schemas/methislab}RequestStateType"/>
     *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="benefitsList" type="{http://www.scortelemed.com/schemas/methislab}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="Zip" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "requestDate",
        "requestNumber",
        "requestState",
        "productCode",
        "policyNumber",
        "certificateNumber",
        "fiscalIdentificationNumber",
        "mobilePhone",
        "phoneNumber1",
        "phoneNumber2",
        "benefitsList",
        "zip",
        "notes"
    })
    public static class Expediente {

        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestDate;
        @XmlElement(required = true)
        protected String requestNumber;
        @XmlElement(required = true)
        protected RequestStateType requestState;
        @XmlElement(required = true)
        protected String productCode;
        @XmlElement(required = true)
        protected String policyNumber;
        @XmlElement(required = true)
        protected String certificateNumber;
        @XmlElement(required = true)
        protected String fiscalIdentificationNumber;
        @XmlElement(required = true)
        protected String mobilePhone;
        @XmlElement(required = true)
        protected String phoneNumber1;
        @XmlElement(required = true)
        protected String phoneNumber2;
        @XmlElement(nillable = true)
        protected List<BenefitsType> benefitsList;
        @XmlElement(name = "Zip", required = true)
        protected byte[] zip;
        @XmlElement(required = true)
        protected String notes;

        /**
         * Gets the value of the requestDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getRequestDate() {
            return requestDate;
        }

        /**
         * Sets the value of the requestDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setRequestDate(XMLGregorianCalendar value) {
            this.requestDate = value;
        }

        /**
         * Gets the value of the requestNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRequestNumber() {
            return requestNumber;
        }

        /**
         * Sets the value of the requestNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRequestNumber(String value) {
            this.requestNumber = value;
        }

        /**
         * Gets the value of the requestState property.
         * 
         * @return
         *     possible object is
         *     {@link RequestStateType }
         *     
         */
        public RequestStateType getRequestState() {
            return requestState;
        }

        /**
         * Sets the value of the requestState property.
         * 
         * @param value
         *     allowed object is
         *     {@link RequestStateType }
         *     
         */
        public void setRequestState(RequestStateType value) {
            this.requestState = value;
        }

        /**
         * Gets the value of the productCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductCode() {
            return productCode;
        }

        /**
         * Sets the value of the productCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductCode(String value) {
            this.productCode = value;
        }

        /**
         * Gets the value of the policyNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPolicyNumber() {
            return policyNumber;
        }

        /**
         * Sets the value of the policyNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPolicyNumber(String value) {
            this.policyNumber = value;
        }

        /**
         * Gets the value of the certificateNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCertificateNumber() {
            return certificateNumber;
        }

        /**
         * Sets the value of the certificateNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCertificateNumber(String value) {
            this.certificateNumber = value;
        }

        /**
         * Gets the value of the fiscalIdentificationNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFiscalIdentificationNumber() {
            return fiscalIdentificationNumber;
        }

        /**
         * Sets the value of the fiscalIdentificationNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFiscalIdentificationNumber(String value) {
            this.fiscalIdentificationNumber = value;
        }

        /**
         * Gets the value of the mobilePhone property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMobilePhone() {
            return mobilePhone;
        }

        /**
         * Sets the value of the mobilePhone property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMobilePhone(String value) {
            this.mobilePhone = value;
        }

        /**
         * Gets the value of the phoneNumber1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhoneNumber1() {
            return phoneNumber1;
        }

        /**
         * Sets the value of the phoneNumber1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhoneNumber1(String value) {
            this.phoneNumber1 = value;
        }

        /**
         * Gets the value of the phoneNumber2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhoneNumber2() {
            return phoneNumber2;
        }

        /**
         * Sets the value of the phoneNumber2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhoneNumber2(String value) {
            this.phoneNumber2 = value;
        }

        /**
         * Gets the value of the benefitsList property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the benefitsList property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBenefitsList().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BenefitsType }
         * 
         * 
         */
        public List<BenefitsType> getBenefitsList() {
            if (benefitsList == null) {
                benefitsList = new ArrayList<BenefitsType>();
            }
            return this.benefitsList;
        }

        /**
         * Gets the value of the zip property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getZip() {
            return zip;
        }

        /**
         * Sets the value of the zip property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setZip(byte[] value) {
            this.zip = ((byte[]) value);
        }

        /**
         * Gets the value of the notes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNotes() {
            return notes;
        }

        /**
         * Sets the value of the notes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNotes(String value) {
            this.notes = value;
        }

    }

}


package com.scortelemed.schemas.methislabCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for MethislabCFUnderwrittingCaseManagementRequest complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MethislabCFUnderwrittingCaseManagementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CandidateInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="requestState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="mobileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="actuarialAge" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="birthCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="agency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="agent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="agentPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="agentEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="gender" type="{http://www.scortelemed.com/schemas/methislab}GenderType" minOccurs="0"/>
 *                   &lt;element name="civilState" type="{http://www.scortelemed.com/schemas/methislab}CivilStateType" minOccurs="0"/>
 *                   &lt;element name="customerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="contactTime" type="{http://www.scortelemed.com/schemas/methislab}ContactTimeType" minOccurs="0"/>
 *                   &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="eUWReferenceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="previousQuestions" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dataType" type="{http://www.scortelemed.com/schemas/methislab}QuestionDataType" minOccurs="0"/>
 *                   &lt;element name="answerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="answer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="services" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="serviceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="serviceDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="benefits" type="{http://www.scortelemed.com/schemas/methislab}BenefitsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethislabCFUnderwrittingCaseManagementRequest", propOrder = {
    "candidateInformation",
    "previousQuestions",
    "services",
    "benefits"
})
public class MethislabCFUnderwrittingCaseManagementRequest {

    @XmlElement(name = "CandidateInformation", required = true)
    protected MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation candidateInformation;
    @XmlElement(nillable = true)
    protected List<MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions> previousQuestions;
    @XmlElement(nillable = true)
    protected List<MethislabCFUnderwrittingCaseManagementRequest.Services> services;
    protected BenefitsType benefits;

    /**
     * Gets the value of the candidateInformation property.
     *
     * @return
     *     possible object is
     *     {@link MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation }
     *
     */
    public MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation getCandidateInformation() {
        return candidateInformation;
    }

    /**
     * Sets the value of the candidateInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation }
     *
     */
    public void setCandidateInformation(MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation value) {
        this.candidateInformation = value;
    }

    /**
     * Gets the value of the previousQuestions property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the previousQuestions property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreviousQuestions().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions }
     *
     *
     */
    public List<MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions> getPreviousQuestions() {
        if (previousQuestions == null) {
            previousQuestions = new ArrayList<MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions>();
        }
        return this.previousQuestions;
    }

    /**
     * Gets the value of the services property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the services property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServices().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethislabCFUnderwrittingCaseManagementRequest.Services }
     *
     *
     */
    public List<MethislabCFUnderwrittingCaseManagementRequest.Services> getServices() {
        if (services == null) {
            services = new ArrayList<MethislabCFUnderwrittingCaseManagementRequest.Services>();
        }
        return this.services;
    }

    /**
     * Gets the value of the benefits property.
     *
     * @return
     *     possible object is
     *     {@link BenefitsType }
     *
     */
    public BenefitsType getBenefits() {
        return benefits;
    }

    /**
     * Sets the value of the benefits property.
     *
     * @param value
     *     allowed object is
     *     {@link BenefitsType }
     *
     */
    public void setBenefits(BenefitsType value) {
        this.benefits = value;
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
     *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="requestState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="mobileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="actuarialAge" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="birthCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="agency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="agent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="agentPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="agentEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="gender" type="{http://www.scortelemed.com/schemas/methislab}GenderType" minOccurs="0"/>
     *         &lt;element name="civilState" type="{http://www.scortelemed.com/schemas/methislab}CivilStateType" minOccurs="0"/>
     *         &lt;element name="customerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="contactTime" type="{http://www.scortelemed.com/schemas/methislab}ContactTimeType" minOccurs="0"/>
     *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="eUWReferenceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "productCode",
        "requestNumber",
        "policyNumber",
        "certificateNumber",
        "requestDate",
        "password",
        "requestState",
        "name",
        "surname",
        "address",
        "postalCode",
        "city",
        "province",
        "country",
        "phoneNumber1",
        "phoneNumber2",
        "mobileNumber",
        "email",
        "birthDate",
        "actuarialAge",
        "birthCity",
        "fiscalIdentificationNumber",
        "agency",
        "agent",
        "agentPhone",
        "agentEmail",
        "gender",
        "civilState",
        "customerType",
        "contactTime",
        "comments",
        "euwReferenceCode"
    })
    public static class CandidateInformation {

        @XmlElement(required = true)
        protected String productCode;
        @XmlElement(required = true)
        protected String requestNumber;
        protected String policyNumber;
        protected String certificateNumber;
        protected String requestDate;
        protected String password;
        @XmlElement(required = true)
        protected String requestState;
        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String surname;
        @XmlElement(required = true)
        protected String address;
        @XmlElement(required = true)
        protected String postalCode;
        @XmlElement(required = true)
        protected String city;
        protected String province;
        @XmlElement(required = true)
        protected String country;
        protected String phoneNumber1;
        protected String phoneNumber2;
        @XmlElement(required = true)
        protected String mobileNumber;
        protected String email;
        @XmlElement(required = true)
        protected String birthDate;
        @XmlElement(required = true)
        protected String actuarialAge;
        protected String birthCity;
        @XmlElement(required = true)
        protected String fiscalIdentificationNumber;
        protected String agency;
        protected String agent;
        protected String agentPhone;
        protected String agentEmail;
        protected GenderType gender;
        protected CivilStateType civilState;
        protected String customerType;
        protected ContactTimeType contactTime;
        protected String comments;
        @XmlElement(name = "eUWReferenceCode")
        protected String euwReferenceCode;

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
         * Gets the value of the requestDate property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestDate() {
            return requestDate;
        }

        /**
         * Sets the value of the requestDate property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestDate(String value) {
            this.requestDate = value;
        }

        /**
         * Gets the value of the password property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the value of the password property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setPassword(String value) {
            this.password = value;
        }

        /**
         * Gets the value of the requestState property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestState() {
            return requestState;
        }

        /**
         * Sets the value of the requestState property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestState(String value) {
            this.requestState = value;
        }

        /**
         * Gets the value of the name property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the surname property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSurname() {
            return surname;
        }

        /**
         * Sets the value of the surname property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSurname(String value) {
            this.surname = value;
        }

        /**
         * Gets the value of the address property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAddress() {
            return address;
        }

        /**
         * Sets the value of the address property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAddress(String value) {
            this.address = value;
        }

        /**
         * Gets the value of the postalCode property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * Sets the value of the postalCode property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setPostalCode(String value) {
            this.postalCode = value;
        }

        /**
         * Gets the value of the city property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCity() {
            return city;
        }

        /**
         * Sets the value of the city property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Gets the value of the province property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getProvince() {
            return province;
        }

        /**
         * Sets the value of the province property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setProvince(String value) {
            this.province = value;
        }

        /**
         * Gets the value of the country property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCountry(String value) {
            this.country = value;
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
         * Gets the value of the mobileNumber property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getMobileNumber() {
            return mobileNumber;
        }

        /**
         * Sets the value of the mobileNumber property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setMobileNumber(String value) {
            this.mobileNumber = value;
        }

        /**
         * Gets the value of the email property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the value of the email property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Gets the value of the birthDate property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getBirthDate() {
            return birthDate;
        }

        /**
         * Sets the value of the birthDate property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setBirthDate(String value) {
            this.birthDate = value;
        }

        /**
         * Gets the value of the actuarialAge property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getActuarialAge() {
            return actuarialAge;
        }

        /**
         * Sets the value of the actuarialAge property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setActuarialAge(String value) {
            this.actuarialAge = value;
        }

        /**
         * Gets the value of the birthCity property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getBirthCity() {
            return birthCity;
        }

        /**
         * Sets the value of the birthCity property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setBirthCity(String value) {
            this.birthCity = value;
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
         * Gets the value of the agency property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAgency() {
            return agency;
        }

        /**
         * Sets the value of the agency property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAgency(String value) {
            this.agency = value;
        }

        /**
         * Gets the value of the agent property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAgent() {
            return agent;
        }

        /**
         * Sets the value of the agent property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAgent(String value) {
            this.agent = value;
        }

        /**
         * Gets the value of the agentPhone property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAgentPhone() {
            return agentPhone;
        }

        /**
         * Sets the value of the agentPhone property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAgentPhone(String value) {
            this.agentPhone = value;
        }

        /**
         * Gets the value of the agentEmail property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getAgentEmail() {
            return agentEmail;
        }

        /**
         * Sets the value of the agentEmail property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setAgentEmail(String value) {
            this.agentEmail = value;
        }

        /**
         * Gets the value of the gender property.
         *
         * @return
         *     possible object is
         *     {@link GenderType }
         *
         */
        public GenderType getGender() {
            return gender;
        }

        /**
         * Sets the value of the gender property.
         *
         * @param value
         *     allowed object is
         *     {@link GenderType }
         *
         */
        public void setGender(GenderType value) {
            this.gender = value;
        }

        /**
         * Gets the value of the civilState property.
         *
         * @return
         *     possible object is
         *     {@link CivilStateType }
         *
         */
        public CivilStateType getCivilState() {
            return civilState;
        }

        /**
         * Sets the value of the civilState property.
         *
         * @param value
         *     allowed object is
         *     {@link CivilStateType }
         *
         */
        public void setCivilState(CivilStateType value) {
            this.civilState = value;
        }

        /**
         * Gets the value of the customerType property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCustomerType() {
            return customerType;
        }

        /**
         * Sets the value of the customerType property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCustomerType(String value) {
            this.customerType = value;
        }

        /**
         * Gets the value of the contactTime property.
         *
         * @return
         *     possible object is
         *     {@link ContactTimeType }
         *
         */
        public ContactTimeType getContactTime() {
            return contactTime;
        }

        /**
         * Sets the value of the contactTime property.
         *
         * @param value
         *     allowed object is
         *     {@link ContactTimeType }
         *
         */
        public void setContactTime(ContactTimeType value) {
            this.contactTime = value;
        }

        /**
         * Gets the value of the comments property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getComments() {
            return comments;
        }

        /**
         * Sets the value of the comments property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setComments(String value) {
            this.comments = value;
        }

        /**
         * Gets the value of the euwReferenceCode property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEUWReferenceCode() {
            return euwReferenceCode;
        }

        /**
         * Sets the value of the euwReferenceCode property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEUWReferenceCode(String value) {
            this.euwReferenceCode = value;
        }

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
     *         &lt;element name="dataType" type="{http://www.scortelemed.com/schemas/methislab}QuestionDataType" minOccurs="0"/>
     *         &lt;element name="answerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="answer" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "dataType",
        "answerCode",
        "question",
        "answer"
    })
    public static class PreviousQuestions {

        protected QuestionDataType dataType;
        @XmlElement(required = true)
        protected String answerCode;
        protected String question;
        @XmlElement(required = true)
        protected String answer;

        /**
         * Gets the value of the dataType property.
         *
         * @return
         *     possible object is
         *     {@link QuestionDataType }
         *
         */
        public QuestionDataType getDataType() {
            return dataType;
        }

        /**
         * Sets the value of the dataType property.
         *
         * @param value
         *     allowed object is
         *     {@link QuestionDataType }
         *     
         */
        public void setDataType(QuestionDataType value) {
            this.dataType = value;
        }

        /**
         * Gets the value of the answerCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnswerCode() {
            return answerCode;
        }

        /**
         * Sets the value of the answerCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnswerCode(String value) {
            this.answerCode = value;
        }

        /**
         * Gets the value of the question property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuestion() {
            return question;
        }

        /**
         * Sets the value of the question property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuestion(String value) {
            this.question = value;
        }

        /**
         * Gets the value of the answer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnswer() {
            return answer;
        }

        /**
         * Sets the value of the answer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnswer(String value) {
            this.answer = value;
        }

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
     *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="serviceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="serviceDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "serviceType",
        "serviceCode",
        "serviceDescription"
    })
    public static class Services {

        @XmlElement(required = true)
        protected String serviceType;
        @XmlElement(required = true)
        protected String serviceCode;
        @XmlElement(required = true)
        protected String serviceDescription;

        /**
         * Gets the value of the serviceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceType() {
            return serviceType;
        }

        /**
         * Sets the value of the serviceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceType(String value) {
            this.serviceType = value;
        }

        /**
         * Gets the value of the serviceCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceCode() {
            return serviceCode;
        }

        /**
         * Sets the value of the serviceCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceCode(String value) {
            this.serviceCode = value;
        }

        /**
         * Gets the value of the serviceDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServiceDescription() {
            return serviceDescription;
        }

        /**
         * Sets the value of the serviceDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServiceDescription(String value) {
            this.serviceDescription = value;
        }

    }

}

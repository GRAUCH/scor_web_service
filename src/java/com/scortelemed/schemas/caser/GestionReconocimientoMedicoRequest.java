
package com.scortelemed.schemas.caser;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for gestionReconocimientoMedicoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="gestionReconocimientoMedicoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CandidateInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="gender" type="{http://www.scortelemed.com/schemas/caser}GenderType"/>
 *                   &lt;element name="civilState" type="{http://www.scortelemed.com/schemas/caser}CivilStateType" minOccurs="0"/>
 *                   &lt;element name="mobileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="igp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="agente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
 *                   &lt;element name="operationType" type="{http://www.scortelemed.com/schemas/caser}OperationType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PolicyHolderInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="surname1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="surname2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ServiceInformation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="serviceCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="serviceDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BenefitsType" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="benefictName" type="{http://www.scortelemed.com/schemas/caser}BenefictNameType" minOccurs="0"/>
 *                   &lt;element name="benefictCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="benefictCapital" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "gestionReconocimientoMedicoRequest", propOrder = {
    "candidateInformation",
    "policyHolderInformation",
    "serviceInformation",
    "benefitsType"
})
public class GestionReconocimientoMedicoRequest {

    @XmlElement(name = "CandidateInformation", required = true)
    protected GestionReconocimientoMedicoRequest.CandidateInformation candidateInformation;
    @XmlElement(name = "PolicyHolderInformation", required = true)
    protected GestionReconocimientoMedicoRequest.PolicyHolderInformation policyHolderInformation;
    @XmlElement(name = "ServiceInformation", nillable = true)
    protected List<GestionReconocimientoMedicoRequest.ServiceInformation> serviceInformation;
    @XmlElement(name = "BenefitsType", nillable = true)
    protected List<GestionReconocimientoMedicoRequest.BenefitsType> benefitsType;

    /**
     * Gets the value of the candidateInformation property.
     * 
     * @return
     *     possible object is
     *     {@link GestionReconocimientoMedicoRequest.CandidateInformation }
     *     
     */
    public GestionReconocimientoMedicoRequest.CandidateInformation getCandidateInformation() {
        return candidateInformation;
    }

    /**
     * Sets the value of the candidateInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GestionReconocimientoMedicoRequest.CandidateInformation }
     *     
     */
    public void setCandidateInformation(GestionReconocimientoMedicoRequest.CandidateInformation value) {
        this.candidateInformation = value;
    }

    /**
     * Gets the value of the policyHolderInformation property.
     * 
     * @return
     *     possible object is
     *     {@link GestionReconocimientoMedicoRequest.PolicyHolderInformation }
     *     
     */
    public GestionReconocimientoMedicoRequest.PolicyHolderInformation getPolicyHolderInformation() {
        return policyHolderInformation;
    }

    /**
     * Sets the value of the policyHolderInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GestionReconocimientoMedicoRequest.PolicyHolderInformation }
     *     
     */
    public void setPolicyHolderInformation(GestionReconocimientoMedicoRequest.PolicyHolderInformation value) {
        this.policyHolderInformation = value;
    }

    /**
     * Gets the value of the serviceInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GestionReconocimientoMedicoRequest.ServiceInformation }
     * 
     * 
     */
    public List<GestionReconocimientoMedicoRequest.ServiceInformation> getServiceInformation() {
        if (serviceInformation == null) {
            serviceInformation = new ArrayList<GestionReconocimientoMedicoRequest.ServiceInformation>();
        }
        return this.serviceInformation;
    }

    /**
     * Gets the value of the benefitsType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the benefitsType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBenefitsType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GestionReconocimientoMedicoRequest.BenefitsType }
     * 
     * 
     */
    public List<GestionReconocimientoMedicoRequest.BenefitsType> getBenefitsType() {
        if (benefitsType == null) {
            benefitsType = new ArrayList<GestionReconocimientoMedicoRequest.BenefitsType>();
        }
        return this.benefitsType;
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
     *         &lt;element name="benefictName" type="{http://www.scortelemed.com/schemas/caser}BenefictNameType" minOccurs="0"/>
     *         &lt;element name="benefictCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="benefictCapital" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "benefictName",
        "benefictCode",
        "benefictCapital"
    })
    public static class BenefitsType {

        protected BenefictNameType benefictName;
        @XmlElement(required = true)
        protected String benefictCode;
        @XmlElement(required = true)
        protected String benefictCapital;

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
     *         &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="gender" type="{http://www.scortelemed.com/schemas/caser}GenderType"/>
     *         &lt;element name="civilState" type="{http://www.scortelemed.com/schemas/caser}CivilStateType" minOccurs="0"/>
     *         &lt;element name="mobileNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="igp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="agente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/>
     *         &lt;element name="operationType" type="{http://www.scortelemed.com/schemas/caser}OperationType" minOccurs="0"/>
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
        "fiscalIdentificationNumber",
        "name",
        "surname",
        "address",
        "city",
        "province",
        "country",
        "postalCode",
        "email",
        "gender",
        "civilState",
        "mobileNumber",
        "phoneNumber1",
        "phoneNumber2",
        "password",
        "igp",
        "agente",
        "productCode",
        "birthDate",
        "operationType"
    })
    public static class CandidateInformation {

        @XmlElement(required = true)
        protected String fiscalIdentificationNumber;
        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String surname;
        protected String address;
        protected String city;
        protected String province;
        protected String country;
        protected String postalCode;
        protected String email;
        @XmlElement(required = true, nillable = true)
        protected GenderType gender;
        protected MaritalStatusType civilState;
        @XmlElement(required = true)
        protected String mobileNumber;
        protected String phoneNumber1;
        protected String phoneNumber2;
        protected String password;
        protected String igp;
        protected String agente;
        @XmlElement(required = true)
        protected String productCode;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar birthDate;
        protected OperationType operationType;

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
         *     {@link MaritalStatusType }
         *     
         */
        public MaritalStatusType getCivilState() {
            return civilState;
        }

        /**
         * Sets the value of the civilState property.
         * 
         * @param value
         *     allowed object is
         *     {@link MaritalStatusType }
         *     
         */
        public void setCivilState(MaritalStatusType value) {
            this.civilState = value;
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
         * Gets the value of the igp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIgp() {
            return igp;
        }

        /**
         * Sets the value of the igp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIgp(String value) {
            this.igp = value;
        }

        /**
         * Gets the value of the agente property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAgente() {
            return agente;
        }

        /**
         * Sets the value of the agente property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAgente(String value) {
            this.agente = value;
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
         * Gets the value of the birthDate property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public XMLGregorianCalendar getBirthDate() {
            return birthDate;
        }

        /**
         * Sets the value of the birthDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
        }

        /**
         * Gets the value of the operationType property.
         * 
         * @return
         *     possible object is
         *     {@link OperationType }
         *     
         */
        public OperationType getOperationType() {
            return operationType;
        }

        /**
         * Sets the value of the operationType property.
         * 
         * @param value
         *     allowed object is
         *     {@link OperationType }
         *     
         */
        public void setOperationType(OperationType value) {
            this.operationType = value;
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
     *         &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="surname1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="surname2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "fiscalIdentificationNumber",
        "name",
        "surname1",
        "surname2",
        "policyNumber",
        "certificateNumber",
        "requestDate",
        "requestNumber",
        "comments"
    })
    public static class PolicyHolderInformation {

        @XmlElement(required = true)
        protected String fiscalIdentificationNumber;
        @XmlElement(required = true)
        protected String name;
        protected String surname1;
        protected String surname2;
        @XmlElement(required = true)
        protected String policyNumber;
        @XmlElement(required = true)
        protected String certificateNumber;
        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestDate;
        @XmlElement(required = true)
        protected String requestNumber;
        @XmlElement(required = true)
        protected String comments;

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
         * Gets the value of the surname1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurname1() {
            return surname1;
        }

        /**
         * Sets the value of the surname1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurname1(String value) {
            this.surname1 = value;
        }

        /**
         * Gets the value of the surname2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSurname2() {
            return surname2;
        }

        /**
         * Sets the value of the surname2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSurname2(String value) {
            this.surname2 = value;
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
     *         &lt;element name="serviceDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    public static class ServiceInformation {

        @XmlElement(required = true)
        protected String serviceType;
        @XmlElement(required = true)
        protected String serviceCode;
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

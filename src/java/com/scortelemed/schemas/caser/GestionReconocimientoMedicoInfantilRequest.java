package com.scortelemed.schemas.caser;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gestionReconocimientoMedicoInfantilRequest", propOrder = {
        "policyInformation",
        "candidateInformation"
})
public class GestionReconocimientoMedicoInfantilRequest {
    @XmlElement(name = "PolicyInformation", required = true)
    protected PolicyInformation policyInformation;
    @XmlElement(name = "CandidateInformation", required = true)
    protected List<GestionReconocimientoMedicoInfantilRequest.CandidateInformation> candidateInformation;

    @Override
    public String toString() {
        return "GestionReconocimientoMedicoInfantilRequest{" +
                "policyInformation=" + policyInformation +
                ", candidateInformation=" + candidateInformation +
                '}';
    }

    /**
     * Gets the value of the policyInformation property.
     *
     * @return possible object is
     * {@link PolicyInformation }
     */
    public PolicyInformation getPolicyInformation() {
        return policyInformation;
    }

    /**
     * Sets the value of the policyInformation property.
     *
     * @param value allowed object is
     *              {@link PolicyInformation }
     */
    public void setPolicyInformation(PolicyInformation value) {
        this.policyInformation = value;
    }

    /**
     * Gets the value of the candidateInformation property.
     *
     * @return possible object is
     * {@link GestionReconocimientoMedicoInfantilRequest.CandidateInformation }
     */
    public List<GestionReconocimientoMedicoInfantilRequest.CandidateInformation> getCandidateInformation() {
        if (candidateInformation == null) {
            candidateInformation = new ArrayList<>();
        }
        return candidateInformation;
    }

    /**
     * Sets the value of the candidateInformation property.
     *
     * @param value allowed object is
     *              {@link GestionReconocimientoMedicoInfantilRequest.CandidateInformation }
     */
    public void setCandidateInformation(List<GestionReconocimientoMedicoInfantilRequest.CandidateInformation> value) {
        this.candidateInformation = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "policyNumber",
            "certificateNumber",
            "productCode",
            "requestNumber",
            "requestDate",
            "comments"
    })
    public static class PolicyInformation {

        @XmlElement(required = true)
        protected String policyNumber;
        @XmlElement(required = true)
        protected String certificateNumber;
        @XmlElement(required = true)
        protected String productCode;
        @XmlElement(required = true)
        protected String requestNumber;
        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestDate;
        @XmlElement(required = true)
        protected String comments;

        @Override
        public String toString() {
            return "PolicyInformation{" +
                    "policyNumber='" + policyNumber + '\'' +
                    ", certificateNumber='" + certificateNumber + '\'' +
                    ", productCode='" + productCode + '\'' +
                    ", requestNumber='" + requestNumber + '\'' +
                    ", requestDate=" + requestDate +
                    ", comments='" + comments + '\'' +
                    '}';
        }

        /**
         * Gets the value of the policyNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPolicyNumber() {
            return policyNumber;
        }

        /**
         * Sets the value of the policyNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPolicyNumber(String value) {
            this.policyNumber = value;
        }

        /**
         * Gets the value of the certificateNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCertificateNumber() {
            return certificateNumber;
        }

        /**
         * Sets the value of the certificateNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCertificateNumber(String value) {
            this.certificateNumber = value;
        }

        /**
         * Gets the value of the productCode property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getProductCode() {
            return productCode;
        }

        /**
         * Sets the value of the productCode property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setProductCode(String value) {
            this.productCode = value;
        }

        /**
         * Gets the value of the requestNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getRequestNumber() {
            return requestNumber;
        }

        /**
         * Sets the value of the requestNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setRequestNumber(String value) {
            this.requestNumber = value;
        }

        /**
         * Gets the value of the requestDate property.
         *
         * @return possible object is
         * {@link XMLGregorianCalendar }
         */
        public XMLGregorianCalendar getRequestDate() {
            return requestDate;
        }

        /**
         * Sets the value of the requestDate property.
         *
         * @param value allowed object is
         *              {@link XMLGregorianCalendar }
         */
        public void setRequestDate(XMLGregorianCalendar value) {
            this.requestDate = value;
        }

        /**
         * Gets the value of the comments property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getComments() {
            return comments;
        }

        /**
         * Sets the value of the comments property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setComments(String value) {
            this.comments = value;
        }

    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "tutor",
            "identificationCode",
            "name",
            "surname",
            "gender",
            "maritalStatus",
            "birthDate",
            "address",
            "city",
            "province",
            "country",
            "postalCode",
            "email",
            "mobileNumber",
            "phoneNumber1",
            "phoneNumber2",
            "agent",
            "serviceInformation",
            "benefitsType"
    })
    public static class CandidateInformation {

        @XmlElement(required = true)
        protected Boolean tutor;
        @XmlElement(required = true)
        protected String identificationCode;
        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String surname;
        @XmlElement(required = true, nillable = true)
        protected GenderType gender;
        protected MaritalStatusType maritalStatus;
        @XmlElement(required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar birthDate;
        protected String address;
        protected String city;
        protected String province;
        protected String country;
        protected String postalCode;
        protected String email;
        @XmlElement(required = true)
        protected String mobileNumber;
        protected String phoneNumber1;
        protected String phoneNumber2;
        protected String agent;
        @XmlElement(name = "ServiceInformation", nillable = true)
        protected List<GestionReconocimientoMedicoInfantilRequest.ServiceInformation> serviceInformation;
        @XmlElement(name = "BenefitsType", required = true)
        protected List<GestionReconocimientoMedicoInfantilRequest.BenefitsType> benefitsType;

        @Override
        public String toString() {
            return "CandidateInformation{" +
                    "tutor=" + tutor +
                    ", identificationCode='" + identificationCode + '\'' +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", gender=" + gender +
                    ", maritalStatus=" + maritalStatus +
                    ", birthDate=" + birthDate +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", province='" + province + '\'' +
                    ", country='" + country + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    ", email='" + email + '\'' +
                    ", mobileNumber='" + mobileNumber + '\'' +
                    ", phoneNumber1='" + phoneNumber1 + '\'' +
                    ", phoneNumber2='" + phoneNumber2 + '\'' +
                    ", agent='" + agent + '\'' +
                    ", serviceInformation=" + serviceInformation +
                    ", benefitsType=" + benefitsType +
                    '}';
        }

        /**
         * Gets the value of the policyHolder property.
         *
         * @return possible object is
         * {@link Boolean }
         */
        public Boolean getTutor() {
            return tutor;
        }

        /**
         * Sets the value of the policyHolder property.
         *
         * @param value allowed object is
         *              {@link Boolean }
         */
        public void setTutor(Boolean value) {
            this.tutor = value;
        }

        /**
         * Gets the value of the fiscalIdentificationNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getIdentificationCode() {
            return identificationCode;
        }

        /**
         * Sets the value of the fiscalIdentificationNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setIdentificationCode(String value) {
            this.identificationCode = value;
        }

        /**
         * Gets the value of the name property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the surname property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSurname() {
            return surname;
        }

        /**
         * Sets the value of the surname property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSurname(String value) {
            this.surname = value;
        }

        /**
         * Gets the value of the birthDate property.
         *
         * @return possible object is
         * {@link Object }
         */
        public XMLGregorianCalendar getBirthDate() {
            return birthDate;
        }

        /**
         * Sets the value of the birthDate property.
         *
         * @param value allowed object is
         *              {@link Object }
         */
        public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
        }

        /**
         * Gets the value of the address property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getAddress() {
            return address;
        }

        /**
         * Sets the value of the address property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setAddress(String value) {
            this.address = value;
        }

        /**
         * Gets the value of the city property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCity() {
            return city;
        }

        /**
         * Sets the value of the city property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Gets the value of the province property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getProvince() {
            return province;
        }

        /**
         * Sets the value of the province property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setProvince(String value) {
            this.province = value;
        }

        /**
         * Gets the value of the country property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCountry(String value) {
            this.country = value;
        }

        /**
         * Gets the value of the postalCode property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * Sets the value of the postalCode property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPostalCode(String value) {
            this.postalCode = value;
        }

        /**
         * Gets the value of the email property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the value of the email property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setEmail(String value) {
            this.email = value;
        }

        /**
         * Gets the value of the gender property.
         *
         * @return possible object is
         * {@link GenderType }
         */
        public GenderType getGender() {
            return gender;
        }

        /**
         * Sets the value of the gender property.
         *
         * @param value allowed object is
         *              {@link GenderType }
         */
        public void setGender(GenderType value) {
            this.gender = value;
        }

        /**
         * Gets the value of the civilState property.
         *
         * @return possible object is
         * {@link MaritalStatusType }
         */
        public MaritalStatusType getMaritalStatus() {
            return maritalStatus;
        }

        /**
         * Sets the value of the civilState property.
         *
         * @param value allowed object is
         *              {@link MaritalStatusType }
         */
        public void setMaritalStatus(MaritalStatusType value) {
            this.maritalStatus = value;
        }

        /**
         * Gets the value of the mobileNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getMobileNumber() {
            return mobileNumber;
        }

        /**
         * Sets the value of the mobileNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setMobileNumber(String value) {
            this.mobileNumber = value;
        }

        /**
         * Gets the value of the phoneNumber1 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPhoneNumber1() {
            return phoneNumber1;
        }

        /**
         * Sets the value of the phoneNumber1 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPhoneNumber1(String value) {
            this.phoneNumber1 = value;
        }

        /**
         * Gets the value of the phoneNumber2 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPhoneNumber2() {
            return phoneNumber2;
        }

        /**
         * Sets the value of the phoneNumber2 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPhoneNumber2(String value) {
            this.phoneNumber2 = value;
        }

        /**
         * Gets the value of the agent property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getAgent() {
            return agent;
        }

        /**
         * Sets the value of the agent property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setAgent(String value) {
            this.agent = value;
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
         * {@link GestionReconocimientoMedicoInfantilRequest.ServiceInformation }
         */
        public List<GestionReconocimientoMedicoInfantilRequest.ServiceInformation> getServiceInformation() {
            if (serviceInformation == null) {
                serviceInformation = new ArrayList<>();
            }
            return this.serviceInformation;
        }

        /**
         * Sets the value of the ServiceInformation property.
         *
         * @param value allowed object is
         *              {@link GestionReconocimientoMedicoInfantilRequest.ServiceInformation }
         */
        public void setCandidateInformation(List<GestionReconocimientoMedicoInfantilRequest.ServiceInformation> value) {
            this.serviceInformation = value;
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
         * {@link GestionReconocimientoMedicoInfantilRequest.BenefitsType }
         */
        public List<GestionReconocimientoMedicoInfantilRequest.BenefitsType> getBenefitsType() {
            if (benefitsType == null) {
                benefitsType = new ArrayList<>();
            }
            return this.benefitsType;
        }

        /**
         * Sets the value of the BenefitsType property.
         *
         * @param value allowed object is
         *              {@link GestionReconocimientoMedicoInfantilRequest.BenefitsType }
         */
        public void setBenefitsType(List<GestionReconocimientoMedicoInfantilRequest.BenefitsType> value) {
            this.benefitsType = value;
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

        @Override
        public String toString() {
            return "ServiceInformation{" +
                    "serviceType='" + serviceType + '\'' +
                    ", serviceCode='" + serviceCode + '\'' +
                    ", serviceDescription='" + serviceDescription + '\'' +
                    '}';
        }

        /**
         * Gets the value of the serviceType property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getServiceType() {
            return serviceType;
        }

        /**
         * Sets the value of the serviceType property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setServiceType(String value) {
            this.serviceType = value;
        }

        /**
         * Gets the value of the serviceCode property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getServiceCode() {
            return serviceCode;
        }

        /**
         * Sets the value of the serviceCode property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setServiceCode(String value) {
            this.serviceCode = value;
        }

        /**
         * Gets the value of the serviceDescription property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getServiceDescription() {
            return serviceDescription;
        }

        /**
         * Sets the value of the serviceDescription property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setServiceDescription(String value) {
            this.serviceDescription = value;
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
     *         &lt;element name="benefictName" type="{http://www.scortelemed.com/schemas/caser}BenefictNameType" minOccurs="0"/>
     *         &lt;element name="benefictCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="benefictCapital" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "benefictCode",
            "benefictCapital"
    })
    public static class BenefitsType {
        @XmlElement(required = true)
        protected String benefictCode;
        @XmlElement(required = true)
        protected String benefictCapital;

        @Override
        public String toString() {
            return "BenefitsType{" +
                    ", benefictCode='" + benefictCode + '\'' +
                    ", benefictCapital='" + benefictCapital + '\'' +
                    '}';
        }

        /**
         * Gets the value of the benefictCode property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getBenefictCode() {
            return benefictCode;
        }

        /**
         * Sets the value of the benefictCode property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setBenefictCode(String value) {
            this.benefictCode = value;
        }

        /**
         * Gets the value of the benefictCapital property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getBenefictCapital() {
            return benefictCapital;
        }

        /**
         * Sets the value of the benefictCapital property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setBenefictCapital(String value) {
            this.benefictCapital = value;
        }

    }
}


package com.scortelemed.schemas.caser;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for consultaExpedienteResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="consultaExpedienteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="status" type="{http://www.scortelemed.com/schemas/caser}StatusType"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExpedienteConsulta" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="requestNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="requestState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="benefitsList" type="{http://www.scortelemed.com/schemas/caser}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
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
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaExpedienteResponse", propOrder = {
        "date",
        "status",
        "notes",
        "expedienteConsulta"
})
public class ConsultaExpedienteResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected StatusType status;
    @XmlElement(required = true)
    protected String notes;
    @XmlElement(name = "ExpedienteConsulta", nillable = true)
    protected List<ConsultaExpedienteResponse.ExpedienteConsulta> expedienteConsulta;

    /**
     * Gets the value of the date property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link StatusType }
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link StatusType }
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the notes property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the expedienteConsulta property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the expedienteConsulta property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExpedienteConsulta().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultaExpedienteResponse.ExpedienteConsulta }
     */
    public List<ConsultaExpedienteResponse.ExpedienteConsulta> getExpedienteConsulta() {
        if (expedienteConsulta == null) {
            expedienteConsulta = new ArrayList<ConsultaExpedienteResponse.ExpedienteConsulta>();
        }
        return this.expedienteConsulta;
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
     *         &lt;element name="requestState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="policyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="certificateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fiscalIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mobilePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="phoneNumber2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="benefitsList" type="{http://www.scortelemed.com/schemas/caser}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="Zip" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
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
            "notes",
            "domicilio",
            "codPostal",
            "localidad",
            "provincia"

    })
    public static class ExpedienteConsulta {

        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar requestDate;
        @XmlElement(required = true)
        protected String requestNumber;
        @XmlElement(required = true)
        protected String requestState;
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

        @XmlElement(nillable = true)
        protected String domicilio;
        @XmlElement(nillable = true)
        protected String codPostal;
        @XmlElement(nillable = true)
        protected String localidad;
        @XmlElement(nillable = true)
        protected String provincia;




        public String getCodPostal() {
            return codPostal;
        }

        public void setCodPostal(String codPostal) {
            this.codPostal = codPostal;
        }

        public String getLocalidad() {
            return localidad;
        }

        public void setLocalidad(String localidad) {
            this.localidad = localidad;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getDomicilio() {
            return domicilio;
        }

        public void setDomicilio(String domicilio) {
            this.domicilio = domicilio;
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
         * Gets the value of the requestState property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getRequestState() {
            return requestState;
        }

        /**
         * Sets the value of the requestState property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setRequestState(String value) {
            this.requestState = value;
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
         * Gets the value of the fiscalIdentificationNumber property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getFiscalIdentificationNumber() {
            return fiscalIdentificationNumber;
        }

        /**
         * Sets the value of the fiscalIdentificationNumber property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setFiscalIdentificationNumber(String value) {
            this.fiscalIdentificationNumber = value;
        }

        /**
         * Gets the value of the mobilePhone property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getMobilePhone() {
            return mobilePhone;
        }

        /**
         * Sets the value of the mobilePhone property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setMobilePhone(String value) {
            this.mobilePhone = value;
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
         * @return possible object is
         * byte[]
         */
        public byte[] getZip() {
            return zip;
        }

        /**
         * Sets the value of the zip property.
         *
         * @param value allowed object is
         *              byte[]
         */
        public void setZip(byte[] value) {
            this.zip = ((byte[]) value);
        }

        /**
         * Gets the value of the notes property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getNotes() {
            return notes;
        }

        /**
         * Sets the value of the notes property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setNotes(String value) {
            this.notes = value;
        }

    }

}

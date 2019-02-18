
package com.scortelemed.schemas.ama;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="status" type="{http://www.scortelemed.com/schemas/ama}StatusType"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Expediente" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="candidateInformation" type="{http://www.scortelemed.com/schemas/ama}CandidateInformationType"/>
 *                   &lt;element name="policyHolderInformation" type="{http://www.scortelemed.com/schemas/ama}PolicyHolderInformationType"/>
 *                   &lt;element name="benefits" type="{http://www.scortelemed.com/schemas/ama}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="appointments" type="{http://www.scortelemed.com/schemas/ama}AppointmentsType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="activities" type="{http://www.scortelemed.com/schemas/ama}ActivitiesType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="documents" type="{http://www.scortelemed.com/schemas/ama}DocumentType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "consultaExpedienteResponse", propOrder = {
    "date",
    "status",
    "notes",
    "expediente"
})
public class ConsultaExpedienteResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true)
    protected StatusType status;
    @XmlElement(required = true)
    protected String notes;
    @XmlElement(name = "Expediente", nillable = true)
    protected List<ConsultaExpedienteResponse.Expediente> expediente;

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
     * {@link ConsultaExpedienteResponse.Expediente }
     * 
     * 
     */
    public List<ConsultaExpedienteResponse.Expediente> getExpediente() {
        if (expediente == null) {
            expediente = new ArrayList<ConsultaExpedienteResponse.Expediente>();
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
     *         &lt;element name="candidateInformation" type="{http://www.scortelemed.com/schemas/ama}CandidateInformationType"/>
     *         &lt;element name="policyHolderInformation" type="{http://www.scortelemed.com/schemas/ama}PolicyHolderInformationType"/>
     *         &lt;element name="benefits" type="{http://www.scortelemed.com/schemas/ama}BenefitsType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="appointments" type="{http://www.scortelemed.com/schemas/ama}AppointmentsType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="activities" type="{http://www.scortelemed.com/schemas/ama}ActivitiesType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="documents" type="{http://www.scortelemed.com/schemas/ama}DocumentType" maxOccurs="unbounded" minOccurs="0"/>
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
        "candidateInformation",
        "policyHolderInformation",
        "benefits",
        "appointments",
        "activities",
        "documents"
    })
    public static class Expediente {

        @XmlElement(required = true)
        protected CandidateInformationType candidateInformation;
        @XmlElement(required = true)
        protected PolicyHolderInformationType policyHolderInformation;
        @XmlElement(nillable = true)
        protected List<BenefitsType> benefits;
        @XmlElement(nillable = true)
        protected List<AppointmentsType> appointments;
        @XmlElement(nillable = true)
        protected List<ActivitiesType> activities;
        @XmlElement(nillable = true)
        protected List<DocumentType> documents;

        /**
         * Gets the value of the candidateInformation property.
         * 
         * @return
         *     possible object is
         *     {@link CandidateInformationType }
         *     
         */
        public CandidateInformationType getCandidateInformation() {
            return candidateInformation;
        }

        /**
         * Sets the value of the candidateInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link CandidateInformationType }
         *     
         */
        public void setCandidateInformation(CandidateInformationType value) {
            this.candidateInformation = value;
        }

        /**
         * Gets the value of the policyHolderInformation property.
         * 
         * @return
         *     possible object is
         *     {@link PolicyHolderInformationType }
         *     
         */
        public PolicyHolderInformationType getPolicyHolderInformation() {
            return policyHolderInformation;
        }

        /**
         * Sets the value of the policyHolderInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link PolicyHolderInformationType }
         *     
         */
        public void setPolicyHolderInformation(PolicyHolderInformationType value) {
            this.policyHolderInformation = value;
        }

        /**
         * Gets the value of the benefits property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the benefits property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBenefits().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BenefitsType }
         * 
         * 
         */
        public List<BenefitsType> getBenefits() {
            if (benefits == null) {
                benefits = new ArrayList<BenefitsType>();
            }
            return this.benefits;
        }

        /**
         * Gets the value of the appointments property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the appointments property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAppointments().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AppointmentsType }
         * 
         * 
         */
        public List<AppointmentsType> getAppointments() {
            if (appointments == null) {
                appointments = new ArrayList<AppointmentsType>();
            }
            return this.appointments;
        }

        /**
         * Gets the value of the activities property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the activities property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActivities().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ActivitiesType }
         * 
         * 
         */
        public List<ActivitiesType> getActivities() {
            if (activities == null) {
                activities = new ArrayList<ActivitiesType>();
            }
            return this.activities;
        }

        /**
         * Gets the value of the documents property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the documents property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDocuments().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DocumentType }
         * 
         * 
         */
        public List<DocumentType> getDocuments() {
            if (documents == null) {
                documents = new ArrayList<DocumentType>();
            }
            return this.documents;
        }

    }

}

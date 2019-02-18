
package com.scortelemed.schemas.ama;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resultadoSiniestroResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultadoSiniestroResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="estado" type="{http://www.scortelemed.com/schemas/ama}StatusType"/>
 *         &lt;element name="notas" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Expediente" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="interviniente" type="{http://www.scortelemed.com/schemas/ama}Interviniente" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fechaDeEstado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="motivoEstado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="situacionPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="idOperacionConsultaPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="numeroPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="numeroSuplemento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="numeroCertificado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fechaAltaPoliza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="fechaSolicitudPoliza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="producto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Siniestro">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="numeroSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="causa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="coberturaSiniestrada" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="limitacionFuncional" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="fechaOcurrencia" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="fechaDeclaracion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="rentaCapital" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="descripcionSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="textoBeneficiarios" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="fechaBajaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="fechaAltaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="fechaInicioDerecho" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="diasBajaEstimados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="oficinaGestora" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Pago" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="subtipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="metodoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="tipoPerceptor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="tipoImpuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="intervinienteBeneficiario" type="{http://www.scortelemed.com/schemas/ama}Interviniente"/>
 *                                       &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                       &lt;element name="fechaInicioPago" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                       &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="fechaFactura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                       &lt;element name="importeBruto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="importeNeto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Provision" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="idProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="tipoProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="subcodigoReserva" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="fechaProvision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                       &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
@XmlType(name = "resultadoSiniestroResponse", propOrder = {
    "fecha",
    "estado",
    "notas",
    "expediente"
})
public class ResultadoSiniestroResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true)
    protected StatusType estado;
    @XmlElement(required = true)
    protected String notas;
    @XmlElement(name = "Expediente", nillable = true)
    protected List<ResultadoSiniestroResponse.Expediente> expediente;

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setEstado(StatusType value) {
        this.estado = value;
    }

    /**
     * Gets the value of the notas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Sets the value of the notas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotas(String value) {
        this.notas = value;
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
     * {@link ResultadoSiniestroResponse.Expediente }
     * 
     * 
     */
    public List<ResultadoSiniestroResponse.Expediente> getExpediente() {
        if (expediente == null) {
            expediente = new ArrayList<ResultadoSiniestroResponse.Expediente>();
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
     *         &lt;element name="interviniente" type="{http://www.scortelemed.com/schemas/ama}Interviniente" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fechaDeEstado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="motivoEstado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="situacionPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="idOperacionConsultaPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="numeroPoliza" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="numeroSuplemento" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="numeroCertificado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fechaAltaPoliza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="fechaSolicitudPoliza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="producto" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Siniestro">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="numeroSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="causa" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="coberturaSiniestrada" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="limitacionFuncional" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="fechaOcurrencia" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="fechaDeclaracion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="rentaCapital" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="descripcionSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="textoBeneficiarios" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="fechaBajaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="fechaAltaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="fechaInicioDerecho" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="diasBajaEstimados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="oficinaGestora" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Pago" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="subtipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="metodoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="tipoPerceptor" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="tipoImpuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="intervinienteBeneficiario" type="{http://www.scortelemed.com/schemas/ama}Interviniente"/>
     *                             &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                             &lt;element name="fechaInicioPago" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                             &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="fechaFactura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                             &lt;element name="importeBruto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="importeNeto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Provision" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="idProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="tipoProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="subcodigoReserva" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="fechaProvision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                             &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
    @XmlType(name = "", propOrder = {
        "interviniente",
        "codigoST",
        "estado",
        "fechaDeEstado",
        "motivoEstado",
        "situacionPoliza",
        "idOperacionConsultaPoliza",
        "numeroPoliza",
        "numeroSuplemento",
        "numeroCertificado",
        "fechaAltaPoliza",
        "fechaSolicitudPoliza",
        "producto",
        "siniestro"
    })
    public static class Expediente {

        @XmlElement(nillable = true)
        protected List<Interviniente> interviniente;
        @XmlElement(required = true)
        protected String codigoST;
        @XmlElement(required = true)
        protected String estado;
        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar fechaDeEstado;
        @XmlElement(required = true)
        protected String motivoEstado;
        @XmlElement(required = true)
        protected String situacionPoliza;
        @XmlElement(required = true)
        protected String idOperacionConsultaPoliza;
        @XmlElement(required = true)
        protected String numeroPoliza;
        @XmlElement(required = true)
        protected String numeroSuplemento;
        @XmlElement(required = true)
        protected String numeroCertificado;
        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar fechaAltaPoliza;
        @XmlElement(required = true, nillable = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar fechaSolicitudPoliza;
        @XmlElement(required = true)
        protected String producto;
        @XmlElement(name = "Siniestro", required = true)
        protected ResultadoSiniestroResponse.Expediente.Siniestro siniestro;

        /**
         * Gets the value of the interviniente property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the interviniente property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInterviniente().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Interviniente }
         * 
         * 
         */
        public List<Interviniente> getInterviniente() {
            if (interviniente == null) {
                interviniente = new ArrayList<Interviniente>();
            }
            return this.interviniente;
        }

        /**
         * Gets the value of the codigoST property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodigoST() {
            return codigoST;
        }

        /**
         * Sets the value of the codigoST property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodigoST(String value) {
            this.codigoST = value;
        }

        /**
         * Gets the value of the estado property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstado() {
            return estado;
        }

        /**
         * Sets the value of the estado property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstado(String value) {
            this.estado = value;
        }

        /**
         * Gets the value of the fechaDeEstado property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFechaDeEstado() {
            return fechaDeEstado;
        }

        /**
         * Sets the value of the fechaDeEstado property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFechaDeEstado(XMLGregorianCalendar value) {
            this.fechaDeEstado = value;
        }

        /**
         * Gets the value of the motivoEstado property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMotivoEstado() {
            return motivoEstado;
        }

        /**
         * Sets the value of the motivoEstado property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMotivoEstado(String value) {
            this.motivoEstado = value;
        }

        /**
         * Gets the value of the situacionPoliza property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSituacionPoliza() {
            return situacionPoliza;
        }

        /**
         * Sets the value of the situacionPoliza property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSituacionPoliza(String value) {
            this.situacionPoliza = value;
        }

        /**
         * Gets the value of the idOperacionConsultaPoliza property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdOperacionConsultaPoliza() {
            return idOperacionConsultaPoliza;
        }

        /**
         * Sets the value of the idOperacionConsultaPoliza property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdOperacionConsultaPoliza(String value) {
            this.idOperacionConsultaPoliza = value;
        }

        /**
         * Gets the value of the numeroPoliza property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroPoliza() {
            return numeroPoliza;
        }

        /**
         * Sets the value of the numeroPoliza property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroPoliza(String value) {
            this.numeroPoliza = value;
        }

        /**
         * Gets the value of the numeroSuplemento property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroSuplemento() {
            return numeroSuplemento;
        }

        /**
         * Sets the value of the numeroSuplemento property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroSuplemento(String value) {
            this.numeroSuplemento = value;
        }

        /**
         * Gets the value of the numeroCertificado property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroCertificado() {
            return numeroCertificado;
        }

        /**
         * Sets the value of the numeroCertificado property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroCertificado(String value) {
            this.numeroCertificado = value;
        }

        /**
         * Gets the value of the fechaAltaPoliza property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFechaAltaPoliza() {
            return fechaAltaPoliza;
        }

        /**
         * Sets the value of the fechaAltaPoliza property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFechaAltaPoliza(XMLGregorianCalendar value) {
            this.fechaAltaPoliza = value;
        }

        /**
         * Gets the value of the fechaSolicitudPoliza property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFechaSolicitudPoliza() {
            return fechaSolicitudPoliza;
        }

        /**
         * Sets the value of the fechaSolicitudPoliza property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFechaSolicitudPoliza(XMLGregorianCalendar value) {
            this.fechaSolicitudPoliza = value;
        }

        /**
         * Gets the value of the producto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProducto() {
            return producto;
        }

        /**
         * Sets the value of the producto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProducto(String value) {
            this.producto = value;
        }

        /**
         * Gets the value of the siniestro property.
         * 
         * @return
         *     possible object is
         *     {@link ResultadoSiniestroResponse.Expediente.Siniestro }
         *     
         */
        public ResultadoSiniestroResponse.Expediente.Siniestro getSiniestro() {
            return siniestro;
        }

        /**
         * Sets the value of the siniestro property.
         * 
         * @param value
         *     allowed object is
         *     {@link ResultadoSiniestroResponse.Expediente.Siniestro }
         *     
         */
        public void setSiniestro(ResultadoSiniestroResponse.Expediente.Siniestro value) {
            this.siniestro = value;
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
         *         &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="numeroSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="causa" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="coberturaSiniestrada" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="limitacionFuncional" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="fechaOcurrencia" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="fechaDeclaracion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="rentaCapital" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="descripcionSiniestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="textoBeneficiarios" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="fechaBajaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="fechaAltaMedica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="fechaInicioDerecho" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="diasBajaEstimados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="oficinaGestora" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Pago" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="subtipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="metodoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="tipoPerceptor" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="tipoImpuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="intervinienteBeneficiario" type="{http://www.scortelemed.com/schemas/ama}Interviniente"/>
         *                   &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                   &lt;element name="fechaInicioPago" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                   &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="fechaFactura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                   &lt;element name="importeBruto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="importeNeto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Provision" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="idProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="tipoProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="subcodigoReserva" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="fechaProvision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                   &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
        @XmlType(name = "", propOrder = {
            "codigoST",
            "numeroSiniestro",
            "causa",
            "coberturaSiniestrada",
            "limitacionFuncional",
            "fechaOcurrencia",
            "fechaDeclaracion",
            "rentaCapital",
            "descripcionSiniestro",
            "textoBeneficiarios",
            "fechaBajaMedica",
            "fechaAltaMedica",
            "fechaInicioDerecho",
            "diasBajaEstimados",
            "oficinaGestora",
            "pago",
            "provision"
        })
        public static class Siniestro {

            @XmlElement(required = true)
            protected String codigoST;
            @XmlElement(required = true)
            protected String numeroSiniestro;
            @XmlElement(required = true)
            protected String causa;
            @XmlElement(required = true)
            protected String coberturaSiniestrada;
            @XmlElement(required = true)
            protected String limitacionFuncional;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar fechaOcurrencia;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar fechaDeclaracion;
            @XmlElement(required = true)
            protected BigDecimal rentaCapital;
            @XmlElement(required = true)
            protected String descripcionSiniestro;
            @XmlElement(required = true)
            protected String textoBeneficiarios;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar fechaBajaMedica;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar fechaAltaMedica;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar fechaInicioDerecho;
            @XmlElement(required = true)
            protected BigDecimal diasBajaEstimados;
            @XmlElement(required = true)
            protected String oficinaGestora;
            @XmlElement(name = "Pago", nillable = true)
            protected List<ResultadoSiniestroResponse.Expediente.Siniestro.Pago> pago;
            @XmlElement(name = "Provision", nillable = true)
            protected List<ResultadoSiniestroResponse.Expediente.Siniestro.Provision> provision;

            /**
             * Gets the value of the codigoST property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodigoST() {
                return codigoST;
            }

            /**
             * Sets the value of the codigoST property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodigoST(String value) {
                this.codigoST = value;
            }

            /**
             * Gets the value of the numeroSiniestro property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumeroSiniestro() {
                return numeroSiniestro;
            }

            /**
             * Sets the value of the numeroSiniestro property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumeroSiniestro(String value) {
                this.numeroSiniestro = value;
            }

            /**
             * Gets the value of the causa property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCausa() {
                return causa;
            }

            /**
             * Sets the value of the causa property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCausa(String value) {
                this.causa = value;
            }

            /**
             * Gets the value of the coberturaSiniestrada property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCoberturaSiniestrada() {
                return coberturaSiniestrada;
            }

            /**
             * Sets the value of the coberturaSiniestrada property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCoberturaSiniestrada(String value) {
                this.coberturaSiniestrada = value;
            }

            /**
             * Gets the value of the limitacionFuncional property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLimitacionFuncional() {
                return limitacionFuncional;
            }

            /**
             * Sets the value of the limitacionFuncional property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLimitacionFuncional(String value) {
                this.limitacionFuncional = value;
            }

            /**
             * Gets the value of the fechaOcurrencia property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaOcurrencia() {
                return fechaOcurrencia;
            }

            /**
             * Sets the value of the fechaOcurrencia property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaOcurrencia(XMLGregorianCalendar value) {
                this.fechaOcurrencia = value;
            }

            /**
             * Gets the value of the fechaDeclaracion property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaDeclaracion() {
                return fechaDeclaracion;
            }

            /**
             * Sets the value of the fechaDeclaracion property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaDeclaracion(XMLGregorianCalendar value) {
                this.fechaDeclaracion = value;
            }

            /**
             * Gets the value of the rentaCapital property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRentaCapital() {
                return rentaCapital;
            }

            /**
             * Sets the value of the rentaCapital property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRentaCapital(BigDecimal value) {
                this.rentaCapital = value;
            }

            /**
             * Gets the value of the descripcionSiniestro property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcionSiniestro() {
                return descripcionSiniestro;
            }

            /**
             * Sets the value of the descripcionSiniestro property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcionSiniestro(String value) {
                this.descripcionSiniestro = value;
            }

            /**
             * Gets the value of the textoBeneficiarios property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTextoBeneficiarios() {
                return textoBeneficiarios;
            }

            /**
             * Sets the value of the textoBeneficiarios property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTextoBeneficiarios(String value) {
                this.textoBeneficiarios = value;
            }

            /**
             * Gets the value of the fechaBajaMedica property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaBajaMedica() {
                return fechaBajaMedica;
            }

            /**
             * Sets the value of the fechaBajaMedica property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaBajaMedica(XMLGregorianCalendar value) {
                this.fechaBajaMedica = value;
            }

            /**
             * Gets the value of the fechaAltaMedica property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaAltaMedica() {
                return fechaAltaMedica;
            }

            /**
             * Sets the value of the fechaAltaMedica property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaAltaMedica(XMLGregorianCalendar value) {
                this.fechaAltaMedica = value;
            }

            /**
             * Gets the value of the fechaInicioDerecho property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaInicioDerecho() {
                return fechaInicioDerecho;
            }

            /**
             * Sets the value of the fechaInicioDerecho property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaInicioDerecho(XMLGregorianCalendar value) {
                this.fechaInicioDerecho = value;
            }

            /**
             * Gets the value of the diasBajaEstimados property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDiasBajaEstimados() {
                return diasBajaEstimados;
            }

            /**
             * Sets the value of the diasBajaEstimados property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDiasBajaEstimados(BigDecimal value) {
                this.diasBajaEstimados = value;
            }

            /**
             * Gets the value of the oficinaGestora property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOficinaGestora() {
                return oficinaGestora;
            }

            /**
             * Sets the value of the oficinaGestora property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOficinaGestora(String value) {
                this.oficinaGestora = value;
            }

            /**
             * Gets the value of the pago property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the pago property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPago().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ResultadoSiniestroResponse.Expediente.Siniestro.Pago }
             * 
             * 
             */
            public List<ResultadoSiniestroResponse.Expediente.Siniestro.Pago> getPago() {
                if (pago == null) {
                    pago = new ArrayList<ResultadoSiniestroResponse.Expediente.Siniestro.Pago>();
                }
                return this.pago;
            }

            /**
             * Gets the value of the provision property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the provision property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getProvision().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ResultadoSiniestroResponse.Expediente.Siniestro.Provision }
             * 
             * 
             */
            public List<ResultadoSiniestroResponse.Expediente.Siniestro.Provision> getProvision() {
                if (provision == null) {
                    provision = new ArrayList<ResultadoSiniestroResponse.Expediente.Siniestro.Provision>();
                }
                return this.provision;
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
             *         &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="subtipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="metodoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="tipoPerceptor" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="tipoImpuesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="intervinienteBeneficiario" type="{http://www.scortelemed.com/schemas/ama}Interviniente"/>
             *         &lt;element name="fechaEmision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *         &lt;element name="fechaInicioPago" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *         &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="fechaFactura" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *         &lt;element name="importeBruto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="importeNeto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "codigoST",
                "subtipoPago",
                "metodoPago",
                "tipoPerceptor",
                "tipoImpuesto",
                "intervinienteBeneficiario",
                "fechaEmision",
                "fechaInicioPago",
                "numeroFactura",
                "fechaFactura",
                "importeBruto",
                "importeNeto",
                "tipoPago"
            })
            public static class Pago {

                @XmlElement(required = true)
                protected String codigoST;
                @XmlElement(required = true, nillable = true)
                protected String subtipoPago;
                @XmlElement(required = true)
                protected String metodoPago;
                @XmlElement(required = true)
                protected String tipoPerceptor;
                @XmlElement(required = true)
                protected String tipoImpuesto;
                @XmlElement(required = true, nillable = true)
                protected Interviniente intervinienteBeneficiario;
                @XmlElement(required = true, nillable = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar fechaEmision;
                @XmlElement(required = true, nillable = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar fechaInicioPago;
                @XmlElement(required = true)
                protected String numeroFactura;
                @XmlElement(required = true, nillable = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar fechaFactura;
                @XmlElement(required = true)
                protected BigDecimal importeBruto;
                @XmlElement(required = true)
                protected BigDecimal importeNeto;
                @XmlElement(required = true)
                protected String tipoPago;

                /**
                 * Gets the value of the codigoST property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCodigoST() {
                    return codigoST;
                }

                /**
                 * Sets the value of the codigoST property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCodigoST(String value) {
                    this.codigoST = value;
                }

                /**
                 * Gets the value of the subtipoPago property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSubtipoPago() {
                    return subtipoPago;
                }

                /**
                 * Sets the value of the subtipoPago property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSubtipoPago(String value) {
                    this.subtipoPago = value;
                }

                /**
                 * Gets the value of the metodoPago property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMetodoPago() {
                    return metodoPago;
                }

                /**
                 * Sets the value of the metodoPago property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMetodoPago(String value) {
                    this.metodoPago = value;
                }

                /**
                 * Gets the value of the tipoPerceptor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoPerceptor() {
                    return tipoPerceptor;
                }

                /**
                 * Sets the value of the tipoPerceptor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoPerceptor(String value) {
                    this.tipoPerceptor = value;
                }

                /**
                 * Gets the value of the tipoImpuesto property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoImpuesto() {
                    return tipoImpuesto;
                }

                /**
                 * Sets the value of the tipoImpuesto property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoImpuesto(String value) {
                    this.tipoImpuesto = value;
                }

                /**
                 * Gets the value of the intervinienteBeneficiario property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Interviniente }
                 *     
                 */
                public Interviniente getIntervinienteBeneficiario() {
                    return intervinienteBeneficiario;
                }

                /**
                 * Sets the value of the intervinienteBeneficiario property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Interviniente }
                 *     
                 */
                public void setIntervinienteBeneficiario(Interviniente value) {
                    this.intervinienteBeneficiario = value;
                }

                /**
                 * Gets the value of the fechaEmision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getFechaEmision() {
                    return fechaEmision;
                }

                /**
                 * Sets the value of the fechaEmision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setFechaEmision(XMLGregorianCalendar value) {
                    this.fechaEmision = value;
                }

                /**
                 * Gets the value of the fechaInicioPago property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getFechaInicioPago() {
                    return fechaInicioPago;
                }

                /**
                 * Sets the value of the fechaInicioPago property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setFechaInicioPago(XMLGregorianCalendar value) {
                    this.fechaInicioPago = value;
                }

                /**
                 * Gets the value of the numeroFactura property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNumeroFactura() {
                    return numeroFactura;
                }

                /**
                 * Sets the value of the numeroFactura property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNumeroFactura(String value) {
                    this.numeroFactura = value;
                }

                /**
                 * Gets the value of the fechaFactura property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getFechaFactura() {
                    return fechaFactura;
                }

                /**
                 * Sets the value of the fechaFactura property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setFechaFactura(XMLGregorianCalendar value) {
                    this.fechaFactura = value;
                }

                /**
                 * Gets the value of the importeBruto property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getImporteBruto() {
                    return importeBruto;
                }

                /**
                 * Sets the value of the importeBruto property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setImporteBruto(BigDecimal value) {
                    this.importeBruto = value;
                }

                /**
                 * Gets the value of the importeNeto property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getImporteNeto() {
                    return importeNeto;
                }

                /**
                 * Sets the value of the importeNeto property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setImporteNeto(BigDecimal value) {
                    this.importeNeto = value;
                }

                /**
                 * Gets the value of the tipoPago property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoPago() {
                    return tipoPago;
                }

                /**
                 * Sets the value of the tipoPago property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoPago(String value) {
                    this.tipoPago = value;
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
             *         &lt;element name="idProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="tipoProvision" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="subcodigoReserva" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="fechaProvision" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *         &lt;element name="importe" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
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
                "idProvision",
                "tipoProvision",
                "subcodigoReserva",
                "fechaProvision",
                "importe"
            })
            public static class Provision {

                @XmlElement(required = true)
                protected String idProvision;
                @XmlElement(required = true)
                protected String tipoProvision;
                @XmlElement(required = true, nillable = true)
                protected String subcodigoReserva;
                @XmlElement(required = true, nillable = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar fechaProvision;
                @XmlElement(required = true)
                protected BigDecimal importe;

                /**
                 * Gets the value of the idProvision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getIdProvision() {
                    return idProvision;
                }

                /**
                 * Sets the value of the idProvision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setIdProvision(String value) {
                    this.idProvision = value;
                }

                /**
                 * Gets the value of the tipoProvision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoProvision() {
                    return tipoProvision;
                }

                /**
                 * Sets the value of the tipoProvision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoProvision(String value) {
                    this.tipoProvision = value;
                }

                /**
                 * Gets the value of the subcodigoReserva property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSubcodigoReserva() {
                    return subcodigoReserva;
                }

                /**
                 * Sets the value of the subcodigoReserva property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSubcodigoReserva(String value) {
                    this.subcodigoReserva = value;
                }

                /**
                 * Gets the value of the fechaProvision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getFechaProvision() {
                    return fechaProvision;
                }

                /**
                 * Sets the value of the fechaProvision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setFechaProvision(XMLGregorianCalendar value) {
                    this.fechaProvision = value;
                }

                /**
                 * Gets the value of the importe property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getImporte() {
                    return importe;
                }

                /**
                 * Sets the value of the importe property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setImporte(BigDecimal value) {
                    this.importe = value;
                }

            }

        }

    }

}

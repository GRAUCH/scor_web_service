
package com.scortelemed.schemas.psn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppointmentsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppointmentsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="citaId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.scortelemed.com/schemas/psn}EstadoCitaType" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaModificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="horaFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoCita" type="{http://www.scortelemed.com/schemas/psn}TipoCitaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentsType", propOrder = {
    "citaId",
    "descripcion",
    "estado",
    "fecha",
    "fechaCreacion",
    "fechaModificacion",
    "hora",
    "horaFin",
    "tema",
    "tipoCita"
})
public class AppointmentsType {

    @XmlElement(required = true)
    protected String citaId;
    protected String descripcion;
    protected EstadoCitaType estado;
    protected String fecha;
    protected String fechaCreacion;
    protected String fechaModificacion;
    protected String hora;
    protected String horaFin;
    protected String tema;
    protected TipoCitaType tipoCita;

    /**
     * Gets the value of the citaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitaId() {
        return citaId;
    }

    /**
     * Sets the value of the citaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitaId(String value) {
        this.citaId = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoCitaType }
     *     
     */
    public EstadoCitaType getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoCitaType }
     *     
     */
    public void setEstado(EstadoCitaType value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the fechaCreacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Sets the value of the fechaCreacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCreacion(String value) {
        this.fechaCreacion = value;
    }

    /**
     * Gets the value of the fechaModificacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Sets the value of the fechaModificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaModificacion(String value) {
        this.fechaModificacion = value;
    }

    /**
     * Gets the value of the hora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHora() {
        return hora;
    }

    /**
     * Sets the value of the hora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHora(String value) {
        this.hora = value;
    }

    /**
     * Gets the value of the horaFin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * Sets the value of the horaFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraFin(String value) {
        this.horaFin = value;
    }

    /**
     * Gets the value of the tema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTema() {
        return tema;
    }

    /**
     * Sets the value of the tema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTema(String value) {
        this.tema = value;
    }

    /**
     * Gets the value of the tipoCita property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCitaType }
     *     
     */
    public TipoCitaType getTipoCita() {
        return tipoCita;
    }

    /**
     * Sets the value of the tipoCita property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCitaType }
     *     
     */
    public void setTipoCita(TipoCitaType value) {
        this.tipoCita = value;
    }

}

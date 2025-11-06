
package com.scortelemed.schemas.ama;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Interviniente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Interviniente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoST" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoInterviniente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="genero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apellidos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroMutualista" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCuenta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iban" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoTelefono1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoTelefono2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provincia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoPostal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pais" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Interviniente", propOrder = {
    "codigoST",
    "tipoInterviniente",
    "genero",
    "tipoDocumento",
    "numeroDocumento",
    "nombre",
    "apellidos",
    "numeroMutualista",
    "numeroCuenta",
    "iban",
    "tipoTelefono1",
    "telefono1",
    "tipoTelefono2",
    "telefono2",
    "email",
    "direccion",
    "localidad",
    "provincia",
    "codigoPostal",
    "pais",
    "fechaNacimiento"
})
public class Interviniente {

    @XmlElement(required = true)
    protected String codigoST;
    @XmlElement(required = true)
    protected String tipoInterviniente;
    @XmlElement(required = true)
    protected String genero;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected String numeroDocumento;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String apellidos;
    @XmlElement(required = true)
    protected String numeroMutualista;
    @XmlElement(required = true)
    protected String numeroCuenta;
    @XmlElement(required = true)
    protected String iban;
    @XmlElement(required = true)
    protected String tipoTelefono1;
    @XmlElement(required = true)
    protected String telefono1;
    @XmlElement(required = true)
    protected String tipoTelefono2;
    @XmlElement(required = true)
    protected String telefono2;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String direccion;
    @XmlElement(required = true)
    protected String localidad;
    @XmlElement(required = true, nillable = true)
    protected String provincia;
    @XmlElement(required = true)
    protected String codigoPostal;
    @XmlElement(required = true)
    protected String pais;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaNacimiento;

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
     * Gets the value of the tipoInterviniente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInterviniente() {
        return tipoInterviniente;
    }

    /**
     * Sets the value of the tipoInterviniente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInterviniente(String value) {
        this.tipoInterviniente = value;
    }

    /**
     * Gets the value of the genero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Sets the value of the genero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenero(String value) {
        this.genero = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the numeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Sets the value of the numeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the apellidos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the value of the apellidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidos(String value) {
        this.apellidos = value;
    }

    /**
     * Gets the value of the numeroMutualista property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroMutualista() {
        return numeroMutualista;
    }

    /**
     * Sets the value of the numeroMutualista property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroMutualista(String value) {
        this.numeroMutualista = value;
    }

    /**
     * Gets the value of the numeroCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Sets the value of the numeroCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCuenta(String value) {
        this.numeroCuenta = value;
    }

    /**
     * Gets the value of the iban property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIban(String value) {
        this.iban = value;
    }

    /**
     * Gets the value of the tipoTelefono1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoTelefono1() {
        return tipoTelefono1;
    }

    /**
     * Sets the value of the tipoTelefono1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoTelefono1(String value) {
        this.tipoTelefono1 = value;
    }

    /**
     * Gets the value of the telefono1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono1() {
        return telefono1;
    }

    /**
     * Sets the value of the telefono1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono1(String value) {
        this.telefono1 = value;
    }

    /**
     * Gets the value of the tipoTelefono2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoTelefono2() {
        return tipoTelefono2;
    }

    /**
     * Sets the value of the tipoTelefono2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoTelefono2(String value) {
        this.tipoTelefono2 = value;
    }

    /**
     * Gets the value of the telefono2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono2() {
        return telefono2;
    }

    /**
     * Sets the value of the telefono2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono2(String value) {
        this.telefono2 = value;
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
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the localidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Sets the value of the localidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalidad(String value) {
        this.localidad = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvincia(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the codigoPostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Sets the value of the codigoPostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPostal(String value) {
        this.codigoPostal = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaNacimiento(XMLGregorianCalendar value) {
        this.fechaNacimiento = value;
    }

}

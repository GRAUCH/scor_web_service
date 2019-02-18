
package com.ws.cajamar.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CajamarUnderwrittingCaseManagementRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CajamarUnderwrittingCaseManagementRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RegScor">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tlcom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tlabr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ytipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="cia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="yramex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="yprodu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="numref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="fechso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ofsucu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="telccc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="sucnom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="wpers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="nmbase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ap1ase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ap2ase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="dniase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ysexcl" type="{http://www.scortelemed.com/schemas/cajamar}SexType" minOccurs="0"/>
 *                   &lt;element name="ffnaci" type="{http://www.scortelemed.com/schemas/cajamar}FfnaciType" minOccurs="0"/>
 *                   &lt;element name="wdomcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ydpost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="wpobcl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="wprocl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tefcli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="movcli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="zprumd" type="{http://www.scortelemed.com/schemas/cajamar}MedicalTestType" minOccurs="0"/>
 *                   &lt;element name="profri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="moto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="franho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="franhi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="franhf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="diastl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="cobert" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="cobertType" type="{http://www.scortelemed.com/schemas/cajamar}CobertType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="deport" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="deportType" type="{http://www.scortelemed.com/schemas/cajamar}deportType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="xsegvi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xbajam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xfisic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xhospi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xdrosn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xdepre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="estatu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pesokg" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="xfumsn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xpsida" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="xesvid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="enferm" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="enfermType" type="{http://www.scortelemed.com/schemas/cajamar}EnfermType" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="obs382" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="gcontr" type="{http://www.scortelemed.com/schemas/cajamar}GcontrType" minOccurs="0"/>
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
@XmlType(name = "CajamarUnderwrittingCaseManagementRequest", propOrder = {
    "regScor"
})
public class CajamarUnderwrittingCaseManagementRequest {

    @XmlElement(name = "RegScor", required = true)
    protected CajamarUnderwrittingCaseManagementRequest.RegScor regScor;

    /**
     * Gets the value of the regScor property.
     * 
     * @return
     *     possible object is
     *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor }
     *     
     */
    public CajamarUnderwrittingCaseManagementRequest.RegScor getRegScor() {
        return regScor;
    }

    /**
     * Sets the value of the regScor property.
     * 
     * @param value
     *     allowed object is
     *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor }
     *     
     */
    public void setRegScor(CajamarUnderwrittingCaseManagementRequest.RegScor value) {
        this.regScor = value;
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
     *         &lt;element name="tlcom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tlabr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ytipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="cia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="yramex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="yprodu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="numref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="fechso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ofsucu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="telccc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="sucnom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="wpers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="nmbase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ap1ase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ap2ase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="dniase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ysexcl" type="{http://www.scortelemed.com/schemas/cajamar}SexType" minOccurs="0"/>
     *         &lt;element name="ffnaci" type="{http://www.scortelemed.com/schemas/cajamar}FfnaciType" minOccurs="0"/>
     *         &lt;element name="wdomcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ydpost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="wpobcl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="wprocl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tefcli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="movcli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="zprumd" type="{http://www.scortelemed.com/schemas/cajamar}MedicalTestType" minOccurs="0"/>
     *         &lt;element name="profri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="moto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="franho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="franhi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="franhf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="diastl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="cobert" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="cobertType" type="{http://www.scortelemed.com/schemas/cajamar}CobertType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="deport" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="deportType" type="{http://www.scortelemed.com/schemas/cajamar}deportType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="xsegvi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xbajam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xfisic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xhospi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xdrosn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xdepre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="estatu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pesokg" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="xfumsn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xpsida" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="xesvid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="enferm" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="enfermType" type="{http://www.scortelemed.com/schemas/cajamar}EnfermType" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="obs382" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="gcontr" type="{http://www.scortelemed.com/schemas/cajamar}GcontrType" minOccurs="0"/>
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
        "tlcom",
        "tlabr",
        "ytipo",
        "cia",
        "yramex",
        "yprodu",
        "numref",
        "fechso",
        "ofsucu",
        "telccc",
        "sucnom",
        "wpers",
        "nmbase",
        "ap1Ase",
        "ap2Ase",
        "dniase",
        "ysexcl",
        "ffnaci",
        "wdomcc",
        "ydpost",
        "wpobcl",
        "wprocl",
        "tefcli",
        "movcli",
        "zprumd",
        "profri",
        "moto",
        "franho",
        "franhi",
        "franhf",
        "diastl",
        "cobert",
        "deport",
        "xsegvi",
        "xbajam",
        "xfisic",
        "xhospi",
        "xdrosn",
        "xdepre",
        "estatu",
        "pesokg",
        "xfumsn",
        "xpsida",
        "xesvid",
        "enferm",
        "obs382",
        "gcontr"
    })
    public static class RegScor {

        protected String tlcom;
        protected String tlabr;
        protected String ytipo;
        protected String cia;
        protected String yramex;
        protected String yprodu;
        protected String numref;
        protected String fechso;
        protected String ofsucu;
        protected String telccc;
        protected String sucnom;
        protected String wpers;
        protected String nmbase;
        @XmlElement(name = "ap1ase")
        protected String ap1Ase;
        @XmlElement(name = "ap2ase")
        protected String ap2Ase;
        protected String dniase;
        protected SexType ysexcl;
        protected FfnaciType ffnaci;
        protected String wdomcc;
        protected String ydpost;
        protected String wpobcl;
        protected String wprocl;
        protected String tefcli;
        protected String movcli;
        protected MedicalTestType zprumd;
        protected String profri;
        protected String moto;
        protected String franho;
        protected String franhi;
        protected String franhf;
        protected String diastl;
        protected CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert cobert;
        protected CajamarUnderwrittingCaseManagementRequest.RegScor.Deport deport;
        protected String xsegvi;
        protected String xbajam;
        protected String xfisic;
        protected String xhospi;
        protected String xdrosn;
        protected String xdepre;
        protected String estatu;
        protected Integer pesokg;
        protected String xfumsn;
        protected String xpsida;
        protected String xesvid;
        protected CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm enferm;
        protected String obs382;
        protected GcontrType gcontr;

        /**
         * Gets the value of the tlcom property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTlcom() {
            return tlcom;
        }

        /**
         * Sets the value of the tlcom property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTlcom(String value) {
            this.tlcom = value;
        }

        /**
         * Gets the value of the tlabr property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTlabr() {
            return tlabr;
        }

        /**
         * Sets the value of the tlabr property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTlabr(String value) {
            this.tlabr = value;
        }

        /**
         * Gets the value of the ytipo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYtipo() {
            return ytipo;
        }

        /**
         * Sets the value of the ytipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYtipo(String value) {
            this.ytipo = value;
        }

        /**
         * Gets the value of the cia property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCia() {
            return cia;
        }

        /**
         * Sets the value of the cia property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCia(String value) {
            this.cia = value;
        }

        /**
         * Gets the value of the yramex property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYramex() {
            return yramex;
        }

        /**
         * Sets the value of the yramex property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYramex(String value) {
            this.yramex = value;
        }

        /**
         * Gets the value of the yprodu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYprodu() {
            return yprodu;
        }

        /**
         * Sets the value of the yprodu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYprodu(String value) {
            this.yprodu = value;
        }

        /**
         * Gets the value of the numref property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumref() {
            return numref;
        }

        /**
         * Sets the value of the numref property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumref(String value) {
            this.numref = value;
        }

        /**
         * Gets the value of the fechso property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechso() {
            return fechso;
        }

        /**
         * Sets the value of the fechso property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechso(String value) {
            this.fechso = value;
        }

        /**
         * Gets the value of the ofsucu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOfsucu() {
            return ofsucu;
        }

        /**
         * Sets the value of the ofsucu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOfsucu(String value) {
            this.ofsucu = value;
        }

        /**
         * Gets the value of the telccc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTelccc() {
            return telccc;
        }

        /**
         * Sets the value of the telccc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTelccc(String value) {
            this.telccc = value;
        }

        /**
         * Gets the value of the sucnom property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSucnom() {
            return sucnom;
        }

        /**
         * Sets the value of the sucnom property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSucnom(String value) {
            this.sucnom = value;
        }

        /**
         * Gets the value of the wpers property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWpers() {
            return wpers;
        }

        /**
         * Sets the value of the wpers property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWpers(String value) {
            this.wpers = value;
        }

        /**
         * Gets the value of the nmbase property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNmbase() {
            return nmbase;
        }

        /**
         * Sets the value of the nmbase property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNmbase(String value) {
            this.nmbase = value;
        }

        /**
         * Gets the value of the ap1Ase property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAp1Ase() {
            return ap1Ase;
        }

        /**
         * Sets the value of the ap1Ase property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAp1Ase(String value) {
            this.ap1Ase = value;
        }

        /**
         * Gets the value of the ap2Ase property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAp2Ase() {
            return ap2Ase;
        }

        /**
         * Sets the value of the ap2Ase property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAp2Ase(String value) {
            this.ap2Ase = value;
        }

        /**
         * Gets the value of the dniase property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDniase() {
            return dniase;
        }

        /**
         * Sets the value of the dniase property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDniase(String value) {
            this.dniase = value;
        }

        /**
         * Gets the value of the ysexcl property.
         * 
         * @return
         *     possible object is
         *     {@link SexType }
         *     
         */
        public SexType getYsexcl() {
            return ysexcl;
        }

        /**
         * Sets the value of the ysexcl property.
         * 
         * @param value
         *     allowed object is
         *     {@link SexType }
         *     
         */
        public void setYsexcl(SexType value) {
            this.ysexcl = value;
        }

        /**
         * Gets the value of the ffnaci property.
         * 
         * @return
         *     possible object is
         *     {@link FfnaciType }
         *     
         */
        public FfnaciType getFfnaci() {
            return ffnaci;
        }

        /**
         * Sets the value of the ffnaci property.
         * 
         * @param value
         *     allowed object is
         *     {@link FfnaciType }
         *     
         */
        public void setFfnaci(FfnaciType value) {
            this.ffnaci = value;
        }

        /**
         * Gets the value of the wdomcc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWdomcc() {
            return wdomcc;
        }

        /**
         * Sets the value of the wdomcc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWdomcc(String value) {
            this.wdomcc = value;
        }

        /**
         * Gets the value of the ydpost property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYdpost() {
            return ydpost;
        }

        /**
         * Sets the value of the ydpost property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYdpost(String value) {
            this.ydpost = value;
        }

        /**
         * Gets the value of the wpobcl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWpobcl() {
            return wpobcl;
        }

        /**
         * Sets the value of the wpobcl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWpobcl(String value) {
            this.wpobcl = value;
        }

        /**
         * Gets the value of the wprocl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWprocl() {
            return wprocl;
        }

        /**
         * Sets the value of the wprocl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWprocl(String value) {
            this.wprocl = value;
        }

        /**
         * Gets the value of the tefcli property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTefcli() {
            return tefcli;
        }

        /**
         * Sets the value of the tefcli property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTefcli(String value) {
            this.tefcli = value;
        }

        /**
         * Gets the value of the movcli property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMovcli() {
            return movcli;
        }

        /**
         * Sets the value of the movcli property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMovcli(String value) {
            this.movcli = value;
        }

        /**
         * Gets the value of the zprumd property.
         * 
         * @return
         *     possible object is
         *     {@link MedicalTestType }
         *     
         */
        public MedicalTestType getZprumd() {
            return zprumd;
        }

        /**
         * Sets the value of the zprumd property.
         * 
         * @param value
         *     allowed object is
         *     {@link MedicalTestType }
         *     
         */
        public void setZprumd(MedicalTestType value) {
            this.zprumd = value;
        }

        /**
         * Gets the value of the profri property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProfri() {
            return profri;
        }

        /**
         * Sets the value of the profri property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProfri(String value) {
            this.profri = value;
        }

        /**
         * Gets the value of the moto property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMoto() {
            return moto;
        }

        /**
         * Sets the value of the moto property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMoto(String value) {
            this.moto = value;
        }

        /**
         * Gets the value of the franho property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFranho() {
            return franho;
        }

        /**
         * Sets the value of the franho property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFranho(String value) {
            this.franho = value;
        }

        /**
         * Gets the value of the franhi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFranhi() {
            return franhi;
        }

        /**
         * Sets the value of the franhi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFranhi(String value) {
            this.franhi = value;
        }

        /**
         * Gets the value of the franhf property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFranhf() {
            return franhf;
        }

        /**
         * Sets the value of the franhf property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFranhf(String value) {
            this.franhf = value;
        }

        /**
         * Gets the value of the diastl property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiastl() {
            return diastl;
        }

        /**
         * Sets the value of the diastl property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiastl(String value) {
            this.diastl = value;
        }

        /**
         * Gets the value of the cobert property.
         * 
         * @return
         *     possible object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert }
         *     
         */
        public CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert getCobert() {
            return cobert;
        }

        /**
         * Sets the value of the cobert property.
         * 
         * @param value
         *     allowed object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert }
         *     
         */
        public void setCobert(CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert value) {
            this.cobert = value;
        }

        /**
         * Gets the value of the deport property.
         * 
         * @return
         *     possible object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Deport }
         *     
         */
        public CajamarUnderwrittingCaseManagementRequest.RegScor.Deport getDeport() {
            return deport;
        }

        /**
         * Sets the value of the deport property.
         * 
         * @param value
         *     allowed object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Deport }
         *     
         */
        public void setDeport(CajamarUnderwrittingCaseManagementRequest.RegScor.Deport value) {
            this.deport = value;
        }

        /**
         * Gets the value of the xsegvi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXsegvi() {
            return xsegvi;
        }

        /**
         * Sets the value of the xsegvi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXsegvi(String value) {
            this.xsegvi = value;
        }

        /**
         * Gets the value of the xbajam property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXbajam() {
            return xbajam;
        }

        /**
         * Sets the value of the xbajam property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXbajam(String value) {
            this.xbajam = value;
        }

        /**
         * Gets the value of the xfisic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXfisic() {
            return xfisic;
        }

        /**
         * Sets the value of the xfisic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXfisic(String value) {
            this.xfisic = value;
        }

        /**
         * Gets the value of the xhospi property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXhospi() {
            return xhospi;
        }

        /**
         * Sets the value of the xhospi property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXhospi(String value) {
            this.xhospi = value;
        }

        /**
         * Gets the value of the xdrosn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXdrosn() {
            return xdrosn;
        }

        /**
         * Sets the value of the xdrosn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXdrosn(String value) {
            this.xdrosn = value;
        }

        /**
         * Gets the value of the xdepre property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXdepre() {
            return xdepre;
        }

        /**
         * Sets the value of the xdepre property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXdepre(String value) {
            this.xdepre = value;
        }

        /**
         * Gets the value of the estatu property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstatu() {
            return estatu;
        }

        /**
         * Sets the value of the estatu property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstatu(String value) {
            this.estatu = value;
        }

        /**
         * Gets the value of the pesokg property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getPesokg() {
            return pesokg;
        }

        /**
         * Sets the value of the pesokg property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setPesokg(Integer value) {
            this.pesokg = value;
        }

        /**
         * Gets the value of the xfumsn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXfumsn() {
            return xfumsn;
        }

        /**
         * Sets the value of the xfumsn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXfumsn(String value) {
            this.xfumsn = value;
        }

        /**
         * Gets the value of the xpsida property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXpsida() {
            return xpsida;
        }

        /**
         * Sets the value of the xpsida property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXpsida(String value) {
            this.xpsida = value;
        }

        /**
         * Gets the value of the xesvid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getXesvid() {
            return xesvid;
        }

        /**
         * Sets the value of the xesvid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setXesvid(String value) {
            this.xesvid = value;
        }

        /**
         * Gets the value of the enferm property.
         * 
         * @return
         *     possible object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm }
         *     
         */
        public CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm getEnferm() {
            return enferm;
        }

        /**
         * Sets the value of the enferm property.
         * 
         * @param value
         *     allowed object is
         *     {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm }
         *     
         */
        public void setEnferm(CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm value) {
            this.enferm = value;
        }

        /**
         * Gets the value of the obs382 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getObs382() {
            return obs382;
        }

        /**
         * Sets the value of the obs382 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setObs382(String value) {
            this.obs382 = value;
        }

        /**
         * Gets the value of the gcontr property.
         * 
         * @return
         *     possible object is
         *     {@link GcontrType }
         *     
         */
        public GcontrType getGcontr() {
            return gcontr;
        }

        /**
         * Sets the value of the gcontr property.
         * 
         * @param value
         *     allowed object is
         *     {@link GcontrType }
         *     
         */
        public void setGcontr(GcontrType value) {
            this.gcontr = value;
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
         *         &lt;element name="cobertType" type="{http://www.scortelemed.com/schemas/cajamar}CobertType" maxOccurs="unbounded" minOccurs="0"/>
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
            "cobertType"
        })
        public static class Cobert {

            @XmlElement(nillable = true)
            protected List<CobertType> cobertType;

            /**
             * Gets the value of the cobertType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the cobertType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCobertType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CobertType }
             * 
             * 
             */
            public List<CobertType> getCobertType() {
                if (cobertType == null) {
                    cobertType = new ArrayList<CobertType>();
                }
                return this.cobertType;
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
         *         &lt;element name="deportType" type="{http://www.scortelemed.com/schemas/cajamar}deportType" maxOccurs="unbounded" minOccurs="0"/>
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
            "deportType"
        })
        public static class Deport {

            @XmlElement(nillable = true)
            protected List<DeportType> deportType;

            /**
             * Gets the value of the deportType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the deportType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDeportType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link DeportType }
             * 
             * 
             */
            public List<DeportType> getDeportType() {
                if (deportType == null) {
                    deportType = new ArrayList<DeportType>();
                }
                return this.deportType;
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
         *         &lt;element name="enfermType" type="{http://www.scortelemed.com/schemas/cajamar}EnfermType" maxOccurs="unbounded" minOccurs="0"/>
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
            "enfermType"
        })
        public static class Enferm {

            @XmlElement(nillable = true)
            protected List<EnfermType> enfermType;

            /**
             * Gets the value of the enfermType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the enfermType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getEnfermType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link EnfermType }
             * 
             * 
             */
            public List<EnfermType> getEnfermType() {
                if (enfermType == null) {
                    enfermType = new ArrayList<EnfermType>();
                }
                return this.enfermType;
            }

        }

    }

}

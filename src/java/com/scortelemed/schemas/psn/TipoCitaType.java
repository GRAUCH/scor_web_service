
package com.scortelemed.schemas.psn;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoCitaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoCitaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Teleseleccion"/>
 *     &lt;enumeration value="CentroMedico"/>
 *     &lt;enumeration value="ServicioCita"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoCitaType")
@XmlEnum
public enum TipoCitaType {

    @XmlEnumValue("Teleseleccion")
    TELESELECCION("Teleseleccion"),
    @XmlEnumValue("CentroMedico")
    CENTRO_MEDICO("CentroMedico"),
    @XmlEnumValue("ServicioCita")
    SERVICIO_CITA("ServicioCita");
    private final String value;

    TipoCitaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCitaType fromValue(String v) {
        for (TipoCitaType c: TipoCitaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package com.scortelemed.schemas.nn;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstadoCitaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EstadoCitaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Abierto"/>
 *     &lt;enumeration value="CitaProgramada"/>
 *     &lt;enumeration value="PendienteEnvioResultados"/>
 *     &lt;enumeration value="Completado"/>
 *     &lt;enumeration value="Cancelado"/>
 *     &lt;enumeration value="NoPresentado"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EstadoCitaType")
@XmlEnum
public enum EstadoCitaType {

    @XmlEnumValue("Abierto")
    ABIERTO("Abierto"),
    @XmlEnumValue("CitaProgramada")
    CITA_PROGRAMADA("CitaProgramada"),
    @XmlEnumValue("PendienteEnvioResultados")
    PENDIENTE_ENVIO_RESULTADOS("PendienteEnvioResultados"),
    @XmlEnumValue("Completado")
    COMPLETADO("Completado"),
    @XmlEnumValue("Cancelado")
    CANCELADO("Cancelado"),
    @XmlEnumValue("NoPresentado")
    NO_PRESENTADO("NoPresentado");
    private final String value;

    EstadoCitaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EstadoCitaType fromValue(String v) {
        for (EstadoCitaType c: EstadoCitaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

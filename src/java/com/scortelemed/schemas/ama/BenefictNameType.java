
package com.scortelemed.schemas.ama;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BenefictNameType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BenefictNameType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FALLECIMIENTO"/>
 *     &lt;enumeration value="INCAPACIDAD PERMANENTE ABSOLUTA (IPA)"/>
 *     &lt;enumeration value="INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL"/>
 *     &lt;enumeration value="GRAN INVALIDEZ"/>
 *     &lt;enumeration value="INCAPACIDAD TEMPORAL POR ACCIDENTE"/>
 *     &lt;enumeration value="INCAPACIDAD TEMPORAL POR ENFERMEDAD"/>
 *     &lt;enumeration value="FALLECIMIENTO POR ACCIDENTE"/>
 *     &lt;enumeration value="FALLECIMIENTO POR ACCIDENTE DE CIRCULACION"/>
 *     &lt;enumeration value="IPA POR ACCIDENTE"/>
 *     &lt;enumeration value="IPA POR ACCIDENTE DE CIRCULACION"/>
 *     &lt;enumeration value="INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE"/>
 *     &lt;enumeration value="INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE DE CIRCULACION"/>
 *     &lt;enumeration value="GRAN INVALIDEZ POR ACCIDENTE"/>
 *     &lt;enumeration value="GRAN INVALIDEZ POR ACCIDENTE DE CIRCULACION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BenefictNameType")
@XmlEnum
public enum BenefictNameType {

    FALLECIMIENTO("FALLECIMIENTO"),
    @XmlEnumValue("INCAPACIDAD PERMANENTE ABSOLUTA (IPA)")
    INCAPACIDAD_PERMANENTE_ABSOLUTA_IPA("INCAPACIDAD PERMANENTE ABSOLUTA (IPA)"),
    @XmlEnumValue("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL")
    INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL"),
    @XmlEnumValue("GRAN INVALIDEZ")
    GRAN_INVALIDEZ("GRAN INVALIDEZ"),
    @XmlEnumValue("INCAPACIDAD TEMPORAL POR ACCIDENTE")
    INCAPACIDAD_TEMPORAL_POR_ACCIDENTE("INCAPACIDAD TEMPORAL POR ACCIDENTE"),
    @XmlEnumValue("INCAPACIDAD TEMPORAL POR ENFERMEDAD")
    INCAPACIDAD_TEMPORAL_POR_ENFERMEDAD("INCAPACIDAD TEMPORAL POR ENFERMEDAD"),
    @XmlEnumValue("FALLECIMIENTO POR ACCIDENTE")
    FALLECIMIENTO_POR_ACCIDENTE("FALLECIMIENTO POR ACCIDENTE"),
    @XmlEnumValue("FALLECIMIENTO POR ACCIDENTE DE CIRCULACION")
    FALLECIMIENTO_POR_ACCIDENTE_DE_CIRCULACION("FALLECIMIENTO POR ACCIDENTE DE CIRCULACION"),
    @XmlEnumValue("IPA POR ACCIDENTE")
    IPA_POR_ACCIDENTE("IPA POR ACCIDENTE"),
    @XmlEnumValue("IPA POR ACCIDENTE DE CIRCULACION")
    IPA_POR_ACCIDENTE_DE_CIRCULACION("IPA POR ACCIDENTE DE CIRCULACION"),
    @XmlEnumValue("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE")
    INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL_POR_ACCIDENTE("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE"),
    @XmlEnumValue("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE DE CIRCULACION")
    INCAPACIDAD_PERMANENTE_TOTAL_PARA_LA_PROFESION_HABITUAL_POR_ACCIDENTE_DE_CIRCULACION("INCAPACIDAD PERMANENTE TOTAL PARA LA PROFESION HABITUAL POR ACCIDENTE DE CIRCULACION"),
    @XmlEnumValue("GRAN INVALIDEZ POR ACCIDENTE")
    GRAN_INVALIDEZ_POR_ACCIDENTE("GRAN INVALIDEZ POR ACCIDENTE"),
    @XmlEnumValue("GRAN INVALIDEZ POR ACCIDENTE DE CIRCULACION")
    GRAN_INVALIDEZ_POR_ACCIDENTE_DE_CIRCULACION("GRAN INVALIDEZ POR ACCIDENTE DE CIRCULACION");
    private final String value;

    BenefictNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BenefictNameType fromValue(String v) {
        for (BenefictNameType c: BenefictNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

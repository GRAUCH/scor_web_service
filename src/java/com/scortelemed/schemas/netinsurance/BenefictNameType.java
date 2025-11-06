
package com.scortelemed.schemas.netinsurance;

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
 *     &lt;enumeration value="Dead"/>
 *     &lt;enumeration value="Accidental Dead"/>
 *     &lt;enumeration value="Critical Illness"/>
 *     &lt;enumeration value="Permanent Disability"/>
 *     &lt;enumeration value="Disability 30"/>
 *     &lt;enumeration value="Disability 30-90"/>
 *     &lt;enumeration value="Disability 90"/>
 *     &lt;enumeration value="Dependency"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BenefictNameType")
@XmlEnum
public enum BenefictNameType {

    @XmlEnumValue("Dead")
    DEAD("Dead"),
    @XmlEnumValue("Accidental Dead")
    ACCIDENTAL_DEAD("Accidental Dead"),
    @XmlEnumValue("Critical Illness")
    CRITICAL_ILLNESS("Critical Illness"),
    @XmlEnumValue("Permanent Disability")
    PERMANENT_DISABILITY("Permanent Disability"),
    @XmlEnumValue("Disability 30")
    DISABILITY_30("Disability 30"),
    @XmlEnumValue("Disability 30-90")
    DISABILITY_30_90("Disability 30-90"),
    @XmlEnumValue("Disability 90")
    DISABILITY_90("Disability 90"),
    @XmlEnumValue("Dependency")
    DEPENDENCY("Dependency");
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

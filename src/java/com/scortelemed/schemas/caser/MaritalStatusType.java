
package com.scortelemed.schemas.caser;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CivilStateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MaritalStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="V"/>
 *     &lt;enumeration value="O"/>
 *     &lt;enumeration value="E"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MaritalStatusType")
@XmlEnum
public enum MaritalStatusType {

    C,
    D,
    P,
    S,
    V,
    O,
    E;

    public String value() {
        return name();
    }

    public static MaritalStatusType fromValue(String v) {
        return valueOf(v);
    }

}


package com.scortelemed.schemas.psn;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QuestionDataType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QuestionDataType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="String"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="Boolean"/>
 *     &lt;enumeration value="Date"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QuestionDataType")
@XmlEnum
public enum QuestionDataType {

    @XmlEnumValue("String")
    STRING("String"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("Boolean")
    BOOLEAN("Boolean"),
    @XmlEnumValue("Date")
    DATE("Date");
    private final String value;

    QuestionDataType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QuestionDataType fromValue(String v) {
        for (QuestionDataType c: QuestionDataType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

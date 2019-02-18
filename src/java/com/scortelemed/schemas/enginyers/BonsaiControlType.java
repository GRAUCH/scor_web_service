
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bonsaiControlType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="bonsaiControlType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="dropDown"/>
 *     &lt;enumeration value="textBox"/>
 *     &lt;enumeration value="label"/>
 *     &lt;enumeration value="checkBox"/>
 *     &lt;enumeration value="numericTextBox"/>
 *     &lt;enumeration value="dateTextBox"/>
 *     &lt;enumeration value="radioButton"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "bonsaiControlType")
@XmlEnum
public enum BonsaiControlType {

    @XmlEnumValue("dropDown")
    DROP_DOWN("dropDown"),
    @XmlEnumValue("textBox")
    TEXT_BOX("textBox"),
    @XmlEnumValue("label")
    LABEL("label"),
    @XmlEnumValue("checkBox")
    CHECK_BOX("checkBox"),
    @XmlEnumValue("numericTextBox")
    NUMERIC_TEXT_BOX("numericTextBox"),
    @XmlEnumValue("dateTextBox")
    DATE_TEXT_BOX("dateTextBox"),
    @XmlEnumValue("radioButton")
    RADIO_BUTTON("radioButton");
    private final String value;

    BonsaiControlType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BonsaiControlType fromValue(String v) {
        for (BonsaiControlType c: BonsaiControlType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

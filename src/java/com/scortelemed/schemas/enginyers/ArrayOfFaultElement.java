
package com.scortelemed.schemas.enginyers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFaultElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFaultElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="faultElement" type="{http://www.scortelemed.com/schemas/enginyers}faultElement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFaultElement", propOrder = {
    "faultElement"
})
public class ArrayOfFaultElement {

    @XmlElement(nillable = true)
    protected List<FaultElement> faultElement;

    /**
     * Gets the value of the faultElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the faultElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFaultElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FaultElement }
     * 
     * 
     */
    public List<FaultElement> getFaultElement() {
        if (faultElement == null) {
            faultElement = new ArrayList<FaultElement>();
        }
        return this.faultElement;
    }

}

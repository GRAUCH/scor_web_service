
package com.scortelemed.schemas.enginyers;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroups complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroups">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="groupElement" type="{http://www.scortelemed.com/schemas/enginyers}groupElement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroups", propOrder = {
    "groupElement"
})
public class ArrayOfGroups {

    @XmlElement(nillable = true)
    protected List<GroupElement> groupElement;

    /**
     * Gets the value of the groupElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroupElement }
     * 
     * 
     */
    public List<GroupElement> getGroupElement() {
        if (groupElement == null) {
            groupElement = new ArrayList<GroupElement>();
        }
        return this.groupElement;
    }

}

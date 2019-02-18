
package com.scortelemed.schemas.enginyers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for groupElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="groupElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="questionElementArray" type="{http://www.scortelemed.com/schemas/enginyers}ArrayOfQuestionsElement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupElement", propOrder = {
    "number",
    "title",
    "questionElementArray"
})
public class GroupElement {

    protected int number;
    protected String title;
    protected ArrayOfQuestionsElement questionElementArray;

    /**
     * Gets the value of the number property.
     * 
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     */
    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the questionElementArray property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfQuestionsElement }
     *     
     */
    public ArrayOfQuestionsElement getQuestionElementArray() {
        return questionElementArray;
    }

    /**
     * Sets the value of the questionElementArray property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfQuestionsElement }
     *     
     */
    public void setQuestionElementArray(ArrayOfQuestionsElement value) {
        this.questionElementArray = value;
    }

}

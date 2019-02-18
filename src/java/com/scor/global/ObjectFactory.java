
package com.scor.global;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scor.global2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SimpleMethodResponse_QNAME = new QName("http://services/", "simpleMethodResponse");
    private final static QName _SimpleMethod_QNAME = new QName("http://services/", "simpleMethod");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scor.global2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SimpleMethod }
     * 
     */
    public SimpleMethod createSimpleMethod() {
        return new SimpleMethod();
    }

    /**
     * Create an instance of {@link SimpleMethodResponse }
     * 
     */
    public SimpleMethodResponse createSimpleMethodResponse() {
        return new SimpleMethodResponse();
    }

    /**
     * Create an instance of {@link Compania }
     * 
     */
    public Compania createCompania() {
        return new Compania();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimpleMethodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "simpleMethodResponse")
    public JAXBElement<SimpleMethodResponse> createSimpleMethodResponse(SimpleMethodResponse value) {
        return new JAXBElement<SimpleMethodResponse>(_SimpleMethodResponse_QNAME, SimpleMethodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimpleMethod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services/", name = "simpleMethod")
    public JAXBElement<SimpleMethod> createSimpleMethod(SimpleMethod value) {
        return new JAXBElement<SimpleMethod>(_SimpleMethod_QNAME, SimpleMethod.class, null, value);
    }

}

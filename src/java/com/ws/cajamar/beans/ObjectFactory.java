
package com.ws.cajamar.beans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ws.cajamar.beans package. 
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

    private final static QName _ConsolidacionPolizaRequest_QNAME = new QName("http://www.scortelemed.com/schemas/cajamar", "ConsolidacionPolizaRequest");
    private final static QName _CajamarUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/cajamar", "CajamarUnderwrittingCaseManagementResponse");
    private final static QName _CajamarUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/cajamar", "CajamarUnderwrittingCaseManagementRequest");
    private final static QName _ConsolidacionPolizaResponse_QNAME = new QName("http://www.scortelemed.com/schemas/cajamar", "ConsolidacionPolizaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ws.cajamar.beans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeportType }
     * 
     */
    public DeportType createDeportType() {
        return new DeportType();
    }

    /**
     * Create an instance of {@link ConsolidacionPolizaResponse }
     * 
     */
    public ConsolidacionPolizaResponse createConsolidacionPolizaResponse() {
        return new ConsolidacionPolizaResponse();
    }

    /**
     * Create an instance of {@link GcontrType }
     * 
     */
    public GcontrType createGcontrType() {
        return new GcontrType();
    }

    /**
     * Create an instance of {@link FfnaciType }
     * 
     */
    public FfnaciType createFfnaciType() {
        return new FfnaciType();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementResponse }
     * 
     */
    public CajamarUnderwrittingCaseManagementResponse createCajamarUnderwrittingCaseManagementResponse() {
        return new CajamarUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Deport }
     * 
     */
    public CajamarUnderwrittingCaseManagementRequest.RegScor.Deport createCajamarUnderwrittingCaseManagementRequestRegScorDeport() {
        return new CajamarUnderwrittingCaseManagementRequest.RegScor.Deport();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementRequest.RegScor }
     * 
     */
    public CajamarUnderwrittingCaseManagementRequest.RegScor createCajamarUnderwrittingCaseManagementRequestRegScor() {
        return new CajamarUnderwrittingCaseManagementRequest.RegScor();
    }

    /**
     * Create an instance of {@link EnfermType }
     * 
     */
    public EnfermType createEnfermType() {
        return new EnfermType();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm }
     * 
     */
    public CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm createCajamarUnderwrittingCaseManagementRequestRegScorEnferm() {
        return new CajamarUnderwrittingCaseManagementRequest.RegScor.Enferm();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementRequest }
     * 
     */
    public CajamarUnderwrittingCaseManagementRequest createCajamarUnderwrittingCaseManagementRequest() {
        return new CajamarUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert }
     * 
     */
    public CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert createCajamarUnderwrittingCaseManagementRequestRegScorCobert() {
        return new CajamarUnderwrittingCaseManagementRequest.RegScor.Cobert();
    }

    /**
     * Create an instance of {@link ConsolidacionPolizaRequest }
     * 
     */
    public ConsolidacionPolizaRequest createConsolidacionPolizaRequest() {
        return new ConsolidacionPolizaRequest();
    }

    /**
     * Create an instance of {@link CobertType }
     * 
     */
    public CobertType createCobertType() {
        return new CobertType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidacionPolizaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cajamar", name = "ConsolidacionPolizaRequest")
    public JAXBElement<ConsolidacionPolizaRequest> createConsolidacionPolizaRequest(ConsolidacionPolizaRequest value) {
        return new JAXBElement<ConsolidacionPolizaRequest>(_ConsolidacionPolizaRequest_QNAME, ConsolidacionPolizaRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CajamarUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cajamar", name = "CajamarUnderwrittingCaseManagementResponse")
    public JAXBElement<CajamarUnderwrittingCaseManagementResponse> createCajamarUnderwrittingCaseManagementResponse(CajamarUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<CajamarUnderwrittingCaseManagementResponse>(_CajamarUnderwrittingCaseManagementResponse_QNAME, CajamarUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CajamarUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cajamar", name = "CajamarUnderwrittingCaseManagementRequest")
    public JAXBElement<CajamarUnderwrittingCaseManagementRequest> createCajamarUnderwrittingCaseManagementRequest(CajamarUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<CajamarUnderwrittingCaseManagementRequest>(_CajamarUnderwrittingCaseManagementRequest_QNAME, CajamarUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidacionPolizaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cajamar", name = "ConsolidacionPolizaResponse")
    public JAXBElement<ConsolidacionPolizaResponse> createConsolidacionPolizaResponse(ConsolidacionPolizaResponse value) {
        return new JAXBElement<ConsolidacionPolizaResponse>(_ConsolidacionPolizaResponse_QNAME, ConsolidacionPolizaResponse.class, null, value);
    }

}

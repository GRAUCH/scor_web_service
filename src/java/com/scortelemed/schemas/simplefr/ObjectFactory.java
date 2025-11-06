
package com.scortelemed.schemas.simplefr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.simplefr package. 
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

    private final static QName _SimplefrUnderwrittingCasesResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/simplefr", "SimplefrUnderwrittingCasesResultsResponse");
    private final static QName _SimplefrUnderwrittingCasesResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/simplefr", "SimplefrUnderwrittingCasesResultsRequest");
    private final static QName _SimplefrUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/simplefr", "SimplefrUnderwrittingCaseManagementResponse");
    private final static QName _SimplefrUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/simplefr", "SimplefrUnderwrittingCaseManagementRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.simplefr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCaseManagementRequest.CandidateInformation }
     * 
     */
    public SimplefrUnderwrittingCaseManagementRequest.CandidateInformation createSimplefrUnderwrittingCaseManagementRequestCandidateInformation() {
        return new SimplefrUnderwrittingCaseManagementRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCaseManagementRequest }
     * 
     */
    public SimplefrUnderwrittingCaseManagementRequest createSimplefrUnderwrittingCaseManagementRequest() {
        return new SimplefrUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCaseManagementRequest.Services }
     * 
     */
    public SimplefrUnderwrittingCaseManagementRequest.Services createSimplefrUnderwrittingCaseManagementRequestServices() {
        return new SimplefrUnderwrittingCaseManagementRequest.Services();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCaseManagementRequest.PreviousQuestions }
     * 
     */
    public SimplefrUnderwrittingCaseManagementRequest.PreviousQuestions createSimplefrUnderwrittingCaseManagementRequestPreviousQuestions() {
        return new SimplefrUnderwrittingCaseManagementRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCaseManagementResponse }
     * 
     */
    public SimplefrUnderwrittingCaseManagementResponse createSimplefrUnderwrittingCaseManagementResponse() {
        return new SimplefrUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCasesResultsRequest }
     * 
     */
    public SimplefrUnderwrittingCasesResultsRequest createSimplefrUnderwrittingCasesResultsRequest() {
        return new SimplefrUnderwrittingCasesResultsRequest();
    }

    /**
     * Create an instance of {@link ResultsBasicType }
     * 
     */
    public ResultsBasicType createResultsBasicType() {
        return new ResultsBasicType();
    }

    /**
     * Create an instance of {@link SimplefrUnderwrittingCasesResultsResponse }
     * 
     */
    public SimplefrUnderwrittingCasesResultsResponse createSimplefrUnderwrittingCasesResultsResponse() {
        return new SimplefrUnderwrittingCasesResultsResponse();
    }

    /**
     * Create an instance of {@link BenefitsType }
     * 
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimplefrUnderwrittingCasesResultsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/simplefr", name = "SimplefrUnderwrittingCasesResultsResponse")
    public JAXBElement<SimplefrUnderwrittingCasesResultsResponse> createSimplefrUnderwrittingCasesResultsResponse(SimplefrUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<SimplefrUnderwrittingCasesResultsResponse>(_SimplefrUnderwrittingCasesResultsResponse_QNAME, SimplefrUnderwrittingCasesResultsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimplefrUnderwrittingCasesResultsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/simplefr", name = "SimplefrUnderwrittingCasesResultsRequest")
    public JAXBElement<SimplefrUnderwrittingCasesResultsRequest> createSimplefrUnderwrittingCasesResultsRequest(SimplefrUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<SimplefrUnderwrittingCasesResultsRequest>(_SimplefrUnderwrittingCasesResultsRequest_QNAME, SimplefrUnderwrittingCasesResultsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimplefrUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/simplefr", name = "SimplefrUnderwrittingCaseManagementResponse")
    public JAXBElement<SimplefrUnderwrittingCaseManagementResponse> createSimplefrUnderwrittingCaseManagementResponse(SimplefrUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<SimplefrUnderwrittingCaseManagementResponse>(_SimplefrUnderwrittingCaseManagementResponse_QNAME, SimplefrUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimplefrUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/simplefr", name = "SimplefrUnderwrittingCaseManagementRequest")
    public JAXBElement<SimplefrUnderwrittingCaseManagementRequest> createSimplefrUnderwrittingCaseManagementRequest(SimplefrUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<SimplefrUnderwrittingCaseManagementRequest>(_SimplefrUnderwrittingCaseManagementRequest_QNAME, SimplefrUnderwrittingCaseManagementRequest.class, null, value);
    }

}

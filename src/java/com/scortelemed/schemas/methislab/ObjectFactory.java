
package com.scortelemed.schemas.methislab;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.methislab package. 
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

    private final static QName _MethislabUnderwrittingCasesResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "MethislabUnderwrittingCasesResultsRequest");
    private final static QName _MethislabUnderwrittingCasesResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "MethislabUnderwrittingCasesResultsResponse");
    private final static QName _CaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "CaseManagementResponse");
    private final static QName _CaseManagementResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "CaseManagementResultsRequest");
    private final static QName _MethislabUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "MethislabUnderwrittingCaseManagementRequest");
    private final static QName _CaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "CaseManagementRequest");
    private final static QName _MethislabUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "MethislabUnderwrittingCaseManagementResponse");
    private final static QName _CaseManagementResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislab", "CaseManagementResultsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.methislab
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCaseManagementRequest.PreviousQuestions }
     * 
     */
    public MethislabUnderwrittingCaseManagementRequest.PreviousQuestions createMethislabUnderwrittingCaseManagementRequestPreviousQuestions() {
        return new MethislabUnderwrittingCaseManagementRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link BenefitsType }
     * 
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCasesResultsResponse.Expediente }
     * 
     */
    public MethislabUnderwrittingCasesResultsResponse.Expediente createMethislabUnderwrittingCasesResultsResponseExpediente() {
        return new MethislabUnderwrittingCasesResultsResponse.Expediente();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCaseManagementResponse }
     * 
     */
    public MethislabUnderwrittingCaseManagementResponse createMethislabUnderwrittingCaseManagementResponse() {
        return new MethislabUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link ResultsBasicType }
     * 
     */
    public ResultsBasicType createResultsBasicType() {
        return new ResultsBasicType();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCaseManagementRequest.CandidateInformation }
     * 
     */
    public MethislabUnderwrittingCaseManagementRequest.CandidateInformation createMethislabUnderwrittingCaseManagementRequestCandidateInformation() {
        return new MethislabUnderwrittingCaseManagementRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCaseManagementRequest }
     * 
     */
    public MethislabUnderwrittingCaseManagementRequest createMethislabUnderwrittingCaseManagementRequest() {
        return new MethislabUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCasesResultsResponse }
     * 
     */
    public MethislabUnderwrittingCasesResultsResponse createMethislabUnderwrittingCasesResultsResponse() {
        return new MethislabUnderwrittingCasesResultsResponse();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCaseManagementRequest.Services }
     * 
     */
    public MethislabUnderwrittingCaseManagementRequest.Services createMethislabUnderwrittingCaseManagementRequestServices() {
        return new MethislabUnderwrittingCaseManagementRequest.Services();
    }

    /**
     * Create an instance of {@link MethislabUnderwrittingCasesResultsRequest }
     * 
     */
    public MethislabUnderwrittingCasesResultsRequest createMethislabUnderwrittingCasesResultsRequest() {
        return new MethislabUnderwrittingCasesResultsRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCasesResultsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "MethislabUnderwrittingCasesResultsRequest")
    public JAXBElement<MethislabUnderwrittingCasesResultsRequest> createMethislabUnderwrittingCasesResultsRequest(MethislabUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<MethislabUnderwrittingCasesResultsRequest>(_MethislabUnderwrittingCasesResultsRequest_QNAME, MethislabUnderwrittingCasesResultsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCasesResultsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "MethislabUnderwrittingCasesResultsResponse")
    public JAXBElement<MethislabUnderwrittingCasesResultsResponse> createMethislabUnderwrittingCasesResultsResponse(MethislabUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<MethislabUnderwrittingCasesResultsResponse>(_MethislabUnderwrittingCasesResultsResponse_QNAME, MethislabUnderwrittingCasesResultsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "CaseManagementResponse")
    public JAXBElement<MethislabUnderwrittingCaseManagementResponse> createCaseManagementResponse(MethislabUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<MethislabUnderwrittingCaseManagementResponse>(_CaseManagementResponse_QNAME, MethislabUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCasesResultsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "CaseManagementResultsRequest")
    public JAXBElement<MethislabUnderwrittingCasesResultsRequest> createCaseManagementResultsRequest(MethislabUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<MethislabUnderwrittingCasesResultsRequest>(_CaseManagementResultsRequest_QNAME, MethislabUnderwrittingCasesResultsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "MethislabUnderwrittingCaseManagementRequest")
    public JAXBElement<MethislabUnderwrittingCaseManagementRequest> createMethislabUnderwrittingCaseManagementRequest(MethislabUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<MethislabUnderwrittingCaseManagementRequest>(_MethislabUnderwrittingCaseManagementRequest_QNAME, MethislabUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "CaseManagementRequest")
    public JAXBElement<MethislabUnderwrittingCaseManagementRequest> createCaseManagementRequest(MethislabUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<MethislabUnderwrittingCaseManagementRequest>(_CaseManagementRequest_QNAME, MethislabUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "MethislabUnderwrittingCaseManagementResponse")
    public JAXBElement<MethislabUnderwrittingCaseManagementResponse> createMethislabUnderwrittingCaseManagementResponse(MethislabUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<MethislabUnderwrittingCaseManagementResponse>(_MethislabUnderwrittingCaseManagementResponse_QNAME, MethislabUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabUnderwrittingCasesResultsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislab", name = "CaseManagementResultsResponse")
    public JAXBElement<MethislabUnderwrittingCasesResultsResponse> createCaseManagementResultsResponse(MethislabUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<MethislabUnderwrittingCasesResultsResponse>(_CaseManagementResultsResponse_QNAME, MethislabUnderwrittingCasesResultsResponse.class, null, value);
    }

}

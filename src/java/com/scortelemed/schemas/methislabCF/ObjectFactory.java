
package com.scortelemed.schemas.methislabCF;

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

    private final static QName _MethislabUnderwrittingCasesResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "MethislabCFUnderwrittingCasesResultsRequest");
    private final static QName _MethislabUnderwrittingCasesResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "MethislabCFUnderwrittingCasesResultsResponse");
    private final static QName _CaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "CaseManagementResponse");
    private final static QName _CaseManagementResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "CaseManagementResultsRequest");
    private final static QName _MethislabUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "MethislabCFUnderwrittingCaseManagementRequest");
    private final static QName _CaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "CaseManagementRequest");
    private final static QName _MethislabUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "MethislabCFUnderwrittingCaseManagementResponse");
    private final static QName _CaseManagementResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/methislabCF", "CaseManagementResultsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.methislab
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions }
     *
     */
    public MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions createMethislabUnderwrittingCaseManagementRequestPreviousQuestions() {
        return new MethislabCFUnderwrittingCaseManagementRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link BenefitsType }
     *
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCasesResultsResponse.Expediente }
     *
     */
    public MethislabCFUnderwrittingCasesResultsResponse.Expediente createMethislabUnderwrittingCasesResultsResponseExpediente() {
        return new MethislabCFUnderwrittingCasesResultsResponse.Expediente();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCaseManagementResponse }
     *
     */
    public MethislabCFUnderwrittingCaseManagementResponse createMethislabUnderwrittingCaseManagementResponse() {
        return new MethislabCFUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link ResultsBasicType }
     *
     */
    public ResultsBasicType createResultsBasicType() {
        return new ResultsBasicType();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation }
     *
     */
    public MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation createMethislabUnderwrittingCaseManagementRequestCandidateInformation() {
        return new MethislabCFUnderwrittingCaseManagementRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCaseManagementRequest }
     *
     */
    public MethislabCFUnderwrittingCaseManagementRequest createMethislabUnderwrittingCaseManagementRequest() {
        return new MethislabCFUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCasesResultsResponse }
     *
     */
    public MethislabCFUnderwrittingCasesResultsResponse createMethislabUnderwrittingCasesResultsResponse() {
        return new MethislabCFUnderwrittingCasesResultsResponse();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     *
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCaseManagementRequest.Services }
     *
     */
    public MethislabCFUnderwrittingCaseManagementRequest.Services createMethislabUnderwrittingCaseManagementRequestServices() {
        return new MethislabCFUnderwrittingCaseManagementRequest.Services();
    }

    /**
     * Create an instance of {@link MethislabCFUnderwrittingCasesResultsRequest }
     *
     */
    public MethislabCFUnderwrittingCasesResultsRequest createMethislabUnderwrittingCasesResultsRequest() {
        return new MethislabCFUnderwrittingCasesResultsRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCasesResultsRequest }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "MethislabCFUnderwrittingCasesResultsRequest")
    public JAXBElement<MethislabCFUnderwrittingCasesResultsRequest> createMethislabUnderwrittingCasesResultsRequest(MethislabCFUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<MethislabCFUnderwrittingCasesResultsRequest>(_MethislabUnderwrittingCasesResultsRequest_QNAME, MethislabCFUnderwrittingCasesResultsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCasesResultsResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "MethislabCFUnderwrittingCasesResultsResponse")
    public JAXBElement<MethislabCFUnderwrittingCasesResultsResponse> createMethislabUnderwrittingCasesResultsResponse(MethislabCFUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<MethislabCFUnderwrittingCasesResultsResponse>(_MethislabUnderwrittingCasesResultsResponse_QNAME, MethislabCFUnderwrittingCasesResultsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCaseManagementResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "CaseManagementResponse")
    public JAXBElement<MethislabCFUnderwrittingCaseManagementResponse> createCaseManagementResponse(MethislabCFUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<MethislabCFUnderwrittingCaseManagementResponse>(_CaseManagementResponse_QNAME, MethislabCFUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCasesResultsRequest }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "CaseManagementResultsRequest")
    public JAXBElement<MethislabCFUnderwrittingCasesResultsRequest> createCaseManagementResultsRequest(MethislabCFUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<MethislabCFUnderwrittingCasesResultsRequest>(_CaseManagementResultsRequest_QNAME, MethislabCFUnderwrittingCasesResultsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCaseManagementRequest }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "MethislabCFUnderwrittingCaseManagementRequest")
    public JAXBElement<MethislabCFUnderwrittingCaseManagementRequest> createMethislabUnderwrittingCaseManagementRequest(MethislabCFUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<MethislabCFUnderwrittingCaseManagementRequest>(_MethislabUnderwrittingCaseManagementRequest_QNAME, MethislabCFUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCaseManagementRequest }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "CaseManagementRequest")
    public JAXBElement<MethislabCFUnderwrittingCaseManagementRequest> createCaseManagementRequest(MethislabCFUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<MethislabCFUnderwrittingCaseManagementRequest>(_CaseManagementRequest_QNAME, MethislabCFUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCaseManagementResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "MethislabCFUnderwrittingCaseManagementResponse")
    public JAXBElement<MethislabCFUnderwrittingCaseManagementResponse> createMethislabUnderwrittingCaseManagementResponse(MethislabCFUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<MethislabCFUnderwrittingCaseManagementResponse>(_MethislabUnderwrittingCaseManagementResponse_QNAME, MethislabCFUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethislabCFUnderwrittingCasesResultsResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/methislabCF", name = "CaseManagementResultsResponse")
    public JAXBElement<MethislabCFUnderwrittingCasesResultsResponse> createCaseManagementResultsResponse(MethislabCFUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<MethislabCFUnderwrittingCasesResultsResponse>(_CaseManagementResultsResponse_QNAME, MethislabCFUnderwrittingCasesResultsResponse.class, null, value);
    }

}


package com.scortelemed.schemas.societegenerale;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.societegenerale package. 
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

    private final static QName _SocieteGeneraleUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/societeGenerale", "SocieteGeneraleUnderwrittingCaseManagementRequest");
    private final static QName _SocieteGeneraleUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/societeGenerale", "SocieteGeneraleUnderwrittingCaseManagementResponse");
    private final static QName _SocieteGeneraleUnderwrittingCasesResultsResponse_QNAME = new QName("http://www.scortelemed.com/schemas/societeGenerale", "SocieteGeneraleUnderwrittingCasesResultsResponse");
    private final static QName _SocieteGeneraleUnderwrittingCasesResultsRequest_QNAME = new QName("http://www.scortelemed.com/schemas/societeGenerale", "SocieteGeneraleUnderwrittingCasesResultsRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.societegenerale
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCaseManagementRequest.Services }
     * 
     */
    public SocieteGeneraleUnderwrittingCaseManagementRequest.Services createSocieteGeneraleUnderwrittingCaseManagementRequestServices() {
        return new SocieteGeneraleUnderwrittingCaseManagementRequest.Services();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCaseManagementRequest.PreviousQuestions }
     * 
     */
    public SocieteGeneraleUnderwrittingCaseManagementRequest.PreviousQuestions createSocieteGeneraleUnderwrittingCaseManagementRequestPreviousQuestions() {
        return new SocieteGeneraleUnderwrittingCaseManagementRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link BenefitsType }
     * 
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link ResultsBasicType }
     * 
     */
    public ResultsBasicType createResultsBasicType() {
        return new ResultsBasicType();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCaseManagementRequest.CandidateInformation }
     * 
     */
    public SocieteGeneraleUnderwrittingCaseManagementRequest.CandidateInformation createSocieteGeneraleUnderwrittingCaseManagementRequestCandidateInformation() {
        return new SocieteGeneraleUnderwrittingCaseManagementRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCasesResultsRequest }
     * 
     */
    public SocieteGeneraleUnderwrittingCasesResultsRequest createSocieteGeneraleUnderwrittingCasesResultsRequest() {
        return new SocieteGeneraleUnderwrittingCasesResultsRequest();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCaseManagementRequest }
     * 
     */
    public SocieteGeneraleUnderwrittingCaseManagementRequest createSocieteGeneraleUnderwrittingCaseManagementRequest() {
        return new SocieteGeneraleUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCaseManagementResponse }
     * 
     */
    public SocieteGeneraleUnderwrittingCaseManagementResponse createSocieteGeneraleUnderwrittingCaseManagementResponse() {
        return new SocieteGeneraleUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link SocieteGeneraleUnderwrittingCasesResultsResponse }
     * 
     */
    public SocieteGeneraleUnderwrittingCasesResultsResponse createSocieteGeneraleUnderwrittingCasesResultsResponse() {
        return new SocieteGeneraleUnderwrittingCasesResultsResponse();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SocieteGeneraleUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/societeGenerale", name = "SocieteGeneraleUnderwrittingCaseManagementRequest")
    public JAXBElement<SocieteGeneraleUnderwrittingCaseManagementRequest> createSocieteGeneraleUnderwrittingCaseManagementRequest(SocieteGeneraleUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<SocieteGeneraleUnderwrittingCaseManagementRequest>(_SocieteGeneraleUnderwrittingCaseManagementRequest_QNAME, SocieteGeneraleUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SocieteGeneraleUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/societeGenerale", name = "SocieteGeneraleUnderwrittingCaseManagementResponse")
    public JAXBElement<SocieteGeneraleUnderwrittingCaseManagementResponse> createSocieteGeneraleUnderwrittingCaseManagementResponse(SocieteGeneraleUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<SocieteGeneraleUnderwrittingCaseManagementResponse>(_SocieteGeneraleUnderwrittingCaseManagementResponse_QNAME, SocieteGeneraleUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SocieteGeneraleUnderwrittingCasesResultsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/societeGenerale", name = "SocieteGeneraleUnderwrittingCasesResultsResponse")
    public JAXBElement<SocieteGeneraleUnderwrittingCasesResultsResponse> createSocieteGeneraleUnderwrittingCasesResultsResponse(SocieteGeneraleUnderwrittingCasesResultsResponse value) {
        return new JAXBElement<SocieteGeneraleUnderwrittingCasesResultsResponse>(_SocieteGeneraleUnderwrittingCasesResultsResponse_QNAME, SocieteGeneraleUnderwrittingCasesResultsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SocieteGeneraleUnderwrittingCasesResultsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/societeGenerale", name = "SocieteGeneraleUnderwrittingCasesResultsRequest")
    public JAXBElement<SocieteGeneraleUnderwrittingCasesResultsRequest> createSocieteGeneraleUnderwrittingCasesResultsRequest(SocieteGeneraleUnderwrittingCasesResultsRequest value) {
        return new JAXBElement<SocieteGeneraleUnderwrittingCasesResultsRequest>(_SocieteGeneraleUnderwrittingCasesResultsRequest_QNAME, SocieteGeneraleUnderwrittingCasesResultsRequest.class, null, value);
    }

}

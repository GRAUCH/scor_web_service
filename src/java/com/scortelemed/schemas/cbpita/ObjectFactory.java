
package com.scortelemed.schemas.cbpita;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.cbpita package. 
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

    private final static QName _CaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/cbpita", "CaseManagementRequest");
    private final static QName _CbpitaUnderwrittingCaseManagementRequest_QNAME = new QName("http://www.scortelemed.com/schemas/cbpita", "CbpitaUnderwrittingCaseManagementRequest");
    private final static QName _CbpitaUnderwrittingCaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/cbpita", "CbpitaUnderwrittingCaseManagementResponse");
    private final static QName _CaseManagementResponse_QNAME = new QName("http://www.scortelemed.com/schemas/cbpita", "caseManagementResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.cbpita
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CbpitaUnderwrittingCaseManagementRequest.CandidateInformation }
     * 
     */
    public CbpitaUnderwrittingCaseManagementRequest.CandidateInformation createCbpitaUnderwrittingCaseManagementRequestCandidateInformation() {
        return new CbpitaUnderwrittingCaseManagementRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link ResultsBasicType }
     * 
     */
    public ResultsBasicType createResultsBasicType() {
        return new ResultsBasicType();
    }

    /**
     * Create an instance of {@link CbpitaUnderwrittingCaseManagementRequest }
     * 
     */
    public CbpitaUnderwrittingCaseManagementRequest createCbpitaUnderwrittingCaseManagementRequest() {
        return new CbpitaUnderwrittingCaseManagementRequest();
    }

    /**
     * Create an instance of {@link CbpitaUnderwrittingCaseManagementRequest.Services }
     * 
     */
    public CbpitaUnderwrittingCaseManagementRequest.Services createCbpitaUnderwrittingCaseManagementRequestServices() {
        return new CbpitaUnderwrittingCaseManagementRequest.Services();
    }

    /**
     * Create an instance of {@link BenefitsType }
     * 
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link CbpitaUnderwrittingCaseManagementRequest.PreviousQuestions }
     * 
     */
    public CbpitaUnderwrittingCaseManagementRequest.PreviousQuestions createCbpitaUnderwrittingCaseManagementRequestPreviousQuestions() {
        return new CbpitaUnderwrittingCaseManagementRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link CbpitaUnderwrittingCaseManagementResponse }
     * 
     */
    public CbpitaUnderwrittingCaseManagementResponse createCbpitaUnderwrittingCaseManagementResponse() {
        return new CbpitaUnderwrittingCaseManagementResponse();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbpitaUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cbpita", name = "CaseManagementRequest")
    public JAXBElement<CbpitaUnderwrittingCaseManagementRequest> createCaseManagementRequest(CbpitaUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<CbpitaUnderwrittingCaseManagementRequest>(_CaseManagementRequest_QNAME, CbpitaUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbpitaUnderwrittingCaseManagementRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cbpita", name = "CbpitaUnderwrittingCaseManagementRequest")
    public JAXBElement<CbpitaUnderwrittingCaseManagementRequest> createCbpitaUnderwrittingCaseManagementRequest(CbpitaUnderwrittingCaseManagementRequest value) {
        return new JAXBElement<CbpitaUnderwrittingCaseManagementRequest>(_CbpitaUnderwrittingCaseManagementRequest_QNAME, CbpitaUnderwrittingCaseManagementRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbpitaUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cbpita", name = "CbpitaUnderwrittingCaseManagementResponse")
    public JAXBElement<CbpitaUnderwrittingCaseManagementResponse> createCbpitaUnderwrittingCaseManagementResponse(CbpitaUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<CbpitaUnderwrittingCaseManagementResponse>(_CbpitaUnderwrittingCaseManagementResponse_QNAME, CbpitaUnderwrittingCaseManagementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbpitaUnderwrittingCaseManagementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/cbpita", name = "caseManagementResponse")
    public JAXBElement<CbpitaUnderwrittingCaseManagementResponse> createCaseManagementResponse(CbpitaUnderwrittingCaseManagementResponse value) {
        return new JAXBElement<CbpitaUnderwrittingCaseManagementResponse>(_CaseManagementResponse_QNAME, CbpitaUnderwrittingCaseManagementResponse.class, null, value);
    }

}

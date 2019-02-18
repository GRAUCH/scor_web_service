
package com.scortelemed.schemas.nn;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.nn package. 
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

    private final static QName _ResultadoReconocimientoMedicoRequest_QNAME = new QName("http://www.scortelemed.com/schemas/nn", "ResultadoReconocimientoMedicoRequest");
    private final static QName _GestionReconocimientoMedicoResponse_QNAME = new QName("http://www.scortelemed.com/schemas/nn", "GestionReconocimientoMedicoResponse");
    private final static QName _GestionReconocimientoMedicoRequest_QNAME = new QName("http://www.scortelemed.com/schemas/nn", "GestionReconocimientoMedicoRequest");
    private final static QName _ResultadoReconocimientoMedicoResponse_QNAME = new QName("http://www.scortelemed.com/schemas/nn", "ResultadoReconocimientoMedicoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.nn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.Services }
     * 
     */
    public GestionReconocimientoMedicoRequest.Services createGestionReconocimientoMedicoRequestServices() {
        return new GestionReconocimientoMedicoRequest.Services();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.PreviousQuestions }
     * 
     */
    public GestionReconocimientoMedicoRequest.PreviousQuestions createGestionReconocimientoMedicoRequestPreviousQuestions() {
        return new GestionReconocimientoMedicoRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.CandidateInformation }
     * 
     */
    public GestionReconocimientoMedicoRequest.CandidateInformation createGestionReconocimientoMedicoRequestCandidateInformation() {
        return new GestionReconocimientoMedicoRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link ResultadoReconocimientoMedicoResponse }
     * 
     */
    public ResultadoReconocimientoMedicoResponse createResultadoReconocimientoMedicoResponse() {
        return new ResultadoReconocimientoMedicoResponse();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoResponse }
     * 
     */
    public GestionReconocimientoMedicoResponse createGestionReconocimientoMedicoResponse() {
        return new GestionReconocimientoMedicoResponse();
    }

    /**
     * Create an instance of {@link ResultadoReconocimientoMedicoRequest }
     * 
     */
    public ResultadoReconocimientoMedicoRequest createResultadoReconocimientoMedicoRequest() {
        return new ResultadoReconocimientoMedicoRequest();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest }
     * 
     */
    public GestionReconocimientoMedicoRequest createGestionReconocimientoMedicoRequest() {
        return new GestionReconocimientoMedicoRequest();
    }

    /**
     * Create an instance of {@link BenefitsType }
     * 
     */
    public BenefitsType createBenefitsType() {
        return new BenefitsType();
    }

    /**
     * Create an instance of {@link ResultadoReconocimientoMedicoResponse.Expediente }
     * 
     */
    public ResultadoReconocimientoMedicoResponse.Expediente createResultadoReconocimientoMedicoResponseExpediente() {
        return new ResultadoReconocimientoMedicoResponse.Expediente();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoReconocimientoMedicoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/nn", name = "ResultadoReconocimientoMedicoRequest")
    public JAXBElement<ResultadoReconocimientoMedicoRequest> createResultadoReconocimientoMedicoRequest(ResultadoReconocimientoMedicoRequest value) {
        return new JAXBElement<ResultadoReconocimientoMedicoRequest>(_ResultadoReconocimientoMedicoRequest_QNAME, ResultadoReconocimientoMedicoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestionReconocimientoMedicoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/nn", name = "GestionReconocimientoMedicoResponse")
    public JAXBElement<GestionReconocimientoMedicoResponse> createGestionReconocimientoMedicoResponse(GestionReconocimientoMedicoResponse value) {
        return new JAXBElement<GestionReconocimientoMedicoResponse>(_GestionReconocimientoMedicoResponse_QNAME, GestionReconocimientoMedicoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestionReconocimientoMedicoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/nn", name = "GestionReconocimientoMedicoRequest")
    public JAXBElement<GestionReconocimientoMedicoRequest> createGestionReconocimientoMedicoRequest(GestionReconocimientoMedicoRequest value) {
        return new JAXBElement<GestionReconocimientoMedicoRequest>(_GestionReconocimientoMedicoRequest_QNAME, GestionReconocimientoMedicoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoReconocimientoMedicoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/nn", name = "ResultadoReconocimientoMedicoResponse")
    public JAXBElement<ResultadoReconocimientoMedicoResponse> createResultadoReconocimientoMedicoResponse(ResultadoReconocimientoMedicoResponse value) {
        return new JAXBElement<ResultadoReconocimientoMedicoResponse>(_ResultadoReconocimientoMedicoResponse_QNAME, ResultadoReconocimientoMedicoResponse.class, null, value);
    }

}

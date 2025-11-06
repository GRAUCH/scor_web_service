
package com.scortelemed.schemas.ama;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scortelemed.schemas.ama package. 
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

    private final static QName _ResultadoSiniestroResponse_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ResultadoSiniestroResponse");
    private final static QName _ConsolidacionPolizaResponse_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsolidacionPolizaResponse");
    private final static QName _ConsolidacionPolizaRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsolidacionPolizaRequest");
    private final static QName _ResultadoReconocimientoMedicoResponse_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ResultadoReconocimientoMedicoResponse");
    private final static QName _ResultadoSiniestroRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ResultadoSiniestroRequest");
    private final static QName _ConsultaExpedienteRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsultaExpedienteRequest");
    private final static QName _GestionReconocimientoMedicoRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "GestionReconocimientoMedicoRequest");
    private final static QName _GestionReconocimientoMedicoResponse_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "GestionReconocimientoMedicoResponse");
    private final static QName _ConsultaDocumento_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsultaDocumento");
    private final static QName _ConsultaExpediente_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsultaExpediente");
    private final static QName _ConsultaDocumentoRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsultaDocumentoRequest");
    private final static QName _ResultadoReconocimientoMedicoRequest_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ResultadoReconocimientoMedicoRequest");
    private final static QName _ConsultaExpedienteResponse_QNAME = new QName("http://www.scortelemed.com/schemas/ama", "ConsultaExpedienteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scortelemed.schemas.ama
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResultadoSiniestroResponse.Expediente }
     * 
     */
    public ResultadoSiniestroResponse.Expediente createResultadoSiniestroResponseExpediente() {
        return new ResultadoSiniestroResponse.Expediente();
    }

    /**
     * Create an instance of {@link ResultadoSiniestroResponse.Expediente.Siniestro.Provision }
     * 
     */
    public ResultadoSiniestroResponse.Expediente.Siniestro.Provision createResultadoSiniestroResponseExpedienteSiniestroProvision() {
        return new ResultadoSiniestroResponse.Expediente.Siniestro.Provision();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.BenefitsType }
     * 
     */
    public GestionReconocimientoMedicoRequest.BenefitsType createGestionReconocimientoMedicoRequestBenefitsType() {
        return new GestionReconocimientoMedicoRequest.BenefitsType();
    }

    /**
     * Create an instance of {@link ConsultaDocumentoRequest }
     * 
     */
    public ConsultaDocumentoRequest createConsultaDocumentoRequest() {
        return new ConsultaDocumentoRequest();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoResponse }
     * 
     */
    public GestionReconocimientoMedicoResponse createGestionReconocimientoMedicoResponse() {
        return new GestionReconocimientoMedicoResponse();
    }

    /**
     * Create an instance of {@link ConsultaExpedienteRequest }
     * 
     */
    public ConsultaExpedienteRequest createConsultaExpedienteRequest() {
        return new ConsultaExpedienteRequest();
    }

    /**
     * Create an instance of {@link ConsultaDocumentoResponse.Documento }
     * 
     */
    public ConsultaDocumentoResponse.Documento createConsultaDocumentoResponseDocumento() {
        return new ConsultaDocumentoResponse.Documento();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.Services }
     * 
     */
    public GestionReconocimientoMedicoRequest.Services createGestionReconocimientoMedicoRequestServices() {
        return new GestionReconocimientoMedicoRequest.Services();
    }

    /**
     * Create an instance of {@link Interviniente }
     * 
     */
    public Interviniente createInterviniente() {
        return new Interviniente();
    }

    /**
     * Create an instance of {@link ConsultaExpedienteResponse.Expediente }
     * 
     */
    public ConsultaExpedienteResponse.Expediente createConsultaExpedienteResponseExpediente() {
        return new ConsultaExpedienteResponse.Expediente();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.PreviousQuestions }
     * 
     */
    public GestionReconocimientoMedicoRequest.PreviousQuestions createGestionReconocimientoMedicoRequestPreviousQuestions() {
        return new GestionReconocimientoMedicoRequest.PreviousQuestions();
    }

    /**
     * Create an instance of {@link ResultadoSiniestroResponse.Expediente.Siniestro }
     * 
     */
    public ResultadoSiniestroResponse.Expediente.Siniestro createResultadoSiniestroResponseExpedienteSiniestro() {
        return new ResultadoSiniestroResponse.Expediente.Siniestro();
    }

    /**
     * Create an instance of {@link ConsultaDocumentoResponse }
     * 
     */
    public ConsultaDocumentoResponse createConsultaDocumentoResponse() {
        return new ConsultaDocumentoResponse();
    }

    /**
     * Create an instance of {@link ResultadoSiniestroResponse.Expediente.Siniestro.Pago }
     * 
     */
    public ResultadoSiniestroResponse.Expediente.Siniestro.Pago createResultadoSiniestroResponseExpedienteSiniestroPago() {
        return new ResultadoSiniestroResponse.Expediente.Siniestro.Pago();
    }

    /**
     * Create an instance of {@link PolicyHolderInformationType }
     * 
     */
    public PolicyHolderInformationType createPolicyHolderInformationType() {
        return new PolicyHolderInformationType();
    }

    /**
     * Create an instance of {@link ResultadoSiniestroResponse }
     * 
     */
    public ResultadoSiniestroResponse createResultadoSiniestroResponse() {
        return new ResultadoSiniestroResponse();
    }

    /**
     * Create an instance of {@link CandidateInformationType }
     * 
     */
    public CandidateInformationType createCandidateInformationType() {
        return new CandidateInformationType();
    }

    /**
     * Create an instance of {@link ResultadoReconocimientoMedicoResponse }
     * 
     */
    public ResultadoReconocimientoMedicoResponse createResultadoReconocimientoMedicoResponse() {
        return new ResultadoReconocimientoMedicoResponse();
    }

    /**
     * Create an instance of {@link com.scortelemed.schemas.ama.BenefitsType }
     * 
     */
    public com.scortelemed.schemas.ama.BenefitsType createBenefitsType() {
        return new com.scortelemed.schemas.ama.BenefitsType();
    }

    /**
     * Create an instance of {@link ConsolidacionPolizaRequest }
     * 
     */
    public ConsolidacionPolizaRequest createConsolidacionPolizaRequest() {
        return new ConsolidacionPolizaRequest();
    }

    /**
     * Create an instance of {@link BenefictResultType }
     * 
     */
    public BenefictResultType createBenefictResultType() {
        return new BenefictResultType();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest.CandidateInformation }
     * 
     */
    public GestionReconocimientoMedicoRequest.CandidateInformation createGestionReconocimientoMedicoRequestCandidateInformation() {
        return new GestionReconocimientoMedicoRequest.CandidateInformation();
    }

    /**
     * Create an instance of {@link AppointmentsType }
     * 
     */
    public AppointmentsType createAppointmentsType() {
        return new AppointmentsType();
    }

    /**
     * Create an instance of {@link GestionReconocimientoMedicoRequest }
     * 
     */
    public GestionReconocimientoMedicoRequest createGestionReconocimientoMedicoRequest() {
        return new GestionReconocimientoMedicoRequest();
    }

    /**
     * Create an instance of {@link ResultadoReconocimientoMedicoRequest }
     * 
     */
    public ResultadoReconocimientoMedicoRequest createResultadoReconocimientoMedicoRequest() {
        return new ResultadoReconocimientoMedicoRequest();
    }

    /**
     * Create an instance of {@link ActivitiesType }
     * 
     */
    public ActivitiesType createActivitiesType() {
        return new ActivitiesType();
    }

    /**
     * Create an instance of {@link ResultadoSiniestroRequest }
     * 
     */
    public ResultadoSiniestroRequest createResultadoSiniestroRequest() {
        return new ResultadoSiniestroRequest();
    }

    /**
     * Create an instance of {@link ConsolidacionPolizaResponse }
     * 
     */
    public ConsolidacionPolizaResponse createConsolidacionPolizaResponse() {
        return new ConsolidacionPolizaResponse();
    }

    /**
     * Create an instance of {@link Resultado }
     * 
     */
    public Resultado createResultado() {
        return new Resultado();
    }

    /**
     * Create an instance of {@link DocumentType }
     * 
     */
    public DocumentType createDocumentType() {
        return new DocumentType();
    }

    /**
     * Create an instance of {@link ConsultaExpedienteResponse }
     * 
     */
    public ConsultaExpedienteResponse createConsultaExpedienteResponse() {
        return new ConsultaExpedienteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoSiniestroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ResultadoSiniestroResponse")
    public JAXBElement<ResultadoSiniestroResponse> createResultadoSiniestroResponse(ResultadoSiniestroResponse value) {
        return new JAXBElement<ResultadoSiniestroResponse>(_ResultadoSiniestroResponse_QNAME, ResultadoSiniestroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidacionPolizaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsolidacionPolizaResponse")
    public JAXBElement<ConsolidacionPolizaResponse> createConsolidacionPolizaResponse(ConsolidacionPolizaResponse value) {
        return new JAXBElement<ConsolidacionPolizaResponse>(_ConsolidacionPolizaResponse_QNAME, ConsolidacionPolizaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidacionPolizaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsolidacionPolizaRequest")
    public JAXBElement<ConsolidacionPolizaRequest> createConsolidacionPolizaRequest(ConsolidacionPolizaRequest value) {
        return new JAXBElement<ConsolidacionPolizaRequest>(_ConsolidacionPolizaRequest_QNAME, ConsolidacionPolizaRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoReconocimientoMedicoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ResultadoReconocimientoMedicoResponse")
    public JAXBElement<ResultadoReconocimientoMedicoResponse> createResultadoReconocimientoMedicoResponse(ResultadoReconocimientoMedicoResponse value) {
        return new JAXBElement<ResultadoReconocimientoMedicoResponse>(_ResultadoReconocimientoMedicoResponse_QNAME, ResultadoReconocimientoMedicoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoSiniestroRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ResultadoSiniestroRequest")
    public JAXBElement<ResultadoSiniestroRequest> createResultadoSiniestroRequest(ResultadoSiniestroRequest value) {
        return new JAXBElement<ResultadoSiniestroRequest>(_ResultadoSiniestroRequest_QNAME, ResultadoSiniestroRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaExpedienteRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsultaExpedienteRequest")
    public JAXBElement<ConsultaExpedienteRequest> createConsultaExpedienteRequest(ConsultaExpedienteRequest value) {
        return new JAXBElement<ConsultaExpedienteRequest>(_ConsultaExpedienteRequest_QNAME, ConsultaExpedienteRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestionReconocimientoMedicoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "GestionReconocimientoMedicoRequest")
    public JAXBElement<GestionReconocimientoMedicoRequest> createGestionReconocimientoMedicoRequest(GestionReconocimientoMedicoRequest value) {
        return new JAXBElement<GestionReconocimientoMedicoRequest>(_GestionReconocimientoMedicoRequest_QNAME, GestionReconocimientoMedicoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestionReconocimientoMedicoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "GestionReconocimientoMedicoResponse")
    public JAXBElement<GestionReconocimientoMedicoResponse> createGestionReconocimientoMedicoResponse(GestionReconocimientoMedicoResponse value) {
        return new JAXBElement<GestionReconocimientoMedicoResponse>(_GestionReconocimientoMedicoResponse_QNAME, GestionReconocimientoMedicoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDocumentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsultaDocumento")
    public JAXBElement<ConsultaDocumentoResponse> createConsultaDocumento(ConsultaDocumentoResponse value) {
        return new JAXBElement<ConsultaDocumentoResponse>(_ConsultaDocumento_QNAME, ConsultaDocumentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaExpedienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsultaExpediente")
    public JAXBElement<ConsultaExpedienteResponse> createConsultaExpediente(ConsultaExpedienteResponse value) {
        return new JAXBElement<ConsultaExpedienteResponse>(_ConsultaExpediente_QNAME, ConsultaExpedienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDocumentoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsultaDocumentoRequest")
    public JAXBElement<ConsultaDocumentoRequest> createConsultaDocumentoRequest(ConsultaDocumentoRequest value) {
        return new JAXBElement<ConsultaDocumentoRequest>(_ConsultaDocumentoRequest_QNAME, ConsultaDocumentoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoReconocimientoMedicoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ResultadoReconocimientoMedicoRequest")
    public JAXBElement<ResultadoReconocimientoMedicoRequest> createResultadoReconocimientoMedicoRequest(ResultadoReconocimientoMedicoRequest value) {
        return new JAXBElement<ResultadoReconocimientoMedicoRequest>(_ResultadoReconocimientoMedicoRequest_QNAME, ResultadoReconocimientoMedicoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaExpedienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.scortelemed.com/schemas/ama", name = "ConsultaExpedienteResponse")
    public JAXBElement<ConsultaExpedienteResponse> createConsultaExpedienteResponse(ConsultaExpedienteResponse value) {
        return new JAXBElement<ConsultaExpedienteResponse>(_ConsultaExpedienteResponse_QNAME, ConsultaExpedienteResponse.class, null, value);
    }

}

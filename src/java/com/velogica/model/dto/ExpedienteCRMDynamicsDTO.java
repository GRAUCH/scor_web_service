package com.velogica.model.dto;

public class ExpedienteCRMDynamicsDTO {

    /**
     * códigoST del expediente
     */
    String codigoExpedienteST;

    /**
     * códigoST de la compañia cliente
     */
    String codigoCompanyiaST;

    /**
     * número de solicitud del expediente
     */
    String numSolicitud;

    /**
     * número de póliza
     */
    String numPoliza;

    /**
     * número de subpóliza (en este caso indica cuántos expedientes hay en esa póliza)
     */
    String numSubPoliza;

    /**
     * código del producto para la compañía
     */
    String codigoProductoCompanyia;

    /**
     * código de estado del expediente
     */
    String codigoEstadoExpediente;

    /**
     * número de certificado del expediente
     */
    String numCertificado;

    /**
     * las observaciones de la tarificación del expediente
     */
    String observacionesTarificacion;

    /**
     * la ubicación en el gestor documental de los documentos asociados al expediente
     */
    String nodoAlfresco;

    /**
     * la fecha de apertura del expediente
     */
    String fechaApertura;

    public ExpedienteCRMDynamicsDTO() {

    }

    public ExpedienteCRMDynamicsDTO(String codigoExpedienteST, String numSolicitud, String codigoEstadoExpediente, String codigoProductoCompanyia, String numPoliza, String numCertificado, String observacionesTarificacion, String nodoAlfresco, String fechaApertura) {
        this.codigoExpedienteST = codigoExpedienteST;
        this.numSolicitud = numSolicitud;
        this.numPoliza = numPoliza;
        this.codigoProductoCompanyia = codigoProductoCompanyia;
        this.codigoEstadoExpediente = codigoEstadoExpediente;
        this.numCertificado = numCertificado;
        this.observacionesTarificacion = observacionesTarificacion;
        this.nodoAlfresco = nodoAlfresco;
        this.fechaApertura = fechaApertura;
    }

    public ExpedienteCRMDynamicsDTO(String codigoExpedienteST, String codigoCompanyiaST, String numSolicitud, String numPoliza, String numSubPoliza, String codigoProductoCompanyia, String codigoEstadoExpediente, String numCertificado, String observacionesTarificacion, String nodoAlfresco, String fechaApertura) {
        this.codigoExpedienteST = codigoExpedienteST;
        this.codigoCompanyiaST = codigoCompanyiaST;
        this.numSolicitud = numSolicitud;
        this.numPoliza = numPoliza;
        this.numSubPoliza = numSubPoliza;
        this.codigoProductoCompanyia = codigoProductoCompanyia;
        this.codigoEstadoExpediente = codigoEstadoExpediente;
        this.numCertificado = numCertificado;
        this.observacionesTarificacion = observacionesTarificacion;
        this.nodoAlfresco = nodoAlfresco;
        this.fechaApertura = fechaApertura;
    }

    public ExpedienteCRMDynamicsDTO(ExpedienteCRMDynamicsDTO expedienteCRMDynamicsDTO) {
        this.codigoExpedienteST = expedienteCRMDynamicsDTO.getCodigoExpedienteST();
        this.codigoCompanyiaST = expedienteCRMDynamicsDTO.getCodigoCompanyiaST();
        this.numSolicitud = expedienteCRMDynamicsDTO.getNumSolicitud();
        this.numPoliza = expedienteCRMDynamicsDTO.getNumPoliza();
        this.numSubPoliza = expedienteCRMDynamicsDTO.getNumSubPoliza();
        this.codigoProductoCompanyia = expedienteCRMDynamicsDTO.getCodigoProductoCompanyia();
        this.codigoEstadoExpediente = expedienteCRMDynamicsDTO.getCodigoEstadoExpediente();
        this.numCertificado = expedienteCRMDynamicsDTO.getNumCertificado();
        this.observacionesTarificacion = expedienteCRMDynamicsDTO.getObservacionesTarificacion();
        this.nodoAlfresco = expedienteCRMDynamicsDTO.getNodoAlfresco();
        this.fechaApertura = expedienteCRMDynamicsDTO.getFechaApertura();
    }

    public String getCodigoExpedienteST() {
        return codigoExpedienteST;
    }

    public void setCodigoExpedienteST(String codigoExpedienteST) {
        this.codigoExpedienteST = codigoExpedienteST;
    }

    public String getCodigoCompanyiaST() {
        return codigoCompanyiaST;
    }

    public void setCodigoCompanyiaST(String codigoCompanyiaST) {
        this.codigoCompanyiaST = codigoCompanyiaST;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getNumPoliza() {
        return numPoliza;
    }

    public void setNumPoliza(String numPoliza) {
        this.numPoliza = numPoliza;
    }

    public String getNumSubPoliza() {
        return numSubPoliza;
    }

    public void setNumSubPoliza(String numSubPoliza) {
        this.numSubPoliza = numSubPoliza;
    }

    public String getCodigoProductoCompanyia() {
        return codigoProductoCompanyia;
    }

    public void setCodigoProductoCompanyia(String codigoProductoCompanyia) {
        this.codigoProductoCompanyia = codigoProductoCompanyia;
    }

    public String getCodigoEstadoExpediente() {
        return codigoEstadoExpediente;
    }

    public void setCodigoEstadoExpediente(String codigoEstadoExpediente) {
        this.codigoEstadoExpediente = codigoEstadoExpediente;
    }

    public String getNumCertificado() {
        return numCertificado;
    }

    public void setNumCertificado(String numCertificado) {
        this.numCertificado = numCertificado;
    }

    public String getObservacionesTarificacion() {
        return observacionesTarificacion;
    }

    public void setObservacionesTarificacion(String observacionesTarificacion) {
        this.observacionesTarificacion = observacionesTarificacion;
    }

    public String getNodoAlfresco() {
        return nodoAlfresco;
    }

    public void setNodoAlfresco(String nodoAlfresco) {
        this.nodoAlfresco = nodoAlfresco;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
}
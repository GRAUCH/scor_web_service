package com.scortelemed.schemas.caser;

public class ExpedienteCRMDynamics{

    /*
        A.Scor_name as Num_Expediente, E.scor_codigoST as CodCompania,              A.scor_nsolicitud_compania as Num_Solic_Cia
        A.Scor_name as Num_Expediente, E.scor_codigoST as CodCompania,              A.scor_nsolicitud_compania as Num_Solic_Cia
        A.Scor_name as Num_Expediente, E.scor_codigoST as CodCompania,              A.scor_nsolicitud_compania as Num_Solic_Cia,    a.Scor_nsubpoliza
        A.Scor_name as Num_Expediente, E.scor_codigoST as CodCompania,              A.scor_nsolicitud_compania as Num_Solic_Cia,    a.Scor_nsubpoliza,      A.scor_productoidName,  a.Scor_fechadeestado,   a.scor_estado
        A.Scor_name as Num_Expediente, A.scor_nsolicitud_compania as Num_Solic_Cia, a.scor_estado,                                  A.scor_productoidName,  a.Scor_npoliza,         a.Scor_fechadeestado
    */

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
     * número de subpóliza (en este caso indica cuántos expedientes hay en esa póliza)
     */
    String numSubPoliza;

    public ExpedienteCRMDynamics(){

    }

    public ExpedienteCRMDynamics(String codigoExpedienteST, String codigoCompanyiaST, String numSolicitud) {
        this.codigoExpedienteST = codigoExpedienteST;
        this.codigoCompanyiaST = codigoCompanyiaST;
        this.numSolicitud = numSolicitud;
    }

    // A.Scor_name as Num_Expediente, E.scor_codigoST as CodCompania,              A.scor_nsolicitud_compania as Num_Solic_Cia,    a.Scor_nsubpoliza       ,a.Scor_fechadeestado

    public ExpedienteCRMDynamics(String codigoExpedienteST, String codigoCompanyiaST, String numSolicitud, String numSubPoliza, String fecha) {
        this.codigoExpedienteST = codigoExpedienteST;
        this.codigoCompanyiaST = codigoCompanyiaST;
        this.numSolicitud = numSolicitud;
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

}
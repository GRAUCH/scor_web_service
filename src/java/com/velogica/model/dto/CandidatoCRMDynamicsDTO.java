package com.velogica.model.dto;

public class CandidatoCRMDynamicsDTO {

    /**
     * entiendo que es el DNI del candidato.
     */
    String numeroDocumento;

    /**
     * número de teléfono 1 del candidato
     */

    String telefonol;

    /**
     * tipo de teléfono 1 del candidato (1 es MOVIL, 2 es FIJO)
     */

    int tipoTelefonol;

    /**
     * número de teléfono 2 del candidato
     */
    String telefono2;

    /**
     * tipo de teléfono 2 del candidato (1 es MOVIL, 2 es FIJO)
     */

    int tipoTelefono2;

    /**
     * número de teléfono 3 del candidato
     */
    String telefono3;

    /**
     * tipo de teléfono 3 del candidato (1 es MOVIL, 2 es FIJO)
     */

    int tipoTelefono3;

    /**
     * dirección de vivienda del candidato
     */

    String direccion;

    /**
     * codigo postal de la vivienda del candidato
     */
    String codPostal;

    /**
     * localidad de la vivienda del candidato
     */
    String localidad;

    /**
     * provincia de la vivienda del candidato
     */
    String provincia;

    public CandidatoCRMDynamicsDTO() {

    }

    public CandidatoCRMDynamicsDTO(String numeroDocumento, String telefonol, int tipoTelefonol, String telefono2, int tipoTelefono2, String telefono3, int tipoTelefono3, String direccion, String codPostal, String localidad, String provincia) {
        this.numeroDocumento = numeroDocumento;
        this.telefonol = telefonol;
        this.tipoTelefonol = tipoTelefonol;
        this.telefono2 = telefono2;
        this.tipoTelefono2 = tipoTelefono2;
        this.telefono3 = telefono3;
        this.tipoTelefono3 = tipoTelefono3;
        this.direccion = direccion;
        this.codPostal = codPostal;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public CandidatoCRMDynamicsDTO(CandidatoCRMDynamicsDTO candidatoCRMDynamicsDTO) {
        this.numeroDocumento = candidatoCRMDynamicsDTO.getNumeroDocumento();
        this.telefonol = candidatoCRMDynamicsDTO.getTelefonol();
        this.tipoTelefonol = candidatoCRMDynamicsDTO.getTipoTelefonol();
        this.telefono2 = candidatoCRMDynamicsDTO.getTelefono2();
        this.tipoTelefono2 = candidatoCRMDynamicsDTO.getTipoTelefono2();
        this.telefono3 = candidatoCRMDynamicsDTO.getTelefono3();
        this.tipoTelefono3 = candidatoCRMDynamicsDTO.getTipoTelefono3();
        this.direccion = candidatoCRMDynamicsDTO.getDireccion();
        this.codPostal = candidatoCRMDynamicsDTO.getCodPostal();
        this.localidad = candidatoCRMDynamicsDTO.getLocalidad();
        this.provincia = candidatoCRMDynamicsDTO.getProvincia();
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefonol() {
        return telefonol;
    }

    public void setTelefonol(String telefonol) {
        this.telefonol = telefonol;
    }

    public int getTipoTelefonol() {
        return tipoTelefonol;
    }

    public void setTipoTelefonol(int tipoTelefonol) {
        this.tipoTelefonol = tipoTelefonol;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public int getTipoTelefono2() {
        return tipoTelefono2;
    }

    public void setTipoTelefono2(int tipoTelefono2) {
        this.tipoTelefono2 = tipoTelefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public int getTipoTelefono3() {
        return tipoTelefono3;
    }

    public void setTipoTelefono3(int tipoTelefono3) {
        this.tipoTelefono3 = tipoTelefono3;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
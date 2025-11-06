package com.velogica.model.dto;

public class CoberturasCRMDynamicsDTO {

    /**
     * nombre de la cobertura
     */
    String nombreCobertura;

    /**
     * código de la cobertura
     */
    String codigoCobertura;

    /**
     * capital de la cobertura.
     */
    String capitalCobertura;

    /**
     * resultado de la cobertura
     */
    String resultadoCobertura;

    /**
     * codigo del resultado de la cobertura
     */
    String codigoResultadoCobertura;

    /**
     * valoración de la prima de la cobertura
     */
    String valoracionPrima;

    /**
     * valoración del capital de la cobertura
     */
    String valoracionCapital;

    /**
     * exclusiones de la cobertura
     */
    String exclusiones;

    public CoberturasCRMDynamicsDTO() {

    }

    public CoberturasCRMDynamicsDTO(String nombreCobertura, String codigoCobertura, String capitalCobertura, String resultadoCobertura, String codigoResultadoCobertura, String valoracionPrima, String valoracionCapital, String exclusiones) {
        this.nombreCobertura = nombreCobertura;
        this.codigoCobertura = codigoCobertura;
        this.capitalCobertura = capitalCobertura;
        this.resultadoCobertura = resultadoCobertura;
        this.codigoResultadoCobertura = codigoResultadoCobertura;
        this.valoracionPrima = valoracionPrima;
        this.valoracionCapital = valoracionCapital;
        this.exclusiones = exclusiones;
    }

    public String getNombreCobertura() {
        return nombreCobertura;
    }

    public void setNombreCobertura(String nombreCobertura) {
        this.nombreCobertura = nombreCobertura;
    }

    public String getCodigoCobertura() {
        return codigoCobertura;
    }

    public void setCodigoCobertura(String codigoCobertura) {
        this.codigoCobertura = codigoCobertura;
    }

    public String getCapitalCobertura() {
        return capitalCobertura;
    }

    public void setCapitalCobertura(String capitalCobertura) {
        this.capitalCobertura = capitalCobertura;
    }

    public String getResultadoCobertura() {
        return resultadoCobertura;
    }

    public void setResultadoCobertura(String resultadoCobertura) {
        this.resultadoCobertura = resultadoCobertura;
    }

    public String getCodigoResultadoCobertura() {
        return codigoResultadoCobertura;
    }

    public void setCodigoResultadoCobertura(String codigoResultadoCobertura) {
        this.codigoResultadoCobertura = codigoResultadoCobertura;
    }

    public String getValoracionPrima() {
        return valoracionPrima;
    }

    public void setValoracionPrima(String valoracionPrima) {
        this.valoracionPrima = valoracionPrima;
    }

    public String getValoracionCapital() {
        return valoracionCapital;
    }

    public void setValoracionCapital(String valoracionCapital) {
        this.valoracionCapital = valoracionCapital;
    }

    public String getExclusiones() {
        return exclusiones;
    }

    public void setExclusiones(String exclusiones) {
        this.exclusiones = exclusiones;
    }
}

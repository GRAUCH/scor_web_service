package com.scor.global;

public class Uri {

    //scorWebservice/services/enginyers/EnginyersUnderwrittingCaseManagement

    String application;
    String servicio;
    String company;
    String endpoint;

    public Uri(String application, String servicio, String company, String endpoint) {
        this.application = application;
        this.servicio = servicio;
        this.company = company;
        this.endpoint = endpoint;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}

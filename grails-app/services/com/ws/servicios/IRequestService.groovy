package com.ws.servicios

import com.scortelemed.Company
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion

interface IRequestService {

    Request getBBDDRequest(Request requestInstance, String opername, String schema, Class<?> myObjectClass)
    def unmarshall(String entrada, Class<?> myObjectClass)
    String marshall(def objetoRelleno, TipoCompany company)
    String marshall(def objetoRelleno, Class<?> clase)
    String marshall(String nameSpace, def objetoRelleno, Class<?> clase)
    Request crear(String message, String requestXML)
    void insertarRecibido(Company company, String identificador, String info, TipoOperacion operacion)
    void insertarError(Company company, String identificador, String info, TipoOperacion operacion, String detalleError)
    void insertarEnvio (Company company, String identificador, String info)
    void insertarRecibido(String companyId, String identificador, String info, TipoOperacion operacion)
    void insertarError(String companyId, String identificador, String info, TipoOperacion operacion, String detalleError)
    void insertarEnvio (String companyId, String identificador, String info)

}
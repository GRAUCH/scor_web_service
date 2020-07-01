package com.ws.servicios

import com.scortelemed.Company
import com.scortelemed.Request
import com.scortelemed.TipoOperacion

interface IRequestService {

    def getBBDDRequest(Request requestInstance, String opername, String schema, Class<?> myObjectClass)
    def unmarshall(String entrada, Class<?> myObjectClass)
    def marshall(def objetoRelleno, Class<?> clase)
    def marshall(String nameSpace, def objetoRelleno, Class<?> clase)
    def crear(message, requestXML)
    void insertarRecibido(Company company, String identificador, String info, TipoOperacion operacion)
    void insertarError(Company company, String identificador, String info, TipoOperacion operacion, String detalleError)
    void insertarEnvio (Company company, String identificador, String info)
    void insertarRecibido(String companyId, String identificador, String info, TipoOperacion operacion)
    void insertarError(String companyId, String identificador, String info, TipoOperacion operacion, String detalleError)
    void insertarEnvio (String companyId, String identificador, String info)

}
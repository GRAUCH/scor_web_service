package com.ws.servicios

import com.scortelemed.Request

interface IRequestService {

    def getBBDDRequest(Request requestInstance, String opername, String schema, Class<?> myObjectClass)
    def jaxbParser(String entrada, Class<?> myObjectClass)
    def marshall(def objetoRelleno, Class<?> clase)
    def marshall(String nameSpace, def objetoRelleno, Class<?> clase)
    def crear(message, requestXML)

}
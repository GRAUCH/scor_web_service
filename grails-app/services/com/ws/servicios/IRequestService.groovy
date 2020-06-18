package com.ws.servicios

interface IRequestService {

    def jaxbParser(String entrada, Class<?> myObjectClass)
    def marshall(def objetoRelleno, Class<?> clase)
    def marshall(String nameSpace, def objetoRelleno, Class<?> clase)
}
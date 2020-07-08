package com.ws.servicios

import com.scortelemed.Request

interface ICompanyService {

    String marshall(def objeto)
    def buildDatos(Request req, String codigoSt)
    def getCodigoStManual(Request req)

}
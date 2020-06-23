package com.ws.servicios

import com.scortelemed.Request

interface ICompanyService {

    def buildDatos(Request req, String codigoSt)
    def getCodigoStManual(Request req)

}
package com.ws.servicios

import com.scortelemed.TipoCompany
import com.scortelemed.Request

interface IExpedienteService {

    def crearExpediente(Request req, TipoCompany comp)

}
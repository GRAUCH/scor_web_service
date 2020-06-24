package com.ws.servicios

import com.scortelemed.TipoCompany
import com.scortelemed.Request
import servicios.Filtro

interface IExpedienteService {

    def consultaExpediente(String ou, Filtro filtro)
    def consultaExpedienteCodigoST(String codigoST, String unidadOrganizativa)
    def consultaExpedienteNumSolicitud(String requestNumber, String unidadOrganizativa, String codigoST)
    def crearExpediente(Request req, TipoCompany comp)

}
package com.ws.servicios

import com.scortelemed.TipoCompany
import servicios.Expediente
import servicios.ExpedienteInforme

interface IComprimidoService {

    def obtenerZip(String nodo)
    def obtenerZip(Expediente expediente)
    def obtenerZip(TipoCompany tipoCompany, Expediente expediente)
}
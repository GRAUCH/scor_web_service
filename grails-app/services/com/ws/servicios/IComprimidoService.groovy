package com.ws.servicios

import com.scortelemed.TipoCompany
import servicios.Expediente
import servicios.ExpedienteInforme

interface IComprimidoService {

    def obtenerZip(String nodo)
    def obtenerZip(Expediente expediente)

}
package com.ws.servicios

import servicios.ExpedienteInforme

interface IComprimidoService {

    def obtenerZip(String nodo)
    def obtenerZip(ExpedienteInforme expediente)

}
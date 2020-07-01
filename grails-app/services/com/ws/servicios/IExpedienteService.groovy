package com.ws.servicios

import com.scortelemed.TipoCompany
import com.scortelemed.Request
import servicios.Expediente
import servicios.Filtro

interface IExpedienteService {

    def consultaExpediente(String ou, Filtro filtro)
    def consultaExpedienteCodigoST(String codigoST, String unidadOrganizativa)
    def consultaExpedienteNumSolicitud(String requestNumber, String unidadOrganizativa, String codigoST)
    def obtenerInformeExpedientes(String companya, String servicioScor, int estado, String fechaIni, String fechaFin, String pais)
    def obtenerInformeExpedientesSiniestros(String companya, String producto, int estado, String fechaIni, String fechaFin, String pais)
    def modificaExpediente(String pais, Expediente expediente, def servicioScorList, def paqueteScorList)
    def crearExpediente(Request req, TipoCompany comp)

}
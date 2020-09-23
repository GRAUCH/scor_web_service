package com.ws.servicios

import com.scortelemed.Company
import com.scortelemed.TipoCompany
import com.scortelemed.Request
import com.ws.enumeration.UnidadOrganizativa
import servicios.Expediente
import servicios.Filtro
import servicios.Usuario

interface IExpedienteService {

    def consultaExpediente(pais, filtro)
    def consultaExpedienteCodigoST(String codigoST, UnidadOrganizativa pais)
    def consultaExpedienteNumSolicitud(String requestNumber, UnidadOrganizativa pais, String codigoST)
    def obtenerInformeExpedientes(String companya, String servicioScor, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais)
    def obtenerInformeExpedientesSiniestros(String companya, String producto, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais)
    def modificaExpediente(UnidadOrganizativa pais, Expediente expediente, def servicioScorList, def paqueteScorList)
    boolean crearExpediente(Request req, TipoCompany comp)
    def busquedaCrm(Request requestBBDD, Company company, String requestNumber, String certificateNumber, String policyNumber)
    Usuario obtenerUsuarioFrontal(UnidadOrganizativa unidadOrganizativa)

}
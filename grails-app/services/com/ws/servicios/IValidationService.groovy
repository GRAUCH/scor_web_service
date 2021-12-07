package com.ws.servicios

import com.scortelemed.Validation

interface IValidationService {

    Collection<Validation> obtenerValidaciones(String codigoCompanyiaST, String unidadOrganizativaCompanyia, String codigoProductoCompanyia)
    Set<String> obtenerBenefitCodes(Collection<Validation> validaciones)
}

package com.scortelemed

import com.scor.global.CompanyLog
import grails.plugin.springsecurity.annotation.Secured
import hwsol.webservices.LogUtil

import java.text.SimpleDateFormat

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class DashboardController {

    def elementos
    def nombre
    def idCia
    def enviados
    def recibidos
    def company
    def vista

    def index(params) {

        params.max = Math.min(params.max ? params.int('max') : 10, 10)
        LogUtil logUtil = new LogUtil()
        String ou = session.getAttribute("ou")
        List<CompanyLog> ciasLog = new ArrayList<CompanyLog>()
        ciasLog = logUtil.obtenerCopaniasLog(ou)
        Company company = null

        Calendar calHasta = Calendar.getInstance()
        calHasta.set(Calendar.HOUR_OF_DAY, 23)
        calHasta.set(Calendar.MINUTE, 59)
        calHasta.set(Calendar.SECOND, 59)
        calHasta.set(Calendar.MILLISECOND, 0)

        Date hasta = calHasta.getTime()

        Calendar calDesde = Calendar.getInstance()
        calDesde.set(Calendar.HOUR_OF_DAY, 0)
        calDesde.set(Calendar.MINUTE, 0)
        calDesde.set(Calendar.SECOND, 0)
        calDesde.set(Calendar.MILLISECOND, 0)
        Date desde = calDesde.getTime()

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy")

        if (params.logs != null) {
            idCia = params.idCia
            nombre = Company.findById(idCia).nombre
            company = Company.findById(idCia)
        }

        if (params.logs == null) {
            [ciasLog: ciasLog, company: nombre, elementos: elementos, ou: ou, desde: formatter.format(desde), hasta: formatter.format(hasta), max: params.max, lista: null, idCia: idCia]
        } else if (params.logs == 'recibido') {
            elementos = logUtil.obtenerRecibidos(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaRecibidos" + nombre + ".gsp", idCia: idCia]
        } else if (params.logs == 'error') {
            elementos = logUtil.obtenerErrores(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaErrores.gsp", idCia: idCia]
        } else {
            elementos = logUtil.obtenerEnviados(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaEnviados" + nombre + ".gsp", idCia: idCia]
        }


    }

}

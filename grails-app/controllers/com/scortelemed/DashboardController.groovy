package com.scortelemed

import com.scor.global.CompanyLog
import grails.plugin.springsecurity.annotation.Secured
import hwsol.factory.LogFactory
import hwsol.utilities.LogUtil
import hwsol.webservices.LogService

import java.text.SimpleDateFormat

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class DashboardController {

    def elementos
    def enviados
    def recibidos
    def company

    def index(params) {

        params.max = params.max ?: 10

        if (params.int('max') > 100) {
            params.max = 100
        }

        LogUtil logUtil = new LogUtil()
        String ou = session.getAttribute("ou")
        List<CompanyLog> ciasLog = session.getAttribute("ciasLog")

        if (ciasLog == null || ciasLog.isEmpty()) {
            ciasLog = logUtil.obtenerCompanyLogs(ou)
            session.setAttribute("ciasLog", ciasLog)
        }

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

        if (flash.message != null) {
            flash.message = flash.message
        } else if (params.logs == null) {
            flash.message = 'Seleccione una acción'
        } else if (params.idCia == null && !params.idCia.toString().isEmpty()) {
            flash.message = 'Seleccione una compania'
        } else {
            company = Company.findById(params.idCia)
        }

        if (params.logs == null || company == null) {
            [ciasLog: ciasLog, company: ' ', elementos: elementos, ou: ou, desde: formatter.format(desde), hasta: formatter.format(hasta), max: params.max, lista: null, idCia: ' ']
        } else if (params.logs == 'recibido') {
            LogService recibidos = LogFactory.newLogService(Recibido.class)
            elementos = recibidos.obtener(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: company.nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaRecibidos" + company.nombre + ".gsp", idCia: company.id]
        } else if (params.logs == 'error') {
            LogService errores = LogFactory.newLogService(Error.class)
            elementos = errores.obtener(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: company.nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaErrores.gsp", idCia: company.id]
        } else {
            LogService enviados = LogFactory.newLogService(Envio.class)
            elementos = enviados.obtener(company, formatter.parse(params.desde), hasta, params.max)
            [ciasLog: ciasLog, company: company.nombre, elementos: elementos, ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, lista: "listaEnviados.gsp", idCia: company.id]
        }


    }

}

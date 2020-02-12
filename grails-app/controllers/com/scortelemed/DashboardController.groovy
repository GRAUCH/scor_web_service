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

        Calendar calHasta = Calendar.getInstance()
        calHasta.set(Calendar.HOUR_OF_DAY, 23);
        calHasta.set(Calendar.MINUTE, 59);
        calHasta.set(Calendar.SECOND, 59);
        calHasta.set(Calendar.MILLISECOND, 0);

        Date hasta = calHasta.getTime()

        Calendar calDesde = Calendar.getInstance();
        calDesde.set(Calendar.HOUR_OF_DAY, 0);
        calDesde.set(Calendar.MINUTE, 0);
        calDesde.set(Calendar.SECOND, 0);
        calDesde.set(Calendar.MILLISECOND, 0);
        Date desde = calDesde.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        nombre = ciasLog.get(0).getName()
        idCia = ciasLog.get(0).getId()


        elementos = logUtil.obtenerRecibidos(Company.findById(idCia), desde, hasta, params.max)

        [ciasLog: ciasLog, company: nombre, elementos: elementos, vista: "recibido.gsp", ou: ou, desde: formatter.format(desde), hasta: formatter.format(hasta), max: params.max, recibidoCia: "listaRecibidos" + nombre + ".gsp", idCia: idCia]
    }


    def enviado(params) {

        params.max = Math.min(params.max ? params.int('max') : 10, 10)
        LogUtil logUtil = new LogUtil()
        String ou = session.getAttribute("ou")
        List<CompanyLog> ciasLog = new ArrayList<CompanyLog>()
        ciasLog = logUtil.obtenerCopaniasLog(ou)

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

        if (params.idCia == null) {
            /**Seleccionmos la primera de las companias
             *
             */
            nombre = ciasLog.get(0).getName()
            idCia = ciasLog.get(0).getId()
        } else {
            idCia = params.idCia
            nombre = Company.findById(idCia).nombre
        }
        Company company = Company.findById(idCia)
        if (params.desde != null && params.hasta != null) {

            calHasta = Calendar.getInstance()
            calHasta.setTime(formatter.parse(params.hasta))
            calHasta.set(Calendar.HOUR_OF_DAY, 23)
            calHasta.set(Calendar.MINUTE, 59)
            calHasta.set(Calendar.SECOND, 59)
            calHasta.set(Calendar.MILLISECOND, 0)

            hasta = calHasta.getTime()

            elementos = logUtil.obtenerEnviados(company, formatter.parse(params.desde), hasta, params.max)
            session.setAttribute("desde", params.desde)
            session.setAttribute("hasta", params.hasta)

            [ciasLog: ciasLog, company: nombre, elementos: elementos, vista: "recibido.gsp", ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, enviadoCia: "listaEnviados" + nombre + ".gsp", idCia: idCia]
        } else {
            elementos = logUtil.obtenerEnviados(company, desde, hasta,params.max)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, vista: "enviado.gsp", ou: ou, desde: formatter.format(desde), hasta: formatter.format(hasta), max: params.max, enviadoCia: "listaEnviados" + nombre + ".gsp", idCia: idCia]
        }
    }

    def recibido(params) {

        params.max = Math.min(params.max ? params.int('max') : 10, 10)
        LogUtil logUtil = new LogUtil()
        String ou = session.getAttribute("ou")
        List<CompanyLog> ciasLog = new ArrayList<CompanyLog>()
        ciasLog = logUtil.obtenerCopaniasLog(ou)

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

        if (params.idCia == null) {

            nombre = ciasLog.get(0).getName()
            idCia = ciasLog.get(0).getId()
        } else {

            idCia = params.idCia
            nombre = Company.findById(idCia).nombre

        }
        Company company = Company.findById(idCia)
        if (params.desde != null && params.hasta != null) {

            calHasta = Calendar.getInstance()
            calHasta.setTime(formatter.parse(params.hasta))
            calHasta.set(Calendar.HOUR_OF_DAY, 23)
            calHasta.set(Calendar.MINUTE, 59)
            calHasta.set(Calendar.SECOND, 59)
            calHasta.set(Calendar.MILLISECOND, 0)

            hasta = calHasta.getTime()


            elementos = logUtil.obtenerRecibidos(company, formatter.parse(params.desde), hasta, params.max)
            session.setAttribute("desde", params.desde)
            session.setAttribute("hasta", params.hasta)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, vista: "recibido.gsp", ou: ou, desde: params.desde, hasta: params.hasta, max: params.max, recibidoCia: "listaRecibidos" + nombre + ".gsp", idCia: idCia]

        } else {
            elementos = logUtil.obtenerRecibidos(company, desde, hasta, params.max)
            [ciasLog: ciasLog, company: nombre, elementos: elementos, vista: "recibido.gsp", ou: ou, desde: formatter.format(desde), hasta: formatter.format(hasta), max: params.max, recibidoCia: "listaRecibidos" + nombre + ".gsp", idCia: idCia]
        }
    }
}

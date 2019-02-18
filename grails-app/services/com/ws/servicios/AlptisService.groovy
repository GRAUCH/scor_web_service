package com.ws.servicios

import hwsol.webservices.FetchUtil

class AlptisService {

	/*
		Se conecta al CRM
	 */
	def fetchUtil = new FetchUtil()
	 
	def testManagement = {fecha,hora ->   		
		def xmlResultado = ""

		def sqlXml = fetchUtil.dameServiciosTestManagment(fecha.toString(),hora.toString(),"1019")

		def crmService = new ServiceCrmService ()
		xmlResultado = crmService.conexion(sqlXml)
		
		return xmlResultado	
	}
	
}
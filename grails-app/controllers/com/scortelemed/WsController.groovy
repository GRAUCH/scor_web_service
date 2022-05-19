package com.scortelemed

import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.AdditionalNote
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.BenefitInformation
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.BenefitResultValue
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Candidate
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Dossier
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Exclusion
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.File
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.MedicalReport
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Product
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.SaveDossierResultsResponseE
import com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.TemporalLoading
import com.scor.util.IntIncrement
import com.scortelemed.schemas.caser.XSDProcessExecutionPort
import com.scortelemed.schemas.caser.XSDProcessExecutionServiceLocator
import com.ws.alptis.sp.beans.AlptisUnderwrittingCaseResultsRequest
import com.ws.cajamar.beans.Cobertura
import com.ws.enumeration.UnidadOrganizativa
import com.ws.servicios.IComprimidoService
import com.ws.servicios.ServiceFactory
import com.ws.servicios.impl.companies.AmaService
import com.ws.servicios.impl.comprimidos.CommonZipService
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import hwsol.entities.parser.RegistrarEventoSCOR
import hwsol.utilities.LogUtil
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.codehaus.groovy.grails.web.servlet.HttpHeaders
import servicios.*
import wslite.http.auth.HTTPBasicAuthorization
import wslite.soap.SOAPClient
import wslite.soap.SOAPResponse

import javax.xml.rpc.holders.StringHolder
import java.text.SimpleDateFormat


@Secured('permitAll')
class WsController {
    def estadisticasService
    def logginService
    def soapAlptisRecetteWS
    def soapAlptisRecetteWSPRO
    def soapCajamarRecetteWS
    def soapCaserRecetteWS
    def requestService
    def expedienteService
    def tarificadorService
    CommonZipService commonZipService
    CorreoUtil correoUtil = new CorreoUtil()

    def caseresult = {

        def fechaFin
        def fechaIni
        def responseRecette
        def soap
        def opername = "caseresult"
        def cases = []
        List<Expediente> expedientes = new ArrayList<>()

        try {
            def company = Company.findByNombre('alptis')
            StringBuilder sbInfo = new StringBuilder("* Realizando proceso envio de informacion para " + company.nombre + " *")
            if (params.myGroup != null && params.myGroup == 'codigoST' && params.codigoST) {
                sbInfo.append(" al expediente con codigo ST ${params.codigoST}")
                expedientes.addAll(expedienteService.informeExpedienteCodigoST(params.codigoST, company.ou))
                sbInfo.append(" * se encontraron :  ${expedientes.size()}  expedientes con el codigo ST *")
            } else {
                //EJEMPLO DE URL:
                //http://192.168.1.188:8080/scorWebservice/ws/caseresult?ini=20150512 00:00:00:00&fin=20150512 23:59:59:59
                fechaIni = LogUtil.paramsToDateIni(params)
                fechaFin = LogUtil.paramsToDateFin(params)
                sbInfo.append(" con fecha inicio ").append(fechaIni).append("-").append(" con fecha fin ").append(fechaFin)
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 1, fechaIni, fechaFin, company.ou))
            }
            if (Environment.current.name?.equals("production")) {
                logginService.putInfoMessage("WS PRD")
                soap = soapAlptisRecetteWSPRO
            } else {
                logginService.putInfoMessage("WS PREPRO")
                soap = soapAlptisRecetteWS
            }
            def resulComprimidos = []
            def i = 0
            def contadorResultados = 0
            def x = 0

            logginService.putInfoMessage(sbInfo?.toString())

            if (expedientes && expedientes.size() > 0) {
                expedientes.each { item ->
                    if (item && item.nodoAlfresco) {
                        logginService.putInfoMessage("Obteniendo comprimido para expediente: " + item?.getCodigoST() + " con numero de poliza: " + item.getNumPoliza())
                        def comprimido = commonZipService.obtenerZip(item.nodoAlfresco)

                        if (comprimido) {
                            def xml = tarificadorService.obtenerXMLExpedientePorZip(comprimido)
                            resulComprimidos[x] = comprimido + "###" + xml
                            x++
                            cases.add(xml?.toString())
                        }

                    }
                }

                cases.each { caso ->
                    String identificador = null
                    String info = caso?.toString()?.replace("\n", "").replace("\t", "").replace(" ", "").trim()
                    Envio envio = new Envio()
                    envio.setFecha(new Date())
                    envio.setCia(company.id?.toString())
                    if (info?.toString()?.indexOf("dossierCode") > -1) {
                        int inicio = info?.toString()?.indexOf("dossierCode") + 12
                        identificador = info?.toString()?.substring(inicio, inicio + 8)
                        envio.setIdentificador(identificador)
                    }
                    envio.setInfo(info)
                    envio.save(flush: true)
                    logginService.putInfoMessage("Informacion expediente " + identificador + " enviado a " + company.nombre + " correctamente")
                }


                responseRecette = soap.send(connectTimeout: 600000, readTimeout: 600000) {
                    body {
                        AlptisUnderwrittingCaseResultsRequest(xmlns: "http://www.scortelemed.com/schemas/alptis") {
                            expedientes.each { item ->
                                tuw_cases() {
                                    //AQUI OBTENEMOS EL ZIP EN BYTES Y EL CONTENIDO DEL XML
                                    if (resulComprimidos[i]) {
                                        def resultado = resulComprimidos[i].split("###")
                                        if (resultado.size() == 2) {
                                            if (resultado[1].contains('tuw')) {
                                                mkp.yieldUnescaped(resultado[1]?.toString())
                                                zip(resultado[0])
                                                contadorResultados++
                                            }
                                        }
                                    }
                                    i++
                                }
                            }
                            dateRequested(params.date?.toString())
                            comments("ok")
                        }
                    }
                }

                def alptisUnderwrittingCaseResultsRequest = new AlptisUnderwrittingCaseResultsRequest()
                alptisUnderwrittingCaseResultsRequest.tuw_cases = cases
                alptisUnderwrittingCaseResultsRequest.dateRequested = fechaIni + " - " + fechaFin
                alptisUnderwrittingCaseResultsRequest.comments = "ok"
                alptisUnderwrittingCaseResultsRequest.respuesta_remota = responseRecette?.getBody()?.toString()

                def requestXML = requestService.marshall(alptisUnderwrittingCaseResultsRequest, AlptisUnderwrittingCaseResultsRequest.class)

                /**Remplazamos caracteres raros
                 *
                 */
                requestXML = requestXML?.toString()?.replace("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>", "").replace("&lt;/", "</").replace("&lt;", "<").replace("/&gt;", "/>").replace("&gt;", ">")
                requestService.crear("AlptisUnderwrittingCasesResultsRequest", requestXML)

                /**Enviamos correo informativo
                 *
                 */
                correoUtil.envioEmail("AlptisUnderwrittingCasesResultsRequest", null, contadorResultados)
                logginService.putInfoMessage("proceso envio de informacion para " + company.nombre + " terminado.")
            } else {
                logginService.putInfoMessage("No hay resultados para " + company.nombre)

            }
            flash.message = "** Se procesaron :" + expedientes.size() + " **  " + " Compania : " + company.nombre + " *"
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
        } catch (Exception ex) {
            logginService.putError("Endpoint-" + opername, "Error en al obtener resultados para las fechas " + fechaIni + "-" + fechaFin + ":" + ex)
            correoUtil.envioEmail("AlptisUnderwrittingCasesResultsRequest", cases?.toString() + ex, 0)
            flash.message = "KO - Ver logs"
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
//            responseRecette = soap.send(connectTimeout: 300000, readTimeout: 300000) {
//                body {
//                    AlptisUnderwrittingCaseResultsRequest(xmlns: "http://www.scortelemed.com/schemas/alptis") {
//                        dateRequested(params.date?.toString())
//                        comments(ex)
//                    }
//                }
//            }


        }
    }

    def caseresultCM = {
        String fechaFin
        String fechaIni
        def responseRecette
        def soap = soapCajamarRecetteWS
        def opername = "caseresultCM"
        def cases = []
        def resulExpedienteSoap = []
        SOAPResponse response = new SOAPResponse()
        def estadoCausa = []
        def coberturas = []

        //EJEMPLO DE URL:
        //http://localhost:8080/scorWebservices/ws/caseresultCM?ini=20160720 00:00:00:00&fin=20160720 23:59:59:59

        TransformacionUtil transformacion = new TransformacionUtil()

        try {

            def company = Company.findByNombre('cajamar')
            StringBuilder sbInfo = new StringBuilder(" * Realizando proceso envio de informacion para " + company.nombre + " *")
            sbInfo.append("\n")
            if (params.myGroup != null && params.myGroup == 'codigoST' && params.codigoST) {
                sbInfo.append(" al expediente con codigo ST ${params.codigoST}")
                resulExpedienteSoap.addAll(expedienteService.informeExpedienteCodigoST(params.codigoST, company.ou))
                sbInfo.append("se encontraron :  ${resulExpedienteSoap.size()}  expedientes con el codigo ST")
            } else {
                fechaIni = LogUtil.paramsToDateIni(params)
                fechaFin = LogUtil.paramsToDateFin(params)
                sbInfo.append(" con fecha inicio ").append(fechaIni).append("-").append(" con fecha fin ").append(fechaFin)
                resulExpedienteSoap.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 0, fechaIni, fechaFin, company.ou))
                resulExpedienteSoap.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 1, fechaIni, fechaFin, company.ou))
                resulExpedienteSoap.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 2, fechaIni, fechaFin, company.ou))
            }

            def client = new SOAPClient('https://www.generali.es/evi_vidaEmissioServWeb/services/TeleSeleccionHandlerService?Company=M')
            client.authorization = new HTTPBasicAuthorization("vevisct", "21yb51gs")
            Calendar cal = Calendar?.getInstance()
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
            Date fechaSalida = sdf.parse("2016/07/26")

            logginService.putInfoMessage(sbInfo?.toString())

            resulExpedienteSoap.each { expediente ->

                def excluirDelEnvio = false

                if (expediente instanceof ExpedienteInforme) {

                    cal.setTime(sdf.parse(expediente?.getFechaApertura()))

                    if (cal?.getTime()?.after(fechaSalida) || cal?.getTime()?.equals(fechaSalida)) {

                        def wpers = transformacion.transformarCodigoCliente(expediente?.getObservaciones())
                        def numeroSolicitud = expediente?.getNumSolicitud()
                        def producto = expediente?.getProducto()?.getCodigoProductoCompanya()
                        def ramo = transformacion.transformarRamo(expediente?.getProducto()?.getCodigoProductoCompanya())
                        estadoCausa = transformacion.transformarTipoCausaTerminacion(expediente?.getCodigoEstado(), expediente?.getMotivoAnulacion())

                        if (estadoCausa != null) {

                            //FECHA DE ULTIMOCAMBIOESTADO-ESTADO
                            cal.setTime(sdf.parse(expediente?.getFechaUltimoCambioEstado()))
                            def anioEstado = cal?.get(Calendar.YEAR)?.toString()
                            int dia = cal?.get(Calendar.DAY_OF_MONTH) - 1
                            def diaEstado = dia++ < 10 ? "0" + dia : dia?.toString()
                            int mes = cal?.get(Calendar.MONTH) + 1
                            def mesEstado = mes < 10 ? "0" + mes : mes?.toString()

                            //FECHA DE SOLICITUD-GENERACION
                            cal.setTime(sdf.parse(expediente?.getFechaSolicitud()))
                            def anioSolicitud = cal?.get(Calendar.YEAR)?.toString()
                            dia = cal?.get(Calendar.DAY_OF_MONTH) - 1
                            def diaSolicitud = dia++ < 10 ? "0" + dia : dia?.toString()
                            mes = cal?.get(Calendar.MONTH) + 1
                            def mesSolicitud = mes < 10 ? "0" + mes : mes?.toString()

                            //SERVICIOS
                            def tlabr = "0"
                            def tlcom = "0"

                            for (i in expediente?.getServicios()) {

                                if (Environment.current.name?.equals("production")) {
                                    if (i?.getCodigoStFacturacion()?.toString()?.equals("002245")) {
                                        tlabr = "1"
                                    } else if (i?.getCodigoStFacturacion()?.toString()?.equals("000991")) {
                                        tlcom = "1"
                                    }
                                } else {
                                    if (i?.getCodigoStFacturacion()?.toString()?.equals("002245")) {
                                        tlabr = "1"
                                    } else if (i?.getCodigoStFacturacion()?.toString()?.equals("001761")) {
                                        tlcom = "1"
                                    }
                                }
                            }

                            //COBERTURAS

                            def listaCoberturas = []
                            Cobertura cober = new Cobertura()

                            if (estadoCausa != null && estadoCausa[0]?.equals("01")) {

                                def todasRechazadas = transformacion.obtenerEstadoCoberturas(expediente?.getCoberturasExpediente())

                                for (i in expediente?.getCoberturasExpediente()) {

                                    String codigoCobertura = transformacion.obtenerCodigoCajamar(i?.getCodigoCobertura(), expediente?.getProducto()?.getCodigoProductoCompanya())

                                    if (codigoCobertura != "10001" && codigoCobertura != "10002") {

                                        try {
                                            cober = transformacion.devolverCoberturaCm(codigoCobertura, i, expediente?.getNumSolicitud())
                                        } catch (Exception ex) {
                                            excluirDelEnvio = true
                                            logginService.putInfoMessage("Expediente " + expediente?.getCodigoST() + " no tiene tarificacion para la cobertura: " + i?.getNombreCobertura() + ". Se excluye del envio")

                                        }

                                    } else {//10001,10002 devolvemos vacio

                                        cober = transformacion.devolverCoberturaVacia(codigoCobertura, todasRechazadas)

                                    }

                                    listaCoberturas << cober

                                }
                            } else {

                                cober.setCobern("")
                                cober.setVacobe("")
                                cober.setSobpri("")
                                cober.setSobmor("")

                                listaCoberturas << cober

                            }


                            String cadenaCoberturas = ""
                            listaCoberturas.each {

                                cadenaCoberturas = cadenaCoberturas + "<cobert>" + "<cobern>" + it?.getCobern() + "</cobern>" + "<sobmor>" + it?.getSobmor() + "</sobmor>" + "<sobpri>" + it?.getSobpri() + "</sobpri>" + "<vacobe>" + it?.getVacobe() + "</vacobe>" + "</cobert>"
                            }

                            if (!excluirDelEnvio) {
                                response = client.send(
                                        """<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:tel='http://teleseleccion.emissio.b2b.evi.generali.es/' xmlns:bean='http://bean.emissio.b2b.evi.generali.es'>
							   <soapenv:Header/>
							   <soapenv:Body>
							      <tel:executeValoracionTeleSeleccion>
							         <arg0>
							            <bean:valoracion>
							               <causte>""" + estadoCausa[1] + """</causte>
										   """ + cadenaCoberturas + """
										   <ffesta>
											  <aaests>""" + anioEstado + """</aaests>
							                  <ddests>""" + diaEstado + """</ddests>
							                  <mmests>""" + mesEstado + """</mmests>
							               </ffesta>
							               <ffgets>
							                  <aagets>""" + anioSolicitud + """</aagets>
							                  <ddgets>""" + diaSolicitud + """</ddgets>
							                  <mmgets>""" + mesSolicitud + """</mmgets>
							               </ffgets>
							               <gclien>
							                  <wpers>""" + wpers + """</wpers>
										   </gclien>
										   <gcontr>
											  <coder8></coder8>
											  <werrts></werrts>
										   </gcontr>
										   <gpetic>
											  <cia>8</cia>
											  <numref>""" + numeroSolicitud + """</numref>
							                  <yprodu>""" + producto + """</yprodu>
											  <yramex>""" + ramo + """</yramex>
							               </gpetic>
							               <tipote>""" + estadoCausa[0] + """</tipote>
										   <tlabr>""" + tlabr + """</tlabr>
										   <tlcom>""" + tlcom + """</tlcom>
										</bean:valoracion>
									 </arg0>
								  </tel:executeValoracionTeleSeleccion>
							   </soapenv:Body>
							</soapenv:Envelope>""")

                                render "OK"
                                Envio envio = new Envio()
                                envio.setFecha(new Date())
                                envio.setCia(company.id?.toString())
                                envio.setIdentificador(expediente?.getNumSolicitud())
                                envio.setInfo(response?.getText()?.trim())
                                envio.save(flush: true)
                                logginService.putInfoMessage("Informacion expediente " + expediente?.getCodigoST() + " enviado a " + company.nombre + " correctamente")
                            }


                        } else {

                            logginService.putInfoMessage("Expediente " + expediente?.getCodigoST() + " no cumple con ningun estado de notificacion")

                        }
                    }
                }
            }
            sbInfo.append("\n")
            sbInfo.append(" se procesaron cantidad : ${resulExpedienteSoap.size()}")
            logginService.putInfoMessage(" * proceso envio de informacion para " + company.nombre + " terminado. *")
            flash.message = sbInfo?.toString()
            return redirect(controller: 'dashboard', action: 'index',params: [idCia: ''])
        } catch (Exception ex) {
            logginService.putErrorMessage("Error: " + opername + ". " + ex?.getMessage()?.toString() + ". Detalles:" + ex.printStackTrace())
            flash.message = "KO - Ver logs"
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
        }
    }

    def caseresultCaser = {
        def fechaFin
        def fechaIni
        def responseRecette
        def soap = soapCaserRecetteWS
        def opername = "caseresultCaser"
        def cases = []
        List<Expediente> expedientes = new ArrayList<Expediente>()
        List<Cita> citas = new ArrayList<Cita>()
        List<Actividad> actividades = new ArrayList<Actividad>()
        def estadoCausa = []
        def coberturas = []
        def password
        def username
        List<RespuestaCRMInforme> expedientesInforme = new ArrayList<RespuestaCRMInforme>()

        //EJEMPLO DE URL:
        //http://localhost:8080/scorWebservices/ws/caseresultCaser?ini=20170519 00:00:00&fin=20170519 23:59:59

        TransformacionUtil transformacion = new TransformacionUtil()
        def company = Company.findByNombre('caser')
        try {
			logginService.putInfoMessage("WsController - Caser - Before 1st try")
            StringBuilder sbInfo = new StringBuilder(" * Realizando proceso envio de informacion para " + company.nombre + " *")
            sbInfo.append("\n")
            if (params.myGroup != null && params.myGroup == 'codigoST' && params.codigoST) {
				logginService.putInfoMessage("WsController - Caser - Retrieve data with codigoST")
                sbInfo.append(" al expediente con codigo ST ${params.codigoST}")
                expedientes.addAll(expedienteService.informeExpedienteCodigoST(params.codigoST, company.ou))
                sbInfo.append(" * se encontraron :  ${expedientes.size()}  expedientes con el codigo ST *")
				logginService.putInfoMessage("WsController - Caser - End retrieve data with codigoST")
            } else {
				logginService.putInfoMessage("WsController - Caser - Retrieve data with dates")
                fechaIni = LogUtil.paramsToDateIni(params)
                fechaFin = LogUtil.paramsToDateFin(params)
                sbInfo.append(" con fecha inicio ").append(fechaIni).append("-").append(" con fecha fin ").append(fechaFin)
                sbInfo.append("** compania " + company.codigoSt + " **")
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 0, fechaIni, fechaFin, company.ou))
				logginService.putInfoMessage("WsController - Caser - End Retrieve data with dates step 1")
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 1, fechaIni, fechaFin, company.ou))
				logginService.putInfoMessage("WsController - Caser - End Retrieve data with dates step 2")
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 2, fechaIni, fechaFin, company.ou))
				logginService.putInfoMessage("WsController - Caser - End Retrieve data with dates")
            }

            XSDProcessExecutionServiceLocator locator = new XSDProcessExecutionServiceLocator()

            if (Environment.current.name?.equals("production")) {
                sbInfo.append("** Clave  de caser  entorno -> production **")
                username = "caser"
                password = "a2aa10aPvQ8D5i6VDNwtXU5F7acSeKGre9PLL6iQEFLbbGfRgZdoHRzdygau"
                locator.setXSDProcessExecutionPortEndpointAddress("https://iwssgo.caser.es/sgowschannel/XSDProcessExecution?WSDL")
            } else {
                sbInfo.append("** Clave  de caser  entorno -> preproduction **")
                username = "caser"
                password = "abdbc632c0dd1807407c6ceee46e0ab48c0bc12c"
                locator.setXSDProcessExecutionPortEndpointAddress("https://iwssgotest.caser.es/sgowschannel/XSDProcessExecution?WSDL")
            }
			
            XSDProcessExecutionPort port = locator?.getXSDProcessExecutionPort()
            StringHolder salida = new StringHolder()
            logginService.putInfoMessage(sbInfo?.toString())
            RegistrarEventoSCOR entradaDetalle = new RegistrarEventoSCOR()
            String stringRequest = null
            //int erroneos = 0
			IntIncrement erroneos = new IntIncrement()
			logginService.putInfoMessage("WsController - Caser - For each expediente")
            expedientes.each { expediente ->
                entradaDetalle = transformacion.obtenerDetalle(expediente)
				logginService.putInfoMessage("WsController - Caser - After obtenerDetalle")
                if (entradaDetalle != null) {
                    stringRequest = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?><service_RegistrarEventoSCOR><inputMap type='map'><username type='String'><_value_>" + username + "</_value_></username><password type='String'><_value_>" + password + "</_value_></password><idExpediente type='Integer'><_value_>" + entradaDetalle?.getIdExpediente() + "</_value_></idExpediente><codigoEvento type='String'><_value_>" + entradaDetalle?.getCodigoEvento() + "</_value_></codigoEvento><detalle type='String'><_value_>" + entradaDetalle?.getDetalle() + "</_value_></detalle><fecha type='Date'><_value_>" + entradaDetalle?.getFechaCierre() + "</_value_></fecha></inputMap></service_RegistrarEventoSCOR>"
                    Thread.sleep(6000)
					logginService.putInfoMessage("WsController - Caser - Before 2nd try")
					logginService.putInfoMessage("WsController - Caser - body" + stringRequest)
                    try {
						logginService.putInfoMessage("WsController - Caser - before do procees")
                        port.doProcessExecution(stringRequest, salida)
						logginService.putInfoMessage("WsController - Caser - after do procees")
						Thread.sleep(6000)
                        Envio envio = new Envio()
                        envio.setFecha(new Date())
                        envio.setCia(company.id?.toString())
                        envio.setIdentificador(entradaDetalle?.getIdExpediente())
                        envio.setInfo(stringRequest)
                        envio.save(flush: true)
                        logginService.putInfoMessage("Informacion de salida envio de cambios en expedientes " + entradaDetalle?.getIdExpediente())
                        logginService.putInfoMessage("Informacion recibida de cambios en expedientes " + entradaDetalle?.getIdExpediente() + ":" + salida.value.trim())
                        logginService.putInfoMessage("Informacion expedientes " + entradaDetalle?.getIdExpediente() + " enviado a " + company.nombre + " correctamente")

                    } catch (Exception ex) {
                        /**Metemos en errores
                         *
                         */
                        //erroneos++
						logginService.putInfoMessage("WsController - Caser - inside catch")
						erroneos.inc()
                        Error error = new Error()
                        error.setFecha(new Date())
                        error.setCia(company.id?.toString())
                        error.setIdentificador(entradaDetalle?.getIdExpediente())
                        error.setInfo(stringRequest)
                        error.setOperacion("ENVIO ESTADO")
                        error.setError("Peticion no realizada para solicitud: " + entradaDetalle?.getIdExpediente() + ". Error: " + ex?.getMessage())
                        error.save(flush: true)
                        logginService.putErrorMessage("Error: " + opername + ". No se ha podido mandar el caso a Caser. Detalles:" + ex?.getMessage())
                    }
					logginService.putInfoMessage("WsController - Caser - after try")
                }
            }
			logginService.putInfoMessage("WsController - Caser - End For each expediente")
            logginService.putInfoMessage("proceso envio de informacion para " + company.nombre + " terminado.")
            logginService.putInfoMessage("** Se han procesado :" + expedientes.size() + " **" + "Correctamente/Erroneos:" + expedientes.size()-erroneos.getValue() + "/" + erroneos.getValue())
            sbInfo.append("\n")
            sbInfo.append("* se procesaron cantidad : ${expedientes.size()} *")
            flash.message = sbInfo?.toString()
            return redirect(controller: 'dashboard', action: 'index',params: [idCia: ''])
        } catch (Exception ex) {
			logginService.putInfoMessage("WsController - Caser - Catch 1st try")
            logginService.putErrorMessage("Error: " + opername + ". " + ex?.getMessage() + ". Detalles:" + ex?.getMessage())
            Error error = new Error()
            error.setFecha(new Date())
            error.setCia(company.id?.toString())
            error.setIdentificador(" ")
            error.setInfo("$controllerName")
            error.setOperacion(opername)
            error.setError("Peticion no realizada para solicitud: " + ex?.getMessage() + ". Error: " + ex?.getMessage())
            error.save(flush: true)
            flash.message = "KO - Ver logs"
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
        }
    }

    def caseresultAma = {

        def fechaFin
        def fechaIni
        def responseRecette
        def soap = soapCaserRecetteWS
        def opername = "caseresultAma"
        def cases = []
        def estadoCausa = []
        def coberturas = []

        List<Expediente> expedientes = new ArrayList<Expediente>()
        List<Cita> citas = new ArrayList<Cita>()
        List<Actividad> actividades = new ArrayList<Actividad>()
        List<File> files = new ArrayList<File>()
        DossierDataStoreWSStub stub
        SaveDossierResultsResponseE respuestaCRM = new SaveDossierResultsResponseE()
        List<BenefitInformation> listaBenefitInformation = new ArrayList<BenefitInformation>()
        TransformacionUtil transformacion = new TransformacionUtil()
        //EJEMPLO DE URL:
        //http://localhost:8080/scorWebservice/ws/caseresultAma?ini=20171107 00:00:00&fin=20171107 23:59:59

        try {
            def company = Company.findByNombre(TipoCompany.AMA.nombre)
            def companyVida = Company.findByNombre(TipoCompany.AMA_VIDA.nombre)
            def identificadorCaso
            Expediente expediente
            StringBuilder sbInfo = new StringBuilder(" *Realizando proceso envio de informacion para " + company.nombre + " *")
            sbInfo.append("\n")
            if (params.myGroup != null && params.myGroup == 'codigoST' && params.codigoST) {
                sbInfo.append(" al expediente con codigo ST ${params.codigoST}")
                expedientes.addAll(expedienteService.informeExpedienteCodigoST(params.codigoST, company.ou))
                sbInfo.append(" * se encontraron :  ${expedientes.size()}  expedientes con el codigo ST *")
            } else {
                fechaIni = LogUtil.paramsToDateIni(params)
                fechaFin = LogUtil.paramsToDateFin(params)
                sbInfo.append(" con fecha inicio ").append(fechaIni).append("-").append(" con fecha fin ").append(fechaFin)
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 1, fechaIni, fechaFin, company.ou))
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 2, fechaIni, fechaFin, company.ou))
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(companyVida.codigoSt, null, 1, fechaIni, fechaFin, company.ou))
                expedientes.addAll(expedienteService.obtenerInformeExpedientes(companyVida.codigoSt, null, 2, fechaIni, fechaFin, company.ou))
            }

            logginService.putInfoMessage(sbInfo?.toString())
            if (Environment.current.name?.equals("production")) {
                logginService.putInfoMessage(" ** end point de PRD**")
                def usuario = "aplCORWS"
                def password = "Wh1t3p&&\$"
                stub = new DossierDataStoreWSStub("https://servicios.amaseguros.com/AmaPublish/ama/amascortelemed-ws/services/DossierDataStoreWS?wsdl", usuario, password)
            } else {
                logginService.putInfoMessage(" ** end point de PREPRO**")
                def usuario = "aplCORWSPRE"
                def password = "IE4tKQA6"
                stub = new DossierDataStoreWSStub("https://pre-servicios.amaseguros.com/AMAPublish/ama/amascortelemed-ws/services/DossierDataStoreWS?wsdl", usuario, password)
            }

            long timeout = 3 * 60 * 1000; // Tres minuitos
            stub._getServiceClient()?.getOptions()?.setTimeOutInMilliSeconds(timeout);

            Dossier dossier = new Dossier()
            DossierDataStoreWSStub.SaveDossierResultsE resultsE = new DossierDataStoreWSStub.SaveDossierResultsE()
            DossierDataStoreWSStub.SaveDossierResults saveResult = new DossierDataStoreWSStub.SaveDossierResults()
            DossierDataStoreWSStub.DossierResultsIN saveResultIN = new DossierDataStoreWSStub.DossierResultsIN()

            if (expedientes) {
                for (int i = 0; i < expedientes.size(); i++) {
                    try {

                        expediente = expedientes?.get(i)
                        logginService.putInfoMessage(" ** Comienzo el proceso para el dossier: ${expediente?.getNumSolicitud()} de AMA**")
                        if (!seExcluyeEnvio(expediente)) {
                            identificadorCaso = expediente?.getNumSolicitud()
                            files = new ArrayList<File>()
                            listaBenefitInformation = new ArrayList<BenefitInformation>()
                            dossier.setDossierCode(expediente?.getNumSolicitud())
                            dossier.setResultsCode("OK")
                            dossier.setState((short) 1)

                            /**CANDIDATO
                             *
                             */
                            Candidate candidate = new Candidate()
                            candidate.setBirthDate(transformacion.fromStringToCalendar(expediente?.getCandidato()?.getFechaNacimiento()))
                            candidate.setFullName(expediente?.getCandidato()?.getNombre() + " " + expediente?.getCandidato()?.getApellidos())

                            if (expediente?.getCandidato()?.getSexo() != null) {
                                candidate.setGender(expediente?.getCandidato()?.getSexo() == TipoSexo.HOMBRE ? (short) 1 : (short) 2)
                            } else {
                                candidate.setGender((short) 1)
                            }

                            candidate.setOccupation("")
                            dossier.setCandidate(candidate)

                            /**CIA
                             *
                             */
                            com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Company cia = new com.amaseguros.amascortelemed_ws.webservices.DossierDataStoreWSStub.Company()


                            if (expediente?.getCandidato() != null && expediente?.getCandidato()?.getCompanya() != null && expediente?.getCandidato()?.getCompanya()?.getCodigoST()?.equals("1064")) {
                                cia.setCompanyCode("C0803")
                                cia.setCompanyDescription("A.M.A. Vida")
                            }
                            if (expediente?.getCandidato() != null && expediente?.getCandidato()?.getCompanya() != null && expediente?.getCandidato()?.getCompanya()?.getCodigoST()?.equals("1059")) {
                                cia.setCompanyCode("M0328")
                                cia.setCompanyDescription("A.M.A.")
                            }

                            dossier.setCompany(cia)
                            /**PRODUCTO
                             *
                             */
                            Product producto = new Product()

                            if (expediente?.getProducto() != null) {
                                if (expediente?.getProducto()?.getCodigoProductoCompanya()?.toString()?.equals("P49")) {
                                    producto.setProductCode("0013")
                                    logginService.putInfoMessage(" ** Producto: 0013 **")
                                } else {
                                    producto.setProductCode(expediente?.getProducto()?.getCodigoProductoCompanya())
                                    logginService.putInfoMessage(" ** Producto: ${expediente?.getProducto()?.getCodigoProductoCompanya()} **")
                                }
                                producto.setProductDescription(expediente?.getProducto()?.getNombre())
                                logginService.putInfoMessage(" ** Producto: ${expediente?.getProducto()?.getNombre()} **")
                            } else {
                                producto.setProductCode("")
                                producto.setProductDescription("")
                                logginService.putInfoMessage(" ** Sin producto **")
                            }

                            dossier.setProduct(producto)

                            if (expediente?.getCoberturasExpediente() != null && expediente?.getCoberturasExpediente()?.size() > 0) {

                                expediente?.getCoberturasExpediente()?.each { CoberturaExpediente coberturasPoliza ->


                                    BenefitInformation benefitInformation = new BenefitInformation();
                                    logginService.putInfoMessage(" ** Cobertura ${coberturasPoliza?.getCodigoCobertura()} **")
                                    benefitInformation.setBenefitName(coberturasPoliza?.getNombreCobertura());
                                    benefitInformation.setBenefitCode(coberturasPoliza?.getCodigoCobertura());

                                    BenefitResultValue benefitResultValue = new BenefitResultValue();

                                    benefitResultValue.setDescLoadingCapital("");
                                    benefitResultValue.setDescLoadingPremium("");
                                    benefitResultValue.setLoadingCapital(BigDecimal.valueOf(0));
                                    benefitResultValue.setLoadingPremium(BigDecimal.valueOf(0));


                                    if (coberturasPoliza?.getCodResultadoCobertura()?.trim()?.startsWith("3") && (notNullNotEmpty(coberturasPoliza?.getValoracionCapital()) || notNullNotEmpty(coberturasPoliza?.getValoracionPrima()))) {
                                        if(notNullNotEmpty(coberturasPoliza?.getValoracionCapital()) && notNullNotEmpty(coberturasPoliza?.getValoracionPrima())) {
                                            benefitInformation.setBenefitResultCode("3");
                                            benefitInformation.setBenefitResultType("Combinado");
                                            benefitResultValue.setDescLoadingCapital(coberturasPoliza?.getCodResultadoCobertura()?.toString())
                                            benefitResultValue.setDescLoadingPremium(coberturasPoliza?.getCodResultadoCobertura()?.toString())
                                            benefitResultValue.setLoadingCapital(new BigDecimal(coberturasPoliza?.getValoracionCapital()?.replace(",", ".")))
                                            benefitResultValue.setLoadingPremium(new BigDecimal(coberturasPoliza?.getValoracionPrima()?.replace(",", ".")))

                                        } else if (notNullNotEmpty(coberturasPoliza?.getValoracionCapital())) {
                                            benefitInformation.setBenefitResultCode("31");
                                            benefitInformation.setBenefitResultType("Sobremortalidad");
                                            benefitResultValue.setDescLoadingCapital(coberturasPoliza?.getCodResultadoCobertura()?.toString());
                                            benefitResultValue.setDescLoadingPremium("");
                                            benefitResultValue.setLoadingCapital(new BigDecimal(coberturasPoliza?.getValoracionCapital()?.replace(",", ".")));
                                            benefitResultValue.setLoadingPremium(new BigDecimal(0));

                                        } else if (notNullNotEmpty(coberturasPoliza?.getValoracionPrima())) {
                                            benefitInformation.setBenefitResultCode("30");
                                            benefitInformation.setBenefitResultType("Sobreprima");
                                            benefitResultValue.setDescLoadingCapital("");
                                            benefitResultValue.setDescLoadingPremium(coberturasPoliza?.getCodResultadoCobertura()?.toString());
                                            benefitResultValue.setLoadingCapital(new BigDecimal(0));
                                            benefitResultValue.setLoadingPremium(new BigDecimal(coberturasPoliza?.getValoracionPrima()?.replace(",", ".")));

                                        }
                                    } else if (coberturasPoliza?.getCodResultadoCobertura() != null) {
                                        benefitInformation.setBenefitResultCode(coberturasPoliza?.getCodResultadoCobertura());
                                        benefitInformation.setBenefitResultType(devolverLiteralCobertura(coberturasPoliza?.getCodResultadoCobertura()));
                                        benefitResultValue.setDescLoadingCapital("");
                                        benefitResultValue.setDescLoadingPremium("");
                                        benefitResultValue.setLoadingCapital(new BigDecimal(0));
                                        benefitResultValue.setLoadingPremium(new BigDecimal(0));

                                    } else {
                                        benefitInformation.setBenefitResultCode("");
                                        benefitInformation.setBenefitResultType("");
                                        benefitResultValue.setDescLoadingCapital("");
                                        benefitResultValue.setDescLoadingPremium("");
                                        benefitResultValue.setLoadingCapital(new BigDecimal(0));
                                        benefitResultValue.setLoadingPremium(new BigDecimal(0));

                                    }

                                    benefitInformation.setBenefitResultValue(benefitResultValue);

                                    if (transformacion.hasElements(coberturasPoliza?.getExclusiones())) {

                                        StringTokenizer st = new StringTokenizer(coberturasPoliza?.getExclusiones(), ";");

                                        while (st.hasMoreTokens()) {

                                            Exclusion exlusion = new Exclusion();
                                            exlusion.setText(st.nextToken());
                                            exlusion.setType("4");
                                            benefitInformation.addExclusionsList(exlusion);

                                        }

                                    } else {
                                        benefitInformation.setExclusionsList(null);
                                    }

                                    if (transformacion.hasElements(coberturasPoliza?.getInformesMedicos())) {

                                        StringTokenizer st = new StringTokenizer(coberturasPoliza?.getInformesMedicos(), ";");

                                        while (st.hasMoreTokens()) {

                                            MedicalReport medicalReport = new MedicalReport();
                                            medicalReport.setText(st.nextToken());
                                            medicalReport.setType("1");
                                            benefitInformation.addMedicalReportsList(medicalReport);

                                        }

                                    } else {
                                        benefitInformation.addMedicalReportsList(null);
                                    }

                                    if (transformacion.hasElements(coberturasPoliza?.getNotas())) {

                                        StringTokenizer st = new StringTokenizer(coberturasPoliza?.getNotas(), ";");

                                        while (st.hasMoreTokens()) {

                                            AdditionalNote note = new AdditionalNote();
                                            note.setText(st.nextToken());
                                            note.setType("5");
                                            benefitInformation.addAdditionalNotesList(note);

                                        }

                                    } else {
                                        benefitInformation.addAdditionalNotesList(null);
                                    }

                                    if (transformacion.hasElements(coberturasPoliza?.getValoracionTemporal())) {

                                        StringTokenizer st = new StringTokenizer(coberturasPoliza?.getValoracionTemporal(), ";");

                                        while (st.hasMoreTokens()) {

                                            TemporalLoading temporal = new TemporalLoading()
                                            temporal.setText(st.nextToken());
                                            temporal.setType("3");

                                            benefitInformation.addTemporalLoadingsList(temporal)
                                        }

                                    } else {
                                        benefitInformation.addTemporalLoadingsList(null);
                                    }

                                    if (notNullNotEmpty(coberturasPoliza?.getCapitalCobertura())) {
                                        dossier.setCapitalInsured(new BigDecimal(coberturasPoliza?.getCapitalCobertura()))
                                    } else {
                                        dossier.setCapitalInsured(new BigDecimal(0))
                                    }

                                    listaBenefitInformation.add(benefitInformation)
                                }
                                logginService.putInfoMessage("Agrego ZIP")
                                byte[] compressedData = commonZipService.obtenerZip(expediente.getNodoAlfresco())

                                File file = new File()
                                file.setFileBase64(new String(compressedData))
                                file.setTypeFile("zip")

                                files.add(file)
                                logginService.putInfoMessage("Termino de agregar el ZIP")

                                saveResultIN.setListFiles((File[]) files.toArray())
                                saveResultIN.setBenefitsInformationsList((BenefitInformation[]) listaBenefitInformation.toArray());
                                saveResultIN.setDossier(dossier)
                                saveResultIN?.getDossier()?.setResultsCode("OK");
                                saveResultIN?.getDossier()?.setState((short) 1);
                                saveResult.setDossierResultsIN(saveResultIN)
                                resultsE.setSaveDossierResults(saveResult)
                                logginService.putInfoMessage("Listo para enviar a AMA")
                                respuestaCRM = stub.saveDossierResults(resultsE);
                                logginService.putInfoMessage("** El resultado fue : **")
                                if (respuestaCRM != null && respuestaCRM?.localSaveDossierResultsResponse != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT() != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localTypeResult?.equals("OK")) {

                                    Envio envio = new Envio()
                                    envio.setFecha(new Date())
                                    envio.setCia(company.id?.toString())
                                    envio.setIdentificador(expediente?.getNumSolicitud())
                                    envio.setInfo("Estado: " + expediente?.getCodigoEstado()?.toString() + "-Respuesta: " + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                                    envio.save(flush: true)

                                    logginService.putInfoMessage("Informacion de salida de expediente " + expediente?.getCodigoST() + ". Codigo AMA: " + expediente?.getNumSolicitud())
                                    logginService.putInfoMessage("Respuesta desde AMA al envio de informacion para expediente " + expediente?.getCodigoST() + ". " + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                                    logginService.putInfoMessage("Informacion expediente " + expediente?.getCodigoST() + " enviado a " + company.nombre + " correctamente")

                                } else if (respuestaCRM != null && respuestaCRM?.localSaveDossierResultsResponse != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT() != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localTypeResult?.equals("ERROR")) {

                                    Error error = new Error()
                                    error.setFecha(new Date())
                                    error.setCia(company.id?.toString())
                                    error.setIdentificador(identificadorCaso)
                                    error.setInfo("Error en el envio")
                                    error.setOperacion("ENVIO")
                                    error.setError("Peticion no realizada para solicitud: " + identificadorCaso + ". Error: " + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                                    error.save(flush: true)

                                    logginService.putInfoMessage("Informacion de salida de expediente " + expediente?.getCodigoST() + ". Codigo AMA: " + expediente?.getNumSolicitud())
                                    logginService.putInfoMessage("Respuesta desde AMA al envio de informacion para expediente " + expediente?.getCodigoST() + ". ERROR:" + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                                    logginService.putInfoMessage("Informacion expediente " + expediente?.getCodigoST() + " no enviado a " + company.nombre + " correctamente")

                                } else {

                                    Error error = new Error()
                                    error.setFecha(new Date())
                                    error.setCia(company.id?.toString())
                                    error.setIdentificador(identificadorCaso)
                                    error.setInfo("Error en el envio")
                                    error.setOperacion("ENVIO")
                                    error.setError("Peticion no realizada para solicitud: " + identificadorCaso + ". Error: desconocido")
                                    error.save(flush: true)

                                    logginService.putInfoMessage("Informacion de salida de expediente " + expediente?.getCodigoST() + ". Codigo AMA: " + expediente?.getNumSolicitud())
                                    logginService.putInfoMessage("Respuesta desde AMA al envio de informacion para expediente " + expediente?.getCodigoST() + ". ERROR: Desconocido")
                                    logginService.putInfoMessage("IInformacion expediente " + expediente?.getCodigoST() + " no enviado a " + company.nombre + " correctamente")

                                }
                                logginService.putInfoMessage("** TERMINO PROCESO **")
                            } else {
                                logginService.putInfoMessage(" ** SIN COBERTURA -- NO SE ENVIA -- **")
                            }
                        }
                    } catch (Exception ex) {
                        Error error = new Error()
                        error.setFecha(new Date())
                        error.setCia(company.id?.toString())
                        error.setIdentificador(identificadorCaso)
                        error.setInfo("Error en el envio")
                        error.setOperacion("ENVIO")
                        if (respuestaCRM != null && respuestaCRM?.localSaveDossierResultsResponse != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT() != null && respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localTypeResult?.equals("ERROR")) {
                            error.setError("Peticion no realizada para solicitud: " + identificadorCaso + ". Error: " + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                            logginService.putInfoMessage("Informacion de salida de expediente " + expediente?.getCodigoST() + ". Codigo AMA: " + expediente?.getNumSolicitud())
                            logginService.putInfoMessage("Respuesta desde AMA al envio de informacion para expediente " + expediente?.getCodigoST() + ". ERROR: " + respuestaCRM?.localSaveDossierResultsResponse?.getDossierResultsOUT()?.localDescriptionResultList[0])
                            logginService.putInfoMessage("Informacion expediente " + expediente?.getCodigoST() + " no enviado a " + company.nombre)
                        } else {
                            error.setError("Peticion no realizada para solicitud: " + identificadorCaso + ". Error: " + ex?.getMessage())
                            logginService.putInfoMessage("Informacion de salida de expediente " + expediente?.getCodigoST() + ". Codigo AMA: " + expediente?.getNumSolicitud())
                            logginService.putInfoMessage("Respuesta desde AMA al envio de informacion para expediente " + expediente?.getCodigoST() + ". ERROR: " + ex?.getMessage())
                            logginService.putInfoMessage("Informacion expediente " + expediente?.getCodigoST() + " no enviado a " + company.nombre)
                        }
                        error.save(flush: true)
                        flash.message = "KO - Ver logs"
                        return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
                    }
                }
                logginService.putInfoMessage("Proceso envio de informacion para " + company.nombre + " terminado.")
                logginService.putInfoMessage("** Se enviaron :" + expedientes.size() + " **")
                sbInfo.append("\n")
                sbInfo.append("*  se procesaron cantidad : ${expedientes.size()} *")
                flash.message = sbInfo?.toString()
            } else {
                flash.message = "No hay expedientes disponibles bajo esos criterios de busqueda"
            }
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
        } catch (Exception ex) {

            logginService.putErrorMessage("Error: " + opername + ". No se ha podido mandar el caso a Ama. Detalles:" + ex?.getMessage())
            flash.message = "KO - Ver logs"
            return redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
        }
    }


    def generarZip() {
        flash.message = ""
        String codigost = params.codigoST
        String company = params.companyName
        TipoCompany tipoCompany = TipoCompany.fromNombre(company)
        UnidadOrganizativa unidad = expedienteService.obtenerUnidadOrganizativa(tipoCompany)
        RespuestaCRM respuestaCRM = expedienteService.consultaExpedienteCodigoST(codigost, unidad)
       if(respuestaCRM?.getListaExpedientes()?.size() > 0) {
           Expediente expediente = respuestaCRM?.getListaExpedientes()?.get(0)
           def zip = commonZipService.obtenerZipFile(expediente.nodoAlfresco)

           String contentDisposition = 'attachment'
           String mimeType2 = 'APPLICATION/OCTET-STREAM'

           response.resetBuffer()
           response.setContentType(mimeType2)
           response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "${contentDisposition};filename=${zip.nombre}")
           byte[] decodedBytes = Base64.getDecoder().decode(zip.content)
           response.outputStream << decodedBytes
           response.flushBuffer()

       }else{
           flash.message = "No hay documentación"
           redirect(controller: 'dashboard', action: 'index', params: [idCia: ''])
       }
    }

    boolean seExcluyeEnvio(Expediente expediente){

        if (expediente?.getServicios()?.size() == 1){

            return expediente?.getServicios()?.get(0)?.getCompanyaServicio()?.getCodigoST()?.equals("002561")

        }

        return false

    }

    String devolverLiteralCobertura (String code) {

        def literal

        switch (code) {
            case "1":
                literal = "Est�ndar"
                break
            case "2":
                literal = "Rechazo"
                break
            case "3":
                literal = "Combinado"
                break
            case "4":
                literal = "Exclusi�n"
                break
            case "5":
                literal = "Informe Medico"
                break
            case "6":
                literal = "Prueba Medica"
                break
            case "7":
                literal = "Rechazo Cobertura"
                break
            case "8":
                literal = "Posponer"
                break
            case "9":
                literal = "Consultar tarificador"
                break
            case "10":
                literal = "Acuerdo"
                break
            case "11":
                literal = "Teleselecci�n"
                break
            case "12":
                literal = "valor absoluto"
                break
            case "20":
                literal = "No Tarificado"
                break
            case "30":
                literal = "Sobreprima"
                break
            case "31":
                literal = "Sobremortalidad"
                break
        }
        return literal
    }

    boolean notNullNotEmpty(String entrada) {
        return (entrada && !entrada.trim().isEmpty())
    }
}
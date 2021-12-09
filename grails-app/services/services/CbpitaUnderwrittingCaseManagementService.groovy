package services

import com.scor.global.ZipUtils
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Operacion
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import com.scortelemed.schemas.cbpita.*
import com.ws.servicios.*
import com.ws.servicios.impl.ExpedienteService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.CbpitaService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty

import org.springframework.web.context.request.RequestContextHolder
import servicios.Documentacion
import servicios.RespuestaCRMInforme

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/cbpita")
@SchemaValidation
@GrailsCxfEndpoint(address = '/cbpita/CbpitaUnderwrittingCaseManagement',
        expose = EndpointType.JAX_WS, properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class CbpitaUnderwrittingCaseManagementService {

    def expedienteService
    def cbpitaService
    def estadisticasService
    def requestService
    def logginService
    def tarificadorService

    @WebResult(name = "caseManagementResponse")
    CbpitaUnderwrittingCaseManagementResponse cbpitaUnderwrittingCaseManagement(
            @WebParam(partName = "CaseManagementRequest", name = "CaseManagementRequest")
                    CbpitaUnderwrittingCaseManagementRequest cbpitaUnderwrittingCaseManagementRequest) {

        CbpitaUnderwrittingCaseManagementResponse resultado = new CbpitaUnderwrittingCaseManagementResponse()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()
        List<WsError> wsErrors = new ArrayList<WsError>()
        def opername = "CbpitaUnderwrittingCaseManagementRequest"
        def requestXML = ""
        Request requestBBDD
        Company company = null
        String message = null
        StatusType status = null
        int code = 0
        List<servicios.Expediente> expedientes = new ArrayList<servicios.Expediente>()

        try {

            company = Company.findByNombre(TipoCompany.CBP_ITALIA.getNombre())

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            logginService.putInfoEndpoint("Endpoint-" + opername, " Peticion de " + company.nombre + " para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

            if (operacion && operacion.activo) {

                if (company.generationAutomatic) {

                    requestXML = cbpitaService.marshall(cbpitaUnderwrittingCaseManagementRequest)
                    requestBBDD = requestService.crear(opername, requestXML)

                    wsErrors = cbpitaService.validarDatosObligatorios(requestBBDD)

                    if (wsErrors != null && wsErrors.size() == 0) {


                        expedientes = cbpitaService.existeExpediente(cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, company.nombre, company.codigoSt, company.ou)

                        if (expedientes != null && expedientes.size() == 0) {

                            message = "Il caso e stato elaborato correttamente"
                            status = StatusType.OK
                            code = 0

                            expedienteService.crearExpediente(requestBBDD, TipoCompany.CBP_ITALIA)

                            logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
                            requestService.insertarRecibido(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)

                            /**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
                            expedienteService.busquedaCrm(requestBBDD, company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, null, null)

                        } else if (expedientes != null && expedientes.size() == 1) {

                            try {

                                com.scortelemed.servicios.Expediente expedienteModificado = new com.scortelemed.servicios.Expediente()
                                expedienteModificado = cbpitaService.componerExpedienteModificado(expedientes.get(0), cbpitaUnderwrittingCaseManagementRequest.getCandidateInformation())
                                com.scortelemed.servicios.RespuestaCRM respuestaModificaExpediente = cbpitaService.modificarExpediente(expedienteModificado, company.ou.toString())

                                if (respuestaModificaExpediente.getErrorCRM() != null) {

                                    message = "Il caso non e stato modificato"
                                    status = StatusType.ERROR
                                    code = 2

                                    logginService.putErrorEndpoint("Modificacion expediente para " + company.nombre, "Modificaición no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + respuestaModificaExpediente.getErrorCRM().getDetalle())
                                    correoUtil.envioEmailErrores("ERROR en modificación de " + company.nombre, "Modificacion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + " se ha procesado pero no se ha modificado en CRM", null)


                                } else {

                                    message = "Il caso e stato modificato correttamente"
                                    status = StatusType.OK
                                    code = 0

                                    requestService.insertarRecibido(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION)
                                    logginService.putInfoMessage("Modificacion realizada para " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)
                                    correoUtil.envioEmailNoTratados("Modificacion webservices " + company.nombre, "Se ha procesado una modificacion para " + company.nombre + "con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber)

                                }

                            } catch (Exception e) {

                                message = "Errore: C'è stata un'eccezione"
                                status = StatusType.ERROR
                                code = 2

                                requestService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION, "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())

                                logginService.putErrorEndpoint("Modificacion expediente para " + company.nombre, "Modificaición no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
                                correoUtil.envioEmailErrores("ERROR en modificación de " + company.nombre, "Modificacion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + " se ha procesado pero no se ha modificado en CRM", null)
                            }
                        } else {

                            message = "Errore: c'è più di un file"
                            status = StatusType.ERROR
                            code = 5

                            requestService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.MODIFICACION, "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: Existe mas de un expediente")

                            logginService.putErrorEndpoint("Modificacion expediente para " + company.nombre, "Modificaición no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: Exiete mas de un expediente")
                            correoUtil.envioEmailErrores("ERROR en modificación de " + company.nombre, "Modificacion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + " se ha procesado pero no se ha modificado en CRM porque existe mas de un expediente", null)

                        }
                    } else {

                        String error = util.detalleError(wsErrors)

                        message = "Errore di convalida: " + error
                        status = StatusType.ERROR
                        code = 8

                        requestService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)
                        logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error de validacion: " + error)

                    }
                }
            } else {

                message = "L'operazione viene disattivata temporaneamente"
                status = StatusType.OK
                code = 1

                logginService.putInfoEndpoint("GestionReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")

                correoUtil.envioEmailErrores("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, "Esta operacion para " + company.nombre + " esta desactivada temporalmente")

            }
        } catch (Exception e) {

            message = "Errore: " + e.getMessage()
            status = StatusType.ERROR
            code = 2

            requestService.insertarError(company, cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())

            logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + cbpitaUnderwrittingCaseManagementRequest.candidateInformation.requestNumber, e)

        } finally {

            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setMessage(message)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)
        resultado.setCode(code)

        return resultado
    }

    @WebResult(name = "CaseManagementResultsResponse")
    CbpitaUnderwrittingCasesResultsResponse cbpitaUnderwrittingCasesResultsResponse(
            @WebParam(partName = "CaseManagementResultsRequest", name = "CaseManagementResultsRequest")
                    CbpitaUnderwrittingCasesResultsRequest cbpitaUnderwrittingCasesResultsRequest) {

        def opername = "cbpitaUnderwrittingCaseManagementResponse"
        def requestXML = ""
        Request requestBBDD
        List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()
        Company company = null
        String message = null
        StatusType status = null
        int code = 0
        ZipUtils zipUtils = new ZipUtils()
        boolean audioEncontrado = false

        CbpitaUnderwrittingCasesResultsResponse resultado = new CbpitaUnderwrittingCasesResultsResponse()

        try {

            company = Company.findByNombre(TipoCompany.CBP_ITALIA.getNombre())

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            logginService.putInfoMessage("Realizando proceso envio de informacion para " + company.nombre + " con fecha " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString())

            if (operacion && operacion.activo) {

                if (company.generationAutomatic) {

                    if (cbpitaUnderwrittingCasesResultsRequest && cbpitaUnderwrittingCasesResultsRequest.dateStart && cbpitaUnderwrittingCasesResultsRequest.dateEnd) {

                        requestXML = cbpitaService.marshall(cbpitaUnderwrittingCasesResultsRequest)
                        requestBBDD = requestService.crear(opername, requestXML)

                        Calendar calendarIni = Calendar.getInstance()
                        Date date = cbpitaUnderwrittingCasesResultsRequest.dateStart.toGregorianCalendar().getTime()
                        calendarIni.setTime(date)
                        calendarIni.add(Calendar.HOUR, -1)
                        SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
                        String fechaIni = sdfr.format(calendarIni.getTime())

                        Calendar calendarFin = Calendar.getInstance()
                        date = cbpitaUnderwrittingCasesResultsRequest.dateEnd.toGregorianCalendar().getTime()
                        calendarFin.setTime(date)
                        calendarFin.add(Calendar.HOUR, -1)
                        String fechaFin = sdfr.format(calendarFin.getTime())

                        expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 1, fechaIni, fechaFin, company.getOu()))
                        expedientes.addAll(expedienteService.obtenerInformeExpedientes(company.codigoSt, null, 2, fechaIni, fechaFin, company.getOu()))


                        requestService.insertarEnvio(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), requestXML.toString())

                        if (expedientes) {

                            expedientes.each { expedientePoliza ->

                                if (!expedientePoliza.getCodigoEstado().toString().equals("ANULADO")) {

                                    List<Documentacion> listaDocumentosAudio = new ArrayList<Documentacion>()
                                    audioEncontrado = false

                                    listaDocumentosAudio = zipUtils.obtenerAudios(expedientePoliza, null)

                                    if (listaDocumentosAudio != null && listaDocumentosAudio.size() > 0) {

                                        for (int i = 0; i < listaDocumentosAudio.size(); i++) {
                                            if (listaDocumentosAudio.get(i).getNombre().contains("TUW realizzata")) {
                                                audioEncontrado = true
                                                break
                                            }
                                        }
                                    }

                                    if (audioEncontrado) {
                                        resultado.getExpediente().add(cbpitaService.rellenaDatosSalidaConsulta(expedientePoliza, "CBP", cbpitaUnderwrittingCasesResultsRequest.dateStart, Conf.findByName("rutaFicheroZip")?.value, Conf.findByName("usuarioZip")?.value, Conf.findByName("passwordZip")?.value))
                                        requestService.insertarEnvio(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), "ST:" + expedientePoliza.getCodigoST() + "#CIA:" + expedientePoliza.getNumSolicitud())
                                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Expediente  con codigo ST: " + expedientePoliza.getCodigoST() + " y codigo cia: " + expedientePoliza.getNumSolicitud() + " para la cia: " + company.nombre + " se ha enviado correctamente")

                                    } else {
                                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han encontardo audio necesarios para completar ZIP para " + company.nombre + " con expediente " + expedientePoliza.getCodigoST())
                                        requestService.insertarError(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), requestXML.toString(), TipoOperacion.CONSULTA, (String)"No se han encontardo audio necesarios para completar ZIP para " + company.nombre + " con expediente " + expedientePoliza.getCodigoST())
                                        correoUtil.envioEmailNoTratados("Error en generacion de zip " + company.nombre, "El expediente : " + expedientePoliza.getCodigoST() + " no se ha enviado porque no tiene el audio requerido")

                                    }

                                } else {
                                    resultado.getExpediente().add(cbpitaService.rellenaDatosSalidaConsulta(expedientePoliza, "CBP", cbpitaUnderwrittingCasesResultsRequest.dateStart, Conf.findByName("rutaFicheroZip")?.value, Conf.findByName("usuarioZip")?.value, Conf.findByName("passwordZip")?.value))
                                    requestService.insertarEnvio(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), "ST:" + expedientePoliza.getCodigoST() + "#CIA:" + expedientePoliza.getNumSolicitud())
                                    logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Expediente  con codigo ST: " + expedientePoliza.getCodigoST() + " y codigo cia: " + expedientePoliza.getNumSolicitud() + " para la cia: " + company.nombre + " se ha enviado correctamente sin zip porque esta ANULADO")
                                }
                            }

                            message = "Risultati restituiti"
                            status = StatusType.OK
                            code = 0
                            logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Peticion realizada correctamente para " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString())
                        } else {

                            message = "Nessun risultato per le date indicate"
                            status = StatusType.OK
                            code = 6
                            logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No hay resultados para " + company.nombre)
                        }
                    } else {

                        message = "Nessuna data  stata inserita per la query"
                        status = StatusType.ERROR
                        code = 7
                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han introducido fechas para la consulta " + company.nombre)
                    }
                }
            } else {

                message = "L'operazione viene disabilitata temporaneamente"
                status = StatusType.OK
                code = 1

                logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
                correoUtil.envioEmailErrores("ResultadoReconocimientoMedico", "Peticion de " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString(), "Esta operacion para " + company.nombre + " esta desactivada temporalmente",null)
            }

        } catch (Exception e) {

            message = "Error: " + e.getMessage()
            status = StatusType.ERROR
            code = 2

            logginService.putErrorEndpoint("ResultadoReconocimientoMedico", "Peticion realizada para " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString() + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("ResultadoReconocimientoMedico", "Peticion realizada para " + company.nombre + " con fecha: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString() + ". Error: " + e.getMessage(),e)

            requestService.insertarError(company, cbpitaUnderwrittingCasesResultsRequest.dateStart.toString().substring(0, 10) + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString().substring(0, 10), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + cbpitaUnderwrittingCasesResultsRequest.dateStart.toString() + "-" + cbpitaUnderwrittingCasesResultsRequest.dateEnd.toString() + ". Error: " + e.getMessage())

        } finally {

            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setMessage(message)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)
        resultado.setCode(code)

        return resultado
    }
}
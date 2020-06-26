package services

import com.scortelemed.Company
import com.scortelemed.Operacion
import com.scortelemed.TipoCompany
import com.scortelemed.schemas.caser.*
import com.ws.servicios.*
import com.ws.servicios.impl.ExpedienteService
import com.ws.servicios.impl.RequestService
import com.ws.servicios.impl.companies.CaserService
import grails.util.Environment
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import servicios.Expediente
import servicios.RespuestaCRM
import servicios.RespuestaCRMInforme

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import java.text.SimpleDateFormat

@WebService(targetNamespace = "http://www.scortelemed.com/schemas/caser")
@SchemaValidation
@GrailsCxfEndpoint(address = '/caser/CaserUnderwrittingCaseManagement',
        expose = EndpointType.JAX_WS, properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class CaserUnderwrittingCaseManagementService {

    def expedienteService = new ExpedienteService()
    @Autowired
    private CaserService caserService
    @Autowired
    private EstadisticasService estadisticasService
    @Autowired
    private RequestService requestService
    @Autowired
    private LogginService logginService
    @Autowired
    private TarificadorService tarificadorService

    @WebResult(name = "GestionReconocimientoMedicoResponse")
    GestionReconocimientoMedicoResponse gestionReconocimientoMedico(
            @WebParam(partName = "GestionReconocimientoMedicoRequest", name = "GestionReconocimientoMedicoRequest")
                    GestionReconocimientoMedicoRequest gestionReconocimientoMedico) {

        GestionReconocimientoMedicoResponse resultado = new GestionReconocimientoMedicoResponse()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        def opername = "CaserResultadoReconocimientoMedicoRequest"
        def requestXML = ""
        def requestBBDD
        String notes = null
        StatusType status = null

        RespuestaCRM respuestaCRM = new RespuestaCRM();

        def company = Company.findByNombre('caser')
        List<servicios.Expediente> expedientes = new ArrayList<servicios.Expediente>()
        logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

        try {

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (Company.findByNombre("caser").generationAutomatic) {

                    requestXML = caserService.marshall("http://www.scortelemed.com/schemas/caser", gestionReconocimientoMedico)
                    requestBBDD = requestService.crear(opername, requestXML)
                    requestBBDD.fecha_procesado = new Date()
                    requestBBDD.save(flush: true)

                    notes = "El caso se ha procesado correctamente"
                    status = StatusType.OK

                    logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + gestionReconocimientoMedico.policyHolderInformation.requestNumber)

//Chequeo si existe el expediente.

                    expedientes = caserService.existeExpediente(gestionReconocimientoMedico.policyHolderInformation.requestNumber, company.nombre, company.codigoSt, company.ou.toString())
                    if(expedientes != null)
                    logginService.putInfoMessage("Exisiten ${expedientes.size()}  con numero de solicitud " + gestionReconocimientoMedico.policyHolderInformation.requestNumber)
                    if (expedientes != null && expedientes.size() == 0) {
                        expedienteService.crearExpediente(requestBBDD, TipoCompany.CASER)
                        caserService.insertarRecibido(company, gestionReconocimientoMedico.policyHolderInformation.requestNumber, requestXML.toString(), "ALTA")
                        /**Llamamos al metodo asincrono que busca en el crm el expediente recien creado
                         *
                         */
                        caserService.busquedaCrm(gestionReconocimientoMedico.policyHolderInformation.policyNumber, company.ou, gestionReconocimientoMedico.policyHolderInformation.requestNumber, opername, company.codigoSt, company.id, requestBBDD, gestionReconocimientoMedico.policyHolderInformation.certificateNumber, company.nombre)
                    } else {
                        logginService.putInfoMessage("Se procede al envio del email para notificar del cambio  Ref: ${gestionReconocimientoMedico.policyHolderInformation.requestNumber}")
                        //el expediente existe, le envio un email a quien este configurado en la compania.
                        caserService.envioEmail(requestBBDD)
                    }
                }
            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK
                logginService.putInfoEndpoint("GestionReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
                correoUtil.envioEmail("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.policyHolderInformation.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            notes = "Error: " + e.getMessage()
            status = StatusType.ERROR

            caserService.insertarError(company, gestionReconocimientoMedico.policyHolderInformation.requestNumber, requestXML.toString(), "ALTA", "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.policyHolderInformation.requestNumber + ". Error: " + e.getMessage())

            logginService.putErrorEndpoint("GestionReconocimientoMedico", "Peticion no realizada de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.policyHolderInformation.requestNumber + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("GestionReconocimientoMedico", "Peticion de " + company.nombre + " con numero de solicitud: " + gestionReconocimientoMedico.policyHolderInformation.requestNumber, e)
        } finally {

            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setNotes(notes)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)

        return resultado
    }

    @WebResult(name = "ResultadoReconocimientoMedicoResponse")
    ResultadoReconocimientoMedicoResponse resultadoReconocimientoMedico(
            @WebParam(partName = "ResultadoReconocimientoMedicoRequest", name = "ResultadoReconocimientoMedicoRequest")
                    ResultadoReconocimientoMedicoRequest resultadoReconocimientoMedico) {

        ResultadoReconocimientoMedicoResponse resultado = new ResultadoReconocimientoMedicoResponse()

        def opername = "CaserResultadoReconocimientoMedicoResponse"
        def requestXML = ""

        String notes = null
        StatusType status = null

        List<RespuestaCRMInforme> expedientes = new ArrayList<RespuestaCRMInforme>();
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        Company company = Company.findByNombre("caser")

        logginService.putInfoMessage("Realizando peticion de informacion de servicio ResultadoReconocimientoMedico para la cia " + company.nombre)

        try {

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (resultadoReconocimientoMedico && resultadoReconocimientoMedico.dateStart && resultadoReconocimientoMedico.dateEnd) {

                    requestXML = caserService.marshall("http://www.scortelemed.com/schemas/caser", resultadoReconocimientoMedico)

                    requestService.crear(opername, requestXML)

                    Date date = resultadoReconocimientoMedico.dateStart.toGregorianCalendar().getTime()
                    SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
                    String fechaIni = sdfr.format(date);
                    date = resultadoReconocimientoMedico.dateEnd.toGregorianCalendar().getTime()
                    String fechaFin = sdfr.format(date);

                    logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Realizando peticion para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString())

                    caserService.insertarEnvio(company, resultadoReconocimientoMedico.dateStart.toString().substring(0, 10) + "-" + resultadoReconocimientoMedico.dateEnd.toString().substring(0, 10), requestXML.toString())

                    for (int i = 1; i < 3; i++) {

                        if (Environment.current.name.equals("production_wildfly")) {
                            expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1061", null, i, fechaIni, fechaFin, "ES"))
                        } else {
                            expedientes.addAll(tarificadorService.obtenerInformeExpedientes("1062", null, i, fechaIni, fechaFin, "ES"))
                        }
                    }

                    if (expedientes) {

                        expedientes.each { expedientePoliza ->

                            resultado.getExpediente().add(caserService.rellenaDatosSalida(expedientePoliza, resultadoReconocimientoMedico.dateStart, logginService))
                        }

                        notes = "Resultados devueltos"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Peticion realizada correctamente para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString())
                    } else {

                        notes = "No hay resultados para las fechas indicadas"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No hay resultados para " + company.nombre)
                    }
                } else {

                    notes = "No se han introducido fechas para la consulta"
                    status = StatusType.ERROR

                    logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han introducido fechas para la consulta " + company.nombre)
                }
            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK

                logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
                correoUtil.envioEmail("ResultadoReconocimientoMedico", "Peticion de " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString() + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            logginService.putErrorEndpoint("ResultadoReconocimientoMedico", "Peticion realizada para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString() + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("ResultadoReconocimientoMedico", "Peticion realizada para " + company.nombre + " con fecha: " + resultadoReconocimientoMedico.dateStart.toString(), e)

            notes = "Error en resultadoReconocimientoMedico: " + e.getMessage()
            status = StatusType.ERROR

            caserService.insertarError(company, resultadoReconocimientoMedico.dateStart.toString().substring(0, 10) + "-" + resultadoReconocimientoMedico.dateEnd.toString().substring(0, 10), requestXML.toString(), "CONSULTA", "Peticion no realizada para solicitud: " + resultadoReconocimientoMedico.dateStart.toString() + "-" + resultadoReconocimientoMedico.dateEnd.toString() + ". Error: " + e.getMessage())
        } finally {

            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setNotes(notes)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)

        return resultado
    }

    @WebResult(name = "ConsultaExpedienteResponse")
    ConsultaExpedienteResponse consultaExpedienteResponse(
            @WebParam(partName = "ConsultaExpedienteRequest", name = "ConsultaExpedienteRequest")
                    ConsultaExpedienteRequest consultaExpediente) {

        ConsultaExpedienteResponse resultado = new ConsultaExpedienteResponse()

        def opername = "CaserConsultaExpedienteResponse"
        def requestXML = ""

        String notes = null
        StatusType status = null

        RespuestaCRM respuestaCRM = new RespuestaCRM()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        Company company = Company.findByNombre("caser")

        logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsultaExpediente para la cia " + company.nombre)

        try {

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (consultaExpediente && consultaExpediente.codExpediente) {

                    requestXML = caserService.marshall("http://www.scortelemed.com/schemas/caser", consultaExpediente)

                    requestService.crear(opername, requestXML)

                    logginService.putInfoEndpoint("ConsultaExpediente", "Realizando peticion para " + company.nombre + " con numero de expiente: " + consultaExpediente.codExpediente)

                    respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consultaExpediente.codExpediente, "ES", company.codigoSt)

                    caserService.insertarEnvio(company, consultaExpediente.codExpediente, requestXML.toString())

                    if (respuestaCRM != null && respuestaCRM.getListaExpedientes() != null) {

                        for (int i = 0; i < respuestaCRM.getListaExpedientes().size(); i++) {

                            Expediente expediente = respuestaCRM.getListaExpedientes().get(i)

                            /**PARA EVITAR CONSULTAR DATOS DE OTRAS COMPA�IAS
                             *
                             */

                            if (expediente.getCandidato().getCompanya().getCodigoST().equals(company.getCodigoSt())) {
                                resultado.getExpedienteConsulta().add(caserService.rellenaDatosSalidaConsulta(expediente, util.fromDateToXmlCalendar(new Date()), logginService))
                            }
                        }
                    }

                    if (resultado.getExpedienteConsulta() != null && resultado.getExpedienteConsulta().size() > 0) {

                        notes = "Resultados devueltos"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ConsultaExpediente", "Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consultaExpediente.codExpediente)
                    } else {

                        resultado.expedienteConsulta = null
                        notes = "No hay resultados para el expediente indicado"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ConsultaExpediente", "No hay resultados para " + company.nombre)
                    }
                } else {

                    notes = "No se ha introducido un expediente"
                    status = StatusType.ERROR

                    logginService.putInfoEndpoint("ConsultaExpediente", "No se han introducido expediente para la consulta de " + company.nombre)
                }
            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK

                logginService.putInfoEndpoint("ConsultaExpediente", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
                correoUtil.envioEmail("ConsultaExpediente", "Peticion de " + company.nombre + " con numero de expiente: " + consultaExpediente.codExpediente + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            logginService.putErrorEndpoint("ConsultaExpediente", "Peticion realizada para " + company.nombre + " con con numero de expiente: " + consultaExpediente.codExpediente + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("ConsultaExpediente", "Peticion realizada para " + company.nombre + " con numero de expiente: " + consultaExpediente.codExpediente, e)

            notes = "Error en ConsultaExpediente: " + e.getMessage()
            status = StatusType.ERROR

            caserService.insertarError(company, consultaExpediente.codExpediente, requestXML.toString(), "CONSULTA", "Peticion no realizada para solicitud: " + consultaExpediente.codExpediente + ". Error: " + e.getMessage())
        } finally {
            //BORRAMOS VARIABLES DE SESION
            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setNotes(notes)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)

        return resultado
    }

    @WebResult(name = "ConsolidacionPolizaResponse")
    ConsolidacionPolizaResponse consolidacionPoliza(
            @WebParam(partName = "ConsolidacionPolizaRequest", name = "ConsolidacionPolizaRequest")
                    ConsolidacionPolizaRequest consolidacionPoliza) {

        def opername = "CaserConsolidacionPolizaResponse"
        def correoUtil = new CorreoUtil()
        def requestXML = ""
        def crearExpedienteService
        def requestBBDD
        def codigoSt

        String notes = null
        StatusType status = null
        int codigo = 0

        Company company = Company.findByNombre("caser")
        RespuestaCRM expediente = new RespuestaCRM();
        TransformacionUtil util = new TransformacionUtil()
        ConsolidacionPolizaResponse resultado = new ConsolidacionPolizaResponse()

        logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsolidacionPoliza para la cia " + company.nombre)

        try {

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                requestXML = caserService.marshall("http://www.scortelemed.com/schemas/caser", consolidacionPoliza)
                requestBBDD = requestService.crear(opername, requestXML)
                requestBBDD.fecha_procesado = new Date()
                requestBBDD.save(flush: true)

                if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() != null && consolidacionPoliza.ciaCode != null && !consolidacionPoliza.ciaCode.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()) {

                    caserService.insertarRecibido(company, consolidacionPoliza.requestNumber, requestXML.toString(), "CONSOLIDACION")

                    expediente = expedienteService.consultaExpedienteNumSolicitud(consolidacionPoliza.requestNumber, "ES", codigoSt)

                    if (expediente != null && expediente.getErrorCRM() == null && expediente.getListaExpedientes() != null && expediente.getListaExpedientes().size() > 0) {

                        if (expediente.getListaExpedientes().size() == 1) {

                            logginService.putInfoEndpoint("ConsolidacionPoliza", "Se procede a la modificacion de " + company.nombre + " con numero de solicitud " + consolidacionPoliza.requestNumber)

                            Expediente eModificado = expediente.getListaExpedientes().get(0)
                            eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

                            RespuestaCRM respuestaCrmExpediente = tarificadorService.modificaExpediente("ES", eModificado, null, null)

                            if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()) {

                                notes = "Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle()
                                status = StatusType.ERROR
                                codigo = 1

                                correoUtil.envioEmail("ConsolidacionPoliza", "Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle(), null)
                                logginService.putInfoEndpoint("ConsolidacionPoliza", "Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())

                                caserService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), "CONSOLIDACION", "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())
                            } else {

                                notes = "El caso se ha procesado correctamente"
                                status = StatusType.OK
                                codigo = 0

                                logginService.putInfoEndpoint("ConsolidacionPoliza", "Peticion realizada correctamente para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber)
                            }
                        } else {

                            notes = "Se ha encontrado mas de un expediente"
                            status = StatusType.OK
                            codigo = 2

                            logginService.putInfoEndpoint("ConsolidacionPoliza", "Peticion no realizada para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + " porque hay mas de un resultado")
                        }
                    } else {

                        notes = "Poliza no encontrada"
                        status = StatusType.OK
                        codigo = 3

                        logginService.putInfoEndpoint("ConsolidacionPoliza", "No hay resultados para " + company.nombre)
                    }
                } else {

                    notes = "Datos obligatorios incompletos (ciaCode, requestNumber, policyNumber)"
                    status = StatusType.ERROR
                    codigo = 3

                    logginService.putInfoEndpoint("ConsolidacionPoliza", "Peticion no realizada para " + company.nombre + " por falta de datos de entrada")
                }
            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK
                codigo = 4

                logginService.putInfoEndpoint("ConsolidacionPoliza", "Esta operacion para " + company.nombre + " esta desactivada temporalmente")
                correoUtil.envioEmail("ConsolidacionPoliza", "Peticion de " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber + ". Esta operacion para " + company.nombre + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            notes = "Error: " + e.getMessage()()
            status = StatusType.ERROR
            codigo = 5

            logginService.putErrorEndpoint("ConsolidacionPoliza", "Peticion realizada para " + company.nombre + " con con numero de expiente: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("ConsolidacionPoliza", "Peticion realizada para " + company.nombre + " con numero de expiente: " + consolidacionPoliza.requestNumber, e)

            caserService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), "CONSOLIDACION", "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
        } finally {

            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
        }

        resultado.setMessage(notes)
        resultado.setDate(util.fromDateToXmlCalendar(new Date()))
        resultado.setStatus(status)
        resultado.setCodigo(codigo)

        return resultado
    }
}
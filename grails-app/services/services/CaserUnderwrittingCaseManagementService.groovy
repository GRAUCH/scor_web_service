package services

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scortelemed.*
import com.scortelemed.schemas.caser.*
import com.velogica.model.dto.ExpedienteCRMDynamicsDTO
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.web.context.request.RequestContextHolder
import servicios.Expediente
import servicios.RespuestaCRM

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

    def expedienteService
    def caserService
    def estadisticasService
    def requestService
    def logginService
    def validationService

    @WebResult(name = "GestionReconocimientoMedicoResponse")
    GestionReconocimientoMedicoResponse gestionReconocimientoMedico(
            @WebParam(partName = "GestionReconocimientoMedicoRequest", name = "GestionReconocimientoMedicoRequest")
                    GestionReconocimientoMedicoRequest gestionReconocimientoMedico) {

        GestionReconocimientoMedicoResponse resultado = new GestionReconocimientoMedicoResponse()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        def opername = "CaserResultadoReconocimientoMedicoRequest"
        def requestXML = ""
        Request requestBBDD
        String notes = null
        StatusType status = null

        def company = Company.findByNombre(TipoCompany.CASER.getNombre())

        List<Expediente> expedientes = new ArrayList<servicios.Expediente>()

        logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)

        try {

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (company.generationAutomatic) {

                    requestXML = caserService.marshall(gestionReconocimientoMedico)
                    requestBBDD = requestService.crear(opername, requestXML)

                    notes = "El caso se ha procesado correctamente"
                    status = StatusType.OK

                    logginService.putInfoMessage("Se procede el alta automatica de " + company.nombre + " con numero de solicitud " + gestionReconocimientoMedico.policyHolderInformation.requestNumber)

                    //Chequeo si existe el expediente.

                    expedientes = caserService.existeExpediente(gestionReconocimientoMedico.policyHolderInformation.requestNumber, company.nombre, company.codigoSt, company.ou)
                    if (expedientes != null)
                        logginService.putInfoMessage("Exisiten ${expedientes.size()}  con numero de solicitud " + gestionReconocimientoMedico.policyHolderInformation.requestNumber)
                    if (expedientes != null && expedientes.size() == 0) {
                        expedienteService.crearExpediente(requestBBDD, TipoCompany.CASER)
                        requestService.insertarRecibido(company, gestionReconocimientoMedico.policyHolderInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA)
                        /**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
                        expedienteService.busquedaCrm(requestBBDD, company, gestionReconocimientoMedico.policyHolderInformation.requestNumber, gestionReconocimientoMedico.policyHolderInformation.certificateNumber, null)
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

            requestService.insertarError(company, gestionReconocimientoMedico.policyHolderInformation.requestNumber, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + gestionReconocimientoMedico.policyHolderInformation.requestNumber + ". Error: " + e.getMessage())

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

    @WebResult(name = "GestionReconocimientoMedicoInfantilResponse")
    GestionReconocimientoMedicoInfantilResponse gestionReconocimientoMedicoInfantil(
            @WebParam(partName = "GestionReconocimientoMedicoInfantilRequest", name = "GestionReconocimientoMedicoInfantilRequest")
                    GestionReconocimientoMedicoInfantilRequest gestionReconocimientoMedicoInfantil) {


        GestionReconocimientoMedicoInfantilResponse resultado = new GestionReconocimientoMedicoInfantilResponse()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        String opername = "CaserResultadoReconocimientoMedicoInfantilRequest"
        String requestXML = ""
        Request requestBBDD
        String notes = null
        StatusType status = null

        def company = Company.findByNombre(TipoCompany.CASER.getNombre())
        List<ExpedienteCRMDynamicsDTO> expedientes = new ArrayList<>()
        List<ExpedienteCRMDynamicsDTO> expedientesCreados = new ArrayList<>()
        logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedicoInfantil para la cia ${company.getNombre()}")

        String numeroSolicitud = gestionReconocimientoMedicoInfantil.getPolicyInformation().getRequestNumber()
        String productCode = gestionReconocimientoMedicoInfantil.getPolicyInformation().getProductCode()

        try {

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (company.generationAutomatic) {

                    requestXML = caserService.marshall(gestionReconocimientoMedicoInfantil)
                    requestBBDD = requestService.crear(opername, requestXML)

                    notes = "El caso se ha procesado correctamente"
                    status = StatusType.OK

                    Collection<Validation> validationCollection = validationService.obtenerValidaciones(company.getCodigoSt(), company.getOu().toString(), gestionReconocimientoMedicoInfantil.getPolicyInformation().getProductCode())

                    caserService.validarCoberturas(gestionReconocimientoMedicoInfantil, company)

                    logginService.putInfoMessage("Se procede el alta automatica de ${company.getNombre()} con numero de solicitud ${numeroSolicitud}")

                    //Chequeo si existen expedientes asociados a ese número de solicitud.
                    expedientes = expedienteService.obtenerExpedientesCRMDynamics(company.getNombre(), company.getOu().getKey(), company.getCodigoSt(), numeroSolicitud, productCode)

                    if (expedientes != null && expedientes.size()) {
                        logginService.putInfoMessage("Ya existen ${expedientes.size()} expedientes con numero de solicitud ${numeroSolicitud}")
                        throw new Exception("Ya existen ${expedientes.size()} expedientes con numero de solicitud ${numeroSolicitud}")
                    }

                    if (expedientes != null && expedientes.size() == 0) {

                        expedienteService.crearExpediente(requestBBDD, TipoCompany.CASER)
                        requestService.insertarRecibido(company, numeroSolicitud, requestXML.toString(), TipoOperacion.ALTA)

                        try {
                            // realizamos la consulta al CRMDynamics para evitar llamadas de consulta al frontal, ya que no funcionan los filtros para obtener expedientes por número de solicitud
                            expedientesCreados = expedienteService.obtenerExpedientesCRMDynamics(company.getNombre(), company.getOu().getKey(), company.getCodigoSt(), numeroSolicitud, productCode)

                            if (expedientesCreados.size() == caserService.obtenerNumeroCandidatos(requestBBDD)) {
                                logginService.putInfoMessage("${opername} - Nueva alta de ${numeroSolicitud} se ha procesado pero no se ha dado de alta en CRM")
                            } else {
                                logginService.putInfoMessage("${opername} - Nueva alta de ${numeroSolicitud} se ha procesado pero no se ha dado de alta en CRM")
                                correoUtil.envioEmailErrores(opername,"Nueva alta de " + numeroSolicitud + " se ha procesado pero no se ha dado de alta en CRM",null)
                                requestService.insertarError(company.getId(), numeroSolicitud, requestBBDD.getRequest(), TipoOperacion.ALTA, "Peticion procesada para solicitud: ${numeroSolicitud}. Error: No encontrada en CRM")
                            }
                        } catch (Exception e) {
                            logginService.putInfoMessage("${opername} - Nueva alta de ${numeroSolicitud} . Error: " + e.getMessage())
                            correoUtil.envioEmailErrores(opername,"Nueva alta de " + numeroSolicitud, e)
                        }

                    } else {
                        logginService.putInfoMessage("Se procede al envio del email para notificar del cambio  Ref: ${numeroSolicitud}")
                        //el expediente existe, le envio un email a quien este configurado en la compania.
                        caserService.envioEmail(requestBBDD)
                    }
                }

            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK
                logginService.putInfoEndpoint("GestionReconocimientoMedicoInfantil", "Esta operacion para ${company.getNombre()} esta desactivada temporalmente")
                correoUtil.envioEmail("GestionReconocimientoMedicoInfantil", "Peticion de " + company.getNombre() + " con numero de solicitud: " + numeroSolicitud + ". Esta operacion para " + company.getNombre() + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            if (e.getMessage() == null) {
                notes = e.getCause().getMessage()
            } else {
                notes = "Error: " + e.getMessage()
            }

            status = StatusType.ERROR

            requestService.insertarError(company, numeroSolicitud, requestXML.toString(), TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + numeroSolicitud + ". Error: " + notes)

            logginService.putErrorEndpoint("GestionReconocimientoMedicoInfantil", "Peticion no realizada de " + company.getNombre() + " con numero de solicitud: " + numeroSolicitud + ". Error: " + notes)
            correoUtil.envioEmailErrores("GestionReconocimientoMedicoInfantil", "Peticion de " + company.getNombre() + " con numero de solicitud: " + numeroSolicitud, e)
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

        List<ExpedienteCRMDynamicsDTO> expedientesParaClasificar
        List<ExpedienteCRMDynamicsDTO> expedientesFinales = new ArrayList<>()

        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        Company company = Company.findByNombre(TipoCompany.CASER.getNombre())

        logginService.putInfoMessage("Realizando petición de información de servicio ResultadoReconocimientoMedico para la compañía " + company.getNombre())

        try {

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.getActivo()) {

                if (resultadoReconocimientoMedico && resultadoReconocimientoMedico.getDateStart() && resultadoReconocimientoMedico.getDateEnd()) {

                    requestXML = caserService.marshall(resultadoReconocimientoMedico)

                    Date date = resultadoReconocimientoMedico.getDateStart().toGregorianCalendar().getTime()
                    SimpleDateFormat sdfr = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
                    String fechaIni = sdfr.format(date)
                    date = resultadoReconocimientoMedico.getDateEnd().toGregorianCalendar().getTime()
                    String fechaFin = sdfr.format(date)

                    logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Realizando peticion para " + company.getNombre() + " con fecha: " + resultadoReconocimientoMedico.getDateStart().toString() + "-" + resultadoReconocimientoMedico.getDateEnd().toString())

                    requestService.insertarEnvio(company, resultadoReconocimientoMedico.getDateStart().toString().substring(0, 10) + "-" + resultadoReconocimientoMedico.getDateEnd().toString().substring(0, 10), requestXML.toString())

                    expedientesParaClasificar = expedienteService.obtenerExpedientesCerradosAnuladosCRMDynamics(company.getNombre(), company.getCodigoSt(), fechaIni, fechaFin, company.getOu().getKey())

                    // Contiene los expedientes agrupados por número de solicitud, de este modo, por cada número de solicitud se obtienen todos los expedientes relacionados.
                    Map<String, List<ExpedienteCRMDynamicsDTO>> mapaExpedientesAgrupados = new HashMap<>()

                    if (expedientesParaClasificar) {

                        expedientesParaClasificar.each { expedienteClasificar ->

                            List<ExpedienteCRMDynamicsDTO> expedientesInfantilesHermanos
                            List<ExpedienteCRMDynamicsDTO> listaExpedientes

                            // si el expediente tiene número de subpóliza, indica que forma parte de una póliza infantil y hay que buscar los expedientes hermanos.
                            if (expedienteClasificar.getNumSubPoliza()) {
                                expedientesInfantilesHermanos = obtenerExpedientesInfantilesHermanosCaser(expedienteClasificar, company)

                                // Si el expediente tiene hermanos y coinciden con los definidos en el número de subpóliza, lo añadimos al mapa de agrupados por
                                // número de solicitud, ya que forman parte de la misma póliza infantil y han de evaluarse.
                                if (expedienteClasificar.getNumSubPoliza().toInteger() == expedientesInfantilesHermanos.size()) {
                                    mapaExpedientesAgrupados.put(expedienteClasificar.getNumSolicitud(), expedientesInfantilesHermanos)
                                }
                            } else {
                                listaExpedientes = new ArrayList<>()
                                listaExpedientes.add(expedienteClasificar)
                                mapaExpedientesAgrupados.put(expedienteClasificar.getNumSolicitud(), listaExpedientes)
                            }
                        }
                    }

                    // Recorremos el mapa comprobando que todos los expedientes agrupados por número de póliza están en estado 1 o 2,
                    // si es así, pasan a la lista de expedientes finales.
                    if (mapaExpedientesAgrupados) {

                        int contadorExpedientesFinalizados = 1

                        mapaExpedientesAgrupados.each { elementoMapa ->

                            contadorExpedientesFinalizados = 0

                            for (expediente in elementoMapa.value) {
                                if (expediente.getCodigoEstadoExpediente() == "10"){
                                    contadorExpedientesFinalizados++
                                }
                            }

                            // Si todos los expedientes de un número de póliza están finalizados, pasan a la lista de expedientesFinales
                            if (contadorExpedientesFinalizados == elementoMapa.getValue().size()) {
                                expedientesFinales.addAll(elementoMapa.getValue())
                            }
                        }
                    }

                    // Una vez todos los expedientes, tanto los normales como los infantiles con todos sus hermanos finalizados, procedemos a obtener sus documentos
                    if (expedientesFinales) {

                        expedientesFinales.each { expedientePoliza ->

                            resultado.getExpediente().add(caserService.rellenaDatosSalida(expedientePoliza.getCodigoExpedienteST(), resultadoReconocimientoMedico.getDateStart()))
                        }

                        notes = "Resultados devueltos"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Peticion realizada correctamente para " + company.getNombre() + " con fecha: " + resultadoReconocimientoMedico.getDateStart().toString() + "-" + resultadoReconocimientoMedico.getDateEnd().toString())
                    } else {

                        notes = "No hay resultados para las fechas indicadas"
                        status = StatusType.OK

                        logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No hay resultados para " + company.getNombre())
                    }
                } else {

                    notes = "No se han introducido fechas para la consulta"
                    status = StatusType.ERROR

                    logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han introducido fechas para la consulta " + company.getNombre())
                }
            } else {

                notes = "Esta operacion esta desactivada temporalmente"
                status = StatusType.OK

                logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Esta operacion para " + company.getNombre() + " esta desactivada temporalmente")
                correoUtil.envioEmail("ResultadoReconocimientoMedico", "Peticion de " + company.getNombre() + " con fecha: " + resultadoReconocimientoMedico.getDateStart().toString() + "-" + resultadoReconocimientoMedico.getDateEnd().toString() + ". Esta operacion para " + company.getNombre() + " esta desactivada temporalmente", 0)
            }
        } catch (Exception e) {

            logginService.putErrorEndpoint("ResultadoReconocimientoMedico", "Peticion realizada para " + company.getNombre() + " con fecha: " + resultadoReconocimientoMedico.getDateStart().toString() + "-" + resultadoReconocimientoMedico.getDateEnd().toString() + ". Error: " + e.getMessage())
            correoUtil.envioEmailErrores("ResultadoReconocimientoMedico", "Peticion realizada para " + company.getNombre() + " con fecha: " + resultadoReconocimientoMedico.getDateStart().toString(), e)

            notes = "Error en resultadoReconocimientoMedico: " + e.getMessage()
            status = StatusType.ERROR

            requestService.insertarError(company, resultadoReconocimientoMedico.getDateStart().toString().substring(0, 10) + "-" + resultadoReconocimientoMedico.getDateEnd().toString().substring(0, 10), requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + resultadoReconocimientoMedico.getDateStart().toString() + "-" + resultadoReconocimientoMedico.getDateEnd().toString() + ". Error: " + e.getMessage())
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

    private List<ExpedienteCRMDynamicsDTO> obtenerExpedientesInfantilesHermanosCaser(ExpedienteCRMDynamicsDTO expedienteClasificar, Company company) {

        List<ExpedienteCRMDynamicsDTO> expedientesHermanos = new ArrayList<>()

        try {
            // Obtenemos los expedientes con el mísmo número de poliza, ya que los expedientes infantiles de caser comparten el número de póliza
            expedientesHermanos = expedienteService.obtenerExpedientesPorNumeroSolicitudYCompanyaCRMDynamics(company.getNombre(), company.getOu().getKey(), company.getCodigoSt(), expedienteClasificar.getNumSolicitud())
        }
        catch (Exception e) {
            logginService.putErrorEndpoint("obtenerExpedientesInfantilesHermanosCaser", "Error al generar la lista de expedientes hermanos. " + e.getMessage())
            throw new Exception(e)
        }

        return expedientesHermanos
    }



    @WebResult(name = "ConsultaExpedienteResponse")
    ConsultaExpedienteResponse consultaExpedienteResponse(
            @WebParam(partName = "ConsultaExpedienteRequest", name = "ConsultaExpedienteRequest")
                    ConsultaExpedienteRequest consultaExpediente) {

        ConsultaExpedienteResponse resultado = new ConsultaExpedienteResponse()

        def opername = "CaserConsultaExpedienteResponse"
        def requestXML = ""
        Request requestBBDD
        String notes = null
        StatusType status = null

        RespuestaCRM respuestaCRM = new RespuestaCRM()
        TransformacionUtil util = new TransformacionUtil()
        CorreoUtil correoUtil = new CorreoUtil()

        Company company = Company.findByNombre(TipoCompany.CASER.getNombre())

        logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsultaExpediente para la cia " + company.nombre)

        try {

            Operacion operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                if (consultaExpediente && consultaExpediente.codExpediente) {

                    requestXML = caserService.marshall(consultaExpediente)
                    requestBBDD = requestService.crear(opername, requestXML)

                    logginService.putInfoEndpoint("ConsultaExpediente", "Realizando peticion para " + company.nombre + " con numero de expiente: " + consultaExpediente.codExpediente)

                    respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consultaExpediente.codExpediente, company.ou, company.codigoSt)

                    requestService.insertarEnvio(company, consultaExpediente.codExpediente, requestXML.toString())

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

            requestService.insertarError(company, consultaExpediente.codExpediente, requestXML.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para solicitud: " + consultaExpediente.codExpediente + ". Error: " + e.getMessage())
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
        Request requestBBDD
        def codigoSt

        String notes = null
        StatusType status = null
        int codigo = 0

        Company company = Company.findByNombre(TipoCompany.CASER.getNombre())
        RespuestaCRM respuestaCRM = new RespuestaCRM()
        TransformacionUtil util = new TransformacionUtil()
        ConsolidacionPolizaResponse resultado = new ConsolidacionPolizaResponse()

        logginService.putInfoMessage("Realizando peticion de informacion de servicio ConsolidacionPoliza para la cia " + company.nombre)

        try {

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {

                requestXML = caserService.marshall(consolidacionPoliza)
                requestBBDD = requestService.crear(opername, requestXML)

                if (consolidacionPoliza.requestNumber != null && !consolidacionPoliza.requestNumber.isEmpty() != null && consolidacionPoliza.ciaCode != null && !consolidacionPoliza.ciaCode.isEmpty() && consolidacionPoliza.policyNumber != null && !consolidacionPoliza.policyNumber.isEmpty()) {

                    requestService.insertarRecibido(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION)

                    respuestaCRM = expedienteService.consultaExpedienteNumSolicitud(consolidacionPoliza.requestNumber, company.ou, codigoSt)

                    if (respuestaCRM != null && respuestaCRM.getErrorCRM() == null && respuestaCRM.getListaExpedientes() != null && respuestaCRM.getListaExpedientes().size() > 0) {

                        if (respuestaCRM.getListaExpedientes().size() == 1) {

                            logginService.putInfoEndpoint("ConsolidacionPoliza", "Se procede a la modificacion de " + company.nombre + " con numero de solicitud " + consolidacionPoliza.requestNumber)

                            Expediente eModificado = respuestaCRM.getListaExpedientes().get(0)
                            eModificado.setNumPoliza(consolidacionPoliza.policyNumber.toString())

                            RespuestaCRM respuestaCrmExpediente = expedienteService.modificaExpediente(company.ou, eModificado, null, null)

                            if (respuestaCrmExpediente.getErrorCRM() != null && respuestaCrmExpediente.getErrorCRM().getDetalle() != null && !respuestaCrmExpediente.getErrorCRM().getDetalle().isEmpty()) {

                                notes = "Error en modificacion poliza. " + respuestaCrmExpediente.getErrorCRM().getDetalle()
                                status = StatusType.ERROR
                                codigo = 1

                                correoUtil.envioEmail("ConsolidacionPoliza", "Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle(), null)
                                logginService.putInfoEndpoint("ConsolidacionPoliza", "Error en la modificacion de " + company.nombre + " con numero de solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())

                                requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + respuestaCrmExpediente.getErrorCRM().getDetalle())
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

            requestService.insertarError(company, consolidacionPoliza.requestNumber, requestXML.toString(), TipoOperacion.CONSOLIDACION, "Peticion no realizada para solicitud: " + consolidacionPoliza.requestNumber + ". Error: " + e.getMessage())
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
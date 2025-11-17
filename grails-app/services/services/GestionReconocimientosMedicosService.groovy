package services

import com.scortelemed.Company
import com.scortelemed.Recibido
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import com.ws.enumeration.StatusType
import com.ws.enumeration.TipoDictamenType
import com.ws.enumeration.TipoDocumentoType
import com.ws.enumeration.TipoGarantiaType
import com.ws.lagunaro.beans.*
import com.ws.servicios.impl.companies.LagunaroService
import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import org.apache.cxf.annotations.SchemaValidation
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty


//import org.jboss.ws.annotation.SchemaValidation
import org.springframework.web.context.request.RequestContextHolder
import servicios.Filtro

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat

//import org.springframework.security.core.context.SecurityContextHolder
@WebService(targetNamespace = "http://www.scortelemed.com/schemas/lagunaro")
@SchemaValidation
@GrailsCxfEndpoint(address = '/lagunaro/GestionReconocimientosMedicos',
        excludes = ['requestService', 'estadisticasService', 'RequestService', 'getRequestService', 'setRequestService'],
        expose = EndpointType.JAX_WS, properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
class GestionReconocimientosMedicosService {

    def estadisticasService
    def requestService
    def logginService
    def expedienteService
    def tarificadorService
    def lagunaroService

    @WebResult(name = "GestionReconocimientoMedicoResponse")
    GestionReconocimientoMedicoResponse GestionReconocimientosMedico(
            @WebParam(partName = "GestionReconocimientoMedicoRequest", name = "GestionReconocimientoMedicoRequest")
                    GestionReconocimientoMedicoRequest gestionReconocimientoMedicoRequest) {

        def opername = "GestionReconocimientoMedicoRequest"
        def correoUtil = new CorreoUtil()
        def requestXML = ""
        def crearExpedienteService
        Request requestBBDD
        def respuestaCrm

        Company company = Company.findByNombre(TipoCompany.LAGUN_ARO.getNombre())
        Filtro filtro = new Filtro()
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
        TransformacionUtil util = new TransformacionUtil()
        GestionReconocimientoMedicoResponse resultado = new GestionReconocimientoMedicoResponse()

        logginService.putInfoEndpoint("Endpoint-" + opername, "Peticion para solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza)

        try {

            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            if (operacion && operacion.activo) {


                if (company.generationAutomatic && gestionReconocimientoMedicoRequest.getDatos_envio().getMovimiento().getNombre().equals("A")) {

                    requestXML = requestService.marshall(gestionReconocimientoMedicoRequest, GestionReconocimientoMedicoRequest.class)
                    requestBBDD = requestService.crear(opername, requestXML)

                    expedienteService.crearExpediente(requestBBDD, TipoCompany.LAGUN_ARO, gestionReconocimientoMedicoRequest.poliza.cod_poliza)
                    requestService.insertarRecibido(company, gestionReconocimientoMedicoRequest.poliza.cod_poliza, requestBBDD.request, TipoOperacion.ALTA)

                    resultado.setMensaje("El caso se ha procesado correctamente")
                    resultado.setFecha(new Date())
                    resultado.setStatusType(StatusType.ok)

                    logginService.putInfoMessage("Se procede el alta automatica de Lagunaro con numero de solicitud " + gestionReconocimientoMedicoRequest.poliza.cod_poliza)

                    /**Llamamos al metodo asincrono que busca en el crm el expediente recien creado*/
                    expedienteService.busquedaCrm(requestBBDD, company, gestionReconocimientoMedicoRequest.poliza.cod_poliza, gestionReconocimientoMedicoRequest.poliza.certificado, null)

                }

                /**CANCELACION
                 *
                 */

                if (company.generationAutomatic && gestionReconocimientoMedicoRequest.getDatos_envio().getMovimiento().getNombre().equals("B")) {

                    def msg = "Ha llegado una cancelacion de Lagunaro con n�mero de solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza + ", certificado: " + gestionReconocimientoMedicoRequest.poliza.certificado + ", numero de suplemento: " + gestionReconocimientoMedicoRequest.poliza.movimiento

                    requestXML = requestService.marshall(gestionReconocimientoMedicoRequest, GestionReconocimientoMedicoRequest.class)
                    requestBBDD = requestService.crear(opername, requestXML)
                    requestService.insertarRecibido(company, gestionReconocimientoMedicoRequest.poliza.cod_poliza, requestBBDD.request, TipoOperacion.CANCELACION)

                    resultado.setMensaje("El baja se ha procesado correctamente")
                    resultado.setFecha(new Date())
                    resultado.setStatusType(StatusType.ok)

                    logginService.putInfoMessage("Ha llegado una cancelacion de Lagunaro con n�mero de referencia: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza)

                    correoUtil.envioEmailNoTratados(opername, msg)

                }


            } else {
                resultado.setStatusType(StatusType.error)
                resultado.setMensaje("La operacion esta desactivada temporalmente.")
                resultado.setFecha(util.fromDateToXmlCalendar(new Date()))
                logginService.putInfoEndpoint("Endpoint-" + opername, "Peticion no realizada para POLIZA: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza)
                correoUtil.envioEmailErrores(opername, "Endpoint-" + opername + ". La operacion esta desactivada temporalmente", null)
            }

        } catch (Exception e) {

            logginService.putErrorEndpoint("Endpoint-" + opername, "Peticion no realizada para solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza + " y certificado: " + gestionReconocimientoMedicoRequest.poliza.certificado + "- Error: " + e)
            correoUtil.envioEmailErrores(opername, "Peticion no realizada para solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza + " y certificado: " + gestionReconocimientoMedicoRequest.poliza.certificado, e)
            resultado.setMensaje("Error: " + "Peticion no realizada para solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza + " y certificado: " + gestionReconocimientoMedicoRequest.poliza.certificado)
            resultado.setFecha(new Date())
            resultado.setStatusType(StatusType.error)
            requestService.insertarError(company, gestionReconocimientoMedicoRequest.poliza.cod_poliza, requestBBDD.request, TipoOperacion.ALTA, "Peticion no realizada para solicitud: " + gestionReconocimientoMedicoRequest.poliza.cod_poliza + " y certificado: " + gestionReconocimientoMedicoRequest.poliza.certificado + ". Error: " + e.getMessage())

        } finally {

            //BORRAMOS VARIABLES DE SESION
            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")

            resultado.setFecha(gestionReconocimientoMedicoRequest.datos_envio.fecha_envio)
        }

        return resultado
    }


    @WebResult(name = "TramitacionReconocimientoMedicoResponse")
    TramitacionReconocimientoMedicoResponse TramitacionReconocimientoMedico(
            @WebParam(partName = "TramitacionReconocimientoMedicoRequest", name = "TramitacionReconocimientoMedicoRequest")
                    TramitacionReconocimientoMedicoRequest tramitacionReconocimientoMedicoRequest) {

        def opername = "TramitacionReconocimientoMedicoRequest"
        def correoUtil = new CorreoUtil()
        def requestXML
        Request requestBBDD
        TramitacionReconocimientoMedicoResponse result = new TramitacionReconocimientoMedicoResponse()
        def timedelay = System.currentTimeMillis()
        logginService.putInfoEndpoint("Endpoint-" + opername, "Peticion para fecha: " + tramitacionReconocimientoMedicoRequest.fecha)
        logginService.putInfoEndpoint("Endpoint-" + opername, "Tiempo inicial: " + timedelay.toString())
        Company company = Company.findByNombre(TipoCompany.LAGUN_ARO.getNombre())

        try {
            def listaExpedientes
            def operacion = estadisticasService.obtenerObjetoOperacion(opername)

            HttpSession session = RequestContextHolder.currentRequestAttributes().getSession()
            session.setAttribute("compa", "lagunaro")
            if (operacion && operacion.activo) {
                requestXML = requestService.marshall(tramitacionReconocimientoMedicoRequest, TramitacionReconocimientoMedicoRequest.class)
                requestBBDD = requestService.crear(opername, requestXML)
                listaExpedientes = tarificadorService.tarificador(tramitacionReconocimientoMedicoRequest.fecha)
                def listPolizaBasicaGroup = []
                listaExpedientes.each {
                    def polizaBasicaGroup = new PolizaBasicaGroup()

                    //AQUI RELLENAMOS RECONOCIMIENTO MEDICO
                    def dictamen = new DictamenType()

                    //AQUI RELLENAMOS LAS GARANTIAS
                    def listGarantias = []
                    it.garantias.each { g ->
                        def garantia = new Garantia()
                        garantia.codigo = g.codigo
                        garantia.nombre_cobertura = obtainNameCover(g.descripcion) //g.descripcion
                        if (g.valoracion.equals("1")) {
                            garantia.valoracion = TipoDictamenType.ONE
                        } else {
                            garantia.valoracion = TipoDictamenType.TWO
                        }
                        listGarantias.add(garantia)
                    }
                    dictamen.garantia = listGarantias

                    //AQUI RELLENAMOS LAS PRUEBAS MEDICAS
                    def listPmedica = []
                    it.pruebasMedicas.each { pm ->
                        def pmedica = new Prueba_Medica()
                        pmedica.codigo_prueba_medica = pm.codigo

                        def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT+2:00"))
                        Date date = formatter.parse(pm.fecha.toString())
                        pmedica.fecha_realizacion = date

                        pmedica.candidato_aporta_pruebas = pm.aportaPruebas
                        pmedica.descripcion_prueba_medica = pm.descripcion
                        listPmedica.add(pmedica)
                    }

                    //AQUI ASIGANMOS TPO RECONOCIMIENTO,LAS PM Y GARANTIAS AL RECONOCIMIENTO MEDICO
                    def reconocimiento = new ReconocimientoMedicoTramitacionType()
                    def r = tarificadorService.obtenerTipoReconocomiento(it)
                    reconocimiento.tipo_reconocimiento_medico = r
                    reconocimiento.dictamenType = dictamen
                    reconocimiento.prueba_medica = listPmedica

                    //AQUI RELLENAMOS LA POLIZA
                    if (it.tipoDocumento == 2) {
                        polizaBasicaGroup.tipo_documento = TipoDocumentoType.TWO
                    } else {
                        polizaBasicaGroup.tipo_documento = TipoDocumentoType.THREE
                    }
                    polizaBasicaGroup.cod_poliza = it.poliza
                    polizaBasicaGroup.certificado = it.certificado
                    polizaBasicaGroup.movimiento = it.movimiento
                    polizaBasicaGroup.nif = it.nif
                    polizaBasicaGroup.reconocimientoMedicoTramitacionType = reconocimiento
                    if (it.zip) {
                        polizaBasicaGroup.zip = it.zip.toString()
                    }
                    requestService.insertarEnvio(company, polizaBasicaGroup.cod_poliza, "Peticion realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha + " poliza " + polizaBasicaGroup.cod_poliza + ", certificado: " + polizaBasicaGroup.certificado + " movimiento " + polizaBasicaGroup.movimiento)

                    listPolizaBasicaGroup.add(polizaBasicaGroup)
                    logginService.putInfoEndpoint("Endpoint-" + opername, "Peticion realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha + " poliza " + polizaBasicaGroup.cod_poliza + ", certificado: " + polizaBasicaGroup.certificado + " mofivimiento " + polizaBasicaGroup.movimiento)
                }

                result.setStatusType(StatusType.ok)
                result.setObservaciones("ok mensaje")
                result.setPolizaBasicaGroup(listPolizaBasicaGroup)

                //				//ENVIAMOS UN CORREO PARA INFORMAR
                //				correoUtil.envioEmail(opername, null, 1)
                session.setAttribute("compa", "")

            } else {
                result.setStatusType(StatusType.error)
                result.setObservaciones("La operacion esta desactivada temporalmente.")
                requestService.insertarError(company, "", tramitacionReconocimientoMedicoRequest.fecha.toString(), TipoOperacion.CONSULTA, "La operacion esta desactivada temporalmente")

                logginService.putInfoEndpoint("Endpoint-" + opername, "Peticion no realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha)

            }
        } catch (Exception e) {
            requestService.insertarError(company, "", tramitacionReconocimientoMedicoRequest.fecha.toString(), TipoOperacion.CONSULTA, "Peticion no realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha.toString() + "- Error: " + e.getMessage())
            logginService.putErrorEndpoint("Endpoint-" + opername, "Peticion no realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha + "- Error: " + e)
            correoUtil.envioEmailErrores("TramitacionReconocimientoMedicoRequest", "Peticion no realizada para fecha: " + tramitacionReconocimientoMedicoRequest.fecha, e)
            result.setStatusType(StatusType.error)
            result.setObservaciones("Error en TramitacionReconocimientoMedicoRequest: " + e)
        } finally {
            //BORRAMOS VARIABLES DE SESION
            def sesion = RequestContextHolder.currentRequestAttributes().getSession()
            sesion.removeAttribute("userEndPoint")
            sesion.removeAttribute("companyST")
            result.setFecha(tramitacionReconocimientoMedicoRequest.fecha)
            logginService.putInfoEndpoint("Endpoint-" + opername, "Paso por el finally")
        }
        def timeFinal = System.currentTimeMillis() - timedelay
        logginService.putInfoEndpoint("Endpoint-" + opername, "Tiempo TOTAL: " + timeFinal.toString())
        return result
    }


    private obtainNameCover(String cover) {
        def result

        if (cover.equals("Fallecimiento")) {
            result = TipoGarantiaType.Fallecimiento
        }

        if (cover.equals("Fallecimiento accidente")) {
            result = TipoGarantiaType.Fallecimiento_accidente
        }
        if (cover.equals("Fallecimiento por accidente")) {
            result = TipoGarantiaType.Fallecimiento_accidente
        }

        if (cover.equals("Enfermedades graves")) {
            result = TipoGarantiaType.Enfermedades_graves
        }

        if (cover.equals("Invalidez permanente absoluta")) {
            result = TipoGarantiaType.Invalidez_permanente_absoluta
        }
        if (cover.equals("Incapacidad 30")) {
            result = TipoGarantiaType.Incapacidad_30
        }

        if (cover.equals("Incapacidad 30-90")) {
            result = TipoGarantiaType.Incapacidad_30_90
        }

        if (cover.equals("Incapacidad 90")) {
            result = TipoGarantiaType.Incapacidad_90
        }

        if (cover.equals("Dependencia")) {
            result = TipoGarantiaType.Dependencia
        }

        return result
    }
}
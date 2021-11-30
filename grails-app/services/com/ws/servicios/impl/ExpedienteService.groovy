package com.ws.servicios.impl

import com.scor.global.ExceptionUtils
import com.scor.global.WSException
import com.scor.srpfileinbound.RootElement
import com.scortelemed.Company
import com.scortelemed.Conf
import com.scortelemed.Request
import com.scortelemed.TipoCompany
import com.scortelemed.TipoOperacion
import com.velogica.model.dto.CandidatoCRMDynamicsDTO
import com.velogica.model.dto.CoberturasCRMDynamicsDTO
import com.velogica.model.dto.ExpedienteCRMDynamicsDTO
import com.ws.enumeration.UnidadOrganizativa
import com.ws.servicios.ServiceFactory
import com.ws.servicios.ICompanyService
import com.ws.servicios.IExpedienteService
import com.ws.servicios.impl.companies.CaserService
import grails.transaction.Transactional
import grails.util.Environment
import grails.util.Holders
import hwsol.webservices.CorreoUtil
import org.hibernate.transform.Transformers
import servicios.ClaveFiltro
import servicios.Expediente
import servicios.Filtro
import servicios.RespuestaCRM
import servicios.Usuario

import javax.xml.ws.BindingProvider
import java.text.SimpleDateFormat

import static grails.async.Promises.task

@Transactional
class ExpedienteService implements IExpedienteService {

    def logginService = Holders.grailsApplication.mainContext.getBean("logginService")
    def requestService = Holders.grailsApplication.mainContext.getBean("requestService")

    def grailsApplication = Holders.getGrailsApplication()
    CorreoUtil correoUtil = new CorreoUtil()
    ICompanyService companyService

    def sessionFactory_CRMDynamics

    def consultaExpediente(UnidadOrganizativa pais, Filtro filtro) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.consultaExpediente(obtenerUsuarioFrontal(pais),filtro)
            return salida
        } catch (Exception e) {
            logginService.putError("consultaExpediente","No se ha podido consultar el expediente " + e)
            correoUtil.envioEmailErrores("consultaExpediente", "No se ha podido consultar el expediente ", e)
            return null
        }
    }

    def consultaExpedienteCodigoST(String codigoST, UnidadOrganizativa pais) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(pais, filtro)
    }

    def consultaExpedienteNumSolicitud(String requestNumber, UnidadOrganizativa pais, String codigoST) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.CLIENTE)
        filtro.setValor(codigoST)
        Filtro filtroRelacionado = new Filtro()
        filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
        filtroRelacionado.setValor(requestNumber)
        filtro.setFiltroRelacionado(filtroRelacionado)
        return consultaExpediente(pais, filtro)
    }

    def consultaExpedienteNumPoliza(String requestNumber, UnidadOrganizativa pais) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.NUM_POLIZA)
        filtro.setValor(requestNumber)
        return consultaExpediente(pais, filtro)
    }

    /**
     * Obtener Expedientes en formato Informe (Más reducido)
     *
     * @param companya (Código ST de la compañía)
     * @param servicioScor (Código ST de servicio SCOR)
     * @param estado (0 abiertos, 1 Cerrados, 2 Anulados, 3 Cerrados y Anulados)
     * @param fechaIni
     * @param fechaFin
     * @param pais
     * @return
     */
    def obtenerInformeExpedientes(String companya, String servicioScor, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais) {
        try {
            //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientes(obtenerUsuarioFrontal(pais), companya, servicioScor, estado, fechaIni, fechaFin)
            return salida.listaExpedientesInforme
        } catch (Throwable e) {
            logginService.putError("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientes", "No se ha podido obtener el informe de expediente", e)
            return null
        }
    }

    /**
     * Obtener Expedientes de Siniestro en formato Informe (Más reducido)
     *
     * @param companya (Código ST de la compañía)
     * @param producto (Código ST del producto)
     * @param estado (0 abiertos, 1 Cerrados, 2 Anulados, 3 Cerrados y Anulados)
     * @param fechaIni
     * @param fechaFin
     * @param pais
     * @return
     */
    def obtenerInformeExpedientesSiniestros(String companya, String producto, Integer estado, String fechaIni, String fechaFin, UnidadOrganizativa pais) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.informeExpedientesSiniestros(obtenerUsuarioFrontal(pais), companya, producto, estado, fechaIni, fechaFin)
            return salida.listaExpedientes
        } catch (Exception e) {
            logginService.putError("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente " + e)
            correoUtil.envioEmailErrores("obtenerInformeExpedientesSiniestros", "No se ha podido obtener el informe de expediente ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return null
        }
    }

    def informeExpedientePorFiltro(Filtro filtro, UnidadOrganizativa pais) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY,Conf.findByName("frontal.wsdl")?.value)
            def salida=grailsApplication.mainContext.soapClientAlptis.informeExpedientesPorFiltro(obtenerUsuarioFrontal(pais),filtro)
            return salida.listaExpedientesInforme
        } catch (Exception e) {
            logginService.putError("informeExpedientePorFiltro de ama","No se ha podido obtener el informe de expediente : " + e)
            return null
        }
    }

    List<Expediente> informeExpedienteCodigoST(String codigoST, UnidadOrganizativa pais) {
        Filtro filtro = new Filtro()
        filtro.setClave(ClaveFiltro.EXPEDIENTE)
        filtro.setValor(codigoST)
        return consultaExpediente(pais, filtro)?.getListaExpedientes()
    }

    def modificaExpediente(UnidadOrganizativa pais, Expediente expediente, def servicioScorList, def paqueteScorList) {
        try {
            def ctx = grailsApplication.mainContext
            def bean = ctx.getBean("soapClientAlptis")
            bean.getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("frontal.wsdl")?.value)
            def salida = grailsApplication.mainContext.soapClientAlptis.modificaExpediente(obtenerUsuarioFrontal(pais), expediente, servicioScorList, paqueteScorList)
            return salida
        } catch (Exception e) {
            logginService.putError("modificaExpediente", "No se ha podido ejecutar la operacion de modificacion : " + e)
            correoUtil.envioEmailErrores("modificaExpediente", "No se ha podido ejecutar la operacion de modificacion ->   Error msg: "  + e.getMessage()+"    Causa : " + e.getCause())
            return null
        }
    }

    /**
     * MÉTODO PARA LA CREACIÓN DE EXPEDIENTES
     *
     * @param Request req
     * @param TipoCompany comp
     * @return
     */
    boolean crearExpediente(Request req, TipoCompany comp) {
        try {

            CaserService companyService

            if (comp == TipoCompany.CASER) {
                companyService = new CaserService()
            }

            if (companyService?.esCaserInfantil(req)){

                    // NECESITAMOS RELENTIZAR EL ENVÍO DE PETICIONES SOAP AL FRONTAL, YA QUE SI LLEGAN DEMASIADO RÁPIDO SE REPITEN LOS CÓDIGOS ST,
                    // POR LO QUE USAREMOS UN DELAY DE 30 SEGUNDOS (TIEMPO QUE TARDA BPEL EN CREAR UN EXPEDIENTE EN CRM ES DE 25 SEGUNDOS),
                    // EN EL CASO DEL ÚLTIMO EXPEDIENTE, SÓLO ESPERAMOS 5 SEGUNDOS

                    for (int i = 0; i < companyService.obtenerNumeroCandidatos(req); i++) {
                        logginService.putInfoMessage("Creando expediente " + (i+1) + " de " + companyService.obtenerNumeroCandidatos(req))
                        realizarPeticionSOAP(req, comp, crearExpedienteCaserInfantil(req, i))

                        if (i == companyService.obtenerNumeroCandidatos(req) - 1) {
                            Thread.currentThread().sleep(5000)
                        } else {
                            Thread.currentThread().sleep(25000)
                        }
                    }

                    return true

            } else {
                return realizarPeticionSOAP(req, comp, crearExpedienteBPM(req, comp))
            }

        } catch (Exception e) {
            throw new WSException(this.getClass(), "crearExpediente", ExceptionUtils.composeMessage(null, e))
        }
    }

    /**
     * METODO QUE REALIZA LA LLAMADA AL BPEL DE ORACLE PARA CREAR EXPEDIENTES
     * @param req REQUEST EN XML
     * @param comp ENUMERADO QUE IDENTIFICA LA COMPANY
     * @param payload ESTRUCTURA DE DATOS QUE SE ENVIA AL BPEL COMO CUERPO DEL SERVICIO SOAP, SE CONSTRUYE CON METODOS crearExpediente(req, comp)
     *                O crearExpedienteCaserInfantil(req, iteradorCandidatosIndex)
     * @return true SE DEVUELVE SIEMPRE TRUE PORQUE EL SERVICIO SOAP NO DEVUELVE ESTADO DE LA LLAMADA REALIZADA
     *              SE COMPRUEBA POSTERIORMENTE SI EL EXPEDIENTE EXISTE EN CRM PARA VALIDAR QUE SE HA PERSISTIDO CORRECTAMENTE
     */
    private boolean realizarPeticionSOAP(Request req, TipoCompany comp, RootElement payload) {
        //SOBREESCRIBIMOS LA URL A LA QUE TIENE QUE LLAMAR EL WSDL
        def ctx = grailsApplication.mainContext
        def bean = ctx.getBean("soapClientCrearOrabpel")
        bean.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, Conf.findByName("orabpelCreacion.wsdl")?.value)
        def salida = grailsApplication.mainContext.soapClientCrearOrabpel.initiate(payload)
        return true
    }

    private def crearExpedienteBPM(Request req, TipoCompany comp) {
        companyService = ServiceFactory.getCompanyImpl(comp)
        def listadoFinal = []
        RootElement payload = new RootElement()
        try {
            String codigoSt = companyService.getCodigoStManual(req)
            listadoFinal.add(buildCabecera(req, codigoSt))
            listadoFinal.add(companyService.buildDatos(req, codigoSt))
            listadoFinal.add(buildPie(null))
            payload.cabeceraOrDATOSOrPIE = listadoFinal
        } catch (Exception e) {
           logginService.putError("crearExpedienteBPM","Error en el metodo crearExpedienteBPM: " + e)
            //TODO: EXCEPTION: SE CAPTURA PERO NO SE PROPAGA
        }
        return payload
    }

    private def crearExpedienteCaserInfantil(Request req, int iteradorCandidatosIndex) {
        companyService = new CaserService()
        def listadoFinal = []
        RootElement payload = new RootElement()
        try {
            String codigoSt = companyService.getCodigoStManual(req)
            listadoFinal.add(buildCabecera(req, codigoSt))
            listadoFinal.add(companyService.buildDatosCaserInfantil(req, codigoSt, iteradorCandidatosIndex))
            listadoFinal.add(buildPie(null))
            payload.cabeceraOrDATOSOrPIE = listadoFinal
        } catch (Exception e) {
            logginService.putError("crearExpedienteCaserInfantil","Error en el metodo crearExpedienteCaserInfantil: " + e)
            throw new Exception(e)
        }
        return payload
    }

    private def buildCabecera(Request req, String codigoSt) {
        def formato = new SimpleDateFormat("yyyyMMdd")
        RootElement.CABECERA cabecera = new RootElement.CABECERA()
        if(codigoSt) {
            cabecera.setCodigoCia(codigoSt)
        } else {
            cabecera.setCodigoCia(req.company.codigoSt)
        }
        cabecera.setContadorSecuencial("1")
        cabecera.setFechaGeneracion(formato.format(new Date()))
        cabecera.setFiller("")
        cabecera.setTipoFichero("1")
        return cabecera
    }

    private def buildPie(Request req) {
        RootElement.PIE pie = new RootElement.PIE()
        pie.setFiller("")
        pie.setNumFilasFichero(100)
        pie.setNumRegistros(1)
        return pie
    }

    UnidadOrganizativa obtenerUnidadOrganizativa(TipoCompany tipo) {
        Company company = Company.findByNombre(tipo.nombre)
        return company?.ou
    }

    Usuario obtenerUsuarioFrontal(UnidadOrganizativa unidadOrganizativa) {
        def usuario = new Usuario()

        switch(unidadOrganizativa) {
            case UnidadOrganizativa.ES:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "7Q%NN!v5"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "ES"
                    usuario.usuario = "usuarioBPM"
                } else {
                    usuario.clave = "Wcbhjfod!"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "ES"
                    usuario.usuario = "gcaballero-es"
                }
                break
            case UnidadOrganizativa.IT:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "sc5t4!QAZ123"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "IT"
                    usuario.usuario = "adminitalia"
                } else {
                    usuario.clave = "P@ssword"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "IT"
                    usuario.usuario = "admin-ITA"
                }
                break
            case UnidadOrganizativa.FR:
                if (Environment.current.name.equals("production_wildfly")) {
                    usuario.clave = "5#6GAkXP456"
                    usuario.dominio = "SCOR-TELEMED"
                    usuario.unidadOrganizativa = "FR"
                    usuario.usuario = "webclientesFR"
                } else {
                    usuario.clave = "5#6GAkXP456"
                    usuario.dominio = "scor.local"
                    usuario.unidadOrganizativa = "FR"
                    usuario.usuario = "webclientesFR"
                }
                break
            default:
                break
        }
        return usuario
    }

    /**
     * Búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud y producto de una compañía
     *
     *     codigoExpedienteST: códigoST del expediente
     *
     *     codigoCompanyiaST: códigoST de la compañia cliente
     *
     *     numSolicitud: número de solicitud del expediente
     *
     * @param companyNombre
     * @param companyCodigoPais
     * @param companyCodigoSt
     * @param numeroSolicitud
     * @param productoIdName
     * @return
     */
    List<ExpedienteCRMDynamicsDTO> obtenerExpedientesCRMDynamics(String companyNombre, String companyCodigoPais, String companyCodigoSt, String numSolicitud, String productoIdName) {

        logginService.putInfoMessage("Buscando si existen expedientes con número de solicitud " + numSolicitud + " y producto " + productoIdName + " para " + companyNombre)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :companyCodigoPais, :companyCodigoSt, :numSolicitud y :productoIdName
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT CAST(A.Scor_name as VARCHAR(5000)) as codigoExpedienteST, CAST(E.scor_codigoST as VARCHAR(5000)) as codigoCompanyiaST, CAST(A.scor_nsolicitud_compania as VARCHAR(5000)) as numSolicitud FROM Scor_expediente AS A, Contact AS C, Scor_codBusinessUnit AS D, Scor_clienteExtensionBase as E WHERE A.DeletionStateCode = \'0\' and (C.contactId = A.scor_candidatoid) and (A.owningbusinessunit = D.scor_unidaddenegocioid) and (C.scor_candidatosid = e.Scor_clienteID) and d.scor_codigopais=:companyCodigoPais and E.scor_codigoST=:companyCodigoSt and A.scor_nsolicitud_compania=:numSolicitud and A.scor_productoidName=:productoIdName order by a.Scor_name'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso ExpedienteCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(ExpedienteCRMDynamicsDTO.class))

                // seteamos el parámetro 'companyCodigoPais' de la query con el parámetro de entrada del método 'companyCodigoPais'
                setString("companyCodigoPais", companyCodigoPais)

                // seteamos el parámetro 'companyCodigoSt' de la query con el parámetro de entrada del método 'companyCodigoSt'
                setString("companyCodigoSt", companyCodigoSt)

                // seteamos el parámetro 'numSolicitud' de la query con el parámetro de entrada del método 'numSolicitud'
                setString("numSolicitud", numSolicitud)

                // seteamos el parámetro 'productoIdName' de la query con el parámetro de entrada del método 'productoIdName'
                setString("productoIdName", productoIdName)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerExpedientesCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud " + numSolicitud + " y producto " + productoIdName + " para " + companyNombre + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerExpedientesCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud " + numSolicitud + " y producto " + productoIdName + " para " + companyNombre + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerExpedientesCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    /**
     * Búsqueda directa a la BBDD del CRM Dynamics para obtener los expedientes en estado 10 (CERRADOS) y 11 (ANULADOS) de la compañía CASER de España
     *
     *     codigoExpedienteST: códigoST del expediente
     *
     *     codigoCompanyiaST: códigoST de la compañia cliente
     *
     *     numSolicitud: número de solicitud del expediente
     *
     *     numSubPoliza: número de subpóliza (en este caso indica cuántos expedientes hay en esa póliza)
     *
     *     codigoEstadoExpediente: código de estado del expediente, en nuestro caso verificaremos que está CERRADO
     *
     * @param companyNombre
     * @param companyCodigoSt
     * @param fechaIni
     * @param fechaFin
     * @param companyCodigoPais
     * @return
     */
    List<ExpedienteCRMDynamicsDTO> obtenerExpedientesCerradosAnuladosCRMDynamics(String companyNombre, String companyCodigoSt, String fechaIni, String fechaFin, String companyCodigoPais) {

        logginService.putInfoMessage("Buscando expedientes cerrados y anulados entre " + fechaIni + " y " + fechaFin + " para " + companyNombre + "de " + companyCodigoPais)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :companyCodigoSt, :fechaIni, :fechaFin y :companyCodigoPais
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT CAST(A.Scor_name AS VARCHAR(5000)) as codigoExpedienteST, CAST(E.scor_codigoST AS VARCHAR(5000)) as codigoCompanyiaST, CAST(A.scor_nsolicitud_compania AS VARCHAR(5000)) as numSolicitud, CAST(a.Scor_nsubpoliza AS VARCHAR(5000)) as numSubPoliza, CAST(a.scor_estado AS VARCHAR(5000)) as codigoEstadoExpediente FROM Scor_expediente AS A, Contact AS C, Scor_codBusinessUnit AS D, Scor_clienteExtensionBase as E WHERE A.DeletionStateCode = \'0\' and (C.contactId = A.scor_candidatoid) and (A.owningbusinessunit = D.scor_unidaddenegocioid) and (C.scor_candidatosid = e.Scor_clienteID) and d.scor_codigopais=:companyCodigoPais and E.scor_codigoST=:companyCodigoSt and A.scor_estado in(\'10\',\'11\') and a.scor_fechadeestado >:fechaIni and a.scor_fechadeestado<=:fechaFin order by a.Scor_name'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso ExpedienteCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(ExpedienteCRMDynamicsDTO.class))

                // seteamos el parámetro 'companyCodigoSt' de la query con el parámetro de entrada del método 'companyCodigoSt'
                setString("companyCodigoSt", companyCodigoSt)

                // seteamos el parámetro 'fechaIni' de la query con el parámetro de entrada del método 'fechaIni'
                setString("fechaIni", fechaIni)

                // seteamos el parámetro 'fechaFin' de la query con el parámetro de entrada del método 'fechaFin'
                setString("fechaFin", fechaFin)

                // seteamos el parámetro 'companyCodigoPais' de la query con el parámetro de entrada del método 'companyCodigoPais'
                setString("companyCodigoPais", companyCodigoPais)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerExpedientesCerradosAnuladosCRMDynamics", "Error en búsqueda directa a la BBDD del CRM Dynamics para obtener los expedientes en estado 10 (CERRADOS) y 11 (ANULADOS) de la compañía " + companyNombre + " de " + companyCodigoPais + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerExpedientesCerradosAnuladosCRMDynamics", "Error en búsqueda directa a la BBDD del CRM Dynamics para obtener los expedientes en estado 10 (CERRADOS) y 11 (ANULADOS) de la compañía " + companyNombre + " de " + companyCodigoPais + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerExpedientesCerradosAnuladosCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    /**
     * Búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud para una compañía
     *
     *     codigoExpedienteST: códigoST del expediente
     *
     *     codigoCompanyiaST: códigoST de la compañia cliente
     *
     *     numSolicitud: número de solicitud del expediente
     *
     *     numSubPoliza: número de subpóliza (en este caso indica cuántos expedientes hay en esa póliza)
     *
     *     codigoProductoCompanya: código del producto para la compañía CASER
     *
     *     codigoEstadoExpediente: código de estado del expediente, en nuestro caso verificaremos que está CERRADO
     *
     * @param companyNombre
     * @param companyCodigoPais
     * @param companyCodigoSt
     * @param numeroSolicitud
     * @return
     */
    List<ExpedienteCRMDynamicsDTO> obtenerExpedientesPorNumeroSolicitudYCompanyaCRMDynamics(String companyNombre, String companyCodigoPais, String companyCodigoSt, String numSolicitud) {

        logginService.putInfoMessage("Buscando si existen expedientes con número de solicitud " + numSolicitud + " para " + companyNombre)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :companyCodigoSt, :numSolicitud y :companyCodigoPais
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT CAST(A.Scor_name AS VARCHAR(5000)) as codigoExpedienteST, CAST(E.scor_codigoST AS VARCHAR(5000)) as codigoCompanyiaST, CAST(A.scor_nsolicitud_compania AS VARCHAR(5000)) as numSolicitud, CAST(a.Scor_nsubpoliza AS VARCHAR(5000)) as numSubPoliza, CAST(A.scor_productoidName AS VARCHAR(5000)) as codigoProductoCompanya, CAST(a.scor_estado AS VARCHAR(5000)) as codigoEstadoExpediente FROM Scor_expediente AS A, Contact AS C, Scor_codBusinessUnit AS D, Scor_clienteExtensionBase as E WHERE A.DeletionStateCode = \'0\' and (C.contactId = A.scor_candidatoid) and (A.owningbusinessunit = D.scor_unidaddenegocioid) and (C.scor_candidatosid = e.Scor_clienteID) and d.scor_codigopais=:companyCodigoPais and E.scor_codigoST=:companyCodigoSt and A.scor_nsolicitud_compania = :numSolicitud order by a.Scor_name'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso ExpedienteCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(ExpedienteCRMDynamicsDTO.class))

                // seteamos el parámetro 'companyCodigoSt' de la query con el parámetro de entrada del método 'companyCodigoSt'
                setString("companyCodigoSt", companyCodigoSt)

                // seteamos el parámetro 'numSolicitud' de la query con el parámetro de entrada del método 'numSolicitud'
                setString("numSolicitud", numSolicitud)

                // seteamos el parámetro 'companyCodigoPais' de la query con el parámetro de entrada del método 'companyCodigoPais'
                setString("companyCodigoPais", companyCodigoPais)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerExpedientesPorNumeroSolicitudYCompanyaCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud " + numSolicitud + " para la compañía " + companyNombre + " de " + companyCodigoPais + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerExpedientesPorNumeroSolicitudYCompanyaCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los expedientes con el mismo número de solicitud " + numSolicitud + " para la compañía " + companyNombre + " de " + companyCodigoPais + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerExpedientesPorNumeroSolicitudYCompanyaCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    /**
     * Búsqueda directa a la BBDD del CRM Dynamics para obtener los siguientes datos de un expediente:
     *
     *     codigoExpedienteST: códigoST del expediente
     *
     *     numSolicitud: número de solicitud del expediente
     *
     *     codigoEstadoExpediente: código de estado del expediente
     *
     *     codigoProductoCompanya: código del producto para la compañía CASER de ese expediente
     *
     *     numPoliza: número de póliza del expediente
     *
     *     numCertificado: número de certificado del expediente
     *
     *     observacionesTarificacion: las observaciones de la tarificación del expediente
     *
     *     nodoAlfresco: la ubicación en el gestor documental de los documentos asociados al expediente
     *
     * @param codigoExpedienteST
     * @return
     */
    ExpedienteCRMDynamicsDTO obtenerDatosExpedienteCRMDynamics(String codigoExpedienteST) {

        logginService.putInfoMessage("Recuperando información del expediente " + codigoExpedienteST)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :codigoExpedienteST
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT  CAST(A.Scor_name as VARCHAR(5000)) as codigoExpedienteST, CAST(A.scor_nsolicitud_compania as VARCHAR(5000)) as numSolicitud, CAST(A.scor_estado as VARCHAR(5000)) as codigoEstadoExpediente, CAST(A.scor_productoidName as VARCHAR(5000)) as codigoProductoCompanya, CAST(A.Scor_npoliza as VARCHAR(5000)) as numPoliza, CAST(A.Scor_ncertificado as VARCHAR(5000)) as numCertificado, CAST(A.Scor_observacionestarificacion as VARCHAR(5000)) as observacionesTarificacion, CAST(A.Scor_idgestordocumental as VARCHAR(5000)) as nodoAlfresco FROM Scor_expediente AS A, Contact AS C, Scor_codBusinessUnit AS D, Scor_clienteExtensionBase as E WHERE A.DeletionStateCode = \'0\' and (C.contactId = A.scor_candidatoid) and (A.owningbusinessunit = D.scor_unidaddenegocioid) and (C.scor_candidatosid = E.Scor_clienteID) and A.Scor_name=:codigoExpedienteST order by a.Scor_name'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso ExpedienteCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(ExpedienteCRMDynamicsDTO.class))

                // seteamos el parámetro 'codigoExpedienteST' de la query con el parámetro de entrada del método 'codigoExpedienteST'
                setString("codigoExpedienteST", codigoExpedienteST)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerDatosExpedienteCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los siguientes datos del expediente " + codigoExpedienteST + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerDatosExpedienteCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los siguientes datos del expediente " + codigoExpedienteST + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerDatosExpedienteCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    /**
     * Búsqueda directa a la BD del CRM Dynamics para obtener los siguientes datos del candidato del expediente
     *
     *     numeroDocumento: el DNI del candidato.
     *
     *     telefono1: número de teléfono 1 del candidato
     *
     *     tipoTelefono1: tipo de número de teléfono 1 del candidato (1 es MÓVIL, 2 es FIJO)
     *
     *     telefono2: número de teléfono 2 del candidato
     *
     *     tipoTelefono2: tipo de número de teléfono 2 del candidato (1 es MÓVIL, 2 es FIJO)
     *
     *     telefono3: número de teléfono 3 del candidato
     *
     *     tipoTelefono3: tipo de número de teléfono 3 del candidato (1 es MÓVIL, 2 es FIJO)
     *
     *     dirección: dirección de vivienda del candidato
     *
     *     codPostal: codigo postal de la vivienda del candidato
     *
     *     localidad: localidad de la vivienda del candidato
     *
     *     provincia: provincia de la vivienda del candidato
     *
     * @param codigoExpedienteST
     * @return
     */
    CandidatoCRMDynamicsDTO obtenerDatosCandidatoExpedienteCRMDynamics(String codigoExpedienteST) {

        logginService.putInfoMessage("Recuperando información del candidato del expediente " + codigoExpedienteST)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :codigoExpedienteST
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT CAST(scor_dnipasaporte AS VARCHAR(5000)) as numeroDocumento, CAST(telephone1 AS VARCHAR(5000)) as telefonol, Scor_Tipodetelefono as tipoTelefonol, CAST(telephone2 AS VARCHAR(5000)) as telefono2, Scor_tipodetelefono2 as tipoTelefono2, CAST(telephone3 AS VARCHAR(5000)) as telefono3, Scor_Tipodetelefono3 as tipoTelefono3, CAST(address1_line1 AS VARCHAR(5000)) as direccion, CAST(address1_postalcode AS VARCHAR(5000)) as codPostal, CAST(address1_city AS VARCHAR(5000)) as localidad, CAST(address1_stateorprovince AS VARCHAR(5000)) as provincia FROM Scor_expediente AS A, Contact AS C, Scor_codBusinessUnit AS D, Scor_clienteExtensionBase as E WHERE A.DeletionStateCode = \'0\' and (C.contactId = A.scor_candidatoid) and (A.owningbusinessunit = D.scor_unidaddenegocioid) and (C.scor_candidatosid = e.Scor_clienteID) and A.Scor_name=:codigoExpedienteST order by a.Scor_name'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso CandidatoCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(CandidatoCRMDynamicsDTO.class))

                // seteamos el parámetro 'codigoExpedienteST' de la query con el parámetro de entrada del método 'codigoExpedienteST'
                setString("codigoExpedienteST", codigoExpedienteST)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerDatosCandidatoExpedienteCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los siguientes datos del candidato del expediente " + codigoExpedienteST + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerDatosCandidatoExpedienteCRMDynamics", "Error en búsqueda directa a la BD del CRM Dynamics para obtener los siguientes datos del candidato del expediente " + codigoExpedienteST + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerDatosCandidatoExpedienteCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    /**
     * Búsqueda directa a la BBDD del CRM Dynamics para obtener las coberturas de un expediente
     *
     *      nombreCobertura: nombre de la cobertura
     *
     *      codigoCobertura: código de la cobertura
     *
     *      capitalCobertura: capital de la cobertura.
     *
     *      resultadoCobertura: resultado de la cobertura
     *
     *      codigoResultadoCobertura: codigo del resultado de la cobertura
     *
     *      valoracionPrima: valoración de la prima de la cobertura
     *
     *      valoracionCapital: valoración del capital de la cobertura
     *
     *      exclusiones: exclusiones de la cobertura
     *
     * @param codigoExpedienteST
     * @return
     */
    List<CoberturasCRMDynamicsDTO> obtenerCoberturasExpedienteCRMDynamics(String codigoExpedienteST) {

        logginService.putInfoMessage("Recuperando información de las coberturas del expediente " + codigoExpedienteST)

        final List<ExpedienteCRMDynamicsDTO> expedientes

        try {
            // Cogemos la sesión de Hibernate para el datasource del CRMDynamics
            final sessionCRMDynamics = sessionFactory_CRMDynamics.currentSession

            // Creamos la queryString con el parámetro :codigoExpedienteST
            // IMPORTANTE: HAY QUE REALIZAR EL CAST( XXX AS VARCHAR(5000)) PORQUE EN SQLSERVER SE PRODUCE UN ERROR DE DIALECT AL INTENTAR CREAR LA LISTA DE RESULTADOS
            final String query = 'SELECT CAST(C.scor_name as VARCHAR(5000)) as nombreCobertura, CAST(C.Scor_Codigo as VARCHAR(5000)) as codigoCobertura, CAST(a.Scor_capital as VARCHAR(5000)) as capitalCobertura, CAST(D.Scor_name as VARCHAR(5000)) AS resultadoCobertura, CAST(D.Scor_referencia as VARCHAR(5000)) AS codigoResultadoCobertura, CAST(a.Scor_valoracionprima as VARCHAR(5000)) as valoracionPrima, CAST(a.Scor_valoracioncapital as VARCHAR(5000)) as valoracionCapital, CAST(a.Scor_exclusiones as VARCHAR(5000)) as exclusiones FROM Scor_coberturadelexpedienteExtensionBase AS a INNER JOIN Scor_expediente AS b ON a.Scor_expedienteId = b.Scor_expedienteId INNER JOIN Scor_coberturaExtensionBase AS C ON a.Scor_coberturaId = C.Scor_coberturaId LEFT OUTER JOIN Scor_resultadocoberturaExtensionBase AS D ON a.Scor_resultadocoberturaid = D.Scor_resultadocoberturaId WHERE b.DeletionStateCode = \'0\' and b.Scor_name=:codigoExpedienteST'

            // Creamos la query nativa SQL
            final sqlQuery = sessionCRMDynamics.createSQLQuery(query)

            // Usamos el método with() de GORM para invocar métodos sobre el objeto sqlQuery
            expedientes = sqlQuery.with {
                // Definimos un Transformer para que convierta los campos alias a la clase POJO destino, en este caso CoberturasCRMDynamicsDTO
                setResultTransformer(Transformers.aliasToBean(CoberturasCRMDynamicsDTO.class))

                // seteamos el parámetro 'codigoExpedienteST' de la query con el parámetro de entrada del método 'codigoExpedienteST'
                setString("codigoExpedienteST", codigoExpedienteST)

                // Ejecutamos la query y obtenemos los resultados
                list()
            }

        } catch (Exception e) {
            logginService.putError(this.class.getName() + ".obtenerCoberturasExpedienteCRMDynamics", "Error en búsqueda directa a la BBDD del CRM Dynamics para obtener las coberturas del expediente " + codigoExpedienteST + ": " + ExceptionUtils.composeMessage(null, e))
            correoUtil.envioEmailErrores(this.class.getName() + ".obtenerCoberturasExpedienteCRMDynamics", "Error en búsqueda directa a la BBDD del CRM Dynamics para obtener las coberturas del expediente " + codigoExpedienteST + ": ", e)
            throw new WSException(this.getClass(), this.class.getName() + ".obtenerCoberturasExpedienteCRMDynamics", e.getMessage())
        }
        return expedientes
    }

    def busquedaCrm(Request requestBBDD, Company company, String requestNumber, String certificateNumber, String policyNumber) {
        task {

            String opername = "ExpedienteService BusquedaCrm"
            String logExpediente = getLogExpediente(policyNumber, requestNumber, certificateNumber, company.codigoSt)
            logginService.putInfoMessage(opername+" - Buscando en CRM solicitud de "+logExpediente)
            RespuestaCRM respuestaCrm
            int limite = 1
            boolean encontrado = false
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
            Filtro filtro = getFiltradoCRM(policyNumber, requestNumber, certificateNumber, company.codigoSt)
            Thread.currentThread().sleep(25000)

            try {
                while( !encontrado && limite < 20) {
                    Thread.currentThread().sleep(5000)
                    respuestaCrm = consultaExpediente(company.ou, filtro)
                    if (respuestaCrm != null && respuestaCrm.getListaExpedientes() != null && respuestaCrm.getListaExpedientes().size() > 0) {
                        for (Expediente exp: respuestaCrm.getListaExpedientes()) {
                            logginService.putInfoMessage(opername+" - Expediente encontrado: " + exp.getCodigoST() + " para " + company.nombre)

                            String fechaCreacion = format.format(new Date())
                            if (exp.getCandidato() != null && exp.getCandidato().getCompanya() != null && exp.getCandidato().getCompanya().getCodigoST().equals(company.codigoSt) &&
                                    fechaCreacion != null && fechaCreacion.equals(exp.getFechaApertura())){
                                /**Alta procesada correctamente*/
                                encontrado = true
                                logginService.putInfoMessage(opername+" - Nueva alta automatica de "+logExpediente+" procesada correctamente. Verificado tras "+limite+" intentos")
                            }
                        }
                    }
                    limite++
                }

                /**Alta procesada pero no se ha encontrado en CRM.*/
                if (limite == 10) {
                    logginService.putInfoMessage(opername+" - Nueva alta de "+logExpediente+" se ha procesado pero no se ha dado de alta en CRM")
                    correoUtil.envioEmailErrores(opername,"Nueva alta de "+logExpediente+" se ha procesado pero no se ha dado de alta en CRM",null)
                    requestService.insertarError(company.id, requestNumber, requestBBDD.request, TipoOperacion.ALTA, "Peticion procesada para solicitud: "+logExpediente+". Error: No encontrada en CRM")
                }
            } catch (Exception e) {
                logginService.putInfoMessage(opername+" - Nueva alta de "+logExpediente+". Error: " + e.getMessage())
                correoUtil.envioEmailErrores(opername,"Nueva alta de "+logExpediente,e)
            }
        }
    }

    private String getLogExpediente(String numPoliza, String numSolicitud, String numCertificado, String codigoStCompany) {
        String logExpediente = codigoStCompany
        if(numPoliza) {
            logExpediente = logExpediente.concat(" con numPoliza: " + numPoliza)
        }
        if(numSolicitud) {
            logExpediente = logExpediente.concat(" con numSolicitud: " + numSolicitud)
        }
        if(numCertificado) {
            logExpediente = logExpediente.concat(" con numCertificado: " + numCertificado)
        }
        return logExpediente
    }

    private Filtro getFiltradoCRM(String numPoliza, String numSolicitud, String numCertificado, String codigoStCompany) {
        Filtro filtro = new Filtro()
        if(numPoliza) {
            filtro.setClave(ClaveFiltro.NUM_POLIZA)
            filtro.setValor(numPoliza)
        } else if(codigoStCompany && numSolicitud) {
            filtro.setClave(ClaveFiltro.CLIENTE)
            filtro.setValor(codigoStCompany)
            Filtro filtroRelacionado = new Filtro()
            filtroRelacionado.setClave(ClaveFiltro.NUM_SOLICITUD)
            filtroRelacionado.setValor(numSolicitud)
            if(numCertificado) {
                Filtro filtroRelacionado2 = new Filtro()
                filtroRelacionado2.setClave(ClaveFiltro.NUM_CERTIFICADO)
                filtroRelacionado2.setValor(numCertificado)
                filtroRelacionado.setFiltroRelacionado(filtroRelacionado2)
            }
            filtro.setFiltroRelacionado(filtroRelacionado)
        }
        return filtro
    }
}

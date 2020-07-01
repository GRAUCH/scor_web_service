package com.ws.servicios.impl

import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.Request
import com.scortelemed.Error
import com.scortelemed.TipoOperacion
import com.ws.servicios.EstadisticasService
import com.ws.servicios.IRequestService
import com.ws.servicios.LogginService
import org.springframework.web.context.request.RequestContextHolder

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.QName
import javax.xml.transform.stream.StreamSource
import java.text.Normalizer
import java.util.regex.Pattern

import grails.transaction.Transactional

@Transactional
class RequestService implements IRequestService {

    def logginService = new LogginService()
    def estadisticasService = new EstadisticasService()

    def getBBDDRequest(Request requestInstance, String opername, String schema, Class<?> myObjectClass) {
        def object = unmarshall(requestInstance.getRequest(), myObjectClass)
        def requestXML
        if (schema) {
            requestXML = marshall(schema, object, myObjectClass)
        } else {
            requestXML = marshall(object, myObjectClass)
        }
        def requestBBDD = crear(opername, requestXML)
        logginService.putInfoMessage("Se ha procesado una request manualmente para: " + requestInstance.company)
        return requestBBDD
    }

    def unmarshall(String entrada, Class<?> myObjectClass) {
        def object = null
        if (entrada != null && !entrada.trim().isEmpty()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(myObjectClass)
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller()
            StringReader reader = new StringReader(entrada.trim())
            JAXBElement<?> root = jaxbUnmarshaller.unmarshal(new StreamSource(reader), myObjectClass)
            object = root.getValue()
        }
        return object
    }

    def marshall(def objetoRelleno, Class<?> clase) {
        StringWriter writer = new StringWriter()
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clase)
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller()
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            jaxbMarshaller.marshal(objetoRelleno, writer)
        } finally {
            writer.close()
        }
        return writer
    }

    def marshall(String nameSpace, def objetoRelleno, Class<?> clase) {
        StringWriter writer = new StringWriter()
        String result
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clase)
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller()
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            QName qName = new QName(nameSpace, clase.simpleName)
            def root = new JAXBElement<?>(qName, clase, objetoRelleno)
            jaxbMarshaller.marshal(root, writer)
            result = writer.toString()
        } finally {
            writer.close()
        }
        return result
    }

    def crear(message, requestXML) {
        def result
        def operacion = estadisticasService.obtenerObjetoOperacion(message)
        def sesion = RequestContextHolder.currentRequestAttributes().getSession()
        def idCompania = sesion.companyST

        if (idCompania) {
            Company.withNewSession {
                def compania = Company.findByCodigoSt(idCompania)

                if (compania && operacion.activo) {
                    result = new Request()
                    result.operacion = operacion
                    requestXML = convertirHexCode(requestXML.toString())
                    result.request = requestXML
                    result.company = compania
                    //agrego un caso nuevo CaserResultadoReconocimientoMedicoRequest para cuando se procesa desde el boton.
//                    if (operacion.clave == "TramitacionReconocimientoMedicoRequest") {
                    result.fecha_procesado = new Date()
//                    }
                    if (!result.save(flush: true)) {
                        result.errors.each {
                            logginService.putErrorMessage("No se ha podido guardar los request: " + it)
                        }
                    } else {
                        StringBuilder sbInfo = new StringBuilder("Se ha guardado la request correctamente para la operacion [");
                        sbInfo.append(message).append("] para la compania: ").append(compania.nombre);
                        logginService.putInfoMessage(sbInfo.toString())
                    }

                    def salida = estadisticasService.crear(result, operacion, requestXML.toString())
                    //AHORA MISMO ESTO NO HACE NADA ASI QUE SE COMENTA
//					avisosService.enviarAvisos(salida.operacion, salida.claveProceso)
                } else {
                    throw new Exception("No se ha encontrado la cia o la operacion activa.")
                }
            }
        } else {
            throw new Exception("No se ha encontrado el id de compania.")
        }

        return result
    }

    void insertarRecibido(Company company, String identificador, String info, TipoOperacion operacion) {
        insertarRecibido(company.id.toString(), identificador, info, operacion)
    }

    void insertarRecibido(String companyId, String identificador, String info, TipoOperacion operacion) {
        Recibido recibido = new Recibido()
        recibido.setFecha(new Date())
        recibido.setCia(companyId)
        recibido.setIdentificador(identificador)
        recibido.setInfo(info)
        recibido.setOperacion(operacion.name())
        recibido.save(flush: true)
    }

    void insertarError(Company company, String identificador, String info, TipoOperacion operacion, String detalleError) {
        insertarError(company.id.toString(), identificador, info, operacion, detalleError)
    }

    void insertarError(String companyId, String identificador, String info, TipoOperacion operacion, String detalleError) {
        Error error = new Error()
        error.setFecha(new Date())
        error.setCia(companyId)
        error.setIdentificador(identificador)
        error.setInfo(info)
        error.setOperacion(operacion.name())
        error.setError(detalleError)
        error.save(flush: true)
    }

    void insertarEnvio (Company company, String identificador, String info) {
        insertarEnvio (company.id.toString(), identificador, info)
    }

    void insertarEnvio (String companyId, String identificador, String info) {
        Envio envio = new Envio()
        envio.setFecha(new Date())
        envio.setCia(companyId)
        envio.setIdentificador(identificador)
        envio.setInfo(info)
        envio.save(flush:true)
    }
    /**
     * No utilizado

    def unmarshall = { objetoRelleno, clase ->

        StringWriter writer = new StringWriter();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            jaxbMarshaller.marshal(objetoRelleno, writer);
        } finally {
            writer.close();
        }

        return writer
    }
     */

    def convertir(cadena) {
        String original = "áàäéèëíìïóòöúùüñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";

        for (int i = 0; i < original.length(); i++) {
            cadena = cadena.replace(original.charAt(i), ascii.charAt(i));
        }

        return cadena
    }

    def convertirHexCode(cadena) {
        def result = cadena.replace("á", "&#xE1;").replace("à", "&#xe0;").replace("ä", "&#xE4;").replace("Á", "&#xC1;").replace("À", "&#xC0;").replace("Ä", "&#xC4;").replace("â", "&#xE2;").replace("Â", "&#xC2;")
        result = result.replace("é", "&#xe9;").replace("è", "&#xe8;").replace("ë", "&#xEB;").replace("É", "&#xC9;").replace("È", "&#xC8;").replace("Ë", "&#xCB;").replace("ê", "&#xEA;").replace("Ê", "&#xCA;")
        result = result.replace("í", "&#xED;").replace("ì", "&#xEC;").replace("ï", "&#xEF;").replace("Í", "&#xCD;").replace("Ì", "&#xCC;").replace("Ï", "&#xCF;").replace("î", "&#xEE;").replace("Î", "&#xCE;")
        result = result.replace("ó", "&#xF3;").replace("ò", "&#xF2;").replace("ö", "&#xF6;").replace("Ó", "&#xD3;").replace("Ò", "&#xD2;").replace("Ö", "&#xD6;").replace("ô", "&#xf4;").replace("Ô", "&#xD4;")
        result = result.replace("ú", "&#xFA;").replace("ù", "&#xF9;").replace("ü", "&#xFC;").replace("Ú", "&#xDA;").replace("Ù", "&#xD9;").replace("Ü", "&#xDC;").replace("û", "&#xfb;").replace("Û", "&#xDB;")
        result = result.replace("ç", "&#xE7;").replace("Ç", "&#xC7;")

        return result
    }

    def convertirAscii = { cadena ->
        String normalized = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        // Nos quedamos ÃƒÂºnicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\P{ASCII}+");
        return pattern.matcher(normalized).replaceAll("").toUpperCase();
    }
}
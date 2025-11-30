package com.ws.servicios

import com.ws.enumeration.UnidadOrganizativa
import com.zoho.services.Candidato
import com.zoho.services.Frontal
import com.zoho.services.FrontalService
import com.zoho.services.Usuario
import grails.transaction.Transactional
import grails.util.Environment
import hwsol.webservices.TransformacionUtil

import javax.xml.ws.BindingProvider
import javax.xml.ws.WebServiceException

import org.apache.log4j.Logger;

@Transactional
class ServiceZohoService {

    static final Logger log = Logger.getLogger(ServiceZohoService)

    /**
     * INSTANCIAMOS EL FRONTAL
     *
     * @throws Exception
     */
    def instanciarFrontalZoho(String frontalPortAddress) throws Exception {
        Frontal frontal = null

        try {
            // Crear URL del WSDL
            URL url = new URL("${frontalPortAddress}?WSDL")
            log.info("Instanciando frontal " + url)
            // Instanciar el servicio
            FrontalService fs = new FrontalService(url)
            frontal = fs.frontalPort

            if (!frontal) {
                log.error("No se pudo instanciar el port Frontal")
                throw new Exception("No se pudo instanciar el port Frontal")
            }

            // Cambiar dinámicamente el endpoint
            Map<String, Object> context = ((BindingProvider) frontal).requestContext
            context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, frontalPortAddress)

            // Opcional: configurar timeouts
            context.put("com.sun.xml.internal.ws.connect.timeout", 5000)   // tiempo de conexión
            context.put("com.sun.xml.internal.ws.request.timeout", 60000)  // tiempo de respuesta

            log.info("Frontal SOAP inicializado correctamente en: $frontalPortAddress")

        } catch (MalformedURLException e) {
            log.error("URL del WSDL inválida -> $frontalPortAddress")
            throw e
        } catch (WebServiceException e) {
            log.error("No se pudo conectar con el servicio SOAP -> ${e.message}")
            throw e
        } catch (Exception e) {
            log.error("Error inesperado al instanciar Frontal -> ${e.message}")
            throw e
        }

        return frontal
    }

    def obtenerUsuarioFrontal(UnidadOrganizativa unidadOrganizativa) {
        def usuario = new Usuario()

        switch (unidadOrganizativa) {
            case UnidadOrganizativa.ES:
                if (Environment.current.name.equals("production")) {
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
                if (Environment.current.name.equals("production")) {
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
                if (Environment.current.name.equals("production")) {
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

    def devolverDatos(String dato) {
        return dato?.trim() ?: ""
    }

    def devolverTelefonoMovil(Candidato c) {

        return [
                c?.telefono1,
                c?.telefono2,
                c?.telefono3,
                c?.telefono4
        ].find { it?.trim() } ?: ""
    }

    def devolverTelefono1(Candidato c) {
        return c?.telefono1?.trim() ?: ""
    }

    def devolverTelefono2(Candidato c) {
        return c?.telefono2?.trim() ?: ""
    }
}

package com.ws.servicios

import com.scor.global.ZipUtils
import com.ws.enumeration.UnidadOrganizativa
import com.zoho.services.Candidato
import com.zoho.services.Documentacion
import com.zoho.services.ExpedienteInforme
import com.zoho.services.Frontal
import com.zoho.services.FrontalService
import com.zoho.services.Usuario
import com.zoho.services.TipoDocumentacion
import com.zoho.services.TipoEstadoTUW
import grails.transaction.Transactional
import grails.util.Environment
import org.apache.commons.io.FileUtils

import javax.xml.ws.BindingProvider
import javax.xml.ws.WebServiceException

import org.apache.log4j.Logger

import java.text.DateFormat
import java.text.SimpleDateFormat;

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

    def obtenerAudios(ExpedienteInforme expediente) {

        log.info("Obteniendo audios para expediente: " + expediente.getCodigoST())

        List<Documentacion> documentos = new ArrayList<Documentacion>();

        if (expediente.getListaDocumentosInforme() != null && expediente.getListaDocumentosInforme().size() > 0) {

            for (int i = 0; i < expediente.getListaDocumentosInforme().size(); i++) {

                Documentacion documento = expediente.getListaDocumentosInforme().get(i);

                try {

                    if (documento.getFechaBorrado() == null && documento.getTipoDocumentacion() == TipoDocumentacion.AUDIO
                            && expediente.getEstadoTUW() == TipoEstadoTUW.FINALIZADA) {
                        documentos.add(documento);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

        }

        log.info("Fin obteniendo audios para expediente: " + expediente.getCodigoST())

        return documentos;
    }

    def obtenerPdfsTUW(ExpedienteInforme expediente) {

        log.info("Obteniendo pdfs para expediente: " + expediente.getCodigoST())

        List<Documentacion> documentos = new ArrayList<Documentacion>();

        if (expediente.getListaDocumentosInforme() != null && expediente.getListaDocumentosInforme().size() > 0) {

            for (int i = 0; i < expediente.getListaDocumentosInforme().size(); i++) {

                Documentacion documento = expediente.getListaDocumentosInforme().get(i);

                try {

                    if (documento.getFechaBorrado() == null && documento.getTipoDocumentacion() == TipoDocumentacion.TELESELECCION
                            && documento.getNombre().equals("Relazione di Teleselezione PDF") && expediente.getEstadoTUW() == TipoEstadoTUW.FINALIZADA) {
                        documentos.add(documento);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

        }

        log.info("Fin obteniendo pdfs para expediente: " + expediente.getCodigoST())

        return documentos;

    }

    def descargarPdf(String path, String nombreFichero,
                               ExpedienteInforme expediente, TipoDocumentacion tipoDocumentacion, String zipPath, final String user, final String password) throws Exception {

        log.info("Descargando pdf desde path: " + path + " para expediente: " + expediente.getCodigoST());

        URL url = new URL(path);
        File ficheroLocal = null;
        String nombre = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /**
         * • YYYY.MM.DD-PIT-Survey_APPLICATION NUMBER.pdf
         **/

        String application_number = expediente.getNumSolicitud() != null ? expediente.getNumSolicitud() : "";
        String fecha = dateFormat.format(new Date());
        String anio = fecha.substring(0, 4);
        String mes = fecha.substring(5, 7);
        String dia = fecha.substring(8, 10);

        nombre = anio + "." + mes + "." + dia + "-PIT-Survey_" + application_number + ".pdf";

        ficheroLocal = new File(zipPath + application_number + "/" + nombre);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password.toCharArray());
            }
        });

        FileUtils.copyURLToFile(url, ficheroLocal);

        log.info("Fin descargando pdf desde path: " + path + " para expediente: " + expediente.getCodigoST());

        return ficheroLocal.getPath();

    }


    def descargarAudios(String path, String nombreFichero, ExpedienteInforme expediente, TipoDocumentacion tipoDocumentacion, int ordinal, int numeroAudios, String zipPath, final String user, final String password)
            throws Exception {

        log.info("Descargando audio desde path: " + path + " para expediente: " + expediente.getCodigoST());

        URL url = new URL(path);
        File ficheroLocal = null;
        String nombre = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        /**
         * YYYY.MM.DD-PIT-Call_APPLICATION NUMBER.mp3
         **/

        String application_number = expediente.getNumSolicitud() != null ? expediente.getNumSolicitud() : "";
        String fecha = dateFormat.format(new Date());
        String anio = fecha.substring(0, 4);
        String mes = fecha.substring(5, 7);
        String dia = fecha.substring(8, 10);

        if (numeroAudios > 1) {
            nombre = anio + "." + mes + "." + dia + "-Call_" + application_number + "_" + ordinal + ".mp3";
        } else {
            nombre = anio + "." + mes + "." + dia + "-Call_" + application_number + ".mp3";
        }

        ficheroLocal = new File(zipPath + application_number + "/" + nombre);


        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password.toCharArray());
            }
        });

        FileUtils.copyURLToFile(url, ficheroLocal);

        log.info("Fin descargando audio desde path: " + path + " para expediente: " + expediente.getCodigoST());


        return ficheroLocal.getPath();


    }


    def generarZips(ExpedienteInforme expediente, String codigoStCia, String fecha, String zipPath, String user, String password) throws Exception {

        log.info("Generando zips para expediente: " + expediente.getCodigoST())

        ZipUtils zipUtils = new ZipUtils();
        zipUtils.startSession()
        List<Documentacion> listaDocumentosPdf = new ArrayList<Documentacion>();
        List<Documentacion> listaDocumentosAudios = new ArrayList<Documentacion>();
        String rutaDocumento = null;


        if (expediente != null) {

            if (expediente.getProducto().getCodigoProductoCompanya().equals(codigoStCia)) {

                listaDocumentosPdf = obtenerPdfsTUW(expediente, fecha);
                listaDocumentosAudios = obtenerAudios(expediente, fecha);

                /**
                 * Procesamos los pdf
                 *
                 */

                for (int k = 0; k < listaDocumentosPdf.size(); k++) {

                    if (listaDocumentosPdf.get(k).getUrlAlfresco() != null) {

                        rutaDocumento = descargarPdf(listaDocumentosPdf.get(k).getUrlAlfresco(), listaDocumentosPdf.get(k).getNombre(), expediente, listaDocumentosPdf.get(k).getTipoDocumentacion(), zipPath, user, password);
                        log.info("Intentando descargar documento: " + listaDocumentosPdf.get(k).getNombre());
                        if (rutaDocumento != null) {
                            log.info("TUW: Se ha generado el documento " + listaDocumentosPdf.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " en la ruta " + rutaDocumento);
                        } else {
                            log.info("TUW: No se ha generado el documento " + listaDocumentosPdf.get(k).getNombre());
                        }
                    }
                }

                /**
                 * Procesamos los audios
                 *
                 */
                for (int k = 0; k < listaDocumentosAudios.size(); k++) {

                    if (listaDocumentosAudios.get(k).getUrlAlfresco() != null) {

                        rutaDocumento = descargarAudios(listaDocumentosAudios.get(k).getUrlAlfresco(), listaDocumentosAudios.get(k).getNombre(), expediente, listaDocumentosAudios.get(k).getTipoDocumentacion(), k,
                                listaDocumentosAudios.size(), zipPath, user, password);

                        log.info("Intentando descargar documento: " + listaDocumentosAudios.get(k).getNombre());

                        if (rutaDocumento != null) {

                            log.info("TUW: Se ha generado el documento " + listaDocumentosAudios.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " en la ruta " + rutaDocumento);
                        } else {
                            log.info("TUW: No se ha podido generar el documento " + listaDocumentosAudios.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " porque el nodo de alfresco es nulo");
                        }
                    }
                }

                if (listaDocumentosPdf.size() > 0) {
                    return zipUtils.generarZip(expediente.getNumSolicitud(), expediente.getCandidato().getApellidos(), zipPath);
                }

            }
        }

        return null;
    }

}

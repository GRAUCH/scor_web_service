package com.scor.global;

import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.apache.commons.io.FileUtils;
import servicios.Documentacion;
import servicios.TipoDocumentacion;
import servicios.TipoEstadoTUW;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {


    public void startSession() {

        WebServiceFactory.setEndpointAddress("http://172.17.0.32:8080/alfresco/api");
        try {
            AuthenticationUtils.startSession("generico", "pR8x5hqD");
        } catch (AuthenticationFault e) {
            e.printStackTrace();
        }

    }

    public void eraseFiles(servicios.ExpedienteInforme expediente, String zipPath) {

        File dir = new File(zipPath + expediente.getNumSolicitud());

        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            file.delete();
        }

        dir.delete();

        File zip = new File(zipPath + expediente.getCandidato().getApellidos() + "_" + expediente.getNumSolicitud() + ".zip");
        zip.delete();
    }

    public byte[] generarZips(servicios.ExpedienteInforme expediente, String codigoStCia, String fecha, String zipPath, String user, String password) throws Exception {
        startSession();
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
                        System.out.println("Intentando descargar documento: " + listaDocumentosPdf.get(k).getNombre());
                        if (rutaDocumento != null) {
                            System.out.println("TUW: Se ha generado el documento " + listaDocumentosPdf.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " en la ruta " + rutaDocumento);
                        } else {
                            System.out.println("TUW: No se ha generado el documento " + listaDocumentosPdf.get(k).getNombre());
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

                        System.out.println("Intentando descargar documento: " + listaDocumentosAudios.get(k).getNombre());

                        if (rutaDocumento != null) {

                            System.out.println("TUW: Se ha generado el documento " + listaDocumentosAudios.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " en la ruta " + rutaDocumento);
                        } else {
                            System.out.println("TUW: No se ha podido generar el documento " + listaDocumentosAudios.get(k).getNombre() + " para expediente "
                                    + expediente.getCodigoST() + " porque el nodo de alfresco es nulo");
                        }
                    }
                }

                if (listaDocumentosPdf.size() > 0) {
                    return generarZip(expediente.getNumSolicitud(), expediente.getCandidato().getApellidos(), zipPath);
                }

            }
        }


        return null;
    }

    public static List<Documentacion> obtenerPdfsTUW(servicios.ExpedienteInforme expediente, String fecha) {

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

        return documentos;

    }

    public static List<Documentacion> obtenerAudios(servicios.ExpedienteInforme expediente, String fecha) {

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

        return documentos;

    }

    public byte[] generarZip(String applicationNumber, String apellidos, String zipFile)

    {
        byte[] arrayOfByte = new byte[1024];
        ZipOutputStream zos = null;

        try {

            FileOutputStream fos = new FileOutputStream(zipFile + apellidos + "_" + applicationNumber + ".zip");
            zos = new ZipOutputStream(fos);

            /**Recorremos los ficheros de la carpeta
             *
             */
            String[] files = getFiles(zipFile + applicationNumber);

            if (files != null) {

                int size = files.length;

                for (int i = 0; i < size; i++) {
                    System.out.println(files[i]);
                    ZipEntry ze = new ZipEntry(splitName(files[i],i));
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(files[i]);
                    int len;
                    while ((len = in.read(arrayOfByte)) > 0) {
                        zos.write(arrayOfByte, 0, len);
                    }
                    in.close();
                }
            }
            zos.closeEntry();
            zos.close();
            byte[] bFile = readBytesFromFile(zipFile + apellidos + "_" + applicationNumber + ".zip");
            return bFile;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String splitName(String name, int i) {
        if (name != null && !name.isEmpty()) {
            String[] namePart  =name.split("/");
            if(namePart != null && namePart.length >0){
                return namePart[namePart.length-1];
            }else{
                return "file_" + i;
            }
        } else {
            return " ";
        }
    }

    public static String[] getFiles(String dir_path) {

        String[] arr_res = null;

        File f = new File(dir_path);

        if (f.isDirectory()) {

            List<String> res = new ArrayList<String>();
            File[] arr_content = f.listFiles();

            int size = arr_content.length;

            for (int i = 0; i < size; i++) {

                if (arr_content[i].isFile())
                    res.add(arr_content[i].toString());
            }


            arr_res = res.toArray(new String[0]);

        } else
            System.err.println("¡ Path NO válido !");
        return arr_res;
    }

    public String descargarPdf(String path, String nombreFichero,
                               servicios.ExpedienteInforme expediente, TipoDocumentacion tipoDocumentacion, String zipPath, final String user, final String password) throws Exception {

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

        return ficheroLocal.getPath();


    }

    public String descargarAudios(String path, String nombreFichero, servicios.ExpedienteInforme expediente, TipoDocumentacion tipoDocumentacion, int ordinal, int numeroAudios, String zipPath, final String user, final String password)
            throws Exception {

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

        return ficheroLocal.getPath();


    }

    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }
}

package hwsol.webservices

import com.scor.global.FechaUtils
import com.scortelemed.Company
import com.scortelemed.Envio
import com.scortelemed.Recibido
import com.scortelemed.schemas.caser.BenefictNameType
import com.scortelemed.schemas.caser.RequestStateType
import com.ws.cajamar.beans.Cobertura
import com.ws.cajamar.beans.ElementoEntrada
import com.ws.cajamar.beans.ElementoSalida
import hwsol.entities.EnvioCaser
import hwsol.entities.parser.RegistrarEventoSCOR
import servicios.Candidato
import servicios.TipoTelefono

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar
import java.text.DateFormat
import java.text.Normalizer
import java.text.SimpleDateFormat

class TransformacionUtil {

    public static final double DAYS_PER_YEAR = 365.25;
    public static final short HOURS_PER_DAY = 24;
    public static final short MINUTES_PER_HOUR = 60;
    public static final short SECONDS_PER_MINUTE = 60;
    public static final short MILLISECONDS_PER_SECOND = 1000;


    public String[] transformarTipoCausaTerminacion(Object estadoSt, Object motivoAnulacionSt) {

        def estadoCausa = []

        switch (estadoSt) {
            case "CERRADO":
                estadoCausa[0] = "01"
                estadoCausa[1] = "01"
                break
            case "ANULADO":
                if (motivoAnulacionSt != null) {
                    if (motivoAnulacionSt.toString().equals("SOLICITUD_DUPLICADA")) {
                        estadoCausa[0] = "02"
                        estadoCausa[1] = "02"
                    } else if (motivoAnulacionSt.toString().equals("RECHAZO_DEL_CANDIDATO") || motivoAnulacionSt.toString().equals("CANDIDATO_NO_ACUDE") || motivoAnulacionSt.toString().equals("RECHAZA_PRUEBAS") || motivoAnulacionSt.toString().equals("NO_AUTORIZA_LOPD") || motivoAnulacionSt.toString().equals("NO_CONTRATA_SEGURO")) {
                        estadoCausa[0] = "02"
                        estadoCausa[1] = "03"
                    } else if (motivoAnulacionSt.toString().equals("ILOCALIZABLE") || motivoAnulacionSt.toString().equals("ANULADO_POR_LA_COMPANYA")) {
                        estadoCausa[0] = "02"
                        estadoCausa[1] = "05"
                    } else {
                        estadoCausa = null
                    }
                }
                break
            case "NO_ACUDE_CITA":
                estadoCausa[0] = "03"
                estadoCausa[1] = "21"
                break
            case "NO_LOCALIZADO":
                estadoCausa[0] = "03"
                estadoCausa[1] = "22"
                break
        //	EL ESTADO 23 Y 24 SON EL MISMO, NOSOTROS DEVOLVEMOS EL 23 SIEMPRE
            case "RETRASO_CONTACTO":
            case "CONTACTO_DIFERIDO":
                estadoCausa[0] = "03"
                estadoCausa[1] = "23"
                break
            default:
                estadoCausa = null
                break

        }

        return estadoCausa
    }

    public String transformarRamo(String ramo) {
        if (ramo.startsWith("TP")) {
            return "2V"
        } else {
            return "4V"
        }
    }

    public String transformarCodigoCliente(String info) {

        if (info != null && !info.isEmpty() && info.contains("Id_Cliente:")) {
            def splitted = info.split(";")
            return splitted[0].substring(splitted[0].indexOf(":") + 1, splitted[0].length()).trim()
        } else {
            return null
        }
    }


    public List<String> transformarCodigoCoberturas(String info) {

        def listaCoberturas = []

        if (info != null && !info.isEmpty() && info.contains("Id_Coberturas:")) {
            def splitted = info.split(";")
            def coberturas = splitted[1].substring(splitted[1].indexOf(":") + 1, splitted[1].length())
            def splitedCoberturas = coberturas.split(",")
            for (i in splitedCoberturas) {
                listaCoberturas << "${i}"
            }
            return listaCoberturas
        } else {
            return null
        }
    }

    public boolean esFallecimiento(String id) {
        return id.equals("201") || id.equals("401") || id.equals("21001")
    }

    public boolean esEnfermedad(String id) {
        return id.equals("301") || id.equals("302")
    }

    public boolean esInvalidez(String id) {
        return id.equals("702") || id.equals("703") || id.equals("21003")
    }

    public Cobertura devolverCoberturaCm(String codigoCobertura, Object cobertura, String numSolicitud) throws Exception {

        Cobertura cober = new Cobertura()
        def correoUtil = new CorreoUtil()
        Exception e = new Exception()
        def msg = "No hay resultado para la cobetura : " + cobertura.getNombreCobertura() + ". Expediente con n�mero de referencia: " + numSolicitud + ". Se excluye del envio, "

        String valoracionPrima = cobertura.getValoracionPrima()
        String resultadoExpediente = cobertura.getCodResultadoCobertura()

        if (valoracionPrima != null) {
            valoracionPrima = Normalizer.normalize(valoracionPrima, Normalizer.Form.NFD);
            valoracionPrima = valoracionPrima.replaceAll("[^\\p{ASCII}]", "");
        }

        String valoracionCapital = cobertura.getValoracionCapital()

        cober.setCobern(codigoCobertura)

        if (resultadoExpediente != null) {

            //tratamos prima
            if (valoracionPrima != null && valoracionPrima != "") {
                cober.setVacobe("8")
                cober.setSobmor("0")

                if (valoracionPrima.toString().contains("%")) {

                    def numero = valoracionPrima.toString().substring(0, valoracionPrima.toString().indexOf("%"))

                    if (numero.toString().replace(",", ".").isNumber()) {
                        cober.setSobpri(valoracionPrima.replace(",", "."))
                    } else {
                        correoUtil.envioEmailNoTratados("CajamarCaseResult", msg + " porque no se ha encontrado un n�mero en sobreprima")
                        throw e
                    }
                } else if (valoracionPrima.toString().replace(",", ".").isNumber()) {
                    cober.setSobpri(valoracionPrima.replace(",", "."))
                } else {
                    correoUtil.envioEmailNoTratados("CajamarCaseResult", msg + " porque no se ha encontrado un n�mero en sobreprima")
                    throw e
                }
            }

            //tratamos capital
            else if (valoracionCapital != null && valoracionCapital != "") {
                cober.setVacobe("7")
                cober.setSobpri("0")

                if (valoracionCapital.toString().contains("%")) {

                    def numero = valoracionCapital.toString().substring(0, valoracionCapital.toString().indexOf("%"))

                    if (numero.toString().replace(",", ".").isNumber()) {
                        cober.setSobmor(valoracionCapital.replace(",", "."))
                    } else {
                        correoUtil.envioEmailNoTratados("CajamarCaseResult", msg + " porque no se ha encontrado un n�mero en sobrecapital")
                        throw e
                    }
                } else if (valoracionCapital.toString().replace(",", ".").isNumber()) {
                    cober.setSobmor(valoracionCapital.replace(",", "."))
                } else {
                    correoUtil.envioEmailNoTratados("CajamarCaseResult", msg + " porque no se ha encontrado un n�mero en sobrecapital")
                    throw e
                }
            } else if (resultadoExpediente.equals("1")) {
                //else if (valoracionPrima == null && cobertura.getCodResultadoCobertura().equals("1")) {
                cober.setVacobe("6")
                cober.setSobpri("0")
                cober.setSobmor("0")
            } else if (resultadoExpediente.equals("7")) {
                //else if (valoracionPrima == null && cobertura.getCodResultadoCobertura().equals("7")) {
                cober.setVacobe("9")
                cober.setSobpri("0")
                cober.setSobmor("0")
            } else {
                correoUtil.envioEmailNoTratados("CajamarCaseResult", msg)
                throw e
            }
        }
        //Resultado expediente es null
        else {
            correoUtil.envioEmailNoTratados("CajamarCaseResult", msg)
            throw e
        }
        return cober
    }

    public Cobertura devolverCoberturaVacia(String codigoCobertura, boolean todasRechazas) {

        Cobertura cober = new Cobertura()
        cober.setCobern(codigoCobertura)

        if (todasRechazas) {
            cober.setVacobe("9")
        } else {
            cober.setVacobe("")
        }
        cober.setSobpri("0")
        cober.setSobmor("0")


        return cober
    }

    public String obtenerCodigoCajamar(String codigoCrm, String codigoProducto) {

        def codigoCajamar

        switch (codigoProducto) {
            case "TPC02":
                if (codigoCrm.equals("COB215")) {
                    codigoCajamar = "21001"
                }
                if (codigoCrm.equals("COB216")) {
                    codigoCajamar = "21003"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "21007"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "21009"
                }
                break
            case "TPC05":
                if (codigoCrm.equals("COB215")) {
                    codigoCajamar = "21001"
                }
                if (codigoCrm.equals("COB216")) {
                    codigoCajamar = "21003"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "21007"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "21009"
                }
                break
            case "TPU01":
                if (codigoCrm.equals("COB215")) {
                    codigoCajamar = "21001"
                }
                if (codigoCrm.equals("COB216")) {
                    codigoCajamar = "21003"
                }
                break
            case "TPU03":
                if (codigoCrm.equals("COB215")) {
                    codigoCajamar = "21001"
                }
                if (codigoCrm.equals("COB216")) {
                    codigoCajamar = "21003"
                }
                break
            case "TPU11":
                if (codigoCrm.equals("COB215")) {
                    codigoCajamar = "21001"
                }
                if (codigoCrm.equals("COB216")) {
                    codigoCajamar = "21003"
                }
                break
            case "TRA01":
                if (codigoCrm.equals("COB212")) {
                    codigoCajamar = "703"
                }
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "401"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "703"
                }
                break
            case "TRA02":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA03":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA04":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA05":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA06":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                break
            case "TRA08":
                if (codigoCrm.equals("COB212")) {
                    codigoCajamar = "703"
                }
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB213")) {
                    codigoCajamar = "10001"
                }
                if (codigoCrm.equals("COB214")) {
                    codigoCajamar = "10002"
                }
                if (codigoCrm.equals("COB218")) {
                    codigoCajamar = "301"
                }
                if (codigoCrm.equals("COB219")) {
                    codigoCajamar = "302"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "401"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "703"
                }
                break
            case "TRA11":
                if (codigoCrm.equals("COB212")) {
                    codigoCajamar = "703"
                }
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "401"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "703"
                }
                break
            case "TRA12":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA13":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA14":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA15":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                break
            case "TRA16":
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }

                break
            case "TRA32":
                if (codigoCrm.equals("COB212")) {
                    codigoCajamar = "703"
                }
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "401"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "703"
                }
                break
            case "TRA35":
                if (codigoCrm.equals("COB212")) {
                    codigoCajamar = "703"
                }
                if (codigoCrm.equals("COB217")) {
                    codigoCajamar = "201"
                }
                if (codigoCrm.equals("COB220")) {
                    codigoCajamar = "401"
                }
                if (codigoCrm.equals("COB221")) {
                    codigoCajamar = "702"
                }
                if (codigoCrm.equals("COB223")) {
                    codigoCajamar = "703"
                }
            default:
                break
        }

        return codigoCajamar
    }

    boolean obtenerEstadoCoberturas(List<Object> coberturas) {

        Boolean todasRechazadas = true

        for (i in coberturas) {

            String valoracionPrima = i.getResultadoCobertura()

            if (valoracionPrima != null) {

                if (!valoracionPrima.toUpperCase().equals("RECHAZO")) {
                    todasRechazadas = false
                    break
                }
            }
        }

        return todasRechazadas
    }

    def String obtenerDiasContacto(String diasContacto) {

        def dias = ""

        if (diasContacto != null && !diasContacto.isEmpty()) {
            if (diasContacto[0].equals("1")) {
                dias += "L"
            }
            if (diasContacto[1].equals("1")) {
                dias += "-M"
            }
            if (diasContacto[2].equals("1")) {
                dias += "-X"
            }
            if (diasContacto[3].equals("1")) {
                dias += "-J"
            }
            if (diasContacto[4].equals("1")) {
                dias += "-V"
            }
            if (diasContacto[5].equals("1")) {
                dias += "-S"
            }
            if (diasContacto[6].equals("1")) {
                dias += "-D"
            }
        }
        return dias
    }

    public List<ElementoEntrada> obtenerElementoEntrada(Company company) {

        List<ElementoEntrada> elementos = new ArrayList<ElementoEntrada>()

        def recibido = Recibido.findAllByCia(company.id.toString())

        def inicio
        def fin

        recibido.each { item ->

            ElementoEntrada elemento = new ElementoEntrada()
            String info = item.info

            /**TIPO DE ACCION
             *
             */
            inicio = info.indexOf("<ytipo>") + "<ytipo>".length()
            fin = info.indexOf("<ytipo>") + "<ytipo>".length() + 1
            elemento.setTipo(info.substring(inicio, fin).equals("1") ? "ALTA" : info.substring(inicio, fin).equals("2") ? "MODIFICACION" : "CANCELACION")

            /**TELESELECCIONES
             *
             */
            inicio = info.indexOf("<tlcom>") + "<tlcom>".length()
            fin = info.indexOf("<tlcom>") + "<tlcom>".length() + 1
            elemento.setTeleseleccionCorta(info.substring(inicio, fin).equals("1") ? true : false)

            inicio = info.indexOf("<tlabr>") + "<tlabr>".length()
            fin = info.indexOf("<tlabr>") + "<tlabr>".length() + 1
            elemento.setTeleseleccionAbreviada(info.substring(inicio, fin).equals("1") ? true : false)

            /**NUMERO IDENTIFICATIVO CAJAMAR
             *
             */
            inicio = info.indexOf("<numref>") + "<numref>".length()
            fin = info.indexOf("<numref>") + "<numref>".length() + 9
            elemento.setNumSolicitud(info.substring(inicio, fin))

            /**DNI
             *
             */
            inicio = info.indexOf("<dniase>") + "<dniase>".length()
            fin = info.indexOf("<dniase>") + "<dniase>".length() + 9
            elemento.setDni(info.substring(inicio, fin))

            /**FECHA DE ENTRADA
             *
             */
            elemento.setFecha(item.getFecha())

            /**FECHA DE NACIMIENTO
             *
             */
            inicio = info.indexOf("<ddnccl>") + "<ddnccl>".length()
            fin = info.indexOf("<ddnccl>") + "<ddnccl>".length() + 2
            def dia = info.substring(inicio, fin)

            inicio = info.indexOf("<mmnccl>") + "<mmnccl>".length()
            fin = info.indexOf("<mmnccl>") + "<mmnccl>".length() + 2
            def mes = info.substring(inicio, fin)

            inicio = info.indexOf("<aanccl>") + "<aanccl>".length()
            fin = info.indexOf("<aanccl>") + "<aanccl>".length() + 4
            def anio = info.substring(inicio, fin)

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
            def dateInString = dia + "-" + mes + "-" + anio

            elemento.setFechaNacimiento(sdf.parse(dateInString))

            /**TELEFONOS
             *
             */
            inicio = info.indexOf("<tefcli>") + "<tefcli>".length()
            fin = info.indexOf("<tefcli>") + "<tefcli>".length() + 9
            if (info.substring(inicio, fin).startsWith("9999")) {
                elemento.setTelefono1("-")
            } else {
                elemento.setTelefono1(info.substring(inicio, fin))
            }

            inicio = info.indexOf("<movcli>") + "<movcli>".length()
            fin = info.indexOf("<movcli>") + "<movcli>".length() + 9
            elemento.setTelefono2(info.substring(inicio, fin))

            /**PRUEBAS MEDICAS
             *
             */

            inicio = info.indexOf("<zprumd>") + "<zprumd>".length()
            fin = info.indexOf("</zprumd>")

            if (inicio != -1 && fin != -1) {
                elemento.setPruebasMedicas(info.substring(inicio, fin))
            } else {
                elemento.setPruebasMedicas("")
            }

            /**FRANJA HORARIA
             *
             */
            inicio = info.indexOf("<franhi>") + "<franhi>".length()
            fin = info.indexOf("<franhi>") + "<franhi>".length() + 5
            def hi = info.substring(inicio, fin)

            inicio = info.indexOf("<franhf>") + "<franhf>".length()
            fin = info.indexOf("<franhf>") + "<franhf>".length() + 5
            def hf = info.substring(inicio, fin)

            elemento.setFranjaHoraria(hi + "-" + hf)


            inicio = info.indexOf("<diastl>") + "<diastl>".length()
            fin = info.indexOf("</diastl>")
            elemento.setDias(obtenerDiasContacto(info.substring(inicio, fin)))

            /**PREGUNTA SIDA
             */
            inicio = info.indexOf("<xpsida>") + "<xpsida>".length()
            fin = info.indexOf("<xpsida>") + "<xpsida>".length() + 1
            elemento.setSida(info.substring(inicio, fin).equals("S") ? true : false)

            elementos.add(elemento)
        }

        return elementos
    }

    public obtenerElementoSalida(Company company) {

        List<ElementoSalida> elementos = new ArrayList<ElementoSalida>()



        def enviado = Envio.findAllByCia(company.id.toString())

        def inicio
        def fin

        enviado.each { item ->

            ElementoSalida elemento = new ElementoSalida()
            String info = item.info

            /**TIPO TERMINACION
             *
             */
            inicio = info.indexOf("<tipote>") + "<tipote>".length()
            fin = info.indexOf("<tipote>") + "<tipote>".length() + 2
            elemento.setEstado(obtenerTipoTerminacion(info.substring(inicio, fin)))

            /**CAUSA TERMINACION
             *
             */
            inicio = info.indexOf("<causte>") + "<causte>".length()
            fin = info.indexOf("<causte>") + "<causte>".length() + 2
            elemento.setMotivo(obtenerCausaTerminacion(info.substring(inicio, fin)))

            /**COD CLIENTE
             *
             */
            inicio = info.indexOf("<wpers>") + "<wpers>".length()
            fin = info.indexOf("<wpers>") + "<wpers>".length() + 10
            elemento.setCliente(info.substring(inicio, fin))

            /**RAMO
             *
             */
            inicio = info.indexOf("<yramex>") + "<yramex>".length()
            fin = info.indexOf("</yramex>")
            elemento.setRamo(info.substring(inicio, fin))

            /**PRODUCTO
             *
             */
            inicio = info.indexOf("<yprodu>") + "<yprodu>".length()
            fin = info.indexOf("</yprodu>")
            elemento.setProducto(info.substring(inicio, fin))

            /**SOLICITUD
             *
             */
            inicio = info.indexOf("<numref>") + "<numref>".length()
            fin = info.indexOf("<numref>") + "<numref>".length() + 9
            elemento.setSolicitud(info.substring(inicio, fin))

            /**FECHA DE SALIDA
             *
             */
            elemento.setFecha(item.getFecha())

            /**COBERTURAS
             *
             */
            inicio = info.indexOf("<tlcom>") + "<tlcom>".length()
            fin = info.indexOf("<tlcom>") + "<tlcom>".length() + 1
            elemento.setTeleseleccionCorta(info.substring(inicio, fin).equals("1") ? true : false)

            inicio = info.indexOf("<tlabr>") + "<tlabr>".length()
            fin = info.indexOf("<tlabr>") + "<tlabr>".length() + 1
            elemento.setTeleseleccionAbreviada(info.substring(inicio, fin).equals("1") ? true : false)

            /**CODIGO DE ERROR
             *
             */
            inicio = info.indexOf("<coder8>") + "<coder8>".length()
            fin = info.indexOf("<coder8>") + "<coder8>".length() + 3
            elemento.setCodError(info.substring(inicio, fin))

            /**MSG DE ERROR
             *
             */
            inicio = info.indexOf("<werrts>") + "<werrts>".length()
            fin = info.indexOf("</werrts>")
            elemento.setError(info.substring(inicio, fin))

            elemento.setCoberturas()

            elementos.add(elemento)
        }

        return elementos
    }

    public String obtenerTipoTerminacion(String codigo) {

        def codigoCm

        switch (codigo) {
            case "01":
                codigoCm = "Cierre"
                break
            case "02":
                codigoCm = "Anulacion"
                break
            case "03":
                codigoCm = "Alerta"
                break
            default:
                codigoCm = null
                break
        }
    }

    public String obtenerCausaTerminacion(String codigo) {

        def codigoCm

        switch (codigo) {
            case "01":
                codigoCm = "Cierre/Aceptacion"
                break
            case "02":
                codigoCm = "Anulado por solicitud duplicada"
                break
            case "03":
                codigoCm = "Anulado por rechazo candidato"
                break
            case "05":
                codigoCm = "Anulado por orden compa�ia"
                break
            case "21":
                codigoCm = "Alerta1 1-Citado no presentado"
                break
            case "22":
                codigoCm = "Alerta 2-Ilocalizable(< 7 dias)"
                break
            case "23":
                codigoCm = "Alerta 3-Demorado(< 3 meses)"
                break
            case "24":
                codigoCm = "Alerta 4-Demorado(> 3 meses)"
                break
            default:
                codigoCm = null
                break
        }
    }

    def devolverDatos(dato) {
        if (dato != null) {
            return dato.toString()
        } else {
            return ""
        }
    }

    def fromStringToXmlCalendar(String fecha) {

        String format = null
        int pos = 0

        if (fecha != null && !fecha.isEmpty()) {
            fecha = fecha.replace(" ","")
            GregorianCalendar cal = new GregorianCalendar()

            if (fecha.contains('/')) {

                pos = fecha.indexOf("/")

                if (pos == 4) {
                    format = "yyyy/MM/dd"
                }

                if (pos == 2) {
                    format = "dd/MM/yyy"
                }

            } else if (fecha.contains('-')) {

                pos = fecha.indexOf("-")

                if (pos == 4) {
                    format = "yyyy-MM-dd"
                }

                if (pos == 2) {
                    format = "dd-MM-yyy"
                }

            }

            if (fecha.contains('T')) {
                cal.setTime(new SimpleDateFormat(format).parse(fecha.substring(0, fecha.indexOf('T'))))
            } else {
                cal.setTime(new SimpleDateFormat(format).parse(fecha))
            }
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal)

            return calendar

        } else {
            return null
        }

    }

    def fromStringToCalendar(String fecha) {

        String format = null

        if (fecha != null && !fecha.isEmpty()) {

            GregorianCalendar cal = new GregorianCalendar()

            if (fecha.contains('/')) {
                format = "yyyy/MM/dd"
            } else if (fecha.contains('-')) {
                format = "yyyy-MM-dd"
            } else {
                format = "yyyyMMdd"
            }

            if (fecha.contains('T')) {
                cal.setTime(new SimpleDateFormat(format).parse(fecha.substring(0, fecha.indexOf('T'))))
            } else {
                cal.setTime(new SimpleDateFormat(format).parse(fecha))
            }

            return cal

        } else {
            return null
        }

    }

    def fromDateToXmlCalendar(Date fecha) {

        if (fecha != null) {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(fecha);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } else {
            return null
        }
    }

    def fromDateToString(Date date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format)

        return formatter.format(date)

    }

    def devolverEstadoCivil(estado) {

        switch (estado) {
            case "SOLTERO": return CivilStateType.S;
            case "CASADO": return CivilStateType.C;
            case "DIVORCIADO": return CivilStateType.D;
            case "SEPARADO": return CivilStateType.S;
            case "PAREJA_DE_HECHO": return CivilStateType.P;
            case "VIUDO": return CivilStateType.V;
            default: return null;
        }
    }

    def devolverTelefonoMovil(Candidato candidato) {

        if (candidato.getTipoTelefono1() != null && candidato.getTipoTelefono1() == TipoTelefono.MOVIL) {
            return candidato.telefono1
        } else if (candidato.getTipoTelefono2() != null && candidato.getTipoTelefono2() == TipoTelefono.MOVIL) {
            return candidato.telefono2
        } else if (candidato.getTipoTelefono3() != null && candidato.getTipoTelefono3() == TipoTelefono.MOVIL) {
            return candidato.telefono3
        } else {
            return ""
        }
    }

    def devolverTelefono1(Candidato candidato) {

        if (candidato.getTipoTelefono1() != null && candidato.getTipoTelefono1() == TipoTelefono.FIJO && candidato.getTelefono1() != null && !candidato.getTelefono1().isEmpty()) {
            return candidato.getTelefono1()
        } else {
            return ""
        }
    }

    def devolverTelefono2(Candidato candidato) {

        if (candidato.getTipoTelefono2() != null && candidato.getTipoTelefono2() == TipoTelefono.FIJO && candidato.getTelefono2() != null && !candidato.getTelefono2().isEmpty()) {
            return candidato.getTelefono2()
        } else {
            return ""
        }
    }

    def fromStringLoList(String lista) {

        if (lista != null && !lista.isEmpty()) {
            return null
        } else {
            return null
        }
    }

    def devolverStateType(estado) {

        switch (estado) {
            case "CERRADO": return RequestStateType.CLOSED;
            case "ANULADO": return RequestStateType.CANCELLED;
            case "RECHAZADO": return RequestStateType.REJECTED;
            default: return null;
        }

    }

    def devolverStateTypeAma(estado) {

        switch (estado) {
            case "CERRADO": return com.scortelemed.schemas.ama.RequestStateType.CLOSED;
            case "ANULADO": return com.scortelemed.schemas.ama.RequestStateType.CANCELLED;
            case "RECHAZADO": return com.scortelemed.schemas.ama.RequestStateType.REJECTED;
            default: return null;
        }

    }

    BigDecimal devolverDatosDecimal(dato) {

        if (dato != null) {
            Float floatNumber = Float.parseFloat(dato.toString());
            BigDecimal bd = new BigDecimal(floatNumber)
            return bd.setScale(2, BigDecimal.ROUND_UP)

        } else {
            return new BigDecimal(0)
        }
    }

    def obtenerValorEstadoExpediente(tipoEstadoExpediente) {

        switch (tipoEstadoExpediente) {
            case "PENDIENTE_CONTACTO": return "1";
            case "PEDIENTE_VALIDAR_DATOS": return "2";
            case "CITA_CONCERTADA": return "3";
            case "NO_ACUDE_CITA": return "4";
            case "RECHAZA_PRUEBAS": return "5";
            case "NO_LOCALIZADO": return "6";
            case "CARTA_ILOCALIZABLE_ENVIADA": return "7";
            case "SOLICITADO_MENSAJERO": return "8";
            case "PENDIENTE_LABORATORIO": return "9";
            case "CERRADO": return "10";
            case "ANULADO": return "11";
            case "EXPEDIENTE_TARIFICADO": return "12";
            case "RESULTADOS_RECIBIDOS": return "13";
            case "PENDIENTE_TARIFICAR": return "14";
            case "PENDIENTE_INFORME_CANDIDATO": return "15";
            case "DATOS_ERRONEOS": return "16";
            case "CONTACTO_DIFERIDO": return "17";
            case "RETRASO_CONTACTO": return "18";
            case "PENDIENTE_CITA": return "19";
            case "PENDIENTE_MENSAJERO": return "20";
            case "RECEPCION_DOCUMENTACION_RESULTADOS": return "21";
            case "PENDIENTE_CLASIFICAR": return "22";
            case "DOCUMENTACION_PARCIAL": return "23";
            case "DOCUMENTACION_ERRONEA": return "24";
            case "PRUEBAS_ADICIONALES": return "25";
            case "INFORMES_COMPLEMENTARIOS": return "26";
            case "INFORMACION_ADICIONAL": return "27";
            case "ENVIO_RESULTADOS_CANDIDATO": return "28";
            case "CANDIDATO_RECHAZA_PRUEBAS": return "29";
            case "ACEPTADO": return "30";
            case "REAPERTURADO": return "31";
            case "RECHAZADO": return "32";
            case "ABIERTO": return "33";
            case "PREAPERTURADO": return "34";
            case "ABIERTO_POR_REVISION_PAGO_RENTAS": return "35";
            case "PENDIENTE_RED": return "36";
            case "NO_LOCALIZADO_CITA": return "37";
            case "COMUNICAR_CITA_CANDIDATO": return "38";
            case "PENDIENTE_CM_GESTION_CITA": return "39";
            case "PENDIENTE_LLAMADA_CANDIDATO": return "40";
            case "PENDIENTE_LLAMADA_OFICINA": return "41";
            case "REALIZA_TUW_NO_PPMM": return "42";
            case "PENDIENTE_CIA": return "43";
            default: return "null";
        }
    }

    def obtenerValorTipoMotivoEstadoExpediente(motivoEstado) {

        switch (motivoEstado) {
            case "PTE_DOC_ASEGURADO": return "1";
            case "PTE_DOC_BENEFICIARIO": return "2";
            case "REVISION_ADMINISTRATIVA": return "3";
            case "REVISION_MEDICA": return "4";
            case "PENDIENTE_VALORADOR": return "5";
            case "PENDIENTE_IQ": return "6";
            case "RECAIDA": return "7";
            case "DOCUMENTACION_RECIBIDA": return "8";
            case "RECLAMACION_JUDICIAL": return "9";
            case "REEVALUACION_SINIESTRO": return "10";
            case "PERIODO_CARENCIA": return "11";
            case "PERIODO_FRANQUICIA": return "12";
            case "FALTA_PAGO_PRIMAS": return "13";
            case "GARANTIA_NO_EN_VIGOR": return "14";
            case "POLIZA_NO_EN_VIGOR": return "15";
            case "ASEGURADO_NO_APORTA_PREUBAS": return "16";
            case "FECHA_ANT_SUSCRIPCION": return "17";
            case "FECHA_POS_SUSCRIPCION": return "18";
            case "EXCLUSION": return "19";
            case "PREEXISTENCIA": return "20";
            case "DUPLICADO": return "21";
            case "FUERA_PLAZO": return "22";
            case "ANULADO": return "23";
            case "ERROR": return "24";
            case "OTROS": return "25";
            case "ALTA_MEDICA": return "26";
            case "ALCANZA_LIMITE": return "27";
            case "BAJA_NO_IMPEDITIVO": return "28";
            case "FALLECIMIENTO": return "29";
            case "NO_CUMPLE_REQUISITOS": return "30";
            case "SUPERA_INGRESOS": return "31";
            case "IAP": return "32";
            case "ANULACION_IAP": return "33";
            case "TERMINADO": return "34";
            case "PRESCRIPCIOON": return "35";
            case "RENUNCIA": return "36";
            case "FRAUDE": return "37";
            case "POR_CIA": return "38";
            case "PTE_CIA": return "39";
            case "PAGO_RENTAS": return "40";
            case "LIBERACION": return "41";
            case "FALTA_DOCUMENTACION": return "42";
            case "RECHAZO_TELEFONICO": return "43";
            case "GARANTIA_NO_ENCONTRADA": return "44";
            case "RESCATADA": return "45";
            default: return "null";
        }
    }

    def obtenerValorSituacionPoliza(tipoSituacionPoliza) {

        switch (tipoSituacionPoliza) {
            case "EN_VIGOR": return "1";
            case "SUSPENDIDA": return "2";
            case "REHABILITADA": return "3";
            case "SINIESTRADA": return "4";
            case "ANULADA": return "5";
            case "VENCIDA": return "6";
            case "LIBERADA": return "7";
            default: return "null";
        }
    }


    def obtenerValorCausaAccidente(tipoCausaAccidente) {

        switch (tipoCausaAccidente) {
            case "ENFERMEDAD": return "1";
            case "ACCIDENTE": return "2";
            case "NATURAL": return "3";
            case "PARTO": return "4";
            default: return "null";
        }
    }

    def obtenerValorDano(tipoDano) {

        switch (tipoDano) {
            case "INCAPACIDAD_TRANSITORIA": return "1";
            case "FALLECIMIENTO": return "2";
            case "INVALIDEZ_ABSOLUTA": return "3";
            case "INVALIDEZ_TOTAL": return "4";
            case "INVALIDEZ_PARCIAL": return "5";
            case "GRAN_INVALIDEZ": return "6";
            case "INCAPACIDAD_PROFESIONAL": return "7";
            default: return "null";
        }
    }

    def obtenerValorGrupoProfesional(tipoGrupoProfesional) {

        switch (tipoGrupoProfesional) {
            case "GRUPO_1": return "1";
            case "GRUPO_2": return "2";
            case "GRUPO_3": return "3";
            case "GRUPO_4": return "4";
            default: return "null";
        }
    }

    def obtenerTipoPago(tipoPago) {

        switch (tipoPago) {
            case "PARCIAL": return "1";
            case "FINAL": return "2";
            case "ANTICIPO": return "3";
            default: return "null";
        }
    }

    def obtenerTipoPerceptor(tipoPerceptor) {

        switch (tipoPerceptor) {
            case "ASEGURADO": return "1";
            case "COLABORADOR": return "2";
            case "BENEFICIARIO": return "3";
            default: return "null";
        }
    }

    def obtenerSubtipoPago(subtipoPago) {

        switch (subtipoPago) {
            case "ABOGADO": return "1";
            case "MEDICO": return "2";
            case "PROCURADOR": return "3";
            default: return null;
        }
    }

    def obtenerMetodoPago(metodoPago) {

        switch (metodoPago) {
            case "TALON": return "1";
            case "TRANSFERENCIA_EXTERNA": return "2";
            case "ESPECIAL": return "3";
            case "TRANSFERENCIA_INTERNA": return "4";
            default: return "null";
        }
    }

    def obtenerTipoIva(tipoIva) {

        switch (tipoIva) {
            case "IVA_REPERCUTIDO_8": return "1";
            case "IVA_REPERCUTIDO_21": return "2";
            case "IVA_REPERCUTIDO_4": return "3";
            case "IVA_REPERCUTIDO_EXENTO": return "4";
            case "IVA_SOPORTADO_0": return "5";
            case "IVA_SOPORTADO_4": return "6";
            case "IVA_SOPORTADO_10": return "7";
            case "IVA_SOPORTADO_21": return "8";
            case "IVA_SOPORTADO_EXENTO": return "9";
            case "IGIC_SOPORTADO_0": return "10";
            case "IGIC_SOPORTADO_2": return "11";
            case "IGIC_SOPORTADO_7": return "12";
            case "IGIC_SOPORTADO_13_5": return "13";
            case "IGIC_SOPORTADO_EXENTO": return "14";
            case "IVA_INFRACOMUNITARIO_21": return "15";
            default: return "";
        }
    }

    def obtenerValorSexo(tipoSexo) {

        switch (tipoSexo) {
            case "HOMBRE": return "1";
            case "MUJER": return "2";
            default: return "null";
        }
    }

    def obtenerTipoDocumento(tipoDocumento) {

        switch (tipoDocumento) {
            case "CIF": return "1";
            case "DNI": return "2";
            case "PASAPORTE": return "3";
            case "TARJETA_RESIDENTE": return "4";
            default: return "null";
        }
    }

    def obtenerValorTelefono(tipoTelefono) {

        switch (tipoTelefono) {
            case "MOVIL": return "1";
            case "FIJO": return "2";
            case "FAX": return "3";
            default: return "null";
        }
    }

    def obtenerValorTipoProvision(tipoProvision) {

        switch (tipoProvision) {
            case "RESERVA_COLABORADOR": return "1";
            case "RESERVA_SINIESTRO": return "2";
            case "AJUSTE_RESERVA_COLABORADOR": return "3";
            case "AJUSTE_RESERVA_SINIESTRO": return "4";
            default: return "null";
        }
    }

    def obtenerValorTipoReserva(tipoReserva) {

        switch (tipoReserva) {
            case "ABOGADO": return "1";
            case "MEDICO": return "2";
            case "PROCURADOR": return "3";
            default: return null;
        }
    }

    def obtenerValorLimitacionFuncional(limitacionFuncional) {

        switch (limitacionFuncional) {
            case "ABOGADO": return "1";
            case "MEDICO": return "2";
            case "PROCURADOR": return "3";
            default: return "null";
        }
    }

    def obtenerTipoInterviniente(interviniente) {

        switch (interviniente) {
            case "ASEGURADO": return "1";
            case "TOMADOR": return "2";
            case "BENEFICIARIO": return "3";
            case "COLABORADOR": return "4";

            default: return "null";
        }
    }

    def obtenerDetalle(entidad) {

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd")
        DateFormat formatterSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

        RegistrarEventoSCOR entradaDetalle = new RegistrarEventoSCOR()

        if (entidad instanceof servicios.Expediente) {

            switch (obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString())) {
                case "1":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("PENDIENTE_CONTACTO")
                    return entradaDetalle
                case "4":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("NO_ACUDE_CITA")
                    return entradaDetalle
                case "5":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("RECHAZA_PRUEBAS")
                    return entradaDetalle
                case "6":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("NO_LOCALIZADO")
                    return entradaDetalle
                case "7":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("CARTA_ILOCALIZABLE_ENVIADA")
                    return entradaDetalle
                case "10":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("CERRADO")
                    return entradaDetalle
                case "11":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("ANULADO")
                    return entradaDetalle
                case "17":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("CONTACTO_DIFERIDO")
                    return entradaDetalle
                case "18":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("RETRASO_CONTACTO")
                    return entradaDetalle
                case "19":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("PENDIENTE_CITA")
                    return entradaDetalle
                case "20":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("PENDIENTE_MENSAJERO")
                    return entradaDetalle
                case "21":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("RECEPCION_DOCUMENTACION_RESULTADOS")
                    return entradaDetalle
                case "26":
                    Date date = formatter.parse(entidad.getFechaApertura())
                    entradaDetalle.setIdExpediente(entidad.getNumSolicitud())
                    entradaDetalle.setFechaCierre(formatterSalida.format(date))
                    entradaDetalle.setCodigoEvento(obtenerValorEstadoExpediente(entidad.getCodigoEstado().toString()))
                    entradaDetalle.setDetalle("INFORMES_COMPLEMENTARIOS")
                    return entradaDetalle
                default:
                    return null
            }

        }

    }

    int calcularEdad(Date birthdate) {

        Calendar fechaNac = Calendar.getInstance()
        fechaNac.setTime(birthdate)

        Calendar today = Calendar.getInstance()
        int diffYear = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR)
        int diffMonth = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH)
        int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH)

        if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
            diffYear = diffYear - 1
        }
        return diffYear
    }

    def devolverNombreCobertura(codigo) {

        if (codigo.equals("COB5")) {
            return BenefictNameType.DEAD
        } else if (codigo.equals("COB4")) {
            return BenefictNameType.DISABILITY_30
        } else if (codigo.equals("COB2")) {
            return BenefictNameType.ACCIDENTAL_DEAD
        } else if (codigo.equals("COB1")) {
            return BenefictNameType.DEPENDENCY
        } else {
            return null
        }
    }

    def boolean hasElements(String informacion) {
        return (informacion != null && !informacion.isEmpty());
    }

    /**Crea una cadena con los errores de validacion de entrada de datos
     *
     * @param errores
     * @return
     */
    public String detalleError(List<WsError> errores) {

        String detalle = ""

        for (int i = 0; i < errores.size(); i++) {
            detalle += "Atrributo " + "'" + errores.get(i).getCampo() + "'" + " : " + errores.get(i).getValor() + ". " + errores.get(i).getDetalle() + "."
        }

        return detalle

    }

    public int calcularEdadActuarial(Calendar fechaNacimiento) {

        double millisecsEnUnAnio = DAYS_PER_YEAR * HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;

        GregorianCalendar calendarNacimiento = new GregorianCalendar(FechaUtils.getYearAsInt(fechaNacimiento.getTime()), FechaUtils.getMonthAsInt(fechaNacimiento.getTime()) - 1, FechaUtils.getDayAsInt(fechaNacimiento.getTime()));

        GregorianCalendar hoy = new GregorianCalendar();
        double anios = (hoy.getTimeInMillis() - calendarNacimiento.getTimeInMillis()) / millisecsEnUnAnio;
        String edadCandidato = (new Long(Math.round(anios))).toString();


        return Integer.valueOf(edadCandidato)


    }

    private String codificarResultado(String resultadoCobertura) {

        switch (resultadoCobertura) {

            case "1":
                return "3"; //Standard
            case "5":
                return "11";//In attesa referto médico
            case "6":
                return "8";//In attesa prova médica
            case "7":
                return "4";//Rifuto cobertura
            case "8":
                return "9";//Posporre
            case "9":
                return "10";//Consultar tariffatore
            case "10":
                return "12";//Accordo
            case "20":
                return "13";//Non valutato
            case "30":
                return "5";//Sovrapremio
            case "31":
                return "6";//Sovramortalita
            default:
                return null;
        }

    }

}
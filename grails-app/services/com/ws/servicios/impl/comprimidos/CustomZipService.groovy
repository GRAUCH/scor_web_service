package com.ws.servicios.impl.comprimidos

import com.scor.global.ZipUtils
import com.scortelemed.Conf
import com.scortelemed.TipoCompany
import com.ws.servicios.IComprimidoService
import com.ws.servicios.LogginService
import grails.transaction.Transactional
import servicios.Documentacion
import servicios.Expediente
import servicios.TipoEstadoExpediente

import java.text.SimpleDateFormat

@Transactional
class CustomZipService implements IComprimidoService{

    LogginService logginService
    def zipUtils = new ZipUtils()
    TipoCompany company

    CustomZipService(TipoCompany tipoCompany) {
        this.company = tipoCompany
    }

    @Override
    def obtenerZip(String nodo) {
        return null
    }

    def obtenerZip(Expediente expediente) {
        def resultado
        if (expediente.getCodigoEstado()== TipoEstadoExpediente.CERRADO) {
            logginService.putInfoMessage("Iniciado generacion de zip para expediente " + expediente.codigoST)
            List<Documentacion> listaDocumentosAudio = new ArrayList<Documentacion>()
            boolean audioEncontrado = false

            listaDocumentosAudio = zipUtils.obtenerAudios(expediente, null)

            if (listaDocumentosAudio != null && listaDocumentosAudio.size() > 0) {

                for (int i = 0; i < listaDocumentosAudio.size(); i++) {
                    if (listaDocumentosAudio.get(i).getNombre().contains(company.audio)) {
                        audioEncontrado = true
                        break
                    }
                }
            }

            if (audioEncontrado) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
                String dateString = formatter.format(new Date().getTime())
                resultado = zipUtils.generarZips(expediente, company.zip, dateString, Conf.findByName("rutaFicheroZip")?.value, Conf.findByName("usuarioZip")?.value, Conf.findByName("passwordZip")?.value)
                logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "Expediente  con codigo ST: " + expediente.getCodigoST() + " y codigo cia: " + expediente.getNumSolicitud() + " para la cia: " + company.nombre + " se ha enviado correctamente")

            } else {
                logginService.putInfoEndpoint("ResultadoReconocimientoMedico", "No se han encontardo audio necesarios para completar ZIP para " + company.nombre + " con expediente " + expediente.getCodigoST())
                resultado = new byte[0]
            }
        } else {
            logginService.putInfoEndpoint("obtenerZip(Expediente)", "Expediente  con codigo ST: " + expediente.getCodigoST() + " no se ha generado zip porque el expediente no está CERRADO")
            resultado = new byte[0]
        }
        return resultado
    }

}

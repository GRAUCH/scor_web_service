package hwsol.webservices

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import servicios.DocumentacionExpedienteInforme

import com.ws.comprimirDocumentos.ComprimirDocumentos_PortType;
import com.ws.comprimirDocumentos.ComprimirDocumentos_ServiceLocator;
import com.ws.comprimirDocumentos.DatosZIP;
import com.ws.comprimirDocumentos.ParametrosEntrada;
import com.ws.comprimirDocumentos.ParametrosSalida;
import com.ws.comprimirDocumentos.Unzip;
import com.ws.servicios.LogginService

class GenerarZip {

	private String usuario;
	private String password;

	public byte[] generarZIP(LogginService log, String nodoAlfresco) {

		Unzip unzip = new Unzip();
		int codigoResultado = 0;

		byte[] compressedData = null;
		
		try {
		  
			ParametrosEntrada parametrosEntrada = new ParametrosEntrada();
			parametrosEntrada.setUsuario("generico");
			parametrosEntrada.setClave("*W;n-:Aj.Oa12");
			parametrosEntrada.setRefNodo(nodoAlfresco);
			ParametrosSalida parametrosSalida = null;

			ComprimirDocumentos_ServiceLocator comprimirDocumentosServiceLocator = new ComprimirDocumentos_ServiceLocator();
			ComprimirDocumentos_PortType port = comprimirDocumentosServiceLocator.getComprimirDocumentosPort();

			parametrosSalida = port.process(parametrosEntrada);

			DatosZIP datosZip = parametrosSalida.getDatosRespuesta();

			compressedData = datosZip.getContent();
			
		} catch (ServiceException e) {
			log.putInfoEndpoint("Exception in class " + getClass().getName() + ". " + e.getMessage());
		} catch (RemoteException e) {
			log.putInfoEndpoint("Exception in class " + getClass().getName() + ". " + e.getMessage());
		} catch (IOException e) {
			log.putInfoEndpoint("Exception in class " + getClass().getName() + ". " + e.getMessage());
		}

		return compressedData;
	}
}
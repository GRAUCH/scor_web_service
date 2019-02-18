package services


import hwsol.webservices.CorreoUtil
import hwsol.webservices.TransformacionUtil
import hwsol.webservices.WsError

import java.text.SimpleDateFormat
import java.util.List;

import org.apache.cxf.annotations.SchemaValidation
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.jws.WebMethod;

import com.scortelemed.Company
import com.scortelemed.schemas.enginyers.ArrayOfFaultElement;
import com.scortelemed.schemas.enginyers.ErrorElement;
import com.scortelemed.schemas.enginyers.Expedient
import com.scortelemed.schemas.enginyers.AddExpResponse
import com.scortelemed.schemas.enginyers.AddExp
import com.scortelemed.schemas.enginyers.FaultElement;
import com.ws.servicios.EnginyersService
import com.ws.servicios.EstadisticasService
import com.ws.servicios.LogginService;
import com.ws.servicios.RequestService
import com.ws.servicios.TarificadorService


@WebService(targetNamespace = "http://www.scortelemed.com/schemas/enginyers")
@SchemaValidation
@GrailsCxfEndpoint(address='/enginyers/EnginyersUnderwrittingCaseManagement',
expose = EndpointType.JAX_WS,properties = [@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), @GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")])
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)

class EnginyersUnderwrittingCaseManagementService	 {
	
	
	@Autowired
	private EnginyersService psnService
	@Autowired
	private EstadisticasService estadisticasService
	@Autowired
	private RequestService requestService
	@Autowired
	private LogginService logginService
	@Autowired
	private TarificadorService tarificadorService

	@WebResult(name = "addExpResponse")
	@WebMethod(action = "http://www.scortelemed.com/schemas/enginyers/addExp")
	AddExpResponse addExp(
			@WebParam(partName = "addExp",name = "addExp")
			AddExp addExp) {
			

			def opername="EnginyersResultadoReconocimientoMedicoRequest"
			def correoUtil = new CorreoUtil()
			List<WsError> wsErrors = new ArrayList<WsError>()
			def requestXML = ""
			def crearExpedienteService
			def requestBBDD
			def respuestaCrm
	
			String message = null
			int code = 0
	
			Company company = Company.findByNombre("enginyers")
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd")
			TransformacionUtil util = new TransformacionUtil()
			AddExpResponse resultado=new AddExpResponse()
	
			logginService.putInfoMessage("Realizando peticion de informacion de servicio GestionReconocimientoMedico para la cia " + company.nombre)
			
			
			try{
		
				def operacion = estadisticasService.obtenerObjetoOperacion(opername)
				
					if (operacion && operacion.activo) {
				
						if (Company.findByNombre("psn").generationAutomatic) {
							
							logginService.putInfoMessage("Realizando peticion AddExp para " + company.nombre + " con numero de solicitud: " + addExp.d.getPolicyNumber())
							
							
							requestXML = psnService.marshall("http://www.scortelemed.com/schemas/enginyers", gestionReconocimientoMedico)
							requestBBDD = requestService.crear(opername, requestXML)
							requestBBDD.fecha_procesado = new Date()
							requestBBDD.save(flush: true)
		
							wsErrors = psnService.validarDatosObligatorios(requestBBDD)
		
							if (wsErrors != null && wsErrors.size() == 0) {
							
							
							}
							
						}
					}
			} catch (Exception e){

			}


			ArrayOfFaultElement array = new ArrayOfFaultElement()
			FaultElement fault = new FaultElement();
			
		Expedient expedient = new Expedient();
		expedient.setAddExpResultCode(1)
		expedient.setIdExpedient(1)
		ErrorElement error = new ErrorElement()
		error.setErrorMessage("")
		error.setErrorNumber(0)
		error.setErrorSource("")
		
		fault.setDetail(error)
		fault.setFaultActor("")
		fault.setFaultCode("")
		fault.setFaultString("")
		
		List<FaultElement> faultList = new ArrayList<FaultElement>();
		faultList.add(fault)
		
		array.getFaultElement().addAll(faultList)
		
		expedient.setFaultArray(array)
		resultado.setAddExpResult(expedient)
		
		

		logginService.putInfoMessage("Fin peticion de informacion de servicio GestionReconocimientoMedico para la cia mutua")
		
		return resultado
	}
				
}

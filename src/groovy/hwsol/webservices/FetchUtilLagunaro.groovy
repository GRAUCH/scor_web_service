package hwsol.webservices

import java.util.ArrayList;
import org.springframework.web.context.request.RequestContextHolder;
import java.text.SimpleDateFormat;
import wslite.soap.*
import org.codehaus.groovy.grails.commons.GrailsApplication

class FetchUtilLagunaro {
	GrailsApplication grailsApplication
	
	private static String dameExpedientesTarificados(Date fecha,String compania){	

		/**Ahora se pasa de las horas. Se toma la hora del sistema y se resta 
		 * 
		 */
		def tiempoActual

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	
		tiempoActual=new Date().getTime();
	
		def horas1 =  1 * 60 * 60 * 1000
		tiempoActual=tiempoActual-horas1
	
		Date fechaSig = new Date(tiempoActual - horas1*2)

		//devuelvo las 2 �ltimas horas.		
		def fechaInicio = formatoDelTexto.format(fechaSig)
		def fechaFin= formatoDelTexto.format(tiempoActual)
		

def coberturas = ""

coberturas = """
			<link-entity name='scor_coberturadelexpediente' from='scor_expedienteid' to='scor_expedienteid'>
				<attribute name='scor_valoracioncapital'/>
				<attribute name='scor_valoracionprima'/>
				<attribute name='scor_valoraciontemporal'/>			
				<link-entity name='scor_cobertura' from='scor_coberturaid' to='scor_coberturaid'>
					<attribute name='scor_name'/>
					<attribute name='scor_codigo'/>
				</link-entity>
			</link-entity>
			"""

	
		
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<attribute name='scor_name'/>
							<attribute name='scor_npoliza'/>
							<attribute name='scor_nsuplemento'/>
							<attribute name='scor_nsolicitud_compania'/>
							<attribute name='scor_estado'/>
							<attribute name='scor_expedienteid'/>
							<attribute name='scor_fechadeestado'/>
							<attribute name='scor_ncertificado'/>
							
							<filter type='and'>
								<condition attribute='scor_estado' operator='eq' value='10'/>
								<condition attribute='scor_fechadeestado' operator='between'>
									<value>$fechaInicio</value>
									<value>$fechaFin</value>
								</condition>									
							
							</filter>
							$coberturas
							<link-entity name='contact' from='contactid' to='scor_candidatoid' link-type='natural'>
								<attribute name='scor_dnipasaporte'/>	
								<link-entity name='scor_cliente' from='scor_clienteid' to='scor_candidatosid'>
									<attribute name='scor_codigost'/>
									<filter type='and'>
										<condition attribute='scor_codigost' operator='eq' value='$compania'/>
									</filter>								
								</link-entity>									
							</link-entity>		
						</entity>
					</fetch>"""		
			
		//println "EL XML" + sqlXml
		/*<condition attribute='scor_tipodocumento' operator='between'/>
			<value>2010-07-01T00:00:00+00:00</value>
			<value>2010-07-01T00:00:00+00:00</value>
		</condition>
		*/
		
		return 	sqlXml;
	}  		

	private static ArrayList rellenaExpedientesTarificados(String xmlExpedientes){
	  	//println "A RELLENAR"
		def records = new XmlSlurper().parseText(xmlExpedientes)	
		def listaExpedientes = []
		def expediente
		def nsolicitud
		def npoliza 
		def numExpedienteAnterior = ""
		def fallecimiento 
		def invalidez
		def valoracion
		
		expediente = new ScorExpedienteTarificado ()

		records.children().each { result ->
			nsolicitud = result.scor_nsolicitud_compania
			npoliza = result.scor_npoliza
			//println "Veamos: "+ numExpedienteAnterior + " " + result.scor_npoliza
			if (numExpedienteAnterior!= result.scor_name){
				//println "son distintos"
				expediente = new ScorExpedienteTarificado ()
				
				if (nsolicitud == npoliza)
					expediente.tipoDocumento=3
				else
					expediente.tipoDocumento=2
				
				expediente.scorName = result.scor_name
				expediente.poliza = result.scor_nsolicitud_compania
				expediente.certificado = result.scor_ncertificado
				expediente.movimiento = result.scor_nsuplemento
				expediente.nif = result.'scor_candidatoid.scor_dnipasaporte'		
				expediente.id= result.scor_expedienteid
			}	
	////////////////////////////////////////////////////////////// COBERTURAS
				
			if (result.'scor_expedienteid.scor_valoracionprima'!="")
				valoracion = result.'scor_expedienteid.scor_valoracionprima'
			else
				valoracion = result.'scor_expedienteid.scor_valoracioncapital'
				
			if (result.'scor_coberturaid.scor_name'=="Fallecimiento"){
				fallecimiento = valoracion.toString().trim().toLowerCase()
				if (fallecimiento=="normal")
					expediente.dictamenFalle=1
				else 
					expediente.dictamenFalle=2						
			}
			if (result.'scor_coberturaid.scor_name'=="Invalidez"){
				invalidez =  valoracion.toString().trim().toLowerCase()
				if (invalidez=="normal")
					expediente.dictamenInva=1
				else 
					expediente.dictamenInva=2
			}							

			//println expediente.coberturas
			
			if (numExpedienteAnterior!= result.scor_name){
				//println "LOS CAMBIO"
				listaExpedientes += expediente		
				numExpedienteAnterior = result.scor_name
			}
			
		}
		//println "LISTA EXPEDIENTES:"+listaExpedientes
		
		return listaExpedientes
	}	



	private static String dameCoberturasExpediente(String idExpediente){


///////////////////// COBERTURAS
		
	def coberturas = """
				<link-entity name='scor_coberturadelexpediente' from='scor_expedienteid' to='scor_expedienteid'>
					<attribute name='scor_valoracioncapital'/>
					<attribute name='scor_valoracionprima'/>
					<attribute name='scor_valoraciontemporal'/>
					<attribute name='scor_exclusiones'/>	
					<attribute name='scor_capital'/>	
					<attribute name='scor_resultadocoberturaid'/>
					<attribute name="transactioncurrencyid" />	
					<link-entity name='scor_cobertura' from='scor_coberturaid' to='scor_coberturaid'>
						<attribute name='scor_name'/>
						<attribute name='scor_codigo'/>
					</link-entity>
				</link-entity>
				"""

		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
							$coberturas
						</entity>
					</fetch>"""		
	
		//println "EL XML" + sqlXml
		
		return 	sqlXml;
	}  		
	
	private static ScorExpedienteTarificado rellenaCoberturasExpediente(String xmlExpedientes,ScorExpedienteTarificado expediente){
	  	//println "A RELLENAR"
		def records = new XmlSlurper().parseText(xmlExpedientes)	
		expediente.garantias= []
		def ultimaCobertura = ""
		def dictamen
		def valoracion
		def exclusiones
		def descripcion
		expediente.pruebasMedicas=[] //Se inicializa aquí para luego solo añadir elementos tanto en paquetes como en servicios
		
		
		records.children().each { result ->
			if (ultimaCobertura!=(String)result.'scor_coberturaid.scor_name'){

				valoracion = result.'scor_expedienteid.scor_resultadocoberturaid'.@'name'.text()
				exclusiones= result.'scor_expedienteid.scor_exclusiones'
					
				valoracion = valoracion.trim().toLowerCase()
				if ((valoracion=="normal" || valoracion=="estándar") && exclusiones.isEmpty())
					dictamen=1
				else 
					dictamen=2
				
				descripcion = parseoCobertura ((String)result.'scor_coberturaid.scor_codigo')	
				
				expediente.garantias += new Garantias((String)result.'scor_coberturaid.scor_codigo',descripcion,dictamen.toString())
				
				ultimaCobertura=(String)result.'scor_coberturaid.scor_name'
			}
		}
		//println "LISTA EXPEDIENTES:"+listaExpedientes
		
//return listaExpedientes
	return expediente
	}	
	
private static String parseoCobertura(String codigo){  

	 if (codigo == "COB2")
	 	return "Fallecimiento accidente"
	 if (codigo == "COB3")
	 	return "Enfermedades graves"
	 if (codigo == "COB4")
	 	return "Invalidez permanente absoluta"
	 if (codigo == "COB5")
	 	return "Fallecimiento"
	 if (codigo == "COB8")
	 	return "Incapacidad 30" 
	 	
	 return ""	

}	

	private static String dameExpedientePaquete(String idExpediente){  

		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
					
							<link-entity name='scor_expediente_paquete' from='scor_expedienteid' to='scor_expedienteid'>
								<attribute name='scor_expediente_paqueteid'/>
								<attribute name='scor_name'/>
								<attribute name='createdon'/>
								<attribute name='statecode'/>
								<filter type='and'>
									<condition attribute='statecode' operator='eq' value='0'/>
								</filter>	
									<link-entity name='scor_clientepaquete' from='scor_clientepaqueteid' to='scor_clientepaqueteid'>
										<attribute name='scor_codigocompania'/>	
									</link-entity>							
							</link-entity>
						</entity>
					</fetch>"""		
				
		//println "EL XML DameExpedientePaquete" + sqlXml
		return 	sqlXml;
	}  		

	private static ScorExpedienteTarificado rellenaExpedientePaquete(String xmlPaquetes,ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlPaquetes)	

		records.children().each { result ->
			expediente.tipoRMedico=result.'scor_clientepaqueteid.scor_codigocompania'
			expediente.codTipoRMedico =  result.'scor_expedienteid.scor_expediente_paqueteid'
		}
		return expediente
	}  		
/////////////

	private static String dameServiciosPaquete(String idExpedientePaquete,String compania){  

		def sqlXml = """<fetch mapping='logical'>
							<entity name='scor_expediente_paquete'>
								<link-entity name='scor_exp_paq_serv' from='scor_expedientepaqueteid' to='scor_expediente_paqueteid'>
									<attribute name='scor_candidatoaportapruebas' />
									<attribute name='scor_expedientepaqueteid' />
									<attribute name='scor_fecha_cierre' />
									<attribute name='scor_name' />
									<attribute name='modifiedon'/>
									<filter>
										<condition attribute='scor_expedientepaqueteid' operator='eq' value='$idExpedientePaquete' />
									</filter>
									
									<link-entity name='scor_service' from='scor_serviceid' to='scor_servicioid'>
										<link-entity name='scor_clienteservicio' from='scor_servicioscor_clienteservicio_id' to='scor_serviceid'>
											<attribute name='scor_codigocompania'/>	
											<link-entity name='scor_cliente' from='scor_clienteid' to='scor_cliente_servicio_id'>
												<attribute name='scor_codigost'/>
												<filter type='and'>
													<condition attribute='scor_codigost' operator='eq' value='$compania'/>
												</filter>								
											</link-entity>												
										</link-entity>	
									</link-entity>	
																	
								</link-entity>
							</entity>
						</fetch>"""		

		//println "EL XML DameExpedientePaquete" + sqlXml
		return 	sqlXml;
	}  		

	private static ScorExpedienteTarificado rellenaServiciosPaquete(String xmlServiciosPaquetes,ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlServiciosPaquetes)
		def pruebaMedica
		def listaServicios = []	

		records.children().each { result ->
			if (!existeEnLista (listaServicios,(String)result.'scor_serviceid.scor_codigocompania')){
				pruebaMedica =  new PruebaMedica("","","","")
				if (expediente.tipoRMedico=='15' && result.'scor_serviceid.scor_codigocompania'=='05') {
					pruebaMedica.codigo = "11"
				}else{
					pruebaMedica.codigo = result.'scor_serviceid.scor_codigocompania'
				}
				pruebaMedica.fecha= result.'scor_expediente_paqueteid.modifiedon'
				pruebaMedica.descripcion = result.'scor_expediente_paqueteid.scor_name'
				            
				if (result.'scor_expediente_paqueteid.scor_candidatoaportapruebas'.@name=="No")
					pruebaMedica.aportaPruebas=false
				//if (result.'scor_expediente_paqueteid.scor_candidatoaportapruebas'.@name=="Si")
				else
					pruebaMedica.aportaPruebas=true
				
				expediente.pruebasMedicas += pruebaMedica
				
				listaServicios += result.'scor_serviceid.scor_codigocompania'
				//println "AportaPruebas:" + result.'scor_expedienteid.scor_candidatoaportapruebas'.@name
				//println "Codigo: " + result.'scor_expedienteid.scor_codigocompania'
			}
		}
		return expediente
	}  		

/////////////

	private static String dameExpedienteServicio (String idExpediente){  
			
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
					
							<link-entity name='scor_expediente_servicio' from='scor_expedienteid' to='scor_expedienteid'>
								<attribute name='scor_name'/>
								<attribute name='modifiedon'/>
								<attribute name='statecode'/>
								<attribute name='scor_candidatoaportapruebas'/>
								<filter type='and'>
									<condition attribute='statecode' operator='eq' value='0'/>
								</filter>		
								<link-entity name='scor_clienteservicio' from='scor_clienteservicioid' to='scor_compania_servicioid'>
									<attribute name='scor_codigocompania'/>	
								</link-entity>															
							</link-entity>
						</entity>
						</fetch>"""							
		//println "EL XML dameExpedienteServicio" + sqlXml
		return 	sqlXml;
	}  	

	private static ScorExpedienteTarificado rellenaExpedienteServicio (String xmlServicios,ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlServicios)
		def pruebaMedica	

		//expediente.pruebasMedicas=[]

		records.children().each { result ->

			pruebaMedica =  new PruebaMedica("","","","")
			pruebaMedica.codigo = result.'scor_compania_servicioid.scor_codigocompania'
			pruebaMedica.fecha= result.'scor_expedienteid.modifiedon'
			pruebaMedica.descripcion = result.'scor_expedienteid.scor_name'

			if (result.'scor_expedienteid.scor_candidatoaportapruebas'.@name=="No")
				pruebaMedica.aportaPruebas=false
			//if (result.'scor_expedienteid.scor_candidatoaportapruebas'.@name=="Si")
			else
				pruebaMedica.aportaPruebas=true
			
			expediente.pruebasMedicas += pruebaMedica
			//println "AportaPruebas:" + result.'scor_expedienteid.scor_candidatoaportapruebas'.@name
			//println "Codigo: " + result.'scor_expedienteid.scor_codigocompania'
		}
		return expediente
	}  	

	private static String creaXmlTarificacionLagunaro(ArrayList listadoExpedientes){  
		def expediente = new ScorExpedienteTarificado ()
		def sqlXml = """<reconocimientosMedicos>
							<fecha></fecha>
							<observaciones></observaciones>
							<expedientes>
								<expediente>
									<tipoReconocimiento></tipoReconocimiento>
								</expediente>
							</expedientes>
					</reconocimientosMedicos>"""		
		//println "EL XML" + sqlXml
		return 	sqlXml;
	}  	
	

	private static boolean existeEnLista (ArrayList lista, String elemento){
		for(int i=0; i< lista.size(); i++) {
			if (elemento == (String)lista[i]){
				return true
			}
		}
		
		return false
	
	}
	
	
	//TODO ESTO ESTA SIMULADO
	private static ScorExpedienteTarificado rellenaGarantiasSimulacion(String xmlServicios, ScorExpedienteTarificado expediente){
		def gAll=[]
		def gara1=new Garantias("codigo","Fallecimiento","50")
		def gara2=new Garantias("codigo2","Dependencia","502")
		gAll.add(gara1)
		gAll.add(gara2)
		expediente.garantias=gAll

		return expediente
	}
	
	def ArrayList rellenaExpedientesTarificadosSimulacion(String xmlExpedientes){

		
		def listaExpedientes=[]
		
		2.times{
			def expediente = new ScorExpedienteTarificado ()
			expediente.poliza = "00034"+it
			expediente.certificado = "certificado"+it
			expediente.movimiento = "movimiento"+it
			expediente.nif = 'scor_candidatoid.scor_dnipasaporte'+it
			expediente.id= '5'+it
			listaExpedientes += expediente
		}

	  
	  return listaExpedientes
  }
	
	private static ScorExpedienteTarificado rellenaExpedientePaqueteSimulacion(String xmlPaquetes,ScorExpedienteTarificado expediente){
		expediente.tipoRMedico="2"

		return expediente
	}
	
	private static ScorExpedienteTarificado rellenaExpedienteServicioSimulacion (String xmlServicios,ScorExpedienteTarificado expediente){

		expediente.pruebasMedicas=[]

		3.times {
			def pm=new PruebaMedica("Prueba médica"+it)
			expediente.pruebasMedicas += pm
		}
	
		return expediente
	}
	
}



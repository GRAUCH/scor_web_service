package hwsol.webservices

import java.util.ArrayList;
import org.springframework.web.context.request.RequestContextHolder;
import java.text.SimpleDateFormat;

class FetchUtil {

	private static String dameExpedientesTarificados(String fecha,String compania){
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		def fechaDate= formatoDelTexto.parse(fecha)
		def tiempoActual = fechaDate.getTime()
		def unDia =  24 * 60 * 60 * 1000

		Date fechaSig = new Date(tiempoActual - unDia)
		
		//println "LA FECHA" + formatoDelTexto.format(fechaSig)
		
		fecha = formatoDelTexto.format(fechaSig)
		
		//fecha = "2010-07-01"  
		//fecha = "2010-11-21"
		
		
		def fechaInicio = fecha + "T00:00:00-05:00"
		def fechaFin= fecha +"T23:59:59-05:00"
		
		//def fechaInicio= "2010-06-01" +"T00:00:00+02:00"
		//def fechaFin= "2011-11-10"+"T23:59:59+02:00"
		
		//def fechaInicio= "2010-06-01"
		//def fechaFin= "2011-11-10"


///////////////////// COBERTURAS

		def coberturas = ""
		
		coberturas = """
					<link-entity name='scor_coberturadelexpediente' from='scor_expedienteid' to='scor_expedienteid'>
						<attribute name='scor_capital'/>
						<attribute name="transactioncurrencyid" />		
						<link-entity name='scor_cobertura' from='scor_coberturaid' to='scor_coberturaid'>
							<attribute name='scor_name'/>
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
	  
		def records = new XmlSlurper().parseText(xmlExpedientes)	
		def listaExpedientes = []
		def expediente
		def nsolicitud
		def npoliza 


		records.children().each { result ->
			expediente = new ScorExpedienteTarificado ()
			
			nsolicitud = result.scor_nsolicitud_compania
			npoliza = result.scor_npoliza
			
			if (nsolicitud == npoliza)
				expediente.tipoDocumento=3
			else
				expediente.tipoDocumento=2
			
			expediente.poliza = result.scor_nsolicitud_compania
			expediente.certificado = result.scor_ncertificado
			expediente.movimiento = result.scor_nsuplemento
			expediente.nif = result.'scor_candidatoid.scor_dnipasaporte'		

			expediente.id= result.scor_expedienteid

			def fallecimiento 
			def invalidez 

	////////////////////////////////////////////////////////////// COBERTURAS
				def valoracion
				
				
				if (result.scor_coberturacontratada_1.toString()=="1"){
						
					if (result.scor_valoracionprima_1!="")
						valoracion = result.scor_valoracionprima_1
					else
						valoracion = result.scor_valoracioncapital_1
						
					if (result.'scor_cobertura1id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura1id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}							

						
				}
	
				if (result.scor_coberturacontratada_2.toString()=="1") {
					if (result.'scor_valoracionprima_2'!="")	
						valoracion = result.scor_valoracionprima_2
					else
						valoracion = result.scor_valoracioncapital_2
						
					if (result.'scor_cobertura2id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura2id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				
				if (result.scor_coberturacontratada_3.toString()=="1"){
						
					if (result.scor_valoracionprima_3!="")
						valoracion = result.scor_valoracionprima_3
					else
						valoracion = result.scor_valoracioncapital_3
						
					if (result.'scor_cobertura3id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura3id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
							
				if (result.scor_coberturacontratada_4.toString()=="1"){
						
					if (result.scor_valoracionprima_4!="")
						valoracion = result.scor_valoracionprima_4
					else
						valoracion = result.scor_valoracioncapital_4
						
					if (result.'scor_cobertura4id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura4id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				if (result.scor_coberturacontratada_5.toString()=="1"){
						
					if (result.scor_valoracionprima_5!="")
						valoracion = result.scor_valoracionprima_5
					else
						valoracion = result.scor_valoracioncapital_5
						
					if (result.'scor_cobertura5id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura5id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				if (result.scor_coberturacontratada_6.toString()=="1"){
						
					if (result.scor_valoracionprima_6!="")
						valoracion = result.scor_valoracionprima_6
					else
						valoracion = result.scor_valoracioncapital_6
						
					if (result.'scor_cobertura6id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura6id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				if (result.scor_coberturacontratada_7.toString()=="1"){
						
					if (result.scor_valoracionprima_7!="")
						valoracion = result.scor_valoracionprima_7
					else
						valoracion = result.scor_valoracioncapital_1
						
					if (result.'scor_cobertura7id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura7id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				
				if (result.scor_coberturacontratada_8.toString()=="1") {
						
					if (result.scor_valoracionprima_8!="")
						valoracion = result.scor_valoracionprima_8
					else
						valoracion = result.scor_valoracioncapital_8
						
					if (result.'scor_cobertura8id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura8id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				
				if (result.scor_coberturacontratada_9.toString()=="1"){
						
					if (result.scor_valoracionprima_9!="")
						valoracion = result.scor_valoracionprima_9
					else
						valoracion = result.scor_valoracioncapital_9
						
					if (result.'scor_cobertura9id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura9id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
				
				if (result.scor_coberturacontratada_10.toString()=="1"){
						
					if (result.scor_valoracionprima_10!="")
						valoracion = result.scor_valoracionprima_10
					else
						valoracion = result.scor_valoracioncapital_10
						
					if (result.'scor_cobertura10id'.@name=="Fallecimiento"){
						fallecimiento = valoracion.toString().trim().toLowerCase()
						if (fallecimiento=="normal")
							expediente.dictamenFalle=1
						else 
							expediente.dictamenFalle=2						
					}
					if (result.'scor_cobertura10id'.@name=="Invalidez"){
						invalidez =  valoracion.toString().trim().toLowerCase()
						if (invalidez=="normal")
							expediente.dictamenInva=1
						else 
							expediente.dictamenInva=2
					}	
				}
/*					
				if (result.'scor_cobertura11id'.@name=="Fallecimiento" || result.'scor_cobertura11id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_11!="")
						valoracion = result.scor_valoracionprima_11
					if (result.'scor_valoracioncapital_11'!="")
						valoracion = result.scor_valoracioncapital_11
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura11id'.@name,(String)result.scor_capital_11,(String)valoracion)
				}
			
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_12!="")
						valoracion = result.scor_valoracionprima_12
					if (result.'scor_valoracioncapital_12'!="")
						valoracion = result.scor_valoracioncapital_12
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura12id'.@name,(String)result.scor_capital_12,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_13!="")
						valoracion = result.scor_valoracionprima_13
					if (result.'scor_valoracioncapital_13'!="")
						valoracion = result.scor_valoracioncapital_13
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura13id'.@name,(String)result.scor_capital_13,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_14!="")
						valoracion = result.scor_valoracionprima_14
					if (result.'scor_valoracioncapital_14'!="")
						valoracion = result.scor_valoracioncapital_14
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura14id'.@name,(String)result.scor_capital_14,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_15!="")
						valoracion = result.scor_valoracionprima_15
					if (result.'scor_valoracioncapital_15'!="")
						valoracion = result.scor_valoracioncapital_15
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura15id'.@name,(String)result.scor_capital_15,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_16!="")
						valoracion = result.scor_valoracionprima_16
					if (result.'scor_valoracioncapital_16'!="")
						valoracion = result.scor_valoracioncapital_16
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura16id'.@name,(String)result.scor_capital_16,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_17!="")
						valoracion = result.scor_valoracionprima_17
					if (result.'scor_valoracioncapital_17'!="")
						valoracion = result.scor_valoracioncapital_17
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura17id'.@name,(String)result.scor_capital_17,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_18!="")
						valoracion = result.scor_valoracionprima_18
					if (result.'scor_valoracioncapital_18'!="")
						valoracion = result.scor_valoracioncapital_18
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura18id'.@name,(String)result.scor_capital_18,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_19!="")
						valoracion = result.scor_valoracionprima_19
					if (result.'scor_valoracioncapital_19'!="")
						valoracion = result.scor_valoracioncapital_19
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura19id'.@name,(String)result.scor_capital_19,(String)valoracion)
				}
				
				if (result.'scor_cobertura2id'.@name=="Fallecimiento" || result.'scor_cobertura2id'.@name=="Invalidez"){
						
					if (result.scor_valoracionprima_20!="")
						valoracion = result.scor_valoracionprima_20
					if (result.'scor_valoracioncapital_20'!="")
						valoracion = result.scor_valoracioncapital_20
						
					expediente.coberturas += new Coberturas((String)result.'scor_cobertura20id'.@name,(String)result.scor_capital_20,(String)valoracion)
				}
*/		
				
				//println expediente.coberturas
	/////////////////////////////////////////////////////////////////////////		

			
			//Dictamenes Antiguos antes del cambio de cobuertura
			/*
			def fallecimiento = result.scor_coberturadefallecimientovaloracion.toString().trim().toLowerCase()
			def invalidez = result.scor_coberturadeinvalidezvaloracion.toString().trim().toLowerCase()
		
			if (result.scor_coberturadefallecimiento.toString()=="1"){
				if (fallecimiento=="normal")
					expediente.dictamenFalle=1
				else 
					expediente.dictamenFalle=2
			}
			
			if (result.scor_coberturadeinvalidez.toString()=="1"){				
				if (invalidez=="normal")
					expediente.dictamenInva=1
				else 
					expediente.dictamenInva=2				
			}
			*/

		
			///

			listaExpedientes += expediente		
			
		}
		//println "LISTA EXPEDIENTES:"+listaExpedientes
		
		return listaExpedientes
	}	

	private static String dameExpedientePaquete(String idExpediente){  
		/* FASE 1
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
										<attribute name='scor_codigost'/>	
									</link-entity>							
							</link-entity>
						</entity>
					</fetch>"""		
		*/			
		/* BUENO*/
		
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
		/**/
					

		return 	sqlXml;
	}  		

	private static ScorExpedienteTarificado rellenaExpedientePaquete(String xmlPaquetes,ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlPaquetes)	

		records.children().each { result ->
			
			/* FASE 1
			//println "DENTRO RELLENAEXPEDPAQUETE FOR"

			//////////////////////////////////////////////////
			/// APAÑO PARA CONECTARSE AL CRM 1
			//println "RELLENAEXPEDIENTEtipoRMedico"+result.'scor_clientepaqueteid.scor_codigost'
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000029")
				expediente.tipoRMedico="02"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000030")
				expediente.tipoRMedico="05"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000033")
				expediente.tipoRMedico="06"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000031")
				expediente.tipoRMedico="06"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000034")
				expediente.tipoRMedico="09"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000032")
				expediente.tipoRMedico="09"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000035")
				expediente.tipoRMedico="11"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000036")
				expediente.tipoRMedico="05"		
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000116")
				expediente.tipoRMedico="14"																																		
													
			*/
		
			/////////////////////////////////////////////////


			
			expediente.tipoRMedico=result.'scor_clientepaqueteid.scor_codigocompania'
			//expediente.tipoRMedico=result.'scor_clientepaqueteid.scor_codigost'


		}
		return expediente
	}  		

	private static String dameExpedienteServicio (String idExpediente){  
		/* FASE 1
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
					
							<link-entity name='scor_expediente_servicio' from='scor_expedienteid' to='scor_expedienteid'>
								<attribute name='scor_name'/>
								<attribute name='createdon'/>
								<attribute name='statecode'/>
								<filter type='and'>
									<condition attribute='statecode' operator='eq' value='0'/>
								</filter>		
								<link-entity name='scor_clienteservicio' from='scor_clienteservicioid' to='scor_compania_servicioid'>
									<attribute name='scor_codigost'/>	
								</link-entity>															
							</link-entity>
						</entity>
						</fetch>"""		
		*/				
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
					
							<link-entity name='scor_expediente_servicio' from='scor_expedienteid' to='scor_expedienteid'>
								<attribute name='scor_name'/>
								<attribute name='createdon'/>
								<attribute name='statecode'/>
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

		expediente.pruebasMedicas=[]

		records.children().each { result ->
			/* FASE 1
			//////////////////////////////////////////////////
			/// APAÑO PARA CONECTARSE AL CRM 1

			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000091")
				expediente.tipoRMedico="01"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000092")
				expediente.tipoRMedico="02"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000151")
				expediente.tipoRMedico="03"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000094")
				expediente.tipoRMedico="04"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000099")
				expediente.tipoRMedico="08"	
			if (result.'scor_clientepaqueteid.scor_codigost'.toString()=="000109")
				expediente.tipoRMedico="13"																					
			*/
	
			/////////////////////////////////////////////////
			//??????
			//expediente.tipoRMedico = result.'scor_compania_servicioid.scor_codigocompania'
			expediente.pruebasMedicas += result.'scor_compania_servicioid.scor_codigocompania'

		}
		return expediente
	}  	


	private static String dameServicios(String idExpediente,String compania){  
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<filter type='and'>
								<condition attribute='scor_expedienteid' operator='eq' value='$idExpediente'/>
							</filter>
							<link-entity name='scor_expediente_servicio' from='scor_expedienteid' to='scor_expedienteid'>
								<attribute name='scor_name'/>
								<attribute name='createdon'/>
								<attribute name='statecode'/>
								<filter type='and'>
									<condition attribute='statecode' operator='eq' value='0'/>
								</filter>								
							</link-entity>
						</entity>
						</fetch>"""		
		//println "EL XML" + sqlXml
		return 	sqlXml;
	}  

	private static ScorExpedienteTarificado rellenaServicios(String xmlServicios, ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlServicios)	

		expediente.pruebasMedicas=[]

		records.children().each { result ->
			expediente.pruebasMedicas += "codigos"
			//println "SERVICIO:"+expediente.servicios
		}
		return expediente
	}

	private static String damePaquetes(String idExpediente,String compania){  
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
							</link-entity>
						</entity>
					</fetch>"""		
		
		return 	sqlXml;
	}  		

	private static ScorExpedienteTarificado rellenaPaquetes(String xmlPaquetes, ScorExpedienteTarificado expediente){  
		def records = new XmlSlurper().parseText(xmlPaquetes)	

		records.children().each { result ->

			expediente.tipoRMedico= "convertirCodigo"

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

		return 	sqlXml;
	}  	


	private static String dameServiciosTestManagment(String fecha,String hora, String compania){


		def fechaHoy = new Date().format('yyyy-MM-dd')
		Calendar fecha2 = Calendar.getInstance();
		def zone = fecha2.getTime().format('Z').substring(0, 3)
		fecha2.add(Calendar.MINUTE , -11) //Para evitar condiciones de carrera se mira 20 minutos antes
		
		def minFin = fecha2.getTime().format('mm')
		def horaFin =fecha2.getTime().format('HH')	
		def fechaFinal = fecha2.getTime().format('yyyy-MM-dd')
		

		
		fecha2.add(Calendar.MINUTE , -9)
		def minIni = fecha2.getTime().format('mm')
		def horaIni =fecha2.getTime().format('HH')			
		def fechaIni = fecha2.getTime().format('yyyy-MM-dd')
	
		def fechaInicio= fechaIni+"T"+horaIni+":"+minIni+ ":00"+ zone +":00"
		def fechaFin= fechaFinal+"T"+horaFin+":"+minFin+ ":59"+ zone +":00"


		/*
		def fechaInicio = fecha + "T"+hora+":00:00+02:00"
		def fechaFin= fecha +"T"+hora+":59:59+02:00"		
				
		fechaInicio= "2013-04-09" +"T00:01:00+01:00"
		fechaFin= "2013-04-10"+"T19:29:59+01:00"
		*/
		//fechaInicio = fecha + "T00:00:00+02:00"
		//fechaFin= fecha +"T23:59:59+02:00"		


	
		def sqlXml = """<fetch mapping='logical'>
						<entity name='scor_expediente'>
							<attribute name='scor_name'/>
							<attribute name='scor_npoliza'/>
							<attribute name='scor_nsuplemento'/>
							<attribute name='scor_nsolicitud_compania'/>
							<attribute name='scor_ncertificado'/>
							<attribute name='scor_nsubpoliza'/>
							<attribute name='scor_estado'/>
							<filter type='and'>
								<condition attribute='scor_estado' operator='ne' value='10'/>
								<condition attribute='scor_estado' operator='ne' value='11'/>
							</filter>								
							<link-entity name='contact' from='contactid' to='scor_candidatoid' link-type='natural'>
								<attribute name="scor_dnipasaporte" />
								<link-entity name='scor_cliente' from='scor_clienteid' to='scor_candidatosid'>
									<filter type='and'>
										<condition attribute='scor_codigost' operator='eq' value='$compania'/>
									</filter>								
								</link-entity>									
							</link-entity>									
				            <link-entity name="scor_scor_expediente_scor_ordendetrabajo" from="scor_expedienteid" to="scor_expedienteid" link-type="natural">
				                  <link-entity name="scor_ordendetrabajo" from="scor_ordendetrabajoid" to="scor_ordendetrabajoid">
				                  		<attribute name="createdon" />
											<filter type='and'>
												<condition attribute='createdon' operator='between'>
													<value>$fechaInicio</value>
													<value>$fechaFin</value>
												</condition>									
											</filter>			
											<filter type='or'>
												<condition attribute='scor_estado' operator='eq' value='1'/>								
												<condition attribute='scor_estado' operator='eq' value='2'/>
											</filter>												                  		
				                        <link-entity name="scor_servicio_ot" from="scor_ordendetrabajoid" to="scor_ordendetrabajoid">
				                             <attribute name="scor_name" />
						                        <link-entity name="scor_proveedorservicio" from="scor_proveedorservicioid" to="scor_proveedor_servicioid">
						                        	<attribute name="scor_codigost" />
							                        <link-entity name="scor_service" from="scor_serviceid" to="scor_servicio_proveedor_id">
							                             <attribute name="scor_descripcion" />
							                        </link-entity>	
						                        </link-entity>	
				                        </link-entity>		
				                  </link-entity>		
				            </link-entity>
						</entity>

					
					</fetch>"""		
		
		return 	sqlXml;
	}  		
/*
Después del filtro de fecha
											<filter type='and'>
												<condition attribute='scor_estado' operator='eq' value='1'/>								
											</filter>	

				            <link-entity name="scor_scor_expediente_scor_ordendetrabajo" from="scor_expedienteid" to="scor_expedienteid" link-type="natural">
				
				                  <link-entity name="scor_ordendetrabajo" from="scor_ordendetrabajoid" to="scor_scor_expediente_scor_ordendetrabajoid">
				                  	
				                  		<attribute name="createdon" />
										<filter type='and'>
											<condition attribute='createdon' operator='between'>
												<value>$fechaInicio</value>
												<value>$fechaFin</value>
											</condition>									
										</filter>				                  		
				
				                        <link-entity name="scor_servicio_ot" from="scor_ordendetrabajoid" to="scor_ordendetrabajoid">
				                             <attribute name="createdon" />
				                             <attribute name="scor_codigost" />
				                             <attribute name="scor_name" />
				                        </link-entity>
				
				                  </link-entity>
				
				            </link-entity>
*/
	
}



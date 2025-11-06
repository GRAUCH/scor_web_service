package com.ws.servicios
import grails.util.Holders
import java.util.Date
import com.scortelemed.Conf
import com.scortelemed.Request
import com.scortelemed.ConfFichero
import com.scortelemed.Estadistica

class RecepcionFicheroLagunaroService {
	def logginService
	/*
	 * Los registros a dia 23-11-2010 ocupan por linea 621 chars.
	 */

	def guardarDatos(datosGuardar) {
		//Cabecera//
		def lote = ConfFichero.findByName("contador_fichero")
		lote.value = lote.value.toInteger() + 1
		lote.save()
		def fechaActual = new Date().formatoAnglo()
		String espC = " "*609
		//OLD String espC = " "*578
		def cabecera = lote.value + fechaActual + "01" + "LAGU" + espC + "\r\n"

		String todo = cabecera
		String errores = ""
		datosGuardar.each {
			todo = todo + it.value[0]
			if(it.value[1] != 'sinError') errores =  errores + it.value[1]
		}
		
		int contador = 0
		
		todo.eachLine { contador++ }
		//pie//
		def numRegistros = contador - 1
		def numFilas = contador + 1
		String espP = " "*628
		//OLD String espP = " "*585
		
		def pie = "6" + numRegistros.toString() + numFilas.toString() + espP

		todo = todo + pie
		def conseguido = true
		if(numRegistros != 0) {
			// GUARDA EL FICHERO FISICO
			def ran = new Random()
			def prefijoFicheros = Conf.findByName('prefijoFicheros')
			String nombreFichero = "LAGU-"+lote.value + "-" + fechaActual + "-LAGU-" + ran.nextInt(99999+1) + prefijoFicheros.value.toString()
			
			try {
				def carpetaFicheros = Conf.findByName('carpetaFicheros')
				def fichero= new File(carpetaFicheros.value.toString() + nombreFichero)
				fichero.createNewFile()
				fichero.append(todo)
			} catch (e) {
				logginService.putError("guardarDatos","No se ha podido gaurdar los datos: "+e )
				conseguido = false
			}

		} else { conseguido = false }

		[todo, errores, conseguido]
	}

	def preparar(peticion) {
		try{
			def req = Request.findByClaveProcesoLike(peticion)
			if(req.descartado == false) {
				def estadistica
				def esta = [:]
				
				estadistica = Estadistica.findAllByRequest(req)
				estadistica.each { esta.put(it.clave, it.value) }

				def solicitud
				def estado
				
				if(esta.poliza_tipo_documento == "2") { 
					solicitud = "SOLICITUD" 
				} 
				
				else if(esta.poliza_tipo_documento == "3") {
					solicitud = esta.poliza_cod_poliza 
				}
				
				if(esta.datos_envio_movimiento == "A") { estado = "N" } 
				else if(esta.datos_envio_movimiento == "B") { estado = "C" } // Antes estaba a M se cambia 1/12/2010
				
				def apellidos = esta.asegurado_apellido1 + " " + esta.asegurado_apellido2
				def observaciones = new StringBuilder(esta.datos_envio_observaciones==null?"":esta.datos_envio_observaciones)
				observaciones.append(esta.poliza_texto_informativo==null?"":esta.poliza_texto_informativo)
				
				def datosPeti = [esta.poliza_producto, solicitud, esta.poliza_cod_poliza, esta.poliza_certificado, esta.poliza_movimiento, ffecha(esta.poliza_fecha_efecto),
						 ffecha(esta.datos_envio_fecha_envio), " ", " ", estado, esta.asegurado_nombre, apellidos, esta.asegurado_direccion, esta.asegurado_codigo_postal,
						 esta.asegurado_localidad, esta.asegurado_provincia, esta.asegurado_telefono1, esta.asegurado_telefono2, " ", esta.asegurado_email,
						 ffecha(esta.asegurado_fecha_nacimiento), " ", esta.asegurado_nif, esta.agente_agente, esta.agente_descripcion, esta.agente_telefono,
						 esta.poliza_capital_fallecimiento, esta.poliza_cobertura_invalidez, esta.poliza_capital_invalidez, esta.asegurado_sexo,
						 esta.asegurado_estado_civil, "N", esta.asegurado_hora_contacto, observaciones.toString()]
				
				def tipo, codigo, descrip
				def arraySer
				def partido
				def servicios = []
				def vienePrueba = false
				def vieneTipo = false
				esta.each {
					if(it.key =~ /reconocimiento_medico_/){
						if(it.key =~ /reconocimiento_medico_codigo_prueba_medica/) {
							tipo = "S"
							codigo = it.value
							partido = it.key.split("#")
							descrip = "reconocimiento_medico_descripcion#" + partido[1]
							arraySer = [tipo, codigo, esta.get(descrip)]
							servicios.add(arraySer)
						}
						if(it.key =~ /reconocimiento_medico_tipo_reconocimiento_medico/) {
							tipo = "P"
							codigo = it.value
							arraySer = [tipo, codigo, '']
							servicios.add(arraySer)
						}
					}
				}
				
				// Generacion de clase FICHERO
				def datosPeticion = new hwsol.webservices.RecepcionFicheroLagunaroClass()
				datosPeticion.putProceso(peticion)
				datosPeticion.putDatos(datosPeti)
				datosPeticion.putServicios(servicios)
				datosPeticion.putCoberturas(esta.datos_capital)
				
				if(!datosPeticion.tieneErrores()) {
					req.errores = null
					
					return [datosPeticion.getAll(), "sinError", ""]
					//return [datosPeticion.getAll(), "<li>Sin errores para proceso: <b>" + req.claveProceso + "</b></li>", ""]
				} else {
					req.errores = datosPeticion.getErroresString()
					return ["", datosPeticion.getErroresString(), peticion]
				}
			}
		}catch (Exception e){
			return ["", "No hay datos para tramitar en peticion: "+peticion+"\n", peticion]
		}
	}
	
	def procesarPeticion(peticion) {
		def req = Request.findByClaveProcesoLike(peticion)
		if(req) {
			def fecha = new Date()
			req.fecha_procesado = fecha
			req.save()
		}
	}
	
	def ffecha(String fecha) {
		return fecha.getAt(0..9).replaceAll("-","")
	}
}
package com.ws.servicios

import java.text.SimpleDateFormat

import com.scortelemed.Conf
import com.scortelemed.ConfFichero
import com.scortelemed.Estadistica
import com.scortelemed.Request
import com.ws.enumeration.DatosCoberturasCajamarEnum

class FicheroCajamarService {
	def final String TIPO_DEPORTE = "D"
	def final String TIPO_ENFERMEDAD = "E"
	def logginService
	def ficheroService
	
		def guardarDatos(datosGuardar,nombreCompaniaAbreviado) {
			String todoFinal=""
			//PROCESAMOS DATOS DE LA CABEZERA
			String todo = procesarCabezeraFichero(nombreCompaniaAbreviado)
			String errores = ""
			String nombreFichero 
			def numeroRegistros = 0
			def peticiones=[]
			datosGuardar.each {
				nombreFichero = it.value[0]
				if(it.value[2].length() > 0 && it.value[2].contains("Error")) {
					errores =  errores + it.value[2]
				}else{
					todo = todo + it.value[1]
					numeroRegistros = numeroRegistros + it.value[3]
					peticiones.add(it.key)
				}
			}
			
			//PROCESAMOS EL PIE DE PAGINA
			def procesadoPie = procesarPieDePagina(numeroRegistros)
			todo = todo + procesadoPie[0]
			errores = errores+procesadoPie[1]
	
			def conseguido = true
			//		if(errores.length() < 1 ) {
			try {
				todoFinal = todoFinal + todo
				//SE VA A GUARDAR EL FICHERO
				def carpetaFicheros = Conf.findByName('carpetaFicheros')
				def fichero = new File(carpetaFicheros.value.toString() + nombreFichero)
				fichero.createNewFile()
				fichero.append(todo)
	
				//SI EL FICHERO SE PROCESO SE ACTUALIZA REQUEST
				errores = errores + procesarPeticion(peticiones)
			} catch (e) {
				logginService.putError("guardarDatos","Creacion de archivo fallida: "+e)
				conseguido = false
			}
	
			//		} else { conseguido = false }
	
			[todoFinal,	errores,conseguido]
	}

	def procesarCabezeraFichero(compania){
		try{
			SimpleDateFormat formato = new SimpleDateFormat("yyyymmdd");
			
			//OBTENEMOS EL SIGUIENTE ID DE FICHERO
			def lote = ConfFichero.findByName("contador_fichero")
			lote.value = lote.value.toInteger() + 1
			lote.save()
					
			//OBTENEMOS LA FECHA
			def fechaActual=new Date().formatoAnglo()
			String blancos = " "*598
			
			return "1"+lote.value + fechaActual + "01" + compania + blancos + "\r\n"
		}catch (Exception e){
			logginService.putError("procesarCabezeraFichero","Error al crear la cabezera del fichero: "+e)
		}
		
	}
	
	def procesarNombreFichero(compania){
		def lote = ConfFichero.findByName("contador_fichero")
		def ran = new Random()
		def fechaActual=new Date().formatoAnglo()
		def prefijoFicheros = Conf.findByName('prefijoFicheros')
		def nombreArchivo 
		
		if (compania.equals("ALPT")) {
			nombreArchivo = "ALP-"+lote.value + "-" + fechaActual + "-" +compania +"-" + ran.nextInt(99999+1) + prefijoFicheros.value.toString()
		} else {
			nombreArchivo = compania + "-" +lote.value + "-" + fechaActual + "-" +compania +"-" + ran.nextInt(99999+1) + prefijoFicheros.value.toString()
		}
		
		return nombreArchivo
	}
	
	def procesarDatosFichero(mapFinal,peticion,datosEnum){
		def cadena = "2"
		def error = ""
		def cont = 0
		
		try{
			datosEnum.each{
				def blancos=" "*it.tamanio
				if(mapFinal.get(it.valor)){
					def procesado = procesarLineaFichero (mapFinal,it,peticion)
					cadena = cadena.concat(procesado[0])
					if(procesado[1].length()>0){
						if(cont>0){
							error = error+procesado[1]
						}else{
							error = peticion+":<br>"+procesado[1]
							cont++
						}
					}
				}else{
					cadena=cadena.concat(blancos)
				}
			}
			
			cadena = cadena.concat("\r\n")
			return [cadena, error, 1]
		}catch (Exception e){
			cadena=""
			error="Error al procesar los datos del fichero: " + e.getMessage()
			return [cadena, error]
		}	
	}
	
	def procesarRespuestasFichero(mapFinal, peticion, tipo){
		def cadena = ""
		def error = ""
		def numeroRegistros = 0
		
		try{
			mapFinal.each{
				def valor = it.value.split("==")
				
				if (valor && valor.size() == 2) {
					cadena = cadena.concat("3  ").concat(tipo).concat(valor[1]).concat("\r\n")
				}
				
				numeroRegistros++
			}
			
			return [cadena, error, numeroRegistros]
		}catch (Exception e){
			cadena=""
			error="Error al procesar las respuestas del fichero: "+e.getMessage()
			
			return [cadena,error]
		}	
	}
	
	def procesarCoberturasFichero(mapBenefit,peticion){
		def cadena = ""
		def error = ""
		def numeroRegistros=0
		
		try{		
			//RECIBIMOS ALGO ASÍ: benefit_name=FALLECIMIENTO##benefit_capital=500.00
			mapBenefit.each{
				//TODO
				//CUANDO SEPAMOS QUE HACER CON EL CODIGO ESTO SE MODIFICARA
				cadena=cadena.concat("5  ")
				
				def mapFinal = [:]
				def valor = separadorAlmohadilla(it.value)
				valor.each{
					mapFinal.put(it.split("==")[0], it.split("==")[1])
				}
				
				//EMPEZAMOS A RELLENAR LAS LINEAS CON LAS COBERTURAS
				DatosCoberturasCajamarEnum.each {				
					if(mapFinal.get(it.valor)) {
						//DEVUELVE [CADENA,ERROR]
						def procesado = procesarLineaFichero (mapFinal,it,peticion)									
						cadena = cadena.concat(procesado[0])
					}else{
						def blancos = " " * it.tamanio
						cadena = cadena.concat(blancos)
					}
				}
				
				cadena=cadena.concat("\r\n")
				numeroRegistros++
			}
			
			return [cadena, error, numeroRegistros]
		}catch (Exception e){
			cadena=""
			error="Error al procesar las coberturas del fichero: "+e.getMessage()
			logginService.putError("procesarCoberturasFichero",error)
			return [cadena,error]
		}
	}
	
	def procesarPieDePagina(numRegistros){
		def cadena=""
		def error=""
		
		try{
			def caracteresNumRegistro=String.valueOf(numRegistros).length()
			def numRegistrosSinCabezera=aniadirCeroALaIzquierda(String.valueOf(numRegistros),7-caracteresNumRegistro)
			def numRegistrosConCabezera=aniadirCeroALaIzquierda(String.valueOf(numRegistros+2),8-caracteresNumRegistro)
			def blancos=" "*605
			
			cadena="9"+numRegistrosSinCabezera+numRegistrosConCabezera + blancos + "\r\n"	
			
			return [cadena,error]
		}catch (Exception e){
			cadena=""
			error="Error al procesar pie de página del fichero: "+e.getMessage()
			logginService.putError("procesarPieDePagina",error)
			return [cadena,error]
		}
	}
	
	def preparar(peticion) {
		try{
			def req = Request.findByClaveProcesoLike(peticion)
			def ficheroFinal=""
			if(req.descartado == false) {
				def estadistica
				def mapAux = [:]
				def mapFinal = [:]
				def mapBenefit = [:]
				def mapSport = [:]
				def mapIllnes = [:]
				def error
				
				estadistica = Estadistica.findAllByRequest(req)
				estadistica.each { mapAux.put(it.clave, it.value) }

				mapAux.each{
					if(it.key.equals("deportType")){
						//SEPARAMOS ALGO ASÍ: question_code=DI##data_type=1##answer=Respuesta 1%%question_code=DI2##data_type=2##answer=Respuesta 2
						def valores = separadorPorcentaje(it.value)
						def cont=0
						valores.each{
							mapSport.put("DEP"+cont,it)
							cont++
						}
					}else if(it.key.equals("cobertType")){
						//SEPARAMOS ALGO ASÍ: benefit_name=FALLECIMIENTO##benefit_capital=500.00%%benefit_name=DEPENDENCIA##benefit_capital=800.00%%
						def valores = separadorPorcentaje(it.value)
						def cont=0
						valores.each{
							mapBenefit.put("COB"+cont,it)
							cont++
						}
					}else if(it.key.equals("enfermType")){
						//SEPARAMOS ALGO ASÍ: benefit_name=FALLECIMIENTO##benefit_capital=500.00%%benefit_name=DEPENDENCIA##benefit_capital=800.00%%
						def valores = separadorPorcentaje(it.value)
						def cont=0
						valores.each{
							mapIllnes.put("ENF"+cont,it)
							cont++
						}
					} else if(it.key.equals("ffnaci")){
						//SEPARAMOS ALGO ASÍ: aanccl==1987##mmnccl==05##ddnccl==23
						def valores = separadorAlmohadilla(it.value)
						
						valores.each{
							//PRIMERO SEPARAMOS =
							def valor = it.split("==")
							
							if (valor && valor.size() == 2) {	
								mapFinal.put(valor[0],valor[1])
							}
						}
					} else {
						//SEPARAMOS ALGO ASÍ: record=A##date=2002-05-30T09:00:00##comments=Comentarios
						//PRIMERO SEPARAMOS LAS ##  => record=A
						def valores=separadorAlmohadilla(it.value.replace('%%','##'))
						valores.each{
							//PRIMERO SEPARAMOS =
							def valor = it.split("==")
							if(it.split("==").size()==1){
								mapFinal.put(valor,"")
							}else{
								mapFinal.put(valor[0],valor[1])
							}
						}
					}
				}
				
				def nombrePeticion=peticion.split("#")[0]
				def errores = ""
				def numeroRegistros = 0

				//PROCESAMOS DATOS DEL FICHERO
				def datosFile = ficheroService.procesarDatosFichero(mapFinal, peticion, ficheroService.obtenerDatosEnum(nombrePeticion))
				ficheroFinal = ficheroFinal + datosFile[0]
				errores = errores.concat(datosFile[1])
				
				//PROCESAMOS DEPORTES
				def deportesFile = procesarRespuestasFichero(mapSport, peticion, TIPO_DEPORTE)
				ficheroFinal = ficheroFinal + deportesFile[0]
				
				//PROCESAMOS ENFERMEDADES
				def enfermedadesFile = procesarRespuestasFichero(mapIllnes, peticion, TIPO_ENFERMEDAD)
				ficheroFinal = ficheroFinal + enfermedadesFile[0]
				
				//PROCESAMOS DATOS DE LAS COBERTURAS
				def coberturasFile = procesarCoberturasFichero(mapBenefit,peticion)
				ficheroFinal = ficheroFinal + coberturasFile[0]
				errores = errores.concat(coberturasFile[1])
				
				//OBTENEMOS EL NUMERO DE REGISTROS POR CADA REQUEST
				numeroRegistros = datosFile[2] + deportesFile[2] + enfermedadesFile[2] + coberturasFile[2]

				//RETORNAMOS NOMBRE DEL FICHERO, DATOS DEL FICHERO Y ERRORES SI TUVIERA
				return [procesarNombreFichero(nombrePeticion.substring(0,4).toUpperCase()),	ficheroFinal, errores, numeroRegistros]
			}
		}catch (Exception e){
			logginService.putError("preparar","Error: No hay datos para tramitar en peticion: "+peticion+"\n")
			return [null,null,"Error: No hay datos para tramitar en peticion: "+peticion+"\n",null]
		}
	}
	
	def procesarLineaFichero (mapFinal,enumerado,peticion){
		def blancos
		def cadena=""
		def error=""
		def valor = enumerado.valor
		def tamanioMaximo = enumerado.tamanio
		
		def valorF=mapFinal.get(valor)
		def longitudValor=valorF.trim().length()
		def diferenciaBlancos=tamanioMaximo-longitudValor
			
		//SI ES CAPITAL SE TIENE QUE TRATAR
		if(valor.equals("benefit_capital")){
			longitudValor=valorF.trim().replaceAll("\\.","").replaceAll(",","").length()
			valorF=procesarCapital(valorF,tamanioMaximo)
		}
		
		//SE TRATA EL TIPO DE DOCUMENTO
		if(valor.equals("birth_date")){
			valorF = valorF.replaceAll("-","")
			valorF = valorF.split("\\+")[0]
			longitudValor=valorF.trim().length()
			diferenciaBlancos=tamanioMaximo-longitudValor
		}
		
		//SE TRATA EL TIPO DE DOCUMENTO
		if(valor.equals("document_type")){
			if(valorF.equals("2")) {
				valorF = "SOLICITUD"
				diferenciaBlancos=tamanioMaximo-valorF.trim().length()
			}
		}
		
		if(longitudValor <= tamanioMaximo){
			blancos=" "*diferenciaBlancos
			cadena=cadena.concat(valorF.trim()+blancos)
		}else{
			valorF = valorF.substring(0, tamanioMaximo)
			cadena = cadena.concat(valorF.trim())
			error = error.concat("Error -> El "+valor+" es mayor de "+ tamanioMaximo+". <br>")
		}

		return [cadena,error]
	}
	
	
	def procesarCapital(valor,tamanioMaximo) {
		try{
			def valorFinal
			if(!valor.contains(',') && !valor.contains('.')){
				valorFinal=valor.concat("00")
			}else{
				valorFinal=valor.replaceAll("\\.",",")
				def aux=valorFinal.split(",")
				def numeroDecimales=""

				//AQUI RECOGEMOS SOLO LOS 2 PRIMEROS DECIMALES
				if(aux[1].length()>=2){
					numeroDecimales=aux[1].substring(0, 2)
					valorFinal=aux[0].concat(numeroDecimales)
				}else{
					//SI SOLO HAY UN DECIMAL AÑADIMOS LOS 0 RESTANTES
					def diferencia=2-aux[1].length()
					numeroDecimales=aux[1].concat(aniadirCeroALaDerecha(numeroDecimales,diferencia))
				}

				valorFinal=aux[0].concat(numeroDecimales)
			}

			def diferencia=tamanioMaximo-valorFinal.length()
			def numerosFaltantes=""
			(1..diferencia).each{
				numerosFaltantes=numerosFaltantes.concat("0")
			}
			
			return numerosFaltantes.concat(valorFinal)
			
		}catch (Exception e){
			logginService.putError("procesarCapital","Error al procesar el capital con valor "+valor+": "+e )
		}
	}
	
	def procesarPeticion(peticiones) {
		def error=""
		try{
			peticiones.each{
				def req = Request.findByClaveProcesoLike(it)
				if(req) {
					def fecha = new Date()
					req.fecha_procesado = fecha
					req.save()
				}
			}
		}catch(Exception e){
			error="Error en el metodo en procesar petición"+e
			logginService.putError("procesarPeticion","Error al procesar las peticiones: "+e )
		}finally{
			return error
		}
	}
	
	def ffecha(String fecha) {
		return fecha.getAt(0..9).replaceAll("-","")
	}
	
	def separadorAlmohadilla = { datos->
		return datos.split("##")	
	}
	
	def separadorPorcentaje = { datos->
		return datos.split("%%")
	}
	
	def aniadirCeroALaIzquierda = { valor,diferencia ->
		def ceros="0"*diferencia
		
		return ceros.concat(valor)
	}
	
	def aniadirCeroALaDerecha = { valor,diferencia ->
		def numeroDecimales=valor
		(1..diferencia).each{
			numeroDecimales=numeroDecimales.concat("0")
		}
		
		return numeroDecimales
	}

}
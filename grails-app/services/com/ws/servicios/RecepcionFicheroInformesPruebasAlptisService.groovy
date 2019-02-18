package com.ws.servicios
import com.scortelemed.Conf

class RecepcionFicheroInformesPruebasAlptisService {
	
	def avisosService
	def logginService
	/*
	 * Se coge el contenido de la peticion: .results.test_results.test.zip_files y se crea un fichero
	 * luego se manda por email informado de su nombre y el numero de poliza: .results.policy_number
	 */
	
	def copyFichero = { contenido ->
		try {
			def carpeta = Conf.findByName('alptisCarpetaInformesPruebas')
			def nombreFichero = contenido.results[0].cod_st.toString().replaceAll(" ", "") + "_" + contenido.results[0].policy_number.toString().replaceAll(" ", "") + "_" + new Date().format('dd-MM-yyyy HH:mm:ss')
			def nuevoFichero = new File(carpeta.value.toString() + "/" + nombreFichero + ".zip")
			nuevoFichero.createNewFile()
			def contenidoFichero = contenido.results[0].test_results.test.zip_files 
			nuevoFichero.append(contenidoFichero.decodeBase64())
			
			def destinoEmail = Conf.findByName('alptisEmailInformes').value
			def tema = 'Email automatico de plataforma WEBSERVICES - Informes de pruebas'
			def cuerpo = 'Policy_number: ' + contenido.results[0].policy_number + ' File_name: ' + nombreFichero + ' Test_status: ' + contenido.results[0].test_results.test.test_status

			//avisosService.enviarEmail(destinoEmail, tema, cuerpo)
			
			return true
		} catch (Exception e) {
			logginService.putError("copyFichero"," Creacion de informe fallida: "+e )
			return false
		}
	}
	
	def generator = { String alphabet, int n ->
		new Random().with {
		  (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
		}
	} 
}
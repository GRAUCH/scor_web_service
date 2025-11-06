package hwsol.webservices

public class RecepcionFicheroLagunaroClass {
	
	private String proceso
	private String datos
	private String servicios
	private boolean error
	private String erroresString = ""

	public putProceso(proces) {
		proceso = proces
	}
		
	public putDatos(dat) {
		String tipo = "2"
		String alma
		String conte = ""
		
		//OLD def tamanos = [5,15,15,8,8,8,8,1,8,1,25,40,40,5,40,25,10,10,10,50,8,40,9,8,70,10,8,1,8,1,1,1,1,100,1,1]
		def tamanos = [5,15,15,8,8,8,8,1,8,1,25,40,50,5,40,25,10,10,10,50,8,40,9,8,70,10,18,1,18,1,1,1,1,2000,1,1]
		int i = 0

		conte = conte + tipo
		dat.each {
			alma = verificarTam(it,tamanos.get(i))
			if(alma){
				conte = conte + alma
				i++
			} else {
				error = true
				//throw new org.codehaus.groovy.grails.exceptions.GrailsRuntimeException("Error de validacion de datos.")
			}
		}
		
		datos = conte + "\r\n"
	}
	
	public putServicios(dat) {
		if(dat) {
			String tipo = "4"
			String esp = blancos(319)
			//String esp = blancos(289)
			String alma
			String conte = ""
			
			def tamanos = [1,8,300]
			int i = 0
	
			def indice = 0
	
			def ejemplar
			dat.each {
				conte = conte + tipo
				ejemplar = it
				ejemplar.each {
					alma = verificarTam(it,tamanos.get(i))
					if(alma){
						conte = conte + alma
						i++
					} else {
						error = true
						//throw new org.codehaus.groovy.grails.exceptions.GrailsRuntimeException("Error de validacion de datos.")
					}
				}
				i = 0
				conte = conte + esp + "\r\n"
			}
			servicios = conte
		} else {
			servicios = ""
		}
	}
	
	public putCoberturas(dat) {
		
		if(dat) {
			String tipo = "5"
			String esp = blancos(554)
			//String esp = blancos(289)
			String alma
			String conte = ""
			
			def tamanos = [15,100,14]
			int i = 0
	
			def indice = 0
	
			//RECIBIMOS EN DAT (codigo=1##descripcion=Fallecimiento##capital=5000%%codigo=8##descripcion=Dependencia##capital=1,5)
			//PROCEDEMOS A SEPARARLO
			
			def coberturas=dat.split ("%%")
			coberturas.each{
				conte = conte + tipo
				def valores=it.split ("##")
				valores.each{
					def valor=it.split ("=")
					
					if(valor[0].equals("codigo")){
						alma = verificarTam(valor[1],tamanos.get(0))
						if(alma){
							conte = conte + alma
						}else{
							error=true
						}
					}
					
					if(valor[0].equals("descripcion")){						
						alma = verificarTam(valor[1],tamanos.get(1))
						if(alma){
							conte = conte + alma
						}else{
							error=true
						}
					}
					
					if(valor[0].equals("capital")){
						if(!valor[1].contains(',') && !valor[1].contains('.')){
							valor[1]=valor[1].concat("00")
						}else{
							valor[1]=valor[1].replaceAll("\\.",",")
							def aux=valor[1].split(",")						
							def numeroDecimales=""
							if(aux[1].length()>=2){
								numeroDecimales=aux[1].substring(0, 2)
								valor[1]=aux[0].concat(numeroDecimales)
							}else{
								def diferencia=2-aux[1].length()
								(1..diferencia).each{
									numeroDecimales=numeroDecimales.concat("0")
								}
								numeroDecimales=aux[1].concat(numeroDecimales)
							}
							valor[1]=aux[0].concat(numeroDecimales)
						}

						alma = verificarTam(valor[1],tamanos.get(2))
						if(alma){				
							def diferencia=tamanos.get(2)-valor[1].length()
							def numerosFaltantes=""
							(1..diferencia).each{
								numerosFaltantes=numerosFaltantes.concat("0")
							}
							conte = conte + numerosFaltantes.concat(alma)
						}else{
							error=true
						}
					}
				}
				conte = conte + esp + "\r\n"
			}

			servicios = servicios+conte
		} else {
			servicios = ""
		}
	}
	
	public getAll() {
		return datos + servicios
	}
	
	public tieneErrores() {
		return error
	}
	
	public getErroresString() {
		return erroresString
	}

	protected verificarTam(String dato, int tam) {
		String esp

		if(dato){
			if(dato.size() > tam) {
				erroresString = erroresString + "<li><span style=\"color: red\">ERROR</span> EN PROCESO: <b>" + proceso + "</b>. El valor -> <b>" + dato + "</b> se espera size(" + tam + ")</li>"
				error = true
				return false
				//throw new org.codehaus.groovy.grails.exceptions.GrailsRuntimeException("Error de validacion: " + dato + " (" + tam + ")")
			} else {
				//erroresString = erroresString + "<li>SIN ERRORES EN: <b>" + proceso + "</b> en el dato -> <b>" + dato + "</b> se espera size(" + tam + ")</li>"
				if (dato.size() < tam) {
					int dif = tam - dato.size()
					esp = blancos(dif)
					return dato + esp
				} else { return dato }
			}
		} else {
			esp = blancos(tam)
			return esp
		}
	}
	
	protected String blancos(int cantidad) {
		String esp = ""
		(1..cantidad).each { esp = esp + " " }
		return esp
	}
	
}
import org.grails.datastore.mapping.collection.PersistentSet;

class DefinicionBootStrap {

	 def init = { servletContext ->	
		
		PersistentSet.metaClass.lista = {->
			def sb = new StringBuffer()
			def lon = delegate.size()
			def i = 0
			delegate.each {
				i++
				sb << it
				if(i != lon)
					sb << ","
			}
			sb.toString()
		}
		
		PersistentSet.metaClass.listaHtml = {->
			def sb = new StringBuffer()
			sb << "<ul>"
			delegate.each {
				 sb << "<li>" + it + "</li>"
			}
			sb << "</ul>"
			sb.toString()
		}
		
		// date
		
		Date.metaClass.lista = {->
			def fecha = delegate.toString().split(" ")
			fecha
		}
		
		Date.metaClass.diaSemana = {->
			def diasSemana = ["Mon":"Lunes", "Tue":"Martes", "Wed":"Miercoles",
							  "Thu":"Jueves", "Fri":"Viernes", "Sat":"Sabado", "Sun":"Domingo"]
			def fecha = delegate.lista()
			diasSemana[fecha[0]]
		}
		
		Date.metaClass.mes = {->
			def mes = ["Jan":"Enero", "Feb":"Febrero", "Mar":"Marzo", "Apr":"Abril",
					   "May":"Mayo", "Jun":"Junio", "Jul":"Julio", "Aug":"Agosto",
					   "Sep":"Septiembre", "Oct":"Octubre", "Nov":"Noviembre", "Dec":"Diciembre"]
			def fecha = delegate.lista()
			mes[fecha[1]]
		}
		
		Date.metaClass.dia = {->
			def fecha = delegate.lista()
			fecha[2]
		}
		
		Date.metaClass.tiempo = {->
			def fecha = delegate.lista()
			fecha[3]
		}
		
		Date.metaClass.anio = {->
			def fecha = delegate.lista()
			fecha[5]
		}
		
		Date.metaClass.mostrar = {->
			delegate.diaSemana() + ", " + delegate.dia() + " de " + delegate.mes() + " del " + delegate.anio()
		}
		
		Date.metaClass.noes = {->
			delegate.dia() + delegate.mes() + delegate.anio()
		}
		
		Date.metaClass.formatoAnglo = {->
			def mes = ["Jan":"01", "Feb":"02", "Mar":"03", "Apr":"04",
					   "May":"05", "Jun":"06", "Jul":"07", "Aug":"08",
					   "Sep":"09", "Oct":"10", "Nov":"11", "Dec":"12"]
			def fecha = delegate.lista()
			delegate.anio() + mes[fecha[1]] + delegate.dia()
		}

	   }
	 
	 def destroy = {}
}
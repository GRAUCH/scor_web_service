
class AyudantesTagLib {

	def recorre = { attrs->
		def salida = ""
		def tip = ""
		if(attrs.tipo == "lista") {
			tip = "<br />"
		} 
		else {
			tip = ", "
		}
		def lon = attrs.array.size()
		def i = 0
		attrs.array.each{
			salida += it 
			i++
			if(i != lon)
				salida += tip
		}
		
		println salida
		
		out << salida
	}

	
}

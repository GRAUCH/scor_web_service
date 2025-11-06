package hwsol.webservices

class ScorExpedienteTarificado {
	def id
	//GENERAL
	def tipoDocumento
	def scorName
	def poliza
	def certificado
	def movimiento
	def nif
	def tipoRMedico
	def codTipoRMedico // Para saber el código del tipodereconocimientomédico
	def dictamen
	def dictamenFalle
	def dictamenInva
	def zip
	
	//PRUEBAS MEDICAS
	def pruebasMedicas = []
	def garantias = []
	
}

	class PruebaMedica {
		def codigo
		def fecha
		def aportaPruebas
		def descripcion
		
		PruebaMedica (String cod, String fech, String aporta, String descrip) {
			codigo = cod
			fecha = fech
			aportaPruebas = aporta
			descripcion = descrip
		}
	}
	
	
	class Garantias {
		String codigo
		String descripcion
		String valoracion
		
		Garantias (String cod,String des,String val) {
			codigo = cod
			descripcion = des
			valoracion = val
		}
	}



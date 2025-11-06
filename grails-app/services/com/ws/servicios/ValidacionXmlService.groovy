package com.ws.servicios
import org.springframework.web.context.request.RequestContextHolder
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

class ValidacionXmlService {
	
	def validar(String xml, xsdRuta) {
		
		def xsd = getXsd(xsdRuta)
	
		def factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
		def schema = factory.newSchema(new StreamSource(new StringReader(xsd)))
		def validator = schema.newValidator()
		
		try {
			validator.validate(new StreamSource(new StringReader(xml)))
		} catch(java.lang.IllegalArgumentException e) {
			return e
		} catch(org.xml.sax.SAXException e) {
			return e
		} catch(java.io.IOException e) {
			return e
		} catch(java.lang.NullPointerException e) {
			return e
		}
		
		return false

	}
	
	
	/*
	 * Obtiene un documento XSD en particular.
	 * desc -> EJEMPLO: lagunaro/GestionReconocimientosMedicos
	 */
	def getXsd(desc) {
		def rutaPadre = RequestContextHolder.currentRequestAttributes().getSession().getServletContext().getRealPath("/")
		rutaPadre = rutaPadre + "WEB-INF/" + desc + ".xsd"
		new File(rutaPadre).text

	}
	

}


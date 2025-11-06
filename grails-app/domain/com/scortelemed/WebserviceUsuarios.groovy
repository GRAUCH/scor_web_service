package com.scortelemed



class WebserviceUsuarios implements Serializable {

	static belongsTo = [
		webservice : Webservice,
		person: Person
	]
	
	static mapping = {
		id composite: ['webservice', 'person']
		version false
	}
}

package com.ws.enumeration

 enum UnidadOrganizativa {

	ES ("ES"),
	FR ("FR"),
	PT ("PT"),
	IT ("IT")

	final String value

	UnidadOrganizativa(String value) {
		this.value = value
	}

	String toString() {
		
		value
	}
	String getKey() {
		name()
	}
}

package com.ws.enumeration

enum UnidadOrganizativa {

	ES ("ES"),
	FR ("FR"),
	PT ("PT"),
	IT ("IT")

	private final String value

	UnidadOrganizativa(String value) {
		this.value = value
	}

	String toString() {
		return this.value
	}
	String getKey() {
		return this.name()
	}

	static UnidadOrganizativa fromNombre( String value ) {
		if(value != null && !value.isEmpty()) {
			for (UnidadOrganizativa actual : UnidadOrganizativa.values()) {
				if (actual.value == value) {
					return actual
				}
			}
		}

		//values().find { it.value == value }
	}
}

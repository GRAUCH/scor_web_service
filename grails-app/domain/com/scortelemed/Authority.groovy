package com.scortelemed

class Authority {

	String authority
	String description

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}

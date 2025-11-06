package com.scortelemed
class ConfFichero {

	String name
	String value
	String description

    static constraints = {
    	name(blank:false, unique:true)
    	value(blank:false, size:0..512)
    }

}
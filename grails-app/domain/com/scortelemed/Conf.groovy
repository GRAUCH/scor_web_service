package com.scortelemed
class Conf {

	String name
	String value
	String description

    static constraints = {
    	name(blank:false, unique:true)
    	value(blank:false, size:0..512)
    }
	
	public String toString() {
		return name;
	}	
}

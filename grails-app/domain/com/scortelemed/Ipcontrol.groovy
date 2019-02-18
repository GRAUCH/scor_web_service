package com.scortelemed

class Ipcontrol {
	static belongsTo = [Company]
	                    
	String ipAddress
	
	Company company
	
	static constraints = {
    	ipAddress(blank:false, unique:true)
    	company(blank:false)
    }
	
	public String toString() {
		return ipAddress
	}
}

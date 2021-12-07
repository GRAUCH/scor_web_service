package com.scortelemed

class Validation {

    String domainId
    String key
    String value

    static constraints = {
        key(blank:false, unique:true)
        value(blank:false, unique:true)
    }
}

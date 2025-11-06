package com.scortelemed

class Domain {

    String description

    static belongsTo = [company:Company,product:Product]

    static constraints = {
        description(blank:false, unique:true)
    }
}

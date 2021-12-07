package com.scortelemed

class Product {

    String code
    String description

    static constraints = {
        code(blank:false, unique:true)
        description(blank:false, unique:true)
    }
}

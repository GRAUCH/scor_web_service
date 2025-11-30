package com.scortelemed

class Envio {

    Date fecha
    String cia
    String identificador
    String info

    static mapping = {
        info type: 'text'
    }

    static constraints = {
        fecha(nullable: false)
        cia(nullable: false)
        identificador(nullable: false)
        info(nullable: false)
    }

}

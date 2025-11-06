package com.scortelemed

class Envio {

    Date fecha
    String cia
    String identificador
    String info

    static constraints = {
        fecha(nullable: false)
        cia(nullable: false)
        identificador(nullable: false)
        info(nullable: false)
    }

}

package com.scortelemed

class Agente {

    Date fecha
    String cia
    String valor
    String agente
    
    static constraints = {
        fecha(nullable:false)
        cia(nullable:false)
        valor(nullable:false)
        agente(nullable:false)
    }
}

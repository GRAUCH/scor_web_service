package com.scortelemed

enum TipoCompany {

    LAGUN_ARO("lagunaro"),
    ALPTIS("alptis"),
    AFI_ESCA("afiesca"),
    CAJAMAR("cajamar"),
    AMA("ama"),
    PSN("psn"),
    CASER("caser"),
    NET_INSURANCE("netinsurance"),
    MALAKOFF_MEDERIC("simplefr"),
    SOCIETE_GENERALE("societeGenerale"),
    METHIS_LAB("methislab"),
    CBP_ITALIA("cbp-italia"),
    ZEN_UP("lifesquare"),
    ENGINYERS("enginyers"),
    CF_LIFE("methislabCF"),
    SCOR("Scor"),
    DEFAULT("default")

    private final String nombre

    TipoCompany(String nombre) {
        this.nombre = nombre
    }

    String getNombre() {
        return nombre
    }

    static TipoCompany fromValue(String string) {
        return valueOf(string)
    }

    String toString() {
        return this.nombre
    }

    static TipoCompany fromNombre(String nombre) {
        if(nombre != null && !nombre.isEmpty()) {
            for (TipoCompany actual : TipoCompany.values()) {
                if (actual.nombre.equalsIgnoreCase(nombre)) {
                    return actual
                }
            }
        }
        return TipoCompany.DEFAULT
    }

}
package com.scortelemed

enum TipoCompany {

    LAGUN_ARO("lagunaro", null, null),
    ALPTIS("alptis", null, null),
    AFI_ESCA("afiesca", null, null),
    CAJAMAR("cajamar", null, null),
    AMA("ama", null, null),
    AMA_VIDA("amaVida", null, null),
    PSN("psn", null, null),
    CASER("caser", null, null),
    NET_INSURANCE("netinsurance", null, null),
    MALAKOFF_MEDERIC("simplefr", null, null),
    NATIONALE_NETHERLANDEN("nn", null, null),
    SOCIETE_GENERALE("societeGenerale", null, null),
    METHIS_LAB("methislab", null, null),
    CBP_ITALIA("cbp-italia", "CBP", "TUW realizzata"),
    ZEN_UP("lifesquare", null, null),
    ENGINYERS("enginyers", null, null),
    CF_LIFE("methislabCF", null, null),
    SCOR("Scor", null, null),
    DEFAULT("default", null, null)

    private final String nombre
    private final String zip
    private final String audio


    TipoCompany(String nombre, String zip, String audio) {
        this.nombre = nombre
        this.zip = zip
        this.audio = audio
    }

    String getNombre() {
        return nombre
    }

    String getZip() {
        return zip
    }

    String getAudio() {
        return audio
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
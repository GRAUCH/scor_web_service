package hwsol.webservices

class WsError {

    String campo
    String valor
    String detalle

    WsError(String campo, String valor, String detalle) {
        this.campo = campo
        this.valor = valor
        this.detalle = detalle
    }

    String getCampo() {
        return campo
    }

    void setCampo(String campo) {
        this.campo = campo
    }

    String getValor() {
        return valor
    }

    void setValor(String valor) {
        this.valor = valor
    }

    String getDetalle() {
        return detalle
    }

    void setDetalle(String detalle) {
        this.detalle = detalle
    }
}

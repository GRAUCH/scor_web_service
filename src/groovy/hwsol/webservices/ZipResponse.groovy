package hwsol.webservices

class ZipResponse {

    def error
    def codigo
    def descripcion
    def datosRespuesta

    def getDescripcion() {
        return descripcion
    }

    void setDescripcion(descripcion) {
        this.descripcion = descripcion
    }

    def getDatosRespuesta() {
        return datosRespuesta
    }

    void setDatosRespuesta(datosRespuesta) {
        this.datosRespuesta = datosRespuesta
    }

    def getError() {
        return error
    }

    void setError(error) {
        this.error = error
    }

    def getCodigo() {
        return codigo
    }

    void setCodigo(codigo) {
        this.codigo = codigo
    }
}

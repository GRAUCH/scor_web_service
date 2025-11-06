package hwsol.entities

class Envio {

    Date fecha
    String cia
    String identificador
    String info

    Envio() {

    }

    void set(com.scortelemed.Envio antiguo){
        if(antiguo != null) {
            this.fecha = antiguo.getFecha()
            this.cia = antiguo.getCia()
            this.identificador = antiguo.getIdentificador()
            this.info = antiguo.getInfo()
        }
    }
}

package hwsol.webservices

class EntradaDetalle {
	
	private String fecha 
	private String idExpediente
	private String codigoEvento
	private String detalle
	
	public EntradaDetalle() {
		super();
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	public String getCodigoEvento() {
		return codigoEvento;
	}
	public void setCodigoEvento(String codigoEvento) {
		this.codigoEvento = codigoEvento;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	

}

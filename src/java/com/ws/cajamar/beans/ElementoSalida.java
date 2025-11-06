package com.ws.cajamar.beans;

import java.util.Date;
import java.util.List;

public class ElementoSalida {

	String estado;
	String motivo;
	String cliente;
	String ramo;
	String producto;
	String solicitud;
	Date fecha;
	boolean teleseleccionCorta;
	boolean teleseleccionAbreviada;
	String codError;
	String error;
	List<Cobertura> coberturas;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getRamo() {
		return ramo;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isTeleseleccionCorta() {
		return teleseleccionCorta;
	}
	public void setTeleseleccionCorta(boolean teleseleccionCorta) {
		this.teleseleccionCorta = teleseleccionCorta;
	}
	public boolean isTeleseleccionAbreviada() {
		return teleseleccionAbreviada;
	}
	public void setTeleseleccionAbreviada(boolean teleseleccionAbreviada) {
		this.teleseleccionAbreviada = teleseleccionAbreviada;
	}
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<Cobertura> getCoberturas() {
		return coberturas;
	}
	public void setCoberturas(List<Cobertura> coberturas) {
		this.coberturas = coberturas;
	}
}

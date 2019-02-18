package com.ws.cajamar.beans;

import java.util.Date;
import java.util.List;

public class ElementoEntrada {
	
	boolean teleseleccionCorta;
	boolean teleseleccionAbreviada;
	String numSolicitud;
	String dni;
	Date fecha;
	Date fechaNacimiento;
	String telefono1;
	String telefono2;
	List<String> pruebasMedicas;
	String franjaHoraria;
	String dias;
	boolean sida;
	
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
	public String getNumSolicitud() {
		return numSolicitud;
	}
	public void setNumSolicitud(String numSolicitud) {
		this.numSolicitud = numSolicitud;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public List<String> getPruebasMedicas() {
		return pruebasMedicas;
	}
	public void setPruebasMedicas(List<String> pruebasMedicas) {
		this.pruebasMedicas = pruebasMedicas;
	}
	public String getFranjaHoraria() {
		return franjaHoraria;
	}
	public void setFranjaHoraria(String franjaHoraria) {
		this.franjaHoraria = franjaHoraria;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public boolean isSida() {
		return sida;
	}
	public void setSida(boolean sida) {
		this.sida = sida;
	}

}

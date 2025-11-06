package com.ws.lagunaro.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;	

import com.ws.enumeration.EstadoCivilType;
import com.ws.enumeration.HoraContactoType;
import com.ws.enumeration.SexoType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AseguradoType", propOrder = {
    "nif",
    "nombre",
    "apellido1",
    "apellido2",
    "direccion",
    "localidad",
    "codigo_postal",
    "provincia",
    "telefono1",
    "telefono2",
    "email",
    "fecha_nacimiento",
    "sexo",
    "estado_civil",
    "hora_contacto"
})
public class AseguradoType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String nif;
	private String nombre;
	private String apellido1;
	@XmlElement(nillable=true)
	private String apellido2;
	private String direccion;
	private String localidad;
	private String codigo_postal;
	private String provincia;
	private String telefono1;
	@XmlElement(nillable=true)
	private String telefono2;
	@XmlElement(nillable=true)
	private String email;
	@XmlSchemaType(name = "date")
	private Date fecha_nacimiento;
	private SexoType sexo;
	@XmlElement(nillable=true)
	private EstadoCivilType estado_civil;
	@XmlElement(nillable=true)
	private HoraContactoType hora_contacto;
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public SexoType getSexo() {
		return sexo;
	}
	public void setSexo(SexoType sexo) {
		this.sexo = sexo;
	}
	public EstadoCivilType getEstado_civil() {
		return estado_civil;
	}
	public void setEstado_civil(EstadoCivilType estado_civil) {
		this.estado_civil = estado_civil;
	}
	public HoraContactoType getHora_contacto() {
		return hora_contacto;
	}
	public void setHora_contacto(HoraContactoType hora_contacto) {
		this.hora_contacto = hora_contacto;
	}
	
	
}

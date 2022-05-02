package usuarios;

import java.util.Objects;

public class Usuario {

	private String idUsuario;
	private String nombre;
	private String dni;
	private Integer telefono;
	private String correo;
	private String centro;
	private String zona;
	private String estado;
	private Boolean capacitacion;
	
	public Usuario(String idUsuario, String nombre, String dni, Integer telefono, String correo, String centro,
			String zona, String estado, Boolean capacitacion) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
		this.correo = correo;
		this.centro = centro;
		this.zona = zona;
		this.estado = estado;
		this.capacitacion = capacitacion;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(Boolean capacitacion) {
		this.capacitacion = capacitacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario);
	}

	
	
}

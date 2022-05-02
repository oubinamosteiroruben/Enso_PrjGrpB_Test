package equipos;
import java.util.ArrayList;
import java.util.Objects;

import usuarios.Usuario;

public class Equipo {
	
	private ArrayList<usuarios.Usuario> miembros;
	private String tipo;
	private String idEquipo;
	//Esto es una prueba 
	
	public Equipo(String idEquipo,ArrayList<Usuario> miembros, String tipo) {
		super();
		this.idEquipo = idEquipo;
		this.miembros = miembros;
		this.tipo = tipo;
	}

	public ArrayList<usuarios.Usuario> getMiembros() {
		return miembros;
	}

	public void setMiembros(ArrayList<usuarios.Usuario> miembros) {
		this.miembros = miembros;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEquipo);
	}

	public String getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		return Objects.equals(idEquipo, other.idEquipo);
	}

	
	
	

}

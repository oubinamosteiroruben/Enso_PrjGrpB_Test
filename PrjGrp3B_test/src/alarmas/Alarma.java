package alarmas;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Alarma{

	private String idAlarma;
	private String tipo;
	private String centro;
	private Date fechaInicio;
	private Date fechaCierre;
	private List<Accion> acciones;
	
	public Alarma(String idAlarma, String tipo, String centro, Date fechaInicio, List<Accion> acciones) {
		this.idAlarma = idAlarma;
		this.tipo = tipo;
		this.centro = centro;
		this.fechaInicio = fechaInicio;
		this.acciones = acciones;
	}

	public String getIdAlarma() {
		return idAlarma;
	}

	public void setIdAlarma(String idAlarma) {
		this.idAlarma = idAlarma;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public List<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAlarma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alarma other = (Alarma) obj;
		return Objects.equals(idAlarma, other.idAlarma);
	}
}

package alarmas;
import java.util.Date;
import java.util.Objects;

public class Accion {

	private Date fechaInicio; //fecha en la que se crea la accion
	private Date fechaFin; //fecha en la que llega la verificacion de la accion
	private String mensajeAccion;
	private String mensajeVerificacion;

	public Accion(Date fechaInicio, String mensajeAccion) {
		super();
		this.fechaInicio = fechaInicio;
		this.mensajeAccion = mensajeAccion;
	}
	
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setMensajeVerificacion(String mensajeVerificacion) {
		this.mensajeVerificacion = mensajeVerificacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getMensajeAccion() {
		return mensajeAccion;
	}

	public void setMensajeAccion(String mensajeAccion) {
		this.mensajeAccion = mensajeAccion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public String getMensajeVerificacion() {
		return mensajeVerificacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaInicio, mensajeAccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accion other = (Accion) obj;
		return Objects.equals(fechaInicio, other.fechaInicio) && Objects.equals(mensajeAccion, other.mensajeAccion);
	}
}

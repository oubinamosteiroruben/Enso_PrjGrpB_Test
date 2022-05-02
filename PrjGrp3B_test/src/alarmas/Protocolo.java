package alarmas;
import java.util.List;
import java.util.Objects;

public class Protocolo {

	private String idProtocolo;
	private String tipoAlarma;
	private String tipoEquipo;
	private List<String> mensajesAcciones;
	
	public Protocolo(String idProtocolo, String tipoAlarma, String tipoEquipo, List<String> mensajesAcciones) {
		this.idProtocolo = idProtocolo;
		this.tipoAlarma = tipoAlarma;
		this.tipoEquipo = tipoEquipo;
		this.mensajesAcciones = mensajesAcciones;
	}

	public String getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(String idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public String getTipoAlarma() {
		return tipoAlarma;
	}

	public void setTipoAlarma(String tipoAlarma) {
		this.tipoAlarma = tipoAlarma;
	}

	public String getTipoEquipo() {
		return tipoEquipo;
	}

	public void setTipoEquipo(String tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}

	public List<String> getMensajesAcciones() {
		return mensajesAcciones;
	}

	public void setMensajesAcciones(List<String> mensajesAcciones) {
		this.mensajesAcciones = mensajesAcciones;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProtocolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Protocolo other = (Protocolo) obj;
		return Objects.equals(idProtocolo, other.idProtocolo);
	}
}

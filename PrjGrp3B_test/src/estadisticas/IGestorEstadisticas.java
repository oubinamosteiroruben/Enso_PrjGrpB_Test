package estadisticas;
import alarmas.Alarma;
import java.util.ArrayList;

public interface IGestorEstadisticas {
	
	//Añade una alarma resuelta a la lista de alarmas resueltas del gestor
	void addAlarmaResuelta(Alarma a);
	
	Integer accionesTotales();
	
	Integer alarmasResueltas();
	
	Float duracionMediaAccion();
	
	ArrayList<String> centrosConMasAlarmas();
	
	ArrayList<String> centrosConMenosAlarmas();
	
	Float accionesPorAlarmaPromedio();
	
	Float duracionMediaAlarma();
	
}

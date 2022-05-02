package alarmas;

import java.util.Date;
import java.util.List;

public interface IGestorAlarmas {
	
	//Llama al constructor de alarma, generando un id nuevo 
	//y creando las acciones a partir del protocolo correspondiente al tipo de alarma,
	//por cada mensjae se crea una accion, la fecha de inicio de la accion ya se pondra en procesar alarma cuando se envien los mensajes 
	public Alarma crearAlarma (String tipo, String centro, Date fechaInicio) throws IllegalArgumentException;
	
	//Llama al constructor del protocolo generando un id de protocolo 
	public Protocolo crearProtocolo(String tipoAlarma, String tipoEquipo, List<String> mensajesAcciones) throws IllegalArgumentException;	
	
	//Se añade un protocolo a la lista de protocolos que tiene el gestor
	public boolean addProtocolo(Protocolo p) throws IllegalArgumentException;
	
	//Se elimina un protocolo de la lista de protocolos que tiene el gestor
	public boolean eliminarProtocolo(Protocolo p) throws IllegalArgumentException;
	
	//Se procesa una alarma añadiendola a alarmasActivas y llamando a recibirDatos emergencia de gestor equipos
	public boolean procesarAlarma(Alarma a) throws IllegalArgumentException;
	
	//No hace nada
	public void activarSirena();
	
	//Se devuelve la alarma con ese id
	public Alarma obtenerAlarma(String idAlarma);
	
	//Se devuelve el protocolo de la lista de protocolos que tiene el gestor correspondiente al tipo de alarma indicado
	public Protocolo obtenerProtocolo(String tipoAlarma);
	
	// Devuelve la lista de alarmas activas
	public List<Alarma> consultarAlarmasActivas();
}

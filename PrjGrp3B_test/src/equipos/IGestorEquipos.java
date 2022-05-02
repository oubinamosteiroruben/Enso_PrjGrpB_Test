package equipos;
import usuarios.Usuario;
import alarmas.Alarma;
import alarmas.Accion;
import java.util.ArrayList;

public interface IGestorEquipos {
	
	//LLama al constructor de equipos
	Equipo crearEquipo(ArrayList<Usuario> miembros, String tipo);
	
	//Se recibe la alarma con las acciones, que tienen los mensajes a enviar y se envian con enviarAccion al equipo que es
	void recibirDatosEmergencia(Alarma a);
	
	//Se añade un equipo a la lista de equipos del gestor
	boolean addEquipo(Equipo e);
	
	//Se elimina un equipo de la lista de equipos del gestor
	boolean eliminarEquipo(Equipo e);
	
	//Se recibe un equipo nuevo (copia) con atributos cambiados MENOS EL ID y se busca el equipo en el gestor que le corresponde y se modifican sus datos
	Equipo modificarEquipo(Equipo e, String IdEquipo);
	
	//No hace nada
	void enviarAccion(Accion a, Equipo e);
	
	//Se confirma una accion y se comprobara en Gestor Alarmas si hay alguna alarma en la que se han confirmado todas las acciones y se puede cerrar
	Accion confirmarAccion(String mensajeVerificacion, Accion a);
	
	//Se devuelven los equipos que tiene el gestor
	ArrayList<Equipo> obtenerEquipos();
	
}

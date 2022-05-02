package usuarios;
import alarmas.Alarma;
import java.util.ArrayList;

public interface IGestorUsuarios {
	
	//Crea un usuario llamando al constructor y generando un id de usuario
	Usuario crearUsuario(String nombre, String dni, Integer telefono, String correo, String centro,
			String zona, String estado, Boolean capacitacion);
	
	//No hace nada
	void enviarMensaje(String centro, String mensaje);
	
	//Despues de haber creado una alarma en el GestorAlarmas se puede llamar a declararAlarma que solo llama a procesarAlarma
	Boolean declararAlarma(Usuario u, Alarma a);
	
	//Añade el usuario a la lista de usuarios del gestor
	Boolean addUsuario(Usuario u);
	
	//Elimina el usuario de la lista de usuarios del gestor
	Boolean eliminarUsuario(Usuario u);
	
	//Se recibe un usuario nuevo (copia) con atributos cambiados MENOS EL ID y se busca el equipo en el gestor que le corresponde y se modifican sus datos
	Usuario modificarUsuario(Usuario u, String IdUsuario);
	
	//Devuelve la lista de usuarios del sistema que tiene el gestor
	ArrayList<Usuario> obtenerListaUsuarios();
}

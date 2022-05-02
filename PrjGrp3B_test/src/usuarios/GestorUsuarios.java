package usuarios;

import java.lang.reflect.*;
import java.util.ArrayList;

import alarmas.Alarma;
import alarmas.GestorAlarmas;

public class GestorUsuarios implements IGestorUsuarios {
	
	private ArrayList<Usuario> usuarios;
	private static GestorUsuarios instancia;
	
	
	private GestorUsuarios() {
		super();
		this.usuarios= new ArrayList<>();
		
	}

	public static GestorUsuarios obtenerGestorUsuarios() {
		if(instancia == null) {
			instancia=new GestorUsuarios();
		}
		return instancia;
	}

	@Override
	public Usuario crearUsuario(String nombre, String dni, Integer telefono, String correo, String centro, String zona,
			String estado, Boolean capacitacion) {
		//Generamos un id a partir del nombre del usuario y de su centro
		String id = generarId(nombre, centro);
		Usuario u = new Usuario(id, nombre, dni, telefono, correo, centro, zona, estado, capacitacion);
		return u;
	}

	private String generarId (String nombre, String centro) {
		String id = nombre + "_" + centro;
		// Comprobamos si ya hay un usuario con ese id, en cuyo caso aÃ±adimos un nÃºmero al id
		int coincidencias = 0;
		for (Usuario u: usuarios) {
			if (u.getIdUsuario() == id) {
				coincidencias++;
			}
		}
		if (coincidencias > 0) {
			id += "_" + coincidencias;
		}
		return id;
	}

	@Override
	public void enviarMensaje(String centro, String mensaje) {
		//no hace nada

	}

	@Override
	public Boolean declararAlarma(Usuario u, Alarma a) throws IllegalArgumentException{
		//Se comprueba que el usuario existe 
		if (!usuarios.contains(u)) {
			throw new IllegalArgumentException("El usuario introducido no estÃ¡ registrado en el sistema");
		}
		//Se llama a procesar alarma de gestor de alarmas
		GestorAlarmas g = GestorAlarmas.obtenerGestorAlarmas();
		return g.procesarAlarma(a);
	}

	@Override
	public Boolean addUsuario(Usuario u) throws IllegalArgumentException {
		if (!usuarios.contains(u)) {
			return usuarios.add(u);
		}
		else {
			return false; 
		}
	}

	@Override
	public Boolean eliminarUsuario(Usuario u) throws IllegalArgumentException {
		if (u == null) {
			throw new IllegalArgumentException("Usuario no vÃ¡lido");
		}
		return usuarios.remove(u);
	}

	@Override
	public Usuario modificarUsuario(Usuario u, String IdUsuario) {	// Complejidad ciclom�tica de 6

		
		//Se recibe un usuario nuevo (copia) con atributos cambiados MENOS EL ID y 
		//se busca el equipo en el gestor que le corresponde y se modifican sus datos
		
		if (u == null) {
			throw new IllegalArgumentException("Usuario no vÃ¡lido");
		}

		Usuario antiguo = null;
		for (Usuario user: usuarios) {
			if (user.getIdUsuario() == IdUsuario) {
				antiguo = user;
			}
		}
		if (antiguo == null) {
			throw new IllegalArgumentException ("El id introducido no corresponde con el de un usuario del sistema");
		}
		// Una opciÃ³n es implementar esto modificando cada mÃ©todo uno por uno, para hacerlo mÃ¡s general se ha decidido iterar sobre
		// los atributos de la clase realizando modificaciones. 
		Class<?> c = u.getClass();
		for (Field atributo: c.getDeclaredFields()) {
			String nombreAtributo = atributo.getName();
			if (nombreAtributo == "id")
				continue;
			// Ponemos la primera letra del nombre en mayÃºscula
			nombreAtributo = Character.toUpperCase(nombreAtributo.charAt(0)) + nombreAtributo.substring(1);
			// Vamos a buscar el mÃ©todo setter y getter en la clase para obtener el valor del atributo en el usuario de entrada y 
			// modificarlo en el usuario guardado
			String setterName = "set" + nombreAtributo;
			String getterName = "get" + nombreAtributo;
			Method setter = null, getter = null;
			try {
				setter = c.getDeclaredMethod(setterName, atributo.getType());	// Estos métodos tienen throws, por lo que aporta a la complejidad
				getter = c.getDeclaredMethod(getterName, (Class<?>[])null);
				if (getter.invoke(u, (Object[]) null) == null)
					throw new IllegalArgumentException("Uno de los campos de usuario es nulo");
				setter.invoke(antiguo, getter.invoke(u, (Object[])null));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return antiguo;	//Devolvemos el objeto usuario del gestor sobre el que ya hemos realizado las modificaciones
	}
	
	
	@Override
	public ArrayList<Usuario> obtenerListaUsuarios(){
		return usuarios;
	}

}

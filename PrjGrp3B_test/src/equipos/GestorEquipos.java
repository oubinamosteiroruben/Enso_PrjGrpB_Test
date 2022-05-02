package equipos;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;


import alarmas.Accion;
import alarmas.Alarma;
import usuarios.Usuario;
import alarmas.GestorAlarmas;

public class GestorEquipos implements IGestorEquipos {

	private ArrayList<Equipo> equipos;
	private static GestorEquipos instancia;
	private static int idCounter = 0; //Contador para los id de os equipos
	
	private GestorEquipos() {
		super();
		this.equipos= new ArrayList<>();
		
	}
	
	public static GestorEquipos obtenerGestorEquipos() {
		if(instancia == null) {
			instancia=new GestorEquipos();
		}
		return instancia;
	}

	@Override
	public Equipo crearEquipo(ArrayList<Usuario> miembros, String tipo) throws IllegalArgumentException {
		String idEquipo=null;
		
		//Comprobación de los argumentos 
		
		if(tipo == null) {
			throw new IllegalArgumentException("El argumento de los miembros es incorrecto, no se puede crear el equipo");
			
		}
		//Se comprueba si ya existe un equipo con esos miembros y tipo 
		//Para ello se ve si hay o un miembro o el tipo distinto. Si no lo hay se devuelve excepcion
		
		
		//Si va bien, enerar idEquipo
		idEquipo = generarSiguienteIdEquipo(); 
		return new Equipo(idEquipo, miembros, tipo);
	}
	
	private static String generarSiguienteIdEquipo() {
		// Genera el siguiente ID de alarma único a partir de un contador interno
		// Formato ALRM_X
		idCounter++;
		return String.format("EQ_%d", idCounter);
	}

	@Override
	public void recibirDatosEmergencia(Alarma a) throws IllegalArgumentException{
		//Se recibe la alarma con las acciones, 
		//que tienen los mensajes a enviar y se envian con enviarAccion al equipo que es
		if(a == null) {
			throw new IllegalArgumentException("El argumento de los miembros es incorrecto");

		}
		
		for(Equipo e : equipos) {
			if(e.getTipo().equals(a.getTipo()) && e.getMiembros().get(0).getCentro().equals(a.getCentro())){
				for(Accion acc: a.getAcciones()) {
					enviarAccion(acc,e); 
				}
				
			}
		}
		

	}
	
	
	//Este método es de complejidad 6 
	@Override
	public boolean addEquipo(Equipo e)  throws IllegalArgumentException{
		ArrayList<Usuario> auxMiembros = null;  //Para comprobaciones
		boolean flag; //Para comprobaciones
		int numeroMiembros = 0; //Para comprobaciones
		//Comprobaciones previas 
		if(e == null ) {
			throw new IllegalArgumentException("Equipo no válido");

		}
		
		try {
			if(!e.getIdEquipo().startsWith("EQ_"))
				throw new IllegalArgumentException("ID de equipo no válida!");
			Integer.parseInt(e.getIdEquipo().substring(3));
		} catch(NumberFormatException E) {
			throw new IllegalArgumentException("ID de equipo no válida!");
		}
		
		for(Equipo equipo : equipos) {
			flag = false; 
			for(Usuario u: e.getMiembros()) { //Se comprueba para todos los miembros introducidos
				auxMiembros = equipo.getMiembros();  
				numeroMiembros=0; 
				if((auxMiembros.contains(u))) {
					numeroMiembros++; 
				}
				//Si numeroMiembros es igual al tamaño de auxMiembros, se repite el equipo
				if(numeroMiembros == auxMiembros.size()) flag=true; 
			}
			//Podría ser un equipo con los mismos miembros, pero distinto tipo. Se comprueba
			if(flag == true) { //Se repetirían los equipos 
				if(!(equipo.getTipo().equals(e.getTipo()))) { 
					//Si el tipo es distinto, se vuelve a poner a false, el equipo no se repite
					flag = false; 
				}
			}
			//Ahora, si flag es true, es decir, se repite el equipo, se lanza una excepcion
			if(flag == true) {
				throw new IllegalArgumentException("El equipo ya existe, no se puede crear el equipo");

			}
			
		}
		
		return equipos.add(e);

	}
	
	@Override
	public boolean eliminarEquipo(Equipo e) {
		if(e == null) {
			throw new IllegalArgumentException("Equipo incorrecto");

		}
		equipos.remove(e);
		return true; 
	}

	@Override
	public Equipo modificarEquipo(Equipo e, String IdEquipo) throws  IllegalArgumentException{
		//Se recibe un equipo nuevo (copia) con atributos cambiados MENOS EL ID 
		//y se busca el equipo en el gestor que le corresponde y se modifican sus datos
		
		//Se comprueba primero que se introducen datos correctos
		if(e == null) {
			throw new IllegalArgumentException("Equipo no válido");

		}
		if(IdEquipo == null) {
			throw new IllegalArgumentException("IdEquipo no válido");

		}
		
		ListIterator<Equipo> it= equipos.listIterator();
		while(it.hasNext()) {
			Equipo eqModificar = it.next(); 
			if(eqModificar.getIdEquipo().equals(IdEquipo)) {
				//Se remplaza si el Id coincide
				//Primero hay que poner el IdCorrecto, ya que si no va a poner 
				//el Id del equipo auxiliar que se le ha mandado
				e.setIdEquipo(IdEquipo);
				//Se cambia
				it.set(e);
			}
		}
		
		
		return e;

	}

	@Override
	public void enviarAccion(Accion a, Equipo e) {
		//No hace nada

	}

	@Override
	public ArrayList<Equipo> obtenerEquipos() {
		return equipos;
	}
	
	@Override
	public Accion confirmarAccion(String mensajeVerificacion, Accion a) throws  IllegalArgumentException {
		if(mensajeVerificacion == null || a == null) {
			throw new IllegalArgumentException("Argumentos no válidos");
		}
		
		
		//confirma la accion y pone el Date del fin de la accion
		a.setMensajeVerificacion(mensajeVerificacion);
		a.setFechaFin(new Date());
		
		
		
		//llama a comprobarCierreAlarmas de gestor alarmas
		GestorAlarmas.obtenerGestorAlarmas().comprobarCierreAlarmas();
		return a; 
		

	}

}

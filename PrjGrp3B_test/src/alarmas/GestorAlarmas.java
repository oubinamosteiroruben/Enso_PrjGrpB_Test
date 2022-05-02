package alarmas;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import equipos.GestorEquipos;
import estadisticas.GestorEstadisticas;

public class GestorAlarmas implements IGestorAlarmas {

	private List<Protocolo> protocolos;
	private List<Alarma> alarmasActivas;
	private static GestorAlarmas instancia;

	// Contador para los IDs de las alarmas y prototipos creados
	private static int idAlarmaCounter = 0;
	private static int idPrototipoCounter = 0;

	private GestorAlarmas() {
		super();
		this.protocolos = new ArrayList<>();
		this.alarmasActivas = new ArrayList<>();
	}

	public static GestorAlarmas obtenerGestorAlarmas() {
		if (instancia == null) {
			instancia = new GestorAlarmas();
		}
		return instancia;
	}

	@Override
	public Alarma crearAlarma(String tipo, String centro, Date fechaInicio) throws IllegalArgumentException {
		// Comprobaciones iniciales
		if (tipo == null || centro == null || fechaInicio == null || fechaInicio.before(new Date(0)))
			throw new IllegalArgumentException("Argumentos incorrectos, no se pudo crear la alarma");

		// Obtener protocolo para el tipo de alarma
		Protocolo protocoloASeguir = obtenerProtocolo(tipo);

		// No se encontraron protocolos para el tipo de alarma
		if (protocoloASeguir == null)
			throw new IllegalArgumentException("No existen protocolos para el tipo de alarma " + tipo);

		// Hemos encontrado un protocolo del mismo tipo, crear las acciones copiando el mensaje a nuevas acciones
		// y poniendo en cada nueva Accion la fecha actual
		ArrayList<Accion> acciones = new ArrayList<>();
		for(String mensajeAccion : protocoloASeguir.getMensajesAcciones()) {
			acciones.add(new Accion(new Date(), mensajeAccion));
		}

		// Todo fue bien: generar idAlarma único y devolver objeto de Alarma
		String idAlarma = generarSiguienteIdAlarma();
		return new Alarma(idAlarma, tipo, centro, fechaInicio, acciones);
	}

	private static String generarSiguienteIdAlarma() {
		// Genera el siguiente ID de alarma único a partir de un contador interno
		// Formato ALRM_X
		idAlarmaCounter++;
		return String.format("ALRM_%d", idAlarmaCounter);
	}

	@Override
	public Protocolo crearProtocolo(String tipoAlarma, String tipoEquipo, List<String> mensajesAcciones)
			throws IllegalArgumentException {
		if (tipoAlarma == null || tipoEquipo == null || mensajesAcciones == null)
			throw new IllegalArgumentException("Argumentos incorrectos, no se pudo crear el protocolo");

		if (mensajesAcciones.size() == 0)
			throw new IllegalArgumentException("Un protocolo debe tener al menos una acción a seguir registrada");

		// Generar idProtocolo
		String idProtocolo = generarSiguienteIdProtocolo();
		return new Protocolo(idProtocolo, tipoAlarma, tipoEquipo, mensajesAcciones);
	}

	private static String generarSiguienteIdProtocolo() {
		// Genera el siguiente ID de alarma único a partir de un contador interno
		// Formato PROT_X
		idPrototipoCounter++;
		return String.format("PROT_%d", idPrototipoCounter);
	}

	@Override
	public boolean addProtocolo(Protocolo p) throws IllegalArgumentException {
		// Intenta añadir el protocolo, devuelve si este fue añadido
		if (p == null)
			throw new IllegalArgumentException("Protocolo no válido");
		
		String idProt = p.getIdProtocolo();
		String tipoAProt = p.getTipoAlarma();
		
		try {
			if(!idProt.startsWith("PROT_"))
				throw new IllegalArgumentException("ID de protocolo no válida");
			Integer.parseInt(idProt.substring(5));
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("ID de protocolo no válida");
		}
		
		for(Protocolo protReg: protocolos) {
			if(protReg.getIdProtocolo().equals(idProt))
				throw new IllegalArgumentException("Ya existe un protocolo con la ID " + 
							idProt);
			if(protReg.getTipoAlarma().equals(tipoAProt))
				throw new IllegalArgumentException("Ya existe un protocolo para el tipo de alarma " + 
						tipoAProt);
		}
		
		return protocolos.add(p);
	}

	@Override
	public boolean eliminarProtocolo(Protocolo p) throws IllegalArgumentException {
		if (p == null)
			throw new IllegalArgumentException("Protocolo no válido");
		return protocolos.remove(p);
	}

	@Override
	public boolean procesarAlarma(Alarma a) throws IllegalArgumentException {
		/*
		 *  Se procesa una alarma añadiendola a alarmasActivas y
		 *  llamando a recibirDatos emergencia de gestor equipos
		 */
		if (a == null)
			throw new IllegalArgumentException("Alarma no válida");
		
		String idAlarma = a.getIdAlarma();
		
		try {
			if(!idAlarma.startsWith("ALRM_"))
				throw new IllegalArgumentException("ID de alarma no válida");
			Integer.parseInt(idAlarma.substring(5));
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("ID de alarma no válida");
		}
		
				
		GestorEquipos g = GestorEquipos.obtenerGestorEquipos();
		g.recibirDatosEmergencia(a);
		return alarmasActivas.add(a);

	}

	@Override
	public void activarSirena() {
		// No hace nada
	}

	@Override
	public Alarma obtenerAlarma(String idAlarma) {
		/*
		 * Devuelve la alarma con el idAlarma indicado, o null en su defecto
		 */
		for(Alarma a: alarmasActivas) {
			if(a.getIdAlarma().equals(idAlarma))
				return a;
		}
		return null;	
	}
	
	@Override
	public Protocolo obtenerProtocolo(String tipoAlarma) {
		/*
		 * Devuelve el protocolo de la lista de protocolos con el tipoAlarma indicado, o null en su defecto
		 */
		for(Protocolo p: protocolos) {
			if(p.getTipoAlarma().equals(tipoAlarma))
				return p;
		}
		return null;
	}

	@Override
	public List<Alarma> consultarAlarmasActivas() {
		/*
		 * Devuelve la lista de alarmas activas
		 */
		return alarmasActivas;
	}
	
	public void comprobarCierreAlarmas() {
		/* 
		 * Comprueba si hay alguna alarma que tenga todas las acciones con el mensaje de
		 * verificacion
		 * 
		 * Si hay alguna es que ya se han hecho todas las acciones entonces hay que
		 * cerrarla:
		 *     1. Se añade a alarmas resueltas de gestor de estadisticas
		 *     2. Se borra de alarmas activas de gestor alarmas (haciendo un filtrado en la lista de alarmasActivas)
		 */
		GestorEstadisticas g = GestorEstadisticas.obtenerGestorEstadisticas();
		Iterator<Alarma> it = alarmasActivas.iterator();
		while(it.hasNext()) {
			Alarma al = it.next();
			for(Accion ac : al.getAcciones()) {
				if(ac.getMensajeVerificacion() == null) {
					it.remove();
					g.addAlarmaResuelta(al);
					break;
				}			
			}				
		}
	}
}

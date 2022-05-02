
package estadisticas;


import alarmas.Accion;
import alarmas.Alarma;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Collections;


// Gestor de Estadísticas: Patrón Singleton
// Cada vez que una alarma finalice, se le pasa como argumento para ser añadida a la listaAlarmasResueltas del gestor
// Sobre esta lista se obtienen las estadísticas



// Prueba parcial del módulo: descomentar linea 44


public class GestorEstadisticas implements IGestorEstadisticas {

	// ArrayList a actualizar cada vez que se recibe una alarma finalizada
	private ArrayList<Alarma> listaAlarmasResueltas;
	// Patrón de diseño singleton
	private static GestorEstadisticas instancia;
	
	
	// El constructor lanza la excepcion ParseException para el caso de pruebas
	//public GestorEstadisticas() throws ParseException {
	// private GestorEstadisticas()
	public GestorEstadisticas() {
		super();
		this.listaAlarmasResueltas= new ArrayList<>();
		// Prueba parcial del módulo
		//this.pruebas();
		//this.printCentros(this.centrosConMasAlarmas());
		
	}
	
	// Patrón de diseño Singleton
	
	public static GestorEstadisticas obtenerGestorEstadisticas() {
		if(instancia == null) {
			instancia = new GestorEstadisticas();
		}
		return instancia;
	}
	
	
	// Obtencion del numero de alarmas resueltas
	@Override
	public Integer alarmasResueltas() {
		try {
	            // Excepciones distintas
		    if (this.listaAlarmasResueltas == null) {
				throw new NullPointerException("(NULL): No existen alarmas resueltas.");
		    }
		    if (this.listaAlarmasResueltas.size() == 0) {
				throw new ArrayIndexOutOfBoundsException("No existen alarmas resueltas.");
		    }
		    // Devolver el tamaño de la lista
		    return Integer.valueOf(this.listaAlarmasResueltas.size());
		} 
		catch (Exception e) {
		    System.out.println("No se pueden procesar las alarmas. ");
		    e.printStackTrace();
		    return null;
		}
	}
	// Actualizacion del array de alarmas resueltas
	@Override
	public void addAlarmaResuelta(Alarma a) throws NullPointerException{
		this.listaAlarmasResueltas.add(a);
		try {
          	// Excepciones distintas
		    if (a == null) {
				throw new NullPointerException("(NULL).");
		    }
		    if (a.getIdAlarma() == null) {
		    	throw new NullPointerException("(NULL): El identificador de la alarma es incorrecto");
		    }
		    // Añadir la alarma
	    	this.listaAlarmasResueltas.add(a);
		} 
		catch (Exception e) {
		    System.out.println("La alarma no se puede añadir. ");
		    e.printStackTrace();
		}

	}
	
	// Obtencion de la lista de centros con más ocurrencias
	@Override
	public ArrayList<String> centrosConMasAlarmas() {
		ArrayList<String> centrosConMasAlarmas = new ArrayList<String>();
		ArrayList<String> centrosConMenosAlarmas = this.centrosConMenosAlarmas();
		// Mostrar el arrayList a la inversa
		for(int i = centrosConMenosAlarmas.size() - 1 ; i >= 0; i --)
			centrosConMasAlarmas.add(centrosConMenosAlarmas.get(i));
		return centrosConMasAlarmas;
	}
	

	// Obtener la lista de centros con menos ocurrencias
	@Override
	public ArrayList<String> centrosConMenosAlarmas() {
		HashMap<String, Integer> mapaCentros = new HashMap<String, Integer>();
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		ArrayList<String> centros = new ArrayList<String>();
		
		// Relacionar centros-ocurrencias de alarmas
		if (this.listaAlarmasResueltas == null) {
			System.out.println("Lista de alarmas nula");
			return null;
		}
		// Obtención de la lista de centros
		for(Alarma alarma: this.listaAlarmasResueltas) {
			// Si el centro no ha sido añadido hasta ahora
			centros.add(alarma.getCentro());			
		}		
		// Obtencion de las coincidencias
		for(String centro: centros) {
				if(!mapaCentros.containsKey(centro)) {
					Integer occurrences = Integer.valueOf(Collections.frequency(centros, centro));
					// Obtención del mapa
					mapaCentros.put(centro, occurrences);
					
				}
				else{
					Integer occurrences = Integer.valueOf(Collections.frequency(centros, centro) + 1);
					mapaCentros.replace(centro, occurrences);
				}
		}
		// Obtención del array a ordenar por quicksort
		for(String centro: mapaCentros.keySet())
			ocurrencias.add(mapaCentros.get(centro));
		/*
		for(String centro: mapaCentros.keySet()) {
			System.out.println("Centro: " + centro + ", Ocurrencias: " + mapaCentros.get(centro));
		}	
		*/
		// Ordenar ocurrencias
		ocurrencias = this.ordenar(ocurrencias);
		// Ordenar centros
		ArrayList<String> centrosOrdenados = new ArrayList<String>();
		for(int i = 0; i < ocurrencias.size(); i++) {
			for(String centro: mapaCentros.keySet()) {
				
				// Comprobacion no es repetido y obtencion de los centros cuyas ocurrencias se corresponden con la lista
				if((!centrosOrdenados.contains(centro)) && (mapaCentros.get(centro) == ocurrencias.get(i))){
					centrosOrdenados.add(centro);
				}
					
			}
		}
		return centrosOrdenados;
	}


	@Override
	public Float accionesPorAlarmaPromedio() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer accionesTotales() {
		// TODO Auto-generated method stub
		return null;
	}
	// Obtencion de la estadistica de duracion de verificaciones
	@Override
	public Float duracionMediaAccion() {
	    float sumaParcial = 0, media = 0;
	    for(Alarma alarma: this.listaAlarmasResueltas) {
	    	    sumaParcial = 0;
		    for(Accion accion: alarma.getAcciones()) {
		    	Date diff = new Date(accion.getFechaFin().getTime() - accion.getFechaInicio().getTime());
		    	sumaParcial += diff.getTime();
		    }
		    // Calculo de la media parcial para las acciones de cada alarma
		    sumaParcial = sumaParcial /alarma.getAcciones().size();
		    media = media + sumaParcial;
	    }
	    //return Float.valueOf(media);
		return null;
	}
	
	
	// Obtencion de la estadistica de duracion de alarmas
	// Completar diferencia en horas, segundos, días, meses, años
	@Override
	public Float duracionMediaAlarma() {
	    long sum = 0;
	    for(Alarma alarma: this.listaAlarmasResueltas) {
	    	Date diff = new Date(alarma.getFechaCierre().getTime() - alarma.getFechaInicio().getTime());
	    	sum += diff.getTime();
	    }
	   // return Float.valueOf(sum / this.listaAlarmasResueltas.size());
		return null;
	}
	


	
	public static Date parseDateFormat(String s) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

		// String dateInString = "7-Jun-2013";
		try {
			Date date = formatter.parse(s);
			if(date == null)	
				throw new ParseException("Error al parsear la fecha", 1);
			
			return date;
		}
		catch (Exception e) {
			System.out.println("La alarma no se puede añadir. ");
		    e.printStackTrace();
		    return null;
		}
		
	}
	// Función para establecer parámetros de prueba parcial
	public void pruebas() throws ParseException{
		ArrayList<Accion> accionesAlarma1 = new ArrayList<Accion>();
		ArrayList<Accion> accionesAlarma2 = new ArrayList<Accion>();
		ArrayList<Accion> accionesAlarma3 = new ArrayList<Accion>();
		ArrayList<Accion> accionesAlarma4 = new ArrayList<Accion>();
		ArrayList<Alarma> alarmas = new ArrayList<Alarma>();
		Date date = new Date();
		Accion accion1 = new Accion(new Date(), "salir");
		Accion accion2 = new Accion(new Date(), "correr");
		Accion accion3 = new Accion(new Date(), "saltar");
		Accion accion4 = new Accion(new Date(), "salir");
		Accion accion5 = new Accion(new Date(), "irse");
		Accion accion6 = new Accion(new Date(), "evacuar");
		Accion accion7 = new Accion(new Date(), "omitir");
		Accion accion8 = new Accion(new Date(), "cerrar");
		Accion accion9 = new Accion(new Date(), "asegurar");
		accionesAlarma1.add(accion1);
		accionesAlarma1.add(accion2);
		accionesAlarma2.add(accion3);
		accionesAlarma2.add(accion4);
		accionesAlarma3.add(accion5);
		accionesAlarma3.add(accion6);
		accionesAlarma4.add(accion7);
		accionesAlarma4.add(accion8);
		accionesAlarma4.add(accion9);
		Alarma alarma1 = new Alarma("alarmaA1", "incendio", "mates", parseDateFormat("6-Jun-2013"), accionesAlarma1);
		alarma1.setFechaCierre(parseDateFormat("7-Jun-2013"));
		Alarma alarma2 = new Alarma("alarmaA2", "incendio", "mates", parseDateFormat("6-Jun-2013"), accionesAlarma2);
		alarma2.setFechaCierre(parseDateFormat("7-Jun-2013"));
		Alarma alarma3 = new Alarma("alarmaA1", "incendio", "bio", parseDateFormat("4-Jun-2013"), accionesAlarma3);
		alarma3.setFechaCierre(parseDateFormat("4-Jun-2013"));
		Alarma alarma4 = new Alarma("alarmaA2", "incendio", "info", parseDateFormat("5-Jun-2013"), accionesAlarma4);
		alarma4.setFechaCierre(parseDateFormat("6-Jun-2013"));
		alarmas.add(alarma1);
		alarmas.add(alarma2);
		alarmas.add(alarma3);
		alarmas.add(alarma4);
		this.listaAlarmasResueltas = alarmas;
			
		
	}
	

	
	
	
	// Mostrar la lista de centros con más ocurrencias
	public void printCentros(ArrayList<String> centrosConMasAlarmas){
		System.out.println("Centros con mas alarmas: ");
		for(String centro : centrosConMasAlarmas){
			System.out.println(" " + centro + " ");
		}
	}
	
	// ----- Ordenacion del array con el algoritmo de quicksort()
	
	public ArrayList<Integer> ordenar(ArrayList<Integer> elementos){
	    //System.out.println("Vector a ordenar: ");
	    //this.printArray(elementos);
	     
	    this.quickSort(elementos, 0, elementos.size() - 1);
	    //System.out.println("Sorted array: ");
	    //this.printArray(elementos);
	    
	    return elementos;
	}

	// Función auxiliar de intercambio de elementos
	public void swap(ArrayList<Integer> elementos , int i, int j){
	    int temp = elementos.get(i);
	    elementos.set(i,  elementos.get(j));
	    elementos.set(j, temp);
	}
 
	// Función que toma el último elemento como pivote, lo coloca en su posición correcta en un array ordenado
	// y coloca todos los elementos menores que ese pivote a la izquierda y los mayores a la derecha
	// @param low es el índice más bajo
	// @param high es el índice más alto (n-1)
	public int partition(ArrayList<Integer> elementos, int low, int high){
	    int pivot = elementos.get(high);
	     
	    // Inicialización del índice donde se colocará el elemento menor a -1 
	    int i = (low - 1);
	    // Trasponer los elementos desde j = low a high-1
	    for(int j = low; j <= high - 1; j++){
	         
	        // Si el elemento actual es menor que el pivote, se traspone
	        if (elementos.get(j) < pivot){
	             
	            // Incremento del índice donde se encuentra el menor elemento
	            i++;
	            // Trasponer elemento menor
	            this.swap(elementos, i, j);
	        }
	    }
	    // Trasponer último elemento
	    this.swap(elementos, i + 1, high);
	    return (i + 1);
	}
	
	// Función del algoritmo quicksort
	// @param elementos lista a ordenar
	// @param low índice inicio
	// @param high índice final
	public void quickSort(ArrayList<Integer> elementos, int low, int high){
	    int pi;
	    if (low < high)
	    {	         
	        // pi es el índice sobre el que se particiona el array a ordenar
	        pi = this.partition(elementos, low, high);
	 
	        // Estrategia divide y vencerás: ordenar a la izqda y dcha de pi
	        this.quickSort(elementos, low, pi - 1);
	        this.quickSort(elementos, pi + 1, high);
	    }
	}
 
	// Funcion de imprimir un array
	public void printArray(ArrayList<Integer> elementos){
		int size = elementos.size();
	    for(int i = 0; i < size; i++)
	        System.out.print(elementos.get(i) + " ");
	         
	    System.out.println();
	}

}


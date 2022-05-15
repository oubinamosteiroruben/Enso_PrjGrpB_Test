package estadisticas;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import alarmas.Alarma;
import org.mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class TestCentrosConMenosAlarmasCB {

	IGestorEstadisticas ge;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// TODO: poner en el informe que no se ha podido usar este método para obtener la instancia porque 
		// se me guardaba de una vez para otra y salían mal las pruebas. Como no existe método para eliminar
		// alarmas se tiene que hacer así
		// ge = (IGestorEstadisticas) GestorEstadisticas.obtenerGestorEstadisticas();
		ge = (IGestorEstadisticas) new GestorEstadisticas();
	}

	@AfterEach
	void tearDown() throws Exception {
		ge = null;
	}
	


	@Test
	@DisplayName("PCB_02_Caso_01. Prueba con listaAlarmasResueltas vacía.")
	// con este CE no entra en ningun bucle for, ni en los ifs
	void PCB_02_Caso_01() {
		// Preparación
		ArrayList<String> esperado = new ArrayList<>();
		
		// Ejecución
		ArrayList<String> resultado = ge.centrosConMenosAlarmas();
		
		// Evaluación
		assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
	}
	
	
	@Test
	@DisplayName("PCB_02_Caso_02. Prueba con una sola alarma resuelta, y creación correcta de la alarma.")
	// con este CE entra una sola vez en cada bucle, y la alarma está correctamente creada y finalizada
	void PCB_02_Caso_02() {
		// Preparación
		ArrayList<String> esperado = new ArrayList<>();
		esperado.add("ade");
		
		Alarma alarma = new Alarma("alarmaA1", "incendio", "ade", null, null);
		ge.addAlarmaResuelta(alarma);
		
		// Ejecución
		ArrayList<String> resultado = ge.centrosConMenosAlarmas();
		
		// Evaluación
		assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
	}
	
	
	@Test
	@DisplayName("PCB_02_Caso_03. Prueba con una sola alarma resuelta, y creación incorrecta de la alarma.")
	// con este CE entra una sola vez en cada bucle, y la alarma está mal creada (centro = null)
	void PCB_02_Caso_03() {
		// Preparación
		ArrayList<String> esperado = new ArrayList<>();
		esperado.add(null);
		
		Alarma alarma1 = new Alarma("alarmaA1", "incendio", null, null, null);
		ge.addAlarmaResuelta(alarma1);
		
		// Ejecución
		ArrayList<String> resultado = ge.centrosConMenosAlarmas();
		
		// Evaluación
		assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
	}
	
	
	// TODO: Se están contando mal las alarmas, crear un caso de prueba que haga que mete. Mirar el main de test.
	
	
	@Nested
	@DisplayName("Pruebas de con varias alarmas")
	class PruebasVariasAlarmas{
		
		@Test
		@DisplayName("PCB_02_Caso_04. Varias alarmas con un orden claro de entrada y salida.")
		void PCB_02_Caso_04() {
			// Preparación
			ArrayList<String> esperado = new ArrayList<>();
			esperado.add("ade");
			esperado.add("etse");
			esperado.add("medicina");
			
			Alarma alarma1 = new Alarma("alarmaA1", "tipo", "ade", null, null);
			Alarma alarma2 = new Alarma("alarmaA2", "tipo", "etse", null, null);
			Alarma alarma3 = new Alarma("alarmaA3", "tipo", "etse", null, null);
			Alarma alarma4 = new Alarma("alarmaA4", "tipo", "medicina", null, null);
			Alarma alarma5 = new Alarma("alarmaA5", "tipo", "medicina", null, null);
			Alarma alarma6 = new Alarma("alarmaA6", "tipo", "medicina", null, null);
			ge.addAlarmaResuelta(alarma1);
			ge.addAlarmaResuelta(alarma2);
			ge.addAlarmaResuelta(alarma3);
			ge.addAlarmaResuelta(alarma4);
			ge.addAlarmaResuelta(alarma5);
			ge.addAlarmaResuelta(alarma6);
			
			// Ejecución
			ArrayList<String> resultado = ge.centrosConMenosAlarmas();
			
			// Evaluación
			assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
		}
		
		
		@Test
		@DisplayName("PCB_02_Caso_05. Varias alarmas con un orden claro de salida, pero entrada desordenada.")
		void PCB_02_Caso_05() {
			// Preparación
			ArrayList<String> esperado = new ArrayList<>();
			esperado.add("medicina");
			esperado.add("etse");
			esperado.add("ade");
			
			Alarma alarma1 = new Alarma("alarmaA1", "tipo", "etse", null, null);
			Alarma alarma2 = new Alarma("alarmaA2", "tipo", "ade", null, null);
			Alarma alarma3 = new Alarma("alarmaA3", "tipo", "etse", null, null);
			Alarma alarma4 = new Alarma("alarmaA4", "tipo", "medicina", null, null);
			Alarma alarma5 = new Alarma("alarmaA5", "tipo", "ade", null, null);
			Alarma alarma6 = new Alarma("alarmaA6", "tipo", "ade", null, null);
			ge.addAlarmaResuelta(alarma1);
			ge.addAlarmaResuelta(alarma2);
			ge.addAlarmaResuelta(alarma3);
			ge.addAlarmaResuelta(alarma4);
			ge.addAlarmaResuelta(alarma5);
			ge.addAlarmaResuelta(alarma6);
			
			// Ejecución
			ArrayList<String> resultado = ge.centrosConMenosAlarmas();
			
			// Evaluación
			assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
		}
		
		// funcion auxiliar para poder determinar si dos arrays son iguales
		boolean arraysIguales(ArrayList<String> array1, ArrayList<String> array2) {
			if(array1.size() != array2.size())
				return false;
			else {
				for(int i = 0; i < array1.size(); i++) {
					if(!array1.get(i).equals(array2.get(i)))
						return false;
				}
				return true;
			}
		}
		
		@Test
		@DisplayName("PCB_02_Caso_06. Varias alarmas con dos centros con el mismo número de alarmas, orden alfabético o o alfabético.")
		void PCB_02_Caso_06() {
			// Preparación 1
			ArrayList<String> esperado1 = new ArrayList<>();
			esperado1.add("ade");
			esperado1.add("etse");
			esperado1.add("medicina");
			Alarma alarma1_1 = new Alarma("alarmaA1", "tipo", "ade", null, null);
			Alarma alarma2_1 = new Alarma("alarmaA2", "tipo", "etse", null, null);
			Alarma alarma3_1 = new Alarma("alarmaA3", "tipo", "medicina", null, null);
			Alarma alarma4_1 = new Alarma("alarmaA4", "tipo", "etse", null, null);
			Alarma alarma5_1 = new Alarma("alarmaA5", "tipo", "medicina", null, null);
			ge.addAlarmaResuelta(alarma1_1);
			ge.addAlarmaResuelta(alarma2_1);
			ge.addAlarmaResuelta(alarma3_1);
			ge.addAlarmaResuelta(alarma4_1);
			ge.addAlarmaResuelta(alarma5_1);
			
			// Ejecución1
			ArrayList<String> resultado1 = ge.centrosConMenosAlarmas();
			
			
			// Preparación 2
			ge = (IGestorEstadisticas) new GestorEstadisticas(); // es necesario reiniciar el gestor
			ArrayList<String> esperado2 = new ArrayList<>();
			esperado2.add("topologia");
			esperado2.add("criminologia");
			esperado2.add("podologia");
			Alarma alarma1_2 = new Alarma("alarmaA1", "tipo", "criminologia", null, null);
			Alarma alarma2_2 = new Alarma("alarmaA2", "tipo", "podologia", null, null);
			Alarma alarma3_2 = new Alarma("alarmaA3", "tipo", "topologia", null, null);
			Alarma alarma4_2 = new Alarma("alarmaA4", "tipo", "criminologia", null, null);
			Alarma alarma5_2 = new Alarma("alarmaA5", "tipo", "podologia", null, null);
			ge.addAlarmaResuelta(alarma1_2);
			ge.addAlarmaResuelta(alarma2_2);
			ge.addAlarmaResuelta(alarma3_2);
			ge.addAlarmaResuelta(alarma4_2);
			ge.addAlarmaResuelta(alarma5_2);
			
			// Ejecución 2
			ArrayList<String> resultado2 = ge.centrosConMenosAlarmas();
			
			
			// Evaluación
			boolean alfabetico = arraysIguales(esperado1, resultado1) && arraysIguales(esperado2, resultado2);
			boolean noAlfabetico = !arraysIguales(esperado1, resultado1) && !arraysIguales(esperado2, resultado2);
			boolean resultado = alfabetico || noAlfabetico;
			assertTrue(resultado, "La salida no se puede predecir, no está en orden alfabético ni NO alfabético");
		}
		
		// TODO: decir que no se puede predecir la salida que va a tener, ya que en caso tener dos centros el mismo número de alarmas,
		// no se ha definido ningún criterio adicional (como un orden alfabético). El orden que se sigue es el que ha elegido el
		// hashmap, que no se puede predecir (es su función, gracias a la función hash).
	}
	
	@Test
	@DisplayName("PCB_02_Caso_07. Mock a Alarma.getCentro")
	void PCB_02_Caso_07() {	
		Alarma alarmaMocked = Mockito.mock(Alarma.class);
		
		// Preparación
		ArrayList<String> esperado = new ArrayList<>();
		esperado.add("medicina");
		esperado.add("ade");
		esperado.add("etse");
		
		// esto es necesario para evitar excepciones
		Mockito.when(alarmaMocked.getIdAlarma()).thenReturn("AlarmaA1");
		ge.addAlarmaResuelta(alarmaMocked);
		ge.addAlarmaResuelta(alarmaMocked);
		ge.addAlarmaResuelta(alarmaMocked);
		ge.addAlarmaResuelta(alarmaMocked);
		ge.addAlarmaResuelta(alarmaMocked);
		ge.addAlarmaResuelta(alarmaMocked);
		
		// a pesar de que el orden real tendría que ser totalmente opuesto, 
		// Mockito nos permite ponerlo a nuestro gusto
		Mockito.when(alarmaMocked.getCentro()).thenReturn("medicina").thenReturn("ade").thenReturn("ade").thenReturn("etse");
		
		// Ejecución
		ArrayList<String> resultado = ge.centrosConMenosAlarmas();
		
		// Evaluación
		assertEquals(esperado, resultado, "Los centros con menos alarmas no son iguales");
			
	}

}

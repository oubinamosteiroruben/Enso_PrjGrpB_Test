package alarmas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.*;


class AlarmaTestCN {

	IGestorAlarmas ga;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ga = GestorAlarmas.obtenerGestorAlarmas();
	}

	@AfterEach
	void tearDown() throws Exception {
		ga = null;
	}
	
	
	@Test
	@DisplayName("PCN_02_Caso_01. Prueba de procesarAlarma. Alarma válida")
	void testAlarmaCorrecta() {
		// Preparación
		Protocolo protocolo = ga.crearProtocolo("FUEGO", "EQUIPO_FUEGO", 
				Arrays.asList("evacuar a la gente", "correr", "intentar no morir", "esperar a los bomberos"));
		ga.addProtocolo(protocolo);
		Alarma alarma = ga.crearAlarma("FUEGO", "etse", new Date());
		
		// Ejecución
		boolean resultado = ga.procesarAlarma(alarma);
		
		// Evaluación
		assertTrue(resultado);
	}
	
	
	@Test
	@DisplayName("PCN_02_Caso_02. Prueba de procesarAlarma. Alarma inválida")
	void testAlarmaNull() {
		// Preparación
		Alarma alarma = null;
		
		// Ejecución
		//boolean resultado = ga.procesarAlarma(null);
		
		// Evaluación
		assertThrows(IllegalArgumentException.class, ()->{ga.procesarAlarma(alarma);});
	}
	

}

package alarmas;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class TestAddProtocolo {
	GestorAlarmas ga;
	List<String> mensajes;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		mensajes = List.of("mensaje1", "mensaje2");
		ga = GestorAlarmas.obtenerGestorAlarmas();
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	@DisplayName("PCB_03_Caso_01")
	@Test
	void testAddProtocolo() {
		Protocolo prot1 = ga.crearProtocolo("FUEGO", "EQUIPO_FUEGO", mensajes);
		boolean esperado = true;
		boolean real = ga.addProtocolo(prot1);
		
		assertAll("Prueba multiple de true y equals",
				() -> assertEquals(esperado, real, "Fallo al añadir protocolo"),
				() -> assertTrue(real, "Fallo al añadir protocolo")
		);
	}
	
	@Nested
	@DisplayName("Pruebas Excepciones")
	class PruebasExcepciones{
	
		@DisplayName("PCB_03_Caso_02")
		@Test
		void testAddProtocolo2() {
			
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.addProtocolo(null);});
			assertEquals("Protocolo no válido", i.getMessage(), "Fallo al añadir equipo");
		}	
	
		@DisplayName ("PCB_03_Caso_03")
		@Test
		void testAddProtocolo3() {
			Protocolo prot1 = new Protocolo("PROTO_1","FUEGO", "EQUIPO_FUEGO", mensajes);
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.addProtocolo(prot1);});
			
			assertEquals("ID de protocolo no válida", i.getMessage(), "Fallo al anhadir protocolo");
		}
		
		@DisplayName ("PCB_03_Caso_04")
		@Test
		void testAddProtocolo4() {
			Protocolo prot1 = new Protocolo("PROT_J","FUEGO", "EQUIPO_FUEGO", mensajes);
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.addProtocolo(prot1);});
			
			assertEquals("ID de protocolo no válida", i.getMessage(), "Fallo al anhadir protocolo");
		}
	
		@DisplayName ("PCB_03_Caso_05")
		@Test
		void testAddProtocolo5() {
			Protocolo p1 = ga.crearProtocolo("AGUA", "EQUIPO_AGUA", mensajes);
			Protocolo p2 = p1;
			ga.addProtocolo(p1);
			
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.addProtocolo(p2);});
			
			assertEquals("Ya existe un protocolo con la ID "+p1.getIdProtocolo(), i.getMessage(), i.getMessage());
		}
		
		@DisplayName ("PCB_03_Caso_06")
		@Test
		void testAddProtocolo6() {
			Protocolo prot1 = ga.crearProtocolo("AIRE", "EQUIPO_AIRE", mensajes);
			Protocolo p2 = ga.crearProtocolo(prot1.getTipoAlarma(), prot1.getTipoEquipo(), mensajes);
			ga.addProtocolo(prot1);
			
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.addProtocolo(p2);});
			
			assertEquals("Ya existe un protocolo para el tipo de alarma AIRE", i.getMessage(), "Fallo al anhadir protocolo");
		}
	}
	
	@Mock
	private Protocolo prot1;
	@DisplayName("PCB_03_Caso_07")
	@Test
	void testAddProtocoloMockito() {
		prot1 = ga.crearProtocolo("FUEGO", "EQUIPO_FUEGO", mensajes);
		ga = Mockito.mock(GestorAlarmas.class);
		boolean real = false;
		when(ga.addProtocolo(prot1)).thenReturn(real=true);
		assertEquals(true,real);
	}
	
	 
	

}

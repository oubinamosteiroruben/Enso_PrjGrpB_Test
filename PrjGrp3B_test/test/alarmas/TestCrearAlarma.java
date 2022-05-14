package alarmas;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.*;

class TestCrearAlarma {

	//Fixtures
	GestorAlarmas ga;
	Protocolo protocol;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ga = GestorAlarmas.obtenerGestorAlarmas();
		List<String> acciones = new ArrayList<>();
		acciones.add("Encender alarma");
		acciones.add("Desalojar edificio");
		acciones.add("Avisar bomberos");
		acciones.add("Verificar daños");
		protocol = ga.crearProtocolo("FUEGO", "EQUIPO_FUEGO", acciones);
	}

	@AfterEach
	void tearDown() throws Exception {
		ga.eliminarProtocolo(protocol);
	}
	
	@DisplayName("PCB_05_Caso_01")
	@Test
	void PCB_05_Caso_01() {
		//Arrange
		String tipo = "FUEGO";
		String centro = "etse";
		Date fechaInicio = new Date();

		ga.addProtocolo(protocol);
		
		//Act
		Alarma alarm = ga.crearAlarma(tipo, centro, fechaInicio);
		String tipo_real = alarm.getTipo();
		String centro_real = alarm.getCentro();
		Date fechaInicio_real = alarm.getFechaInicio();
		
		//Assert
		assertAll(
				()->assertEquals(tipo, tipo_real,"El tipo guardado no es el indicado"),
				()->assertEquals(centro, centro_real,"El centro guardado no es el indicado"),
				()->assertEquals(fechaInicio, fechaInicio_real,"La fechaInicio guardada no es la indicada")
		);
	}
	
	@Nested
	@DisplayName("Pruebas de excepciones")
	class PruebasExcepcion{
		@DisplayName("PCB_05_Caso_02")
		@Test
		void PCB_05_Caso_02() {
			//Arrange
			String tipo = null;
			String centro = "etse";
			Date fechaInicio = new Date();
	
			ga.addProtocolo(protocol);
			
			//Act
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.crearAlarma(tipo,centro,fechaInicio);});
			
			//Assert
			assertEquals("Argumentos incorrectos, no se pudo crear la alarma", i.getMessage(), "Fallo al crear alarma con tipo = null");
		}
		
		@DisplayName("PCB_05_Caso_03")
		@Test
		void PCB_05_Caso_03() {
			//Arrange
			String tipo = "FUEGO";
			String centro = null;
			Date fechaInicio = new Date();
	
			ga.addProtocolo(protocol);
			
			//Act
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.crearAlarma(tipo,centro,fechaInicio);});
			
			//Assert
			assertEquals("Argumentos incorrectos, no se pudo crear la alarma", i.getMessage(), "Fallo al crear alarma con centro = null");
		}
		
		@DisplayName("PCB_05_Caso_04")
		@Test
		void PCB_05_Caso_04() {
			//Arrange
			String tipo = "FUEGO";
			String centro = "etse";
			Date fechaInicio = null;
	
			ga.addProtocolo(protocol);
			
			//Act
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.crearAlarma(tipo,centro,fechaInicio);});
			
			//Assert
			assertEquals("Argumentos incorrectos, no se pudo crear la alarma", i.getMessage(), "Fallo al crear alarma con fechaInicio = null");
		}
		
		@DisplayName("PCB_05_Caso_05")
		@Test
		void PCB_05_Caso_05() {
			//Arrange
			String tipo = "FUEGO";
			String centro = "etse";
			Date fechaInicio = new Date(-3000);

			ga.addProtocolo(protocol);
			
			//Act
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.crearAlarma(tipo,centro,fechaInicio);});
			
			//Assert
			assertEquals("Argumentos incorrectos, no se pudo crear la alarma", i.getMessage(), "Fallo al crear alarma porque fechaInicio.before(new Date(0))");
		}
		
		@DisplayName("PCB_05_Caso_06")
		@Test
		void PCB_05_Caso_06() {
			//Arrange
			String tipo = "FUEGO";
			String centro = "etse";
			Date fechaInicio = new Date();
			
			//Act
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ga.crearAlarma(tipo,centro,fechaInicio);});
			
			//Assert
			assertEquals("No existen protocolos para el tipo de alarma FUEGO", i.getMessage(), "Fallo al crear alarma sin protocolos para su tipo");
		}
	}
	
	@Nested
	@DisplayName("Pruebas del bucle for")
	class PruebasFor{
		
		@DisplayName("PCB_05_Caso_07")
		@Test
		void PCB_05_Caso_07() {
			//Arrange
			String tipo = "AGUA";
			String centro = "etse";
			Date fechaInicio = new Date();
			GestorAlarmas ga2 = Mockito.mock(GestorAlarmas.class);
			Protocolo prot;
			
			List<String> acciones = new ArrayList<>();
			int num_acciones = acciones.size();
			prot = new Protocolo("PROT_10","AGUA","EQUIPO_AGUA",acciones);
			
			Mockito.when(ga2.addProtocolo(prot)).thenReturn(true);
			
			//Act
			Alarma alarm = ga.crearAlarma(tipo,centro,fechaInicio);
			List<Accion> acc = alarm.getAcciones();
			int num_acciones_real = acc.size();
			
			//Assert
			assertEquals(num_acciones, num_acciones_real, "Se han introducido acciones");
		}
		
		@DisplayName("PCB_05_Caso_08")
		@Test
		void PCB_05_Caso_08() {
			//Arrange
			String tipo = "AIRE";
			String centro = "etse";
			Date fechaInicio = new Date();
			
			List<String> acciones = new ArrayList<>();
			acciones.add("Encender alarma");
			Protocolo prot = ga.crearProtocolo("AIRE", "EQUIPO_AIRE", acciones);
			int num_acciones = acciones.size();
			
			ga.addProtocolo(prot);
			
			//Act
			Alarma alarm = ga.crearAlarma(tipo,centro,fechaInicio);
			List<Accion> acc = alarm.getAcciones();
			int num_acciones_real = acc.size();
			
			//Assert
			assertEquals(num_acciones, num_acciones_real, "No se ha introducido solo 1 accion");
		}
		
		@DisplayName("PCB_05_Caso_09")
		@Test
		void PCB_05_Caso_09() {
			//Arrange
			String tipo = "TIERRA";
			String centro = "etse";
			Date fechaInicio = new Date();
			
			List<String> acciones = new ArrayList<>();
			acciones.add("Encender alarma");
			acciones.add("Desalojar edificio");
			Protocolo prot = ga.crearProtocolo("TIERRA", "EQUIPO_TIERRA", acciones);
			int num_acciones = acciones.size();
			
			ga.addProtocolo(prot);
			
			//Act
			Alarma alarm = ga.crearAlarma(tipo,centro,fechaInicio);
			List<Accion> acc = alarm.getAcciones();
			int num_acciones_real = acc.size();
			
			//Assert
			assertEquals(num_acciones, num_acciones_real, "No se han introducido solo 2 acciones");
		}
	}
	
}

package equipos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import alarmas.Alarma;
import alarmas.GestorAlarmas;
import alarmas.Protocolo;
import usuarios.GestorUsuarios;
import usuarios.Usuario;

import org.mockito.*;
//
class Prueba_01 {
	GestorEquipos ge; 
	ArrayList<Usuario> miembros;
	GestorUsuarios gu;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		miembros = new ArrayList<>();
		ge = GestorEquipos.obtenerGestorEquipos();
		gu = GestorUsuarios.obtenerGestorUsuarios();
	}

	@AfterEach
	void tearDown() throws Exception {
		for(int i=0;i < gu.obtenerListaUsuarios().size();i++) {
			gu.eliminarUsuario(gu.obtenerListaUsuarios().get(i));
		}
	}
	
	
	
	@Nested
	@DisplayName("PCB_01")
	class PruebasCajaBlanca{
		
		@DisplayName("PCB_01_Caso_01")
		@Test
		void PCB_01_Caso_01() {
			//Arrange
			Usuario usuario6 = gu.crearUsuario("Marcos", "39945566Z", 123456789, 
					"marco@gmail.com", "etse", "aulaA3","activa",true);
		    ArrayList<Usuario> miembros1 = new ArrayList<>();
			miembros1.add(usuario6);
			Equipo equipo1 = ge.crearEquipo(miembros1, "EQUIPO_FUEGO");
			ge.addEquipo(equipo1);
			Equipo equipo2 = ge.crearEquipo(miembros1, "EQUIPO_AGUA");
			boolean esperado = true;
			//Act
			boolean real = ge.addEquipo(equipo2);
			//Assert
			assertAll("Prueba multiple de true y equals",
					() -> assertEquals(esperado, real, "Fallo al añadir equipo"),
					() -> assertTrue(real, "Fallo al añadir equipo")
			);	
		}
		
		@DisplayName("PCB_01_Caso_02")
		@Test
		void PCB_01_Caso_02() {
			IllegalArgumentException i = 
				assertThrows(IllegalArgumentException.class, ()->{ge.addEquipo(null);});
			assertEquals("Equipo no válido", i.getMessage(), "Fallo al añadir equipo");
		}
		
		@DisplayName("PCB_01_Caso_03")
		@Test
		void PCB_01_Caso_03() {
			Equipo equipo = new Equipo("EQUI_1", miembros, "EQUIPO_FUEGO");
			IllegalArgumentException i = 
				assertThrows(IllegalArgumentException.class, ()->{ge.addEquipo(equipo);});
			assertEquals("ID de equipo no válida!", i.getMessage(), "Fallo al añadir equipo");
		}
		
		@DisplayName("PCB_01_Caso_04")
		@Test
		void PCB_01_Caso_04() {
			//Arrange
			Usuario usuario4 = gu.crearUsuario("Lydia", "33445566Z", 123456789, 
					"lidi@gmail.com", "etse", "aulaA2","activa",true);

		    ArrayList<Usuario> miembros1 = new ArrayList<>();
			miembros1.add(usuario4);
			Equipo equipo1 = ge.crearEquipo(miembros1, "EQUIPO_FUEGO");
			ge.addEquipo(equipo1);
			Equipo equipo2 = equipo1;
			//Act, Assert
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ge.addEquipo(equipo2);});
			assertSame("El equipo ya existe, no se puede crear el equipo", i.getMessage(), "Fallo al añadir equipo");
		}

		@DisplayName("PCB_01_Caso_05")
		@Test
		void PCB_01_Caso_05() {
			Equipo equipo = new Equipo("EQ_  ", miembros, "EQUIPO_FUEGO");
			IllegalArgumentException i = 
					assertThrows(IllegalArgumentException.class, ()->{ge.addEquipo(equipo);});
			assertEquals("ID de equipo no válida!", i.getMessage(), "Fallo al añadir equipo");
		}
		
	
		@DisplayName("PCB_01_Caso_06")
		@Test
		void PCB_01_Caso_06() {
			//Arrange
			Usuario usuario4 = gu.crearUsuario("Hugo", "33444566Z", 123456789, 
					"hugo@gmail.com", "etse", "aulaA2","activa",true);
			Usuario usuario5 = gu.crearUsuario("Sofia", "33446966Z", 987654321, 
					"sofia@gmail.com", "etse", "aulaA3","activa",true);
		    ArrayList<Usuario> miembros1 = new ArrayList<>();
			miembros1.add(usuario4);
			miembros1.add(usuario5);
			Equipo equipo1 = ge.crearEquipo(miembros1, "EQUIPO_FUEGO");
			ge.addEquipo(equipo1);
			Equipo equipo2 = equipo1;
			//Act, Assert
			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ge.addEquipo(equipo2);});
			assertSame("El equipo ya existe, no se puede crear el equipo", i.getMessage(), "Fallo al añadir equipo");
		}
		
		@Mock 
		private Equipo equipo7;
		@DisplayName("PCB_01_Caso_07")
		@Test
		void testAddEquipoMockito() {
			//Arrange
			Usuario usuario6 = gu.crearUsuario("Iván", "39945566Z", 123456789, 
					"ivan@gmail.com", "etse", "aulaA3","activa",true);
			gu.addUsuario(usuario6);
		    ArrayList<Usuario> miembros1 = new ArrayList<>();
			miembros1.add(usuario6);
			
			equipo7=ge.crearEquipo(miembros1, "EQUIPO_AGUA");
			ge = Mockito.mock(GestorEquipos.class);
			boolean real=false; 
			when(ge.addEquipo(equipo7)).thenReturn(real=true);

			assertEquals(true, real);
			
		}
	}
	

	
	
	

	@Nested
	@DisplayName("PCN_01")
	class PruebasCajaNegra{
	
	@DisplayName("PCN_01_Caso_01")
	@Test
		void PCN_01_Caso_01() {
			GestorUsuarios gu = GestorUsuarios.obtenerGestorUsuarios();
			GestorAlarmas ga =  GestorAlarmas.obtenerGestorAlarmas();
			Usuario u = gu.crearUsuario("Monya", "33445566Z", 123456789, 
					"monya@gmail.com", "etse", "aulaA2","activa",true);
			gu.addUsuario(u);
			
			Protocolo protocolo = ga.crearProtocolo("FUEGO", "EQUIPO_FUEGO", 
					Arrays.asList("Evacuar a la gente", "No correr", "Esperar a los bomberos"));
			ga.addProtocolo(protocolo);
			
			Alarma a = ga.crearAlarma("FUEGO", "matemáticas", new Date());
		
			boolean esperado = true;
			
			boolean real = gu.declararAlarma(u, a);
			assertSame(esperado, real, "Fallo al declarar alarma");
		}
	
		
	    @DisplayName("PCN_01_Caso_02")
		@Test
		void PCN_01_Caso_02() {
			GestorAlarmas ga = GestorAlarmas.obtenerGestorAlarmas();
			Protocolo protocolo = ga.crearProtocolo("AGUA", "EQUIPO_FUEGO", 
					Arrays.asList("Evacuar a la gente", "No correr", "Esperar a los bomberos"));
			ga.addProtocolo(protocolo);
			
			Alarma a = ga.crearAlarma("AGUA", "matemáticas", new Date());
		
			boolean esperado = false;
			

			IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ gu.declararAlarma(null, a);});
		}
		
		
	    @DisplayName("PCN_01_Caso_03")
		@Test
		void PCN_01_Caso_03() {
		Usuario u = gu.crearUsuario("Jose", "39345566Z", 523456789, 
				"jose@gmail.com", "mates", "aulaA3","activa",true);
		gu.addUsuario(u);
		boolean esperado = false;
		
		IllegalArgumentException i = assertThrows(IllegalArgumentException.class, ()->{ gu.declararAlarma(u, null);});
	}
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

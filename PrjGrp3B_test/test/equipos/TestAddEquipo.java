package equipos;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import usuarios.GestorUsuarios;
import usuarios.Usuario;

class TestAddEquipo {

	GestorEquipos ge;
	GestorUsuarios gu;
	ArrayList<Usuario> miembros;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ge = GestorEquipos.obtenerGestorEquipos();
		gu = GestorUsuarios.obtenerGestorUsuarios();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@DisplayName("PCN_03_Caso_01")
	@Test
	void testAddEquipo01() {
		
		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		Usuario usuario = gu.crearUsuario("Nerea", "11111111P", 623456789, "correo@usc.es", "etse", "A", "activa", false);
		miembros.add(usuario);
		Equipo equip1 = ge.crearEquipo(miembros, "EQUIPO_FUEGO");
		
		boolean esperado= true;
		boolean real = ge.addEquipo(equip1);
		
		assertSame(esperado, real, "Fallo al añadir equipo");
	}

	@DisplayName("PCN_03_Caso_02")
	@Test
	void testAddEquipo02() {
		
		assertThrows(Exception.class, ()->{ge.addEquipo(null);}, "Fallo al añadir equipo");
		
	}
}

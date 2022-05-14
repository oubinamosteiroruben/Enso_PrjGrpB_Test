package equipos;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class TestModificarEquipo {
	
	GestorEquipos ge;
	GestorUsuarios gu;
	
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
	
	@Test
	@DisplayName("PCN_05_Caso_01")
	void PCN_05_Caso_01() {
		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		String tipo = "AGUA";
		Equipo eq = ge.crearEquipo(miembros, tipo);
		ge.addEquipo(eq);
		Usuario usuario = gu.crearUsuario("Ainhoa", "44882288F", 981258465, "correo@usc.es", "etse", "A", "activa", false);
		miembros.add(usuario);
		Equipo aux = ge.crearEquipo(miembros, "FUEGO");
		Equipo modificado = ge.modificarEquipo(aux, eq.getIdEquipo());
		Equipo esperado = new Equipo(eq.getIdEquipo(),miembros,"FUEGO");
		
		assertEquals(esperado, modificado, "No se ha modificado correctamente el equipo");
	}
	
	@Test
	@DisplayName("PCN_05_Caso_02")
	void PCN_05_Caso_02() {
		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		String tipo = "AGUA";
		Equipo eq = ge.crearEquipo(miembros, tipo);
		ge.addEquipo(eq);
		Usuario usuario = gu.crearUsuario("Ainhoa", "44882288F", 981258465, "correo@usc.es", "etse", "A", "activa", false);
		miembros.add(usuario);
		
		assertThrows(Exception.class, ()->{ge.modificarEquipo(null, eq.getIdEquipo());}, "No se ha lanzado una excepcion al introducir miembros null");
	}
	
	@Test
	@DisplayName("PCN_05_Caso_03")
	void PCN_05_Caso_03() {
		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		String tipo = "AGUA";
		Equipo eq = ge.crearEquipo(miembros, tipo);
		ge.addEquipo(eq);
		Usuario usuario = gu.crearUsuario("Ainhoa", "44882288F", 981258465, "correo@usc.es", "etse", "A", "activa", false);
		miembros.add(usuario);
		Equipo aux = ge.crearEquipo(miembros, "FUEGO");
		
		assertThrows(Exception.class, ()->{ge.modificarEquipo(aux, null);}, "No se ha lanzado una excepcion al introducir IdEquipo null");
	}
	
	@Test
	@DisplayName("PCN_05_Caso_04")
	void PCN_05_Caso_04() {
		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		String tipo = "AGUA";
		Equipo eq = ge.crearEquipo(miembros, tipo);
		ge.addEquipo(eq);
		Usuario usuario = gu.crearUsuario("Ainhoa", "44882288F", 981258465, "correo@usc.es", "etse", "A", "activa", false);
		miembros.add(usuario);
		Equipo aux = ge.crearEquipo(miembros, "FUEGO");
		Equipo modificado = ge.modificarEquipo(aux, "EQ");
		Equipo esperado = new Equipo(eq.getIdEquipo(),miembros,"FUEGO");
		
		assertThrows(Exception.class, ()->{ge.modificarEquipo(aux, "EQ");}, "No se ha lanzado una excepcion al introducir un IdEquipo inexistente");
	}
	
}

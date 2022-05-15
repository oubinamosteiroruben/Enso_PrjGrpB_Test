package usuarios;

import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

//import org.mockito.*;

class ModificarUsuariosTests_CajaBlanca {
	
	
	@AfterEach
	@DisplayName("Limpieza lista usuarios")
	void limpiezaUsuarios() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		ArrayList<Usuario> usuarios = igu.obtenerListaUsuarios();
		for(Usuario u: usuarios) {
			igu.eliminarUsuario(u);
		}
	}

	@Tag("PCB_04_Caso_01")
	@Test
	@DisplayName("PCB_04_Caso_01. Se envía usuario nulo")
	void testUsuarioNull() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(null,"idPrueba");});
		
		assertEquals(iae.getMessage(),"Usuario no vÃƒÂ¡lido","Error, aceptó que se pasase un usuario nulo");
				
	}
	
	/*
	
	@Test
	@DisplayName("Mockito")
	void testConMockito() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
	
		try(MockedStatic<IGestorUsuarios> utilities = Mockito.mockStatic(IGestorUsuarios.class)){
			
			utilities.when(IGestorUsuarios::crearUsuario)
			Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
			
		}
		
		
				
	}*/
	
	
	@Tag("PCB_04_Caso_02")
	@Test
	@DisplayName("PCB_04_Caso_02. No existen usuarios anhadidos")
	void test_NoUsuariosAnhadidos() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
		
		Usuario uCloned = clonarUsuario(u);
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,uCloned.getIdUsuario());});
		
		assertEquals(iae.getMessage(),"El id introducido no corresponde con el de un usuario del sistema","Error, aceptó que se pasase un id no válido");
		
	}
	
	@Nested
	@DisplayName("Tests con usuarios creados")
	class TestsConUsuariosCreados{ 
		@BeforeEach
		@DisplayName("Creacion Usuarios")
		void creacionUsuarios() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
			Usuario u2 = igu.crearUsuario("Ricarda", "38789985H", 678987589, "email2@dominio.com", "Matematicas", "Aula Magna", "Activo", true);
							
			igu.addUsuario(u);
			igu.addUsuario(u2);
		}
		
		@Tag("PCB_04_Caso_03")
		@Test
		@DisplayName("PCB_04_Caso_03. Id introducido incorrecto")
		void test_IdNoValido() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
								
			IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,"idNoValido");});
			
			assertEquals(iae.getMessage(),"El id introducido no corresponde con el de un usuario del sistema","Error, aceptó que se pasase un id no válido");
			
		}
		
		@Tag("PCB_04_Caso_04")
		@Test
		@DisplayName("PCB_04_Caso_04. Id modificado")
		void test_ModificacionId() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
			
			String oldId = uCloned.getIdUsuario().toString();
			
			uCloned.setIdUsuario("id");
			
			Usuario uModified = igu.modificarUsuario(uCloned, "Ruben_ETSE");
			
			assertEquals(oldId,uModified.getIdUsuario(),"Error, aceptó la modificación del Id");
			
		}
		
		@Tag("PCB_04_Caso_05")
		@Test
		@DisplayName("PCB_04_Caso_05. Modificación campo a null")
		void test_ModificacionCampoNull() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
						
			String oldCentro = uCloned.getCentro();
			
			uCloned.setCentro(null);
			
			Usuario uModified = igu.modificarUsuario(uCloned, "Ruben_ETSE");
						
			assertEquals(oldCentro,uModified.getCentro(),"Error, aceptó que se modificase un campo a null");
			
		}
		
		@Tag("PCB_04_Caso_06")
		@Test
		@DisplayName("PCB_04_Caso_06. Modificación campo correctamente")
		void test_ModificacionCampoCorrectamente() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
						
			String oldCentro = uCloned.getCentro();			
						
			uCloned.setCentro("Filosofia");
			uCloned.setZona("Cafeteria");
			uCloned.setEstado("Descanso");
			
			Usuario uModified = igu.modificarUsuario(uCloned, "Ruben_ETSE");
						
			assertEquals(uCloned,uModified,"Error, no realizó los cambios");
			
		}
		
	}
		
	Usuario clonarUsuario(Usuario original) {
		return new Usuario(original.getIdUsuario(),original.getNombre(),original.getDni(),original.getTelefono(), original.getCorreo(), original.getCentro(), original.getZona(), original.getEstado(), original.getCapacitacion());
	}

}

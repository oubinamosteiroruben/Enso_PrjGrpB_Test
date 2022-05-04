package usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ModificarUsuariosTests {
	
	
	@AfterEach
	@DisplayName("Limpieza lista usuarios")
	void limpiezaUsuarios() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		ArrayList<Usuario> usuarios = igu.obtenerListaUsuarios();
		for(Usuario u: usuarios) {
			igu.eliminarUsuario(u);
		}
	}

	@Test
	@DisplayName("Se env�a usuario nulo")
	void testUsuarioNull() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(null,"idPrueba");});
		
		assertEquals(iae.getMessage(),"Usuario no vÃ¡lido","Error, acept� que se pasase un usuario nulo");
				
	}
	
	@Test
	@DisplayName("No existen usuarios anhadidos")
	void test_NoUsuariosAnhadidos() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
		
		Usuario uCloned = clonarUsuario(u);
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,uCloned.getIdUsuario());});
		
		assertEquals(iae.getMessage(),"El id introducido no corresponde con el de un usuario del sistema","Error, acept� que se pasase un id no v�lido");
		
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
		
		@Test
		@DisplayName("Id introducido incorrecto")
		void test_IdNoValido() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
								
			IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,"idNoValido");});
			
			assertEquals(iae.getMessage(),"El id introducido no corresponde con el de un usuario del sistema","Error, acept� que se pasase un id no v�lido");
			
		}
		
		@Test
		@DisplayName("Id modificado")
		void test_ModificacionId() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
			
			String oldId = uCloned.getIdUsuario();
			
			uCloned.setIdUsuario("id");
			
			Usuario uModified = igu.modificarUsuario(uCloned, oldId);
			
			assertEquals(oldId,uModified.getIdUsuario(),"Error, acept� la modificaci�n del Id");
			
		}
		
		@Test
		@DisplayName("Modificaci�n campo a null")
		void test_ModificacionCampoNull() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
						
			String oldCentro = uCloned.getCentro();
			
			uCloned.setCentro(null);
			
			Usuario uModified = igu.modificarUsuario(uCloned, uCloned.getIdUsuario());
						
			assertEquals(oldCentro,uModified.getCentro(),"Error, acept� que se modificase un campo a null");
			
		}
		
		@Test
		@DisplayName("Modificaci�n campo correctamente")
		void test_ModificacionCampoCorrectamente() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
						
			Usuario uCloned = clonarUsuario(igu.obtenerListaUsuarios().get(0));
						
			String oldCentro = uCloned.getCentro();
			
			
			
			uCloned.setCentro("Filosofia");
			uCloned.setZona("Cafeteria");
			uCloned.setEstado("Descanso");
			
			Usuario uModified = igu.modificarUsuario(uCloned, uCloned.getIdUsuario());
						
			assertEquals(uCloned,uModified,"Error, no realiz� los cambios");
			
		}
		
	}
	
	
	Usuario clonarUsuario(Usuario original) {
		return new Usuario(original.getIdUsuario(),original.getNombre(),original.getDni(),original.getTelefono(), original.getCorreo(), original.getCentro(), original.getZona(), original.getEstado(), original.getCapacitacion());
	}

}

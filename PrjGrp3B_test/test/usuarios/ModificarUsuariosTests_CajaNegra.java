package usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ModificarUsuariosTests_CajaNegra {
		
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
	@DisplayName("PCN_04_Caso_01. Se envía usuario e id nulo")
	void PCN_04_Caso_01() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
		
		assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(null,null);});
				
	}
	
	@Test
	@DisplayName("PCN_04_Caso_02. Se envía usuario nulo")
	void PCN_04_Caso_02() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
				
		assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(null,u.getIdUsuario());});
				
	}
	
	@Test
	@DisplayName("PCN_04_Caso_03. Se envía id nulo")
	void PCN_04_Caso_03() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
		
		Usuario uCloned = clonarUsuario(u);
		
		assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,null);});
				
	}
	
	@Test
	@DisplayName("PCN_04_Caso_04. Se envía id y usuario no null")
	void PCN_04_Caso_04() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Ruben", "344552655H", 123456789, "email@dominio.com", "ETSE", "Beach Solpor", "Activo", true);
		
		Usuario uCloned = clonarUsuario(u);
		
		assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,"Ruben_ETSE");});
				
	}
	
	@Nested
	@DisplayName("Tests con usuarios previamente creados y añadidos")
	class TestsConUsuarios{
		
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
		@DisplayName("PCN_04_Caso_05. Se modifica el id")
		void PCN_04_Caso_05() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0);
			Usuario uCloned = clonarUsuario(u);
			
			String oldId = uCloned.getIdUsuario();
						
			uCloned.setIdUsuario("IdNuevo");
			
			assertEquals(oldId,igu.modificarUsuario(uCloned, "Ruben_ETSE").getIdUsuario(),"Ha modificado el id del usuario");
					
		}
		
		@Test
		@DisplayName("PCN_04_Caso_06. Se modifica un usuario de id null")
		void PCN_04_Caso_06() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0); 
			
			Usuario uCloned = clonarUsuario(u);
					
			uCloned.setNombre("Carlos");
			
			assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,null);});
					
		}
		
		@Test
		@DisplayName("PCN_04_Caso_07. Se modifica un usuario de id no existente")
		void PCN_04_Caso_07() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0);
			
			Usuario uCloned = clonarUsuario(u);
					
			uCloned.setNombre("Carlos");
			
			assertThrows(IllegalArgumentException.class,()->{igu.modificarUsuario(uCloned,"idInexistente");});
					
		}
		
		@Test
		@DisplayName("PCN_04_Caso_08. Se modifica un usuario con un atributo a null")
		void PCN_04_Caso_08() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0);
			
			Usuario uCloned = clonarUsuario(u);
			
			String oldNombre = uCloned.getNombre();
					
			uCloned.setNombre(null);
			
			assertEquals(oldNombre,igu.modificarUsuario(uCloned, "Ruben_ETSE").getNombre(),"Ha realizado la modificación del nombre aunque se pase el null");
								
		}
		
		@Test
		@DisplayName("PCN_04_Caso_09. Se modifica el centro de un usuario correctamente")
		void PCN_04_Caso_09() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0);
			
			Usuario uCloned = clonarUsuario(u);
					
			String newCentro = "Facultad de fisica";
			
			uCloned.setCentro(newCentro);
			
			assertEquals(newCentro,igu.modificarUsuario(uCloned, "Ruben_ETSE").getCentro(),"No realizó el cambio de centro correctamente");
					
		}
		
		@Test
		@DisplayName("PCN_04_Caso_10. Se modifica la capacitacion de un usuario correctamente")
		void PCN_04_Caso_10() {
			IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
			
			Usuario u = igu.obtenerListaUsuarios().get(0);
			
			Usuario uCloned = clonarUsuario(u);
					
			Boolean newCapacitacion = false;
			
			uCloned.setCapacitacion(newCapacitacion);			
						
			assertEquals(newCapacitacion,igu.modificarUsuario(uCloned,"Ruben_ETSE").getCapacitacion(),"No realizó el cambio de capacitacion correctamente");
					
		}
		
	}
	
	
	
	Usuario clonarUsuario(Usuario original) {
		return new Usuario(original.getIdUsuario(),original.getNombre(),original.getDni(),original.getTelefono(), original.getCorreo(), original.getCentro(), original.getZona(), original.getEstado(), original.getCapacitacion());
	}

}

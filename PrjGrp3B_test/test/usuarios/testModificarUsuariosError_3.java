package usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class testModificarUsuariosError_3 {

	@Test
	void test() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario u = igu.crearUsuario("Rubén", "35628420V", 123456789, "fklssls@lksjls.com", "lsksl", "lksjsl", "lsjsl", true);
		
		igu.addUsuario(u);
		
		u.setCapacitacion(false);
		u.setTelefono(4789);
		igu.modificarUsuario(u,"123");
		
		List<Usuario> lu = igu.obtenerListaUsuarios();
							
		assertEquals(lu.get(0),u,"No modificado correctamente");
	}

}

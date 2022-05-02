package usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class testModificarUsuariosError_6 {

	@Test
	void test() {
		IGestorUsuarios igu = (IGestorUsuarios) GestorUsuarios.obtenerGestorUsuarios();
		
		Usuario uRef = igu.crearUsuario("Rubén", "35628420V", 123456789, "fklssls@lksjls.com", "lsksl", "lksjsl", "lsjsl", true);
		igu.addUsuario(uRef);
		
		Usuario u = new Usuario(uRef.getIdUsuario(), uRef.getNombre(), uRef.getDni(), uRef.getTelefono(), uRef.getCorreo(), uRef.getCentro(),
			uRef.getZona(), uRef.getEstado(), uRef.getCapacitacion());
		
		igu.modificarUsuario(u,uRef.getIdUsuario());
		
		List<Usuario> lu = igu.obtenerListaUsuarios();
							
		
		Usuario uRef2 = igu.crearUsuario("Rubén", "356284209V", 123456789, "fklssls@lksjls.com", "lsksl", "lksjsl", "lsjsl", true);
		igu.addUsuario(uRef2);
		
		assertEquals(lu.get(0),u,"No modificado correctamente"); // no debería dejar hacer el cambio de id
	}

}

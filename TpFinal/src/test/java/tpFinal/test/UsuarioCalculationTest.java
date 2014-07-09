package tpFinal.test;

import static org.junit.Assert.*;

import java.util.StringTokenizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.UsuarioDao;
import tpFinal.security.Usuario;
import tpFinal.service.calculation.calculationImpl.UsuarioCalculation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class UsuarioCalculationTest {
	@Autowired
	UsuarioCalculation calculation;
	@Autowired
	UsuarioDao dao;
	
	String mensaje; //el mensaje que devuelve el metodo
	String username;
	String apellido;
	String password;
	Usuario registroTest;

	@Before
	public void setUp(){
		username = "prueba";
		password = "prueba";
	}
	
	@After
	public void tearDown(){
		dao.delete(registroTest);
	}

	@Test
	public void testNuevoSuccess() {//creo un usuario de rrhh (rol = 1)
		mensaje = calculation.nuevo(username, password, 1, 0);
		assertEquals(1, contarPalabras(mensaje));//solo tiene que devolver el id
		assertNotNull(dao.get(Integer.parseInt(mensaje)));
		registroTest = dao.get(Integer.parseInt(mensaje));//guardo el registro para poder borrarlo despues
	}
	
	@Test
	@ExpectedException(NullPointerException.class)
	public void testNuevoFailException() {//creo un usuario vendedor (rol = 2) especificando un vendedor inexistente
		calculation.nuevo(username, password, 2, 0);
	}
	
	@Test
	public void testNuevoFail() {//creo un usuario de rrhh (rol = 1) con parametros vacios
		mensaje = calculation.nuevo("", "", 1, 0);
		assertNotEquals(1, contarPalabras(mensaje));//no devuelve el id porque ocurre un error
	}

	@Test
	public void testCambiarEstado() {
		mensaje = calculation.cambiarEstado(4);//cambio de estado el registro de Juan, que está desactivado
		assertEquals(1, contarPalabras(mensaje));
	}
	
	public int contarPalabras(String texto){
		return new StringTokenizer(texto).countTokens();
	}
}

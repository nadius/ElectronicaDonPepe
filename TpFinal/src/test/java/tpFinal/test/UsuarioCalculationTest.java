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
import tpFinal.domain.Vendedor;
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
	int registroTestCambiarEstado = 6;//corresponde al usuario JuanPerez
	int ultimoRegistroNoTest;

	@Before
	public void setUp(){
		username = "prueba";
		password = "prueba";
		ultimoRegistroNoTest = dao.getAll().size();
	}
	
	@After
	public void tearDown(){
		for (Usuario registro : dao.getAll()){
			if (registro.getId() > ultimoRegistroNoTest){
				dao.delete(registro);
			}
		}
	}
	
	public int contarPalabras(String texto){
		return new StringTokenizer(texto).countTokens();
	}

//TESTS CORRESPONDIENTES A LA ADMINISTRACION DE USUARIOS
	@Test
	public void testNuevoUsuarioSuccess() {//creo un usuario de rrhh (rol = 1)
		mensaje = calculation.nuevo(username, password, 1, 0);
		assertEquals(1, contarPalabras(mensaje));//solo tiene que devolver el id
		assertNotNull(dao.get(Integer.parseInt(mensaje)));//tiene que haber algo guardado en la base de datos
		registroTest = dao.get(Integer.parseInt(mensaje));//guardo el registro para poder borrarlo despues
	}
	
	@Test
	public void testNuevoUsuarioFail() {//creo un usuario de rrhh (rol = 1) con parametros vacios
		mensaje = calculation.nuevo("", "", 1, 0);//verifico que el id devuelto realmente pertenece a un registro
		assertNotEquals(1, contarPalabras(mensaje));//no devuelve el id porque ocurre un error
	}

	@Test
	public void testCambiarEstado() {
		boolean estadoOriginal = dao.get(registroTestCambiarEstado).isActivo();//guardo el valor del estado
		mensaje = calculation.cambiarEstado(registroTestCambiarEstado);//cambio de estado el registro de Juan, que está desactivado
		assertEquals(registroTestCambiarEstado, Integer.parseInt(mensaje));//la funcion tiene que devolver el id del regstro actualizado
		assertNotEquals(estadoOriginal, dao.get(registroTestCambiarEstado).isActivo());//los estados no tienen que ser iguales
		calculation.cambiarEstado(registroTestCambiarEstado);//lo vuelvo a desactivar
	}
	
//TESTS DE LOGIN
	@Test
	public void testLoginSuccess(){
		Usuario usuarioTest = dao.get(1);//el usuario 1 está activo
		assertTrue(calculation.login(usuarioTest.getUsername(), usuarioTest.getPassword()));
	}
	
	@Test
	public void testLoginFailNoExisteUsuario(){
		assertFalse(calculation.login("pepe", "pepe"));
	}
	
	@Test
	public void testLoginFailUsuarioNoActivo(){
		Usuario usuarioTest = dao.get(6);//el usuario 6 (Juan Perez) no esta activo
		assertFalse(calculation.login(usuarioTest.getUsername(), usuarioTest.getPassword()));
	}
}

package tpFinal.test;

import static org.junit.Assert.*;

import java.util.StringTokenizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.VendedorDao;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.service.calculation.calculationImpl.VendedorCalculation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class VendedorCalculationTest {//TESTS CORRESPONDIENTES A LA ADMINISTRACION DE VENDEDORES
	@Autowired
	VendedorCalculation calculation;
	@Autowired
	VendedorDao dao;
	
	String name;
	String lastname;
	String mensaje;
	Vendedor registroTest;
	int registroTestCambiarEstado = 4;//corresponde al usuario JuanPerez
	int ultimoRegistroNoTest;
	
	@Before
	public void setUp() throws Exception {
		name = "John";
		lastname = "Doe";
		ultimoRegistroNoTest = dao.getAll().size();
	}

/*
	@After
	public void tearDown() throws Exception {
		for (Vendedor registro : dao.getAll()){
			if (registro.getId() > ultimoRegistroNoTest){
				dao.delete(registro);
			}
		}
	}
*/
	
	public int contarPalabras(String texto){
		return new StringTokenizer(texto).countTokens();
	}

	@Test
	public void testNuevoSuccess() {
		mensaje = calculation.nuevo(name, lastname);
		assertEquals(1, contarPalabras(mensaje));//solo tiene que devolver el id
		assertNotNull(dao.get(Integer.parseInt(mensaje)));//verifico que el id devuelto realmente pertenece a un registro
		registroTest = dao.get(Integer.parseInt(mensaje));//guardo el registro para poder borrarlo despues
	}
	
	@Test
	public void testNuevoFail(){//intento crear como nuevo un usuario que ya existe
		Vendedor registro = dao.get(registroTestCambiarEstado);
		mensaje = calculation.nuevo(registro.getNombre(), registro.getApellido());//verifico que el id devuelto realmente pertenece a un registro
		assertNotEquals(1, contarPalabras(mensaje));//no devuelve el id porque ocurre un error
	}

	@Test
	public void testCambiarEstado() {
		boolean estadoOriginal = dao.get(registroTestCambiarEstado).isActivo();//guardo el valor del estado
		mensaje = calculation.cambiarEstado(registroTestCambiarEstado);//cambio de estado el registro de Juan, que está desactivado
		assertEquals(registroTestCambiarEstado, Integer.parseInt(mensaje));//la funcion tiene que devolver el id del regstro actualizado
		assertNotEquals(estadoOriginal, dao.get(registroTestCambiarEstado).isActivo());//los estados no tienen que ser iguales
	}
}

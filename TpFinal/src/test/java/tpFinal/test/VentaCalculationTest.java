package tpFinal.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.VendedorDao;
import tpFinal.dao.impl.VentaDao;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.service.VentaService;
import tpFinal.service.calculation.calculationImpl.VentaCalculation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class VentaCalculationTest {
	@Autowired
	VentaService service;
	@Autowired
	VentaDao dao;
	@Autowired
	VendedorDao vendedores;
	
	String mensaje;
	Venta registroTest;
	Vendedor vendedorTest;
	int idSugerido;
	int ultimoRegistroNoTest;
	GregorianCalendar desde;
	GregorianCalendar hasta;

	@Before
	public void setUp() throws Exception {
		vendedorTest = vendedores.get(4);//traigo a Juan Perez, que está desactivado
		ultimoRegistroNoTest = dao.getAll().size();
		desde = new GregorianCalendar(2013, GregorianCalendar.MARCH, 01);
		hasta = new GregorianCalendar(2013, GregorianCalendar.MARCH, 31);
	}

/*	@After
	public void tearDown() throws Exception {
		for (Venta registro : dao.getAll()){
			if (registro.getId() > ultimoRegistroNoTest){
				dao.delete(registro);
			}
		}
	}
*/
	@Test
	public void calcularSinIdSuccess() {//guardo una venta y dejo que la base de datos calcule el id
		Venta registroTest;
		int cantidadOriginal = dao.getAll().size();
		//agrego los productos a la venta
				service.Agregar(1);
				service.Agregar(6);
				service.Agregar(1);
				service.Agregar(8);
		mensaje = service.calcular(vendedorTest);
		assertNotEquals(cantidadOriginal, dao.getAll().size());//la cantidad de registros no puede ser la misma
		assertEquals("", mensaje);//el mensaje tiene que estar vacio
		registroTest = getUltimo();
		assertEquals(4, registroTest.getProductos().size());//la cantidad de productos debe ser la misma
		assertEquals(vendedorTest.getId(), registroTest.getVendedor().getId());//el vendedor debe ser el mismo
	}
	
	@Test
	public void calcularSinIdFail() {
		int cantidadOriginal = dao.getAll().size();
		//agrego los productos a la venta
		service.Agregar(1);
		service.Agregar(6);
		service.Agregar(1);
		service.Agregar(8);
		service.Agregar(7);
		
		mensaje = service.calcular(null);
		assertNotEquals("", mensaje);//el mensaje no puede ser nulo
		assertEquals(cantidadOriginal, dao.getAll().size());//debe haber igual cantidad de registros
	}
	
	@Test
	public void calcularConIdSuccess() {
		Venta registroTest;
		int cantidadOriginal = dao.getAll().size();
		int idProximoRegistro = getUltimo().getId() + 1;
		
		//agrego los productos a la venta
				service.Agregar(1);
				service.Agregar(6);
				service.Agregar(1);
				service.Agregar(10);
				
		mensaje = service.calcular(vendedorTest, idProximoRegistro);
		assertNotEquals(cantidadOriginal, dao.getAll().size());//la cantidad de registros no puede ser la misma
		assertEquals("", mensaje);//el mensaje tiene que estar vacio
		registroTest = getUltimo();
		assertEquals(4, registroTest.getProductos().size());//la cantidad de productos debe ser la misma
		assertEquals(vendedorTest.getId(), registroTest.getVendedor().getId());//el vendedor debe ser el mismo
	}
	
	@Test
	public void calcularConIdFail() {
		int cantidadOriginal = dao.getAll().size();
		int idProximoRegistro = getUltimo().getId() + 1;
		
		//agrego los productos a la venta
				service.Agregar(1);
				service.Agregar(6);
				service.Agregar(1);
				service.Agregar(10);
				
		mensaje = service.calcular(vendedorTest, 2);//ya hay una venta con ese id
		assertNotEquals("", mensaje);//el mensaje tiene que estar vacio
		assertEquals(cantidadOriginal, dao.getAll().size());//debe haber igual cantidad de registros
		
		mensaje = service.calcular(vendedorTest, 0);//id no valido
		assertNotEquals("", mensaje);//el mensaje tiene que estar vacio
		assertEquals(cantidadOriginal, dao.getAll().size());//debe haber igual cantidad de registros
		
		mensaje = service.calcular(null, idProximoRegistro);//el vendedor no puede ser nulo
		assertNotEquals("", mensaje);//el mensaje tiene que estar vacio
		assertEquals(cantidadOriginal, dao.getAll().size());//debe haber igual cantidad de registros
	}
	
	@Test
	public void buscarFechasSuccess(){//busco en una fecha donde se que hay registros (marzo 2013, id = Arthur Dent)
		ArrayList<Venta> respuesta = service.findBySpecificDatesCreatorId(3, desde.getTime(), hasta.getTime());
		assertFalse(respuesta.isEmpty());
	}
	
	@Test
	public void buscarFechasFail(){//busco en una fecha donde no hay registros (marzo 2013, Juan Perez)
		ArrayList<Venta> respuesta = service.findBySpecificDatesCreatorId(vendedorTest.getId(), desde.getTime(), hasta.getTime());
		assertTrue(respuesta.isEmpty());
	}
	
	@Test
	public void checkImporte(){
		ArrayList<Producto> productos;
		for(Venta registro : dao.getAll()){
			service.resetCarrito();
			for (Producto producto : registro.getProductos()){
				service.Agregar(producto.getId());
			}
			assertEquals(registro.getImporte(), service.getTotal(), 0.1);
		}
	}
	
	private Venta getUltimo(){
		Venta rta = null;
		for (Venta registro : dao.getAll()){
			rta = registro;
		}
		return rta;
	}
}

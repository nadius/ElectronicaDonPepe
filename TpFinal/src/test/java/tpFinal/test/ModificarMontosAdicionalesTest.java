package tpFinal.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.ComisionProductoDao;
import tpFinal.dao.impl.ComisionVentaDao;
import tpFinal.dao.impl.PremioDao;
import tpFinal.dao.impl.PremioMontoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.adicional.monto.ComisionProductoMonto;
import tpFinal.domain.adicional.monto.PremioMonto;
import tpFinal.service.AdicionalMontoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class ModificarMontosAdicionalesTest {
	//service
	@Autowired
	AdicionalMontoService service;
	
	//los dao
	@Autowired
	PremioMontoDao premioMontoDao;
	@Autowired
	ComisionVentaDao comisionVentaDao;
	@Autowired
	ComisionProductoDao comisionProductoDao;
	@Autowired
	PremioDao premioDao;
		
/*	@Before
	public void setUp() throws Exception {//calculo los adicionales y los guardo
		GregorianCalendar desde = new GregorianCalendar(2013, GregorianCalendar.MARCH, 01);
		GregorianCalendar hasta = new GregorianCalendar(2013, GregorianCalendar.MARCH, 31);
		adicionalCalculation.resetParams();
		adicionalCalculation.setVendedores(adicionalCalculation.getVendedoresActivos());
		adicionalCalculation.calcularTodos(desde.getTime(), hasta.getTime());
	}
*/
	
/*	@After
	public void tearDown(){
		for (Adicional registro : adicionalDao.getAll()){
			adicionalDao.delete(registro);
		}
	}
*/
	@Test
	public void actualizarComisionProductoSuccess() {
		int idRegistro = 4;
		float montoNuevo = Float.parseFloat("1.5");
		float monto = 0;
		ComisionProductoMonto registroViejo = service.getMontoComisionProducto(idRegistro);
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionProducto(registroViejo.getId(), montoNuevo);
		
		assertNotEquals(registroViejo.getMonto(), service.getMontoComisionProducto(idRegistro).getMonto());//que los montos no sean iguales en el registro del monto
		assertEquals(3, registrosActualizados);//que los registros actualizados sean los correctos
		
		for (ComisionProducto registro : comisionProductoDao.getAll()){
			if (registro.getProducto().getId() == registroViejo.getProducto().getId()){
				monto = registro.getImporte()/registro.getUnidades(); 
				assertNotEquals(registroViejo.getMonto(), monto);//que el monto no sea el mismo que el anterior
			}
		}
	}
	
	@Test
	public void actualizarComisionProductoFail(){//inserto el mismo valor, para que devuelva que no se modifico ningun registro
		int idRegistro = 4;
		ComisionProductoMonto registroViejo = service.getMontoComisionProducto(idRegistro);
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionProducto(idRegistro, registroViejo.getMonto());
		assertEquals(0, registrosActualizados);
	}
	
	@ExpectedException(NullPointerException.class)
	@Test
	public void actualizarComisionProductoFailException() {//ingreso un id que no existe para generar una excepcion
		float montoNuevo = (float) 1.5;
		service.actualizarMontoComisionProducto(0, montoNuevo);
	}

	@Test
	public void actualizarComisionVentaSuccess() {
		int idRegistro = 1;
		float montoNuevo = 100;
		float montoViejo = service.getMontoComisionVenta(idRegistro).getMonto();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionVenta(idRegistro, montoNuevo);
		
		assertNotEquals(montoViejo, service.getMontoComisionVenta(idRegistro).getMonto());//que los montos no sean iguales en el registro del monto
		assertEquals(3, registrosActualizados);//que los registros actualizados sean los correctos
		
		for (ComisionVenta registro : comisionVentaDao.getAll()){
			assertNotEquals(montoViejo, registro.getImporte());//que el importe no sea el mismo que el anterior
		}
	}

	@Test
	public void actualizarComisionVentaFail(){//inserto el mismo valor, para que devuelva que no se modifico ningun registro
		int idRegistro = 1;
		float montoViejo = service.getMontoComisionVenta(idRegistro).getMonto();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionVenta(idRegistro, montoViejo);
		assertEquals(0, registrosActualizados);
	}
	
	@ExpectedException(NullPointerException.class)
	@Test
	public void actualizarComisionVentaFailException() {//ingreso un id que no existe para generar una excepcion
		float montoNuevo = (float) 1.5;
		service.actualizarMontoComisionVenta(0, montoNuevo);
	}
	
	@Test
	public void actualizarPremioMesSuccess() {
		float montoNuevo = 500;
		PremioMonto registroViejo = premioMontoDao.getMontoPremioMes();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoPremio(registroViejo.getId(), montoNuevo);
		assertNotEquals(registroViejo.getMonto(), premioMontoDao.getMontoPremioMes().getMonto());//que los montos no sean iguales en el registro del monto
		assertEquals(1, registrosActualizados);//que el registro actualizado sea el correcto
		
		for (Premio registro : premioDao.getAllPremioMes()){
			assertNotEquals(registroViejo.getMonto(), registro.getImporte());//que el importe no sea el mismo que el anterior
		}
	}

	@Test
	public void actualizarPremioMesFail(){//inserto el mismo valor, para que devuelva que no se modifico ningun registro
		PremioMonto registroViejo = premioMontoDao.getMontoPremioMes();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionVenta(registroViejo.getId(), registroViejo.getMonto());
		assertEquals(0, registrosActualizados);
	}
	
	@Test
	public void actualizarPremioCampaniaSuccess() {
		float montoNuevo = 100;
		PremioMonto registroViejo = premioMontoDao.getMontoPremioCampania();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoPremio(registroViejo.getId(), montoNuevo);
		assertNotEquals(registroViejo.getMonto(), premioMontoDao.getMontoPremioCampania().getMonto());//que los montos no sean iguales en el registro del monto
		assertEquals(2, registrosActualizados);//que los registros actualizados sean los correctos
		
		for (Premio registro : premioDao.getAllCampania()){
			assertNotEquals(registroViejo.getMonto(), registro.getImporte());//que el importe no sea el mismo que el anterior
		}
	}

	@Test
	public void actualizarPremioCampaniaFail(){//inserto el mismo valor, para que devuelva que no se modifico ningun registro
		PremioMonto registroViejo = premioMontoDao.getMontoPremioMes();
		int registrosActualizados;
		
		registrosActualizados = service.actualizarMontoComisionVenta(registroViejo.getId(), registroViejo.getMonto());
		assertEquals(0, registrosActualizados);
	}
	
	@ExpectedException(NullPointerException.class)
	@Test
	public void actualizarPremioException() {//ingreso un id que no existe para generar una excepcion
		float montoNuevo = (float) 1.5;
		service.actualizarMontoPremio(0, montoNuevo);
	}
}

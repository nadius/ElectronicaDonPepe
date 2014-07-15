package tpFinal.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.CampaniaDao;
import tpFinal.domain.Campania;
import tpFinal.service.calculation.calculationImpl.CampaniaCalculation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class CampaniaCalculationTest {
	@Autowired
	CampaniaCalculation calculation;
	@Autowired
	CampaniaDao dao;

	@Before
	public void setUp() throws Exception {
		calculation.agregar(2);
		calculation.eliminar(2);
	}

	@Test
	public void agregarNuevoSuccess() {
		int cantidadRegistrosOriginal = calculation.getAll().size(); 
		calculation.agregar(1);
		assertNotEquals(cantidadRegistrosOriginal, calculation.getAll().size());
	}
	
	@Test
	public void reactivarRegistroSuccess() {
		Campania registroTest = dao.get(2);
		calculation.eliminar(registroTest.getId());
		calculation.agregar(registroTest.getProducto().getId());
		assertNotEquals(false, dao.get(2).isActivo());
	}
	
	@Test
	public void eliminarSuccess() {
		Campania registroTest = dao.get(3);
		calculation.eliminar(registroTest.getId());
		assertNotEquals(true, dao.get(3).isActivo());
	}

	@ExpectedException(NullPointerException.class)
	@Test
	public void eliminarFail() {
		calculation.eliminar(0);
	}
}

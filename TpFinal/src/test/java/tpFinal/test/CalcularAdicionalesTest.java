package tpFinal.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tpFinal.dao.impl.ProductoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.service.calculation.calculationImpl.ComisionProductoCalculation;
import tpFinal.service.calculation.calculationImpl.ComisionVentaCalculation;
import tpFinal.service.calculation.calculationImpl.PremioCampaniaCalculation;
import tpFinal.service.calculation.calculationImpl.PremioMesCalculation;
import tpFinal.service.findItem.findItemImpl.VendedorFindItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:*applicationContext-test.xml")
public class CalcularAdicionalesTest {//TESTS CORRESPONDIENTES AL CALCULO DE ADICIONALES
// Los calculation
	@Autowired
	private ComisionVentaCalculation comisionVenta;
	@Autowired
	private ComisionProductoCalculation comisionProducto;
	@Autowired
	private PremioCampaniaCalculation campania;
	@Autowired
	private PremioMesCalculation mes;
	
//Acceso a la base de datos
	@Autowired
	private ProductoDao productoDao;
	@Autowired
	private VendedorFindItem vendedoresFindItem;
	
	GregorianCalendar desde;
	GregorianCalendar hasta;
	Date hoy;
	ArrayList<Vendedor> vendedores;
	
	@Before
	public void insertarDatos(){
		hoy = new Date();
		desde = new GregorianCalendar(2013, GregorianCalendar.MARCH, 01);
		hasta = new GregorianCalendar(2013, GregorianCalendar.MARCH, 31);
		vendedores = new ArrayList<Vendedor>(vendedoresFindItem.getAllByFlag(true));
		
		//seteo los parametros necesarios para los calculos
		comisionVenta.setParams(desde.getTime(), hasta.getTime(), hoy);
		comisionProducto.setParams(desde.getTime(), hasta.getTime(), hoy);
		campania.setParams(vendedores, desde.getTime(), hasta.getTime(), hoy);
		mes.setParams(vendedores, desde.getTime(), hoy);
	}

	@Test
	public void testCalcularPremioMejorVendedorMes() {
		Premio premioMes = mes.calcular(desde.getTime());
		assertEquals(2, premioMes.getPremiado().getId());
	}
	
	@Test
	public void testCalcularPremioCampania(){
		ArrayList<Premio> campanias= new ArrayList<Premio>();
		ArrayList<Producto> productos = productoDao.getAll();
		for (Producto item : productos){
		 campanias.add(campania.calcular(item));
		}
		
		assertEquals(2, campanias.size());//cantidad de campanias calculadas
	}
	
	@Test
	public void testCalcularComisionVenta(){
		ArrayList<ComisionVenta> comisionesVenta = new ArrayList<ComisionVenta>();
		for(Vendedor vendedor : vendedores){
			comisionesVenta.add(comisionVenta.calcular(vendedor));
		}
		
		assertEquals(3, comisionesVenta.size());//cantidad de comisiones calculadas
		
		//verificaciones para el primer registro (vendedor=1, Andrea Díaz)
		assertEquals(1, comisionesVenta.get(0).getVendedor().getId());//vendedor
		assertEquals(1, comisionesVenta.get(0).getUnidades());//cantidad de ventas
		assertEquals(200, comisionesVenta.get(0).getImporte(), 0);//importe
		
		//verificaciones para el primer registro (vendedor=2, Donna Noble)
		assertEquals(2, comisionesVenta.get(1).getVendedor().getId());//vendedor
		assertEquals(1, comisionesVenta.get(1).getUnidades());//cantidad de ventas
		assertEquals(200, comisionesVenta.get(1).getImporte(), 0);//importe
		
		//verificaciones para el primer registro (vendedor=3, Arthur Dent)
		assertEquals(3, comisionesVenta.get(2).getVendedor().getId());//id
		assertEquals(3, comisionesVenta.get(2).getUnidades());//cantidad de ventas
		assertEquals(200, comisionesVenta.get(2).getImporte(), 0);//importe
	}
	
	@Test
	public void testCalcularComisionProducto(){
		ArrayList<ComisionProducto> comisionesProductoAndreaDiaz = comisionProducto.calcularTodos(vendedores.get(0));
		ArrayList<ComisionProducto> comisionesProductoDonnaNoble = comisionProducto.calcularTodos(vendedores.get(1));
		ArrayList<ComisionProducto> comisionesProductoArthurDent = comisionProducto.calcularTodos(vendedores.get(2));
		
		//verificaciones para el primer vendedor
		assertEquals(2, comisionesProductoAndreaDiaz.size());//cantidad
		
		//verificaciones para el segundo vendedor
		assertEquals(2, comisionesProductoDonnaNoble.size());//cantidad
		
		//verificaciones para el primer vendedor
		assertEquals(3, comisionesProductoArthurDent.size());//cantidad
	}
}
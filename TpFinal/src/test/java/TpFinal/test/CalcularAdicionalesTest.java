package TpFinal.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import TpFinal.domain.ComisionProducto;
import TpFinal.domain.ComisionVenta;
import TpFinal.domain.Premio;
import TpFinal.domain.Producto;
import TpFinal.domain.Vendedor;
import TpFinal.service.Service;
import TpFinal.servlets.Adicionales;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-context-test.xml")
public class CalcularAdicionalesTest {
	@Autowired
	private Service service;
	@Autowired
	private Adicionales servlet;
	
	GregorianCalendar desde;
	GregorianCalendar hasta;	

	@Before
	public void insertarDatos(){
		desde = new GregorianCalendar(2013, GregorianCalendar.MARCH, 01);
		hasta = new GregorianCalendar(2013, GregorianCalendar.MARCH, 31);
	}

	@Test
	public void testCalcularPremioMejorVendedorMes() {
		Premio premioMes = servlet.calcularPremioVendedor(desde);
		assertEquals(3, premioMes.getPremiado().getId());
	}
	
	@Test
	public void testCalcularPremioCampania(){
		ArrayList<Premio> campanias= new ArrayList<Premio>();
		ArrayList<Producto> productos = service.getProductos();
		for (Producto item : productos){
		 campanias.add(servlet.calcularPremioCampania(desde, hasta, item));
		}
		
		assertEquals(2, campanias.size());//cantidad de campanias calculadas
	}
	
	@Test
	public void testCalcularComisionVenta(){
		ArrayList<Vendedor> vendedores= service.getVendedoresActivos();
		ArrayList<ComisionVenta> comisionesVenta = new ArrayList<ComisionVenta>();
		for(Vendedor vendedor : vendedores){
			comisionesVenta.add(servlet.calcularComisionVenta(vendedor, desde, hasta));
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
		ArrayList<Vendedor> vendedores= service.getVendedoresActivos();
		ArrayList<ComisionProducto> comisionesProductoVendedorUno = servlet.calcularComisionProducto(vendedores.get(0), desde, hasta);
		ArrayList<ComisionProducto> comisionesProductoVendedorDos = servlet.calcularComisionProducto(vendedores.get(1), desde, hasta);
		ArrayList<ComisionProducto> comisionesProductoVendedorTres = servlet.calcularComisionProducto(vendedores.get(2), desde, hasta);
		
		//verificaciones para el primer vendedor
		assertEquals(2, comisionesProductoVendedorUno.size());//cantidad
		
		//verificaciones para el segundo vendedor
		assertEquals(2, comisionesProductoVendedorDos.size());//cantidad
		
		//verificaciones para el primer vendedor
		assertEquals(3, comisionesProductoVendedorTres.size());//cantidad
	}
}
package tpFinal.service.calculation;

import java.util.ArrayList;

import tpFinal.domain.Producto;
import tpFinal.domain.Venta;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class CalculationUtils {
/*	protected VentaFindItem findVentas;//TODO: encontrar la forma de injectar este bean, porque sino el calculo no funciona. 
	
	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas = findVentas;
	}*/

	public int contarProductoVenta(Venta venta, Producto producto)
	{
		int i=0;
		ArrayList<Producto> productosVenta=(ArrayList<Producto>) venta.getProductos();
		for (Producto item : productosVenta)
			if (item.getId()==producto.getId())
				i++;
		
		//System.out.print(i + " veces");
		return i;
	}
}

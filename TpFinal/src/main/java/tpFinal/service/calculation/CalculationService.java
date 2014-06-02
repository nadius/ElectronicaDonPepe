package tpFinal.service.calculation;

import java.util.ArrayList;

import tpFinal.domain.Producto;
import tpFinal.domain.Venta;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public abstract class CalculationService<E> {
	//public E calcular(Vendedor author, Date today, GregorianCalendar from, GregorianCalendar to);
	protected VentaFindItem findVentas;
	protected ArrayList<Venta> ventas;
	
	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas = findVentas;
	}
	
	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}

	public abstract void showResult(E object);
	
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

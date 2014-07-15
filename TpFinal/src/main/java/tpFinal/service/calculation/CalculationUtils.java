package tpFinal.service.calculation;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import tpFinal.domain.Producto;
import tpFinal.domain.Venta;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class CalculationUtils {
	public int contarProductoVenta(Venta venta, Producto producto)
	{
		int i=0;
		ArrayList<Producto> productosVenta=new ArrayList<Producto>(venta.getProductos());
		for (Producto item : productosVenta)
			if (item.getId()==producto.getId())
				i++;
		
		//System.out.print(i + " veces");
		return i;
	}
	
 /**
 * Calcula una fecha nueva cuyo mes tenga una diferencia de una unidad con respecto al mes de la fecha inicial.
 * Ej: Dada una fecha 2013.01.01, la función devuelve 2013.02.01
 * Esta función es utilizada en el cálculo de adicionales.
 * @param fecha la fecha en la que se basa para hacer el cálculo
 * @return un Date con la fecha
 **/
	public Date getFechaIntervaloHasta(Date fecha) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		calendar.add(GregorianCalendar.MONTH, 1);
		return calendar.getTime();
	}
	
/**
* Calcula una fecha nueva cuyo dia el máximo del mes anterior del de la fecha inicial.
* Ej: Dada una fecha 2013.01.01, la función devuelve 2012.12.31
* Esta función es utilizada en el cálculo de adicionales.
* @param fecha la fecha en la que se basa para hacer el cálculo
* @return un Date con la fecha
**/
	public Date getFechaIntervaloDesde(Date fecha) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		calendar.add(GregorianCalendar.MONTH, -1);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
}

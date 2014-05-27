package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import tpFinal.dao.impl.ComisionVentaDao;
import tpFinal.dao.impl.ComisionVentaMontoDao;
import tpFinal.dao.impl.VentaDao;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.ComisionVentaFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class ComisionVentaCalculation implements CalculationService<ComisionVenta> {
	private VentaFindItem findVentas;
	private ComisionVentaFindItem findItem;
	private ComisionVentaMontoDao daoMontos;
	
	ComisionVenta comision = new ComisionVenta();
	
	public ComisionVentaCalculation() {
		comision=new ComisionVenta();
	}

	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas = findVentas;
	}

	public void setFindItem(ComisionVentaFindItem findItem) {
		this.findItem = findItem;
	}

	public void setDaoMontos(ComisionVentaMontoDao daoMontos) {
		this.daoMontos = daoMontos;
	}

	@Override
	public ComisionVenta calcular(Vendedor vendedor, Date fechaHoy, GregorianCalendar desde, GregorianCalendar hasta) {
		ArrayList<Venta>ventas = findVentas.findBySpecificDatesCreatorId(vendedor.getId(), desde.getTime(), hasta.getTime());
		ArrayList<ComisionVentaMonto> montos=daoMontos.getAll();
		
		if (ventas.isEmpty())
		{
			System.out.println("No se encontraron ventas para el periodo buscado");
			return null;
		}
		
		comision.setFechaCreacion(fechaHoy);
		comision.setFechaDesde(desde.getTime());
		comision.setFechaHasta(hasta.getTime());
		comision.setVendedor(vendedor);
		comision.setVentas(ventas);
		comision.setUnidades(ventas.size());
			
		for (ComisionVentaMonto item : montos)
		{
			if (item.getMax()!=0)//cero es el valor del máximo del último monto (ie, sin tope)
			{
				if (ventas.size()<=item.getMax() && ventas.size()>=item.getMin())
					comision.setImporte(item.getMonto());
			}
			else
			{
				if (ventas.size()>=item.getMin())
					comision.setImporte(item.getMonto());
			}
		}
		
		if (findItem.findIdByObject(comision)!=0)
			comision.setId(findItem.findIdByObject(comision));
			
		return comision;
	}

	@Override
	public void showResult() {
		System.out.println("\trealizadas " + comision.getUnidades() + " ventas.");
	}
}

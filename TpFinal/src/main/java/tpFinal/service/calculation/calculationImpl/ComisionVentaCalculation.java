package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionVentaMontoDao;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Vendedor;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.ComisionVentaFindItem;

public class ComisionVentaCalculation extends CalculationService<ComisionVenta> {
	private ComisionVentaFindItem findItem;
	private ComisionVentaMontoDao daoMontos;
	
	
	public void setFindItem(ComisionVentaFindItem findItem) {
		this.findItem = findItem;
	}

	public void setDaoMontos(ComisionVentaMontoDao daoMontos) {
		this.daoMontos = daoMontos;
	}

	public ComisionVenta calcular(Vendedor vendedor, Date fechaHoy, Date desde, Date hasta) {
		//ArrayList<Venta>ventas = findVentas.findBySpecificDatesCreatorId(vendedor.getId(), desde, hasta);
		ArrayList<ComisionVentaMonto> montos=daoMontos.getAll();
		ComisionVenta comision;
		
		if (ventas.isEmpty())
		{
			System.out.println("No se encontraron ventas para el periodo buscado");
			return null;
		}
		
		comision=new ComisionVenta();
		
		comision.setFechaCreacion(fechaHoy);
		comision.setFechaDesde(desde);
		comision.setFechaHasta(hasta);
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
	public void showResult(ComisionVenta registro) {
		System.out.println("\trealizadas " + registro.getUnidades() + " ventas.");
	}
}

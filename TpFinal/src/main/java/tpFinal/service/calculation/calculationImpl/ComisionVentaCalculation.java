package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionVentaMontoDao;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;
import tpFinal.service.calculation.CalculationUtils;
//import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.ComisionVentaFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class ComisionVentaCalculation extends CalculationUtils{
	private ComisionVentaFindItem findItem;
	private ComisionVentaMontoDao daoMontos;
	private VentaFindItem findVentas;
	
	private Date fechaHoy;
	private Date fechaDesde;
	private Date fechaHasta;
			
	public void setFindItem(ComisionVentaFindItem findItem) {
		this.findItem = findItem;
	}

	public void setDaoMontos(ComisionVentaMontoDao daoMontos) {
		this.daoMontos = daoMontos;
	}

	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas=findVentas;
	}
	
	public void setParams(Date fechaDesde, Date fechaHasta, Date fechaHoy) {
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.fechaHoy=fechaHoy;
	}

	public ComisionVenta calcular(Vendedor vendedor) {
		ArrayList<Venta>ventas = findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, fechaHasta);
		ArrayList<ComisionVentaMonto> montos=daoMontos.getAll();
		ComisionVenta comision;
		
		if (ventas==null || ventas.isEmpty())
		{
			System.out.println("No se encontraron ventas para el periodo buscado");
			return null;
		}
		
		comision=new ComisionVenta();
		
		comision.setFechaCreacion(fechaHoy);
		comision.setFechaDesde(fechaDesde);
		comision.setFechaHasta(fechaHasta);
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

	//@Override
	public String showResult(ComisionVenta registro) {
		return "\t id = " + registro.getId() + "\t Ventas: " + registro.getUnidades() + "\t Importe: " + registro.getId();
	}
}

package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionProductoMontoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.ComisionProductoMonto;
import tpFinal.service.calculation.CalculationUtils;
//import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.ComisionProductoFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class ComisionProductoCalculation extends CalculationUtils{
	private ComisionProductoFindItem findItem;
	private ComisionProductoMontoDao daoMontos;
	
	//Parametros del calculo
	private Date fechaHoy;
	private Date fechaDesde;
	private Date fechaHasta;
	private VentaFindItem findVentas;
		
	public void setFindItem(ComisionProductoFindItem findItem) {
		this.findItem = findItem;
	}

	public void setDaoMontos(ComisionProductoMontoDao daoMontos) {
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

	public ArrayList<ComisionProducto> calcularTodos(Vendedor vendedor) {
		ArrayList<Venta>ventas = findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, getFechaIntervaloHasta(fechaDesde));
		ArrayList<ComisionProductoMonto> montos=daoMontos.getAll();
		ArrayList<ComisionProducto> comisiones= new ArrayList<ComisionProducto>();
		ComisionProducto registro;
		
		int unidades=0;
		
		if (ventas==null || ventas.isEmpty())
		{
			System.out.println("\t\t No se encontraron ventas para el período buscado");
			return null;
		}
		
		for (Venta venta : ventas)
		{
			for(ComisionProductoMonto item: montos)
			{
				//if (venta.getProductos().contains(item.getProducto()))//si el producto con adicional está en la lista de productos comprados
				if (contarProductoVenta(venta, item.getProducto()) != 0)
				{
					//System.out.print("\t\t venta: " + venta.getId() + " - "+ item.getProducto().getNombre()+ " encontrado en ");
					unidades=contarProductoVenta(venta, item.getProducto());
					registro = new ComisionProducto(fechaHoy, fechaDesde, fechaHasta, vendedor, unidades, item.getMonto()*unidades, item.getProducto());
					
					if (findItem.findIdByObject(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos //TODO: testear!!
						registro.setId(findItem.findIdByObject(registro));
					
					comisiones.add(registro);
					//System.out.print("\n");
				}
			}
		}
		return comisiones;
	}

	//@Override
	private String showResult(ComisionProducto registro) {
		return "\t id = " + registro.getId() + "\t Producto id " + registro.getProducto().getId() + "\t" + registro.getUnidades() + " unidades \t" + registro.getImporte() + "$";
	}
	
	public String showResultAll(ArrayList<ComisionProducto> registros){
		String rta="";
		for (ComisionProducto item : registros)
			rta.concat(showResult(item) + "\n");
		return rta;
	}
}

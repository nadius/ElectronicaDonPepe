package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionProductoMontoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.ComisionProductoMonto;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.ComisionProductoFindItem;

public class ComisionProductoCalculation extends CalculationService<ComisionProducto>{
	private ComisionProductoFindItem findItem;
	private ComisionProductoMontoDao daoMontos;
	
	public void setFindItem(ComisionProductoFindItem findItem) {
		this.findItem = findItem;
	}

	public void setDaoMontos(ComisionProductoMontoDao daoMontos) {
		this.daoMontos = daoMontos;
	}

	public ArrayList<ComisionProducto> calcularTodos(Vendedor vendedor, Date fechaHoy, Date desde, Date hasta) {
		//ArrayList<Venta>ventas = findVentas.findBySpecificDatesCreatorId(vendedor.getId(), desde, hasta);
		ArrayList<ComisionProductoMonto> montos=daoMontos.getAll();
		ArrayList<ComisionProducto> comisiones= new ArrayList<ComisionProducto>();
		ComisionProducto registro;
		
		int unidades=0;
		
		if (ventas.isEmpty())
		{
			//System.out.println("\t\t No se encontraron ventas para el período buscado");
			return null;
		}
		
		for (Venta venta : ventas)
		{
			for(ComisionProductoMonto item: montos)
			{
				//if (venta.getProductos().contains(item.getProducto()))//si el producto con adicional está en la lista de productos comprados
				if (venta.getProductos().contains(item.getProducto()))
				{
					//System.out.print("\t\t venta: " + venta.getId() + " - "+ item.getProducto().getNombre()+ " encontrado en ");
					unidades=contarProductoVenta(venta, item.getProducto());
					registro = new ComisionProducto(fechaHoy, desde, hasta, vendedor, unidades, item.getMonto()*unidades, item.getProducto());
					
					if (findItem.findIdByObject(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos //TODO: testear!!
						registro.setId(findItem.findIdByObject(registro));
					
					comisiones.add(registro);
					//System.out.print("\n");
				}
			}
		}
		return comisiones;
	}

	public void showResult(ArrayList<ComisionProducto> registros) {
		System.out.println("\t" + "Calculados " + registros.size() + " registros.");
	}

	@Override
	public void showResult(ComisionProducto registro) {
		// TODO Auto-generated method stub
		
	}	
}

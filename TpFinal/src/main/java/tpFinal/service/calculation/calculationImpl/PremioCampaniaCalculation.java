package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.PremioMontoDao;
import tpFinal.domain.Campania;
import tpFinal.domain.Premio;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.PremioMonto;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.CampaniaFindItem;
import tpFinal.service.findItem.findItemImpl.PremioFindItem;


public class PremioCampaniaCalculation extends CalculationService<Premio>{
	private PremioMontoDao daoMontos;
	private PremioFindItem findItem;
	private CampaniaFindItem campaniaFindItem;
	
	public void setDaoMontos(PremioMontoDao daoMontos){
		this.daoMontos=daoMontos;
	}
	public void setFindItem(PremioFindItem findItem){
		this.findItem=findItem;
	}
	
	public void setCampaniaFindItem(CampaniaFindItem campaniaFindItem){
		this.campaniaFindItem=campaniaFindItem;
	}
	
	public Premio calcular(ArrayList<Vendedor> vendedores, Date fechaHoy, Date fechaDesde, Date fechaHasta, Producto producto)
	{
		int cantidad=0, cuenta=0;
		//ArrayList<Venta>ventas = new ArrayList<Venta>();
		Vendedor premiado= new Vendedor();
		PremioMonto monto=daoMontos.getMontoPremioCampania();
		Premio registro;
		
		//System.out.println("Premio mejor vendedor campania: (" + producto.getNombre() + " " + df.format(desde) + " - " + df.format(hasta) + ")");
		
		for (Vendedor vendedor : vendedores)
		{
			//System.out.println("\t " + vendedor.getNombre() + " " + vendedor.getApellido() + ":");
			ventas=findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, fechaHasta);
			if (!ventas.isEmpty())
			{
				for(Venta venta : ventas)
				{
					//System.out.print("\t\t venta: " + venta.getId() + " - ");
					cuenta=contarProductoVenta(venta, producto);
					
					if (cuenta>cantidad)
					{
						cantidad=cuenta;
						premiado=vendedor;
					}
					//System.out.print("\n");
				}
			}/*
			else //Verificacion
			{
				System.out.println("\t\t No se encontraron ventas para el período buscado");
			}*/
			////System.out.print("\n");
		}
		
		if (cantidad==0)
			return null;
		
		registro = new Premio(fechaHoy, fechaDesde, fechaHasta, premiado, true, producto, monto.getMonto());
		if (findItem.findIdByObject(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos //TODO: testear!!
			registro.setId(findItem.findIdByObject(registro));
		//
		
		return registro;
	}
	
	@Override
	public void showResult(Premio registro) {
		System.out.println("Premio mejor vendedor del mes: (" + registro.getFechaDesde().toString() + " - " + registro.getFechaHasta().toString() + ")\t" + registro.getPremiado().getNombre() + " " + registro.getPremiado().getApellido());
	}
	public ArrayList<Premio> calcularTodos(ArrayList<Vendedor> vendedores, Date fechaHoy, Date fechaDesde, Date fechaHasta) {
		ArrayList<Campania> campaniasActivas=campaniaFindItem.getAllByFlag(true);
		ArrayList<Premio> premiosCampania=new ArrayList<Premio>();
		if (!campaniasActivas.isEmpty())
		{
			for (Campania item : campaniasActivas)
				premiosCampania.add(calcular(vendedores, fechaHoy, fechaDesde, fechaHasta, item.getProducto()));
		}
		return premiosCampania;
	}
}

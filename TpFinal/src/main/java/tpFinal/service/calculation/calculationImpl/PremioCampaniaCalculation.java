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
import tpFinal.service.calculation.CalculationUtils;
//import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.CampaniaFindItem;
import tpFinal.service.findItem.findItemImpl.PremioFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;


public class PremioCampaniaCalculation extends CalculationUtils{
	private PremioMontoDao daoMontos;
	private PremioFindItem findItem;
	private CampaniaFindItem campaniaFindItem;
	private VentaFindItem findVentas;
	
	//Parametros del calculo
	private ArrayList<Venta> ventas;
	private ArrayList<Vendedor> vendedores;
	private Date fechaHoy;
	private Date fechaDesde;
	private Date fechaHasta;
		
	public void setDaoMontos(PremioMontoDao daoMontos){
		this.daoMontos=daoMontos;
	}
	public void setFindItem(PremioFindItem findItem){
		this.findItem=findItem;
	}
	
	public void setCampaniaFindItem(CampaniaFindItem campaniaFindItem){
		this.campaniaFindItem=campaniaFindItem;
	}
	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas=findVentas;
	}
		
	public void setParams(ArrayList<Vendedor> vendedores, Date fechaDesde, Date fechaHasta, Date fechaHoy){
		this.vendedores=vendedores;
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		this.fechaHoy=fechaHoy;
	}
	 
	public Premio calcular(Producto producto)
	{
		int i=0;
		int premiado = 0;
		int[] cuenta = new int[vendedores.size()];
		PremioMonto monto=daoMontos.getMontoPremioCampania();
		Premio registro;
		
		
		for (Vendedor vendedor : vendedores)
		{
			cuenta[i] = 0;
			ventas=findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, getFechaIntervaloHasta(fechaDesde));
			if (ventas!=null || !ventas.isEmpty())//si se encontraron ventas para este vendedor
			{
				for(Venta venta : ventas){
					cuenta[i] += contarProductoVenta(venta, producto);
				}
			}
			i++;
		}
		
		premiado = getMax(cuenta);
		
		if (premiado == -1)//si el producto no está en ninguno de las ventas encontradas
			return null;
		
		
		registro = new Premio(fechaHoy, fechaDesde, fechaHasta, vendedores.get(premiado), true, producto, monto.getMonto());
		if (findItem.findIdByObject(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos
			registro.setId(findItem.findIdByObject(registro));
		
		return registro;
	}
	
	public ArrayList<Premio> calcularTodos() {
		ArrayList<Campania> campaniasActivas=campaniaFindItem.getAllByFlag(true);
		ArrayList<Premio> premiosCampania=new ArrayList<Premio>();
		Premio premio;
				
		if (campaniasActivas!=null && !campaniasActivas.isEmpty())
		{
			for (Campania item : campaniasActivas){
				premio = calcular(item.getProducto());
				if (premio !=null )
					premiosCampania.add(premio);
			}
		}
		return premiosCampania;
	}
	
	private int getMax(int[] cuenta){
		int vendedor = -1 , valor=0;
		
		//si el producto esta la misma cantidad de veces para todos los vendedores, ie, no hay uno que lo haya vendido más veces
		if (igualCantidadTodos(cuenta, cuenta[0])){
			return -1;
		}
		
		for (int i =0; i<cuenta.length; i++){
			if (cuenta[i] > valor){
				valor = cuenta[i];
				vendedor = i;
			}
		}
		return vendedor;
	}
	
	private boolean igualCantidadTodos(int[] cuenta, int valor){//verifica si el valor buscado es el mismo en todo el array 
		for (int i : cuenta){
			if (i != valor){
				return false;
			}
		}
		return true;
	}
	
	//@Override
	private String showResult(Premio registro) {
		if (registro!=null)
			return "\t id = " + registro.getId() + "\t idProducto: " + registro.getProducto().getId() + "\t Importe: " + registro.getImporte();
		else
			return "\t No";
	}
	
	public String showResultAll(ArrayList<Premio> registros){
		String rta="";
		for (Premio item : registros)
			rta.concat(showResult(item) + "\n");
		return rta;
	}
}

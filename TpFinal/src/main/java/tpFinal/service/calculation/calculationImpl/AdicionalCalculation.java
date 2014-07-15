package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;
import tpFinal.dao.impl.AdicionalDao;
import tpFinal.domain.Adicional;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.service.calculation.CalculationUtils;
//import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.AdicionalFindItem;
import tpFinal.service.findItem.findItemImpl.VendedorFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class AdicionalCalculation extends CalculationUtils{
	private AdicionalDao dao;
	private AdicionalFindItem findItem;
	private VendedorFindItem vendedorFindItem;
	protected VentaFindItem findVentas;
	
	private PremioMesCalculation calculoPremioMes;
	private PremioCampaniaCalculation calculoPremioCampania;
	private ComisionProductoCalculation calculoComisionProducto;
	private ComisionVentaCalculation calculoComisionVenta;
	
	//Parametros del calculo
	private ArrayList<Venta> ventas;
	private ArrayList<Vendedor> vendedores;
	private Date fechaHoy;
	private Date fechaDesde;
	private Date fechaHasta;
	
	public void setDao(AdicionalDao dao){
		this.dao=dao;
	}
	
	public void setFindItem(AdicionalFindItem findItem){
		this.findItem= findItem;
	}
	
	public void setVendedorFindItem(VendedorFindItem vendedorFindItem) {
		this.vendedorFindItem = vendedorFindItem;
	}

	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas=findVentas;
	}
	
	public ArrayList<Vendedor> getVendedoresActivos() {
		return (ArrayList<Vendedor>) vendedorFindItem.getAllByFlag(true);
	}

	public void setCalculoPremioMes(PremioMesCalculation calculoPremioMes){
		this.calculoPremioMes=calculoPremioMes;
	}
	
	public void setCalculoPremioCampania(PremioCampaniaCalculation calculoPremioCampania){
		this.calculoPremioCampania=calculoPremioCampania;
	}
	
	public void setCalculoComisionProducto(ComisionProductoCalculation calculoComisionProducto){
		this.calculoComisionProducto= calculoComisionProducto;
	}
	
	public void setCalculoComisionVenta(ComisionVentaCalculation calculoComisionVenta){
		this.calculoComisionVenta=calculoComisionVenta;
	}
	
	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}
	
	public ArrayList<Vendedor> getVendedores() {
		return vendedores;
	}
	
	public void setVendedores(ArrayList<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}
	
	public void resetParams(){
		vendedores = new ArrayList<Vendedor>();
		ventas = new ArrayList<Venta>();
		this.fechaHoy=new Date();
	}

	public void setParams(){
		calculoPremioMes.setParams(vendedores, fechaDesde, fechaHoy);
		calculoPremioCampania.setParams(vendedores, fechaDesde, fechaHasta, fechaHoy);
		calculoComisionProducto.setParams(fechaDesde, fechaHasta, fechaHoy);
		calculoComisionVenta.setParams(fechaDesde, fechaHasta, fechaHoy);
	}

	public ArrayList<Adicional> calcularTodos(Date fechaDesde, Date fechaHasta){
		ArrayList<Adicional> adicionales = new ArrayList<Adicional>();
		
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		setParams();//seteo los parametros necesarios para hacer el calculo
				
		//PREMIOS (los calculo primero porque no están relacionados a los vendedores elegidos
		ArrayList<Premio> premiosCampania=calculoPremioCampania.calcularTodos();
		Premio premioMejorVendedorMes=calculoPremioMes.calcular(fechaHasta);
				
		//calculo los adicionales en general
		Adicional adicional=new Adicional();
				
		for(Vendedor vendedor : vendedores)
		{
			ventas=findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, getFechaIntervaloHasta(fechaDesde));
			
			if (ventas!=null && !ventas.isEmpty())
			{
				adicional=calcularUno(vendedor, fechaHoy, fechaDesde, fechaHasta, premioMejorVendedorMes, premiosCampania);
				setTotales(adicional);
				dao.merge(adicional);
				if (adicional!=null)//si en efecto hay un adicional calculado
					adicionales.add(adicional);
			}
		}
		
		//mostrarResultado(adicionales);
		
		return adicionales;
	}
	
	private Adicional calcularUno(Vendedor vendedor, Date fechaHoy, Date desde, Date hasta, Premio vendedorMes, ArrayList<Premio> campanias) {
		ArrayList<ComisionProducto> cProductos=calculoComisionProducto.calcularTodos(vendedor);
		ArrayList<Premio> campaniasVendedor = new ArrayList<Premio>();
		ComisionVenta cVenta=calculoComisionVenta.calcular(vendedor);
		Adicional registro;
		
		registro= new Adicional(fechaHoy, desde, hasta, vendedor, cVenta, cProductos, null, null);
		
		//verifico que el premio por mejor vendedor del mes sea para este vendedor
		if ((vendedorMes!=null) && (vendedorMes.getPremiado().getId() == vendedor.getId()))
			registro.setMejorVendedorMes(vendedorMes);
		
		//verifico que las campanias sean realmente para este vendedor, y las agrego al ArrayList que le corresponde
		for (Premio item : campanias){
			if (item.getPremiado().getId() == vendedor.getId()){
				campaniasVendedor.add(item);
			}
		}
		if (!campaniasVendedor.isEmpty()){//si realmente hay premios por campania para este vendedor los guardo
			registro.setCampanias(campaniasVendedor);
		}
		
		if (findItem.findIdByObject(registro)!=0)
			registro.setId(findItem.findIdByObject(registro));
		
		return registro;
	}
	
	public void setAllTotales(){
		for (Adicional item : dao.getAll()){
			setTotales(item);
			dao.update(item);
		}
	}

	public void setTotales(Adicional registro)
	{
		float[] subtotales = calcularSubtotales(registro);
		registro.setTotalComisionProducto(subtotales[0]);
		registro.setTotalPremiosCampania(subtotales[1]);
		registro.setTotalAdicionales(subtotales[2]);
	}
	
	@SuppressWarnings("unused")
	private float[] calcularSubtotales(Adicional registro)
	{
		float[] subtotales = new float[3];
		for (float i : subtotales)
			i = 0;
		
		//comision productos
		if (registro.getComisionesProducto()!=null && !registro.getComisionesProducto().isEmpty())
		{
			for (ComisionProducto comisionProducto : registro.getComisionesProducto())
				subtotales[0] +=comisionProducto.getImporte();
		}
		
		//premio por campania
		if (registro.getCampanias()!=null && !registro.getCampanias().isEmpty())
		{
			for (Premio campania : registro.getCampanias())
				subtotales[1] +=campania.getImporte();
		}
		
		//registro
		subtotales[2]=subtotales[0] + subtotales[1];
		
		if (registro.getComisionVentas()!=null)
			subtotales[2] +=registro.getComisionVentas().getImporte();
		
		if(registro.getMejorVendedorMes()!=null)
			subtotales[2] += registro.getMejorVendedorMes().getImporte();
		
		return subtotales;
	}
		
	//@Override
	public String showResult(Adicional object) {
		return object.getId() + "\n\t Creado: " + object.getFechaCreacion().toString() + 
								"\n\t Desde " + object.getFechaDesde() + "\t Hasta " + object.getFechaHasta()+
								"\n\t Vendedor: " + object.getVendedor().getNombre() + " " + object.getVendedor().getApellido()+
								"\n\t ComisionProducto: " + calculoComisionProducto.showResultAll((ArrayList<ComisionProducto>) object.getComisionesProducto())+
								"\n\t ComisionVenta: " + calculoComisionVenta.showResult(object.getComisionVentas()) + 
								"\n\t Premios Campania: " + calculoPremioCampania.showResultAll((ArrayList<Premio>) object.getCampanias()) +
								"\n\t Premio Mejor Vendedor Mes: " + calculoPremioMes.showResult(object.getMejorVendedorMes());
	}
}

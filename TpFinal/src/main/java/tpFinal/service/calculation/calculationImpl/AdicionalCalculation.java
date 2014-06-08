package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import tpFinal.dao.impl.AdicionalDao;
import tpFinal.domain.Adicional;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
//import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.AdicionalFindItem;
import tpFinal.service.findItem.findItemImpl.VendedorFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class AdicionalCalculation{
	private AdicionalDao dao;
	private AdicionalFindItem findItem;
	private VendedorFindItem vendedorFindItem;
	protected VentaFindItem findVentas;
	
	private PremioMesCalculation calculoPremioMes;
	private PremioCampaniaCalculation calculoPremioCampania;
	private ComisionProductoCalculation calculoComisionProducto;
	private ComisionVentaCalculation calculoComisionVenta;
	
	protected ArrayList<Venta> ventas;
	protected ArrayList<Vendedor> vendedores;
	protected Date fechaHoy;
	protected Date fechaDesde;
	protected Date fechaHasta;
	
	public void setAdicionalDao(AdicionalDao dao){
		this.dao=dao;
	}
	
	public void setFindItem(AdicionalFindItem findItem){
		this.findItem= findItem;
	}
	
	public void setVendedorFindItem(VendedorFindItem vendedorFindItem) {
		this.vendedorFindItem = vendedorFindItem;
	}

	public void setFindVentas(VentaFindItem findVentas) {
		this.findVentas = findVentas;
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
	

	public ArrayList<Adicional> calcularTodos(Date fechaDesde, Date fechaHasta){
		ArrayList<Adicional> adicionales = new ArrayList<Adicional>();
		
		this.fechaHoy=new Date();
		this.fechaDesde=fechaDesde;
		this.fechaHasta=fechaHasta;
		
		GregorianCalendar gregorianHasta= new GregorianCalendar();
		gregorianHasta.setTime(fechaHasta);
		gregorianHasta.add(2, 1);
		
		//PREMIOS (los calculo primero porque no están relacionados a los vendedores elegidos
		ArrayList<Premio> premiosCampania=calculoPremioCampania.calcularTodos();
		Premio premioMejorVendedorMes=calculoPremioMes.calcular(gregorianHasta.getTime());
				
		//calculo los adicionales en general
		Adicional adicional=new Adicional();
				
		for(Vendedor vendedor : vendedores)
		{
			/*if ((service.getAdicional(vendedor.getId(), desde, hasta)==null) && (service.existenVentas(vendedor, desde, hasta)))//si no hay un registro para esas fechas y vendedor
			{
				adicional=calcularAdicionalVendedor(getFechaHoy(), desde, hasta, vendedor, premioMejorVendedorMes, premiosCampania);
				setTotales(adicional);
				service.guardarAdicional(adicional);
			}
			else
				adicional=service.getAdicional(vendedor.getId(), desde, hasta);//lo traigo de la base
			
			if (adicional!=null)//si en efecto hay un adicional calculado
				adicionales.add(adicional);*/
			
			setVentas(findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, fechaHasta));
			
			if (!ventas.isEmpty())
			{
				adicional=calcularUno(vendedor, fechaHoy, fechaDesde, fechaHasta, premioMejorVendedorMes, premiosCampania);
				setTotales(adicional);
				dao.save(adicional);
				if (adicional!=null)//si en efecto hay un adicional calculado
					adicionales.add(adicional);
			}
		}
		
		//mostrarResultado(adicionales);
		
		return adicionales;
	}
	
	private Adicional calcularUno(Vendedor vendedor, Date fechaHoy, Date desde, Date hasta, Premio vendedorMes, ArrayList<Premio> campanias) {
		ArrayList<ComisionProducto> cProductos=calculoComisionProducto.calcularTodos(vendedor);
		ComisionVenta cVenta=calculoComisionVenta.calcular(vendedor);
		Adicional registro;
		
		registro= new Adicional(fechaHoy, desde, hasta, vendedor, cVenta, cProductos, null, campanias);
		
		if ((vendedorMes!=null) && (vendedorMes.getPremiado().getId() == vendedor.getId()))
			registro.setMejorVendedorMes(vendedorMes);
		
		if (findItem.findIdByObject(registro)!=0)
			registro.setId(findItem.findIdByObject(registro));
		
		return registro;
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
		if (!registro.getComisionesProducto().isEmpty())
		{
			for (ComisionProducto comisionProducto : registro.getComisionesProducto())
				subtotales[0] +=comisionProducto.getImporte();
		}
		
		//premio por campania
		if (!registro.getCampanias().isEmpty())
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
	
	protected int contarProductoVenta(Venta venta, Producto producto)
	{
		int i=0;
		ArrayList<Producto> productosVenta=(ArrayList<Producto>) venta.getProductos();
		for (Producto item : productosVenta)
			if (item.getId()==producto.getId())
				i++;
		
		//System.out.print(i + " veces");
		return i;
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

package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import tpFinal.dao.impl.AdicionalDao;
import tpFinal.domain.Adicional;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.Premio;
import tpFinal.domain.Vendedor;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.AdicionalFindItem;

public class AdicionalCalculation extends CalculationService<Adicional>{
	private AdicionalDao dao;
	private AdicionalFindItem findItem;
	
	private PremioMesCalculation calculoPremioMes;
	private PremioCampaniaCalculation calculoPremioCampania;
	private ComisionProductoCalculation calculoComisionProducto;
	private ComisionVentaCalculation calculoComisionVenta;
	
	public void setAdicionalDao(AdicionalDao dao){
		this.dao=dao;
	}
	
	public void setFindItem(AdicionalFindItem findItem){
		this.findItem= findItem;
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
	
	public ArrayList<Adicional> calcularTodos(ArrayList<Vendedor> vendedores, Date fechaDesde, Date fechaHasta){
		ArrayList<Adicional> adicionales = new ArrayList<Adicional>(); 
		Date fechaHoy= new Date();
		
		//PREMIOS (los calculo primero porque no están relacionados a los vendedores elegidos
		ArrayList<Premio> premiosCampania=calculoPremioCampania.calcularTodos(vendedores, fechaHoy, fechaDesde, fechaHasta);
		Premio premioMejorVendedorMes=calculoPremioMes.calcular(vendedores, fechaHoy, fechaDesde, fechaHasta);
				
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
	
	public Adicional calcularUno(Vendedor vendedor, Date fechaHoy, Date desde, Date hasta, Premio vendedorMes, ArrayList<Premio> campanias) {
		GregorianCalendar gregorianHasta= new GregorianCalendar();
		gregorianHasta.setTime(desde);
		gregorianHasta.add(2, 1);
		
		ArrayList<ComisionProducto> cProductos=calculoComisionProducto.calcularTodos(vendedor, fechaHoy, desde, gregorianHasta.getTime());
		ComisionVenta cVenta=calculoComisionVenta.calcular(vendedor, fechaHoy, desde, hasta);
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
	public float[] calcularSubtotales(Adicional registro)//FIXIT: función temporalmente acá hasta llegar a esta parte del refractor
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
	
	@Override
	public void showResult(Adicional object) {
		// TODO Auto-generated method stub
		
	}
	
}

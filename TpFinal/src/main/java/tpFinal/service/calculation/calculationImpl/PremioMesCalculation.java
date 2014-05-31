package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.PremioMontoDao;
import tpFinal.domain.Premio;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.domain.adicional.monto.PremioMonto;
import tpFinal.service.calculation.CalculationService;
import tpFinal.service.findItem.findItemImpl.PremioFindItem;

public class PremioMesCalculation extends CalculationService<Premio>{
	private PremioFindItem findItem;
	protected PremioMontoDao daoMontos;
		
	public void setDaoMontos(PremioMontoDao daoMontos) {
		this.daoMontos = daoMontos;
	}
	
	public void setFindItem(PremioFindItem findItem){
		this.findItem=findItem;
	}
	
	public Premio calcular(ArrayList<Vendedor> vendedores, Date fechaHoy, Date fechaDesde, Date fechaHasta) {
		int cantidad=0;
		Premio registro;
		ArrayList<Venta>ventas = new ArrayList<Venta>();
		Vendedor premiado=new Vendedor();
		PremioMonto monto=daoMontos.getMontoPremioMes();
		
		//System.out.println("Premio mejor vendedor del mes: (" + df.format(desde.getTime()) + " - " + df.format(hasta) + ")");
	
		for (Vendedor vendedor : vendedores)
		{
			ventas=findVentas.findBySpecificDatesCreatorId(vendedor.getId(), fechaDesde, fechaHasta);//FIXIT: no recupera ninguna venta!
			
			if (!ventas.isEmpty())
			{
				if (ventas.size()>cantidad)
				{
					cantidad=ventas.size();
					premiado=vendedor;
				}
			}/*
			else //Verificacion
			{
				System.out.println("\t\t" No se encontraron ventas para el período buscado");
			}*/
		//System.out.print("\n");
		}
		
		registro=new Premio(fechaHoy, fechaDesde, fechaHasta, premiado, false, null, monto.getMonto());
		
		if (cantidad==0)
			return null;
		if (findItem.findIdByObject(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos //TODO: testear!!
			registro.setId(findItem.findIdByObject(registro));
		
				
		return registro;
	}
		@Override
	public void showResult(Premio registro) {
		System.out.println("Premio mejor vendedor del mes: (" + registro.getFechaDesde().toString() + " - " + registro.getFechaHasta().toString() + ")\t" + registro.getPremiado().getNombre() + " " + registro.getPremiado().getApellido());
	}

}

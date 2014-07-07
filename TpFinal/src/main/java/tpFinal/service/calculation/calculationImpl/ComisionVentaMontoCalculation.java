package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.ComisionVentaDao;
import tpFinal.dao.impl.ComisionVentaMontoDao;
import tpFinal.domain.ComisionVenta;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;

public class ComisionVentaMontoCalculation {
	private ComisionVentaMontoDao montoDao;
	private ComisionVentaDao registroComisionDao;
	private ArrayList<ComisionVenta> registrosComision;
	
	public void setMontoDao(ComisionVentaMontoDao montoDao) {
		this.montoDao = montoDao;
	}
	
	public void setRegistroComisionDao(ComisionVentaDao registroComisionDao) {
		this.registroComisionDao = registroComisionDao;
	}

	public void actualizar(float[] valores) {
		ComisionVentaMonto registro;
		ArrayList<ComisionVentaMonto> registrosMonto=montoDao.getAll();
		registrosComision=registroComisionDao.getAll();
		
		/* //Verificar
		System.out.print("Comision Venta \t Actualizar: ");
		for (float item : valores)
		System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++)
		{
			registro=registrosMonto.get(i);
			
			if (isChanged(registro,valores[i])){
				actualizarRegistrosComision(registro,valores[i]);
				actualizarRegistroMonto(registro,valores[i]);
			}
		}
	}
	
	public int actualizar(int id, float valor) {
		ComisionVentaMonto registro=montoDao.get(id);
		int cantidadActualizados=0;
		
		if (!isChanged(registro, valor)){
			return 0;
		}
		//actualizo todos los importes de los registros
		for (ComisionVenta item : registroComisionDao.getAll())
		{
			if(item.getImporte() == registro.getMonto())//si el importe coincide con el mismo del registro a modificar
			{
				item.setImporte(valor);
				registroComisionDao.update(item);
				cantidadActualizados++;
			}
		}
		
		//actualizo el registro ComisionVentaMonto
		actualizarRegistroMonto(registro, valor);
		return cantidadActualizados;
	}

	
	private boolean isChanged(ComisionVentaMonto registro, float valor){
		if (registro.getMonto()!=valor){
			return true;
		}
		return false;
	}
	
	private void actualizarRegistrosComision(ComisionVentaMonto registro, float montoNuevo){
		for (ComisionVenta comision : registrosComision)//actualizo todos los importes de los comisions
		{
			if(comision.getImporte()==registro.getMonto())
			{
				comision.setImporte(montoNuevo);
				registroComisionDao.update(comision);
			}
		}
	}
	
	private void actualizarRegistroMonto(ComisionVentaMonto registro, float montoNuevo){
		registro.setMonto(montoNuevo);
		montoDao.update(registro);
	}
}

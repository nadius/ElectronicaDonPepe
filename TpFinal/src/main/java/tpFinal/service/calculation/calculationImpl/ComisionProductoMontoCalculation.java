package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.ComisionProductoDao;
import tpFinal.dao.impl.ComisionProductoMontoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.domain.adicional.monto.ComisionProductoMonto;

public class ComisionProductoMontoCalculation {
	private ComisionProductoMontoDao montoDao;
	private ComisionProductoDao registroComisionDao;
	private ArrayList<ComisionProducto> registrosComision;
	
	public void setMontoProductoDao(ComisionProductoMontoDao montoProductoDao) {
		this.montoDao = montoProductoDao;
	}
	public void setComisionProductoDao(ComisionProductoDao comisionProductoDao) {
		this.registroComisionDao = comisionProductoDao;
	}
	
	public void actualizar(float[] valores) {
		ComisionProductoMonto registro;
		ArrayList<ComisionProductoMonto> registrosMonto=montoDao.getAll();
		registrosComision=registroComisionDao.getAll();
		
		/* //Verificar
		System.out.print("Comision Producto \t Actualizar: ");
		for (float item : valores)
		System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++)
		{
			registro=registrosMonto.get(i);
			if (isChanged(registro,valores[i])){
				actualizarRegistrosComisionProducto(registro,valores[i]);
				actualizarRegistroMonto(registro,valores[i]);
			}
		}
	}
	
	private boolean isChanged(ComisionProductoMonto registro, float valor){
		if (registro.getMonto()!=valor){
			return true;
		}
		return false;
	}
	
	private void actualizarRegistrosComisionProducto(ComisionProductoMonto registro, float montoNuevo){
		for (ComisionProducto comision : registrosComision)//actualizo todos los importes de los premios
		{
			if(comision.getImporte()==registro.getMonto())
			{
				comision.setImporte(montoNuevo);
				registroComisionDao.update(comision);
			}
		}
	}
	
	private void actualizarRegistroMonto(ComisionProductoMonto registro, float montoNuevo){
		registro.setMonto(montoNuevo);
		montoDao.update(registro);
	}
}

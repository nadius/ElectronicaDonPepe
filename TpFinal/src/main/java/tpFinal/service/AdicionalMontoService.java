package tpFinal.service;

import java.util.ArrayList;

import tpFinal.dao.impl.ComisionProductoMontoDao;
import tpFinal.dao.impl.ComisionVentaMontoDao;
import tpFinal.dao.impl.PremioMontoDao;
import tpFinal.domain.adicional.monto.ComisionProductoMonto;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;
import tpFinal.domain.adicional.monto.PremioMonto;
import tpFinal.service.calculation.calculationImpl.AdicionalMontoCalculation;

public class AdicionalMontoService {
	private ComisionVentaMontoDao montoVentaDao;
	private ComisionProductoMontoDao montoProductoDao;
	private PremioMontoDao montoPremioDao;
	
	private AdicionalMontoCalculation calculation;
	
	
	public void setMontoVentaDao(ComisionVentaMontoDao montoVentaDao) {
		this.montoVentaDao = montoVentaDao;
	}
	public void setMontoProductoDao(ComisionProductoMontoDao montoProductoDao) {
		this.montoProductoDao = montoProductoDao;
	}
	public void setMontoPremioDao(PremioMontoDao montoPremioDao) {
		this.montoPremioDao = montoPremioDao;
	}	
	public void setCalculation(AdicionalMontoCalculation calculation) {
		this.calculation = calculation;
	}
	
	//Premio
	public ArrayList<PremioMonto> getAllMontosPremio() {
		return montoPremioDao.getAll();
	}
	public PremioMonto getMontoPremio(int id){
		return montoPremioDao.get(id);
	}
	public int actualizarMontoPremio(int id, float valor){
		return calculation.actualizarPremio(id, valor);
	}
		
	//ComisionVenta
	public ArrayList<ComisionVentaMonto> getAllMontoComisionVenta() {
		return montoVentaDao.getAll();
	}
	public ComisionVentaMonto getMontoComisionVenta(int id){
		return montoVentaDao.get(id);
	}
	public int actualizarMontoComisionVenta(int id, float valor){
		return calculation.actualizarComisionVenta(id, valor);
	}

	//ComisionProducto
	public ArrayList<ComisionProductoMonto> getAllMontosComisionProducto() {
		return montoProductoDao.getAll();
	}
	public ComisionProductoMonto getMontoComisionProducto(int id){
		return montoProductoDao.get(id);
	}
	public int actualizarMontoComisionProducto(int id, float valor){
		return calculation.actualizarComisionProducto(id, valor);
	}
}

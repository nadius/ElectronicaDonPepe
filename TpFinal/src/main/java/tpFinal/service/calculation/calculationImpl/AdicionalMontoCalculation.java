package tpFinal.service.calculation.calculationImpl;

public class AdicionalMontoCalculation {
	private ComisionProductoMontoCalculation comisionProducto;
	private ComisionVentaMontoCalculation comisionVenta;
	private PremioMontoCalculation premio;
	private AdicionalCalculation adicional;
	
	
	public void setComisionProducto(ComisionProductoMontoCalculation comisionProducto) {
		this.comisionProducto = comisionProducto;
	}
	public void setComisionVenta(ComisionVentaMontoCalculation comisionVenta) {
		this.comisionVenta = comisionVenta;
	}
	public void setPremio(PremioMontoCalculation premio) {
		this.premio = premio;
	}
	public void setAdicional(AdicionalCalculation adicional) {
		this.adicional = adicional;
	}
	
	public int actualizarComisionProducto(int id, float valor){
		int rta = comisionProducto.actualizar(id, valor);
		adicional.setAllTotales();
		return rta;
	}
	
	public int actualizarComisionVenta(int id, float valor){
		int rta = comisionVenta.actualizar(id, valor);
		adicional.setAllTotales();
		return rta;
	}
	
	public int actualizarPremio(int id, float valor){
		int rta = premio.actualizar(id, valor);
		adicional.setAllTotales();
		return rta;
	}
}

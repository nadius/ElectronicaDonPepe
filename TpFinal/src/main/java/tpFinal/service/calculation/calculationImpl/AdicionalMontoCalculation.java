package tpFinal.service.calculation.calculationImpl;

public class AdicionalMontoCalculation {
	private ComisionProductoMontoCalculation comisionProducto;
	private ComisionVentaMontoCalculation comisionVenta;
	private PremioMontoCalculation premio;
	
	
	public void setComisionProducto(ComisionProductoMontoCalculation comisionProducto) {
		this.comisionProducto = comisionProducto;
	}
	public void setComisionVenta(ComisionVentaMontoCalculation comisionVenta) {
		this.comisionVenta = comisionVenta;
	}
	public void setPremio(PremioMontoCalculation premio) {
		this.premio = premio;
	}
	
	public void actualizarComisionProducto(float[] valores){
		comisionProducto.actualizar(valores);
	}
	
	public void actualizarComisionVenta(float[] valores){
		comisionVenta.actualizar(valores);
	}
	
	public void actualizarPremio(float[] valores){
		premio.actualizar(valores);
	}
}

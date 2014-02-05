package TpFinal.dataccess;

import java.util.ArrayList;
import TpFinal.domain.*;
import TpFinal.domain.adicional.monto.ComisionProductoMonto;
import TpFinal.domain.adicional.monto.ComisionVentaMonto;
import TpFinal.domain.adicional.monto.PremioMonto;
import TpFinal.security.RolUsuario;
import TpFinal.security.Usuario;

public interface DataAccess {
	
//USUARIOS
	//Por id
	public Usuario getUsuario(Integer id);
	
	//Todos
	public ArrayList<Usuario> getUsuarios();
	
	//Guardar
	public void guardarUsuario(Usuario usuario);
	
	//Actualizar
	public void actualizarUsuario(Usuario usuario);
	
//ROL DE USUARIOS
	//Por id
	public RolUsuario getRol(Integer id);
	
	public ArrayList<RolUsuario> getRoles();
	
	//Guardar
	public void guardarRol(RolUsuario rol);
	
	//Actualizar
	//public void actualizarRol(Integer id);

//VENDEDOR
	//Por id
	public Vendedor getVendedor(Integer id);

	//Obtiene todos los vendedores
	public ArrayList<Vendedor> getVendedores();
	
	//Guardar
	public void guardarVendedor(Vendedor vendedor);
	
	//Actualizar
	public void actualizarVendedor(Vendedor vendedor);
	
//PRODUCTO
	//Por id
	public Producto getProducto(Integer id);

	//Todos
	public ArrayList<Producto> getProductos();
	
	//Guardar
	public void guardarProducto(Producto producto);
	
//VENTA
	//Por id de Venta
	public Venta getVenta(Integer idVenta);
	
	//Por id del Vendedor
	//public ArrayList<Venta> getVentas(Integer idVendedor);

	//Todos
	public ArrayList<Venta> getVentas();
	
	//Guardar
	public void guardarVenta(Venta venta);
	
//MONTOS DE COMISIONES
//COMISION PRODUCTO
	//Por id
	public ComisionProductoMonto getMontoProducto(Integer id);
	
	//Todos
	public ArrayList<ComisionProductoMonto> getMontosProducto();
	
	//Actualizar
	public void actualizarProductoMonto(ComisionProductoMonto item);
	
	//Guardar
	public void guardarProductoMonto(ComisionProductoMonto item);
	
//COMISION VENTA
	//Por id
	public ComisionVentaMonto getMontoVenta(Integer id);
	
	//Todos
	public ArrayList<ComisionVentaMonto> getMontosVenta();
	
	//Actualizar
	public void actualizarVentaMonto(ComisionVentaMonto item);
	
	//Guardar
	public void guardarVentaMonto(ComisionVentaMonto item);
	
//PREMIO
	//Por id
	public PremioMonto getMontoPremio(Integer id);
	
	//Todos
	public ArrayList<PremioMonto> getMontosPremio();
	
	//Actualizar
	public void actualizarPremioMonto(PremioMonto item);
	
	//Guardar
	public void guardarPremioMonto(PremioMonto item);
		
//ADICIONAL
	//Por id
	public Adicional getAdicional(Integer idAdicional);
	
	//Por fecha
	//public ArrayList<Adicional> getAdicional(Date fecha);
	
	//Por Vendedor
	//public ArrayList<Adicional> getAdicional(Integer idVendedor);
	
	//Por fecha y vendedor
	//public Adicional getAdicional(Integer idVendedor, Date fecha);
	
	//todos
	public ArrayList<Adicional> getAdicionales();
	
	//Guardar
	public void guardarAdicional(Adicional adicional);
	
//COMISION
	//por id
	public ComisionVenta getComisionVenta(Integer id);
	public ComisionProducto getComisionProducto(Integer id);
		
	//por fecha
	//public ArrayList<ComisionVenta> getComisionVenta(Date fecha);
	//public ArrayList<ComisionProducto> getComisionProducto(Date fecha);
	
	//por vendedor
	//public ArrayList<ComisionVenta> getComisionVenta(Vendedor vendedor);
	//public ArrayList<ComisionProducto> getComisionProducto(Vendedor vendedor);
	
	//por fecha y vendedor
	//public ComisionVenta getComisionVenta(Date fecha, Vendedor vendedor);
	//public ComisionProducto getComisionProducto(Date fecha, Vendedor vendedor);
	 
	//todo
	public ArrayList<ComisionVenta> getComisionVenta();
	public ArrayList<ComisionProducto> getComisionProducto();
	
	//actualizar
	public void actualizarComisionVenta(ComisionVenta comisionVenta);
	public void actualizarComisionProducto(ComisionProducto comisionProducto);
	
	//guardar
	public void guardarComisionVenta(ComisionVenta comisionVenta);
	public void guardarComisionProducto(ComisionProducto comisionProducto);
	
	
//PREMIO
	//por id
	public Premio getPremio(Integer id);
	
	//por fecha
	//public ArrayList<Premio> getPremioMejorVendedorMes(Date fecha);
	//public ArrayList<Premio> getPremioCampania(Date fecha);
	
	//todo
	public ArrayList<Premio> getPremioMejorVendedorMes();
	public ArrayList<Premio> getPremioCampania();
	public ArrayList<Premio> getPremio();
	
	//actualizar
	public void actualizarPremio(Premio premio);
	
	//guardar
	public void guardarPremio(Premio premio);
	
//PAYROLL
	//por id
	public Payroll getPayroll(Integer id);
	
	//Por fecha
	//public ArrayList<Payroll> getPayroll(Date fecha);
	
	//Por Vendedor
	//public ArrayList<Payroll> getPayroll(Integer idVendedor);
	
	//Por fecha y vendedor
	//public ArrayList<Payroll> getPayroll(Date fecha, Integer idVendedor);
	
	//todos
	public ArrayList<Payroll> getPayroll();
	
	//actualizar
	public void actualizarPayroll(Payroll payroll);
	
	//Guardar
	public void guardarPayroll(Payroll payroll);
}
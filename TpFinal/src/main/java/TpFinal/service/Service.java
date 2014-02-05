package TpFinal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import TpFinal.domain.Adicional;
import TpFinal.domain.ComisionProducto;
import TpFinal.domain.ComisionVenta;
import TpFinal.domain.Payroll;
import TpFinal.domain.Premio;
import TpFinal.domain.Producto;
import TpFinal.domain.Vendedor;
import TpFinal.domain.Venta;
import TpFinal.domain.adicional.monto.ComisionProductoMonto;
import TpFinal.domain.adicional.monto.ComisionVentaMonto;
import TpFinal.domain.adicional.monto.PremioMonto;
import TpFinal.security.RolUsuario;
import TpFinal.security.Usuario;

public interface Service {

//Usuarios
	//Por id
	public Usuario getUsuario(Integer id);
	
	//Todos
	public ArrayList<Usuario> getUsuarios();
	
	//Guardar
	public void guardarUsuario(Usuario usuario);
	
	//Actualizar
	public void actualizarUsuario(Usuario usuario);
	
	public boolean login(String username, String password);
	
	public Usuario getUsuario(String username);
	
//ROL DE USUARIOS
	//Por id
	public RolUsuario getRol(Integer id);
	
	public ArrayList<RolUsuario> getRoles();
	
	//Guardar
	public void guardarRol(RolUsuario rol);

//VENDEDOR
	//Por id
	public Vendedor getVendedor(Integer id);

	//Obtiene todos los vendedores
	public ArrayList<Vendedor> getVendedores();
	
	//Todos los vendedores activos
	public ArrayList<Vendedor> getVendedoresActivos();
	
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
	public ArrayList<Venta> getVentas(int idVendedor);
	
	//Por fechas y vendedor
	public ArrayList<Venta> getVentas(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta);
	
	//Por fecha
	public ArrayList<Venta> getVentas(GregorianCalendar desde, GregorianCalendar hasta);
	
	//Todos
	public ArrayList<Venta> getVentas();
	
	//Guardar
	public void guardarVenta(Venta venta);
	
//MONTOS DE COMISIONES
//COMISION PRODUCTO
	//Por id
	public ComisionProductoMonto getMontoProducto(Integer id);
	
	//Por producto
	public ComisionProductoMonto getMontoProducto(Producto producto);
	
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
	
	//Campaña o no campaña?
	public PremioMonto getMontoPremio(boolean campania);
	
	//Actualizar
	public void actualizarPremioMonto(PremioMonto item);
		
	//Guardar
	public void guardarPremioMonto(PremioMonto item);
	
//ADICIONAL
	//Por id
	public Adicional getAdicional(Integer idAdicional);
	
	//Por fecha
	public ArrayList<Adicional> getAdicional(Date fecha);
	
	//Por Vendedor
	public ArrayList<Adicional> getAdicional(int idVendedor);
	
	//Por fecha y vendedor
	public Adicional getAdicional(int idVendedor, Date fecha);
	
	//Por fechas filtradas y vendedor
	public Adicional getAdicional(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta);
	
	//Por fechas filtradas
	public ArrayList<Adicional> getAdicional(GregorianCalendar desde, GregorianCalendar hasta);
	
	//todos
	public ArrayList<Adicional> getAdicionales();
	
	//Guardar
	public void guardarAdicional(Adicional adicional);
	
//COMISION
	//por id
	public ComisionVenta getComisionVenta(Integer id);
	public ComisionProducto getComisionProducto(Integer id);
		
	//por fecha
	public ArrayList<ComisionVenta> getComisionVenta(Date fecha);
	public ArrayList<ComisionProducto> getComisionProducto(Date fecha);
	
	//por vendedor
	public ArrayList<ComisionVenta> getComisionVenta(int idVendedor);
	public ArrayList<ComisionProducto> getComisionProducto(int idVendedor);
	
	//por fecha y vendedor
	public ComisionVenta getComisionVenta(Date fecha, int idVendedor);
	public ComisionProducto getComisionProducto(Date fecha, int idVendedor);
	
	//por fecha filtrada y vendedor
	public ComisionVenta getComisionVenta(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta);
	public ComisionProducto getComisionProducto(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta, Producto producto);
	
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
	
	//por fecha de creacion
	public ArrayList<Premio> getPremioMejorVendedorMes(Date fechaCreacion);
	public ArrayList<Premio> getPremioCampania(Date fechaCreacion);
	
	//por fecha y vendedor
	public Premio getPremioMejorVendedorMes(Date fechaCreacion, Vendedor vendedor);
	public Premio getPremioCampania(Date desde, Date hasta, Vendedor vendedor);
	
	//Por fechas filtradas y vendedor
	public Premio getPremioMejorVendedorMes(Date desde, int idVendedor);
	public Premio getPremioCampania(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta, Producto producto);
	
	//Por fechas filtradas
	public Premio getPremioMejorVendedorMes(GregorianCalendar desde);
	public Premio getPremioCampania(GregorianCalendar desde, GregorianCalendar hasta, Producto producto);
	
	//Por vendedor
	public ArrayList<Premio> getPremioCampania(int idVendedor);
	public ArrayList<Premio> getPremioMejorVendedorMes(int idVendedor);
	
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
	public ArrayList<Payroll> getPayroll(Date fecha);
	
	//Por Vendedor
	public ArrayList<Payroll> getPayroll(int idVendedor);
	
	//Por fecha y vendedor
	public ArrayList<Payroll> getPayroll(Date fecha, int idVendedor);
	
	//todos
	public ArrayList<Payroll> getPayroll();
	
	//actualizar
	public void actualizarPayroll(Payroll payroll);
		
	//Guardar
	public void guardarPayroll(Payroll payroll);
}

package TpFinal.dataccess;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import TpFinal.domain.Adicional;
import TpFinal.domain.Campania;
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

public class DataAccessPlano implements DataAccess
{
	private static Session session;
	
	private void abrirConexion() {
		 session = HIbernateUtil.getSession();
	}
	
	private void cerrarConexion() {
		session.close();
	}
	
//USUARIOS
	@Override
	public Usuario getUsuario(Integer id)
	{
		Usuario rta = new Usuario();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Usuario> seleccion = session.createQuery("from TpFinal.security.Usuario item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
	@Override
	public ArrayList<Usuario> getUsuarios()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Usuario> seleccion = session.createQuery("from TpFinal.security.Usuario item").list();
		cerrarConexion();
		return (ArrayList<Usuario>)seleccion;
	}
	
	@Override
	public void actualizarUsuario(Usuario usuario) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(usuario);
		tx.commit();
		cerrarConexion();
	}

//ROL DE USUARIOS
	@Override
	public RolUsuario getRol(Integer id)
	{
		RolUsuario rta = new RolUsuario();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<RolUsuario> seleccion = session.createQuery("from TpFinal.security.RolUsuario item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
	@Override
	public ArrayList<RolUsuario> getRoles()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<RolUsuario> seleccion = session.createQuery("from TpFinal.security.RolUsuario item").list();
		cerrarConexion();
		return (ArrayList<RolUsuario>)seleccion;
	}
		
//VENDEDOR
	@Override
	public Vendedor getVendedor(Integer id)
	{
		Vendedor rta = new Vendedor();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Vendedor> seleccion = session.createQuery("from TpFinal.domain.Vendedor item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
	@Override
	public ArrayList<Vendedor> getVendedores()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Vendedor> seleccion = session.createQuery("from TpFinal.domain.Vendedor item").list();
		cerrarConexion();
		return (ArrayList<Vendedor>)seleccion;
	}
	
	@Override
	public void guardarVendedor(Vendedor vendedor)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(vendedor);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarVendedor(Vendedor vendedor) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(vendedor);
		tx.commit();
		cerrarConexion();
	}

//PRODUCTO
	@Override
	public Producto getProducto(Integer id)
	{
		Producto rta = new Producto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Producto> seleccion = session.createQuery("from TpFinal.domain.Producto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

	@Override
	public ArrayList<Producto> getProductos()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Producto> seleccion = session.createQuery("from TpFinal.domain.Producto item").list();
		cerrarConexion();
		return (ArrayList<Producto>)seleccion;
	}
	
	@Override
	public void guardarProducto(Producto producto)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(producto);
		tx.commit();
		cerrarConexion();
	}
	
//VENTA
	@Override
	public Venta getVenta(Integer idVenta)
	{
		Venta rta = new Venta();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Venta> seleccion = session.createQuery("from TpFinal.domain.Venta item where item.id ="+idVenta.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

/*	@Override
	public ArrayList<Venta> getVentas(Integer idVendedor)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Venta> seleccion = session.createQuery("from TpFinal.domain.Venta item where item.id ="+idVendedor.toString()).list();
		cerrarConexion();
		return (ArrayList<Venta>) seleccion;
	}*/

	@Override
	public ArrayList<Venta> getVentas()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Venta> seleccion = session.createQuery("from TpFinal.domain.Venta item").list();
		cerrarConexion();
		return (ArrayList<Venta>)seleccion;
	}
	
	@Override
	public void guardarVenta(Venta venta)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(venta);
		tx.commit();
		cerrarConexion();
	}
	
//ADICIONAL
	//Por id
	@Override
	public Adicional getAdicional(Integer idAdicional)
	{	
		Adicional rta = new Adicional();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Adicional> seleccion = session.createQuery("from TpFinal.domain.Adicional item where item.id ="+idAdicional.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
/*	@Override
	public ArrayList<Adicional> getAdicional(Date fecha)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Adicional> seleccion = session.createQuery("from TpFinal.domain.Adicional item where item.fecha ="+fecha.toString()).list();
		cerrarConexion();
		return (ArrayList<Adicional>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<Adicional> getAdicional(Integer idVendedor)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Adicional> seleccion = session.createQuery("from TpFinal.domain.Adicional item where item.vendedor.id ="+idVendedor.toString()).list();
		cerrarConexion();
		return (ArrayList<Adicional>) seleccion;
	}*/
	
/*	@Override
	public Adicional getAdicional(Integer idVendedor, Date fecha)
	{
		abrirConexion();
		Adicional rta = new Adicional();
		@SuppressWarnings("unchecked")
		List<Adicional> seleccion = session.createQuery("from TpFinal.domain.Adicional item where item.fecha ="+fecha.toString() + "and item.vendedor.id ="+idVendedor.toString()).list();
		if (seleccion.size() == 0)
			rta= seleccion.get(0);
		cerrarConexion();
		return rta;
	}*/
	
	@Override
	public ArrayList<Adicional> getAdicionales()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Adicional> seleccion = session.createQuery("from TpFinal.domain.Adicional item").list();
		cerrarConexion();
		return (ArrayList<Adicional>) seleccion;
	}
	
	@Override
	public void guardarAdicional(Adicional adicional)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(adicional);
		tx.commit();
		cerrarConexion();
	}
	
//COMISION
	@Override
	public ComisionVenta getComisionVenta(Integer id)
	{
		ComisionVenta rta = new ComisionVenta();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVenta> seleccion = session.createQuery("from TpFinal.domain.ComisionVenta item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
	@Override
	public ComisionProducto getComisionProducto(Integer id)
	{
		ComisionProducto rta = new ComisionProducto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProducto> seleccion = session.createQuery("from TpFinal.domain.ComisionProducto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
/*	@Override
	public ArrayList<ComisionVenta> getComisionVenta(Date fecha)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVenta> seleccion = session.createQuery("from TpFinal.domain.ComisionVenta item where item.fecha ="+fecha.toString()).list();
		cerrarConexion();
		return (ArrayList<ComisionVenta>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<ComisionProducto> getComisionProducto(Date fecha)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProducto> seleccion = session.createQuery("from TpFinal.domain.ComisionProducto item where item.fecha ="+fecha.toString()).list();
		cerrarConexion();
		return (ArrayList<ComisionProducto>) seleccion;
	}*/
	
/*	@Override
	public ComisionVenta getComisionVenta(Integer id)
	{
		ComisionVenta rta = new ComisionVenta();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVenta> seleccion = session.createQuery("from TpFinal.domain.ComisionVenta item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}*/
	
/*	@Override
	public ComisionProducto getComisionProducto(Integer id)
	{
		ComisionProducto rta = new ComisionProducto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProducto> seleccion = session.createQuery("from TpFinal.domain.ComisionProducto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}*/
	
/*	@Override
	public ArrayList<ComisionVenta> getComisionVenta(Vendedor vendedor)
	{
		Integer id=vendedor.getId();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVenta> seleccion = session.createQuery("from TpFinal.domain.ComisionVenta item where item.vendedor.id ="+id.toString()).list();
		cerrarConexion();
		return (ArrayList<ComisionVenta>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<ComisionProducto> getComisionProducto(Vendedor vendedor)
	{
		Integer id=vendedor.getId();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProducto> seleccion = session.createQuery("from TpFinal.domain.ComisionProducto item where vendedor.id ="+id.toString()).list();
		cerrarConexion();
		return (ArrayList<ComisionProducto>) seleccion;
	}*/
	
/*	@Override
	public ComisionVenta getComisionVenta(Date fecha, Vendedor vendedor)
	{
		Integer id=vendedor.getId();
		abrirConexion();
		@SuppressWarnings("unchecked")
		ComisionVenta rta = (ComisionVenta) session.createQuery("from TpFinal.domain.ComisionVenta item where item.vendedor.id ="+id.toString() + " and item.fecha="+ fecha.toString()).list();
		cerrarConexion();
		return rta;
	}*/
	
/*	@Override
	public ComisionProducto getComisionProducto(Date fecha, Vendedor vendedor)
	{
		Integer id=vendedor.getId();
		abrirConexion();
		@SuppressWarnings("unchecked")
		ComisionProducto rta = (ComisionProducto) session.createQuery("from TpFinal.domain.ComisionProducto item where item.vendedor.id ="+id.toString() + " and item.fecha="+ fecha.toString()).list();
		cerrarConexion();
		return rta;
	}*/
	 
	@Override
	public ArrayList<ComisionVenta> getComisionVenta()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVenta> seleccion = session.createQuery("from TpFinal.domain.ComisionVenta item").list();
		cerrarConexion();
		return (ArrayList<ComisionVenta>)seleccion;
	}
	
	@Override
	public ArrayList<ComisionProducto> getComisionProducto()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProducto> seleccion = session.createQuery("from TpFinal.domain.ComisionProducto item").list();
		cerrarConexion();
		return (ArrayList<ComisionProducto>)seleccion;
	}
	
	@Override
	public void actualizarComisionVenta(ComisionVenta comisionVenta)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(comisionVenta);
		tx.commit();
		cerrarConexion();
	}
	
	@Override
	public void actualizarComisionProducto(ComisionProducto comisionProducto)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(comisionProducto);
		tx.commit();
		cerrarConexion();
	}
	
	@Override
	public void guardarComisionVenta(ComisionVenta comisionVenta)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(comisionVenta);
		tx.commit();
		cerrarConexion();
	}
	
	@Override
	public void guardarComisionProducto(ComisionProducto comisionProducto)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(comisionProducto);
		tx.commit();
		cerrarConexion();
	}
	
//PREMIO
	@Override
	public Premio getPremio(Integer id)
	{
		Premio rta = new Premio();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
/*	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes(Date fecha)
	{
		abrirConexion();
	//PREMIO
	@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.fecha ="+fecha.toString() + "and item.isCampania=false").list();
		cerrarConexion();
		return (ArrayList<Premio>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<Premio> getPremioCampania(Date fecha)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.fecha ="+fecha.toString() + "and item.isCampania=true").list();
		cerrarConexion();
		return (ArrayList<Premio>) seleccion;
	}*/
	
	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.isCampania=false").list();
		cerrarConexion();
		return (ArrayList<Premio>) seleccion;
	}
	
	@Override
	public ArrayList<Premio> getPremioCampania()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.isCampania=false").list();
		cerrarConexion();
		return (ArrayList<Premio>) seleccion;
	}
	
	@Override
	public ArrayList<Premio> getPremio()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Premio> seleccion = session.createQuery("from TpFinal.domain.Premio item").list();
		cerrarConexion();
		return (ArrayList<Premio>) seleccion;
	}
	
	@Override
	public void actualizarPremio(Premio premio)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(premio);
		tx.commit();
		cerrarConexion();
	}
	
	@Override
	public void guardarPremio(Premio premio)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(premio);
		tx.commit();
		cerrarConexion();
	}
	
//PAYROLL
	@Override
	public Payroll getPayroll(Integer id)
	{
		Payroll rta = new Payroll();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Payroll> seleccion = session.createQuery("from TpFinal.domain.Payroll item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}
	
/*	@Override
	public ArrayList<Payroll> getPayroll(Date fecha)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Payroll> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.fecha ="+fecha.toString()).list();
		cerrarConexion();
		return (ArrayList<Payroll>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<Payroll> getPayroll(Integer idVendedor)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Payroll> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.vendedor.id ="+idVendedor.toString()).list();
		cerrarConexion();
		return (ArrayList<Payroll>) seleccion;
	}*/
	
/*	@Override
	public ArrayList<Payroll> getPayroll(Date fecha, Integer idVendedor)
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Payroll> seleccion = session.createQuery("from TpFinal.domain.Premio item where item.fecha ="+fecha.toString() + "and item.vendedor.id ="+idVendedor.toString()).list();
		cerrarConexion();
		return (ArrayList<Payroll>) seleccion;
	}*/
	
	@Override
	public ArrayList<Payroll> getPayroll()
	{
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Payroll> seleccion = session.createQuery("from TpFinal.domain.Premio item").list();
		cerrarConexion();
		return (ArrayList<Payroll>) seleccion;
	}
	
	@Override
	public void guardarPayroll(Payroll payroll)
	{
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(payroll);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(usuario);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void guardarRol(RolUsuario rol) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(rol);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public ComisionProductoMonto getMontoProducto(Integer id) {
		ComisionProductoMonto rta = new ComisionProductoMonto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProductoMonto> seleccion = session.createQuery("from TpFinal.domain.adicional.monto.ComisionProductoMonto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

	@Override
	public ArrayList<ComisionProductoMonto> getMontosProducto() {
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionProductoMonto> seleccion = session.createQuery("from TpFinal.domain.adicional.monto.ComisionProductoMonto item").list();
		cerrarConexion();
		return (ArrayList<ComisionProductoMonto>) seleccion;
	}

	@Override
	public void guardarProductoMonto(ComisionProductoMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public ComisionVentaMonto getMontoVenta(Integer id) {
		ComisionVentaMonto rta = new ComisionVentaMonto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVentaMonto> seleccion = session.createQuery("from TpFinal.domain.adicional.monto.ComisionVentaMonto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

	@Override
	public ArrayList<ComisionVentaMonto> getMontosVenta() {
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<ComisionVentaMonto> seleccion = session.createQuery("from TpFinal.domain.adicional.monto.ComisionVentaMonto item").list();
		cerrarConexion();
		return (ArrayList<ComisionVentaMonto>) seleccion;
	}

	@Override
	public void guardarVentaMonto(ComisionVentaMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public PremioMonto getMontoPremio(Integer id) {
		PremioMonto rta = new PremioMonto();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<PremioMonto> seleccion = session.createQuery("from TpFinal.adicional.monto.PremioMonto item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

	@Override
	public ArrayList<PremioMonto> getMontosPremio() {
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<PremioMonto> seleccion = session.createQuery("from TpFinal.domain.adicional.monto.PremioMonto item").list();
		cerrarConexion();
		return (ArrayList<PremioMonto>) seleccion;
	}

	@Override
	public void guardarPremioMonto(PremioMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarProductoMonto(ComisionProductoMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarVentaMonto(ComisionVentaMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarPremioMonto(PremioMonto item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarPayroll(Payroll payroll) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(payroll);
		tx.commit();
		cerrarConexion();
	}

//Campania
	@Override
	public Campania getCampania(Integer id) {
		Campania rta = new Campania();
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Campania> seleccion = session.createQuery("from TpFinal.adicional.Campania item where item.id ="+id.toString()).list();
		if(seleccion.size()>0)
			rta = seleccion.get(0);
		
		cerrarConexion();
		return rta;
	}

	@Override
	public ArrayList<Campania> getCampania() {
		abrirConexion();
		@SuppressWarnings("unchecked")
		List<Campania> seleccion = session.createQuery("from TpFinal.domain.adicional.Campania item").list();
		cerrarConexion();
		return (ArrayList<Campania>) seleccion;
	}

	@Override
	public void actualizarCampania(Campania item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void guardarCampania(Campania item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		cerrarConexion();
	}

	@Override
	public void actualizarAdicional(Adicional item) {
		abrirConexion();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		cerrarConexion();
	}

}
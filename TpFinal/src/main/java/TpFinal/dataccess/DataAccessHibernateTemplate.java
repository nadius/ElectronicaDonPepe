package TpFinal.dataccess;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import TpFinal.domain.*;
import TpFinal.domain.adicional.monto.*;
import TpFinal.security.*;

public class DataAccessHibernateTemplate implements DataAccess {

	private HibernateTemplate hibernateTemplate = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

//USUARIOS
	@Override
	public Usuario getUsuario(Integer id)
	{
		return this.hibernateTemplate.get(Usuario.class, id);
	}
	
	@Override
	public ArrayList<Usuario> getUsuarios()	
	{
		return (ArrayList<Usuario>) this.hibernateTemplate.loadAll(Usuario.class);
	}
	
	@Override
	public void guardarUsuario(Usuario usuario) {
		this.hibernateTemplate.merge(usuario);
	}
	
	@Override
	public void actualizarUsuario(Usuario usuario) {
		this.hibernateTemplate.update(usuario);
	}

//ROL DE USUARIOS
	@Override
	public RolUsuario getRol(Integer id)
	{
		return this.hibernateTemplate.get(RolUsuario.class, id);
	}
	
	@Override
	public ArrayList<RolUsuario> getRoles()
	{
		return (ArrayList<RolUsuario>) this.hibernateTemplate.loadAll(RolUsuario.class);
	}

	@Override
	public void guardarRol(RolUsuario rol) {
		this.hibernateTemplate.merge(rol);
	}

//VENDEDOR
	@Override
	public Vendedor getVendedor(Integer id) {
		return this.hibernateTemplate.get(Vendedor.class, id);
	}

	@Override
	public ArrayList<Vendedor> getVendedores() {
		return (ArrayList<Vendedor>) this.hibernateTemplate.loadAll(Vendedor.class);
	}
	
	@Override
	public void guardarVendedor(Vendedor vendedor) {
		this.hibernateTemplate.merge(vendedor);
	}

	@Override
	public void actualizarVendedor(Vendedor vendedor) {
		this.hibernateTemplate.update(vendedor);
	}

//PRODUCTO
	@Override
	public Producto getProducto(Integer id) {
		return this.hibernateTemplate.get(Producto.class, id);
	}

	@Override
	public ArrayList<Producto> getProductos() {
		return (ArrayList<Producto>) this.hibernateTemplate.loadAll(Producto.class);
	}
	
	@Override
	public void guardarProducto(Producto producto) {
		this.hibernateTemplate.merge(producto);
	}

//VENTA
	@Override
	public Venta getVenta(Integer idVenta) {
		return this.hibernateTemplate.get(Venta.class, idVenta);
	}

/*	@Override
	public ArrayList<Venta> getVentas(Integer idVendedor) {
		ArrayList<Venta> todos = getVentas();
		ArrayList<Venta> seleccion = new ArrayList<Venta>();
		
		for (Venta item : todos) {
			if (item.getVendedor().getId() == idVendedor)
				seleccion.add(item);
		}
		return seleccion;
	}*/

	@Override
	public ArrayList<Venta> getVentas() {
		return (ArrayList<Venta>) this.hibernateTemplate.loadAll(Venta.class);
	}

	@Override
	public void guardarVenta(Venta venta) {
		this.hibernateTemplate.merge(venta);
	}

//ADICIONAL
	@Override
	public Adicional getAdicional(Integer idAdicional)
	{
		return this.hibernateTemplate.get(Adicional.class, idAdicional);
	}
	
/*	@Override
	public ArrayList<Adicional> getAdicional(Date fecha) {
		ArrayList<Adicional> todos = getAdicionales();
		ArrayList<Adicional> seleccion = new ArrayList<Adicional>();
		
		for (Adicional item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/

/*	@Override
	public ArrayList<Adicional> getAdicional(Integer idVendedor) {
		ArrayList<Adicional> todos = getAdicionales();
		ArrayList<Adicional> seleccion = new ArrayList<Adicional>();
		
		for (Adicional item : todos) {
			if (item.getVendedor().getId() == idVendedor)
				seleccion.add(item);
		}
		return seleccion;
	}*/

/*	@Override
	public Adicional getAdicional(Integer idVendedor, Date fecha) {
		ArrayList<Adicional> todos = getAdicionales();
		
		for (Adicional item : todos) {
			if ((item.getFecha() == fecha) && (item.getVendedor().getId()==idVendedor))
				return item;
		}
		return null;
	}*/

	@Override
	public ArrayList<Adicional> getAdicionales()
	{
		return (ArrayList<Adicional>) this.hibernateTemplate.loadAll(Adicional.class);
	}

	@Override
	public void guardarAdicional(Adicional adicional) {
		this.hibernateTemplate.merge(adicional);
	}

//COMISION
	@Override
	public ComisionVenta getComisionVenta(Integer id)
	{
		return this.hibernateTemplate.get(ComisionVenta.class, id);
	}
	
	@Override
	public ComisionProducto getComisionProducto(Integer id)
	{
		return this.hibernateTemplate.get(ComisionProducto.class, id);
	}
	
/*	@Override
	public ArrayList<ComisionVenta> getComisionVenta(Date fecha)
	{
		ArrayList<ComisionVenta> todos = getComisionVenta();
		ArrayList<ComisionVenta> seleccion = new ArrayList<ComisionVenta>();
		
		for (ComisionVenta item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
/*	@Override
	public ArrayList<ComisionProducto> getComisionProducto(Date fecha)
	{
		ArrayList<ComisionProducto> todos = getComisionProducto();
		ArrayList<ComisionProducto> seleccion = new ArrayList<ComisionProducto>();
		
		for (ComisionProducto item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/
		
/*	@Override
	public ArrayList<ComisionVenta> getComisionVenta(Vendedor vendedor)
	{
		ArrayList<ComisionVenta> todos = getComisionVenta();
		ArrayList<ComisionVenta> seleccion = new ArrayList<ComisionVenta>();
		
		for (ComisionVenta item : todos) {
			if (item.getVendedor().getId() == vendedor.getId())
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
/*	@Override
	public ArrayList<ComisionProducto> getComisionProducto(Vendedor vendedor)
	{
		ArrayList<ComisionProducto> todos = getComisionProducto();
		ArrayList<ComisionProducto> seleccion = new ArrayList<ComisionProducto>();
		
		for (ComisionProducto item : todos) {
			if (item.getVendedor().getId() == vendedor.getId())
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
/*	@Override
	public ComisionVenta getComisionVenta(Date fecha, Vendedor vendedor) {
		ArrayList<ComisionVenta> todos = getComisionVenta();
		
		for (ComisionVenta item : todos) {
			if ((item.getFecha()==fecha) && (item.getVendedor().getId() == vendedor.getId()))
				return item;
		}
		return null;
	}*/

/*	@Override
	public ComisionProducto getComisionProducto(Date fecha, Vendedor vendedor) {
		ArrayList<ComisionProducto> todos = getComisionProducto();
		
		for (ComisionProducto item : todos) {
			if ((item.getFecha()==fecha) && (item.getVendedor().getId() == vendedor.getId()))
				return item;
		}
		return null;
	}*/	
	
	@Override
	public ArrayList<ComisionVenta> getComisionVenta()
	{
		return (ArrayList<ComisionVenta>) this.hibernateTemplate.loadAll(ComisionVenta.class);
	}
	
	@Override
	public ArrayList<ComisionProducto> getComisionProducto()
	{
		return (ArrayList<ComisionProducto>) this.hibernateTemplate.loadAll(ComisionProducto.class);
	}
	
	@Override
	public void actualizarComisionVenta(ComisionVenta comisionVenta) {
		this.hibernateTemplate.update(comisionVenta);
	}

	@Override
	public void actualizarComisionProducto(ComisionProducto comisionProducto) {
		this.hibernateTemplate.update(comisionProducto);
	}
	
	@Override
	public void guardarComisionVenta(ComisionVenta comisionVenta)
	{
		this.hibernateTemplate.merge(comisionVenta);
	}
	
	@Override
	public void guardarComisionProducto(ComisionProducto comisionProducto)
	{
		this.hibernateTemplate.merge(comisionProducto);
	}

//PREMIO
	@Override
	public Premio getPremio(Integer id)
	{
		return this.hibernateTemplate.get(Premio.class, id);
	}
	
/*	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes(Date fecha)
	{
		ArrayList<Premio> todos =getPremioMejorVendedorMes();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		
		for (Premio item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
/*	@Override
	public ArrayList<Premio> getPremioCampania(Date fecha)
	{
		ArrayList<Premio> todos =getPremioCampania();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		
		for (Premio item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes()
	{
		ArrayList<Premio> todos = getPremio();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		
		for (Premio item : todos) {
			if (item.isCampania()==false)
				seleccion.add(item);
		}
		return seleccion;
	}
	
	@Override
	public ArrayList<Premio> getPremioCampania()
	{
		ArrayList<Premio> todos = getPremio();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		
		for (Premio item : todos) {
			if (item.isCampania()==true)
				seleccion.add(item);
		}
		return seleccion;
	}
	
	@Override
	public ArrayList<Premio> getPremio()
	{
		return (ArrayList<Premio>) this.hibernateTemplate.loadAll(Premio.class);
	}
	
	@Override
	public void actualizarPremio(Premio premio)
	{
		this.hibernateTemplate.update(premio); 
	}
		
	@Override
	public void guardarPremio(Premio premio)
	{
		this.hibernateTemplate.merge(premio);
	}

//PAYROLL
	@Override
	public Payroll getPayroll(Integer id)
	{
		return this.hibernateTemplate.get(Payroll.class, id);
	}
	
/*	@Override
	public ArrayList<Payroll> getPayroll(Date fecha) {
		ArrayList<Payroll> todos = getPayroll();
		ArrayList<Payroll> seleccion = new ArrayList<Payroll>();
		
		for (Payroll item : todos) {
			if (item.getFecha() == fecha)
				seleccion.add(item);
		}
		return seleccion;
	}*/

/*	@Override
	public ArrayList<Payroll> getPayroll(Integer idVendedor) {
		ArrayList<Payroll> todos = getPayroll();
		ArrayList<Payroll> seleccion = new ArrayList<Payroll>();
		
		for (Payroll item : todos) {
			if (item.getVendedor().getId() == idVendedor)
				seleccion.add(item);
		}
		return seleccion;
	}*/

/*	@Override
	public ArrayList<Payroll> getPayroll(Date fecha, Integer idVendedor) {
		ArrayList<Payroll> todos = getPayroll();
		ArrayList<Payroll> seleccion = new ArrayList<Payroll>();
		
		for (Payroll item : todos) {
			if ((item.getFecha() == fecha) && (item.getVendedor().getId() == idVendedor))
				seleccion.add(item);
		}
		return seleccion;
	}*/
	
	@Override
	public ArrayList<Payroll> getPayroll()
	{
		return (ArrayList<Payroll>) this.hibernateTemplate.loadAll(Payroll.class);
	}
	
	@Override
	public void actualizarPayroll(Payroll payroll)
	{
		this.hibernateTemplate.update(payroll);
	}
	
	public void guardarPayroll(Payroll payroll) {
		this.hibernateTemplate.merge(payroll);
	}

//MONTOS COMISIONES
//Comision Producto
	@Override
	public ComisionProductoMonto getMontoProducto(Integer id) {
		return this.hibernateTemplate.get(ComisionProductoMonto.class, id);
	}

	@Override
	public ArrayList<ComisionProductoMonto> getMontosProducto() {
		return (ArrayList<ComisionProductoMonto>) this.hibernateTemplate.loadAll(ComisionProductoMonto.class);
	}

	@Override
	public void actualizarProductoMonto(ComisionProductoMonto item)
	{
		this.hibernateTemplate.update(item);
	}
	
	@Override
	public void guardarProductoMonto(ComisionProductoMonto item) {
		this.hibernateTemplate.merge(item);
	}

//Comision Venta
	@Override
	public ComisionVentaMonto getMontoVenta(Integer id) {
		return this.hibernateTemplate.get(ComisionVentaMonto.class, id);
	}

	@Override
	public ArrayList<ComisionVentaMonto> getMontosVenta() {
		return (ArrayList<ComisionVentaMonto>) this.hibernateTemplate.loadAll(ComisionVentaMonto.class);
	}

	@Override
	public void actualizarVentaMonto(ComisionVentaMonto item)
	{
		this.hibernateTemplate.update(item);
	}
	
	@Override
	public void guardarVentaMonto(ComisionVentaMonto item) {
		this.hibernateTemplate.merge(item);
	}

//Premio
	@Override
	public PremioMonto getMontoPremio(Integer id) {
		return this.hibernateTemplate.get(PremioMonto.class, id);
	}

	@Override
	public ArrayList<PremioMonto> getMontosPremio() {
		return (ArrayList<PremioMonto>) this.hibernateTemplate.loadAll(PremioMonto.class);
	}

	@Override
	public void actualizarPremioMonto(PremioMonto item)
	{
		this.hibernateTemplate.update(item);
	}
	
	@Override
	public void guardarPremioMonto(PremioMonto item) {
		this.hibernateTemplate.merge(item);
	}

//Campania
	@Override
	public Campania getCampania(Integer id) {
		return this.hibernateTemplate.get(Campania.class, id);
	}

	@Override
	public ArrayList<Campania> getCampania() {
		return (ArrayList<Campania>) this.hibernateTemplate.loadAll(Campania.class);
	}

	@Override
	public void actualizarCampania(Campania item) {
		this.hibernateTemplate.merge(item);
	}

	@Override
	public void guardarCampania(Campania item) {
		this.hibernateTemplate.merge(item);
	}
}

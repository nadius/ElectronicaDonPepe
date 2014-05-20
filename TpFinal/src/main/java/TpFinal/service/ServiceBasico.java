package TpFinal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import TpFinal.dataccess.DataAccess;
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
import TpFinal.service.Service;

public class ServiceBasico implements Service {

	private DataAccess dataAccess = null;

	public DataAccess getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}
	
//USUARIOS
	@Override
	public Usuario getUsuario(Integer id) {
		return dataAccess.getUsuario(id);
	}

	@Override
	public ArrayList<Usuario> getUsuarios() {
		return dataAccess.getUsuarios();
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		if (usuario.getId() == 0)
			dataAccess.guardarUsuario(usuario);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		dataAccess.actualizarUsuario(usuario);
	}
	
	@Override
	public boolean login(String username, String password)
	{
		ArrayList<Usuario> todos = dataAccess.getUsuarios();
		for (Usuario item : todos)
			if (password.equals(item.getPassword()) && username.equals(item.getUsername()) && item.isActivo()==true)
				return true;
		return false;
	}
	
	@Override
	public Usuario getUsuario(String username)
	{
		ArrayList<Usuario> todos = dataAccess.getUsuarios();
		for (Usuario item : todos)
			if (username.equals(item.getUsername()) && item.isActivo()==true)
				return item;
		return null;
	}
	
//ROL DE USUARIOS
	@Override
	public RolUsuario getRol(Integer id) {
		return dataAccess.getRol(id);
	}

	@Override
	public ArrayList<RolUsuario> getRoles() {
		return dataAccess.getRoles();
	}

	@Override
	public void guardarRol(RolUsuario rol) {
		if (rol.getId() == 0)
			dataAccess.guardarRol(rol);
	}

//VENDEDOR
	@Override
	public Vendedor getVendedor(Integer id) {
		return dataAccess.getVendedor(id);
	}

	@Override
	public ArrayList<Vendedor> getVendedores() {
		return dataAccess.getVendedores();
	}

	@Override
	public ArrayList<Vendedor> getVendedoresActivos()
	{
		ArrayList<Vendedor> todos=getVendedores();
		ArrayList<Vendedor> activos=new ArrayList<Vendedor>();
		for (Vendedor item : todos)
			if (item.isActivo()==true)
				activos.add(item);
		return activos;
	}
	
	@Override
	public void guardarVendedor(Vendedor vendedor) {
		if (vendedor.getId() == 0)
			dataAccess.guardarVendedor(vendedor);
	}

	@Override
	public void actualizarVendedor(Vendedor vendedor) {
		dataAccess.actualizarVendedor(vendedor);
	}
	
//PRODUCTO
	@Override
	public Producto getProducto(Integer id) {
		return dataAccess.getProducto(id);
	}

	@Override
	public ArrayList<Producto> getProductos() {
		return dataAccess.getProductos();
	}

	@Override
	public void guardarProducto(Producto producto) {
		if (producto.getId() == 0)
			dataAccess.guardarProducto(producto);
	}

//VENTA
	@Override
	public Venta getVenta(Integer idVenta) {
		return dataAccess.getVenta(idVenta);
	}

	@Override
	public ArrayList<Venta> getVentas(int idVendedor)
	{
		ArrayList<Venta> todos = dataAccess.getVentas();
		ArrayList<Venta> rta= new ArrayList<Venta>();
		for (Venta item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<Venta> findVentas(int idVendedor, Date desde, Date hasta)
	{
		System.out.println("Buscar desde " + desde.toString() + " hasta " + hasta.toString());
		ArrayList<Venta> todos=getVentas(idVendedor);
		ArrayList<Venta> seleccion=new ArrayList<Venta>();
		Date itemFecha;
		//GregorianCalendar calendario = new GregorianCalendar();
		
		for (Venta item : todos)
		{	
			//System.out.println(item.getId() + ": " + item.getFechaCreacion().toString());
			//calendario.setTime(item.getFecha());
			itemFecha= item.getFecha();
			System.out.println("\tFecha: " + item.getFecha().toString());
			if (itemFecha.compareTo(desde)>=0 && itemFecha.compareTo(hasta)<=0)
			{
				System.out.println("\t=)");
				seleccion.add(item);
			}
		}
		
		//System.out.println("Encontrados " + seleccion.size() + " de " + todos.size() + " registros");
		return seleccion;
	}
	
	@Override
	public ArrayList<Venta> findVentas(GregorianCalendar desde, GregorianCalendar hasta)
	{
		ArrayList<Venta> todos=getVentas();
		ArrayList<Venta> seleccion=new ArrayList<Venta>();
		GregorianCalendar calendario = new GregorianCalendar();
		
		for (Venta item : todos)
		{	
			//System.out.println(item.getId() + ": " + item.getFechaCreacion().toString());
			calendario.setTime(item.getFecha());
			if (calendario.after(desde) && calendario.before(hasta))
				seleccion.add(item);
		}
		
		//System.out.println("Encontrados " + seleccion.size() + " de " + todos.size() + " registros");
		return seleccion;
	}
	
	@Override
	public ArrayList<Venta> getVentas() {
		return dataAccess.getVentas();
	}
	
	@Override
	public boolean existenVentas(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		if (findVentas(vendedor.getId(), desde.getTime(), hasta.getTime()).isEmpty())
			return false;
		return true;
	}

	@Override
	public void guardarVenta(Venta venta) {
		if (venta.getId() == 0)
			dataAccess.guardarVenta(venta);
	}

//MONTO DE COMISIONES
	@Override
	public ComisionProductoMonto getMontoProducto(Integer id) {
		return dataAccess.getMontoProducto(id);
	}

	@Override
	public ArrayList<ComisionProductoMonto> getMontosProducto() {
		return dataAccess.getMontosProducto();
	}

	@Override
	public ComisionProductoMonto getMontoProducto(Producto producto)
	{
		ArrayList<ComisionProductoMonto> todos=getMontosProducto();
		for (ComisionProductoMonto item : todos)
			if (item.getProducto().getId()==producto.getId())
				return item;
		return null;
	}
	
	@Override
	public void guardarProductoMonto(ComisionProductoMonto item) {
		if (item.getId() == 0)
			dataAccess.guardarProductoMonto(item);
	}

	@Override
	public ComisionVentaMonto getMontoVenta(Integer id) {
		return dataAccess.getMontoVenta(id);
	}

	@Override
	public ArrayList<ComisionVentaMonto> getMontosVenta() {
		return dataAccess.getMontosVenta();
	}

	@Override
	public void actualizarProductoMonto(ComisionProductoMonto item)
	{
		dataAccess.actualizarProductoMonto(item);
	}

	@Override
	public void actualizarVentaMonto(ComisionVentaMonto item)
	{
		dataAccess.actualizarVentaMonto(item);
	}

	@Override
	public void guardarVentaMonto(ComisionVentaMonto item) {
		if (item.getId() == 0)
			dataAccess.guardarVentaMonto(item);
	}
	
//MONTO DE PREMIOS
	@Override
	public PremioMonto getMontoPremio(Integer id) {
		return dataAccess.getMontoPremio(id);
	}

	@Override
	public ArrayList<PremioMonto> getMontosPremio() {
		return dataAccess.getMontosPremio();
	}

	@Override
	public PremioMonto getMontoPremio(boolean campania)
	{
		ArrayList<PremioMonto> todos=getMontosPremio();
		for (PremioMonto item: todos)
			if (item.isCampania()==campania)
				return item;
		return null;
	}
	
	@Override
	public void actualizarPremioMonto(PremioMonto item)
	{
		dataAccess.actualizarPremioMonto(item);
	}
		
	@Override
	public void guardarPremioMonto(PremioMonto item) {
		if (item.getId() == 0)
			dataAccess.guardarPremioMonto(item);
	}

//ADICIONAL
	@Override
	public Adicional getAdicional(Integer idAdicional) {
		return dataAccess.getAdicional(idAdicional);
	}
	
	@Override
	public int getAdicional(Adicional registro)
	{
		ArrayList<Adicional> todos = dataAccess.getAdicionales();
		if (todos.isEmpty())
			return 0;
		
		for (Adicional item : todos)
			if(item.equals(registro))
				return item.getId();
		return 0;
	}

	@Override
	public ArrayList<Adicional> findAdicionales(Date fecha)
	{
		ArrayList<Adicional> todos = dataAccess.getAdicionales();
		ArrayList<Adicional> rta= new ArrayList<Adicional>();
		for (Adicional item : todos)
			if (item.getFechaCreacion()==fecha)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<Adicional> findAdicionales(int idVendedor)
	{
		ArrayList<Adicional> todos = dataAccess.getAdicionales();
		ArrayList<Adicional> rta= new ArrayList<Adicional>();
		for (Adicional item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}
	
	@Override
	public Adicional findAdicional(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		ArrayList<Adicional> todos = findAdicionales(idVendedor);
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		GregorianCalendar calendarioHasta = new GregorianCalendar();
		
		for (Adicional item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			calendarioHasta.setTime(item.getFechaHasta());
			if (calendarioDesde==desde && calendarioHasta==hasta)
				return item;
		}
		return null;
	}
	
	@Override
	public Adicional findAdicional(int idVendedor, Date fechaCreacion)
	{
		ArrayList<Adicional> todos = findAdicionales(idVendedor);
		for (Adicional item : todos)
			if (item.getFechaCreacion()==fechaCreacion)
				return item;
		return null;
	}
	
	@Override
	public ArrayList<Adicional> findAdicionales(GregorianCalendar desde, GregorianCalendar hasta)
	{
		ArrayList<Adicional> todos = getAdicionales();
		ArrayList<Adicional> seleccion = new ArrayList<Adicional>();
		GregorianCalendar calendarDesde = new GregorianCalendar();
		GregorianCalendar calendarHasta = new GregorianCalendar();
		
		for (Adicional adicional : todos)
		{
			calendarDesde.setTime(adicional.getFechaDesde());
			calendarHasta.setTime(adicional.getFechaHasta());
			
			if (calendarDesde==desde && calendarHasta==hasta)
				seleccion.add(adicional);
		}
		return seleccion;
	}
	
	@Override
	public ArrayList<Adicional> getAdicionales() {
		return dataAccess.getAdicionales();
	}

	public boolean isEmptyAdicional(Adicional adicional)
	{
		if ((adicional.getComisionVentas()==null) && 
			(adicional.getComisionesProducto().isEmpty()) &&
			(adicional.getMejorVendedorMes()==null) &&
			(adicional.getComisionesProducto().isEmpty()))
			return true;
		return false;
	}
	
	@Override
	public void guardarAdicional(Adicional adicional) {
		//guardarPremio(adicional.getMejorVendedorMes());
		if (adicional.getComisionVentas()!=null && adicional.getId()==0)//si o si tiene que haber comision por ventas para que se justifique guardar un adicional
			dataAccess.guardarAdicional(adicional);
	}

	
//COMISION
	@Override
	public ComisionVenta getComisionVenta(Integer id) {
		return dataAccess.getComisionVenta(id);
	}

	@Override
	public ComisionProducto getComisionProducto(Integer id) {
		return dataAccess.getComisionProducto(id);
	}
	
	@Override
	public int getComisionVenta(ComisionVenta registro)
	{
		ArrayList<ComisionVenta> todos = dataAccess.getComisionVenta();
		if (todos.isEmpty())
			return 0;
		
		for (ComisionVenta item : todos)
			if(item.equals(registro))
				return item.getId();
		return 0;
	}
	
	@Override
	public int getComisionProducto(ComisionProducto registro)
	{
		ArrayList<ComisionProducto> todos = dataAccess.getComisionProducto();
		
		if (todos.isEmpty())
			return 0;
		
		for (ComisionProducto item : todos)
			if(item.equals(registro))
				return item.getId();
		return 0;
	}

	@Override
	public ComisionVenta findComisionVenta(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		ArrayList<ComisionVenta> todos = findComisionesVenta(idVendedor);
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		GregorianCalendar calendarioHasta = new GregorianCalendar();
		
		for (ComisionVenta item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			calendarioHasta.setTime(item.getFechaHasta());
			if (calendarioDesde==desde && calendarioHasta==hasta)
				return item;
		}
		return null;
	}

	@Override
	public ComisionProducto findComisionProducto(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta, Producto producto)
	{
		ArrayList<ComisionProducto> todos = findComisionesProducto(idVendedor);
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		GregorianCalendar calendarioHasta = new GregorianCalendar();
		
		for (ComisionProducto item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			calendarioHasta.setTime(item.getFechaHasta());
			if (calendarioDesde==desde && calendarioHasta==hasta && item.getProducto().getId()==producto.getId())
				return item;
		}
		return null;
	}
	
	@Override
	public ArrayList<ComisionVenta> findComisionesVenta(Date fecha)
	{
		ArrayList<ComisionVenta> todos = dataAccess.getComisionVenta();
		ArrayList<ComisionVenta> rta= new ArrayList<ComisionVenta>();
		for (ComisionVenta item : todos)
			if (item.getFechaCreacion()==fecha)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<ComisionProducto> findComisionesProducto(Date fecha)
	{
		ArrayList<ComisionProducto> todos = dataAccess.getComisionProducto();
		ArrayList<ComisionProducto> rta= new ArrayList<ComisionProducto>();
		for (ComisionProducto item : todos)
			if (item.getFechaCreacion()==fecha)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<ComisionVenta> findComisionesVenta(int idVendedor)
	{
		ArrayList<ComisionVenta> todos = dataAccess.getComisionVenta();
		ArrayList<ComisionVenta> rta= new ArrayList<ComisionVenta>();
		for (ComisionVenta item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<ComisionProducto> findComisionesProducto(int idVendedor)
	{
		ArrayList<ComisionProducto> todos = dataAccess.getComisionProducto();
		ArrayList<ComisionProducto> rta= new ArrayList<ComisionProducto>();
		for (ComisionProducto item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ComisionVenta findComisionesVenta(Date fecha, int idVendedor)
	{
		ArrayList<ComisionVenta> todos = findComisionesVenta(fecha);
		for (ComisionVenta item : todos)
			if (item.getVendedor().getId()==idVendedor)
				return item;
		return null;
	}
	
	@Override
	public ComisionProducto findComisionesProducto(Date fecha, int idVendedor)
	{
		ArrayList<ComisionProducto> todos = findComisionesProducto(fecha);
		for (ComisionProducto item : todos)
			if (item.getVendedor().getId()==idVendedor)
				return item;
		return null;
	}
	
	@Override
	public ArrayList<ComisionVenta> getComisionVenta() {
		return dataAccess.getComisionVenta();
	}

	@Override
	public ArrayList<ComisionProducto> getComisionProducto() {
		return dataAccess.getComisionProducto();
	}
	
	@Override
	public void actualizarComisionVenta(ComisionVenta comisionVenta) {
		dataAccess.actualizarComisionVenta(comisionVenta);
	}

	@Override
	public void actualizarComisionProducto(ComisionProducto comisionProducto) {
		dataAccess.actualizarComisionProducto(comisionProducto);
	}

	@Override
	public void guardarComisionVenta(ComisionVenta comisionVenta) {
		if (comisionVenta!=null && comisionVenta.getId()==0)
			dataAccess.guardarComisionVenta(comisionVenta);
	}

	@Override
	public void guardarComisionProducto(ComisionProducto comisionProducto) {
		if (comisionProducto!=null && comisionProducto.getId()==0)
			dataAccess.guardarComisionProducto(comisionProducto);
	}

//PREMIO
	@Override
	public Premio getPremio(Integer id) {
		return dataAccess.getPremio(id);
	}
	
	@Override
	public int getPremio(Premio registro)
	{
		ArrayList<Premio> todos=dataAccess.getPremio();
		
		if (todos.isEmpty())
			return 0;
		
		for (Premio item : todos)
			if (item.equals(registro))
				return item.getId();
		return 0;
	}

	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes() {
		return dataAccess.getPremioMejorVendedorMes();
	}

	@Override
	public ArrayList<Premio> getPremioCampania() {
		return dataAccess.getPremioCampania();
	}

	@Override
	public ArrayList<Premio> getPremio() {
		return dataAccess.getPremio();
	}

	@Override
	public ArrayList<Premio> getPremiosMejorVendedorMes(Date fechaCreacion)
	{
		ArrayList<Premio> todos = dataAccess.getPremio();
		ArrayList<Premio> rta= new ArrayList<Premio>();
		for (Premio item : todos)
			if (item.getFechaCreacion()==fechaCreacion && item.isCampania()==false)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<Premio> getPremiosCampania(Date fechaCreacion)
	{
		ArrayList<Premio> todos = dataAccess.getPremio();
		ArrayList<Premio> rta= new ArrayList<Premio>();
		for (Premio item : todos)
			if (item.getFechaCreacion()==fechaCreacion && item.isCampania()==true)
				rta.add(item);
		return rta;
	}

	@Override
	public Premio findPremioMejorVendedorMes(Date fechaCreacion, Vendedor vendedor)
	{
		ArrayList<Premio> todos=getPremiosMejorVendedorMes(fechaCreacion);
		for (Premio item : todos)
			if (item.getPremiado().getId()==vendedor.getId())
				return item;
		return null;
	}
	
	@Override
	public ArrayList<Premio> getPremioCampania(int idVendedor)
	{
		ArrayList<Premio> todos=getPremioCampania();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		for (Premio item : todos)
			if (item.getPremiado().getId()==idVendedor)
				seleccion.add(item);
		return seleccion;
	}
	
	@Override
	public ArrayList<Premio> getPremioMejorVendedorMes(int idVendedor)
	{
		ArrayList<Premio> todos=getPremioMejorVendedorMes();
		ArrayList<Premio> seleccion = new ArrayList<Premio>();
		for (Premio item : todos)
			if (item.getPremiado().getId()== idVendedor)
				seleccion.add(item);
		return seleccion;
	}
	
	@Override
	public Premio findPremioCampania(Date desde, Date hasta, Vendedor vendedor)
	{
		ArrayList<Premio> todos=dataAccess.getPremioCampania();
		for (Premio item : todos)
			if (item.getFechaDesde()==desde && item.getFechaHasta()==hasta && item.getPremiado().getId()==vendedor.getId())
				return item;
		return null;
	}
	
	@Override
	public Premio findPremioMejorVendedorMes(Date desde, int idVendedor)
	{
		ArrayList<Premio> todos = getPremioMejorVendedorMes(idVendedor);
		
		for (Premio item : todos)
		{
			if (item.getFechaDesde()==desde && item.getPremiado().getId()==idVendedor)
				return item;
		}
		return null;
	}
	
	@Override
	public Premio findPremioCampania(int idVendedor, GregorianCalendar desde, GregorianCalendar hasta, Producto producto)
	{
		ArrayList<Premio> todos = getPremioCampania(idVendedor);
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		GregorianCalendar calendarioHasta = new GregorianCalendar();
		
		for (Premio item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			calendarioHasta.setTime(item.getFechaHasta());
			if (calendarioDesde==desde && calendarioHasta==hasta && item.getProducto().getId()==producto.getId())
				return item;
		}
		return null;
	}
	
	@Override
	public Premio findPremioMejorVendedorMes(GregorianCalendar desde)
	{
		ArrayList<Premio> todos = getPremioMejorVendedorMes();
		
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		
		for (Premio item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			if (calendarioDesde==desde)
				return item;
		}
		return null;
	}
	
	@Override
	public Premio findPremioCampania(GregorianCalendar desde, GregorianCalendar hasta, Producto producto)
	{
		ArrayList<Premio> todos = getPremioMejorVendedorMes();
		
		GregorianCalendar calendarioDesde = new GregorianCalendar();
		GregorianCalendar calendarioHasta = new GregorianCalendar();
		
		for (Premio item : todos)
		{
			calendarioDesde.setTime(item.getFechaDesde());
			calendarioHasta.setTime(item.getFechaHasta());
			if (calendarioDesde==desde && calendarioHasta==hasta && item.getProducto().getId()==producto.getId())
				return item;
		}
		return null;
	}
	
	@Override
	public void actualizarPremio(Premio premio) {
		dataAccess.actualizarPremio(premio);
	}

	@Override
	public void guardarPremio(Premio premio) {
		if (premio!=null && premio.getId()==0)
			dataAccess.guardarPremio(premio);
	}

	
//PAYROLL
	@Override
	public ArrayList<Payroll> getPayroll(Date fecha)
	{
		ArrayList<Payroll> todos = dataAccess.getPayroll();
		ArrayList<Payroll> rta= new ArrayList<Payroll>();
		for (Payroll item : todos)
			if (item.getFechaCreacion()==fecha)
				rta.add(item);
		return rta;
	}
	
	@Override
	public ArrayList<Payroll> getPayroll(int idVendedor)
	{
		ArrayList<Payroll> todos = dataAccess.getPayroll();
		ArrayList<Payroll> rta= new ArrayList<Payroll>();
		for (Payroll item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}

	@Override
	public ArrayList<Payroll> getPayroll(Date fecha, int idVendedor)
	{
		ArrayList<Payroll> todos = getPayroll(fecha);
		ArrayList<Payroll> rta= new ArrayList<Payroll>();
		for (Payroll item : todos)
			if (item.getVendedor().getId()==idVendedor)
				rta.add(item);
		return rta;
	}

	@Override
	public Payroll getPayroll(Integer id) {
		return getPayroll(id);
	}
	
	@Override
	public ArrayList<Payroll> getPayroll() {
		return dataAccess.getPayroll();
	}
	
	@Override
	public void actualizarPayroll(Payroll payroll)
	{
		dataAccess.actualizarPayroll(payroll);
	}

	@Override
	public void guardarPayroll(Payroll payroll) {
		if (payroll.getId() == 0)
			dataAccess.guardarPayroll(payroll);
	}

	@Override
	public Campania getCampania(Integer id) {
		return dataAccess.getCampania(id);
	}

	@Override
	public ArrayList<Campania> getCampanias() {
		return dataAccess.getCampania();
	}

	@Override
	public ArrayList<Campania> getCampaniasActivas() {
		ArrayList<Campania> todos = getCampanias();
		ArrayList<Campania> rta= new ArrayList<Campania>();
		for (Campania item : todos)
			if (item.isActivo()==true)
				rta.add(item);
		return rta;
	}

	@Override
	public ArrayList<Campania> getCampaniasNoActivas() {
		ArrayList<Campania> todos = getCampanias();
		ArrayList<Campania> rta= new ArrayList<Campania>();
		for (Campania item : todos)
			if (item.isActivo()==false)
				rta.add(item);
		return rta;
	}

	@Override
	public void guardarCampania(Campania item) {
		if (item.getId() == 0)
			dataAccess.guardarCampania(item);
	}

	@Override
	public void actualizarCampania(Campania item) {
		dataAccess.actualizarCampania(item);
	}

	@Override
	public Campania getCampania(Producto producto) {
		ArrayList<Campania> todos = getCampanias();
		Campania rta= new Campania();
		for (Campania item : todos)
			if (item.getProducto().getId()==producto.getId())
				return rta;
		return null;
	}
}

package TpFinal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import TpFinal.domain.*;
import TpFinal.domain.adicional.monto.*;
import TpFinal.security.Usuario;
import TpFinal.service.Service;

public class Adicionales extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private DataAccess dataAccess;
	protected Service service;
	
	private Usuario usuario;
	private Date fechaHoy;
	protected ArrayList<Vendedor> vendedores=null;
	//protected ArrayList<Venta> ventas=null;
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	
	public Adicionales()
	{
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public Adicional calcularAdicional(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		//System.out.println(vendedor.getNombre() + " " + vendedor.getApellido());
		Adicional adicional;
		ArrayList<ComisionProducto> cProductos=calcularComisionProducto(vendedor, desde, hasta);
		ComisionVenta cVenta=calcularComisionVenta(vendedor, desde, hasta);
		
/*		//Por si ya guardé los premios en la base
		Premio vendedorPremio=isPremiadoVendedor(vendedor, desde);
		Premio campania= isPremiadoCampania(vendedor,desde,hasta, producto);
		*/
		
		//Guardo todo (A ver si resuelve el bug de AdicionalesGenerar:157
		service.guardarComisionVenta(cVenta);
		if (cProductos!=null)
			for (ComisionProducto cProducto : cProductos)
				service.guardarComisionProducto(cProducto);
		
		adicional=new Adicional(fechaHoy, desde.getTime(), hasta.getTime(), vendedor, cVenta, cProductos, null, null);
		
		return adicional;
	}
	
	public ArrayList<ComisionProducto> calcularComisionProducto(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		//System.out.println("\tComision Producto: ");
		ArrayList<ComisionProducto> comisiones= new ArrayList<ComisionProducto>();
		ArrayList<Venta>ventas = service.findVentas(vendedor.getId(), desde.getTime(), hasta.getTime());
		ArrayList<ComisionProductoMonto> montos=service.getMontosProducto();
		ComisionProducto registro;
		
		int unidades=0;
		
		if (ventas.isEmpty())
		{
			//System.out.println("\t\t No se encontraron ventas para el período buscado");
			return null;
		}
		
		for (Venta venta : ventas)
		{
			for(ComisionProductoMonto item: montos)
			{
				//if (venta.getProductos().contains(item.getProducto()))//si el producto con adicional está en la lista de productos comprados
				if (buscarProductoVenta(venta.getProductos(), item.getProducto()))
				{
					//System.out.print("\t\t venta: " + venta.getId() + " - "+ item.getProducto().getNombre()+ " encontrado en ");
					unidades=contarProductoVenta(venta, item.getProducto());
					registro = new ComisionProducto(fechaHoy, desde.getTime(), hasta.getTime(), vendedor, unidades, item.getMonto()*unidades, item.getProducto());
					
					if (service.getComisionProducto(registro)!=0)//si ya existe un registro calculado con estos parámetros, así no insertamos registros repetidos //TODO: testear!!
						registro.setId(service.getComisionProducto(registro));
					
					comisiones.add(registro);
					//System.out.print("\n");
				}
			}
		}
		//if (!comisiones.isEmpty())
			//System.out.println("\t" + "Calculados " + comisiones.size() + " registros.");
		return comisiones;
	}
	
	public ComisionVenta calcularComisionVenta(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta)
	{
		ComisionVenta comision= new ComisionVenta();
		ArrayList<Venta>ventas = service.findVentas(vendedor.getId(), desde.getTime(), hasta.getTime());
		ArrayList<ComisionVentaMonto> montos=service.getMontosVenta();
		
		if (ventas.isEmpty())
		{
			System.out.println("No se encontraron ventas para el período buscado");
			return null;
		}
		
		comision.setFechaCreacion(fechaHoy);
		comision.setFechaDesde(desde.getTime());
		comision.setFechaHasta(hasta.getTime());
		comision.setVendedor(vendedor);
		comision.setVentas(ventas);
		comision.setUnidades(ventas.size());
			
		for (ComisionVentaMonto item : montos)
		{
			if (item.getMax()!=0)//cero es el valor del máximo del último monto (ie, sin tope)
			{
				if (ventas.size()<=item.getMax() && ventas.size()>=item.getMin())
					comision.setImporte(item.getMonto());
			}
			else
			{
				if (ventas.size()>=item.getMin())
					comision.setImporte(item.getMonto());
			}
		}
		
		if (service.getComisionVenta(comision)!=0)
			comision.setId(service.getComisionVenta(comision));
		
		//System.out.println("\trealizadas " + comision.getUnidades() + " ventas.");
			
		return comision;
	}
	
	public Premio isPremiadoVendedor(Vendedor vendedor, GregorianCalendar desde)
	{
		return service.findPremioMejorVendedorMes(desde.getTime(), vendedor.getId());
	}
	
	public Premio isPremiadoCampania(Vendedor vendedor, GregorianCalendar desde, GregorianCalendar hasta, Producto producto)
	{
		return service.findPremioCampania(vendedor.getId(), desde, hasta, producto);
	}
	
	public Premio calcularPremioVendedor(GregorianCalendar desde)
	{
		int cantidad=0;
		Premio premio;
		ArrayList<Venta>ventas = new ArrayList<Venta>();
		Vendedor premiado=new Vendedor();
		PremioMonto monto=service.getMontoPremio(false);
		GregorianCalendar hasta=(GregorianCalendar) desde.clone();
		hasta.add(2, 1);
		
		//System.out.println("Premio mejor vendedor del mes: (" + df.format(desde.getTime()) + " - " + df.format(hasta.getTime()) + ")");
	
		for (Vendedor vendedor : vendedores)
		{
			ventas=service.findVentas(vendedor.getId(), desde.getTime(), hasta.getTime());//FIXIT: no recupera ninguna venta!
			
			if (!ventas.isEmpty())
			{
				if (ventas.size()>cantidad)
				{
					cantidad=ventas.size();
					premiado=vendedor;
				}
			}/*
			else //Verificacion
			{
				System.out.println("\t\t" No se encontraron ventas para el período buscado");
			}*/
		//System.out.print("\n");
		}
		
		premio=new Premio(fechaHoy, desde.getTime(), hasta.getTime(), premiado, false, null, monto.getMonto());
		
		if (cantidad==0)
			return null;
		if (service.getPremio(premio)!=0)
			premio.setId(service.getPremio(premio));
		
		System.out.println("Premio mejor vendedor del mes: (" + df.format(desde.getTime()) + " - " + df.format(hasta.getTime()) + ")\t" + premiado.getNombre() + " " + premiado.getApellido() + "(" + cantidad + ")");
		
		return premio;
	}
	
	public Premio calcularPremioCampania(GregorianCalendar desde, GregorianCalendar hasta, Producto producto)
	{
		int cantidad=0, cuenta=0;
		ArrayList<Venta>ventas = new ArrayList<Venta>();
		Vendedor premiado= new Vendedor();
		PremioMonto monto=service.getMontoPremio(true);
		Premio premio;
		
		//System.out.println("Premio mejor vendedor campania: (" + producto.getNombre() + " " + df.format(desde.getTime()) + " - " + df.format(hasta.getTime()) + ")");
		
		for (Vendedor vendedor : vendedores)
		{
			//System.out.println("\t " + vendedor.getNombre() + " " + vendedor.getApellido() + ":");
			ventas=service.findVentas(vendedor.getId(), desde.getTime(), hasta.getTime());
			if (!ventas.isEmpty())
			{
				for(Venta venta : ventas)
				{
					//System.out.print("\t\t venta: " + venta.getId() + " - ");
					cuenta=contarProductoVenta(venta, producto);
					
					if (cuenta>cantidad)
					{
						cantidad=cuenta;
						premiado=vendedor;
					}
					//System.out.print("\n");
				}
			}/*
			else //Verificacion
			{
				System.out.println("\t\t No se encontraron ventas para el período buscado");
			}*/
			////System.out.print("\n");
		}
		
		if (cantidad==0)
			return null;
		
		premio = new Premio(fechaHoy, desde.getTime(), hasta.getTime(), premiado, true, producto, monto.getMonto());
		if (service.getPremio(premio)!=0)
			premio.setId(service.getPremio(premio));
		//System.out.println("Premiado: "+ premiado.getNombre() + " " + premiado.getApellido() + "(" + cantidad + ")");
		
		return premio;
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		//this.dataAccess = (DataAccess) ctx.getBean("dataAccessBean");
		this.service = (Service) ctx.getBean("serviceBean");
	}
	
	public boolean verificarRol(Usuario cuenta)
	{
		if (cuenta.getRol().getId()!=1)
			return false;
		return true;
	}
	
	public int contarProductoVenta(Venta venta, Producto producto)
	{
		int i=0;
		List<Producto> productosVenta=venta.getProductos();
		for (Producto item : productosVenta)
			if (item.getId()==producto.getId())
				i++;
		
		//System.out.print(i + " veces");
		return i;
	}
	
	public boolean buscarProductoVenta(List<Producto> productosVenta, Producto producto)
	{
		for (Producto item : productosVenta)
			if (item.getId()==producto.getId())
				return true;
		return false;
	}
	
	public GregorianCalendar getParamFecha(HttpServletRequest request, String tipo)
	{
		Integer dia, mes, anio;
		dia = Integer.parseInt(request.getParameter(tipo+"Dia"));
		mes = Integer.parseInt(request.getParameter(tipo+"Mes"));
		anio = Integer.parseInt(request.getParameter(tipo+"Anio"));
		
		return new GregorianCalendar(anio,mes-1,dia,0,0,0);
	}

	public void actualizarRegistroPremio(float[] valores) {
		PremioMonto registro=new PremioMonto();
		ArrayList<Premio> premios=service.getPremio();
/*		//Verificar
		System.out.print("Premios \t Actualizar: ");
		for (float item : valores)
			System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++)
		{
			registro=service.getMontoPremio(i+1);
						
			for (Premio premio : premios)//actualizo todos los importes de los premios
			{
				if(premio.getImporte()==registro.getMonto())
				{
					premio.setImporte(valores[i]);
					service.actualizarPremio(premio);
				}
			}
			
			registro.setMonto(valores[i]);
			service.actualizarPremioMonto(registro); 
		}
	}

	public void actualizarRegistroComisionProducto(float[] valores) {
		ComisionProductoMonto registro=new ComisionProductoMonto();
		ArrayList<ComisionProducto> comisiones=service.getComisionProducto();

/*		//Verificar
		System.out.print("Comision Producto \t Actualizar: ");
		for (float item : valores)
			System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++)
		{
			registro=service.getMontoProducto(i+1);
						
			for (ComisionProducto comision : comisiones)//actualizo todos los importes de los premios
			{
				if((comision.getImporte()/comision.getUnidades())==registro.getMonto())
				{
					comision.setImporte(valores[i]*comision.getUnidades());
					service.actualizarComisionProducto(comision);
				}
			}
			
			registro.setMonto(valores[i]);
			service.actualizarProductoMonto(registro); 
		}
	}

	public void actualizarRegistroComisionVenta(float[] valores) {
		ComisionVentaMonto registro=new ComisionVentaMonto();
		ArrayList<ComisionVenta> comisiones=service.getComisionVenta();

/*		//Verificar
		System.out.print("Comision Producto \t Actualizar: ");
		for (float item : valores)
			System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++)
		{
			registro=service.getMontoVenta(i+1);
						
			for (ComisionVenta comision : comisiones)//actualizo todos los importes de los premios
			{
				if(comision.getImporte()==registro.getMonto())
				{
					comision.setImporte(valores[i]);
					service.actualizarComisionVenta(comision);
				}
			}
			
			registro.setMonto(valores[i]);
			service.actualizarVentaMonto(registro); 
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaHoy() {
		return fechaHoy;
	}

	public void setFechaHoy(Date fechaHoy) {
		this.fechaHoy = fechaHoy;
	}

	public ArrayList<Vendedor> getVendedores() {
		return vendedores;
	}

	public void setVendedores(ArrayList<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}
}

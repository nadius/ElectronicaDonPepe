package TpFinal.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import TpFinal.security.Usuario;
import TpFinal.service.Service;
import TpFinal.dataccess.DataAccess;
import TpFinal.domain.Producto;
import TpFinal.domain.Vendedor;
import TpFinal.domain.Venta;

//@WebServlet("/venta/alta")
public class VentaNueva extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataAccess dataAccess;
	public Service service;
	
	private ArrayList<Producto> listaTodos = null;
	private ArrayList<Producto> listaComprados=null;
	private float total=0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//verificacion de usuario
		Usuario usuario=(Usuario) request.getSession().getAttribute("usuario");
		listaComprados=null;//lista de compra
		total=0;//subtotal de compra
		
		if (usuario==null)
			response.sendRedirect(request.getContextPath() + "/login");
		else
		{
			if (verificarRol(usuario))
			{
				listaTodos=dataAccess.getProductos();
				request.setAttribute("productos", listaTodos);
				request.setAttribute("total", total);//para que se muestre que el subtotal está en cero
				request.getRequestDispatcher("/WEB-INF/RegistrarVenta.jsp").forward(request, response);
			}
			else
				response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario cuenta= (Usuario) request.getSession().getAttribute("usuario");
		Vendedor vendedor = cuenta.getVendedor();
		listaTodos= dataAccess.getProductos();
		String error="";
		String id=request.getParameter("id");//el id es obligatorio 
		
		//setListaNombreProducto();
		
		String accion = request.getParameter("accion");
		
		if (accion.equals("agregar"))
			Agregar(request.getParameter("id"));
		if (accion.equals("guardar"))
		{//TODO: hacer una test que recalcule el importe total de las ventas, por las dudas.
			/*if (id.equals(""))
				error=Guardar(vendedor);
			else
				error=Guardar(vendedor, id);*/
			
			//if (!id.equals(""))//el campo id no debe ser vacio porque es obligatorio
				error=Guardar(vendedor, id);
				
			if (error.equals(""))//todo salio bien
				request.setAttribute("ok", "La venta se guardo correctamente.");
			else
				request.setAttribute("error", error );
		}
		
		request.setAttribute("productos", listaTodos);
		request.setAttribute("listaComprados", listaComprados);
		request.setAttribute("total", total);
		
		request.getRequestDispatcher("/WEB-INF/RegistrarVenta.jsp").forward(request, response);
	}	
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.dataAccess = (DataAccess) ctx.getBean("dataAccessBean");
		this.service = (Service) ctx.getBean("serviceBean");
	}

/*	public void setListaNombreProducto()
	{
		if (listaTodos==null)
			listaTodos= dataAccess.getProductos();
		for (Producto item : listaTodos)
			listaNombreProductos.add(item.getNombre());
	}
*/
	
	public void Agregar(String idProducto)
	{
		Integer index = Integer.parseInt(idProducto);
		if (listaComprados==null)
			listaComprados=new ArrayList<Producto>();
		listaComprados.add(listaTodos.get(index-1));
		
		total+=listaTodos.get(index-1).getPrecioUnitario();
	}
	
	public String Guardar(Vendedor vendedor)
	{
		Venta nuevaVenta=new Venta();
		nuevaVenta.setFecha(new Date());
		nuevaVenta.setProductos(listaComprados);
		nuevaVenta.setImporte(total);
		nuevaVenta.setVendedor(vendedor);
		
		String mensaje=verificarDatos(nuevaVenta);//si todo salio bien
		if (mensaje.equals(""))
			dataAccess.guardarVenta(nuevaVenta);
		
		return mensaje;
	}
	
	public String Guardar(Vendedor vendedor, String id)
	{
		if (id.equals(""))
			return "Por favor ingrese un id";
		
		Venta nuevaVenta=new Venta();
		Venta busquedaVenta=dataAccess.getVenta(Integer.parseInt(id));
		
		if(busquedaVenta!=null)//si existe una venta con ese id
			return "El id " + busquedaVenta.getId() + " corresponde a la venta realizada el " 
					+ busquedaVenta.getFecha().toString() + " por "
					+ busquedaVenta.getVendedor().getNombre() + busquedaVenta.getVendedor().getApellido()
					+ ". Por favor elija un nuevo id o deje el campo en blanco";
		
		nuevaVenta.setFecha(new Date());
		nuevaVenta.setProductos(listaComprados);
		nuevaVenta.setImporte(total);
		nuevaVenta.setVendedor(vendedor);
		
		String mensaje=verificarDatos(nuevaVenta);//si todo salio bien
		if (mensaje.equals(""))
		{
			dataAccess.guardarVenta(nuevaVenta);
			listaComprados=null;//reseteo la lista de comprados
			total=0;//y el subtotal
		}
		
		return mensaje;
	}
	
	public float calcularImporte()
	{
		float importe=0;
		for (Producto item : listaComprados)
			importe+=item.getPrecioUnitario();
		return importe;
	}
	
	public String verificarDatos(Venta venta)
	{
		String mensaje="";
		//if (venta.getId())
		//Venta verificacion=dataAccess.getVenta(id);
		if (venta.getFecha()==null)
			mensaje.concat(" fecha vacia");
		if (venta.getProductos()==null)
			mensaje.concat(" lista de productos vacia");
		if (venta.getImporte()==0)
			mensaje.concat(" importe vacio");
		if(venta.getVendedor()==null)
			mensaje.concat(" vendedor vacio");
		return mensaje;
	}
	
	public boolean verificarRol(Usuario cuenta)
	{
		if (cuenta.getRol().getId()!=2)
			return false;
		return true;
	}
}

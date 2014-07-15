package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.VentaService;

//@WebServlet("/venta/alta")
public class ServletVentaNueva extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private int rolPagina=2;
	private VentaService service;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);

		service.resetCarrito();
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setDefaultAttributes();
			redirectPagina("RegistrarVenta");
		}
		else{
			redirectPaginaError();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		String error="";		
		String accion = getParameter("accion");
		
		if (accion.equals("agregar"))
			service.Agregar(parseStringToInt(getParameter("idProducto")));
		if (accion.equals("guardar"))
		{
			try {
				error=service.calcular(usuario.getVendedor(), parseStringToInt(getParameter("id")));
			} catch (NullPointerException | NumberFormatException e) {//por si no se puede recuperar el id o parsear a int
				e.printStackTrace();
				error="Revisar " + e.getMessage();
			} finally {	
				if (error.equals(""))//todo salio bien
					setAttribute("ok", "La venta se guardo correctamente.");
				else
					setAttribute("error", error );
			}
		}
		
		setDefaultAttributes();		
		redirectPagina("RegistrarVenta");
	}	
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.service = (VentaService) ctx.getBean("VentaService");
		super.init(config);
	}
	
	public void setService(VentaService service) {
		this.service = service;
	}

	public void setDefaultAttributes(){
		setAttribute("id", service.getAll().size()+1);//sugiere un posible id del registro
		setAttribute("productos", service.getListaTodosProductos());
		setAttribute("listaComprados", service.getListaComprados());
		setAttribute("total", service.getTotal());
	}
}

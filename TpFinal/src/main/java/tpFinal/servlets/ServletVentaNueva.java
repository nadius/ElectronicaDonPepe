package tpFinal.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.calculation.calculationImpl.VentaCalculation;

//@WebServlet("/venta/alta")
public class ServletVentaNueva extends Servlet {
	private static final long serialVersionUID = 1L;
	private int rolPagina=2;
	private VentaCalculation calculation;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);

		calculation.resetCarrito();
		
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
			calculation.Agregar(parseString(getParameter("idProducto")));
		if (accion.equals("guardar"))
		{
			error=calculation.calcular(usuario.getVendedor(), getParameter("id"));
				
			if (error.equals(""))//todo salio bien
				setAttribute("ok", "La venta se guardo correctamente.");
			else
				setAttribute("error", error );
		}
		
		setDefaultAttributes();		
		redirectPagina("RegistrarVenta");
	}	
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.calculation = (VentaCalculation) ctx.getBean("VentaCalculation");
	}
	
	public void setDefaultAttributes(){
		setAttribute("productos", calculation.getListaTodosProductos());
		setAttribute("listaComprados", calculation.getListaComprados());
		setAttribute("total", calculation.getTotal());
	}
}

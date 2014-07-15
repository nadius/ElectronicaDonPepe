package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.servlets.ServletUtils;
import tpFinal.service.VentaService;

//@WebServlet("/venta/consulta/detalle")
public class ServletVentaDetalle extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private int rolPagina=2;
	private VentaService service;
    
    public ServletVentaDetalle() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		int idVenta=parseStringToInt(getParameter("venta"));
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setAttribute("venta", service.get(idVenta));
			redirectPagina("DetalleVenta");
		}
		else{
			redirectPaginaError();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
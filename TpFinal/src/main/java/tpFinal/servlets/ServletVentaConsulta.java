package tpFinal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.domain.Venta;
import tpFinal.service.VentaService;

//@WebServlet("/venta/consulta")
public class ServletVentaConsulta extends Servlet {
	private static final long serialVersionUID = 1L;
	private int rolPagina=2;
	private VentaService service;
	
	public ServletVentaConsulta() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);

		service.resetCarrito();
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setDefaultAttributes();
			redirectPagina("MostrarVenta");
		}
		else{
			redirectPaginaError();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
		Date fechaDesde = getFecha("desde");
		Date fechaHasta = getFecha("hasta");
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		System.out.println("Calculando adicionales desde " + df.format(fechaDesde) + " hasta " + df.format(fechaHasta));
		
		ArrayList<Venta> lista = service.findBySpecificDatesCreatorId(usuario.getVendedor().getId(), fechaDesde, fechaHasta);
		
		setAttribute("lista", lista);
		redirectPagina("MostrarVenta");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.service = (VentaService) ctx.getBean("VentaService");
	}

	private void setDefaultAttributes() {
		setAttribute("lista", service.findByCreatorId(usuario.getVendedor().getId()));
	}
}

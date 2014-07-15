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
public class ServletVentaConsulta extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private int rolPagina=2;
	private VentaService service;
	
	public ServletVentaConsulta() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
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
		
		Date fechaDesde;
		Date fechaHasta;
		ArrayList<Venta> lista = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String error="";
		
		try {
			fechaDesde = getFecha("desde");
			fechaHasta = getFecha("hasta");
			System.out.println("Buscando ventas desde " + df.format(fechaDesde) + " hasta " + df.format(fechaHasta));
			lista = service.findBySpecificDatesCreatorId(usuario.getVendedor().getId(), fechaDesde, getFechaUnDiaMas(fechaHasta));
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			error = "Por favor revise los datos ingresados.";
		} finally{
			if (lista==null){//Si las fechas se setearon bien pero no hay ninguna venta
				error = "No se encontraron ventas para el período ingresado.";
			}
			setAttribute("lista", lista);
			setAttribute("error", error);
			redirectPagina("MostrarVenta");
		}
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

	private void setDefaultAttributes() {
		setAttribute("lista", service.findByCreatorId(usuario.getVendedor().getId()));
	}
}

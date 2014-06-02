package tpFinal.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.calculation.calculationImpl.UsuarioCalculation;
import tpFinal.service.calculation.calculationImpl.VendedorCalculation;

/**
 * Servlet implementation class Administrador
 */
//@WebServlet("/Administrador")
public class ServletAdministrador extends Servlet {
	private static final long serialVersionUID = 1L;
	private UsuarioCalculation usuarioCalculation;
	private VendedorCalculation vendedorCalculation;
	private int rolPagina =1;
	private String mensaje="";
	
    public void setUsuarioCalculation(UsuarioCalculation usuarioCalculation) {
		this.usuarioCalculation = usuarioCalculation;
	}

	public void setVendedorCalculation(VendedorCalculation vendedorCalculation) {
		this.vendedorCalculation = vendedorCalculation;
	}

	public ServletAdministrador() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setDefaultAttributes();
			redirectPagina("Admin");
		}
		else{
			redirectPaginaError();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		String accion=request.getParameter("accion");
		mensaje="";
		
		//Usuario
		if(accion.equals("nuevoUsuario"))
			mensaje=usuarioCalculation.nuevo(getParameter("username"), getParameter("password"), parseString(getParameter("rol")), parseString(getParameter("vendedor")));
		
		if(accion.equals("estadoUsuario"))
			mensaje=usuarioCalculation.cambiarEstado(parseString(getParameter("idUsuario")));
		
		
		//Vendedor
		if(accion.equals("nuevoVendedor"))
			mensaje=vendedorCalculation.nuevo(request.getParameter("nombre"), request.getParameter("apellido"));
			
		if(accion.equals("estadoVendedor"))
			mensaje=vendedorCalculation.cambiarEstado(parseString(getParameter("idVendedor")));
		
		//mensajes
		if (accion.contains("Usuario"))
		{
			if (mensaje.equals(""))
				request.setAttribute("ok", "El usuario se guardo correctamente.");
			else
				request.setAttribute("error", mensaje);
		}
		
		if (accion.contains("Vendedor"))
		{
			if (mensaje.equals(""))
				request.setAttribute("ok", "El vendedor se guardo correctamente.");
			else
				request.setAttribute("error", mensaje);
		}
			
		redirectPagina("Admin");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.usuarioCalculation = (UsuarioCalculation) ctx.getBean("UsuarioCalculation");
		this.vendedorCalculation = (VendedorCalculation) ctx.getBean("VendedorCalculation");
	}
	
	private void setDefaultAttributes() {
		setAttribute("usuarios", usuarioCalculation.getAll());
		setAttribute("vendedores", vendedorCalculation.getAll());
	}
}

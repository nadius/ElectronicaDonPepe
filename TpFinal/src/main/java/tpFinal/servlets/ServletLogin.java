package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.security.Usuario;
import tpFinal.service.calculation.calculationImpl.UsuarioCalculation;
import tpFinal.service.findItem.findItemImpl.UsuarioFindItem;

//@WebServlet("/login")
public class ServletLogin extends ServletUtils{
	private static final long serialVersionUID = 1L;
	
	private UsuarioFindItem findItem;
	private UsuarioCalculation calculation;
	
	public void setFindItem(UsuarioFindItem findItem) {
		this.findItem = findItem;
	}
	
	public void setCalculation(UsuarioCalculation calculation) {
		this.calculation = calculation;
	}

	public ServletLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
		setOrigen();
		//System.out.println("Context: " + request.getContextPath());
		redirectPagina("Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);

		String usuario = getParameter("usuario").trim();
		String password = getParameter("password").trim();
		Usuario cuenta= findItem.findByUsername(usuario);
		
		if (calculation.login(usuario, password))
		{
			setSessionAttribute("usuario", cuenta);
			if (cuenta.getRol().getId()==2)//si el usuario es vendedor
				System.out.println("Logueado como " + cuenta.getVendedor().getNombre() + " " + cuenta.getVendedor().getApellido());
			redirectPagina("Index");
		}
		else
		{
			setAttribute("mensaje", "Credenciales incorrectas o usuario no existente.");
			redirectPagina("Login");
		}
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.findItem = (UsuarioFindItem) ctx.getBean("UsuarioFindItem");
		this.calculation = (UsuarioCalculation) ctx.getBean("UsuarioCalculation");
		super.init(config);
	}
}

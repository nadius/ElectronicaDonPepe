package TpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import TpFinal.dataccess.DataAccess;
import TpFinal.security.Usuario;
import TpFinal.service.Service;
//@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataAccess dataAccess;
	public Service service;
	
	public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("origen", request.getContextPath());
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usuario = request.getParameter("usuario").trim();
		String password = request.getParameter("password");
		Usuario cuenta= service.getUsuario(usuario);
		
		if (service.login(usuario, password))
		{
			request.getSession().setAttribute("usuario", cuenta);
			if (cuenta.getRol().getId()==2)//si el usuario es vendedor
				System.out.println("Logueado como " + cuenta.getVendedor().getNombre() + " " + cuenta.getVendedor().getApellido());
			request.getRequestDispatcher("/WEB-INF/Index.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("mensaje", "Credenciales incorrectas o usuario no existente.");
			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
		}
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.dataAccess = (DataAccess) ctx.getBean("dataAccessBean");
		this.service = (Service) ctx.getBean("serviceBean");
	}

	public DataAccess getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}

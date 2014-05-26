package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Venta;
import tpFinal.security.Usuario;
import tpFinal.service.Service;

//@WebServlet("/venta/consulta/detalle")
public class VentaDetalle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataAccessInterface dataAccess;
	public Service service;
    
    public VentaDetalle() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//verificacion de usuario
		Usuario usuario=(Usuario) request.getSession().getAttribute("usuario");
		if (usuario==null)
			response.sendRedirect(request.getContextPath() + "/login");
		else
		{
			if (verificarRol(usuario))
			{
				Integer idVenta = Integer.parseInt(request.getParameter("venta"));
				Venta venta = dataAccess.getVenta(idVenta);
				
				request.setAttribute("venta", venta);
				request.setAttribute("lista", venta.getProductos());
				request.getRequestDispatcher("/WEB-INF/DetalleVenta.jsp").forward(request, response);
			}
			else
				response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.dataAccess = (DataAccessInterface) ctx.getBean("dataAccessBean");
		this.service = (Service) ctx.getBean("serviceBean");
	}

	public DataAccessInterface getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	public boolean verificarRol(Usuario cuenta)
	{
		if (cuenta.getRol().getId()!=2)
			return false;
		return true;
	}
}

package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpFinal.security.Usuario;

//@WebServlet("/index")
public class index extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//verificacion de usuario
		Usuario usuario=(Usuario) request.getSession().getAttribute("usuario");
		if (usuario==null)
			response.sendRedirect(request.getContextPath() + "/login");
		else
			request.getRequestDispatcher("/WEB-INF/Index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}

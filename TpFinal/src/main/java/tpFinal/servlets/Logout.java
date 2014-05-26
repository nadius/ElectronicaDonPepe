package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		//request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/login");//redirijo a la página de logueo
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getSession().setAttribute("usuario", null);//la deja en null
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login");//redirijo a la página de logueo
		//request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}

}

package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/index")
public class ServletIndex extends Servlet
{
	private static final long serialVersionUID = 1L;

	public ServletIndex() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		//verificacion de usuario
		if (isLogedIn())
			redirectPagina("Index");
		else
			redirectLogin();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}

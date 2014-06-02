package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpFinal.security.Usuario;
import tpFinal.service.calculation.calculationImpl.UsuarioCalculation;

public abstract class Servlet extends HttpServlet{//Funciones comunes a todos los servlets
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request; 
	private HttpServletResponse response;
	private UsuarioCalculation usuarioCalculation;
	protected Usuario usuario;

	public void setUsuarioCalculation(UsuarioCalculation usuarioCalculation) {
		this.usuarioCalculation = usuarioCalculation;
	}
	
	public void setRequestResponse(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}

/*
 * Verifica que exista una sesion activa
 */
	public boolean isLogedIn() throws IOException, ServletException{
		if(getAttribute("usuario")==null)
			return false;
		usuario=(Usuario) getAttribute("usuario");
		return true;
	}
/*
 * Verifica que el usuario acceda a una funcion que le corresponde.
 */
	public void isAllowed(String jspNombre, Usuario usuario, int idRol) throws ServletException, IOException{
		if (usuarioCalculation.verificarRol(usuario, idRol))
			redirectPagina(jspNombre);
		else
			redirectPaginaError();
	}

/*
 * Verifica que el usuario acceda a una funcion que le corresponde.
 */
	public boolean isAllowed(int idRol) throws ServletException, IOException{
		return usuarioCalculation.verificarRol((Usuario) getAttribute("usuario"), idRol);
	}
/*
 * Convierte un string en un int
 */
	public int parseString(String object){
		return Integer.parseInt(object);
	}

/*
 * Redirecciona a una pagina dada	
 */
	public void redirectPagina(String jspNombre) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/" + jspNombre + ".jsp").forward(request, response);
	}

/*
 * Redirecciona al login
 */
	public void redirectLogin() throws IOException{
		response.sendRedirect(request.getContextPath() + "/login");
	}
/*
 * Redirecciona a la pagina utilizada para cuando un usuario no esta autorizado
 */
	public void redirectPaginaError() throws IOException{
		response.sendRedirect(request.getContextPath() + "/error");
	}

/*
 * Recuperar un atributo del request (NO OLVIDAR DE CASTEAR EL RESULTADO!)
 */
	public Object getAttribute(String nombreAttr){
		return request.getSession().getAttribute("usuario");
	}
	
/*
 * Recuperar un parametro del request (NO OLVIDAR DE CASTEAR EL RESULTADO!)
 */
	public String getParameter(String nombreAttr){
		return request.getParameter("usuario");
	}
	
/*
 * Setear un atributo en el request
 */
	public void setAttribute(String nombreAttr, Object attr){
		request.setAttribute("total", attr);
	}
/*
 * Setea el origen
 */
	public void setOrigen(){
		setAttribute("origen", request.getContextPath());
	}
	
/*
 * Recupera el origen
 */
	public String getOrigen(){
		return (String) getAttribute("origen");
	}
	
	//public abstract void setDefaultParams();
}

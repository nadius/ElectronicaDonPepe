package tpFinal.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.security.Usuario;
import tpFinal.service.calculation.calculationImpl.UsuarioCalculation;

public class ServletUtils extends HttpServlet{//Funciones comunes a todos los servlets
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		//no hace nada porque no hace falta
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		//no hace nada porque no hace falta
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.usuarioCalculation = (UsuarioCalculation) ctx.getBean("UsuarioCalculation");
	}
	
/**
 * Verifica que exista una sesion activa, recuperando el atributo Usuario que fue guardado al momento del login
 * @return true si existe un usuario logueado
 **/
	public boolean isLogedIn(){
		try{
			getSessionAttribute("usuario");
		} catch(NullPointerException e){//si el usuario no existe
			System.out.println("No existe un usuario logueado. Redirigiendo a la página de login.");
			return false;
		}
		usuario=(Usuario) getSessionAttribute("usuario");
		return true;
	}
	
/**
 * Verifica que el usuario acceda a una funcion que le corresponde.
 * Redirije a la página solicitada si el usuario está autorizado, o a la página de error.
 * @param jspNombre el jsp al que se redirigirá
 * @param usuario el registro correspondiente al usuario que inición sesión
 * @param idRol el rol de la página a la que se quiere acceder
 **/
	public void isAllowed(String jspNombre, Usuario usuario, int idRol) throws ServletException, IOException{
		if (usuarioCalculation.verificarRol(usuario, idRol))
			redirectPagina(jspNombre);
		else
			redirectPaginaError();
	}

/**
 * Verifica que el usuario acceda a una funcion que le corresponde.
 * @param idRol el rol de la página a la que se quiere acceder
 **/
	public boolean isAllowed(int idRol) throws ServletException, IOException{
		return usuarioCalculation.verificarRol(usuario, idRol);
	}
/**
 * Convierte un string en un int
 * @param el objeto a parsear
 * @return el objeto convertido a int
 * @throws NumberFormatException si no puede parsear el String.
 **/
	public int parseString(String object){
		try {
			return Integer.parseInt(object);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

/**
 * Redirecciona a una pagina dada
 * @param jspNombre el nombre del archivo jsp al que se quiere redirigir.	
 **/
	public void redirectPagina(String jspNombre) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/" + jspNombre + ".jsp").forward(request, response);
	}

/**
 * Redirecciona al login
 **/
	public void redirectLogin() throws IOException{
		response.sendRedirect(request.getContextPath() + "/login");
	}
/**
 * Redirecciona a la pagina utilizada para cuando un usuario no esta autorizado
 **/
	public void redirectPaginaError() throws IOException{
		response.sendRedirect(request.getContextPath() + "/error");
	}

/**
 * Recuperar un atributo del request (NO OLVIDAR DE CASTEAR EL RESULTADO!)
 * @param nombreAttr nombre del atributo a recuperar
 * @return un Object con el atributo buscado
 * @throws NullPointerException si no se encuentra el atributo.
 **/
	public Object getAttribute(String nombreAttr){
		Object param = request.getAttribute(nombreAttr);
		if (param == null){
			throw new NullPointerException(nombreAttr);
		}
		return param;
	}
	
/**
 * Recuperar un parametro del request (NO OLVIDAR DE CASTEAR EL RESULTADO!)
 * @param nombreAttr nombre del parametro a recuperar
 * @return un String con el parametro buscado
 * @throws NullPointerException si no se encuentra el parámetro.
 **/
	public String getParameter(String nombreAttr){
		String param = request.getParameter(nombreAttr);
		if (param.equals("")){
			throw new NullPointerException(nombreAttr);
		}
		return param;
	}
	
/**
 * Setear un atributo en el request
 * @param nombreAttr nombre del atributo a guardar
 * @param attr el Object a guardar como atributo
 **/
	public void setAttribute(String nombreAttr, Object attr){
		request.setAttribute(nombreAttr, attr);
	}
	
/**
 * Guarda un atributo que estará en memoria mientras exista una sesión abierta
 * @param nombreAttr el nombre con el que se guardará el atributo
 * @param attr el Object a guardar como atributo de sesion
 **/
	public void setSessionAttribute(String nombreAttr, Object attr){
		request.getSession().setAttribute(nombreAttr, attr);
	}
	
/**
 * Recupera una atributo guardado en la sesión.
 * @param nombreAttr el nombre del atributo a recuperar
 * @return el atributo buscado, o null si no lo encuentra
 * @throws NullPointerException si no se encuentra el atributo.
 **/
	public Object getSessionAttribute(String nombreAttr){
		Object attr = request.getSession().getAttribute(nombreAttr);
		if (attr == null){
			throw new NullPointerException(nombreAttr);
		}
		return attr;
	}
	
/**
 * Setea el atributo origen, es decir, el contextPath
 **/
	public void setOrigen(){
		setSessionAttribute("origen", request.getContextPath());
	}
	
/**
 * Recupera el atributo origen, es decir, el contextPath
 * @return el valor del contextPath seteado al momento del logueo
 **/
	public String getOrigen(){
		try {
			getSessionAttribute("origen");
		} catch (NullPointerException e){
			return "/TpFinal";
		}
		return (String) getSessionAttribute("origen");
	}
	
	//public abstract void setDefaultParams();
	
/**
 * Recupera una fecha ingresada.
 * Esta funcion se utiliza cuando una fecha está formada por tres inputs, no por un DatePicker
 * @param tipo el nombre del parametro. Eg: para un input llamado "desdeDia", el tipo es "desde".
 * @return un Date con la fecha lista para utilizar en otras funciones.
 **/
	public Date getFecha(String tipo)
	{
		Integer dia, mes, anio;
		dia = parseString(getParameter(tipo+"Dia"));
		mes = parseString(getParameter(tipo+"Mes"))-1;
		anio = parseString(getParameter(tipo+"Anio"));
		
		if (dia<=0 || dia>31 || mes<0 || mes>12 || anio<0){
			throw new IllegalArgumentException("fecha " + tipo);
		}
		
		return new GregorianCalendar(anio,mes,dia,0,0,0).getTime();
	}
	
/**
 * Finaliza la sesión, invalidándola
 **/
	public void invalidarSesion(){
		request.getSession().invalidate();
	}
}

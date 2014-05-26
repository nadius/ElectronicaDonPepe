package tpFinal.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Vendedor;
import tpFinal.security.Usuario;
import tpFinal.service.Service;

/**
 * Servlet implementation class Administrador
 */
//@WebServlet("/Administrador")
public class Administrador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataAccessInterface dataAccess;
	public Service service;
	ArrayList<Usuario> usuarios=null;
	ArrayList<Vendedor> vendedores=null;
	
	String mensaje="";
	
    public Administrador() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//verificacion de usuario
		Usuario usuario=(Usuario) request.getSession().getAttribute("usuario");
		usuarios=dataAccess.getUsuarios();
		vendedores=dataAccess.getVendedores();
		
		request.setAttribute("usuarios", usuarios);
		request.setAttribute("vendedores", vendedores);
		
		if (usuario==null)
			response.sendRedirect(request.getContextPath() + "/login");
		else
		{
			if (verificarRol(usuario))
				request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
			else
				response.sendRedirect(request.getContextPath() + "/error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String origen=request.getParameter("accion");
		mensaje="";
		
		//Usuario
		if(origen.equals("nuevoUsuario"))
			nuevoUsuario(request.getParameter("username"), request.getParameter("password"), request.getParameter("rol"), request.getParameter("vendedor"));
		
		if(origen.equals("estadoUsuario"))
			estadoUsuario(request.getParameter("idUsuario"));
		
		
		//Vendedor
		if(origen.equals("nuevoVendedor"))
			nuevoVendedor(request.getParameter("nombre"), request.getParameter("apellido"));
			
		if(origen.equals("estadoVendedor"))
			estadoVendedor(request.getParameter("idVendedor"));
		
		//mensajes
		if (origen.contains("Usuario"))
		{
			if (mensaje.equals(""))
				request.setAttribute("ok", "El usuario se guardo correctamente.");
			else
				request.setAttribute("error", mensaje);
		}
		
		if (origen.contains("Vendedor"))
			if (origen.contains("Vendedor"))
		{
			if (mensaje.equals(""))
				request.setAttribute("ok", "El vendedor se guardo correctamente.");
			else
				request.setAttribute("error", mensaje);
		}
			
		request.setAttribute("usuarios", usuarios);
		request.setAttribute("vendedores", vendedores);
		
		request.getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
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
	
	public void nuevoUsuario(String username, String password, String idRol, String idVendedor)
	{	
		Usuario nuevo=new Usuario();
		mensaje="";
		
		nuevo.setUsername(username);
		nuevo.setPassword(password);
		nuevo.setRol(dataAccess.getRol(Integer.parseInt(idRol)));
		
		if (idRol.equals("2"))
			nuevo.setVendedor(dataAccess.getVendedor(Integer.parseInt(idVendedor)));
		
		mensaje=verificarUsuario(nuevo);
		
		if(mensaje.equals(""))			
		{
			dataAccess.guardarUsuario(nuevo);
			usuarios.add(nuevo);
		}
	}
	
	public void estadoUsuario(String id)
	{
		Usuario registro=dataAccess.getUsuario(Integer.parseInt(id));
		mensaje="";
		
		if (registro==null)//si no se encontro nada
		{
			mensaje= "No se pudo encontrar el usuario.";
			return;
		}
		
		if (registro.isActivo())//si el usuario esta activo
			registro.setActivo(false);
		else//si el usuario no esta activo
			registro.setActivo(true);
		
		dataAccess.actualizarUsuario(registro);
		usuarios=dataAccess.getUsuarios();
	}
	
	public void nuevoVendedor(String nombre, String apellido)
	{
		Vendedor nuevo=new Vendedor();
		mensaje="";
		nuevo.setNombre(nombre);
		nuevo.setApellido(apellido);
		
		mensaje=verificarVendedor(nuevo);
		
		if(mensaje.equals(""))			
		{
			dataAccess.guardarVendedor(nuevo);
			vendedores.add(nuevo);
		}
		nuevoUsuario(nombre+apellido, "vendedor", "2", String.valueOf(nuevo.getId()));
	}
	
	public void estadoVendedor(String id)
	{
		Vendedor registro=dataAccess.getVendedor(Integer.parseInt(id));
		mensaje="";
		
		if (registro==null)//si no se encontro nada
		{
			mensaje= "No se pudo encontrar el vendedor.";
			return;
		}
		
		if (registro.isActivo())//si el usuario esta activo
			registro.setActivo(false);
		else//si el usuario no esta activo
			registro.setActivo(true);
		
		dataAccess.actualizarVendedor(registro);
		vendedores=dataAccess.getVendedores();
	}
	
	public String verificarUsuario(Usuario registro)
	{
		String mensaje="";
		if (registro.getUsername().equals(""));
			mensaje.concat(" nombre de usuario vacío");
		if (registro.getPassword().equals(""))
			mensaje.concat(" contraseña vacía");
		if (registro.getRol()==null)
			mensaje.concat(" rol de usuario vacío o no válido");
		if (registro.getRol().getId()==2 && registro.getVendedor()==null)
			mensaje.concat(" vendedor vacío o no válido");
		
		return mensaje;
	}
	
	public String verificarVendedor(Vendedor registro)
	{
		String mensaje="";
		if (registro.getNombre().equals(""));// agregar breakpoint y revisar
			mensaje.concat(" nombre vacío");
		if (registro.getApellido().equals(""))
			mensaje.concat(" apellido vacío");
		
		return mensaje;
	}
	
	public boolean verificarRol(Usuario cuenta)
	{
		if (cuenta.getRol().getId()!=3)
			return false;
		return true;
	}
}

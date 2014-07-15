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
public class ServletAdministrador extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private UsuarioCalculation usuarioCalculation;
	private VendedorCalculation vendedorCalculation;
	private int rolPagina =3;
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
		String accion, username, password, nombre, apellido;
		int rol=0, id=0;
		mensaje="";
		
		try {
			accion =getParameter("accion");
			
			//Usuario
			if(accion.equals("nuevoUsuario")){//da de alta un nuevo usuario
				username = getParameter("username");
				password = getParameter("password");
				rol = parseStringToInt(getParameter("rol"));
				
				if ((rol == 0) || (rol >3)){//si se ingreso un numero de rol no valido
					throw new NullPointerException("rol");
				}
				
				mensaje=usuarioCalculation.nuevo(username, password, rol);
				
				if (contarPalabras(mensaje) == 1){
					setAttribute("ok", "El usuario se guardo correctamente");
				}
			}
			
			if(accion.equals("estadoUsuario")){//activa o desactiva un usuario elegido
				id = parseStringToInt(getParameter("idUsuario"));
				mensaje=usuarioCalculation.cambiarEstado(id);

				if (contarPalabras(mensaje) == 1){
					setAttribute("ok", "El usuario se actualizo correctamente");
				}
			}
			
			//Vendedor
			if(accion.equals("nuevoVendedor")){//agrego un nuevo vendedor y su usuario asociado
				nombre = getParameter("nombre");
				apellido = getParameter("apellido");
				mensaje=vendedorCalculation.nuevo(nombre, apellido);

				if (contarPalabras(mensaje) == 1){
					setAttribute("ok", "El vendedor y el usuario se guardaron correctamente");
				}
			}
				
			if(accion.equals("estadoVendedor")){
				id = parseStringToInt(getParameter("idVendedor"));
				mensaje=vendedorCalculation.cambiarEstado(id);

				if (contarPalabras(mensaje) == 1){
					setAttribute("ok", "El vendedor se actualizo correctamente");
				}
			}
			
			//mensajes
			if (contarPalabras(mensaje) != 1){
				setAttribute("error", mensaje);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();			
			setAttribute("error", getCustomExceptionMessage(e.getMessage()));
		} catch (NumberFormatException e){
			setAttribute("error", getCustomExceptionMessage(e.getMessage()));
		}
		
		setDefaultAttributes();
		redirectPagina("Admin");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.usuarioCalculation = (UsuarioCalculation) ctx.getBean("UsuarioCalculation");
		this.vendedorCalculation = (VendedorCalculation) ctx.getBean("VendedorCalculation");
		super.init(config);
	}
	
	private void setDefaultAttributes() {
		setAttribute("usuarios", usuarioCalculation.getAll());
		setAttribute("vendedores", vendedorCalculation.getAll());
	}
}

package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.AdicionalMontoService;

//@WebServlet("/AdicionalesModificarMontos")
public class ServletAdicionalMonto extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private int rolPagina=1;
	private AdicionalMontoService service;
      
	//parametros de los formularios
	String accion="";
	String tipo= "";
	int id=0;
	int respuesta=0;
	float montoActual = 0;
	float montoNuevo=0;
	
    public ServletAdicionalMonto() {
      //nada para hacer
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setDefaultAttributes();
			redirectPagina("ModificarAdicionales");
		}
		else{
			redirectPaginaError();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);

		try {
			accion=getParameter("accion");
			tipo= getParameter("tipo");
			if (accion.equals("set")){//seteo los valores que se necesitan en el formulario de actualización : registro y tipo del mismo
				id= parseStringToInt(getParameter("id"));
				
				if (tipo.equals("comisionVenta")){
					montoActual = service.getMontoComisionVenta(id).getMonto();
				}
				
				if (tipo.equals("comisionProducto")){
					montoActual = service.getMontoComisionProducto(id).getMonto();
				}
				
				if(tipo.equals("premio")){
					montoActual = service.getMontoPremio(id).getMonto();
				}
				
				setActualizacionParams();
			}
			
			if (accion.equals("update")){//recupero el valor ingresado
				tipo = getParameter("tipo");
				montoNuevo = parseStringToFloat(getParameter("valor"));
				id= parseStringToInt(getParameter("id"));
				
				if (tipo.equals("comisionVenta")){
					respuesta = service.actualizarMontoComisionVenta(id, montoNuevo);
				}
				
				if (tipo.equals("comisionProducto")){
					respuesta = service.actualizarMontoComisionProducto(id, montoNuevo);
				}
				
				if(tipo.equals("premio")){
					respuesta = service.actualizarMontoPremio(id, montoNuevo);
				}
				setAttribute("tipo", tipo);
				setAttribute("actualizados", respuesta);
			}
		} catch (NullPointerException e){
			e.printStackTrace();
			setAttribute("error", "No se pudo encontrar un parámetro.");
			setActualizacionParams();
			setAttribute("montoNuevo", montoNuevo);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setAttribute("error", "No se pudo convertir uno de los números ingresados.");
			setActualizacionParams();
			setAttribute("montoNuevo", montoNuevo);
		} catch (Exception e){
			e.printStackTrace();
			setAttribute("error", "Ocurrió un error inesperado.");
			setActualizacionParams();
			setAttribute("montoNuevo", montoNuevo);
		}
		finally{
			setDefaultAttributes();
			redirectPagina("ModificarAdicionales");
		}
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.service = (AdicionalMontoService) ctx.getBean("AdicionalMontoService");
		super.init(config);
	}
	
	public void setService(AdicionalMontoService service) {
		this.service = service;
	}

	private void setDefaultAttributes() {
		setAttribute("comisionVenta", service.getAllMontoComisionVenta());
		setAttribute("comisionProducto", service.getAllMontosComisionProducto());
		setAttribute("premios", service.getAllMontosPremio());
	}
	
	private void setActualizacionParams() {
		setAttribute("tipo", tipo);
		setAttribute("id", id);
		setAttribute("montoActual", montoActual);
	}
}

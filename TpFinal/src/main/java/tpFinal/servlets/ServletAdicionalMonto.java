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

		String accion=getParameter("accion");
		String tipo= getParameter("tipo");
		int id=0;
		int respuesta=0;
		float valor=0;
		
		if (accion.equals("set")){//seteo los valores que se necesitan en el formulario de actualización : registro y tipo del mismo
			setAttribute("tipo", getParameter("tipo"));
			id= Integer.parseInt(getParameter("id"));
			
			if (tipo.equals("comisionVenta")){
				setAttribute("registro", service.getMontoComisionVenta(id));
			}
			
			if (tipo.equals("comisionProducto")){
				request.setAttribute("registro", service.getMontoComisionProducto(id));
			}
			
			if(tipo.equals("premio")){
				request.setAttribute("registro", service.getMontoPremio(id));
			}
		}
		
		if (accion.equals("update")){//recupero el valor ingresado
			valor = Float.parseFloat(request.getParameter("valor"));
			id= Integer.parseInt(request.getParameter("id"));
			
			if (tipo.equals("comisionVenta")){
				respuesta = service.actualizarMontoComisionVenta(id, valor);
			}
			
			if (tipo.equals("comisionProducto")){
				respuesta = service.actualizarMontoComisionProducto(id, valor);
			}
			
			if(tipo.equals("premio")){
				respuesta = service.actualizarMontoPremio(id, valor);
			}
			request.setAttribute("actualizados", respuesta);
		}
			
		setDefaultAttributes();
		redirectPagina("ModificarAdicionales");
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

	public float[] recuperarValores (String nombreCampo)
	{
		float[] valores;
		Integer cantidad=0, i, j=0;
		String[] valoresString;
		
		//recuepro la cantidad de montos existentes
		if (nombreCampo.equals("cVenta"))
			cantidad=service.getAllMontoComisionVenta().size();
		
		if (nombreCampo.equals("cProducto"))
			cantidad=service.getAllMontosComisionProducto().size();
		
		if (nombreCampo.equals("Premio"))
			cantidad=service.getAllMontosPremio().size();		
		
		valores = new float[cantidad];
		valoresString = new String[cantidad];
		
		for (i=1; i<=cantidad; i++)//recupero todos los parametros
			valoresString[i-1]=getParameter(nombreCampo+i.toString()+ "Valor");
		
		
		System.out.println("Montos a modificar en " + nombreCampo);
		//Verificacion 1 (string)
		System.out.print("\t String:");
		for (String valor : valoresString)
			System.out.print(" " + valor);
		System.out.print("\n");
		
		//paso los valores al array
		for (i=0; i<cantidad; i++)
			if (valoresString[i]!=null)
				valores[j++]=Float.parseFloat(valoresString[i]);
		
		//Verificacion 2 (float)
		System.out.print("\t float:");
		for (float valor : valores)
			System.out.print(" " + valor);
		System.out.print("\n");
		
		return valores;
	}

	private void setDefaultAttributes() {
		setAttribute("comisionVenta", service.getAllMontoComisionVenta());
		setAttribute("comisionProducto", service.getAllMontosComisionProducto());
		setAttribute("premios", service.getAllMontosPremio());
	}
}

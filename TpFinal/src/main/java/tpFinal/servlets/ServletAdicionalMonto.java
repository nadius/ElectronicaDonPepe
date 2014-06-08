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
public class ServletAdicionalMonto extends Servlet {
	private static final long serialVersionUID = 1L;
	private int rolPagina=1;
	private AdicionalMontoService service;
       
    public ServletAdicionalMonto() {
        super();
        // TODO Auto-generated constructor stub
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

		String cVenta = getParameter("cVenta");
		String cProducto=getParameter("cProducto");
		String Premio = getParameter("Premio");
		
		if (cVenta!=null && cVenta.equals("true"))
			service.actualizarMontoComisionVenta(recuperarValores("cVenta"));
			
		if (cProducto!=null && cProducto.equals("true"))
			service.actualiarMontoComisionProducto(recuperarValores("cProducto"));
			
		if (Premio!=null && Premio.equals("true"))
			service.actualizarMontoPremio(recuperarValores("Premio"));
			
		setDefaultAttributes();
		redirectPagina("ModificarAdicionales");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.service = (AdicionalMontoService) ctx.getBean("AdicionalMontoService");
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

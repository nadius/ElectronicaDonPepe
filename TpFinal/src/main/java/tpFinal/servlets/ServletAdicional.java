package tpFinal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.AdicionalService;
import tpFinal.servlets.ServletUtils;

public class ServletAdicional extends ServletUtils {
	private static final long serialVersionUID = 1L;
	private int rolPagina=1;
	private AdicionalService service;
	
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		
		if (!isLogedIn()){
			redirectLogin();
		}
		else if (isAllowed(rolPagina)){
			setDefaultAttributes();
			redirectPagina("CalcularAdicionales");
		}
		else{
			redirectPaginaError();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		service.resetParams();//reseteo la lista de vendedores, de ventas y seteo la fecha de creación.
		
		Date fechaDesde=null;
		Date fechaHasta=null;
		try {
			fechaDesde = getFechaSoloMesAnio("desde");
			fechaHasta = getFechaUnMesMas("desde");
			setParamsVendedores();
		} catch (NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			setAttribute("error", getCustomExceptionMessage(e.getMessage()));
			setDefaultAttributes();
			redirectPagina("CalcularAdicionales");
			return;
		}
		
		System.out.println("Calculando adicionales desde " + df.format(fechaDesde) + " hasta " + df.format(fechaHasta));
		
		if (service.calcular(fechaDesde, fechaHasta).isEmpty()){//si el cálculo devuelve un arraylist vacío
			setAttribute("error", "No se encontraron ventas para el período buscado");
		}
		
		setDefaultAttributes();
		redirectPagina("CalcularAdicionales");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.service = (AdicionalService) ctx.getBean("AdicionalService");
		super.init(config);
	}
	
	public void setService(AdicionalService service) {
		this.service = service;
	}

	public int[] getParamVendedoresIntArray(){
		int cantVendedores=service.getVendedoresActivos().size(), i;
		int[] vendedoresElegidosInteger=new int[cantVendedores];
		
		for (i=1; i<=cantVendedores; i++){//recupero todos los parametros
			try {
				vendedoresElegidosInteger[i-1]=parseStringToInt(getParameter("vendedor"+i));//si el vendedor no está seleccionado getParameter lanza una NullPointerException
			} catch (NullPointerException e) {
				vendedoresElegidosInteger[i-1]=0;
			}
		}
		
		return vendedoresElegidosInteger;
	}
	
	public void setParamsVendedores(){
		if (isVendedoresEmpty()){
			throw new NullPointerException("vendedores");
		}		
		
		for (int i : getParamVendedoresIntArray()){
			if (i!=0){
				service.addVendedor(i);
			}
		}
	}
	
	private boolean isVendedoresEmpty() {
		int[] vendedores=getParamVendedoresIntArray();
		int cuenta=0;
		for (int i : vendedores){
			if (i==0)
				cuenta++;
		}
		
		if (cuenta==service.getVendedoresActivos().size())//si todos los int del array son cero, ie, si no se selecciono ningún vendedor
			return true;
		return false;
	}

	private void setDefaultAttributes() {
		setAttribute("vendedores", service.getVendedoresActivos());
		setAttribute("adicionales", service.getAll());
	}
}
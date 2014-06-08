package tpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.service.calculation.calculationImpl.CampaniaCalculation;

//@WebServlet("/AdicionalesAdministrarCampanias")
public class ServletCampania extends Servlet{
	private static final long serialVersionUID = 1L;
	private int rolPagina=1;
	private CampaniaCalculation calculation;
       
    public ServletCampania() {
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
			redirectPagina("RegistrarCampania");
		}
		else{
			redirectPaginaError();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setRequestResponse(request, response);
		String accion=getParameter("accion");
		
		if (accion.equals("agregar"))
			calculation.agregar(parseString(getParameter("idProducto")));
		
		if (accion.equals("eliminar"))
			calculation.eliminar(parseString(getParameter("idCampania")));
		
		setDefaultAttributes();		
		redirectPagina("RegistrarCampania");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.calculation = (CampaniaCalculation) ctx.getBean("CampaniaCalculation");
	}
	
	private void setDefaultAttributes() {
		setAttribute("campaniasExistentes", calculation.getAll());
		setAttribute("productos", calculation.getProductosNoCampania());
	}
}

package tpFinal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Venta;
import tpFinal.security.Usuario;
import tpFinal.service.Service;

//@WebServlet("/venta/consulta")
public class VentaConsulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataAccessInterface dataAccess;
	public Service service;
      
	Usuario usuario=null;
	
	public VentaConsulta() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//verificacion de usuario
		usuario=(Usuario) request.getSession().getAttribute("usuario");
		if (usuario==null)
			response.sendRedirect(request.getContextPath() + "/login");
		else
		{
			if (verificarRol(usuario))
			{
				ArrayList<Venta> lista =service.getVentas(usuario.getVendedor().getId());
				request.setAttribute("lista", lista);
				request.getRequestDispatcher("/WEB-INF/MostrarVenta.jsp").forward(request, response);
			}
			else
			{
				response.sendRedirect(request.getContextPath() + "/error");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// tengo que recuperar las fechas elegidas y con ellas recuperar las ventas pedidas
	
	//Versión con GregorianCalendar
		GregorianCalendar desde = setFechaGregorian(request.getParameter("desdeDia"), request.getParameter("desdeMes"), request.getParameter("desdeAnio"));
		GregorianCalendar hasta = setFechaGregorian(request.getParameter("hastaDia"), request.getParameter("hastaMes"), request.getParameter("hastaAnio"));
		
		//System.out.println("Buscar desde " + desde.getTime().toString() + " hasta " + hasta.getTime().toString());
		
		ArrayList<Venta> lista =buscarVentas(desde, hasta);
		
	//Versión con Date
/*		Date desde = setFechaDate(request.getParameter("desdeDia"), request.getParameter("desdeMes"), request.getParameter("desdeAnio"));
		Date hasta = setFechaDate(request.getParameter("hastaDia"), request.getParameter("hastaMes"), request.getParameter("hastaAnio"));
		System.out.println("Buscar desde " + desde.toString() + " hasta " + hasta.toString());
		ArrayList<Venta> lista =buscarVentas(desde,hasta);
*/
		request.setAttribute("lista", lista);
		request.getRequestDispatcher("/WEB-INF/MostrarVenta.jsp").forward(request, response);
		//response.sendRedirect(request.getContextPath()+ "/consulta");
	}
	
	@Override
	public void init(ServletConfig config) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		this.dataAccess = (DataAccessInterface) ctx.getBean("dataAccessBean");
		this.service = (Service) ctx.getBean("serviceBean");
	}


	public GregorianCalendar setFechaGregorian(String fechaDia, String fechaMes, String fechaAnio)
	{
		Integer dia, mes, anio;
		dia = Integer.parseInt(fechaDia);
		mes = Integer.parseInt(fechaMes);
		anio = Integer.parseInt(fechaAnio);
		
		return new GregorianCalendar(anio,mes,dia,0,0,0);
	}
	
	@SuppressWarnings("deprecation")
	public Date setFechaDate(String fechaDia, String fechaMes, String fechaAnio)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date fecha =new Date();
		
		Integer dia, mes, anio;
		dia = Integer.parseInt(fechaDia);
		mes = Integer.parseInt(fechaMes);
		anio = Integer.parseInt(fechaAnio);
		
		fecha.setDate(dia);
		fecha.setMonth(mes);
		fecha.setYear(anio);
		fecha.setHours(0);
		fecha.setMinutes(0);
		fecha.setSeconds(0);
		
		/*
		try {
			fecha = df.parse(fechaAnio + "-" + fechaMes + "-" + fechaDia + " 00:00:00.0");
		} catch (ParseException e) {
			System.out.println("No se pudo convertir a Date!");
			e.printStackTrace();
		}
		finally
		{
			return fecha;
		}*/
		return fecha;
	}

	public ArrayList<Venta> buscarVentas(Date desde, Date hasta)
	{
		System.out.println("Buscar desde " + desde.toString() + " hasta " + hasta.toString());
		ArrayList<Venta> todos=dataAccess.getVentas();
		ArrayList<Venta> seleccion=new ArrayList<Venta>();
		
/*		for (Venta item : todos)
			if (item.getFecha().after(desde) && item.getFecha().before(desde))
				seleccion.add(item);*/
		
		for (Venta item : todos)
		{	
			//System.out.println(item.getId() + ": " + item.getFecha().toString());
			if (item.getFecha().after(desde) && item.getFecha().before(desde))
				seleccion.add(item);
		}
		
		System.out.println("Encontrados " + seleccion.size() + " de " + todos.size() + " registros");
		return seleccion;
	}

	public ArrayList<Venta> buscarVentas(GregorianCalendar desde, GregorianCalendar hasta)
	{
		System.out.println("Buscar desde " + desde.toString() + " hasta " + hasta.toString());
		ArrayList<Venta> todos=service.getVentas(usuario.getVendedor().getId());
		ArrayList<Venta> seleccion=new ArrayList<Venta>();
		GregorianCalendar calendario = new GregorianCalendar();
		
/*		for (Venta item : todos)
			if (item.getFecha().after(desde) && item.getFecha().before(desde))
				seleccion.add(item);*/
		
		for (Venta item : todos)
		{	
			System.out.println(item.getId() + ": " + item.getFecha().toString());
			calendario.setTime(item.getFecha());
			if (calendario.after(desde) && calendario.before(hasta))
				seleccion.add(item);
		}
		
		System.out.println("Encontrados " + seleccion.size() + " de " + todos.size() + " registros");
		return seleccion;
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
	
	public boolean verificarRol(Usuario cuenta)
	{
		if (cuenta.getRol().getId()!=2)
			return false;
		return true;
	}
}

package TpFinal.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TpFinal.domain.*;
import TpFinal.security.Usuario;

//@WebServlet("/AdicionalGenerarReporte")
public class AdicionalesGenerarReporte extends Adicionales {
	private static final long serialVersionUID = 1L;
       
   public AdicionalesGenerarReporte() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setUsuario((Usuario) request.getSession().getAttribute("usuario"));
		if (getUsuario()==null)
		{
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		if (!verificarRol(getUsuario()))
		{
			response.sendRedirect(request.getContextPath() + "/error");
			return;
		}
		request.setAttribute("vendedores", service.getVendedoresActivos());
		request.setAttribute("productos", service.getProductos());
		request.setAttribute("adicionales", service.getAdicionales());
		request.getRequestDispatcher("/WEB-INF/CalcularAdicionales.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setFechaHoy(new Date());
		
		calcular(request);
		
		request.setAttribute("vendedores", service.getVendedoresActivos());
		request.setAttribute("productos", service.getProductos());
				
		request.getRequestDispatcher("/WEB-INF/CalcularAdicionales.jsp").forward(request, response);
	}
	
	@Override
	public void init(ServletConfig config) {
		super.init(config);
	}
	
	public ArrayList<Vendedor> recuperarVendedores(Integer[] ids)
	{
		ArrayList<Vendedor> seleccion=new ArrayList<Vendedor>();
		for (Integer id : ids)
			seleccion.add(service.getVendedor(id));
		return seleccion;
	}
	
	public Integer[] recuperarVendedores(HttpServletRequest request){
		Integer cantVendedores=service.getVendedoresActivos().size();
		Integer[] vendedoresElegidosInteger;
		String[] vendedoresElegidosString=new String[cantVendedores];
		Integer i, j=0;
		
		for (i=1; i<=cantVendedores; i++)//recupero todos los parametros
			vendedoresElegidosString[i-1]=request.getParameter("vendedor"+i.toString());
		
/*		//verificacion
		System.out.println("Vendedores elegidos: ");
		System.out.print("String: ");
		for (String item : vendedoresElegidosString)
			System.out.print(item + " ");
		System.out.print("\n");*/
		
		//redefino la cantidad de vendedores
		cantVendedores=0;
		for (i=0; i<vendedoresElegidosString.length; i++)
			if (vendedoresElegidosString[i]!=null)
				cantVendedores++;
		
		//paso los vendedores al array
		vendedoresElegidosInteger = new Integer[cantVendedores];
		for (i=0; i<vendedoresElegidosString.length; i++)
			if (vendedoresElegidosString[i]!=null)
				vendedoresElegidosInteger[j++]=Integer.parseInt(vendedoresElegidosString[i]);
		
/*		//Verificacion
		System.out.print("Integer: ");
		for (Integer item : vendedoresElegidosInteger)
			System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		return vendedoresElegidosInteger;
	}
	
	public void calcular(HttpServletRequest request)
	{
		ArrayList<Adicional> adicionales = new ArrayList<Adicional>(); 
		//FECHAS
		//Producto productoCampania=service.getProducto(Integer.parseInt(request.getParameter("productoCampania")));
		GregorianCalendar desde = setFechaGregorian(request.getParameter("desdeDia"), request.getParameter("desdeMes"), request.getParameter("desdeAnio"));
		GregorianCalendar hasta = setFechaGregorian(request.getParameter("hastaDia"), request.getParameter("hastaMes"), request.getParameter("hastaAnio"));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		System.out.println("Calculando adicionales desde " + df.format(desde.getTime()) + " hasta " + df.format(hasta.getTime()));
		
		//VENDEDORES
		Integer[] vendedoresElegidos=recuperarVendedores(request);
		setVendedores(recuperarVendedores(vendedoresElegidos));
		
		//PRODUCTO
		System.out.println("Producto campania: " + request.getParameter("productoCampania"));
		Producto productoCampania=null;
		if (request.getParameter("productoCampania")!=null && !request.getParameter("productoCampania").equals(""))//"" es el equivalente del null
			productoCampania=service.getProducto(Integer.parseInt(request.getParameter("productoCampania")));
		
		//calculo los premios
		Premio mejorVendedorMes = new Premio();
		Premio campania = new Premio();
		if (service.getPremioMejorVendedorMes(desde)==null)//si no hay un registro para esa fecha
		{
			mejorVendedorMes=calcularPremioVendedor(desde);
			service.guardarPremio(mejorVendedorMes);
		}
		else
			mejorVendedorMes=service.getPremioMejorVendedorMes(desde);//lo traigo de la base
		
		if (productoCampania!=null)
		{
			if (service.getPremioCampania(desde, hasta, productoCampania)==null)//si no hay un registro para esa fecha
			{
				campania = calcularPremioCampania(desde, hasta, productoCampania);
				service.guardarPremio(campania);
			}
			else
				campania=service.getPremioCampania(desde, hasta, productoCampania);//lo traigo de la base
		}
				
		//calculo los adicionales en general
		Adicional adicional=new Adicional();
		for(Vendedor vendedor : vendedores)
		{
			if (service.getAdicional(vendedor.getId(), desde, hasta)==null)//si no hay un registro para esas fechas y vendedor
			{
				adicional=calcularAdicional(vendedor, desde, hasta, productoCampania);
					
				//por si no guardé los premios en la base
				if (mejorVendedorMes!=null && vendedor.getId()==mejorVendedorMes.getPremiado().getId())
					adicional.setMejorVendedorMes(mejorVendedorMes);
				if (campania!=null && vendedor.getId()==campania.getPremiado().getId())
					adicional.setCampania(campania);
				
				service.guardarAdicional(adicional);//TODO:por alguna extraña razón no se guardan las Comisiones por Venta, aunque están calculadas y existen
			}
			else
				adicional=service.getAdicional(vendedor.getId(), desde, hasta);//lo traigo de la base
			
			adicionales.add(adicional);
		}
		
		//mostrarResultado(adicionales);
		
		request.setAttribute("adicionales", adicionales);
	}
	
	public void mostrarResultado(ArrayList<Adicional> adicionales)
	{
		for (Adicional registro : adicionales)
		{
			System.out.println(registro.getVendedor().getNombre() + " " + registro.getVendedor().getApellido());
			
			System.out.println("\t Comision Ventas:");
			if (registro.getComisionVentas()!=null)
			{
				System.out.println("\t\t registradas " + registro.getComisionVentas().getUnidades() + " ventas. Importe: " + registro.getComisionVentas().getImporte());
			}
			else
				System.out.println("\t\t No se encontraron ventas para el período buscado");
			
			System.out.println("\t Comision por Producto:");

			if(registro.getComisionesProducto()!=null)
			{
				for (ComisionProducto comision: registro.getComisionesProducto())
					System.out.println("\t\t " + comision.getProducto().getNombre()+ " - " + comision.getUnidades() + " unidades - " + comision.getImporte() + "$");
			}
			else
				System.out.println("\t\t No se encontraron ventas para el período buscado");
			
			System.out.println("\t Mejor vendedor del mes: ");
			if (registro.getMejorVendedorMes()!=null)
				System.out.println("\t\t" + registro.getMejorVendedorMes().getImporte() + "$");
			else
				System.out.println("\t\t No se encontraron ventas para el período buscado");
			
			System.out.println("\t Mejor vendedor campania: ");
			if (registro.getCampania()!=null)
			{
				System.out.println("\t\t producto: " + registro.getCampania().getProducto().getId() + " - " + registro.getCampania().getProducto().getNombre());
				System.out.println("\t\t importe: " + registro.getCampania().getImporte() + "$");
			}
			else
				System.out.println("\t\t No se encontraron ventas para el período buscado");
		}
	}
}

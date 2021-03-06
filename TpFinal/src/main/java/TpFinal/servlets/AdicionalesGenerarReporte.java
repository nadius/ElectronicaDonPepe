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
		request.setAttribute("adicionales", getAdicionales());
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
	
	public void calcular(HttpServletRequest request)
	{
		ArrayList<Adicional> adicionales = new ArrayList<Adicional>(); 
		//FECHAS
		GregorianCalendar desde = getParamFechaSoloMesAnio(request);
		GregorianCalendar hasta = getParamFechaUnMesMas(desde);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		System.out.println("Calculando adicionales desde " + df.format(desde.getTime()) + " hasta " + df.format(hasta.getTime()));
		
		//VENDEDORES
		Integer[] vendedoresElegidos=getParamVendedores(request);
		setVendedores(setListVendedores(vendedoresElegidos));
		
		if (service.findVentas(desde, getFechaIntervaloHasta(desde)).isEmpty()){//si no hay ventas no se hace nada más
			request.setAttribute("error", "No existen ventas para el período solicitado");
			return;
		}
		
		//PREMIOS (los calculo primero porque no están relacionados a los vendedores elegidos
		Premio premioMejorVendedorMes = new Premio();
		ArrayList<Campania> campaniasActivas = service.getCampaniasActivas();
		ArrayList<Premio> premiosCampania = new ArrayList<Premio>();

		premioMejorVendedorMes=calcularPremioVendedor(desde);//Nueva version que incluye la funcion int getPremio(Premio)
		
		if (!campaniasActivas.isEmpty())//si existen campanias activas
		{
			for (Campania campania : campaniasActivas)
			{
				premiosCampania.add(calcularPremioCampania(desde, hasta, campania.getProducto()));//Nueva version que incluye la funcion int getPremio(Premio)
			}
		}
				
		//ADICIONALES (Los que dependen del vendedor)
		Adicional adicional=new Adicional();
		
		for(Vendedor vendedor : vendedores)
		{			
			if (service.existenVentas(vendedor, desde, getFechaIntervaloHasta(desde)))
			{
				adicional=calcularAdicionalVendedor(getFechaHoy(), desde, hasta, vendedor, premioMejorVendedorMes, premiosCampania);
				setTotales(adicional);
				service.guardarAdicional(adicional);
				if (adicional!=null)//si en efecto hay un adicional calculado
					adicionales.add(adicional);
			}
		}
		
		//mostrarResultado(adicionales);
		
		request.setAttribute("adicionales", adicionales);
	}

	public ArrayList<Vendedor> setListVendedores(Integer[] ids)
	{
		ArrayList<Vendedor> seleccion=new ArrayList<Vendedor>();
		for (Integer id : ids)
			seleccion.add(service.getVendedor(id));
		return seleccion;
	}
	
	public Integer[] getParamVendedores(HttpServletRequest request){
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
			if (!registro.getCampanias().isEmpty())
			{
				for (Premio campania : registro.getCampanias())
				{
					System.out.println("\t\t Vendedor: " + campania.getPremiado());
					System.out.println("\t\t\t producto: " + campania.getProducto().getId() + " - " + campania.getProducto().getNombre());
					System.out.println("\t\t\t importe: " + campania.getImporte() + "$");
				}
			}
			else
				System.out.println("\t\t No se encontraron ventas para el período buscado");
		}
	}
	
		
	public Adicional calcularAdicionalVendedor(Date fechaHoy, GregorianCalendar desde, GregorianCalendar hasta, Vendedor vendedor, Premio vendedorMes, ArrayList<Premio> premiosCampania)
	{
		ArrayList<ComisionProducto> cProductos=calcularComisionProducto(vendedor, desde, hasta);
		ComisionVenta cVenta=calcularComisionVenta(vendedor, desde, hasta);
		ArrayList<Premio> campanias= new ArrayList<Premio>();
		Premio mejorVendedorMes=null;
		Adicional adicional;
		
		if (!premiosCampania.isEmpty())
		{
			for (Premio campania : premiosCampania)
			{
				if ((campania!=null) && (vendedor.getId()==campania.getPremiado().getId()))
					campanias.add(campania);
			}
		}
		
		if ((vendedorMes!=null) && (vendedorMes.getPremiado().getId() == vendedor.getId()))
			mejorVendedorMes=vendedorMes;
		
		adicional= new Adicional(fechaHoy, desde.getTime(), hasta.getTime(), vendedor, cVenta, cProductos, mejorVendedorMes, campanias);
		
		if (service.getAdicional(adicional)!=0)
			adicional.setId(service.getAdicional(adicional));
		
		return adicional;
	}
	
/*	public ArrayList<Premio> calcularPremiosCampaniaVendedor()
	{
		int cantidad=0, cuenta=0;
		ArrayList<Venta>ventas = new ArrayList<Venta>();
		ArrayList<Premio> Premios = new ArrayList<Premio>();
		Vendedor premiado= new Vendedor();
		PremioMonto monto=service.getMontoPremio(true);
		
		
	}*/
	
	public ArrayList<Adicional> getAdicionales()//FIXIT: función temporalmente acá hasta llegar a esta parte del refractor
	{
		ArrayList<Adicional> todos = service.getAdicionales();
		for (Adicional registro : todos)
			setTotales(registro);
		return todos;
	}
}

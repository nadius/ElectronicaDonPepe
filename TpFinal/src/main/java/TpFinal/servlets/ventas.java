package TpFinal.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TpFinal.dataccess.DataAccess;
import TpFinal.domain.Producto;
import TpFinal.domain.Vendedor;
import TpFinal.domain.Venta;

//@WebServlet("/ventas")
public class ventas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Vendedor vendedor = null;
	private ArrayList<Producto> listaProductos = null;
	//private ArrayList<String> listaNombreProductos = null;
	private ArrayList<Producto> listaComprados=null;
	private DataAccess dataAccess;
	private double total=0;
   
	public ventas() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String destino=request.getParameter("destino");
		if (destino.equals("Registrar"))
			request.getRequestDispatcher("/RegistrarVenta.jsp").forward(request, response);
		if (destino.equals("Consultar"))
			request.getRequestDispatcher("/ConsultarVenta.jsp").forward(request, response);*/
		String origen=request.getContextPath();
		if (origen.contains("/venta/alta"))
			request.getRequestDispatcher("/RegistrarVenta.jsp").forward(request, response);
		if (origen.contains("/venta/consulta"))
			request.getRequestDispatcher("/ConsultarVenta.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listaProductos= dataAccess.getProductos();
		vendedor = (Vendedor) request.getSession().getAttribute("vendedor");
		//setListaNombreProducto();
		
		String origen = request.getParameter("origen");
		String accion = request.getParameter("accion");
		
		if (origen.equals("RegistrarVenta"))
		{
			if (accion.equals("agregar"))
				Agregar(request.getParameter("id"));
			if (accion.equals("guardar"))
				Guardar(vendedor);
		}
		if (origen.equals("ConsultarVenta"))
			Consultar(request);
		
		request.setAttribute("productos", listaProductos);
		request.setAttribute("total", total);
		request.getRequestDispatcher("/" + origen + ".jsp").forward(request, response);
	}	

/*	public void setListaNombreProducto()
	{
		if (listaProductos==null)
			listaProductos= dataAccess.getProductos();
		for (Producto item : listaProductos)
			listaNombreProductos.add(item.getNombre());
	}
*/
	
	public void Agregar(String idProducto)
	{
		Integer index = Integer.parseInt(idProducto);
		if (listaComprados==null)
			listaComprados=new ArrayList<Producto>();
		listaComprados.add(listaProductos.get(index));
		total+=listaProductos.get(index).getPrecioUnitario();
	}
	
	public void Guardar(Vendedor vendedor)
	{
		Venta nuevaVenta=new Venta();
		nuevaVenta.setProductos(listaComprados);
		nuevaVenta.setImporte(total);
		nuevaVenta.setVendedor(vendedor);
		dataAccess.guardarVenta(nuevaVenta);
	}
	
	public void Consultar(HttpServletRequest request)
	{
		
	}
	
	//public boolean verificarDatos()

}

package tpFinal.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpFinal.domain.Campania;
import tpFinal.domain.Producto;
import tpFinal.security.Usuario;

//@WebServlet("/AdicionalesAdministrarCampanias")
public class AdicionalesAdministrarCampanias extends Adicionales {
	private static final long serialVersionUID = 1L;
	private ArrayList<Producto> listaProductos=new ArrayList<Producto>();
	private ArrayList<Campania> listaCampaniasExistentes=new ArrayList<Campania>();
	private ArrayList<Campania> listaCampaniasNoActivas=new ArrayList<Campania>();
       
    public AdicionalesAdministrarCampanias() {
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
		
		setListaCampaniasExistentes(service.getCampaniasActivas());
		setListaProductos(getProductosNoCampania());
		setListaCampaniasNoActivas(service.getCampaniasNoActivas());
		
		request.setAttribute("campaniasExistentes", listaCampaniasExistentes);
		request.setAttribute("productos", listaProductos);
		request.getRequestDispatcher("/WEB-INF/RegistrarCampania.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion=request.getParameter("accion");
		
		if (accion.equals("agregar"))
			agregarCampania(request.getParameter("idProducto"));
		
		if (accion.equals("eliminar"))
			eliminarCampania(request.getParameter("idCampania"));
		
		setListaCampaniasExistentes(service.getCampaniasActivas());
		setListaProductos(getProductosNoCampania());
		setListaCampaniasNoActivas(service.getCampaniasNoActivas());
		
		request.setAttribute("campaniasExistentes", listaCampaniasExistentes);
		request.setAttribute("productos", listaProductos);
		request.getRequestDispatcher("/WEB-INF/RegistrarCampania.jsp").forward(request, response);
	}
	
	public void agregarCampania(String id)
	{
		Integer idProducto = Integer.parseInt(id);
		Producto nuevaCampaniaProducto= service.getProducto(idProducto);
		Campania nuevaCampania = null;
		
		if (isCampaniaNoActiva(idProducto))//ya existe una campaña para ese producto pero no está activa?
		{
			nuevaCampania=service.getCampania(nuevaCampaniaProducto);
			nuevaCampania.setActivo(true);
		}
		else
			nuevaCampania = new Campania(nuevaCampaniaProducto);
		
		//listaCampaniasExistentes.add(nuevaCampania);//lo agrego a la lista de existentes
		//listaProductos.remove(nuevaCampaniaProducto);//lo saco de la lista de productos disponibles
		
		if (isCampaniaNoActiva(idProducto))
			service.actualizarCampania(nuevaCampania);
		else
			service.guardarCampania(nuevaCampania);
		//setListaProductos(getProductosNoCampania());
	}
	
	public void eliminarCampania(String id)
	{
		Integer idCampania = Integer.parseInt(id);
		Campania eliminarCampania = service.getCampania(idCampania);
		
		eliminarCampania.setActivo(false);//lo desactivo
		
		//borrarDeCampania(idCampania);//lo saco de la lista de existentes
		//listaProductos.add(eliminarCampania.getProducto());//lo agrego a la lista de productos disponibles
		
		service.actualizarCampania(eliminarCampania);
		//setListaProductos(getProductosNoCampania());
	}
	
	public ArrayList<Producto> getProductosNoCampania()
	{
		ArrayList<Producto> todosProductos = service.getProductos();
		ArrayList<Producto> seleccion = service.getProductos();
		
		if (getListaCampaniasExistentes()==null || getListaCampaniasExistentes().isEmpty())
			return todosProductos;
		
		for (Producto producto : todosProductos)
			for (Campania campania : listaCampaniasExistentes)
				if (producto.getId()==campania.getProducto().getId())
					borrarDeProductos(seleccion, producto.getId());
		return seleccion;
	}
	
	public void borrarDeProductos(ArrayList<Producto> lista, int idProducto)
	{
		int index;
		for (index=0; index<lista.size(); index++)
			if (lista.get(index).getId() == idProducto)
			{
				lista.remove(index);
				return;
			}
		return;
	}
	
	public void borrarDeCampania(int idCampania)
	{
		int index;
		for (index=0; index<listaCampaniasExistentes.size(); index++)
			if (listaProductos.get(index).getId() == idCampania)
			{
				listaCampaniasExistentes.remove(index);
				return;
			}
		return;
	}
	
	public boolean isCampaniaNoActiva(Integer idProducto)
	{
		for (Campania item : listaCampaniasNoActivas)
			if (item.getProducto().getId() == idProducto)
				return true;
		return false;
	}

	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ArrayList<Campania> getListaCampaniasExistentes() {
		return listaCampaniasExistentes;
	}

	public void setListaCampaniasExistentes(
			ArrayList<Campania> listaCampaniasExistentes) {
		this.listaCampaniasExistentes = listaCampaniasExistentes;
	}

	public ArrayList<Campania> getListaCampaniasNoActivas() {
		return listaCampaniasNoActivas;
	}

	public void setListaCampaniasNoActivas(
			ArrayList<Campania> listaCampaniasNoActivas) {
		this.listaCampaniasNoActivas = listaCampaniasNoActivas;
	}
}

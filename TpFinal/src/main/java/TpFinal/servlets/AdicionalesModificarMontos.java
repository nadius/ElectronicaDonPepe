package TpFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TpFinal.security.Usuario;
import TpFinal.domain.adicional.monto.*;

//@WebServlet("/AdicionalesModificarMontos")
public class AdicionalesModificarMontos extends Adicionales {
	private static final long serialVersionUID = 1L;
       
    public AdicionalesModificarMontos() {
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
		
		request.setAttribute("comisionVenta", service.getMontosVenta());
		request.setAttribute("comisionProducto", service.getMontosProducto());
		request.setAttribute("premios", service.getMontosPremio());
		request.setAttribute("productos", getProductosNoComisionProductoMonto());
		request.getRequestDispatcher("/WEB-INF/ModificarAdicionales.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int respuesta=0;
		float valor=0;
		
/*		String cVenta = request.getParameter("cVenta");
		String cProducto=request.getParameter("cProducto");
		String Premio = request.getParameter("Premio");
		
		
		if (cVenta!=null && cVenta.equals("true"))
			cantModificados = actualizarRegistroComisionVenta(recuperarValores(request, "cVenta"));
			
		if (cProducto!=null && cProducto.equals("true"))
			cantModificados = actualizarRegistroComisionProducto(recuperarValores(request, "cProducto"));
			
		if (Premio!=null && Premio.equals("true"))
			cantModificados = actualizarRegistroPremio(recuperarValores(request, "Premio"));
*/
		String accion=request.getParameter("accion");
		String tipo= request.getParameter("tipo");
		int id=0;
		
		if (accion.equals("set")){//seteo los valores que se necesitan en el formulario de actualización : registro y tipo del mismo
			request.setAttribute("tipo", request.getParameter("tipo"));
			id= Integer.parseInt(request.getParameter("id"));
			
			if (tipo.equals("comisionVenta")){
				request.setAttribute("registro", service.getMontoVenta(id));
			}
			
			if (tipo.equals("comisionProducto")){
				request.setAttribute("registro", service.getMontoProducto(id));
			}
			
			if(tipo.equals("premio")){
				request.setAttribute("registro", service.getMontoPremio(id));
			}
		}
		
		if (accion.equals("update")){//recupero el valor ingresado
			valor = Float.parseFloat(request.getParameter("valor"));
			id= Integer.parseInt(request.getParameter("id"));
			
			if (tipo.equals("comisionVenta")){
				respuesta = actualizarRegistroComisionVenta(id, valor);
			}
			
			if (tipo.equals("comisionProducto")){
				respuesta = actualizarRegistroComisionProducto(id, valor);
			}
			
			if(tipo.equals("premio")){
				respuesta = actualizarRegistroPremio(id, valor);
			}
			request.setAttribute("actualizados", respuesta);
		}
		
		if (accion.equals("producto")){//agrego un nuevo registro ComisionProductoMonto
			id= Integer.parseInt(request.getParameter("nuevoId"));//FIXME: NumberFormatException !?
			valor = Float.parseFloat(request.getParameter("nuevoMonto"));
			respuesta = agregarComisionProducto(id, valor);
			if (respuesta !=0){
				request.setAttribute("addError", "El registro " + respuesta + " tiene al producto seleccionado como Comision por Producto.");
			}
		}
		
		/*if (accion.equals("status")){
			id= Integer.parseInt(request.getParameter("id"));
			if (tipo.equals("comisionProducto")){
				respuesta = statusComisionProductoMonto(id);
				
			}
		}*/
			
		request.setAttribute("comisionVenta", service.getMontosVenta());
		request.setAttribute("comisionProducto", service.getMontosProducto());
		request.setAttribute("premios", service.getMontosPremio());
		request.setAttribute("productos", getProductosNoComisionProductoMonto());
		request.getRequestDispatcher("/WEB-INF/ModificarAdicionales.jsp").forward(request, response);
	}
	
/*	private int statusComisionProductoMonto(int id) {
		ComisionProductoMonto registro = service.getMontoProducto(id);
		if (registro.get)
	}*/

	@Override
	public void init(ServletConfig config) {
		super.init(config);
	}
	
	public float[] recuperarValores (HttpServletRequest request, String nombreCampo)
	{
		float[] valores;
		Integer cantidad=0, i, j=0;
		String[] valoresString;
		
		//recuepro la cantidad de montos existentes
		if (nombreCampo.equals("cVenta"))
			cantidad=service.getMontosVenta().size();
		
		if (nombreCampo.equals("cProducto"))
			cantidad=service.getMontosProducto().size();
		
		if (nombreCampo.equals("Premio"))
			cantidad=service.getMontosPremio().size();		
		
		valores = new float[cantidad];
		valoresString = new String[cantidad];
		
		for (i=1; i<=cantidad; i++)//recupero todos los parametros
			valoresString[i-1]=request.getParameter(nombreCampo+i.toString()+ "Valor");
		
		
		System.out.println("Montos a modificar en " + nombreCampo);
		//Verificacion 1 (string)
		System.out.print("\t String:");
		for (String valor : valoresString)
			System.out.print(" " + valor);
		System.out.print("\n");
		
		//paso los valores al array
		for (i=0; i<valoresString.length; i++)
			if (valoresString[i]!=null)
				valores[j++]=Float.parseFloat(valoresString[i]);
		
		//Verificacion 2 (float)
		System.out.print("\t float:");
		for (float valor : valores)
			System.out.print(" " + valor);
		System.out.print("\n");
		
		return valores;
	}
}

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
		request.setAttribute("comisionVenta", service.getMontosVenta());
		request.setAttribute("comisionProducto", service.getMontosProducto());
		request.setAttribute("premios", service.getMontosPremio());
		request.setAttribute("productos", service.getProductos());
		request.getRequestDispatcher("/WEB-INF/ModificarAdicionales.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cVenta = request.getParameter("cVenta");
		String cProducto=request.getParameter("cProducto");
		String Premio = request.getParameter("Premio");
		
		int cantModificados=0;
		
		if (cVenta!=null && cVenta.equals("true"))
			cantModificados = actualizarRegistroComisionVenta(recuperarValores(request, "cVenta"));
			
		if (cProducto!=null && cProducto.equals("true"))
			cantModificados = actualizarRegistroComisionProducto(recuperarValores(request, "cProducto"));
			
		if (Premio!=null && Premio.equals("true"))
			cantModificados = actualizarRegistroPremio(recuperarValores(request, "Premio"));
			
		request.setAttribute("comisionVenta", service.getMontosVenta());
		request.setAttribute("comisionProducto", service.getMontosProducto());
		request.setAttribute("premios", service.getMontosPremio());
		request.setAttribute("actualizados", cantModificados);
		request.getRequestDispatcher("/WEB-INF/ModificarAdicionales.jsp").forward(request, response);
	}
	
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

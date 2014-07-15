package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.dao.DataAccessException;

import tpFinal.dao.impl.ProductoDao;
import tpFinal.dao.impl.VentaDao;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;

public class VentaCalculation {
	private VentaDao dao;
	private ProductoDao productoDao;
	private ArrayList<Producto> listaComprados=null;
	private float total=0;

	
	public void setDao(VentaDao dao) {
		this.dao = dao;
	}
	public void setProductoDao(ProductoDao productoDao) {
		this.productoDao = productoDao;
	}


	public ArrayList<Producto> getListaTodosProductos() {
		return productoDao.getAll();
	}
	
	public ArrayList<Producto> getListaComprados() {
		return listaComprados;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void Agregar(int idProducto)
	{
		Producto producto = productoDao.get(idProducto);//TODO: Recibe el índice de la lista o el id? 
		if (listaComprados==null)
			listaComprados=new ArrayList<Producto>();
		listaComprados.add(producto);
		
		total+=producto.getPrecioUnitario();
	}
	
	public String calcular(Vendedor vendedor)//si el id de la venta es opcional
	{			
		Venta registro=new Venta(dao.getAll().size() +1, new Date(), listaComprados, total, vendedor);
				
		String mensaje=verificarDatos(registro);//si todo salio bien
		if (mensaje.equals(""))
		{
			dao.save(registro);
			resetCarrito();
		}
		
		return mensaje;
	}
	
	public String calcular(Vendedor vendedor, int id)//si el id de la venta es obligatorio
	{
		Venta registro;
		if (id==0)
			return "Por favor ingrese un id";
				
		registro=dao.get(id);
		
		if(registro!=null)//si existe una venta con ese id
			return "El id " + registro.getId() + " corresponde a la venta realizada el " 
					+ registro.getFecha().toString() + " por "
					+ registro.getVendedor().getNombre() + registro.getVendedor().getApellido()
					+ ". Por favor elija un nuevo id válido.";
		
		registro=new Venta(id, new Date(),listaComprados, total, vendedor);
	
		String mensaje=verificarDatos(registro);//si todo salio bien
		if (mensaje.equals(""))
		{
			try {
				dao.save(registro);
			} catch (DataAccessException e) {
				return "No se pudo grabar el registro. Por favor revise los parametros e intente nuevamente.";
			}
			resetCarrito();
		}
		
		return mensaje;
	}
	
	@SuppressWarnings("unused")
	private float calcularImporte()
	{
		float importe=0;
		for (Producto item : listaComprados)
			importe+=item.getPrecioUnitario();
		return importe;
	}
	
	private String verificarDatos(Venta venta)
	{
		if (venta.getFecha()==null)
			venta.setFecha(new Date());
		if (venta.getProductos()==null || venta.getProductos().isEmpty() || total==0)
			return "Atención: revisar lista de productos";
		if(venta.getVendedor()==null)
			return "Atención: revisar vendedor";
		return "";
	}
	
	public void resetCarrito()
	{
		listaComprados=null;//reseteo la lista de comprados
		total=0;//y el subtotal
	}

}

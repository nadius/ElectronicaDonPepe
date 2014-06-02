package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ProductoDao;
import tpFinal.dao.impl.VentaDao;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
//import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class VentaCalculation {
	//private VentaFindItem findItem;
	private VentaDao dao;
	private ProductoDao productoDao;
	private ArrayList<Producto> listaComprados=null;
	private float total=0;

	
	public void setDao(VentaDao dao) {
		this.dao = dao;
	}
/*
	public void setFindItem(VentaFindItem findItem) {
		this.findItem = findItem;
	}*/
	
	public void setProductoDao(ProductoDao productoDao) {
		this.productoDao = productoDao;
	}

	public ArrayList<Producto> getListaComprados() {
		return listaComprados;
	}
	
	public ArrayList<Producto> getListaTodosProductos() {
		return productoDao.getAll();
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
		Venta registro=new Venta(listaComprados, total, vendedor);
		registro.setFecha(new Date());
				
		String mensaje=verificarDatos(registro);//si todo salio bien
		if (mensaje.equals(""))
		{
			dao.save(registro);
			resetCarrito();
		}
		
		return mensaje;
	}
	
	public String calcular(Vendedor vendedor, String id)//si el id de la venta es obligatorio
	{
		Venta registro;
		if (id.equals(""))
			return "Por favor ingrese un id";
				
		registro=dao.get(Integer.parseInt(id));
		
		if(registro!=null)//si existe una venta con ese id
			return "El id " + registro.getId() + " corresponde a la venta realizada el " 
					+ registro.getFecha().toString() + " por "
					+ registro.getVendedor().getNombre() + registro.getVendedor().getApellido()
					+ ". Por favor elija un nuevo id o deje el campo en blanco";
		
		registro=new Venta(listaComprados, total, vendedor);
		registro.setFecha(new Date());
	
		String mensaje=verificarDatos(registro);//si todo salio bien
		if (mensaje.equals(""))
		{
			dao.save(registro);
			resetCarrito();
		}
		
		return mensaje;
	}
	
	public float calcularImporte()
	{
		float importe=0;
		for (Producto item : listaComprados)
			importe+=item.getPrecioUnitario();
		return importe;
	}
	
	public String verificarDatos(Venta venta)
	{
		String mensaje="";
		//if (venta.getId())
		//Venta verificacion=dataAccess.getVenta(id);
		if (venta.getFecha()==null)
			mensaje.concat(" fecha vacia");
		if (venta.getProductos()==null)
			mensaje.concat(" lista de productos vacia");
		if (venta.getImporte()==0)
			mensaje.concat(" importe vacio");
		if(venta.getVendedor()==null)
			mensaje.concat(" vendedor vacio");
		return mensaje;
	}
	
	public void resetCarrito()
	{
		listaComprados=null;//reseteo la lista de comprados
		total=0;//y el subtotal
	}

}

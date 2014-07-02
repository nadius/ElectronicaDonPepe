package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.CampaniaDao;
import tpFinal.dao.impl.ProductoDao;
import tpFinal.domain.Campania;
import tpFinal.domain.Producto;
import tpFinal.service.findItem.findItemImpl.CampaniaFindItem;

public class CampaniaCalculation {
	private CampaniaDao dao;
	private CampaniaFindItem findItem;
	private ProductoDao productoDao;
	
	public void setDao(CampaniaDao dao) {
		this.dao = dao;
	}

	public void setFindItem(CampaniaFindItem findItem) {
		this.findItem = findItem;
	}

	public void setProductoDao(ProductoDao productoDao) {
		this.productoDao = productoDao;
	}

	public void agregar(int idProducto)
	{
		Producto producto= productoDao.get(idProducto);
		Campania registro = null;
		
		if (isCampaniaNoActiva(idProducto))//ya existe una campaña para ese producto pero no está activa?
		{
			registro=findItem.findByProducto(idProducto);
			registro.setActivo(true);
			dao.update(registro);
		}
		else{
			registro = new Campania(producto);
			dao.merge(registro);
		}
	}
	
	public void eliminar(int idCampania)
	{
		Campania registro = dao.get(idCampania);
		registro.setActivo(false);//lo desactivo
		dao.update(registro);
	}
	
	private boolean isCampaniaNoActiva(int idProducto){
		Campania buscada=findItem.findByProducto(idProducto);
		if (buscada!=null && buscada.isActivo()==false)
			return true;
		return false;
	}
	
	public ArrayList<Producto> getProductosNoCampania()
	{
		ArrayList<Producto> todosProductos = productoDao.getAll();
		ArrayList<Producto> seleccion = productoDao.getAll();
		
		if (dao.getAll()==null || dao.getAll().isEmpty())//si no hay Campanias guardadas en la base de datos
			return todosProductos;
		
		for (Producto producto : todosProductos)
			for (Campania campania : dao.getAll())
				if (producto.getId()==campania.getProducto().getId())
					borrarDeProductos(seleccion, producto.getId());
		return seleccion;
	}
	
	private void borrarDeProductos(ArrayList<Producto> lista, int idProducto)
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
		
	public ArrayList<Campania> getAll(){
		return dao.getAll();
	}
	
}

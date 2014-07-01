package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.ProductoDao;
import tpFinal.domain.Producto;

public class ProductoFindItem{
	private ProductoDao dao;
	
	public void setDao(ProductoDao dao) {
		this.dao = dao;
	}

	public int findIdByObject(Producto object) {
		ArrayList<Producto> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Producto item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Producto id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}
}

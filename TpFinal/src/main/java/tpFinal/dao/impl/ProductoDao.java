package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Producto;

public class ProductoDao implements daoInterface<Producto> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Producto get(int id) {
		return (Producto) dataAccess.get(Producto.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Producto> getAll() {
		return (ArrayList<Producto>) dataAccess.getAll(Producto.class);
	}

	@Override
	public void save(Producto registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Producto registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Producto registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Producto registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Producto registro) {
		dataAccess.delete(registro);
	}
}

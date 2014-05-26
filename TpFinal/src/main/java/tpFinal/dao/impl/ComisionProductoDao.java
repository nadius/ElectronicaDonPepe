package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.ComisionProducto;

public class ComisionProductoDao implements
		daoInterface<ComisionProducto> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public ComisionProducto get(int id) {
		return (ComisionProducto) dataAccess.get(ComisionProducto.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ComisionProducto> getAll() {
		return (ArrayList<ComisionProducto>) dataAccess
				.getAll(ComisionProducto.class);
	}

	@Override
	public void save(ComisionProducto registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(ComisionProducto registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(ComisionProducto registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(ComisionProducto registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(ComisionProducto registro) {
		dataAccess.delete(registro);
	}
}

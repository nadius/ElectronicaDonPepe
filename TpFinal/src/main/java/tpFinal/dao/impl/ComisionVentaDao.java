package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.ComisionVenta;

public class ComisionVentaDao implements
		daoInterface<ComisionVenta> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public ComisionVenta get(int id) {
		return (ComisionVenta) dataAccess.get(ComisionVenta.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ComisionVenta> getAll() {
		return (ArrayList<ComisionVenta>) dataAccess
				.getAll(ComisionVenta.class);
	}

	@Override
	public void save(ComisionVenta registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(ComisionVenta registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(ComisionVenta registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(ComisionVenta registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(ComisionVenta registro) {
		dataAccess.delete(registro);
	}
}

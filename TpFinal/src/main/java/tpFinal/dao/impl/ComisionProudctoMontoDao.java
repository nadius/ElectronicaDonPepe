package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.domain.adicional.monto.ComisionProductoMonto;
import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;

public class ComisionProudctoMontoDao implements daoInterface<ComisionProductoMonto> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public ComisionProductoMonto get(int id) {
		return (ComisionProductoMonto) dataAccess.get(ComisionProductoMonto.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ComisionProductoMonto> getAll() {
		return (ArrayList<ComisionProductoMonto>) dataAccess
				.getAll(ComisionProductoMonto.class);
	}

	@Override
	public void save(ComisionProductoMonto registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(ComisionProductoMonto registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(ComisionProductoMonto registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(ComisionProductoMonto registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(ComisionProductoMonto registro) {
		dataAccess.delete(registro);
	}
}

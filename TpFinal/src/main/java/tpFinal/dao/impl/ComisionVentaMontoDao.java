package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.adicional.monto.ComisionVentaMonto;

public class ComisionVentaMontoDao implements daoInterface<ComisionVentaMonto> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public ComisionVentaMonto get(int id) {
		return (ComisionVentaMonto) dataAccess.get(ComisionVentaMonto.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ComisionVentaMonto> getAll() {
		return (ArrayList<ComisionVentaMonto>) dataAccess.getAll(ComisionVentaMonto.class);
	}

	@Override
	public void save(ComisionVentaMonto registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(ComisionVentaMonto registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(ComisionVentaMonto registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(ComisionVentaMonto registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(ComisionVentaMonto registro) {
		dataAccess.delete(registro);
	}
}

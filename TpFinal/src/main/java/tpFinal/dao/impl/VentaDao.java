package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Venta;

public class VentaDao implements daoInterface<Venta> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Venta get(int id) {
		return (Venta) dataAccess.get(Venta.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Venta> getAll() {
		return (ArrayList<Venta>) dataAccess.getAll(Venta.class);
	}

	@Override
	public void save(Venta registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Venta registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Venta registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Venta registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Venta registro) {
		dataAccess.delete(registro);
	}
}

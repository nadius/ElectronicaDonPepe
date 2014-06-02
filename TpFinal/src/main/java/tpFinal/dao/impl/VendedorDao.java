package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Vendedor;

public class VendedorDao implements daoInterface<Vendedor> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Vendedor get(int id) {
		return (Vendedor) dataAccess.get(Vendedor.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Vendedor> getAll() {
		return (ArrayList<Vendedor>) dataAccess.getAll(Vendedor.class);
	}

	@Override
	public void save(Vendedor registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Vendedor registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Vendedor registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Vendedor registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Vendedor registro) {
		dataAccess.delete(registro);
	}
}

package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Adicional;

public class AdicionalDao implements daoInterface<Adicional> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Adicional get(int id) {
		return (Adicional) dataAccess.get(Adicional.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Adicional> getAll() {
		return (ArrayList<Adicional>) dataAccess.getAll(Adicional.class);
	}

	@Override
	public void save(Adicional registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Adicional registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Adicional registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Adicional registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Adicional registro) {
		dataAccess.delete(registro);
	}
}

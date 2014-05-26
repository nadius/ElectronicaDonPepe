package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Premio;

public class PremioDao implements daoInterface<Premio> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Premio get(int id) {
		return (Premio) dataAccess.get(Premio.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Premio> getAll() {
		return (ArrayList<Premio>) dataAccess.getAll(Premio.class);
	}

	@Override
	public void save(Premio registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Premio registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Premio registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Premio registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Premio registro) {
		dataAccess.delete(registro);
	}
}

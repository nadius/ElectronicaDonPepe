package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Campania;

public class CampaniaDao implements daoInterface<Campania> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Campania get(int id) {
		return (Campania) dataAccess.get(Campania.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Campania> getAll() {
		return (ArrayList<Campania>) dataAccess.getAll(Campania.class);
	}

	@Override
	public void save(Campania registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Campania registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Campania registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Campania registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Campania registro) {
		dataAccess.delete(registro);
	}
}

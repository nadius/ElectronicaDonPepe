package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.security.RolUsuario;

public class RolUsuarioDao implements  daoInterface<RolUsuario> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public RolUsuario get(int id) {
		return (RolUsuario) dataAccess.get(RolUsuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RolUsuario> getAll() {
		return (ArrayList<RolUsuario>) dataAccess.getAll(RolUsuario.class);
	}

	@Override
	public void save(RolUsuario registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(RolUsuario registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(RolUsuario registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(RolUsuario registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(RolUsuario registro) {
		dataAccess.delete(registro);
	}
}

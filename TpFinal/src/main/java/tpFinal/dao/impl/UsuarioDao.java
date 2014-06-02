package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.security.Usuario;

public class UsuarioDao implements daoInterface<Usuario> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Usuario get(int id) {
		return (Usuario) dataAccess.get(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Usuario> getAll() {
		return (ArrayList<Usuario>) dataAccess.getAll(Usuario.class);
	}

	@Override
	public void save(Usuario registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Usuario registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Usuario registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Usuario registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Usuario registro) {
		dataAccess.delete(registro);
	}
}

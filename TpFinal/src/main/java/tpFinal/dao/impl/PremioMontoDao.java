package tpFinal.dao.impl;

import java.util.ArrayList;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.adicional.monto.PremioMonto;

public class PremioMontoDao implements daoInterface<PremioMonto> {
	private DataAccessInterface dataAccess;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public PremioMonto get(int id) {
		return (PremioMonto) dataAccess.get(PremioMonto.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PremioMonto> getAll() {
		return (ArrayList<PremioMonto>) dataAccess.getAll(PremioMonto.class);
	}
	
	public PremioMonto getMontoPremioCampania()
	{
		ArrayList<PremioMonto> todos= getAll();
		for (PremioMonto item : todos)
			if(item.isCampania())
				return item;
		return null;
	}
	
	public PremioMonto getMontoPremioMes()
	{
		ArrayList<PremioMonto> todos= getAll();
		for (PremioMonto item : todos)
			if(!item.isCampania())
				return item;
		return null;
	}

	@Override
	public void save(PremioMonto registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(PremioMonto registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(PremioMonto registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(PremioMonto registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(PremioMonto registro) {
		dataAccess.delete(registro);
	}
}

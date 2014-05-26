package tpFinal.dao;

import java.util.List;

public interface daoInterface<E> {
	public E get(int id);
	public List<E> getAll();
	public void save(E registro);
	public void merge(E registro);
	public void saveOrUpdate(E registro);
	public void update(E registro);
	public void delete (E registro);
}

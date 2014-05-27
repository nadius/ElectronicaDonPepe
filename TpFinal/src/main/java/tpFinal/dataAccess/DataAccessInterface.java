package tpFinal.dataAccess;

import java.util.List;

public interface DataAccessInterface {
//Funciones comunes a todas las entidades
	
	@SuppressWarnings("rawtypes")
	public Object get(Class entityClass, int id);
	@SuppressWarnings("rawtypes")
	public List getAll(Class entityClass);
	@SuppressWarnings("rawtypes")
	public List find(Object param);
	public void save(Object registro);
	public void merge(Object registro);
	public void saveOrUpdate(Object registro);
	public void update(Object registro);
	public void delete (Object registro);
	@SuppressWarnings("rawtypes")
	public Object createQuery(Class entityClass);
}
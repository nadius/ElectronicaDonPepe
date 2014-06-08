package tpFinal.dao;

import java.util.List;

public interface daoInterface<E> {
	/**
	 * Obtener un registro especifico
	 * @param id id del registro
	 * @return el registro en cuestion, o null si no lo encuentra
	 */
	public E get(int id);
	
	/**
	 * Obtener todos los registros
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> getAll();
	
	/**
	 * Persiste un registro en la base de datos
	 * @param registro el que se quiere persistir
	 */
	public void save(E registro);
	
	/**
	 * Une un registro en la base de datos
	 * @param registro el que se quiere persistir
	 */
	public void merge(E registro);
	
	/**
	 * Persiste o actualiza un registro en la base de datos
	 * @param registro el que se quiere persistir
	 */
	public void saveOrUpdate(E registro);
	
	/**
	 * Actualiza un registro en la base de datos
	 * @param registro el que se quiere actualizar
	 */
	public void update(E registro);
	
	/**
	 * Borra un registro en la base de datos
	 * @param registro el que se quiere borrar
	 */
	public void delete (E registro);
}

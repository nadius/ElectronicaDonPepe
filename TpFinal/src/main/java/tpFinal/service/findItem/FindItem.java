package tpFinal.service.findItem;

import java.util.Date;
import java.util.List;

public interface FindItem<E> {
	/**
	 * Verifica si un objecto ya está guardado en la base de datos
	 * @param E el objeto a buscar en la base de datos
	 * @return el id del objeto, o 0 si no fue encontrado  
	 */
	public int findIdByObject(E item);
	
	/**
	 * Busca registros segun su fecha de creacion
	 * @param creationDate fecha de creacion
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> findByCreationDate(Date creationDate);
	
	/**
	 * Busca registros segun un periodo de tiempo especificado
	 * @param from desde cuando se busca
	 * @param to hasta cuando se busca
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> findBySpecificDates(Date from, Date to);
}

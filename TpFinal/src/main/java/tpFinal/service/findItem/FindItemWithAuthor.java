package tpFinal.service.findItem;

import java.util.Date;
import java.util.List;

public interface FindItemWithAuthor<E> {
	/**
	 * Busca registros por Vendedor
	 * @param creatorId id del Vendedor
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> findByCreatorId(int creatorId);
	
	/**
	 * Busca registros por fecha de creacion e id del Vendedor
	 * @param creatorId id del Vendedor
	 * @param creationDate fecha de creacion
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> findByCreationDateCreatorId(int creatorId, Date creationDate);
	
	
	/**
	 * Busca registros por un periodo de tiempo especificado y el id del Vendedor
	 * @param creatorId id del Vendedor
	 * @param from desde cuando se busca
	 * @param to hasta cuando se busca
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> findBySpecificDatesCreatorId(int creatorId, Date from, Date to);
}

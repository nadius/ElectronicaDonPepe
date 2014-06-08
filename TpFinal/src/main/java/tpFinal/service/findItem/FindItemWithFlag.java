package tpFinal.service.findItem;

import java.util.List;

public interface FindItemWithFlag<E> {
	/**
	 * Busca registros cuya bandera tenga el mismo valor que la especificada
	 * @param flagValue el valor que se usará bara buscar registros
	 * @return Un ArrayList con los registros hallados
	 */
	public List<E> getAllByFlag(boolean flagValue);
}

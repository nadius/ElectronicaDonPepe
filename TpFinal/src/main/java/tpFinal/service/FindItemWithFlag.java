package tpFinal.service;

import java.util.List;

public interface FindItemWithFlag<E> {
	public List<E> getAllByFlag(boolean flagValue);
}

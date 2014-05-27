package tpFinal.service.findItem;

import java.util.List;

public interface FindItemWithFlag<E> {
	public List<E> getAllByFlag(boolean flagValue);
}

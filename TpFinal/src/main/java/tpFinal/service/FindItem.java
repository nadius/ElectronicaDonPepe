package tpFinal.service;

import java.util.Date;
import java.util.List;

public interface FindItem<E> {
	public int findIdByObject(E item);
	public List<E> findByCreationDate(Date creationDate);
	public List<E> findBySpecificDates(Date from, Date to);
}

package tpFinal.service;

import java.util.Date;
import java.util.List;

public interface FindItemWithAuthor<E> {
	public List<E> findByCreatorId(int creatorId);
	public List<E> findByCreationDateCreatorId(int creatorId, Date creationDate);
	public List<E> findBySpecificDatesCreatorId(int creatorId, Date from, Date to);
}

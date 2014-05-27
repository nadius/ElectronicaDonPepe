package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.PremioDao;
import tpFinal.domain.Premio;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithAuthor;

public class PremioFindItem implements FindItem<Premio>, FindItemWithAuthor<Premio>{
	private PremioDao dao;
	
	public void setPremioDao(PremioDao dao) {
		this.dao = dao;
	}

	@Override
	public int findIdByObject(Premio object) {
		ArrayList<Premio> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Premio item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Premio id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Premio> findByCreationDate(Date creationDate) {
		ArrayList<Premio> all = dao.getAll();
		ArrayList<Premio> answer = new ArrayList<Premio>();
		
		if (all.isEmpty())
			return null;
		
		for (Premio item : all)
		{
			if(item.getFechaCreacion().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Premio> findBySpecificDates(Date from, Date to) {
		ArrayList<Premio> all = dao.getAll();
		ArrayList<Premio> answer = new ArrayList<Premio>();
		
		if (all.isEmpty())
			return null;
		
		for (Premio item : all)
		{
			if(item.getFechaDesde().compareTo(to)==0 && item.getFechaHasta().compareTo(to)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Premio> findByCreatorId(int creatorId) {
		ArrayList<Premio> all = dao.getAll();
		ArrayList<Premio> answer = new ArrayList<Premio>();
		
		if (all.isEmpty())
			return null;
		
		for (Premio item : all)
		{
			if(item.getPremiado().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Premio> findByCreationDateCreatorId(int creatorId, Date creationDate) {
		ArrayList<Premio> all = findByCreationDate(creationDate);
		ArrayList<Premio> answer = new ArrayList<Premio>();
		
		if (all.isEmpty())
			return null;
		
		for (Premio item : all)
		{
			if(item.getPremiado().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Premio> findBySpecificDatesCreatorId(int creatorId, Date from, Date to) {
		ArrayList<Premio> all = findBySpecificDates(from, to);
		ArrayList<Premio> answer = new ArrayList<Premio>();
		
		if (all.isEmpty())
			return null;
		
		for (Premio item : all)
		{
			if(item.getPremiado().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}
}

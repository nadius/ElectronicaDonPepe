package tpFinal.service.findItem.findItemImpl;

import java.util.Date;
import java.util.ArrayList;

import tpFinal.domain.Adicional;
import tpFinal.dao.impl.AdicionalDao;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithAuthor;

public class AdicionalFindItem implements FindItem<Adicional>, FindItemWithAuthor<Adicional> {
	private AdicionalDao dao;
	
	public void setDao(AdicionalDao dao) {
		this.dao = dao;
	}

	@Override
	public int findIdByObject(Adicional object) {
		ArrayList<Adicional> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Adicional item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Adicional id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Adicional> findByCreationDate(Date creationDate) {
		ArrayList<Adicional> all = dao.getAll();
		ArrayList<Adicional> answer = new ArrayList<Adicional>();
		
		if (all.isEmpty())
			return null;
		
		for (Adicional item : all)
		{
			if(item.getFechaCreacion().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Adicional> findBySpecificDates(Date from, Date to) {
		ArrayList<Adicional> all = dao.getAll();
		ArrayList<Adicional> answer = new ArrayList<Adicional>();
		
		if (all.isEmpty())
			return null;
		
		for (Adicional item : all)
		{
			if(item.getFechaDesde().compareTo(from)>=0 && item.getFechaHasta().compareTo(to)<=0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Adicional> findByCreatorId(int creatorId) {
		ArrayList<Adicional> all = dao.getAll();
		ArrayList<Adicional> answer = new ArrayList<Adicional>();
		
		if (all.isEmpty())
			return null;
		
		for (Adicional item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Adicional> findByCreationDateCreatorId(int creatorId, Date creationDate) {
		ArrayList<Adicional> all = findByCreationDate(creationDate);
		ArrayList<Adicional> answer = new ArrayList<Adicional>();
		
		if (all.isEmpty())
			return null;
		
		for (Adicional item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Adicional> findBySpecificDatesCreatorId(int creatorId,	Date from, Date to) {
		ArrayList<Adicional> all = findBySpecificDates(from, to);
		ArrayList<Adicional> answer = new ArrayList<Adicional>();
		
		if (all.isEmpty())
			return null;
		
		for (Adicional item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}
}

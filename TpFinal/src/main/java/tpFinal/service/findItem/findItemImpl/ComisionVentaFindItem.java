package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionVentaDao;
import tpFinal.domain.ComisionVenta;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithAuthor;

public class ComisionVentaFindItem implements FindItem<ComisionVenta>, FindItemWithAuthor<ComisionVenta>{
	private ComisionVentaDao dao;
	
	public void setComisionVentaDao(ComisionVentaDao dao) {
		this.dao = dao;
	}

	@Override
	public int findIdByObject(ComisionVenta object) {
		ArrayList<ComisionVenta> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (ComisionVenta item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro ComisionVenta id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<ComisionVenta> findByCreationDate(Date creationDate) {
		ArrayList<ComisionVenta> all = dao.getAll();
		ArrayList<ComisionVenta> answer = new ArrayList<ComisionVenta>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionVenta item : all)
		{
			if(item.getFechaCreacion().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionVenta> findBySpecificDates(Date from, Date to) {
		ArrayList<ComisionVenta> all = dao.getAll();
		ArrayList<ComisionVenta> answer = new ArrayList<ComisionVenta>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionVenta item : all)
		{
			if(item.getFechaDesde().compareTo(to)==0 && item.getFechaHasta().compareTo(to)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionVenta> findByCreatorId(int creatorId) {
		ArrayList<ComisionVenta> all = dao.getAll();
		ArrayList<ComisionVenta> answer = new ArrayList<ComisionVenta>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionVenta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionVenta> findByCreationDateCreatorId(int creatorId, Date creationDate) {
		ArrayList<ComisionVenta> all = findByCreationDate(creationDate);
		ArrayList<ComisionVenta> answer = new ArrayList<ComisionVenta>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionVenta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionVenta> findBySpecificDatesCreatorId(int creatorId,	Date from, Date to) {
		ArrayList<ComisionVenta> all = findBySpecificDates(from, to);
		ArrayList<ComisionVenta> answer = new ArrayList<ComisionVenta>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionVenta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}
}

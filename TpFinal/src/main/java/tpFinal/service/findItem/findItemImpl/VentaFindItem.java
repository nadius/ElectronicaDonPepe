package tpFinal.service.findItem.findItemImpl;

import java.util.Date;
import java.util.ArrayList;

import tpFinal.domain.Venta;
import tpFinal.dao.impl.VentaDao;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithAuthor;

public class VentaFindItem implements FindItem<Venta>, FindItemWithAuthor<Venta> {
	private VentaDao dao;
	
	public void setDao(VentaDao dao) {
		this.dao = dao;
	}

	@Override
	public int findIdByObject(Venta object) {
		ArrayList<Venta> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Venta item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Venta id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Venta> findByCreationDate(Date creationDate) {
		ArrayList<Venta> all = dao.getAll();
		ArrayList<Venta> answer = new ArrayList<Venta>();
		
		if (all.isEmpty())
			return null;
		
		for (Venta item : all)
		{
			if(item.getFecha().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Venta> findBySpecificDates(Date from, Date to) {
		ArrayList<Venta> all = dao.getAll();
		ArrayList<Venta> answer = new ArrayList<Venta>();
		
		if (all.isEmpty())
			return null;
		
		for (Venta item : all)
		{
			if(item.getFecha().compareTo(to)==0 && item.getFecha().compareTo(to)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Venta> findByCreatorId(int creatorId) {
		ArrayList<Venta> all = dao.getAll();
		ArrayList<Venta> answer = new ArrayList<Venta>();
		
		if (all.isEmpty())
			return null;
		
		for (Venta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Venta> findByCreationDateCreatorId(int creatorId, Date creationDate) {
		ArrayList<Venta> all = findByCreationDate(creationDate);
		ArrayList<Venta> answer = new ArrayList<Venta>();
		
		if (all.isEmpty())
			return null;
		
		for (Venta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Venta> findBySpecificDatesCreatorId(int creatorId,	Date from, Date to) {
		ArrayList<Venta> all = findBySpecificDates(from, to);
		ArrayList<Venta> answer = new ArrayList<Venta>();
		
		if (all.isEmpty())
			return null;
		
		for (Venta item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}
}

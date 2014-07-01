package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.ComisionProductoDao;
import tpFinal.domain.ComisionProducto;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithAuthor;

public class ComisionProductoFindItem implements FindItem<ComisionProducto>, FindItemWithAuthor<ComisionProducto>{
	private ComisionProductoDao dao;
	
	public void setDao(ComisionProductoDao dao) {
		this.dao = dao;
	}

	@Override
	public int findIdByObject(ComisionProducto object) {
		ArrayList<ComisionProducto> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (ComisionProducto item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro ComisionProducto id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<ComisionProducto> findByCreationDate(Date creationDate) {
		ArrayList<ComisionProducto> all = dao.getAll();
		ArrayList<ComisionProducto> answer = new ArrayList<ComisionProducto>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionProducto item : all)
		{
			if(item.getFechaCreacion().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionProducto> findBySpecificDates(Date from, Date to) {
		ArrayList<ComisionProducto> all = dao.getAll();
		ArrayList<ComisionProducto> answer = new ArrayList<ComisionProducto>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionProducto item : all)
		{
			if(item.getFechaDesde().compareTo(to)==0 && item.getFechaHasta().compareTo(to)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionProducto> findByCreatorId(int creatorId) {
		ArrayList<ComisionProducto> all = dao.getAll();
		ArrayList<ComisionProducto> answer = new ArrayList<ComisionProducto>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionProducto item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionProducto> findByCreationDateCreatorId(int creatorId, Date creationDate) {
		ArrayList<ComisionProducto> all = findByCreationDate(creationDate);
		ArrayList<ComisionProducto> answer = new ArrayList<ComisionProducto>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionProducto item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<ComisionProducto> findBySpecificDatesCreatorId(int creatorId,	Date from, Date to) {
		ArrayList<ComisionProducto> all = findBySpecificDates(from, to);
		ArrayList<ComisionProducto> answer = new ArrayList<ComisionProducto>();
		
		if (all.isEmpty())
			return null;
		
		for (ComisionProducto item : all)
		{
			if(item.getVendedor().getId()==creatorId)
			{
				answer.add(item);
			}
		}
		return answer;
	}
}

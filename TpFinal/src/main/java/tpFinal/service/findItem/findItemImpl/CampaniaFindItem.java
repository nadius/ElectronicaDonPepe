package tpFinal.service.findItem.findItemImpl;

import java.util.Date;
import java.util.ArrayList;

import tpFinal.dao.impl.CampaniaDao;
import tpFinal.domain.Campania;
import tpFinal.service.findItem.FindItem;
import tpFinal.service.findItem.FindItemWithFlag;

public class CampaniaFindItem implements FindItem<Campania>, FindItemWithFlag<Campania>{

private CampaniaDao dao;
	
	public void setDao(CampaniaDao dao) {
		this.dao = dao;
	}

	@Override
	public ArrayList<Campania> getAllByFlag(boolean flagValue) {
		ArrayList<Campania> all= dao.getAll();
		ArrayList<Campania> answer= new ArrayList<Campania>();
		for(Campania item : all)
		{
			if (item.isActivo()==flagValue)
				answer.add(item);
		}
		return answer;
	}

	@Override
	public int findIdByObject(Campania object) {
		ArrayList<Campania> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Campania item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Campania id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	public Campania findByProducto(Integer idProducto)
	{
		ArrayList<Campania> all= dao.getAll();
		for (Campania item : all)
			if (item.getProducto().getId() == idProducto)
				return item;
		return null;
	}
	
	@Override
	public ArrayList<Campania> findByCreationDate(Date creationDate) {
		ArrayList<Campania> all = dao.getAll();
		ArrayList<Campania> answer = new ArrayList<Campania>();
		
		if (all.isEmpty())
			return null;
		
		for (Campania item : all)
		{
			if(item.getFechaCreacion().compareTo(creationDate)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

	@Override
	public ArrayList<Campania> findBySpecificDates(Date from, Date to) {
		ArrayList<Campania> all = dao.getAll();
		ArrayList<Campania> answer = new ArrayList<Campania>();
		
		if (all.isEmpty())
			return null;
		
		for (Campania item : all)
		{
			if(item.getFechaCreacion().compareTo(from)==0 && item.getFechaCreacion().compareTo(to)==0)
			{
				answer.add(item);
			}
		}
		return answer;
	}

}

package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;
import java.util.List;

import tpFinal.dao.impl.VendedorDao;
import tpFinal.domain.Vendedor;
import tpFinal.service.findItem.FindItemWithFlag;

public class VendedorFindItem implements FindItemWithFlag<Vendedor>{
	private VendedorDao dao;
	
	public void setVendedorDao(VendedorDao dao){
		this.dao = dao;
	}

	public int findIdByObject(Vendedor object) {
		ArrayList<Vendedor> all = dao.getAll();
		if (all.isEmpty())
			return 0;
		
		for (Vendedor item : all)
		{
			if(item.equals(object))
			{
				System.out.println("Encontrado registro Vendedor id="+item.getId());//Salida para control
				return item.getId();
			}
		}
		return 0;
	}

	@Override
	public List<Vendedor> getAllByFlag(boolean flagValue) {
		ArrayList<Vendedor> all= dao.getAll();
		ArrayList<Vendedor> answer= new ArrayList<Vendedor>();
		for(Vendedor item : all)
		{
			if (item.isActivo())
				answer.add(item);
		}
		return answer;
	}
}

package tpFinal.service.findItem.findItemImpl;

import java.util.ArrayList;
import tpFinal.dao.impl.UsuarioDao;
import tpFinal.security.Usuario;
import tpFinal.service.findItem.FindItemWithFlag;

public class UsuarioFindItem implements FindItemWithFlag<Usuario>{
	private UsuarioDao dao;
	
	public void setDao(UsuarioDao dao){
		this.dao=dao;
	}
		
	@Override
	public ArrayList<Usuario> getAllByFlag(boolean flagValue) {
		ArrayList<Usuario> all=dao.getAll();
		ArrayList<Usuario> answer=new ArrayList<Usuario>();
		
		for (Usuario item : all)
		{
			if (item.isActivo()==flagValue)
				answer.add(item);
		}
		return answer;
	}

	public int findIdByObject(Usuario object) {
		ArrayList<Usuario> all=dao.getAll();
		for (Usuario item : all){
			if (item.equals(object))
				return item.getId();
		}
		return 0;
	}
	
	public Usuario findByUsername(String username)
	{
		ArrayList<Usuario> todos = getAllByFlag(true);
		for (Usuario item : todos)
			if (username.equals(item.getUsername()))
				return item;
		return null;
	}

}

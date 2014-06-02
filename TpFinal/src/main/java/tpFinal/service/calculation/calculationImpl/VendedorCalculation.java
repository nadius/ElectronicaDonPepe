package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.VendedorDao;
import tpFinal.domain.Vendedor;
import tpFinal.service.findItem.findItemImpl.VendedorFindItem;

public class VendedorCalculation {
	private VendedorFindItem findItem;
	private VendedorDao dao;
	private UsuarioCalculation usuarioCalculation;
	
	public void setFindItem(VendedorFindItem findItem){
		this.findItem=findItem;
	}
	
	public void setDao(VendedorDao dao){
		this.dao=dao;
	}
	
	public void setUsuarioCalculation(UsuarioCalculation usuarioCalculation) {
		this.usuarioCalculation = usuarioCalculation;
	}

	public String nuevo(String nombre, String apellido)
	{
		Vendedor nuevo=new Vendedor(nombre, apellido);
				
		String mensaje=verificar(nuevo);
		
		if(!mensaje.equals(""))			
			return mensaje;
		
		dao.save(nuevo);
		usuarioCalculation.nuevo(nombre+apellido, "vendedor", 2, nuevo.getId());//al crear un vendedor también crea un usuario
		
		return "";
	}
	
	public String cambiarEstado(int id)
	{
		Vendedor registro=dao.get(id);
		
		if (registro==null)//si no se encontro nada
			return "No se pudo encontrar el vendedor.";
		
		if (registro.isActivo())//si el usuario esta activo
			registro.setActivo(false);
		else//si el usuario no esta activo
			registro.setActivo(true);
		
		dao.update(registro);
		return "";
	}
	
	public String verificar(Vendedor registro)
	{
		String mensaje="";
		if (registro.getNombre().equals(""));
			mensaje.concat(" nombre vacío");
		if (registro.getApellido().equals(""))
			mensaje.concat(" apellido vacío");
		
		if (findItem.findIdByObject(registro)!=0)
			mensaje="Ya existe un registro para " + registro.getNombre() + " " + registro.getApellido();
		
		return mensaje;
	}
	
	public ArrayList<Vendedor> getAll(){
		return dao.getAll();
	}
}

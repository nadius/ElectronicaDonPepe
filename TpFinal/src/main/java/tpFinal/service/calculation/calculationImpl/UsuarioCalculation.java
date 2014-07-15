package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.RolUsuarioDao;
import tpFinal.dao.impl.UsuarioDao;
import tpFinal.dao.impl.VendedorDao;
import tpFinal.security.Usuario;
import tpFinal.service.findItem.findItemImpl.UsuarioFindItem;

public class UsuarioCalculation {
	private UsuarioFindItem findItem;
	private UsuarioDao dao;
	private RolUsuarioDao rolDao;
	private VendedorDao vendedorDao;
	
	public void setFindItem(UsuarioFindItem findItem){
		this.findItem=findItem;
	}
	
	public void setDao(UsuarioDao dao){
		this.dao=dao;
	}
	
	public void setRolDao(RolUsuarioDao rolDao){
		this.rolDao=rolDao;
	}
	
	public void setVendedorDao(VendedorDao vendedorDao){
		this.vendedorDao=vendedorDao;
	}
	
	public boolean login(String username, String password)
	{
		ArrayList<Usuario> todos = findItem.getAllByFlag(true);
		for (Usuario item : todos)
			if (password.equals(item.getPassword()) && username.equals(item.getUsername()))
				return true;
		return false;
	}
	
	public boolean verificarRol(Usuario cuenta, int idRol)
	{
		if (cuenta.getRol().getId()!=idRol)
			return false;
		return true;
	}
	
	public String nuevo(String username, String password, int idRol, int idVendedor)
	{	
		Usuario registro=new Usuario(username, password, rolDao.get(idRol), null);//todavía no sé si es vendedor
		String mensaje="";
				
		if (idRol == 2){
			return "El alta de vendedores se realiza desde la seccion correspondiente";
		}
		
		mensaje=verificar(registro);
		
		if(mensaje.equals("")){	
			dao.save(registro);
			mensaje = String.valueOf(registro.getId());
		}
		
		return mensaje;
	}
	
	public String nuevoVendedor(String username, String password, int idRol, int idVendedor)
	{	
		Usuario registro=new Usuario(username, password, rolDao.get(idRol), null);//todavía no sé si es vendedor
		String mensaje="";
		
		
		if (idRol==2){
			if (vendedorDao.get(idVendedor) == null){ // si el vendedor elegido no existe
				throw new NullPointerException("vendedor");
			}
			registro.setVendedor(vendedorDao.get(idVendedor));
		}
		
		mensaje=verificar(registro);
		
		if(mensaje.equals("")){	
			dao.save(registro);
			mensaje = String.valueOf(registro.getId());
		}
		
		return mensaje;
	}
	
	public String cambiarEstado(int id)
	{
		Usuario registro=dao.get(id);
		
		if (registro==null)//si no se encontro nada
			return "No se pudo encontrar el usuario.";
		
		if (registro.isActivo())//si el usuario esta activo
			registro.setActivo(false);
		else//si el usuario no esta activo
			registro.setActivo(true);
		
		dao.update(registro);
		return String.valueOf(registro.getId());
	}
	
	public String cambiarEstadoUsuarioVendedor(int idVendedor)
	{
		Usuario registro=findItem.findByIdVendedor(idVendedor);
		return cambiarEstado(registro.getId());
	}
	
	public String verificar(Usuario registro)
	{
		String mensaje="";
		if (registro.getUsername().equals(""))
			mensaje = mensaje + "nombre de usuario vacío ";
		if (registro.getPassword().equals(""))
			mensaje = mensaje + "contraseña vacía ";
		if (registro.getRol()==null)
			mensaje = mensaje + "rol de usuario vacío o no válido ";		
		if (findItem.findIdByObject(registro)!=0)
			mensaje = mensaje + "Ya existe un registro para " + registro.getUsername();
		
		return mensaje;
	}
	
	public ArrayList<Usuario> getAll(){
		return dao.getAll();
	}
}

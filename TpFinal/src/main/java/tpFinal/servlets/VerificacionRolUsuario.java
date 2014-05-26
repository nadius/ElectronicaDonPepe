package tpFinal.servlets;

import tpFinal.security.Usuario;

abstract class VerificacionRolUsuario
{
	//id de los roles de usuario
	protected int rrhh=1;
	protected int vendedor=2;
	protected int administrador=3;
	
	protected String errorPage="/error";//para la redireccion a la pagina de error
	
	public abstract boolean verificarRol(Usuario cuenta);
}
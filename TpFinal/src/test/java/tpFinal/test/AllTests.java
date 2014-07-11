package tpFinal.test;

import java.util.ArrayList;

import org.hibernate.Session;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tpFinal.dataAccess.HIbernateUtil;
import tpFinal.domain.*;
import tpFinal.security.*;

@RunWith(Suite.class)
@SuiteClasses({ CalcularAdicionalesTest.class, UsuarioCalculationTest.class,
		VendedorCalculationTest.class, VentaCalculationTest.class })
public class AllTests {
	private static Session session;
	
	ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();
	ArrayList<RolUsuario> roles = new ArrayList<RolUsuario>();
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	ArrayList<Producto> productos = new ArrayList<Producto>();
	ArrayList<Venta> ventas = new ArrayList<Venta>();

	
	static String stringQueryVendedores = "INSERT INTO 'vendedor' ('id', 'activo', 'apellido', 'nombre') VALUES" + 
													"(1, b'1', 'Diaz', 'Andrea')," + 
													"(2, b'1', 'Noble', 'Donna')," + 
													"(3, b'1', 'Dent', 'Arthur')," +
													"(4, b'0', 'Perez', 'Juan');";
	
	
	static String stringQueryRolesUsuario = "INSERT INTO 'RolUsuario' ('id', 'nombre') VALUES" +
													"(1, 'RRHH')," + 
													"(2, 'Vendedor')," +
													"(3, 'Administrador');" ;
	
	static String stringQueryUsuarios = "INSERT INTO 'Usuario' ('id', 'activo', 'password', 'username', 'rol_id', 'vendedor_id') VALUES" +
												"(1, b'1', 'rrhh', 'RRHH', 1, NULL)," + 
												"(2, b'1', 'vendedor', 'ArthurDent', 2, 3)," +
												"(3, b'1', 'vendedor', 'DonnaNoble', 2, 2)," +
												"(4, b'1', 'admin', 'Admin', 3, NULL)," +
												"(5, b'1', 'vendedor', 'AndreaDiaz', 2, 1)," +
												"(6, b'0', 'vendedor', 'JuanPerez', 2, 4);";
	
	static String stringQueryProducto = "INSERT INTO 'producto' ('id', 'nombre', 'precioUnitario') VALUES" +
			"(1, 'Doctor Who: Complete Series 4 (2008)', 13)," +
			"(2, 'Doctor Who: Complete Specials (2009)', 11.25)," +
			"(3, 'Doctor Who: Complete Series 5 (2010)', 12.5)," +
			"(4, 'Doctor Who: Complete Series 6 (2011)', 13.75)," +
			"(5, 'Doctor Who: Complete Series 7 (2012)', 33.94)," +
			"(6, 'Doctor Who: The Ace Adventures (1987)', 10.99)," +
			"(7, 'Wallander (Branagh): Series 3', 8)," +
			"(8, 'Wallander (Branagh): Series 1 & 2 Box Set', 11.33)," +
			"(9, 'Wallander (Lassgard): Original Films 1-6', 10)," +
			"(10, 'Arne Dahl: Complete Series 1', 14.5)," +
			"(11, 'Rachmaninov: The Ampico Piano Recordings', 9.99)," +
			"(12, 'Turner in his Time', 18.2)," +
			"(13, 'Inspector Morse: The Complete Series 1-12', 39.99)," +
			"(14, 'Karajan: The Complete EMI Recordings Vol. 1', 109.01)," +
			"(15, 'Karajan: The Complete EMI Recordings Vol. 2', 119.48);" ;
	
	static String stringQueryVenta = "INSERT INTO 'venta' ('id', 'fecha', 'importe', 'vendedor_id') VALUES" + 
			"(1, '2013-03-01 20:40:16', 166.79, 2)," + 
			"(2, '2013-03-08 23:55:49', 167.51, 3)," +
			"(3, '2013-03-09 14:14:37', 24.74, 3)," +
			"(4, '2013-03-30 12:48:49', 73.97, 3)," +
			"(5, '2013-03-31 23:09:54', 32.5, 1)," +
			"(6, '2013-11-24 15:45:14', 13, 3)," +
			"(7, '2013-11-24 16:18:25', 23, 3)," +
			"(8, '2013-11-24 16:50:44', 26.2, 1)," +
			"(9, '2013-12-09 08:17:22', 109.01, 1)," +
			"(10, '2014-02-05 09:23:11', 51.9, 3)," +
			"(11, '2014-06-09 03:20:45', 22.24, 1)," +
			"(12, '2014-06-26 05:58:30', 137.68, 1)," +
			"(13, '2014-06-29 18:36:48', 22.99, 1)," +
			"(14, '2014-06-29 18:39:39', 109.01, 1)," +
			"(15, '2014-07-17 19:43:27', 218.02, 1);";
	
	static String stringQueryVentaProductos = "INSERT INTO 'venta_producto' ('Venta_id', 'productos_id') VALUES" +
			"(1, 4)," +
			"(1, 9)," +
			"(1, 5)," +
			"(1, 14)," +
			"(2, 14)," +
			"(2, 8)," +
			"(2, 10)," +
			"(2, 9)," +
			"(2, 2)," +
			"(2, 8)," +
			"(3, 4)," +
			"(3, 6)," +
			"(4, 11)," +
			"(4, 1)," +
			"(4, 6)," +
			"(4, 13)," +
			"(5, 7)," +
			"(5, 9)," +
			"(5, 10)," +
			"(6, 1)," +
			"(7, 1)," +
			"(7, 9)," +
			"(8, 7)," +
			"(8, 12)," +
			"(9, 14)," +
			"(10, 2),"+
			"(10, 8)," +
			"(10, 11)," +
			"(10, 7)," +
			"(10, 8)," +
			"(11, 2)," +
			"(11, 6)," +
			"(12, 12)," +
			"(12, 15)," +
			"(13, 1)," +
			"(13, 11)," +
			"(14, 14)," +
			"(15, 14)," +
			"(15, 14);";
	

	@BeforeClass
	public static void setUp(){
		int cantidadActualizados = 0;
		openConnection();
/*		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryVendedores + " END;").executeUpdate();
		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryRolesUsuario + " END;").executeUpdate();
		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryUsuarios + " END;").executeUpdate();
		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryProducto + " END;").executeUpdate();
		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryVenta + " END;").executeUpdate();
		cantidadActualizados += session.createSQLQuery("BEGIN " + stringQueryVentaProductos + " END;").executeUpdate();*/
		
		cantidadActualizados += session.createSQLQuery(stringQueryVendedores).executeUpdate();
		cantidadActualizados += session.createSQLQuery(stringQueryRolesUsuario).executeUpdate();
		cantidadActualizados += session.createSQLQuery(stringQueryUsuarios).executeUpdate();
		cantidadActualizados += session.createSQLQuery(stringQueryProducto).executeUpdate();
		cantidadActualizados += session.createSQLQuery(stringQueryVenta).executeUpdate();
		cantidadActualizados += session.createSQLQuery(stringQueryVentaProductos).executeUpdate();
		closeConnection();
	}

	private static void openConnection() {
		session = HIbernateUtil.getSession();
	}

	private static void closeConnection() {
		session.close();
	}
	
/*	private void addVendedores(){
		vendedores.add(new Vendedor("Andrea", "Diaz"));
		vendedores.add(new Vendedor("Donna", "Noble"));
		vendedores.add(new Vendedor("Arthur", "Dent"));
		vendedores.add(new Vendedor("Juan", "Perez"));
	}*/
	
	private void addRolesUsuario(){
		roles.add(new RolUsuario(1, "RRHH"));
		roles.add(new RolUsuario(2, "Vendedor"));
		roles.add(new RolUsuario(3, "Administrador"));
	}
	
	private void addUsuarios(){
		usuarios.add(new Usuario("RRHH", "rrhh", roles.get(0), null));
		usuarios.add(new Usuario("ArthurDent", "vendedor", roles.get(1), vendedores.get(2)));
	}
	
	private void addProductos(){
		
	}
	
	private void addVentas(){
		
	}

}

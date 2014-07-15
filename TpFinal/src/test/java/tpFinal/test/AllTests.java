package tpFinal.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalcularAdicionalesTest.class, 
				ModificarMontosAdicionalesTest.class,
				CampaniaCalculationTest.class,
				VentaCalculationTest.class,
				VendedorCalculationTest.class,
				UsuarioCalculationTest.class,})
public class AllTests {
}

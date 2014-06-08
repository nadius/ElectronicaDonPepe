package tpFinal.service.calculation.calculationImpl;

import java.util.ArrayList;

import tpFinal.dao.impl.PremioDao;
import tpFinal.dao.impl.PremioMontoDao;
import tpFinal.domain.Premio;
import tpFinal.domain.adicional.monto.PremioMonto;

public class PremioMontoCalculation {
	private PremioMontoDao montoDao;
	private PremioDao registroPremioDao;
	private ArrayList<Premio> premios;
	
	public void setMontoDao(PremioMontoDao montoDao) {
		this.montoDao = montoDao;
	}

	public void setRegistroPremioDao(PremioDao registroPremioDao) {
		this.registroPremioDao = registroPremioDao;
	}


	public void actualizar(float[] valores) {
		PremioMonto registro;
		premios=registroPremioDao.getAll();
		
		/* //Verificar
		System.out.print("Premios \t Actualizar: ");
		for (float item : valores)
		System.out.print(item.toString() + " ");
		System.out.print("\n");*/
		
		for (int i=0; i<valores.length; i++){
			registro=montoDao.get(i+1);
			
			if (isChanged(registro,valores[i])){
				actualizarRegistrosPremio(registro,valores[i]);
				actualizarRegistroMonto(registro,valores[i]);
			}
		}
	}
	
	private boolean isChanged(PremioMonto registro, float valor){
		if (registro.getMonto()!=valor){
			return true;
		}
		return false;
	}
	
	private void actualizarRegistrosPremio(PremioMonto registro, float montoNuevo){
		for (Premio premio : premios)//actualizo todos los importes de los premios
		{
			if(premio.getImporte()==registro.getMonto())
			{
				premio.setImporte(montoNuevo);
				registroPremioDao.update(premio);
			}
		}
	}
	
	private void actualizarRegistroMonto(PremioMonto registro, float montoNuevo){
		registro.setMonto(montoNuevo);
		montoDao.update(registro);
	}
}
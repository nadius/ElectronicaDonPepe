package tpFinal.service;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.AdicionalDao;
import tpFinal.domain.Adicional;
import tpFinal.domain.Vendedor;
import tpFinal.service.calculation.calculationImpl.AdicionalCalculation;
import tpFinal.service.findItem.findItemImpl.AdicionalFindItem;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class AdicionalService {
	private AdicionalFindItem findItem;
	private AdicionalDao dao;
	private AdicionalCalculation calculation;
	
	
	public void setFindItem(AdicionalFindItem findItem) {
		this.findItem = findItem;
	}
	
	public void setDao(AdicionalDao dao) {
		this.dao = dao;
	}
	
	public void setCalculation(AdicionalCalculation calculation) {
		this.calculation = calculation;
	}

	public int findIdByObject(Adicional object) {
		return findItem.findIdByObject(object);
	}
	
	public ArrayList<Adicional> findByCreationDate(Date creationDate) {
		return findItem.findByCreationDate(creationDate);
	}
	
	public ArrayList<Adicional> findBySpecificDates(Date from, Date to) {
		return findItem.findBySpecificDates(from, to);
	}
	
	public ArrayList<Adicional> findByCreatorId(int creatorId) {
		return findItem.findByCreatorId(creatorId);
	}
	
	public ArrayList<Adicional> findByCreationDateCreatorId(int creatorId,
			Date creationDate) {
		return findItem.findByCreationDateCreatorId(creatorId, creationDate);
	}
	
	public ArrayList<Adicional> findBySpecificDatesCreatorId(int creatorId,
			Date from, Date to) {
		return findItem.findBySpecificDatesCreatorId(creatorId, from, to);
	}
	
	public Adicional get(int id) {
		return dao.get(id);
	}
	
	public ArrayList<Adicional> getAll() {
		ArrayList<Adicional> todos=dao.getAll();
		for (Adicional registro : todos){
			calculation.setTotales(registro);
		}
		return todos;
	}
	
	public ArrayList<Vendedor> getVendedoresActivos() {
		return calculation.getVendedoresActivos();
	}

	public ArrayList<Adicional> calcular(Date fechaDesde, Date fechaHasta) {
		return calculation.calcularTodos(fechaDesde, fechaHasta);
	}

	public String showResult(Adicional object) {
		return calculation.showResult(object);
	}
	
	public void addVendedor(int id){
		calculation.getVendedores().add(getVendedorActivo(id));
	}
	
	public Vendedor getVendedorActivo(int idVendedor){
		for(Vendedor vendedor : calculation.getVendedoresActivos()){
			if (vendedor.getId()==idVendedor)
				return vendedor;
		}
		return null;
	}
	
	public void resetParams(){
		calculation.resetParams();
	}
	
	public void setTotalAll(){
		for(Adicional adicional : dao.getAll()){
			calculation.setTotales(adicional);
			dao.update(adicional);
		}
	}
}

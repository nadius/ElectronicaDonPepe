package tpFinal.service;

import java.util.ArrayList;
import java.util.Date;

import tpFinal.dao.impl.VentaDao;
import tpFinal.domain.Producto;
import tpFinal.domain.Vendedor;
import tpFinal.domain.Venta;
import tpFinal.service.calculation.calculationImpl.VentaCalculation;
import tpFinal.service.findItem.findItemImpl.VentaFindItem;

public class VentaService {
	private VentaFindItem findItem;
	private VentaDao dao;
	private VentaCalculation calculation;
	
	public void setFindItem(VentaFindItem findItem) {
		this.findItem = findItem;
	}
	public void setDao(VentaDao dao) {
		this.dao = dao;
	}
	public void setCalculation(VentaCalculation calculation) {
		this.calculation = calculation;
	}
	
	public int findIdByObject(Venta object) {
		return findItem.findIdByObject(object);
	}
	public ArrayList<Venta> findByCreationDate(Date creationDate) {
		return findItem.findByCreationDate(creationDate);
	}
	public ArrayList<Venta> findBySpecificDates(Date from, Date to) {
		return findItem.findBySpecificDates(from, to);
	}
	public ArrayList<Venta> findByCreatorId(int creatorId) {
		return findItem.findByCreatorId(creatorId);
	}
	public ArrayList<Venta> findByCreationDateCreatorId(int creatorId,
			Date creationDate) {
		return findItem.findByCreationDateCreatorId(creatorId, creationDate);
	}
	public ArrayList<Venta> findBySpecificDatesCreatorId(int creatorId,
			Date from, Date to) {
		return findItem.findBySpecificDatesCreatorId(creatorId, from, to);
	}
	public Venta get(int id) {
		return dao.get(id);
	}
	public ArrayList<Venta> getAll() {
		return dao.getAll();
	}
	public void Agregar(int idProducto) {
		calculation.Agregar(idProducto);
	}
	public void resetCarrito() {
		calculation.resetCarrito();
	}
	public ArrayList<Producto> getListaTodosProductos() {
		return calculation.getListaTodosProductos();
	}
	public ArrayList<Producto> getListaComprados() {
		return calculation.getListaComprados();
	}
	public float getTotal() {
		return calculation.getTotal();
	}
	public String calcular(Vendedor vendedor) {
		return calculation.calcular(vendedor);
	}
	public String calcular(Vendedor vendedor, String id) {
		return calculation.calcular(vendedor, id);
	}
}

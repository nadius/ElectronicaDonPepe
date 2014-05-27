package tpFinal.dao.impl;

import java.util.ArrayList;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import tpFinal.dao.daoInterface;
import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Adicional;

public class AdicionalDao implements daoInterface<Adicional> {
	private DataAccessInterface dataAccess = null;

	public void setDataAccess(DataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}

	@Override
	public Adicional get(int id) {
		return (Adicional) dataAccess.get(Adicional.class, id);
	}
	
	public int findDuplicate(Adicional registro)//solo para busqueda de duplicados
	{
		if (registro.getId()!=0)//si el registro tiene un id es porque ya está en la base de datos
			return 0;

		DetachedCriteria criterio = this.createCriteria(registro);
		return this.find(criterio).get(0).getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Adicional> getAll() {
		return (ArrayList<Adicional>) dataAccess.getAll(Adicional.class);
	}

	@Override
	public void save(Adicional registro) {
		dataAccess.save(registro);
	}

	@Override
	public void merge(Adicional registro) {
		dataAccess.merge(registro);
	}

	@Override
	public void saveOrUpdate(Adicional registro) {
		dataAccess.saveOrUpdate(registro);
	}

	@Override
	public void update(Adicional registro) {
		dataAccess.update(registro);
	}

	@Override
	public void delete(Adicional registro) {
		dataAccess.delete(registro);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Adicional> find(DetachedCriteria criteria)//TODO: testear que funcione
	{
		return (ArrayList<Adicional>) dataAccess.find(criteria);
	}
	
	public DetachedCriteria createCriteria(Adicional registro)
	{
		DetachedCriteria criterio = (DetachedCriteria) dataAccess.createQuery(Adicional.class);
				
		if (registro.getFechaCreacion()!=null)
			criterio.add(Restrictions.eq("fechaCreacion", registro.getFechaCreacion()));
		
		if (registro.getFechaDesde()!=null)
			criterio.add(Restrictions.eq("fechaDesde", registro.getFechaDesde()));
		
		if (registro.getFechaHasta()!=null)
			criterio.add(Restrictions.eq("fechaHasta", registro.getFechaHasta()));
		
		if (registro.getVendedor()!=null)
			criterio.add(Restrictions.eq("vendedor.id", registro.getVendedor().getId()));
		
		if (registro.getComisionVentas()!=null)
			criterio.add(Restrictions.eq("comisionVenta_id", registro.getComisionVentas().getId()));
		
		/*if (!registro.getComisionesProducto().isEmpty())//TODO: terminar
			for (ComisionProducto item : registro.getComisionesProducto())
				criterio.add(Restrictions.eq("comisionProducto.id", item.getId()));*/
		
		if (registro.getMejorVendedorMes()!=null)
			criterio.add(Restrictions.eq("mejorVendedorMes", registro.getMejorVendedorMes()));
		
		/*if (!registro.getCampanias().isEmpty())//TODO: terminar
			for (Premio item : registro.getCampanias())
				criterio.add(Restrictions.eq("Producto.id", item.getId()));*/
		
		if (registro.getTotalAdicionales()!=0)
			criterio.add(Restrictions.eq("totalAdicionales", registro.getTotalAdicionales()));
		
		return criterio;
	}
}

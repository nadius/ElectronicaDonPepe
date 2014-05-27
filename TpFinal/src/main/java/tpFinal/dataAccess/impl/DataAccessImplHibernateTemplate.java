package tpFinal.dataAccess.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.domain.Adicional;

import java.util.List;

public class DataAccessImplHibernateTemplate implements DataAccessInterface {

	private HibernateTemplate hibernateTemplate = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object get(Class entityClass, int id) {
		return this.hibernateTemplate.get(entityClass, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAll(Class entityClass) {
		return (List) this.hibernateTemplate.loadAll(entityClass);
	}

	@Override
	public void save(Object registro) {
		this.hibernateTemplate.save(registro);
	}

	@Override
	public void merge(Object registro) {
		this.hibernateTemplate.merge(registro);
	}

	@Override
	public void saveOrUpdate(Object registro) {
		this.hibernateTemplate.saveOrUpdate(registro);
	}

	@Override
	public void update(Object registro) {
		this.hibernateTemplate.update(registro);
	}

	@Override
	public void delete(Object registro) {
		this.hibernateTemplate.delete(registro);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DetachedCriteria createQuery(Class entityClass) {
		return DetachedCriteria.forClass(Adicional.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List find(Object param) {
		DetachedCriteria criteria = (DetachedCriteria) param;
		return hibernateTemplate.findByCriteria(criteria);
	}
}

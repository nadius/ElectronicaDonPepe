package tpFinal.dataAccess.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import tpFinal.dataAccess.DataAccessInterface;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void merge(Object registro) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(Object registro) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object registro) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object registro) {
		// TODO Auto-generated method stub

	}

}

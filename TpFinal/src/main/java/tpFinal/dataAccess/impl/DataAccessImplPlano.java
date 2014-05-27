package tpFinal.dataAccess.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tpFinal.dataAccess.DataAccessInterface;
import tpFinal.dataAccess.HIbernateUtil;

public class DataAccessImplPlano implements DataAccessInterface {
	private static Session session;

	private void openConnection() {
		session = HIbernateUtil.getSession();
	}

	private void closeConnection() {
		session.close();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object get(Class entityClass, int id) {
		Object rta = null;
		openConnection();
		List seleccion = session.createQuery(
				"from " + entityClass.getName() + " item where item.id =" + id)
				.list();
		if (seleccion.size() > 0)
			rta = seleccion.get(0);

		closeConnection();
		return rta;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAll(Class entityClass) {
		openConnection();
		List seleccion = session.createQuery(
				"from " + entityClass.getName() + " item").list();
		closeConnection();
		return seleccion;
	}

	@Override
	public void save(Object item) {
		openConnection();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		closeConnection();
	}

	@Override
	public void merge(Object item) {
		openConnection();
		Transaction tx = session.beginTransaction();
		session.merge(item);
		tx.commit();
		closeConnection();
	}

	@Override
	public void saveOrUpdate(Object item) {
		openConnection();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(item);
		tx.commit();
		closeConnection();
	}

	@Override
	public void update(Object item) {
		openConnection();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		closeConnection();
	}

	@Override
	public void delete(Object item) {
		openConnection();
		Transaction tx = session.beginTransaction();
		session.delete(item);
		tx.commit();
		closeConnection();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String createQuery(Class entityClass) {
		return "from " + entityClass.getName() + " item";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List find(Object query) {
		String consulta=(String) query;
		openConnection();
		List seleccion = session.createQuery(consulta).list();
		closeConnection();
		return seleccion;
	}

}

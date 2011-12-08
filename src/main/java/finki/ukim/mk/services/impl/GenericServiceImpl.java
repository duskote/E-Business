package finki.ukim.mk.services.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import finki.ukim.mk.services.GenericService;

public class GenericServiceImpl<T, ID extends Serializable> implements
		GenericService<T, ID> {

	@Inject
	private Session session;

	public Session getSession() {
		return session;
	}

	@SuppressWarnings("unchecked")
	public T findById(Class<T> clazz, ID id) {
		return (T) getSession().load(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> clazz) {
		return session.createCriteria(clazz).list();
	}

	public void save(Object o) {
		getSession().saveOrUpdate(o);
	}

	public void delete(Object o) {
		getSession().delete(o);
	}

	public void refresh(Object object) {
		getSession().refresh(object);
	}
}
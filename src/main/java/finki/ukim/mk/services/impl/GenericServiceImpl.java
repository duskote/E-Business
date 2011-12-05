package finki.ukim.mk.services.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import finki.ukim.mk.services.GenericService;

public class GenericServiceImpl<T, ID extends Serializable> implements
		GenericService<T, ID> {

	@Inject
	private Session session;

	@SuppressWarnings("unused")
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Session getSession() {
		return session;
	}

	@SuppressWarnings("unchecked")
	public <V> V findById(Class<V> clazz, ID id) {
		return (V) getSession().load(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <V> List<V> findAll(Class<V> clazz) {
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
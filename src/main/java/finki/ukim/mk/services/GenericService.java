package finki.ukim.mk.services;

import java.io.Serializable;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

public interface GenericService<T, ID extends Serializable> {
	T findById(Class<T> clazz, ID id);

	List<T> findAll(Class<T> clazz);

	void refresh(Object object);

	@CommitAfter
	void save(Object o);

	@CommitAfter
	void delete(Object o);
}

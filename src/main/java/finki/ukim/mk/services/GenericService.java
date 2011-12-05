package finki.ukim.mk.services;

import java.io.Serializable;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

public interface GenericService<T, ID extends Serializable> {
	<V> V findById(Class<V> clazz, ID id);

	<V> List<V> findAll(Class<V> clazz);

	void refresh(Object object);

	@CommitAfter
	void save(Object o);

	@CommitAfter
	void delete(Object o);
}

package swt6.spring.worklog.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
	T       findById(ID id);
  List<T> findAll();
  void    insert(T entity);
  T       merge(T entity);
  // void delete(T entity);
}

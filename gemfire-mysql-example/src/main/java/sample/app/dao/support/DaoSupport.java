package sample.app.dao.support;

import java.io.Serializable;

/**
 * The DaoSupport interface is a contract for Data Access Objects (DAO) specifying data access, persistence and querying
 * operations on application, business domain objects (entities).
 *
 * @author John Blum
 * @see java.io.Serializable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface DaoSupport<T, ID extends Serializable> {

  int count();

  boolean exists(ID id);

  T findBy(ID id);

  Iterable<T> findAll();

  boolean remove(T bean);

  T save(T bean);

}

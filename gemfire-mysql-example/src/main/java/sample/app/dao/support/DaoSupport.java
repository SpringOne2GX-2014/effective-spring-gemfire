package sample.app.dao.support;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

/**
 * The DaoSupport interface is a contract for Data Access Objects (DAO) specifying data access, persistence and querying
 * operations on application, business domain objects (entities).
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see org.springframework.data.repository.CrudRepository
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface DaoSupport<T, ID extends Serializable> extends CrudRepository<T, ID> {

  long count();

  boolean exists(ID id);

  T findBy(ID id);

  Iterable<T> findAll();

  boolean remove(T bean);

  <S extends T> S save(S bean);

}

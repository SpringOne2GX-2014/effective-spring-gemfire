package sample.app.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The DaoSupportAdapter abstract class is a contract for Data Access Objects (DAO) specifying data access, persistence
 * and querying operations on application, business domain objects (entities).
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see org.spring.data.gemfire.app.dao.support.DaoSupport
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class DaoSupportAdapter<T, ID extends Serializable> implements DaoSupport<T, ID> {

  protected static final String NOT_IMPLEMENTED = "Not Implemented!";

  @Override
  public long count() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public boolean exists(final ID id) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public T findBy(final ID id) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public Iterable<T> findAll() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public boolean remove(final T bean) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public <S extends T> S save(final S bean) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  @Override
  public final <S extends T> Iterable<S> save(final Iterable<S> entities) {
    for (S entity : entities) {
      save(entity);
    }

    return entities;
  }

  @Override
  public final T findOne(final ID id) {
    return findBy(id);
  }

  @Override
  public Iterable<T> findAll(final Iterable<ID> ids) {
    List<T> results = new ArrayList<T>();

    for (ID id : ids) {
      results.add(findBy(id));
    }

    return results;
  }

  @Override
  public final void delete(final ID id) {
    remove(findBy(id));
  }

  @Override
  public final void delete(final T entity) {
    remove(entity);
  }

  @Override
  public final void delete(final Iterable<? extends T> entities) {
    for (T entity : entities) {
      remove(entity);
    }
  }

  @Override
  public final void deleteAll() {
    for (T entity : findAll()) {
      remove(entity);
    }
  }

}

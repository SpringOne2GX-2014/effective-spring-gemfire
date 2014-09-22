package sample.app.dao.support;

import java.io.Serializable;

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
  public int count() {
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
  public T save(final T bean) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

}

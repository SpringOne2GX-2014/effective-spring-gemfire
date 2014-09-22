package sample.app.dao;

import sample.app.dao.support.DaoSupport;
import sample.app.domain.Gemstone;

/**
 * The GemstoneDao interface is a Data Access Object definition for accessing and persisting data on Gemstones in both
 * GemFire and MySQL data sources.
 *
 * @author John Blum
 * @see sample.app.domain.Gemstone
 * @see sample.app.dao.support.DaoSupport
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface GemstoneDao extends DaoSupport<Gemstone, Long> {

  void init();

}

package sample.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import sample.app.dao.GemstoneDao;
import sample.app.domain.Gemstone;

/**
 * The GemstoneService class is a Service object implementing business logic and rules, along with data services
 * on Gemstone domain objects.
 *
 * @author John Blum
 * @see sample.app.domain.Gemstone
 * @see sample.app.dao.GemstoneDao
 * @see org.springframework.stereotype.Service
 * @see org.springframework.transaction.annotation.Transactional
 * @since 1.0.0
 */
@Service("gemsService")
@SuppressWarnings("unused")
public class GemstoneService {

  protected static final List<String> APPROVED_GEMSTONES = new ArrayList<String>(Arrays.asList(
    "ALEXANDRITE", "AQUAMARINE", "DIAMOND", "OPAL", "PEARL", "RUBY", "SAPPHIRE", "SPINEL", "TOPAZ"));

  @Resource(name = "databaseGemstoneDao")
  private GemstoneDao databaseGemstoneDao;

  @Resource(name = "gemfireGemstoneDao")
  private GemstoneDao gemfireGemstoneDao;

  public GemstoneService() {
  }

  public GemstoneService(final GemstoneDao databaseGemstoneDao, final GemstoneDao gemfireGemstoneDao) {
    Assert.notNull(databaseGemstoneDao, "The 'Database' GemstoneDao reference must not be null!");
    Assert.notNull(gemfireGemstoneDao, "The 'GemFire' GemstoneDao reference must not be null!");
    this.databaseGemstoneDao = databaseGemstoneDao;
    this.gemfireGemstoneDao = gemfireGemstoneDao;
  }

  protected GemstoneDao getDatabaseGemstoneDao() {
    Assert.state(databaseGemstoneDao != null, "A reference to the 'Database' GemstoneDao was not properly configured!");
    return databaseGemstoneDao;
  }

  protected GemstoneDao getGemFireGemstoneDao() {
    Assert.state(gemfireGemstoneDao != null , "A reference to the 'GemFire' GemstoneDao was not properly configured!");
    return gemfireGemstoneDao;
  }

  @PostConstruct
  public void init() {
    getDatabaseGemstoneDao();
    getGemFireGemstoneDao();
    System.out.printf("%1$s initialized!%n", getClass().getSimpleName());
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public int countFromDatabase() {
    return getDatabaseGemstoneDao().count();
  }

  // NOTE GemFire does not allow Region.size() within a Transactional context even when the Transaction is read-only.
  //@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  //@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Throwable.class)
  public int countFromGemFire() {
    return getGemFireGemstoneDao().count();
  }

  @Transactional(readOnly = true)
  public Iterable<Gemstone> listFromDatabase() {
    return getDatabaseGemstoneDao().findAll();
  }

  // NOTE GemFire does not allow Region.getAll(Region.keySet()) within a Transactional context even when
  // the Transaction is read-only.
  //@Transactional(readOnly = true)
  public Iterable<Gemstone> listFromGemFire() {
    return getGemFireGemstoneDao().findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Gemstone loadFromDatabase(final Long id) {
    return getDatabaseGemstoneDao().findBy(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Gemstone loadFromGemFire(final Long key) {
    return getGemFireGemstoneDao().findBy(key);
  }

  @Transactional(readOnly = false)
  public Gemstone save(Gemstone gemstone) {
    gemstone = getGemFireGemstoneDao().save(getDatabaseGemstoneDao().save(gemstone));

    // NOTE deliberate, but stupid and naive business validation after the mutating data access!
    if (!APPROVED_GEMSTONES.contains(gemstone.getName().toUpperCase())) {
      // NOTE if the gemstone is not valid, blow chunks (should cause transaction to rollback for GemFire and Database)!
      throw new IllegalGemstoneException(String.format("'%1$s' is not a valid gemstone!", gemstone.getName()));
    }

    return gemstone;
  }

  public static final class IllegalGemstoneException extends IllegalArgumentException {

    public IllegalGemstoneException() {
    }

    public IllegalGemstoneException(final String message) {
      super(message);
    }

    public IllegalGemstoneException(final Throwable cause) {
      super(cause);
    }

    public IllegalGemstoneException(final String message, final Throwable cause) {
      super(message, cause);
    }
  }

}

package sample.app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sample.app.dao.GemstoneDao;
import sample.app.dao.provider.DatabaseGemstoneDao;
import sample.app.dao.provider.GemFireGemstoneDao;
import sample.app.domain.Gemstone;
import sample.app.service.GemstoneService;
import sample.app.service.GemstoneService.IllegalGemstoneException;
import sample.app.service.provider.GemFireGemstoneService;

/**
 * The GemFireGlobalTransactionTest class is a test suite of test cases testing the global transactional behavior
 * of both GemFire and an external transactional resource (e.g. RDBMS such as HSQLDB) using JTA and GemFire's
 * Transaction Manager as the JTA implementation.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see sample.app.domain.Gemstone
 * @see sample.app.dao.GemstoneDao
 * @see sample.app.dao.provider.DatabaseGemstoneDao
 * @see sample.app.dao.provider.GemFireGemstoneDao
 * @see sample.app.service.GemstoneService
 * @see sample.app.service.provider.GemFireGemstoneService
 * @see com.gemstone.gemfire.cache.Cache
 * @see com.gemstone.gemfire.cache.CacheFactory
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class AbstractGemFireGlobalTransactionTest {

  protected static final AtomicLong ID_GENERATOR = new AtomicLong(1);

  private Cache gemfireCache;

  private GemstoneService gemstoneService;

  protected static Gemstone createGemstone(final String name) {
    return createGemstone(ID_GENERATOR.getAndIncrement(), name);
  }

  protected static Gemstone createGemstone(final Long id, final String name) {
    return new Gemstone(id, name);
  }

  protected static <T> List<T> toList(final Iterable<T> iterable) {
    List<T> list = new ArrayList<T>();

    if (iterable != null) {
      for (T item : iterable) {
        list.add(item);
      }
    }

    return list;
  }

  protected static String toString(final Iterable<Gemstone> gemstones) {
    StringBuilder buffer = new StringBuilder("[");
    int count = 0;

    for (Gemstone gemstone : gemstones) {
      buffer.append(++count > 1 ? ", " : "").append(toString(gemstone));
    }

    buffer.append("]");

    return buffer.toString();
  }

  protected static String toString(final Gemstone... gemstones) {
    return Arrays.toString(gemstones);
  }

  protected static String toString(final Gemstone gemstone) {
    return gemstone.getName();
  }

  @Before
  public void setup() {
    gemfireCache = new CacheFactory()
      .set("name", "G1")
      .set("cache-xml-file", "gemfire-global-transaction-cache.xml")
      .set("log-level", "config")
      .set("mcast-port", "0")
      .create();

    GemstoneDao databaseGemstoneDao = new DatabaseGemstoneDao(gemfireCache);
    GemstoneDao gemfireGemstoneDao = new GemFireGemstoneDao(gemfireCache.<Long, String>getRegion("Gemstones"));

    databaseGemstoneDao.init();
    gemfireGemstoneDao.init();

    gemstoneService = new GemFireGemstoneService(gemfireCache, databaseGemstoneDao, gemfireGemstoneDao);
    gemstoneService.init();
  }

  @After
  public void tearDown() {
    gemfireCache.close();
    gemfireCache = null;
    gemstoneService = null;
  }

  protected GemstoneService getGemstoneService() {
    return gemstoneService;
  }

  @Test
  public void testGlobalTransactionConfiguration() throws Exception {
    try {
      assertNotNull("A reference to the GemsService was not properly configured!", getGemstoneService());

      assertEquals(0, getGemstoneService().countFromDatabase());
      assertEquals(0, getGemstoneService().countFromGemFire());

      getGemstoneService().save(createGemstone("DIAMOND"));
      getGemstoneService().save(createGemstone("RUBY"));

      assertEquals(2, getGemstoneService().countFromDatabase());
      assertEquals(2, getGemstoneService().countFromGemFire());

      try {
        getGemstoneService().save(createGemstone("Coal"));
        fail("'Coal' is not a valid gemstone!");
      }
      catch (IllegalGemstoneException expected) {
        assertEquals("'Coal' is not a valid gemstone!", expected.getMessage());
      }

      assertEquals(2, getGemstoneService().countFromDatabase());
      assertEquals(2, getGemstoneService().countFromGemFire());

      Gemstone expectedPearl = getGemstoneService().save(createGemstone("Pearl"));
      getGemstoneService().save(createGemstone("sapphire"));

      assertEquals(4, getGemstoneService().countFromDatabase());
      assertEquals(4, getGemstoneService().countFromGemFire());

      try {
        getGemstoneService().save(createGemstone("Quartz"));
        fail("'Quartz' is not a valid gemstone!");
      }
      catch (IllegalGemstoneException expected) {
        assertEquals("'Quartz' is not a valid gemstone!", expected.getMessage());
      }

      assertEquals(4, getGemstoneService().countFromDatabase());
      assertEquals(4, getGemstoneService().countFromGemFire());

      Gemstone databasePearl = getGemstoneService().loadFromDatabase(expectedPearl.getId());

      System.out.printf("Database Pearl (%1$s)%n", databasePearl);

      assertNotNull(databasePearl);
      assertEquals(expectedPearl, databasePearl);

      Gemstone gemfirePearl = getGemstoneService().loadFromGemFire(expectedPearl.getId());

      System.out.printf("GemFire Pearl (%1$s)%n", gemfirePearl);

      assertNotNull(gemfirePearl);
      assertEquals(expectedPearl, gemfirePearl);

      List<Gemstone> databaseGemstones = toList(getGemstoneService().listFromDatabase());
      List<Gemstone> gemfireGemstones = toList(getGemstoneService().listFromGemFire());

      assertFalse(databaseGemstones.isEmpty());
      assertFalse(gemfireGemstones.isEmpty());
      assertEquals(databaseGemstones.size(), gemfireGemstones.size());
      assertTrue(databaseGemstones.containsAll(gemfireGemstones));
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      throw e;
    }
    finally {
      System.out.printf("MySQL 'Gemstones' Table contents (%1$s)%n",
        toString(getGemstoneService().listFromDatabase()));
      System.out.printf("GemFire 'Gemstones' Cache Region contents (%1$s)%n",
        toString(getGemstoneService().listFromGemFire()));
    }
  }

}

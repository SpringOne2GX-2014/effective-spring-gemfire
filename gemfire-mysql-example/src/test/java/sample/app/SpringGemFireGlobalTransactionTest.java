package sample.app;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sample.app.service.GemstoneService;

/**
 * The SpringGemFireGlobalTransactionTest class is a test suite of test cases testing the global transactional behavior
 * of both GemFire and an external transactional resource (e.g. RDBMS such as HSQLDB) using JTA and GemFire's
 * Transaction Manager as the JTA implementation.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.junit.runner.RunWith
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 * @see AbstractGemFireGlobalTransactionTest
 * @see sample.app.service.GemstoneService
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SuppressWarnings("unused")
public class SpringGemFireGlobalTransactionTest extends AbstractGemFireGlobalTransactionTest {

  @Autowired
  private GemstoneService gemstoneService;

  @Override
  public void setup() {
  }

  @Override
  public void tearDown() {
    gemstoneService = null;
  }

  @Override
  protected GemstoneService getGemstoneService() {
    return gemstoneService;
  }

}

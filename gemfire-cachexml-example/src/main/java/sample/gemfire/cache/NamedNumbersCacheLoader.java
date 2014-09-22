package sample.gemfire.cache;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;

import org.springframework.data.gemfire.LazyWiringDeclarableSupport;
import org.springframework.util.Assert;

/**
 * The NamedNumbersCacheLoader class is a GemFire CacheLoader loading Integer-based numbers by name
 * from an external data source into a Region configured with this CacheLoader instance.
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.LazyWiringDeclarableSupport
 * @see com.gemstone.gemfire.cache.CacheLoader
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class NamedNumbersCacheLoader extends LazyWiringDeclarableSupport implements CacheLoader<String, Integer> {

  @Resource(name = "NamedNumbers")
  private Map<String, Integer> namedNumbers;

  public final void setNamedNumbers(final Map<String, Integer> namedNumbers) {
    Assert.notNull(namedNumbers, "The reference to the 'NamedNumbers' Map must not be null!");
    this.namedNumbers = namedNumbers;
  }

  protected Map<String, Integer> getNamedNumbers() {
    Assert.state(namedNumbers != null, "The reference to the 'NamedNumbers' Map was not properly configured and initialized!");
    return namedNumbers;
  }

  @PostConstruct
  public void init() {
    getNamedNumbers();
    System.out.printf("%1$s initialized!%n", getClass().getSimpleName());
  }

  @Override
  public Integer load(final LoaderHelper<String, Integer> helper) throws CacheLoaderException {
    assertInitialized();
    return getNamedNumbers().get(helper.getKey());
  }

  @Override
  public void close() {
  }

}

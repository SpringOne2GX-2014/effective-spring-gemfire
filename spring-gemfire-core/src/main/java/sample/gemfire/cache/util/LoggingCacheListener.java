package sample.gemfire.cache.util;

import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/**
 * The LoggingCacheListener class is a GemFire CacheListener logging Cache Region Entry Events, such as creates, updates
 * and deletes.
 *
 * @author John Blum
 * @see com.gemstone.gemfire.cache.Declarable
 * @see com.gemstone.gemfire.cache.util.CacheListenerAdapter
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class LoggingCacheListener<K, V> extends CacheListenerAdapter<K, V> implements Declarable {

  @Override
  public void afterCreate(final EntryEvent<K, V> event) {
    System.out.printf("Created Region (%1$s) Entry with Key (%2$s) and Value (%3$s)", event.getRegion().getFullPath(),
      event.getKey(), event.getNewValue());
  }

  @Override
  public void afterUpdate(final EntryEvent<K, V> event) {
    System.out.printf("Updated Region (%1$s) Entry with Key (%2$s) from (%3$s) to (%4$s)",
      event.getRegion().getFullPath(), event.getKey(), event.getOldValue(), event.getNewValue());
  }

  @Override
  public void afterDestroy(final EntryEvent<K, V> event) {
    System.out.printf("Removed Region ($1$s) Entry Value for Key (%2$s)", event.getRegion().getFullPath(),
      event.getKey());
  }

  @Override
  public void init(final Properties parameters) {
  }

}

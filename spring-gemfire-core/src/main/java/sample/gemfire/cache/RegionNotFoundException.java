package sample.gemfire.cache;

/**
 * The RegionNotFoundException class is a RuntimeException indicating that the specified Region by name or full path
 * cannot be found.
 *
 * @author John Blum
 * @see RuntimeException
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class RegionNotFoundException extends RuntimeException {

  public RegionNotFoundException() {
  }

  public RegionNotFoundException(final String message) {
    super(message);
  }

  public RegionNotFoundException(final Throwable cause) {
    super(cause);
  }

  public RegionNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

}

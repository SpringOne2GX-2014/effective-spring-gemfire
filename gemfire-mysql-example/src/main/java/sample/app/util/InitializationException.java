package sample.app.util;

/**
 * The InitializationException class is a RuntimeException class indicating an initialization error.
 *
 * @author John Blum
 * @see java.lang.RuntimeException
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class InitializationException extends RuntimeException {

  public InitializationException() {
  }

  public InitializationException(final String message) {
    super(message);
  }

  public InitializationException(final Throwable cause) {
    super(cause);
  }

  public InitializationException(final String message, final Throwable cause) {
    super(message, cause);
  }

}

package sample.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.util.ObjectUtils;

/**
 * The Gemstone class is an abstract data type for modeling a gemstone, such as a diamond or a ruby.
 *
 * @author John Blum
 * @see org.springframework.data.annotation.Id
 * @see org.springframework.data.annotation.PersistenceConstructor
 * @see org.springframework.data.gemfire.mapping.Region
 * @since 1.0.0
 */
@Region("Gemstones")
@SuppressWarnings("unused")
public class Gemstone {

  @Id
  private Long id;

  private String name;

  @PersistenceConstructor
  public Gemstone() {
  }

  public Gemstone(final Long id) {
    this.id = id;
  }

  public Gemstone(final Long id, final String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Gemstone)) {
      return false;
    }

    Gemstone that = (Gemstone) obj;

    return ObjectUtils.nullSafeEquals(this.getId(), that.getId())
      && ObjectUtils.nullSafeEquals(this.getName(), that.getName());
  }

  @Override
  public int hashCode() {
    int hashValue = 17;
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getId());
    hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(getName());
    return hashValue;
  }

  @Override
  public String toString() {
    return String.format("{ @type = %1$s, id = %2$d, name = %3$s }", getClass().getName(), getId(), getName());
  }

}

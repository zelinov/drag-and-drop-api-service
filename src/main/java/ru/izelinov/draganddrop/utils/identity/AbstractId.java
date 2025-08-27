package ru.izelinov.draganddrop.utils.identity;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public abstract class AbstractId<T> {

  protected final T identity;

  protected AbstractId(T identity) {
    this.identity = requireNonNull(identity);
  }

  @Override
  public String toString() {
    return identity.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    return nonNull(object) &&
        getClass() == object.getClass() &&
        identity.equals(((AbstractId<?>) object).identity);
  }

  @Override
  public int hashCode() {
    return identity.hashCode();
  }
}

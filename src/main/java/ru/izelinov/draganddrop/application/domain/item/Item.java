package ru.izelinov.draganddrop.application.domain.item;

import static java.util.Objects.isNull;

public class Item {

  protected ItemId id;
  protected Long version;
  protected String name;
  protected Long position;

  public Item(
      ItemId id,
      Long version,
      String name,
      Long position
  ) {
    this.id = id;
    this.version = version;
    this.name = name;
    this.position = position;
  }

  public void changePosition(Long position) {
    this.position = position;

    validatePosition();
  }

  public ItemId getId() {
    return id;
  }

  public Long getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public Long getPosition() {
    return position;
  }

  protected void validatePosition() {
    if (isNull(this.position)) {
      throw new IllegalArgumentException("Position cannot be null");
    }

    if (this.position < 0) {
      throw new IllegalArgumentException("Position must be greater than zero");
    }
  }
}

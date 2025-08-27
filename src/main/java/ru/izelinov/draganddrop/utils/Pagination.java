package ru.izelinov.draganddrop.utils;

import java.util.Optional;

public class Pagination {

  private Long limit;
  private Long lastPosition;

  public Optional<Long> getLimit() {
    return Optional.ofNullable(limit);
  }

  public Pagination withLimit(Long limit) {
    this.limit = limit;

    return this;
  }

  public Optional<Long> getLastPosition() {
    return Optional.ofNullable(lastPosition);
  }

  public Pagination withLastPosition(Long lastPosition) {
    this.lastPosition = lastPosition;

    return this;
  }
}

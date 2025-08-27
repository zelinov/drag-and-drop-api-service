package ru.izelinov.draganddrop.application.usecase.item.processing;

import java.util.Optional;
import ru.izelinov.draganddrop.application.domain.item.ItemId;
import ru.izelinov.draganddrop.utils.Direction;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class SwapItemsPositionsCommand {

  private ItemId itemId;
  private Direction direction;

  public Optional<ItemId> getItemId() {
    return Optional.ofNullable(itemId);
  }

  public SwapItemsPositionsCommand withItemId(String itemId) {
    this.itemId = isBlank(itemId) ? null : new ItemId(itemId);

    return this;
  }

  public Optional<Direction> getDirection() {
    return Optional.ofNullable(direction);
  }

  public SwapItemsPositionsCommand withDirection(Direction direction) {
    this.direction = direction;

    return this;
  }
}

package ru.izelinov.draganddrop.application.usecase.item.processing;

import java.util.Optional;
import ru.izelinov.draganddrop.application.domain.item.ItemId;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class InsertItemNewPositionCommand {

  private ItemId itemId;
  private ItemId leftItemId;
  private ItemId rightItemId;
  private Boolean recalculateRightItem;

  public Optional<ItemId> getItemId() {
    return Optional.ofNullable(itemId);
  }

  public InsertItemNewPositionCommand withItemId(String itemId) {
    this.itemId = isBlank(itemId) ? null : new ItemId(itemId);

    return this;
  }

  public Optional<ItemId> getLeftItemId() {
    return Optional.ofNullable(leftItemId);
  }

  public InsertItemNewPositionCommand withLeftItemId(String itemId) {
    this.leftItemId = isBlank(itemId) ? null : new ItemId(itemId);

    return this;
  }

  public Optional<ItemId> getRightItemId() {
    return Optional.ofNullable(rightItemId);
  }

  public InsertItemNewPositionCommand withRightItemId(String itemId) {
    this.rightItemId = isBlank(itemId) ? null : new ItemId(itemId);

    return this;
  }

  public Optional<Boolean> getRecalculateRightItem() {
    return Optional.ofNullable(recalculateRightItem);
  }

  public InsertItemNewPositionCommand withRecalculateRightItem(Boolean recalculateRightItem) {
    this.recalculateRightItem = toBoolean(recalculateRightItem);

    return this;
  }
}

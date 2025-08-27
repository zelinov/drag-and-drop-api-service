package ru.izelinov.draganddrop.application.usecase.item.processing;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.ports.inbound.item.processing.InsertItemNewPosition;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;
import ru.izelinov.draganddrop.utils.Direction;
import ru.izelinov.draganddrop.utils.exception.InvalidArgumentException;
import ru.izelinov.draganddrop.utils.exception.NotFoundException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class InsertItemNewPositionImplementation implements InsertItemNewPosition {

  private final Integer windowSize;
  private final Double loadFactor;
  private final ItemRepository itemRepository;

  @Override
  @Transactional
  public void call(InsertItemNewPositionCommand command) {
    final var itemId =
        command
            .getItemId()
            .orElseThrow(() -> new InvalidArgumentException("Item id can't be empty"));

    final var currentItem =
        itemRepository
            .get(itemId)
            .orElseThrow(() -> new NotFoundException("Item with id " + itemId + " not found"));
    final var leftItem =
        command
            .getLeftItemId()
            .map(itemRepository::get)
            .get()
            .orElse(null);
    final var rightItem =
        command
            .getRecalculateRightItem()
            .filter(recalculateRightItem -> recalculateRightItem)
            .map(ignore ->
                isNull(leftItem)
                    ? itemRepository.getNeighbor(0L, Direction.DOWN)
                    : itemRepository.getNeighbor(leftItem.getPosition(), Direction.DOWN)
            )
            .orElse(
                command
                    .getRightItemId()
                    .map(itemRepository::get)
                    .orElse(null)
            )
            .get();

    insertToPosition(
        leftItem,
        rightItem,
        currentItem
    );
  }

  private void insertToPosition(
      Item left,
      Item right,
      Item item
  ) {
    var windowSize = this.windowSize;

    while (true) {
      var leftPosition = nonNull(left) ? left.getPosition() : 0L;
      var windowLeft = (leftPosition / windowSize) * windowSize;
      var windowRight = windowLeft + windowSize;
      var rightPosition = isNull(right) ? windowRight : right.getPosition();

      // проверяем есть ли свободные целые позиции
      if (rightPosition - leftPosition > 1) {
        item.changePosition((leftPosition + rightPosition) / 2);

        itemRepository.save(item);

        return;
      }

      var count = itemRepository.countBetweenPositions(windowLeft, windowRight - 1);
      var loadFactor = (double) count / windowSize;

      // если коэффициент перегрузки не превышает заданного значения запускаем ребалансировку окна
      if (loadFactor < this.loadFactor) {
        rebalanceWindow(windowLeft, windowRight);
        break;
      }

      // если коэффициент перегрузки превышает заданное значение увеличиваем окно в 2 раза
      // и пытаемся отбалансировать большее окно
      windowSize *= 2;
    }
  }

  private void rebalanceWindow(long left, long right) {
    final var items = itemRepository.getAllBetweenPositions(left, right - 1);

    if (items.isEmpty()) {
      return;
    }

    final var step = (right - left) / (items.size() + 1);
    var position = left + step;

    for (Item item : items) {
      item.changePosition(position);
      position += step;
    }

    itemRepository.saveAll(items);
  }
}

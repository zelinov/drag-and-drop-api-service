package ru.izelinov.draganddrop.application.usecase.item.processing;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.izelinov.draganddrop.ports.inbound.item.processing.SwapItemsPositions;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;
import ru.izelinov.draganddrop.utils.exception.InvalidArgumentException;
import ru.izelinov.draganddrop.utils.exception.NotFoundException;

@AllArgsConstructor
public class SwapItemsPositionsImplementation implements SwapItemsPositions {

  private final ItemRepository itemRepository;

  @Override
  @Transactional
  public void call(SwapItemsPositionsCommand command) {
    final var itemId =
        command
            .getItemId()
            .orElseThrow(() -> new InvalidArgumentException("Item id can't be empty"));
    final var currentItem =
        itemRepository
            .get(itemId)
            .orElseThrow(() -> new NotFoundException("Item with id " + itemId + " not found"));
    final var currentItemPosition = currentItem.getPosition();
    final var neighbourItem =
        itemRepository
            .getNeighbor(
                currentItem.getPosition(),
                command
                    .getDirection()
                    .orElseThrow(() -> new InvalidArgumentException("Direction can't be null"))
            )
            .orElseThrow(() ->
                new InvalidArgumentException("Can't find neighbour item for item with id " + itemId)
            );

    currentItem.changePosition(neighbourItem.getPosition());
    neighbourItem.changePosition(currentItemPosition);

    itemRepository.saveAll(
        List.of(currentItem, neighbourItem)
    );
  }
}

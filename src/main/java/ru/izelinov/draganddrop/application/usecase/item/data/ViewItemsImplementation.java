package ru.izelinov.draganddrop.application.usecase.item.data;

import java.util.List;
import lombok.AllArgsConstructor;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.ports.inbound.item.data.ViewItems;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;
import ru.izelinov.draganddrop.utils.exception.InvalidArgumentException;

@AllArgsConstructor
public class ViewItemsImplementation implements ViewItems {

  private final ItemRepository itemRepository;

  @Override
  public List<Item> view(ViewItemsQuery request) {
    final var limit = request
        .getPagination()
        .getLimit()
        .orElseThrow(() -> new InvalidArgumentException("Limit can't be empty"));
    final var lastPosition =
        request
            .getPagination()
            .getLastPosition()
            .orElseThrow(() -> new InvalidArgumentException("Last Position can't be empty"));

    return itemRepository.getAll(limit, lastPosition);
  }
}

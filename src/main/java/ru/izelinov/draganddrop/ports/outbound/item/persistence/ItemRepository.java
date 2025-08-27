package ru.izelinov.draganddrop.ports.outbound.item.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.application.domain.item.ItemId;
import ru.izelinov.draganddrop.utils.Direction;

public interface ItemRepository {

  Long countBetweenPositions(Long from, Long to);

  Optional<Item> get(ItemId itemId);

  Optional<Item> getNeighbor(Long position, Direction direction);

  List<Item> getAll(Long limit, Long lastPosition);

  List<Item> getAllBetweenPositions(Long from, Long to);

  void save(Item item);

  void saveAll(Collection<Item> items);
}

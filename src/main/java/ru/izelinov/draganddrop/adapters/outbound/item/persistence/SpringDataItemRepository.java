package ru.izelinov.draganddrop.adapters.outbound.item.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.dto.ItemEntity;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.spring.ItemJpaRepository;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.application.domain.item.ItemId;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;
import ru.izelinov.draganddrop.utils.Direction;
import ru.izelinov.draganddrop.utils.exception.DuplicateKeyException;

@AllArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SpringDataItemRepository implements ItemRepository {

  private final ItemJpaRepository itemJpaRepository;

  @Override
  public Long countBetweenPositions(Long from, Long to) {
    return itemJpaRepository.countBetweenPositions(from, to);
  }

  @Override
  public Optional<Item> get(ItemId itemId) {
    return itemJpaRepository
        .findById(itemId.getIdentity())
        .map(SpringDataItemRepository::mapItemEntityToItem);
  }

  @Override
  public Optional<Item> getNeighbor(Long position, Direction direction) {
    return findFirstByPosition(position, direction)
        .map(SpringDataItemRepository::mapItemEntityToItem);
  }

  @Override
  public List<Item> getAll(Long limit, Long lastPosition) {
    return itemJpaRepository
        .findAll(limit, lastPosition)
        .stream()
        .map(SpringDataItemRepository::mapItemEntityToItem)
        .toList();
  }

  @Override
  public List<Item> getAllBetweenPositions(Long from, Long to) {
    return itemJpaRepository
        .findAllBetweenPositions(from, to)
        .stream()
        .map(SpringDataItemRepository::mapItemEntityToItem)
        .toList();
  }

  @Override
  @Transactional
  public void save(Item item) {
    try {
      itemJpaRepository.save(mapItemToItemEntity(item));
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateKeyException(e.getMessage());
    }
  }

  @Override
  @Transactional
  public void saveAll(Collection<Item> items) {
    try {
      itemJpaRepository.saveAll(
          items
              .stream()
              .map(SpringDataItemRepository::mapItemToItemEntity)
              .toList()
      );
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateKeyException(e.getMessage());
    }
  }

  private static Item mapItemEntityToItem(ItemEntity itemEntity) {
    return new Item(
        new ItemId(itemEntity.getId()),
        itemEntity.getVersion(),
        itemEntity.getName(),
        itemEntity.getPosition()
    );
  }

  private static ItemEntity mapItemToItemEntity(Item item) {
    return new ItemEntity(
        item.getId().getIdentity(),
        item.getVersion(),
        item.getName(),
        item.getPosition()
    );
  }

  private Optional<ItemEntity> findFirstByPosition(
      Long position,
      Direction direction
  ) {
    return switch (direction) {
      case UP -> itemJpaRepository.findFirstByPositionUp(position);
      case DOWN -> itemJpaRepository.findFirstByPositionDown(position);
    };
  }
}

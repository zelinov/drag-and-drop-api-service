package ru.izelinov.draganddrop.helper;

import java.util.ArrayList;
import java.util.List;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.dto.ItemEntity;

import static java.util.UUID.randomUUID;

public class ItemsHelper {

  public static List<ItemEntity> generateItemEntities(Integer count) {
    final var items = new ArrayList<ItemEntity>();

    for (int i = 0; i < count; i++) {
      final var item = new ItemEntity();
      item.setId(randomUUID());
      item.setName("Item name " + i);
      item.setPosition(i * 1024L);
      items.add(item);
    }

    return items;
  }
}

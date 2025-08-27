package ru.izelinov.draganddrop;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.response.ViewItemsResponse;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.dto.ItemEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertNotNull;
import static ru.izelinov.draganddrop.helper.ItemsHelper.generateItemEntities;

public class ItemTests extends DragAndDropApiServiceTest {

  @BeforeEach
  protected void setup() {
    itemRepository.deleteAll();
  }

  @Test
  protected void testGetItems() {
    itemRepository.saveAll(
        generateItemEntities(3)
    );

    final var response =
        testRestTemplate
            .getForEntity(
                "http://localhost:" + port + "/api/v1/items?limit=2",
                ViewItemsResponse.class
            );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    assertEquals(2, response.getBody().getData().size());
  }

  @Test
  public void testMoveUp() {
    itemRepository.saveAll(
        generateItemEntities(3)
    );

    final var item = itemRepository.findAll().get(2);
    final var response = testRestTemplate
        .postForEntity(
            "http://localhost:" + port + "/api/v1/items/" + item.getId() + "/move/up",
            null,
            Void.class
        );
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  public void testMoveDown() {
    itemRepository.saveAll(
        generateItemEntities(3)
    );

    final var item = itemRepository.findAll().get(0);
    final var response =
        testRestTemplate
            .postForEntity(
                "http://localhost:" + port + "/api/v1/items/" + item.getId() + "/move/down",
                null,
                Void.class
            );
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  protected void testMoveItem() {
    itemRepository.saveAll(
        generateItemEntities(3)
    );

    final var items = itemRepository.findAll();
    final var leftId = items.get(0).getId();
    final var rightId = items.get(1).getId();
    final var movingId = items.get(2).getId();

    final var request = Map.of(
        "leftItemId", leftId,
        "rightItemId", rightId
    );

    final var response = testRestTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/items/" + movingId + "/move",
        request,
        Void.class
    );

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    final var updated = itemRepository.findAll();
    boolean exists = updated.stream().anyMatch(item ->
        item.getId().equals(movingId) &&
            item.getPosition() > items.get(0).getPosition() &&
            item.getPosition() < items.get(1).getPosition()
    );
    assertTrue(exists);
  }

  @Test
  public void testMoveAndRebalance() {
    final var items = generateItemEntities(3);
    final var item1 = items.get(0);
    final var item2 = items.get(1);
    final var item3 = items.get(2);

    itemRepository.saveAll(items);

    // Сначала убедимся, что позиции верны
    final var all = itemRepository.findAll(Sort.by("position"));
    assertEquals(
        List.of(
            item1.getId(),
            item2.getId(),
            item3.getId()
        ),
        all
            .stream()
            .map(ItemEntity::getId)
            .toList()
    );

    Map<String, String> body = Map.of(
        "leftItemId", item2.getId().toString(),
        "rightItemId", item3.getId().toString()
    );

    final var response = testRestTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/items/" + item1.getId() + "/move",
        body,
        Void.class
    );

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    final var updatedItem1 = itemRepository.findById(item1.getId()).orElseThrow();
    assertTrue(updatedItem1.getPosition() > item2.getPosition());
    assertTrue(updatedItem1.getPosition() < item3.getPosition());

    generateItemEntities(10);

    testRestTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/items/" + item3.getId() + "/move/up",
        null,
        Void.class
    );

    final var rebalanced = itemRepository.findAll(Sort.by("position"));
    var prev = Long.MIN_VALUE;
    for (final var it : rebalanced) {
      assertTrue(it.getPosition() > prev, "Positions must be strictly increasing");
      prev = it.getPosition();
    }
  }
}

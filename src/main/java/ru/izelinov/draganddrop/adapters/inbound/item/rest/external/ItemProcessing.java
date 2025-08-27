package ru.izelinov.draganddrop.adapters.inbound.item.rest.external;

import java.util.Map;
import lombok.AllArgsConstructor;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.dto.ItemDto;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.request.MoveItemRequest;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.response.ViewItemsResponse;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.application.usecase.item.data.ViewItemsQuery;
import ru.izelinov.draganddrop.application.usecase.item.processing.SwapItemsPositionsCommand;
import ru.izelinov.draganddrop.ports.inbound.item.data.ViewItems;
import ru.izelinov.draganddrop.ports.inbound.item.processing.InsertItemNewPosition;
import ru.izelinov.draganddrop.ports.inbound.item.processing.SwapItemsPositions;
import ru.izelinov.draganddrop.utils.Direction;

import static ru.izelinov.draganddrop.utils.RetryUtil.retry;

@AllArgsConstructor
public class ItemProcessing {

  private final ViewItems viewItems;
  private final InsertItemNewPosition insertItemNewPosition;
  private final SwapItemsPositions swapItemsPositions;

  protected ViewItemsResponse viewItems(Map<String, String> request) {
    final var items = viewItems.view(new ViewItemsQuery(request));

    return new ViewItemsResponse(
        items
            .stream()
            .map(ItemProcessing::mapItemToItemDto)
            .toList()
    );
  }

  protected void moveUp(String itemId) {
    retry(
        () -> swapItemsPositions.call(
            new SwapItemsPositionsCommand()
                .withItemId(itemId)
                .withDirection(Direction.UP)
        ),
        5,
        10,
        Exception.class
    );
  }

  protected void moveDown(String itemId) {
    retry(
        () -> swapItemsPositions.call(
            new SwapItemsPositionsCommand()
                .withItemId(itemId)
                .withDirection(Direction.DOWN)
        ),
        5,
        10,
        Exception.class
    );
  }

  protected void move(
      String itemId,
      MoveItemRequest request
  ) {
    retry(
        () -> insertItemNewPosition.call(
            request
                .toCommand()
                .withItemId(itemId)
        ),
        () -> insertItemNewPosition.call(
            request
                .toCommand()
                .withItemId(itemId)
                .withRecalculateRightItem(true)
        ),
        10,
        100,
        Exception.class
    );
  }

  private static ItemDto mapItemToItemDto(Item item) {
    return new ItemDto(
        item.getId().toString(),
        item.getName(),
        item.getPosition()
    );
  }
}

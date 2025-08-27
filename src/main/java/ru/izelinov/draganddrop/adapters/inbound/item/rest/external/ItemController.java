package ru.izelinov.draganddrop.adapters.inbound.item.rest.external;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.request.MoveItemRequest;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.response.ViewItemsResponse;

@RestController
@RequestMapping("/api/v1/items")
@AllArgsConstructor
public class ItemController {

  private final ItemProcessing processing;

  /**
   * @apiNote GET /api/v1/items
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ViewItemsResponse> viewItems(
      @RequestParam(required = false) Map<String, String> request
  ) {
    return new ResponseEntity<>(
        processing.viewItems(request),
        HttpStatus.OK
    );
  }

  /**
   * @apiNote POST /api/v1/items/{itemId}/move/up
   */
  @PostMapping("/{itemId}/move/up")
  public ResponseEntity<Map<String, String>> moveUp(
      @PathVariable String itemId
  ) {
    processing.moveUp(itemId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * @apiNote POST /api/v1/items/{itemId}/move/down
   */
  @PostMapping("/{itemId}/move/down")
  public ResponseEntity<Void> moveDown(
      @PathVariable String itemId
  ) {
    processing.moveDown(itemId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * @apiNote POST /api/v1/items/{itemId}/move
   */
  @PostMapping(
      value = "/{itemId}/move",
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Void> move(
      @PathVariable String itemId,
      @RequestBody MoveItemRequest request
  ) {
    processing.move(itemId, request);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

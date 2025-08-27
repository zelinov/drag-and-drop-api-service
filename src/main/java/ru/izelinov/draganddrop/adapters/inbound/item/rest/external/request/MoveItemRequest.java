package ru.izelinov.draganddrop.adapters.inbound.item.rest.external.request;

import ru.izelinov.draganddrop.application.usecase.item.processing.InsertItemNewPositionCommand;

public record MoveItemRequest(
    String leftItemId,
    String rightItemId
) {

  public InsertItemNewPositionCommand toCommand() {
    return new InsertItemNewPositionCommand()
        .withLeftItemId(leftItemId)
        .withRightItemId(rightItemId);
  }
}

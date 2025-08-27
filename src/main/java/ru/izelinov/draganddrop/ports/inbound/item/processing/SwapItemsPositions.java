package ru.izelinov.draganddrop.ports.inbound.item.processing;

import ru.izelinov.draganddrop.application.usecase.item.processing.SwapItemsPositionsCommand;

public interface SwapItemsPositions {

  void call(SwapItemsPositionsCommand command);
}

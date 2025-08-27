package ru.izelinov.draganddrop.ports.inbound.item.processing;

import ru.izelinov.draganddrop.application.usecase.item.processing.InsertItemNewPositionCommand;

public interface InsertItemNewPosition {

  void call(InsertItemNewPositionCommand command);
}

package ru.izelinov.draganddrop.application.domain.item;

import ru.izelinov.draganddrop.utils.identity.GlobalId;

public class ItemId extends GlobalId {

  public ItemId() {
    super();
  }

  public ItemId(Object identity) {
    super(identity);
  }
}

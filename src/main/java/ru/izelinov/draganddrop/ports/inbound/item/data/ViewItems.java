package ru.izelinov.draganddrop.ports.inbound.item.data;

import java.util.List;
import ru.izelinov.draganddrop.application.domain.item.Item;
import ru.izelinov.draganddrop.application.usecase.item.data.ViewItemsQuery;

public interface ViewItems {

  List<Item> view(ViewItemsQuery query);
}

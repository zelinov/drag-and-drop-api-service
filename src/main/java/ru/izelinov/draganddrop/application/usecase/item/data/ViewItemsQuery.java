package ru.izelinov.draganddrop.application.usecase.item.data;

import java.util.Map;
import ru.izelinov.draganddrop.utils.Pagination;

import static java.lang.Long.parseLong;

public class ViewItemsQuery {

  private final Pagination pagination;

  public ViewItemsQuery(Map<String, String> request) {
    this.pagination =
        new Pagination()
            .withLimit(
                request.containsKey("limit")
                    ? parseLong(request.get("limit"))
                    : null
            )
            .withLastPosition(
                request.containsKey("lastPosition")
                    ? parseLong(request.get("lastPosition"))
                    : 0L
            );
  }

  public Pagination getPagination() {
    return pagination;
  }
}

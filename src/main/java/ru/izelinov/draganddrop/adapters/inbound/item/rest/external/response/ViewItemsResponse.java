package ru.izelinov.draganddrop.adapters.inbound.item.rest.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.dto.ItemDto;

@AllArgsConstructor
@NoArgsConstructor
public class ViewItemsResponse {

  @JsonProperty
  private List<ItemDto> data;

  public List<ItemDto> getData() {
    return data;
  }
}

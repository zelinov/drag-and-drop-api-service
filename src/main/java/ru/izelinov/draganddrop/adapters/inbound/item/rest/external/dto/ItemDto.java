package ru.izelinov.draganddrop.adapters.inbound.item.rest.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemDto {

  @JsonProperty
  private String id;
  @JsonProperty
  private String name;
  @JsonProperty
  private Long position;
}

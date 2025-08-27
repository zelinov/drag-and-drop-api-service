package ru.izelinov.draganddrop.configuration.beans.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.izelinov.draganddrop.adapters.inbound.item.rest.external.ItemProcessing;
import ru.izelinov.draganddrop.ports.inbound.item.data.ViewItems;
import ru.izelinov.draganddrop.ports.inbound.item.processing.InsertItemNewPosition;
import ru.izelinov.draganddrop.ports.inbound.item.processing.SwapItemsPositions;

@Configuration
public class ItemRestConfiguration {

  @Bean
  protected ItemProcessing itemProcessing(
      ViewItems viewItems,
      InsertItemNewPosition insertItemNewPosition,
      SwapItemsPositions swapItemsPositions
  ) {
    return new ItemProcessing(
        viewItems,
        insertItemNewPosition,
        swapItemsPositions
    );
  }
}

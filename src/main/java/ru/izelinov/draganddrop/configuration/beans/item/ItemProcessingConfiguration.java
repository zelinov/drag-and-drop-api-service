package ru.izelinov.draganddrop.configuration.beans.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.izelinov.draganddrop.application.usecase.item.processing.InsertItemNewPositionImplementation;
import ru.izelinov.draganddrop.application.usecase.item.processing.SwapItemsPositionsImplementation;
import ru.izelinov.draganddrop.ports.inbound.item.processing.InsertItemNewPosition;
import ru.izelinov.draganddrop.ports.inbound.item.processing.SwapItemsPositions;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;

@Configuration
public class ItemProcessingConfiguration {

  @Bean
  protected InsertItemNewPosition insertItemNewPosition(
      @Value("${app.window-size}") Integer windowSize,
      @Value("${app.load-factor}") Double loadFactor,
      ItemRepository itemRepository
  ) {
    return new InsertItemNewPositionImplementation(
        windowSize,
        loadFactor,
        itemRepository
    );
  }

  @Bean
  protected SwapItemsPositions swapItemsPositions(
      ItemRepository itemRepository
  ) {
    return new SwapItemsPositionsImplementation(
        itemRepository
    );
  }
}

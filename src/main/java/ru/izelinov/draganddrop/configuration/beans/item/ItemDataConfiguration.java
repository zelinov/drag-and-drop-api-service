package ru.izelinov.draganddrop.configuration.beans.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.izelinov.draganddrop.application.usecase.item.data.ViewItemsImplementation;
import ru.izelinov.draganddrop.ports.inbound.item.data.ViewItems;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;

@Configuration
public class ItemDataConfiguration {

  @Bean
  protected ViewItems viewItems(
      ItemRepository itemRepository
  ) {
    return new ViewItemsImplementation(
        itemRepository
    );
  }
}

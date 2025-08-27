package ru.izelinov.draganddrop.configuration.beans.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.SpringDataItemRepository;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.spring.ItemJpaRepository;
import ru.izelinov.draganddrop.ports.outbound.item.persistence.ItemRepository;

@Configuration
public class ItemPersistenceConfiguration {

  @Bean
  protected ItemRepository itemRepository(
      ItemJpaRepository itemJpaRepository
  ) {
    return new SpringDataItemRepository(
        itemJpaRepository
    );
  }
}

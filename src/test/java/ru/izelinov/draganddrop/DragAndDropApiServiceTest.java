package ru.izelinov.draganddrop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.spring.ItemJpaRepository;
import ru.izelinov.draganddrop.configuration.Configuration;
import ru.izelinov.draganddrop.initializer.PostgreSQLInitializer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
    webEnvironment = RANDOM_PORT
)
@ActiveProfiles("test")
@ContextConfiguration(
    initializers = {
        PostgreSQLInitializer.class,
    }
)
@Import({
    Configuration.class
})
public class DragAndDropApiServiceTest {

  @Autowired
  protected TestRestTemplate testRestTemplate;

  @Autowired
  protected ItemJpaRepository itemRepository;

  @LocalServerPort
  protected int port;

  @Test
  public void initializeContext() {
  }
}

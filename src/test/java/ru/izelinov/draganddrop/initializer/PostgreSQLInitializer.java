package ru.izelinov.draganddrop.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.testcontainers.containers.PostgreSQLContainer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PostgreSQLInitializer
    extends PostgreSQLContainer<PostgreSQLInitializer>
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static PostgreSQLInitializer postgreSQLInitializer;

  public PostgreSQLInitializer() {
    super("postgres:17");
  }

  static {
    startContainer();
  }

  private static void startContainer() {
    if (isNull(postgreSQLInitializer)) {
      postgreSQLInitializer = new PostgreSQLInitializer();
      postgreSQLInitializer.start();
    }
  }

  private static void stopContainer() {
    if (
        nonNull(postgreSQLInitializer) &&
        postgreSQLInitializer.isRunning()
    ) {
      postgreSQLInitializer.stop();
    }
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    applicationContext
        .getBeanFactory()
        .registerSingleton("postgreSQLInitializer", postgreSQLInitializer);
    applicationContext.addApplicationListener(applicationEvent -> {
      if (applicationEvent instanceof ContextClosedEvent) {
        stopContainer();
      }
    });

    final var jdbcUrl = postgreSQLInitializer.getJdbcUrl();

    TestPropertyValues
        .of(
            "spring.datasource.url=" + jdbcUrl,
            "spring.datasource.username=" + postgreSQLInitializer.getUsername(),
            "spring.datasource.password=" + postgreSQLInitializer.getPassword(),
            "spring.liquibase.url=" + jdbcUrl,
            "spring.liquibase.user=" + postgreSQLInitializer.getUsername(),
            "spring.liquibase.password=" + postgreSQLInitializer.getPassword()
        )
        .applyTo(applicationContext.getEnvironment());
  }
}

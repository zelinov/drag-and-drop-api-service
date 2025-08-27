package ru.izelinov.draganddrop;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(
    exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    }
)
public class DragAndDropApiService {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DragAndDropApiService.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}

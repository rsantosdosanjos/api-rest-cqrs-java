package nerd.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.SQLException;

@SpringBootApplication
public class ChatApplication {

  public static void main(String[] args) throws SQLException {
    SpringApplication.run(ChatApplication.class, args);
  }

  @Configuration
  @EnableSwagger2
  public static class SwaggerConfig {

    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .title("Simple Spring Boot REST API")
              .description("Um exemplo de implementação spring boot rest com CQRS")
              .version("1.0.0")
                      .build();
    }
  }
}

package com.insider.poc1.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST")
                        .description("API REST Customer and Address")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Tamara Luz")
                                .url("https://github.com/tamarabluz")));
    }

}




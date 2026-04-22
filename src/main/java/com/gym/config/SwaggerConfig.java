package com.gym.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gymOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Gym SaaS CRM API")
                .description("Production-ready backend for multi-tenant Gym management")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("Gym SaaS Documentation")
                .url("https://gym-saas.example.com/docs"));
    }
}

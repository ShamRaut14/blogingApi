package com.codewithme.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		  .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                  .components(new io.swagger.v3.oas.models.Components()
                          .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                  .type(SecurityScheme.Type.HTTP)
                                  .scheme("bearer")
                                  .bearerFormat("JWT")))
                .info(new Info()
                        .title("Blogging Application: Backend")
                        .version("1.0")
                        .description("This project is developed by Sham")
                        .contact(new Contact()
                                .name("Sham Raut")
                                .url("http://localhost:8080/api/posts")
                                .email("Sham00010@gmail.com")));
    }
}

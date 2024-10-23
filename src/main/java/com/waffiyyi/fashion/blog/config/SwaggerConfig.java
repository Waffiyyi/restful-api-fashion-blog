package com.waffiyyi.fashion.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(info = @Info(title = "Fashion Bolg API", version = "1.0.0",
                                description = "Fashion Bolg using RESTful API",
                                termsOfService = "#",
                                contact = @Contact(name = "Mr Waffiyyi",
                                                   email = "fasholawafiyyi@gmail.com",
                                                   url = "https://waffiyyi-fashola.vercel.app"),
                                license = @License(name = "licence", url = "#")

),
                   servers = {
                     @Server(url = "https://selfish-orsa-fashionblog-8e855973.koyeb.app",
                             description = "Production Server"),

                     @Server(url = "http://localhost:8070",
                             description = "Local Server")
                   })
@Configuration
public class SwaggerConfig {
   @Bean
   public OpenAPI customOpenAPI() {
      return new OpenAPI().addSecurityItem(
        new SecurityRequirement().addList("bearerAuth")).components(
        new Components().addSecuritySchemes("bearerAuth", new SecurityScheme().type(
          SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
   }
}
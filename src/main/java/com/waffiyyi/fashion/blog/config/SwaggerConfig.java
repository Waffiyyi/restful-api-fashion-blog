package com.waffiyyi.fashion.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
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
                   },
                   security = @SecurityRequirement(name = "bearerAuth")

)
@SecurityScheme(
  name = "bearerAuth",
  description = "JWT Auth",
  scheme = "bearer",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
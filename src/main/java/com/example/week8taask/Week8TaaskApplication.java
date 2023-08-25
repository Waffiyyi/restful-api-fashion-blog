package com.example.week8taask;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Week 8 Task",
                version = "1.0.0",
                description = "Fashion Bolg using RESTful API",
                termsOfService = "Waffiyyi",
                contact = @Contact(
                        name = "Mr Wafiyyi",
                        email = "fasholawafiyyi@gmail.com"
                ),
                license = @License(
                        name = "licence",
                        url = "waffiyyi"
                )
        )
)
public class Week8TaaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week8TaaskApplication.class, args);
    }
}

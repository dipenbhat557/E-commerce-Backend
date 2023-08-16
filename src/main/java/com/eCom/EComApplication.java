package com.eCom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class EComApplication {

	public static void main(String[] args) {
		SpringApplication.run(EComApplication.class, args);
	}

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
            .title("Ecommerce")
            .version("1.0")
            .description("API documentation of Ecommerce website for avaash")
            .termsOfService("Terms of service")
            .contact(new io.swagger.v3.oas.models.info.Contact()
                .name("Dipendra")
                .url("https://localhost:8080")
                .email("bhattadipen557@gmail.com"))
            .license(new io.swagger.v3.oas.models.info.License()
                .name("License of API")
                .url("API license URL")));
    }

}

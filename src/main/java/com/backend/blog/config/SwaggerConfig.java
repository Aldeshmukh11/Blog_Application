package com.backend.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	    @Bean
	    OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                .title("Blogging Application : Spring Boot Backend Project")
	                .version("1.0")
	                .description("This project is developed by Abhijeet")
	                .termsOfService("Terms of Service")
	                .contact(new Contact()
	                .name("Abhijeet")
	                .url("https://abhi.xyz")
	                .email("abhi@xyz.in"))
	                .license(new License()
	                .name("License of APIs")
	                .url("API license URL")))
	                .addSecurityItem(new SecurityRequirement().addList("JWT"))
	                .components(new Components()
	                .addSecuritySchemes("JWT",
	                    new SecurityScheme()
	                        .name("Authorization")
	                        .type(SecurityScheme.Type.HTTP)
	                        .scheme("bearer")
	                        .bearerFormat("JWT")
	                        .in(SecurityScheme.In.HEADER)));
	    }
}

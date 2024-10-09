package com.sottie.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		Server server = new Server()
			.url("/")
			.description("server description");

		Contact contact = new Contact()
			.email("sottie@sottie.com")
			.name("sottie")
			.url("/");

		License mitLicense = new License()
			.name("MIT License")
			.url("https://choosealicense.com/licenses/mit/");

		Info info = new Info()
			.title("info title")
			.version("1.0")
			.contact(contact)
			.description("info description")
			.termsOfService("/terms")
			.license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(server));
	}
}

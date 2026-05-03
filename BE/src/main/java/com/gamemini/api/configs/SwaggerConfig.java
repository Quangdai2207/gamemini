package com.gamemini.api.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;


/**
 * <h1 style="color: white">Cau hinh tich hop Swagger vao Spring Boot</h1>
 *
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation - Gamemini",
                version = "1.0.0",
                description = "APIs Document",
                contact = @Contact(name = "Host", email = "gamemini@app.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
        )
)
public class SwaggerConfig {
}

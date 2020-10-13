package org.api.mock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * The Swagger config.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Mock-Api", version = "v1"))
public class OpenApiConfig {


}
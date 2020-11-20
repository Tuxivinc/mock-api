package org.api.mock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Swagger config.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Mock-Api", version = "v1"))
public class OpenApiConfig {

    @Value("${keycloak.auth-server-url}")
    private String authServer;
    @Value("${keycloak.realm}")
    private String realm;

    public static final String KEYCLOACK_SCHEMA_NAME = "OAuth2";

    @Bean
    public OpenAPI openAPI() {
        var authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(KEYCLOACK_SCHEMA_NAME, new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Oauth2 flow")
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(authUrl + "/auth")
                                                .refreshUrl(authUrl + "/token")
                                                .tokenUrl(authUrl + "/token")
                                                .scopes(new Scopes())))));
    }

}
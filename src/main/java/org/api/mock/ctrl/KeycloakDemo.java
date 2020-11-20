package org.api.mock.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.api.mock.config.OpenApiConfig;
import org.api.mock.services.KeycloakService;
import org.api.mock.services.helper.KeycloakHelper;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class KeycloakDemo {

    @Resource
    private KeycloakService keycloakService;

    @GetMapping(path = "/unsecured")
    @Operation(summary = "Call an no secured entrypoint")
    public ResponseEntity<String> noSecuredEndpoint() {
        return new ResponseEntity<>("This is an unsecured endpoint", HttpStatus.OK);
    }

    @PostMapping(path = "/token", produces = "application/json")
    @Operation(summary = "Get token for user")
    public ResponseEntity<String> getTocken(@RequestParam String username, @RequestParam String password) throws InterruptedException, IOException {
        // NB: Password in request param are use only for this example
        HttpResponse<String> repToken = keycloakService.token(username, password);
        return new ResponseEntity<>(repToken.body(), HttpStatus.resolve(repToken.statusCode()));
    }

    @GetMapping(path = "/admin")
    @Operation(summary = "Call an entrypoint restricted to admin", security = @SecurityRequirement(name = OpenApiConfig.KEYCLOACK_SCHEMA_NAME))
    public ResponseEntity<AccessToken> adminSecuredEndpoint(Principal principal) {
        return getInfosFromToken(principal);
    }

    @GetMapping("/user")
    @Operation(summary = "Call an entrypoint restricted to user", security = @SecurityRequirement(name = OpenApiConfig.KEYCLOACK_SCHEMA_NAME))
    public ResponseEntity<AccessToken> userSecuredEndpoint(Principal principal) {
        return getInfosFromToken(principal);
    }

    @GetMapping("/all-user")
    @Operation(summary = "Call an entrypoint restricted to admin and user", security = @SecurityRequirement(name = OpenApiConfig.KEYCLOACK_SCHEMA_NAME))
    public ResponseEntity<AccessToken> allSecuredEndpoint(Principal principal) {
        return getInfosFromToken(principal);
    }

    private ResponseEntity<AccessToken> getInfosFromToken(Principal principal) {
        return new ResponseEntity<>(KeycloakHelper.getAccessTokenFromPrincipal(principal), HttpStatus.OK);
    }

}

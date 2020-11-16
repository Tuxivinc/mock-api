package org.api.mock.services.helper;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

import java.security.Principal;

public class KeycloakHelper {

    private KeycloakHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static AccessToken getAccessTokenFromPrincipal(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
    }

}

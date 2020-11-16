package org.api.mock.services;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class KeycloakService {

    private static final String KEY_GRANT_TYPE = "grant_type";
    private static final String VALUE_GRANT_TYPE = "password";
    private static final String KEY_CONTENT_TYPE = "Content-Type";
    private static final String VALUE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_CLIENT_SECRET = "client_secret";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Resource
    private KeycloakConfigResolver keycloakConfigResolver;


    public HttpResponse<String> token(String username, String password) throws IOException, InterruptedException {
        KeycloakDeployment keycloakDeployment = keycloakConfigResolver.resolve(null);
        String tokenUrl = keycloakDeployment.getTokenUrl();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put(KEY_GRANT_TYPE, VALUE_GRANT_TYPE);
        parameters.put(KEY_CLIENT_ID, clientId);
        parameters.put(KEY_CLIENT_SECRET, clientSecret);
        parameters.put(KEY_USERNAME, username);
        parameters.put(KEY_PASSWORD, password);
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(tokenUrl))
                .headers(KEY_CONTENT_TYPE, VALUE_APPLICATION_X_WWW_FORM_URLENCODED)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}

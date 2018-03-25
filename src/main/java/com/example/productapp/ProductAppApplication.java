package com.example.productapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductAppApplication {

    public static final Logger LOG = LoggerFactory.getLogger(ProductAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductAppApplication.class, args);
    }

    @Bean
    public KeycloakApi keycloakApi(
            @Value("${keycloak.auth-server-url}") String authServerUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.resource}") String resource,
            @Value("${keycloak.credentials.secret:#{null}}") String clientSecret
    ) {
        String url = authServerUrl + "/realms/" + realm + "/";
        if (clientSecret != null) {
            return new ConfidentialKeycloakApi(url, resource, clientSecret);
        }

        return new PublicKeycloakApi(url, resource);
    }
}

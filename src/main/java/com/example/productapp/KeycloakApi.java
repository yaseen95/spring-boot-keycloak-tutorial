package com.example.productapp;

import javax.validation.constraints.NotNull;

/**
 * Interface for Keycloak's openid-connect REST endpoint.
 *
 * There is very basic validation, and no exception handling. These are just examples.
 *
 * Return types are simply Object because I didn't want to create custom classes. However, jackson still serializes them
 * with the json data returned by keycloak.
 */
public interface KeycloakApi {
    /**
     * Logs a user in.
     * @param username
     * @param password
     * @return
     */
    @NotNull
    Object login(@NotNull String username, @NotNull String password);

    /**
     * Logs a user out
     * @param accessToken the bearer token with the "Bearer " suffix
     * @param refreshToken the refresh token that is returned in the payload of {@link #login(String, String)}
     * @return
     */
    @NotNull
    Object logout(@NotNull String accessToken, @NotNull String refreshToken);

    /**
     * Returns user info
     * @param accessToken the bearer token with the "Bearer " suffix
     * @return
     */
    @NotNull
    Object userInfo(@NotNull String accessToken);
}

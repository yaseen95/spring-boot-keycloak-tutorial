package com.example.productapp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * {@link KeycloakApi} implementation for a Keycloak client with a `Confidential` access type.
 */
public class ConfidentialKeycloakApi implements KeycloakApi {

    private final String loginUrl;
    private final String logoutUrl;
    private final String userInfoUrl;
    private final String clientId;
    // TODO: Should this stay final?
    // Would we need to support changing the secret during run time?
    private final String clientSecret;
    private final RestTemplate restTemplate;

    public ConfidentialKeycloakApi(String baseUrl, String clientId, String clientSecret) {
        this.loginUrl = baseUrl + KeycloakKeys.OPENID_TOKEN_URL;
        this.logoutUrl = baseUrl + KeycloakKeys.OPENID_LOGOUT_URL;
        this.userInfoUrl = baseUrl + KeycloakKeys.OPENID_USERINFO_URL;

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Override
    public Object login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add(KeycloakKeys.FORM_USERNAME, username);
        payload.add(KeycloakKeys.FORM_PASSWORD, password);
        payload.add(KeycloakKeys.FORM_GRANT_TYPE, KeycloakKeys.GRANT_TYPE_PASSWORD);

        return this.post(loginUrl, headers, payload);
    }

    @Override
    public Object logout(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add(KeycloakKeys.FORM_REFRESH_TOKEN, refreshToken);

        return post(logoutUrl, headers, payload);
    }

    @Override
    public Object userInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        return post(userInfoUrl, headers, payload);
    }

    private Object post(String url, HttpHeaders headers, MultiValueMap<String, String> payload) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        payload.add(KeycloakKeys.FORM_CLIENT_SECRET, clientSecret);
        payload.add(KeycloakKeys.FORM_CLIENT_ID, clientId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForEntity(url, request, Object.class).getBody();
    }
}

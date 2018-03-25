package com.example.productapp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * {@link KeycloakApi} implementation for a Keycloak client with a `Public` access type.
 */
public class PublicKeycloakApi implements KeycloakApi {

    private final String loginUrl;
    private final String logoutUrl;
    private final String userInfoUrl;
    private final String clientId;
    private final RestTemplate restTemplate;

    public PublicKeycloakApi(String baseUrl, String clientId) {
        this.loginUrl = baseUrl + KeycloakKeys.OPENID_TOKEN_URL;
        this.logoutUrl = baseUrl + KeycloakKeys.OPENID_LOGOUT_URL;
        this.userInfoUrl = baseUrl + KeycloakKeys.OPENID_USERINFO_URL;
        this.clientId = clientId;

        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Override
    public Object login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add(KeycloakKeys.FORM_USERNAME, username);
        payload.add(KeycloakKeys.FORM_PASSWORD, password);
        payload.add(KeycloakKeys.FORM_GRANT_TYPE, KeycloakKeys.GRANT_TYPE_PASSWORD);

        return post(loginUrl, headers, payload);
    }

    @Override
    public Object logout(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add(KeycloakKeys.FORM_REFRESH_TOKEN, refreshToken);

        return post(logoutUrl, headers, payload);
    }

    @Override
    public Object userInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        HttpEntity<Object> request = new HttpEntity<>(headers);
        return restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Object.class).getBody();
    }

    private Object post(String url, HttpHeaders headers, MultiValueMap<String, String> payload) {
        payload.add(KeycloakKeys.FORM_CLIENT_ID, clientId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForEntity(url, request, Object.class).getBody();
    }
}

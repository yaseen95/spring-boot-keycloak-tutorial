package com.example.productapp;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
class UserServiceImpl implements UserService {

    @Override
    public ProductAppUser getUser() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) principal;

        IDToken idToken = kp.getKeycloakSecurityContext().getIdToken();
        return ImmutableProductAppUser.builder()
                .userId(idToken.getSubject())
                .username(idToken.getPreferredUsername())
                .firstName(Optional.ofNullable(idToken.getGivenName()))
                .lastName(Optional.ofNullable(idToken.getFamilyName()))
                .build();
    }
}

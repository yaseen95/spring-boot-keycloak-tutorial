package com.example.productapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    private final KeycloakApi keycloakApi;

    @Autowired
    public AuthController(UserService userService, KeycloakApi keycloakApi) {
        this.userService = userService;
        this.keycloakApi = keycloakApi;
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest loginRequest) {
        ProductAppApplication.LOG.info("Attempting login for {}", loginRequest.getUsername());
        Assert.notNull(loginRequest.getUsername(), "Must supply username to login");
        Assert.notNull(loginRequest.getPassword(), "Must supply password to login");

        return keycloakApi.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @GetMapping("/info")
    public Object info(@RequestHeader HttpHeaders httpHeaders) {
        ProductAppApplication.LOG.info("Getting info for {}", userService.getUser().username());
        String accessToken = HeaderUtils.getAuthorization(httpHeaders);
        return keycloakApi.userInfo(accessToken);
    }

    @PostMapping("/logout")
    public Object logout(@RequestHeader HttpHeaders httpHeaders, @RequestBody LogoutRequest logoutRequest) {
        ProductAppApplication.LOG.info("Logging out {}", userService.getUser().username());
        Assert.notNull(logoutRequest.getRefreshToken(), "Must supply refresh token to logout");

        String accessToken = HeaderUtils.getAuthorization(httpHeaders);
        return keycloakApi.logout(accessToken, logoutRequest.getRefreshToken());
    }
}

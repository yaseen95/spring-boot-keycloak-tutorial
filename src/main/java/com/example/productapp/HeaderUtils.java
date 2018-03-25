package com.example.productapp;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import java.util.List;

public class HeaderUtils {
    public static String getAuthorization(HttpHeaders httpHeaders) {
        List<String> authorizationHeaders = httpHeaders.get(HttpHeaders.AUTHORIZATION);
        Assert.isTrue(authorizationHeaders.size() == 1, "Expected only 1 value for Authorization");
        return authorizationHeaders.get(0);
    }
}

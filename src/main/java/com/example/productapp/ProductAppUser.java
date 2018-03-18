package com.example.productapp;

import org.immutables.value.Value;

import java.util.Optional;

/**
 * A user of the ProductApp.
 */
@Value.Immutable
public abstract class ProductAppUser {

    public abstract String userId();
    public abstract String username();
    public abstract Optional<String> firstName();
    public abstract Optional<String> lastName();
}

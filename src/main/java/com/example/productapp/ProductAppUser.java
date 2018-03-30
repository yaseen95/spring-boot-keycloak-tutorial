package com.example.productapp;

import org.immutables.value.Value;

import java.util.Optional;

/**
 * A user of the ProductApp.
 *
 * Doesn't need to be immutable, I just wanted to experiment with the immutables library.
 */
@Value.Immutable
public abstract class ProductAppUser {

    public abstract String userId();
    public abstract String username();
    public abstract Optional<String> firstName();
    public abstract Optional<String> lastName();
}

package com.example.productapp;


interface UserService {
    /**
     * Get the user making the request
     * @return a representation of the User making the request
     */
    ProductAppUser getUser();
}

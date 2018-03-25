package com.example.productapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    @GetMapping(path = "")
    public List<String> getProducts() {
        return Arrays.asList("iPad", "iPhone", "iPod");
    }
}

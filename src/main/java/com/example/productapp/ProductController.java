package com.example.productapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
class ProductController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/products")
    public String getProducts(Model model) {
        ProductAppUser user = userService.getUser();
        model.addAttribute("customUser", user);
        model.addAttribute("products", Arrays.asList("iPad", "iPhone", "iPod"));
        return "products";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}

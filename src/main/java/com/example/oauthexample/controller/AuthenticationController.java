package com.example.oauthexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "This is a protected endpoint.";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        //return "error/unauthorized";
        return "You are not authorized to access this resource";
    }
}
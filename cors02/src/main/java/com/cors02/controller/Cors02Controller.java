package com.cors02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cors02Controller {
    @GetMapping("/api/users")
    public User users() {
        return new User("user", 20);
    }
}

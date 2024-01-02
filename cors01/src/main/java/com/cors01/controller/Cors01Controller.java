package com.cors01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cors01Controller {
    @GetMapping("/")
    public String index() {
        // index.html 리턴
        return "index";
    }
}

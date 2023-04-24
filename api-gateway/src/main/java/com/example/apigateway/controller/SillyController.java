package com.example.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SillyController {

    @GetMapping("/silly")
    public String getSillyMessage() {
        return "Silly message";
    }
}

package com.sandesh.REST_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health-check")    // whatever this returns it automatically converted into json()
    public String healthCheck() {
        return "OK";
    }
}

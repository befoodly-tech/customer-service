package com.befoodly.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {return ResponseEntity.ok("pong!");}
}

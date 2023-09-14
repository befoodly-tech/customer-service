package com.befoodly.be.controller;

import com.befoodly.be.model.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:5173")
public class HealthCheckController {

    @GetMapping(value = "/ping")
    public ResponseEntity<GenericResponse<String>> ping() {
        return ResponseEntity.ok(GenericResponse.<String>builder()
            .statusCode(HttpStatus.OK.value())
            .data("pong!")
            .build());
    }
}

package com.befoodly.be.controller;

import com.befoodly.be.model.request.CookCreateRequest;
import com.befoodly.be.service.CookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/cook")
@RequiredArgsConstructor
public class CookController {

    @Autowired
    private final CookService cookService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCookProfile (@RequestBody CookCreateRequest request) {
        cookService.createCookForVendor(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

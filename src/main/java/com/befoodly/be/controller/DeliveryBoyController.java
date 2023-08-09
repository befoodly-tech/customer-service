package com.befoodly.be.controller;

import com.befoodly.be.model.request.DeliveryBoyCreateRequest;
import com.befoodly.be.service.DeliveryBoyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/delivery-boy")
@RequiredArgsConstructor
public class DeliveryBoyController {

    @Autowired
    private final DeliveryBoyService deliveryBoyService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createDeliveryBoyProfile (@RequestBody DeliveryBoyCreateRequest request) {
        deliveryBoyService.createDeliveryBoyProfile(request);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}

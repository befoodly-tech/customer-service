package com.befoodly.be.controller;

import com.befoodly.be.model.response.DeliveryResponse;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/delivery")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class DeliveryController {

    @Autowired
    private final DeliveryService deliveryService;

    @GetMapping("/pending-orders/{customerReferenceId}")
    public ResponseEntity<GenericResponse<List<DeliveryResponse>>> fetchPendingDeliveryDetails(@PathVariable String customerReferenceId) {
        List<DeliveryResponse> deliveryDetails = deliveryService.fetchDeliveryDetails(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<List<DeliveryResponse>>builder()
                .data(deliveryDetails)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

}

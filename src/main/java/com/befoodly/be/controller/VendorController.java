package com.befoodly.be.controller;

import com.befoodly.be.model.request.VendorRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.model.response.VendorResponse;
import com.befoodly.be.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vendor")
@RequiredArgsConstructor
public class VendorController {

    @Autowired
    private final VendorService vendorService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createVendor(@RequestBody VendorRequest request) {
        vendorService.createNewVendor(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/fetch/{vendorId}")
    public ResponseEntity<GenericResponse<VendorResponse>> fetchVendor(@PathVariable(value = "vendorId") Long vendorId) {
        VendorResponse vendorResponse = vendorService.fetchVendorData(vendorId);

        return new ResponseEntity<>(GenericResponse.<VendorResponse>builder()
                .data(vendorResponse)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}

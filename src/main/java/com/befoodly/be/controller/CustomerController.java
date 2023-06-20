package com.befoodly.be.controller;

import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.request.CustomerEditRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping(value = "/create")
    public ResponseEntity<GenericResponse<String>> createCustomer(@RequestBody CustomerCreateRequest request) {
        String customerReferenceId = customerService.createCustomer(request);

        return new ResponseEntity<>(GenericResponse.<String>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(customerReferenceId)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/edit/{customerReferenceId}")
    public ResponseEntity<GenericResponse<CustomerEntity>> editCustomer(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                                @RequestBody CustomerEditRequest request) {
        CustomerEntity updatedCustomerData = customerService.editCustomerDetails(customerReferenceId, request);

        return new ResponseEntity<>(GenericResponse.<CustomerEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(updatedCustomerData)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{customerReferenceId}")
    public ResponseEntity<GenericResponse<CustomerEntity>> getCustomerDetails(@PathVariable(value = "customerReferenceId") String customerReferenceId) {
        CustomerEntity customerData = customerService.fetchCustomerDetails(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<CustomerEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerData)
                .build(), HttpStatus.OK);
    }
}

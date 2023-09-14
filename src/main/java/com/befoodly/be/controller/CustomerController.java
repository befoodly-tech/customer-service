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

import static com.befoodly.be.model.constant.CommonConstants.CUSTOMER_ID;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping(value = "/create")
    public ResponseEntity<GenericResponse<CustomerEntity>> createCustomer(@RequestBody CustomerCreateRequest request) {
        CustomerEntity customerEntity = customerService.createCustomer(request);

        return new ResponseEntity<>(GenericResponse.<CustomerEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(customerEntity)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/edit/{customerReferenceId}")
    public ResponseEntity<GenericResponse<CustomerEntity>> editCustomer(@PathVariable(value = CUSTOMER_ID) String customerReferenceId,
                                                                @RequestBody CustomerEditRequest request) {
        CustomerEntity updatedCustomerData = customerService.editCustomerDetails(customerReferenceId, request);

        return new ResponseEntity<>(GenericResponse.<CustomerEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(updatedCustomerData)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{customerReferenceId}")
    public ResponseEntity<GenericResponse<CustomerEntity>> getCustomerDetails(@PathVariable(value = CUSTOMER_ID) String customerReferenceId) {
        CustomerEntity customerData = customerService.fetchCustomerDetails(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<CustomerEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerData)
                .build(), HttpStatus.OK);
    }
}
